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
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CurrencyConversionService currencyConversionService;

    @Transactional
    public Transaction recordTransaction(Transaction transaction) {
        log.info("Transaction received {}", transaction);
        if (!transaction.getOriginalCurrency().equals("INR")) {
            log.info("Converting currency to INR");
            double amountInInr = currencyConversionService.convertToInr(transaction.getOriginalAmount(), transaction.getOriginalCurrency());
            log.info("Converted amount in INR {}", amountInInr);
            transaction.setConvertedAmountInINR(amountInInr);
        }
        return this.transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }

    public Map<LocalDate, List<Transaction>> getTransactionsGroupedByDate() {
        List<Transaction> transactions = this.transactionRepository.findAll();
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTimestamp().toLocalDate()));
    }
}
