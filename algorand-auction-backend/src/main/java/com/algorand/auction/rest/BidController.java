package com.algorand.auction.rest;

import com.algorand.auction.usecase.BidAmountForItemUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController("bid")
@RequestMapping(value = "/api")
public class BidController {

    private final BidAmountForItemUseCase useCase;

    @Autowired
    public BidController(BidAmountForItemUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/bid/place")
    public ResponseEntity placeBid(
            @RequestBody PlaceBidRequest request
    ) {
        useCase.bid(request.getAmount(), request.getAuctionId(), request.getUserName());
        return ResponseEntity.ok().build();
    }

}

class PlaceBidRequest {
    private String userName;
    private BigDecimal amount;
    private int auctionId;

    public PlaceBidRequest() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}