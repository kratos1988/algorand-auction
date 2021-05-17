package com.algorand.auction.blockchain;

import com.algorand.algosdk.account.Account;
import com.algorand.auction.blockchain.wrapper.AlgorandConfirmationChecker;
import com.algorand.auction.blockchain.wrapper.AlgorandTransactionSender;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.*;

class TransactionSenderTest {

    private AlgorandTransactionSender transactionSender = mock(AlgorandTransactionSender.class);
    private AlgorandConfirmationChecker confirmationChecker = mock(AlgorandConfirmationChecker.class);

    @Test
    void send() throws Exception {
        Account senderAccount = new Account();
        Account receiverAccount = new Account();
        BigDecimal amount = TEN;

        String notes = "Some notes";
        when(transactionSender.send(senderAccount.getAddress(), receiverAccount.getAddress(), amount, notes)).thenReturn("A_TX_ID");

        TransactionSender underTest = new TransactionSender(transactionSender, confirmationChecker);

        underTest.send(senderAccount.getAddress(), receiverAccount.getAddress(), amount, notes);

        verify(transactionSender).send(senderAccount.getAddress(), receiverAccount.getAddress(), amount, notes);
        verify(confirmationChecker).waitForConfirmation("A_TX_ID", 10);
    }
}