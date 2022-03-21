package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RequestRegistration;
import com.example.demo.model.TransactionModel;
import com.example.demo.repository.DemoTransactionRepository;

@Service
public class DemoTransactionService {
	
	@Autowired
	private DemoTransactionRepository transactions;
	
	public void registerUser(RequestRegistration details, double amount) {
		TransactionModel model= new TransactionModel();
		List<TransactionModel> allTrans = transactions.findAll();
		int index= allTrans.size()+1;
		model.setId(index);
		model.setUserName(details.getUsername());
		model.setAmount(amount);
		model.setAvailableBalance(0);
		model.setPaymentType("Top-Up");
		model.setStatus("Success");
		model.setTransactionNumber(index+22334455);
		transactions.save(model);
	}
	
	
	public void addMoney(RequestRegistration details, double amount) {
		TransactionModel model= new TransactionModel();
		List<TransactionModel> allTrans = transactions.findAll();
		int index= allTrans.size()+1;
		model.setId(index);
		model.setUserName(details.getUsername());
		model.setAmount(amount);
		model.setAvailableBalance(details.getAvailableBalance());
		model.setPaymentType("Top-Up");
		model.setStatus("Success");
		model.setTransactionNumber(index+22334455);
		transactions.save(model);
	}
	
	public void transfers(RequestRegistration details,String userName ,double amount) {
		TransactionModel model= new TransactionModel();
		List<TransactionModel> allTrans = transactions.findAll();
		int index= allTrans.size()+1;
		model.setId(index);
		model.setUserName(userName);
		model.setAmount(amount);
		model.setAvailableBalance(details.getAvailableBalance());
		model.setPaymentType("Transfer");
		model.setStatus("Success");
		model.setTransactionNumber(index+22334455);
		transactions.save(model);
	}

	public void payBills(RequestRegistration details,String paymentType ,double amount) {
		TransactionModel model= new TransactionModel();
		List<TransactionModel> allTrans = transactions.findAll();
		int index= allTrans.size()+1;
		model.setId(index);
		model.setUserName(details.getUsername());
		model.setAmount(amount);
		model.setAvailableBalance(details.getAvailableBalance());
		model.setPaymentType(paymentType);
		model.setStatus("Success");
		model.setTransactionNumber(index+22334455);
		transactions.save(model);
	}
	
	public List<TransactionModel> getTransactions(String userName){
		List<TransactionModel> result = new ArrayList<>();
		List<TransactionModel> allTrans = transactions.findAll();
	    for (int index = 0; index < allTrans.size(); index++) {
	    	TransactionModel details = allTrans.get(index);
	    	if(details.getUserName().equals(userName)) {
	    		result.add(details);
	    	}
	    }
	    return result;
	}
	
	public void deleteId(int id) {
		transactions.deleteById(id);
	}

}
