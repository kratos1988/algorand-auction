package com.algorand.auction.blockchain.wrapper;

import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.transaction.Transaction;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.model.TransactionParametersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.apache.commons.lang3.ArrayUtils.add;

public class AlgorandTransactionSender {

    private Logger logger = LoggerFactory.getLogger(AlgorandTransactionSender.class);

    private final AlgodClient algodClient;
    private final String[] txHeaders;
    private final String[] txValues;

    public AlgorandTransactionSender(
            AlgodClient algodClient,
            AlgodClientHeaders algodClientHeaders
    ) {

        this.txHeaders = add(algodClientHeaders.txHeaders, "Content-Type");
        this.txValues = add(algodClientHeaders.txValues, "application/x-binary");
        this.algodClient = algodClient;
    }

    public String send(
            Address senderPublicKey,
            Address receiverPublicKey,
            BigDecimal amount,
            String notes
    ) throws Exception {
        try {
            TransactionParametersResponse params = algodClient.TransactionParams().execute(txHeaders, txValues).body();
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
