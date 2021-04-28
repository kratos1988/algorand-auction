package com.algorand.auction.job;

import com.algorand.auction.model.ExpiredAuction;
import com.algorand.auction.usecase.AuctionRepository;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class FinalizeAuctionsJob {

    private final ExecuteTransactionUseCase useCase;
    private final AuctionRepository auctionRepository;

    public FinalizeAuctionsJob(
            ExecuteTransactionUseCase useCase,
            AuctionRepository auctionRepository
    ) {
        this.useCase = useCase;
        this.auctionRepository = auctionRepository;
    }

    @Scheduled(cron = "0 * * ? * * *")
    public void apply() {
        List<ExpiredAuction> auctions = auctionRepository.retrieveExpired();
        auctions.forEach(expiredAuction -> useCase.execute(expiredAuction.getBuyer(), expiredAuction.getSeller(), ""));
    }
}
