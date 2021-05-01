package com.algorand.auction.rest;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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

        Either<FailureError, List<Item>> result = useCase.retrieveAll();
        if (result.isRight())
            return ok(result.get());
        else {
            return status(404).build();
        }
    }

    @GetMapping("/auctions/{id}")
    public ResponseEntity<Auction> getAuction(
            @PathVariable Integer id
    ) {
        Either<FailureError, Auction> result = useCase.retrieveById(id);
        if (result.isRight())
            return ok(result.get());
        else {
            return status(404).build();
        }
    }
}
