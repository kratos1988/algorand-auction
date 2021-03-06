package com.algorand.auction.job;

import com.algorand.auction.model.*;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.ItemRepository;
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
import static java.util.stream.Collectors.toList;

@Component
public class FinalizeAuctionsJob {

    private final Logger logger = LoggerFactory.getLogger(FinalizeAuctionsJob.class);

    private final ExecuteTransactionUseCase useCase;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    public FinalizeAuctionsJob(
            ExecuteTransactionUseCase useCase,
            ItemRepository itemRepository,
            BidRepository bidRepository, UserRepository userRepository) {
        this.useCase = useCase;
        this.itemRepository = itemRepository;
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void apply() {
        logger.info("Starting job: FinalizeAuctionsJob");
        Either<FailureError, Void> result = itemRepository.retrieveExpired()
                .flatMap(this::createTransaction)
                .flatMap(items -> itemRepository.setStatusFinished(items.stream().map(Item::getId).collect(toList())));
        if (result.isLeft())
            logger.warn("Finished job: FinalizeAuctionsJob with result false");
        else
            logger.info("Finished job: FinalizeAuctionsJob with result: {}", result.get());
    }

    private Either<FailureError, List<Item>> createTransaction(List<Item> items) {
        AtomicInteger errors = new AtomicInteger();
        AtomicInteger successes = new AtomicInteger();
        items.stream().map(this::createTransactionEither)
                .forEach(result -> result.fold(
                        failureError -> errors.getAndIncrement(),
                        transaction -> successes.getAndIncrement()
                ));
        logger.info("Number of successful transactions: " + successes.get());
        logger.info("Number of failing transactions: " + errors.get());
        return right(items);
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
        return userRepository.getUserByUsername(item.getSeller()).flatMap(seller -> right(Tuple.of(item, bid, seller)));
    }

    private Either<FailureError, Tuple4<Item, Bid, User, User>> getBuyer(Item expiredItem, Bid bid, User seller) {
        return userRepository.getUserByUsername(bid.getUsername()).flatMap(buyer -> right(Tuple.of(expiredItem, bid, seller, buyer)));
    }

}
