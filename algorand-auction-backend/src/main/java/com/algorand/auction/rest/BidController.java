package com.algorand.auction.rest;

import com.algorand.auction.usecase.BidAmountForItemUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController("bid")
public class BidController {

    private BidAmountForItemUseCase useCase;

    @Autowired
    public BidController(BidAmountForItemUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/bid/place")
    public ResponseEntity placeBid(
            @RequestBody PlaceBidRequest request
    ) {
        useCase.bid(request.getAmount(), request.getAuctionId(), request.getUserId());
        return ResponseEntity.ok().build();
    }

}

class PlaceBidRequest {
    private String userId;
    private BigDecimal amount;
    private int auctionId;

    public PlaceBidRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
}