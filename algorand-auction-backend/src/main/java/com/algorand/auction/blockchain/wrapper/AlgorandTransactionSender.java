package com.algorand.auction.blockchain.wrapper;

import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.transaction.Transaction;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.model.TransactionParametersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class AlgorandTransactionSender {

    private final AlgodClientHeaders algodClientHeaders;
    private Logger logger = LoggerFactory.getLogger(AlgorandTransactionSender.class);

    private final AlgodClient algodClient;

    public AlgorandTransactionSender(
            AlgodClient algodClient,
            AlgodClientHeaders algodClientHeaders
    ) {

        this.algodClientHeaders = algodClientHeaders;
        this.algodClient = algodClient;
    }

    public String send(
            Address senderPublicKey,
            Address receiverPublicKey,
            BigDecimal amount,
            String notes
    ) throws Exception {
        try {
            TransactionParametersResponse params = algodClient.TransactionParams().execute(algodClientHeaders.headers, algodClientHeaders.values).body();
            Transaction txn = Transaction.PaymentTransactionBuilder()
                    .sender(senderPublicKey)
                    .note(notes.getBytes())
                        .amount(amount.intValue())
                    .receiver(receiverPublicKey)
                    .suggestedParams(params)
                    .build();
            return txn.txID();
        } catch (Exception e) {
            logger.error("Error when sending transaction from " + senderPublicKey + " to " + receiverPublicKey, e);
            throw e;
        }
    }
}
