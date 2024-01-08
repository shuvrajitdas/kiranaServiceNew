package com.kiranaservice.kiranaServiceNew.service;

import com.kiranaservice.kiranaServiceNew.models.Transaction;
import com.kiranaservice.kiranaServiceNew.repo.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CurrencyConversionService currencyConversionService;

    /**
     * This method takes the responsibility of recording transaction in DB.
     * If the originalCurrency is not INR, it creates another convertedAmountInINR for the use of store manager
     * Then saves it
     */
    @Transactional
    public Transaction recordTransaction(Transaction transaction) {
        try {
            log.info("Transaction received {}", transaction);
            if (!transaction.getOriginalCurrency().equals("INR")) {
                log.info("Converting currency to INR");
                double amountInInr = currencyConversionService.convertToInr(transaction.getOriginalAmount(), transaction.getOriginalCurrency());
                log.info("Converted amount in INR {}", amountInInr);
                transaction.setConvertedAmountInINR(amountInInr);
            }
            // Generate a unique transactionId
            String uniqueID = UUID.randomUUID().toString();
            transaction.setTransactionId(uniqueID);
            return this.transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Error occured while recording transaction", e);
            return null;
        }
    }

    /**
     * Returns all transactions
     */
    public List<Transaction> getAllTransactions(String kiranaStoreId) {
        try {
            List<Transaction> transactions = transactionRepository.findByKiranaStoreId(kiranaStoreId);

            transactions = transactions.stream()
                    .filter(transaction -> kiranaStoreId.equals(transaction.getKiranaStoreId()))
                    .collect(Collectors.toList());

            return transactions;
        } catch (Exception e) {
            log.error("Error occured while getting all transactions", e);
            return null;
        }
    }


    /**
     * Returns all the transactions grouping them by date
     */

    public Map<LocalDate, List<Transaction>> getTransactionsGroupedByDate(String kiranaStoreId) {
        try {
            List<Transaction> transactions = this.transactionRepository.findByKiranaStoreId(kiranaStoreId);
            return transactions.stream()
                    .collect(Collectors.groupingBy(transaction -> transaction.getTimestamp().toLocalDate()));
        } catch (Exception e) {
            log.error("Error occured while grouping transaction by date", e);
            return null;
        }
    }
}
