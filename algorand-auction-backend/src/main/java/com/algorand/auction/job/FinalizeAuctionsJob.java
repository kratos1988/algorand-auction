package com.algorand.auction.job;

import com.algorand.auction.model.Item;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FinalizeAuctionsJob {

    private final ExecuteTransactionUseCase useCase;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public FinalizeAuctionsJob(
            ExecuteTransactionUseCase useCase,
            AuctionRepository auctionRepository,
            UserRepository userRepository) {
        this.useCase = useCase;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 * * ? * * *")
    public void apply() {
        List<Item> items = auctionRepository.retrieveExpired().stream().map(expiredAuctionDto -> {
            Item expiredItem = new Item();
            User user = userRepository.getUserBy(expiredAuctionDto.getUserId());
            return expiredItem;
        }).collect(toList());
        items.forEach(expiredAuction -> useCase.execute(null, null, ""));
    }
}
