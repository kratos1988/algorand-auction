package com.algorand.auction.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class GetAuctionsController {

    @GetMapping("/auctions")
    public ResponseEntity<List<AuctionDto>> getAllAuctions() {
        return ok(emptyList());
    }
}
