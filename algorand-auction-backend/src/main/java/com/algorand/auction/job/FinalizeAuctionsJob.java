package com.algorand.auction.job;

import com.algorand.auction.model.Bid;
import com.algorand.auction.model.Item;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FinalizeAuctionsJob {

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

    @Scheduled(cron = "0 * * ? * * *")
    public void apply() {
        List<Transaction> transactions = auctionRepository.retrieveExpired().stream().map(expiredItem -> {
            return createTransaction(expiredItem);
        }).collect(toList());
        transactions.forEach(expiredAuction -> useCase.execute(null, null, ""));
    }

    private Transaction createTransaction(Item expiredItem) {
        Transaction transaction = new Transaction();
        Bid bid = bidRepository.getHighestBidFor(expiredItem.getId());
        User user = userRepository.getUserBy(expiredItem.getUserId());
        transaction.setSeller(user);
        transaction.setAmount(expiredItem.getHighestBid());
        return transaction;
    }
}
