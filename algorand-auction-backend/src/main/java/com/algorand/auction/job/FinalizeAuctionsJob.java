package com.algorand.auction.job;

import com.algorand.auction.model.*;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.Tuple4;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vavr.control.Either.right;

@Component
public class FinalizeAuctionsJob {

    private final Logger logger = LoggerFactory.getLogger(FinalizeAuctionsJob.class);

    private final ExecuteTransactionUseCase useCase;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    public FinalizeAuctionsJob(
            ExecuteTransactionUseCase useCase,
            AuctionRepository auctionRepository,
            BidRepository bidRepository, UserRepository userRepository) {
        this.useCase = useCase;
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void apply() {
        logger.info("Starting job: FinalizeAuctionsJob");
        Either<FailureError, Void> result = auctionRepository.retrieveExpired().flatMap(this::createTransaction);
        logger.info("FInished job: FinalizeAuctionsJob with result: {}", result.get());
    }

    private Either<FailureError, Void> createTransaction(List<Item> items) {
        AtomicInteger errors = new AtomicInteger();
        AtomicInteger successes = new AtomicInteger();;
        items.stream().map(expiredItem -> createTransactionEither(expiredItem)).forEach(result -> result.fold(
                failureError -> errors.getAndIncrement(),
                transaction -> successes.getAndIncrement()
        ));
        return right(null);
    }

    private Either<FailureError, String> createTransactionEither(Item expiredItem) {
        return getHighestBid(expiredItem)
                .flatMap((Tuple2<Item, Bid> input) -> getSeller(input._1, input._2))
                .flatMap((Tuple3<Item, Bid, User> input) -> getBuyer(input._1, input._2, input._3))
                .flatMap((Tuple4<Item, Bid, User, User> input) -> executeTransaction(input._2, input._3, input._4));
    }

    private Either<FailureError, String> executeTransaction(Bid bid, User seller, User buyer) {
        Transaction transaction = new Transaction();
        transaction.setAmount(bid.getAmount());
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        logger.info("Executing use case with parameters: {}", transaction);
        return useCase.execute(transaction);
    }

    private Either<FailureError, Tuple2<Item, Bid>> getHighestBid(Item expiredItem) {
        return bidRepository.getHighestBidFor(expiredItem.getId()).map(bid -> Tuple.of(expiredItem, bid));
    }

    private Either<FailureError, Tuple3<Item, Bid, User>> getSeller(Item item, Bid bid) {
        return userRepository.getUserById(item.getUserId()).flatMap(seller -> right(Tuple.of(item, bid, seller)));
    }

    private Either<FailureError, Tuple4<Item, Bid, User, User>> getBuyer(Item expiredItem, Bid bid, User seller) {
        return userRepository.getUserById(bid.getUserId()).flatMap(buyer -> right(Tuple.of(expiredItem, bid, seller, buyer)));
    }

}
