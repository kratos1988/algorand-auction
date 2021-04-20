package com.algorand.auction.blockchain.wrapper;

public class AlgodClientHeaders {
    public final String[] txHeaders;
    public final String[] txValues;

    public AlgodClientHeaders(String[] txHeaders, String[] txValues) {
        this.txHeaders = txHeaders;
        this.txValues = txValues;
    }
}
