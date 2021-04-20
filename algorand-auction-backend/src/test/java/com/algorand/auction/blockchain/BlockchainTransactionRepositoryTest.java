package com.algorand.auction.blockchain;

import com.algorand.algosdk.account.Account;
import com.algorand.auction.blockchain.wrapper.AlgorandBalanceChecker;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BlockchainTransactionRepositoryTest {

    private AlgorandBalanceChecker balanceChecker = mock(AlgorandBalanceChecker.class);
    private TransactionSender transactionSender = mock(TransactionSender.class);

    @Test
    void saveTransaction() throws Exception {
        Account senderAccount = new Account();
        Account receiverAccount = new Account();
        when(balanceChecker.checkBalance(senderAccount.getAddress())).thenReturn(1000L);
        BlockchainTransactionRepository underTest = new BlockchainTransactionRepository(
                balanceChecker,
                transactionSender);
        underTest.saveTransaction(senderAccount, receiverAccount);
        verify(transactionSender).send(senderAccount, receiverAccount);
    }

    @Test
    void notSaveTransactionWhenBalanceIsNotEnough() throws Exception {
        Account senderAccount = new Account();
        Account receiverAccount = new Account();
        when(balanceChecker.checkBalance(senderAccount.getAddress())).thenReturn(0L);
        BlockchainTransactionRepository underTest = new BlockchainTransactionRepository(
                balanceChecker,
                transactionSender);
        underTest.saveTransaction(senderAccount, receiverAccount);
        verify(transactionSender, never()).send(senderAccount, receiverAccount);
    }

}