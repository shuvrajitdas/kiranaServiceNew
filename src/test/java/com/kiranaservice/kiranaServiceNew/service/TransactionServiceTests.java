package com.kiranaservice.kiranaServiceNew.service;

import com.kiranaservice.kiranaServiceNew.models.Transaction;
import com.kiranaservice.kiranaServiceNew.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTests {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all transactions for a specific kiranaStoreId")
    void shouldGetAllTransactionsForSpecificKiranaStoreId() {
        // Define a test kiranaStoreId
        String kiranaStoreId = "testStoreId";

        // Create some test transactions
        Transaction transaction1 = new Transaction();
        transaction1.setKiranaStoreId(kiranaStoreId);
        Transaction transaction2 = new Transaction();
        transaction2.setKiranaStoreId(kiranaStoreId);
        Transaction transaction3 = new Transaction();
        transaction3.setKiranaStoreId("otherStoreId");

        // Mock the findAll method of the transactionRepository to return the test transactions
        Mockito.when(transactionRepository.findByKiranaStoreId(Mockito.any())).thenReturn(Arrays.asList(transaction1, transaction2, transaction3));

        // Call the method under test
        List<Transaction> transactions = transactionService.getAllTransactions(kiranaStoreId);

        // Assert that the method returns the correct number of transactions for the test kiranaStoreId
        assertEquals(2, transactions.size());
    }

    @Test
    @DisplayName("Group transactions by date for a specific kiranaStoreId")
    void shouldGroupTransactionsByDateForSpecificKiranaStoreId() {
        // Define a test kiranaStoreId
        String kiranaStoreId = "testStoreId";

        // Create some test transactions
        Transaction transaction1 = new Transaction();
        transaction1.setTimestamp(LocalDateTime.now());
        transaction1.setKiranaStoreId(kiranaStoreId);
        Transaction transaction2 = new Transaction();
        transaction2.setTimestamp(LocalDateTime.now());
        transaction2.setKiranaStoreId(kiranaStoreId);
        Transaction transaction3 = new Transaction();
        transaction3.setTimestamp(LocalDateTime.now());
        transaction3.setKiranaStoreId("otherStoreId");

        // Mock the findAll method of the transactionRepository to return the test transactions
        when(transactionRepository.findByKiranaStoreId(Mockito.any())).thenReturn(Arrays.asList(transaction1, transaction2, transaction3));

        // Call the method under test
        Map<LocalDate, List<Transaction>> transactions = transactionService.getTransactionsGroupedByDate(kiranaStoreId);

        // Assert that the method returns the correct number of transactions for the test kiranaStoreId grouped by date
        assertEquals(3, transactions.get(LocalDateTime.now().toLocalDate()).size());}

    @Test
    @DisplayName("Handle no transactions")
    void shouldHandleNoTransactions() {
        // Mock the findAll method of transactionRepository to return an empty list
        when(transactionRepository.findAll()).thenReturn(Arrays.asList());

        // Call the method to test
        Map<LocalDate, List<Transaction>> transactions = transactionService.getTransactionsGroupedByDate("5");

        // Assert that the result is an empty map
        assertEquals(0, transactions.size());
    }
}
