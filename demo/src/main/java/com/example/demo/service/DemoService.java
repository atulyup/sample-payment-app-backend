package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.RequestAddMoney;
import com.example.demo.model.RequestLogin;
import com.example.demo.model.RequestPayBills;
import com.example.demo.model.RequestRegistration;
import com.example.demo.model.RequestTransfers;
import com.example.demo.model.ResponseLogin;
import com.example.demo.model.TransactionModel;
import com.example.demo.repository.DemoAuthRepository;

@Service
public class DemoService {

	
	@Autowired
	private DemoAuthRepository demoServ;
	
	@Autowired
	private DemoTransactionService transServ;
		
	
	public ResponseEntity<String> addUser(RequestLogin Login){
		List<RequestRegistration> allUsers = demoServ.findAll();
		boolean checkIfExists = false;
		RequestRegistration reqReg= new RequestRegistration();
	    for (int index = 0; index < allUsers.size(); index++) {
	    	RequestRegistration details= allUsers.get(index);
	    	if(details.getUsername().equals(Login.getUsername()) || details.getPassword().equals(Login.getPassword())) {
	    		checkIfExists=true;
	    	}
	    }
	    if(!checkIfExists) {
		int index= allUsers.size()+1;
		Login.setId(index);
		reqReg.setId(Login.getId());
		reqReg.setPassword(Login.getPassword());
		reqReg.setUsername(Login.getUsername());
		reqReg.setAvailableBalance(0);
		demoServ.save(reqReg);
		
		String response="Added User with id:" + Login.getId();
		return ResponseEntity.ok().body(response);
	    }else {
	    	String response="User Already Exists";
    		System.out.print(response);
	    	return ResponseEntity.badRequest().body(response);
	    }
	}
	
	
	public ResponseEntity<ResponseLogin> getUser(RequestLogin parameters) {
		boolean foundUser=false;
		ResponseLogin resp= new ResponseLogin();
		resp.setUsername(parameters.getUsername());
		resp.setFoundUser(foundUser);
		List<RequestRegistration> allUsers = demoServ.findAll();
	    for (int index = 0; index < allUsers.size(); index++) {
	    	RequestRegistration details= allUsers.get(index);
	    	if(details.getUsername().equals(parameters.getUsername()) && details.getPassword().equals(parameters.getPassword())) {
	    		foundUser=true;
	    		System.out.print("User Found");
	    		resp.setFoundUser(foundUser);
	    		return ResponseEntity.ok().body(resp);
	    	}
	    }
		return ResponseEntity.badRequest().body(resp);
	}
	
	
	public ResponseEntity<Boolean> addMoney(RequestAddMoney parameters){
		boolean addMoneySuccess= false;
		RequestRegistration reqReg= new RequestRegistration();
		reqReg.setUsername(parameters.getUsername());
		List<RequestRegistration> allUsers = demoServ.findAll();
	    for (int index = 0; index < allUsers.size(); index++) {
	    	RequestRegistration details= allUsers.get(index);
	    	if(details.getUsername().equals(parameters.getUsername())) {
	    		reqReg.setPassword(details.getPassword());
	    		reqReg.setId(details.getId());
	    		reqReg.setAvailableBalance(parameters.getAmount() + details.getAvailableBalance());
	    		demoServ.save(reqReg);
	    		transServ.addMoney(details,parameters.getAmount());
	    		addMoneySuccess=true;
	    		return ResponseEntity.ok().body(addMoneySuccess);
	    	}
	    }
		return ResponseEntity.badRequest().body(addMoneySuccess);
	}
	
	public ResponseEntity<String> getBalance(String Username){
		String doubleAsString="User Not Found";
		List<RequestRegistration> allUsers = demoServ.findAll();
	    for (int index = 0; index < allUsers.size(); index++) {
	    	RequestRegistration details= allUsers.get(index);
	    	if(details.getUsername().equals(Username)) {
	    		doubleAsString = Double.toString(details.getAvailableBalance());
	    		return ResponseEntity.ok().body(doubleAsString);
	    	}
	    }
	    return ResponseEntity.badRequest().body(doubleAsString);
	}
	
	public ResponseEntity<Boolean> transferMoney(RequestTransfers transfer){
		boolean userFound1 =false;
		boolean userFound2 =false;

		RequestRegistration reqRegUpdate1 = new RequestRegistration();
		RequestRegistration reqRegUpdate2 = new RequestRegistration();

		List<RequestRegistration> allUsers = demoServ.findAll();
	    for (int index = 0; index < allUsers.size(); index++) {
	    	RequestRegistration details= allUsers.get(index);
	    	if(details.getUsername().equals(transfer.getUserName())) {
	    		System.out.print("User Found From Transfer Money");
	    		reqRegUpdate1.setAvailableBalance(details.getAvailableBalance()- transfer.getAmount());
	    		reqRegUpdate1.setId(details.getId());
	    		reqRegUpdate1.setUsername(details.getUsername());
	    		reqRegUpdate1.setPassword(details.getPassword());
	    		userFound1=true;
	    		transServ.transfers(details, details.getUsername(), transfer.getAmount());
	    	}
	    	if(details.getUsername().equals(transfer.getUserNameTo())) {
	    		System.out.print("User Found To Transfer Money");
	    		reqRegUpdate2.setAvailableBalance(details.getAvailableBalance() + transfer.getAmount());
	    		reqRegUpdate2.setId(details.getId());
	    		reqRegUpdate2.setUsername(details.getUsername());
	    		reqRegUpdate2.setPassword(details.getPassword());
	    		userFound2=true;
	    	}
	    }
	    if(userFound1 && userFound2) {
    		demoServ.save(reqRegUpdate1);
    		demoServ.save(reqRegUpdate2);
    		transServ.transfers(reqRegUpdate1, transfer.getUserNameTo(), transfer.getAmount());
    		transServ.transfers(reqRegUpdate1, transfer.getUserNameTo(), transfer.getAmount());
    		return ResponseEntity.ok().body(true);
	    }else {
	    	return ResponseEntity.badRequest().body(false);
	    }
	}
	
	public ResponseEntity<Boolean> payBills(RequestPayBills payBill){
		boolean userFound = false;
		List<RequestRegistration> allUsers = demoServ.findAll();
		RequestRegistration reqRegUpdate1 = new RequestRegistration();

	    for (int index = 0; index < allUsers.size(); index++) {
	    	RequestRegistration details= allUsers.get(index);
	    	if(details.getUsername().equals(payBill.getUserName())) {
	    		reqRegUpdate1.setAvailableBalance(details.getAvailableBalance()- payBill.getAmount());
	    		reqRegUpdate1.setId(details.getId());
	    		reqRegUpdate1.setUsername(details.getUsername());
	    		reqRegUpdate1.setPassword(details.getPassword());
	    		userFound=true;
	    		demoServ.save(reqRegUpdate1);
	    		transServ.payBills(details, payBill.getPaymentType(), payBill.getAmount());
	    		return ResponseEntity.ok().body(userFound);
	    		}
	    	}
	    return ResponseEntity.badRequest().body(userFound);
	}
	
	public ResponseEntity<List<TransactionModel>> getTransactions(String userName){
		 return ResponseEntity.ok().body(transServ.getTransactions(userName));
	}

}
