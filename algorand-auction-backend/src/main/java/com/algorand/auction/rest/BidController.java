package com.algorand.auction.rest;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.rest.request.PlaceBidRequest;
import com.algorand.auction.usecase.BidAmountForItemUseCase;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;

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
        Either<FailureError, Void> result = useCase.bid(request.getAmount(), request.getAuctionId(), request.getToken());
        if (result.isRight())
            return created(null).build();
        return notFound().build();
    }

}

