package com.jp.rest;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int id;
    private double balance;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

	private final Lock accountLock = new ReentrantLock(); 

    public Account(int id, double balance){
         this.id = id;
         this.balance = balance;
    }
    void withdraw(double amount){
         balance -= amount;
    } 
    void deposit(double amount){
         balance += amount;
    }
    
	public boolean hasSufficientFunds(double fundAmount) {
		if(balance>=fundAmount)
			return true;
		return false;
	}

    public static void transfer(Account from, Account to, double amount) throws InSufficientFundsException, InterruptedException
    {
           while(true){
	              if(from.accountLock.tryLock()){
	                try{ 
	                    if (to.accountLock.tryLock()){
	                       try{
	                    	   if(from.hasSufficientFunds(amount)) {
		                           from.withdraw(amount);
		                           to.deposit(amount);
		                           break;
	                    	   }else {
	                    		   throw new InSufficientFundsException();
	                    	   }
	                       } 
	                       finally{
	                           to.accountLock.unlock();
	                       }
	                    }
	               }
	               finally{
	                    from.accountLock.unlock();
	               }
	            }
               Thread.sleep(100); //lock step
            }
    }
}