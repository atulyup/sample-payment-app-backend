package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RequestAddMoney;
import com.example.demo.model.RequestLogin;
import com.example.demo.model.RequestPayBills;
import com.example.demo.model.RequestTransfers;
import com.example.demo.model.ResponseLogin;
import com.example.demo.model.TransactionModel;
import com.example.demo.service.DemoService;
import com.example.demo.service.DemoTransactionService;

@RestController
@CrossOrigin(origins="*")
public class DemoController {
		
	@Autowired
	private DemoService demoService;
	
	@Autowired
	private DemoTransactionService transServ;
	
	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody RequestLogin Login) {
		return demoService.addUser(Login);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseLogin> getUser(@RequestBody RequestLogin parameters) {
		return demoService.getUser(parameters);
	}
	
	
	@PostMapping("/addMoney")
	public ResponseEntity<Boolean> addMoney(@RequestBody RequestAddMoney parameters) {
		return demoService.addMoney(parameters);
	}
	
	@GetMapping(value="/getBalance/{userName}")
	public ResponseEntity<String> getBalance(@PathVariable("userName") String userName){
		return demoService.getBalance(userName);
	}
	
	@PostMapping("/transfers")
	public ResponseEntity<Boolean> transferMoney(@RequestBody RequestTransfers transfers){
		return demoService.transferMoney(transfers);
	}
	
	
	@PostMapping("/payBill")
	public ResponseEntity<Boolean> payBill(@RequestBody RequestPayBills bill){
		return demoService.payBills(bill);
	}
	
	@GetMapping(value="/getTransactions/{userName}")
	public ResponseEntity<List<TransactionModel>> getTransactions(@PathVariable("userName") String userName){
		return demoService.getTransactions(userName);
	}
	
	@DeleteMapping(value="/removeTransaction/{id}")
	public void removeTransaction(@PathVariable("id") int id) {
		transServ.deleteId(id);
	}
}
