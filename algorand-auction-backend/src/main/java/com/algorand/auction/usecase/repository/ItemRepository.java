package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import io.vavr.control.Either;

import java.util.List;

public interface ItemRepository {

    Either<FailureError, List<Item>> retrieveAll();
    Either<FailureError, Item> retrieveBy(Integer id);
    Either<FailureError,List<Item>> retrieveExpired();
    Either<FailureError,Void> setStatusFinished(List<Integer> itemIds);
}
