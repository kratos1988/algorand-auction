package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.v2.client.common.IndexerClient;
import com.algorand.auction.blockchain.error.RetrieveHistoryError;
import com.algorand.auction.blockchain.response.TransactionListResponse;
import com.algorand.auction.blockchain.response.TransactionResponse;
import com.algorand.auction.blockchain.wrapper.AlgodClientHeaders;
import com.algorand.auction.model.FailureError;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;

import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class BlockchainTransactionHistoryRepository {

    private final String host;
    private final int port;
    private String[] ALGOD_API_KEY_HEADERS;
    private String[] ALGOD_API_KEY_VALUES;

    public BlockchainTransactionHistoryRepository(
            String host,
            int port,
            AlgodClientHeaders algodClientHeaders) {
        this.host = host;
        this.port = port;
        this.ALGOD_API_KEY_HEADERS = algodClientHeaders.headers;
        this.ALGOD_API_KEY_VALUES = algodClientHeaders.values;
    }

    public Either<FailureError,List<TransactionResponse>> retrieveTransactionListFor(Address address) {

        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            IndexerClient indexerClient = new IndexerClient(host, port);
            String response =
                    indexerClient.searchForTransactions()
                            .address(address)
                            .execute(ALGOD_API_KEY_HEADERS, ALGOD_API_KEY_VALUES)
                            .toString();
            TransactionListResponse transactionListDto = objectMapper.readValue(response, TransactionListResponse.class);
            return right(transactionListDto.getTransactions());
        } catch (Exception exception) {
            exception.printStackTrace();
            return left(new RetrieveHistoryError(exception));
        }

    }
}
