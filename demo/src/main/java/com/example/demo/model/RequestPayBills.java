package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class RequestPayBills {
	
	private String paymentType;
	private String userName;
	private double amount;

}
