package com.example.demo.model;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString

public class RequestAddMoney {
	private String username;
	private double amount;
}
