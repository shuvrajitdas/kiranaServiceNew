package com.kiranaservice.kiranaServiceNew.service;

import com.kiranaservice.kiranaServiceNew.models.Transaction;
import com.kiranaservice.kiranaServiceNew.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @DisplayName("Group transactions by date")
    void shouldGroupTransactionsByDate() {
        // Create three transactions with the current timestamp
        Transaction transaction1 = new Transaction();
        transaction1.setTimestamp(LocalDateTime.now());
        Transaction transaction2 = new Transaction();
        transaction2.setTimestamp(LocalDateTime.now());
        Transaction transaction3 = new Transaction();
        transaction3.setTimestamp(LocalDateTime.now());

        // Mock the findAll method of transactionRepository to return the three transactions
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2, transaction3));

        // Call the method to test
        Map<LocalDate, List<Transaction>> transactions = transactionService.getTransactionsGroupedByDate();

        // Assert that the transactions are grouped correctly by date
        assertEquals(3, transactions.get(LocalDateTime.now().toLocalDate()).size());
    }

    @Test
    @DisplayName("Handle no transactions")
    void shouldHandleNoTransactions() {
        // Mock the findAll method of transactionRepository to return an empty list
        when(transactionRepository.findAll()).thenReturn(Arrays.asList());

        // Call the method to test
        Map<LocalDate, List<Transaction>> transactions = transactionService.getTransactionsGroupedByDate();

        // Assert that the result is an empty map
        assertEquals(0, transactions.size());
    }
}
