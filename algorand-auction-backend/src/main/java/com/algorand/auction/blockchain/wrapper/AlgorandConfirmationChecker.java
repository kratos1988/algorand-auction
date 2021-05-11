package com.algorand.auction.blockchain.wrapper;

import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;
import com.algorand.algosdk.v2.client.model.NodeStatusResponse;
import com.algorand.algosdk.v2.client.model.PendingTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorandConfirmationChecker {

    private Logger logger = LoggerFactory.getLogger(AlgorandTransactionSender.class);

    private final AlgodClient algodClient;
    private final String[] algodApiKeyHeaders;
    private final String[] algodApiKeyValues;

    public AlgorandConfirmationChecker(
            AlgodClient algodClient,
            AlgodClientHeaders algodClientHeaders
    ) {
        this.algodClient = algodClient;
        this.algodApiKeyHeaders = algodClientHeaders.headers;
        this.algodApiKeyValues = algodClientHeaders.values;
    }

    public PendingTransactionResponse waitForConfirmation(String txID, Integer timeout)
            throws Exception {
        if (algodClient == null || txID == null || timeout < 0) {
            throw new IllegalArgumentException("Bad arguments for waitForConfirmation.");
        }
        Response<NodeStatusResponse> resp = algodClient.GetStatus().execute(algodApiKeyHeaders, algodApiKeyValues);
        if (!resp.isSuccessful()) {
            throw new Exception(resp.message());
        }
        NodeStatusResponse nodeStatusResponse = resp.body();
        Long startRound = nodeStatusResponse.lastRound+1;
        Long currentRound = startRound;
        while (currentRound < (startRound + timeout)) {
            // Check the pending transactions
            Response<PendingTransactionResponse> resp2 = algodClient.PendingTransactionInformation(txID).execute(algodApiKeyHeaders, algodApiKeyValues);
            if (resp2.isSuccessful()) {
                PendingTransactionResponse pendingInfo = resp2.body();
                if (pendingInfo != null) {
                    if (pendingInfo.confirmedRound != null && pendingInfo.confirmedRound > 0) {
                        // Got the completed Transaction
                        return pendingInfo;
                    }
                    if (pendingInfo.poolError != null && pendingInfo.poolError.length() > 0) {
                        // If there was a pool error, then the transaction has been rejected!
                        throw new Exception("The transaction has been rejected with a pool error: " + pendingInfo.poolError);
                    }
                }
            }

            Response<NodeStatusResponse> resp3 = algodClient.WaitForBlock(currentRound).execute(algodApiKeyHeaders, algodApiKeyValues);
            if (!resp3.isSuccessful()) {
                throw new Exception(resp3.message());
            }
            currentRound++;
        }
        throw new Exception("Transaction not confirmed after " + timeout + " rounds!");
    }

}
