package com.jp.rest;

import java.util.ArrayList;
import java.util.List;

public class BankRepository {
	private List<Account> accounts = null;
	
	private static BankRepository _instance = null;
    private BankRepository() {
    	accounts = new ArrayList<Account>();
    }
    
    public static BankRepository getInstance(boolean initialize) {
    	if(initialize==false)
    		return new BankRepository();
    	
    	if(_instance==null) {
    		_instance = new BankRepository();
   	    	_instance.initData();
    	}
    	return _instance;
    }

    private void initData() {
    	accounts.add(new Account(1,10000.00));
    	accounts.add(new Account(2,12000.00));
    	accounts.add(new Account(3,23000.00));
    	accounts.add(new Account(4,15000.00));
    	accounts.add(new Account(5,13000.00));
    	accounts.add(new Account(6,12000.00));
    	accounts.add(new Account(7,19000.00));
    	accounts.add(new Account(8,16000.00));
    }

    public Account findById(int id) {
    	Account a = accounts.stream()
    			  .filter(account -> account.getId()==id)
    			  .findAny()
    			  .orElse(null);
    	return a;
    }
    
    public boolean add(Account a) {
		if(findById(a.getId())==null) {
    		accounts.add(a);
    		return true;
    	}
    	return false;
    }
    
    // ideally this would have been close; writing this to enable test cases
    public void removeAll() {
    	accounts.clear();
    }
    
}
