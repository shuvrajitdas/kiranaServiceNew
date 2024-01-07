package com.kiranaservice.kiranaServiceNew.controller;

import com.kiranaservice.kiranaServiceNew.models.Transaction;
import com.kiranaservice.kiranaServiceNew.repo.TransactionRepository;
import com.kiranaservice.kiranaServiceNew.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/kirana")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    // This API performs the task of recording a transaction in DB, by taking a transaction as Request Body.
    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        log.info("Transaction received {}", transaction);
        Transaction save = transactionService.recordTransaction(transaction);
        return ResponseEntity.ok(save);
    }

    // This API performs the task of displaying all transactions that occured.
    @GetMapping(value = "/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Transaction> getAllTransactions() {
        log.info("All Transactions request received");
        return transactionService.getAllTransactions();
    }

    // This API performs the task of displaying transaction by filtering them datewise, on the attribute timestamp
    @GetMapping("/transactions/daily")
    public ResponseEntity<Map<LocalDate, List<Transaction>>> getDailyTransactions() {
        log.info("Daily Transaction request received ");

        Map<LocalDate, List<Transaction>> transactions = transactionService.getTransactionsGroupedByDate();
        return ResponseEntity.ok(transactions);
    }
}
