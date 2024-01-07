package com.kiranaservice.kiranaServiceNew.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transaction")
@Getter
@Setter
public class Transaction {
    /** This is the schema on real life based transaction occuring in Kirana stores helping the use case to store in DB
     * transactionId is something which is unique for every transaction (front end should make it unique).
     * transactionType - CREDIT/DEBIT
     * timestamp - at which the transaction occured
     */

    @Id
    private String transactionId;
    private Double originalAmount;
    private String originalCurrency;
    private Double convertedAmountInINR;
    private String transactionType;
    private LocalDateTime timestamp;
}
