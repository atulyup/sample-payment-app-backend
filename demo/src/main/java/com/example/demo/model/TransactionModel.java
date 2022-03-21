package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection="transactions")
public class TransactionModel {
	@Id
	private int id;
	private String userName;
	private String paymentType;
	private int transactionNumber;
	private String status;
	private double amount;
	private double availableBalance; 
}
