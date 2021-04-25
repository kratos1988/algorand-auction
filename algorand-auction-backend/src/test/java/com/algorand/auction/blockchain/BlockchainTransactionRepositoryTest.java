package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.wrapper.AlgorandBalanceChecker;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BlockchainTransactionRepositoryTest {

    private AlgorandBalanceChecker balanceChecker = mock(AlgorandBalanceChecker.class);
    private TransactionSender transactionSender = mock(TransactionSender.class);
    private String senderPublicKey = "NVP2A7JWH67X7FIK42SVM4SKINMYNRQIWXWLHQNMAWLCF2PZ64OQDYSLJE";
    private String receiverPublicKey = "SS6YTHETK4BT3NXIXMZZJQ67T7UY45HZTAJ6T5BTLZ3H7H3MRY4MCXYULM";

    @Test
    void saveTransaction() throws Exception {
        Address senderAddress = new Address(senderPublicKey);
        Address receiverAddress = new Address(receiverPublicKey);
        System.out.println(receiverAddress);

        when(balanceChecker.checkBalance(senderAddress)).thenReturn(1000L);
        BlockchainTransactionRepository underTest = new BlockchainTransactionRepository(
                balanceChecker,
                transactionSender);
        underTest.saveTransaction(senderPublicKey, receiverPublicKey, "do li sordi per un oggetto");
        verify(transactionSender).send(senderAddress, receiverAddress, "do li sordi per un oggetto");
    }

    @Test
    void notSaveTransactionWhenBalanceIsNotEnough() throws Exception {
        Address senderAddress = new Address(senderPublicKey);
        Address receiverAddress = new Address(receiverPublicKey);

        when(balanceChecker.checkBalance(senderAddress)).thenReturn(0L);
        BlockchainTransactionRepository underTest = new BlockchainTransactionRepository(
                balanceChecker,
                transactionSender);
        underTest.saveTransaction(senderPublicKey, receiverPublicKey, "do li sordi per un oggetto");
        verify(transactionSender, never()).send(senderAddress, receiverAddress, "do li sordi per un oggetto");
    }

}