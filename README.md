# KiranaService

KiranaService is a Java-based application developed using Spring Boot and Maven. It is designed to handle transactions for a Kirana store.

## Features

- Record Transactions: The service allows recording of transactions with details such as original amount, original currency, converted amount in INR, transaction type, and timestamp.
- Get All Transactions: The service can retrieve all transactions recorded in the system.
- Get Transactions Grouped By Date: The service can retrieve transactions grouped by their date.

## Models

- Transaction: This model represents a transaction in the system. It includes fields like transactionId, originalAmount, originalCurrency, convertedAmountInINR, transactionType, and timestamp.

## Services

- TransactionService: This service handles the business logic related to transactions. It includes methods to record a transaction, get all transactions, and get transactions grouped by date.

## How to Run

This is a Maven project and can be built and run from the command line:
