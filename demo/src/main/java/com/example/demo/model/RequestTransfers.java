package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class RequestTransfers {

	private String userName;
	private String userNameTo;
	private double amount;
}
