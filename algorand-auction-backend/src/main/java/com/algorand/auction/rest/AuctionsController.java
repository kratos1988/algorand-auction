package com.algorand.auction.rest;

import com.algorand.auction.model.Auction;
import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AuctionsController {

    private RetrieveAuctionsUseCase useCase;

    @Autowired
    public AuctionsController(
            RetrieveAuctionsUseCase useCase
    ) {
        this.useCase = useCase;
    }

    @GetMapping("/auctions")
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = useCase.retrieveAll();
        return ok(auctions);

    }
}
