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
    @Id
    private String transactionId;
    private Double originalAmount;
    private String originalCurrency;
    private Double convertedAmountInINR;
    private String transactionType;
    private LocalDateTime timestamp;
}
