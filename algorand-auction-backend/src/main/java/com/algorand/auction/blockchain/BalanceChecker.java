package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;

public interface BalanceChecker {
    Long checkBalance(Address address) throws Exception;
}
