package com.algorand.auction.rest;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController("auction")
public class AuctionController {

    private RetrieveAuctionsUseCase useCase;

    @Autowired
    public AuctionController(
            RetrieveAuctionsUseCase useCase
    ) {
        this.useCase = useCase;
    }

    @GetMapping("/auctions/all")
    public ResponseEntity<List<Item>> getAllAuctions() {
            return ok(useCase.retrieveAll());
    }

    @GetMapping("/auctions/{id}")
    public ResponseEntity<Auction> getAuction(
            @PathVariable Integer id
    ) {
        return ok(useCase.retrieveBy(id));
    }
}
