package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.wrapper.AlgodClientHeaders;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

class BlockchainTransactionHistoryRepositoryTest {

    @Test
    void name() throws NoSuchAlgorithmException, JsonProcessingException {

        String host = "https://testnet-algorand.api.purestake.io/idx2";
        int port = 443;
        AlgodClientHeaders headers = new AlgodClientHeaders(new String[]{"X-API-Key"}, new String[]{"3Zmly1TxTN8Of7CPwqUCf5G1KHvaemwo7OMPNCN4"});
        BlockchainTransactionHistoryRepository underTest = new BlockchainTransactionHistoryRepository(host, port, headers);
        underTest.retrieveTransactionListFor(new Address("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U"));
    }

}