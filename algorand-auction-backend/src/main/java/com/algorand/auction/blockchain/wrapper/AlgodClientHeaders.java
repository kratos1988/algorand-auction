package com.algorand.auction.blockchain.wrapper;

public class AlgodClientHeaders {
    public final String[] headers;
    public final String[] values;

    public AlgodClientHeaders(String[] headers, String[] values) {
        this.headers = headers;
        this.values = values;
    }
}
