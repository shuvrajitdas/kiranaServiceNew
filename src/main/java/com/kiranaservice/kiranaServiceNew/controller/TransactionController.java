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
import java.util.Optional;

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

    // This API performs the task of displaying all transactions that occured for a particular kirana store.
    @GetMapping(value = "/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Transaction> getAllTransactions(@RequestParam String kiranaStoreId) {
        log.info("All Transactions request received");
        return transactionService.getAllTransactions(kiranaStoreId);
    }

    // This API performs the task of displaying transaction for a particular kiranaStore
    // by filtering them datewise, on the attribute timestamp
    @GetMapping("/transactions/daily")
    public ResponseEntity<Map<LocalDate, List<Transaction>>> getDailyTransactions(@RequestParam String kiranaStoreId) {
        log.info("Daily Transaction request received ");
        Map<LocalDate, List<Transaction>> transactions = transactionService.getTransactionsGroupedByDate(kiranaStoreId);
        return ResponseEntity.ok(transactions);
    }

    //This api to see a particular transaction by transactionId
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String transactionId) {
        try {
            Optional<Transaction> transaction = transactionRepository.findById(transactionId);
            if (transaction.isPresent()) {
                return ResponseEntity.ok(transaction.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error occurred while getting transaction by id", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
