package com.jp.rest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransferTest {	
	BankRepository repo = BankRepository.getInstance(false);
 
    @Before
    public void beforeEach() {
        repo.add(new Account(1,1000.0));
        repo.add(new Account(2,2000.0));
        repo.add(new Account(3,200.0));
        repo.add(new Account(4,800.0));

    }
 
    @After
    public void afterEach() {
        repo.removeAll();
    }
 
    
    @Test
    public void transfer_validAccounts_sufficientFunds() {
        Account a = repo.findById(1);
        Account b = repo.findById(2);
        
        try {
			Account.transfer(a, b, 200.0);
		} catch (InSufficientFundsException e) {
			Assert.fail();
		} catch (InterruptedException e) {
			Assert.fail();
		}
        
        Assert.assertTrue("Valid balance after transfer",(repo.findById(1).getBalance()==800.0));
    }


    @Test
    public void transfer_validAccounts_insufficientFunds() {
        Account a = repo.findById(3);
        Account b = repo.findById(2);
        
        try {
			Account.transfer(a, b, 400.0);
		} catch (InSufficientFundsException e) {
			Assert.assertTrue("Valid exception thrown for insufficient funds ", true);
		} catch (InterruptedException e) {
			Assert.fail();
		}
    }
    
    @Test
    public void transfer_withThreads() {
    	BankRepository trepo = BankRepository.getInstance(false);
        trepo.add(new Account(1,1000.0));
        trepo.add(new Account(2,2000.0));
        
        
        Account a = trepo.findById(1);
        Account b = trepo.findById(2);
       double initialABal = a.getBalance();
        double initialBBal = b.getBalance();
        
        Thread t1 = new Thread(){
              public void run(){
                  try {
                      Account a = trepo.findById(1);
                      Account b = trepo.findById(2);
          			Account.transfer(a, b, 200.0);
        
                } catch (InSufficientFundsException e) {
          			Assert.fail();
          		} catch (InterruptedException e) {
          			Assert.fail();
          		}
              }
        };
        Thread t2 = new Thread(){
              public void run(){
                  try {
                      Account a = trepo.findById(1);
                      Account b = trepo.findById(2);
          			Account.transfer(b, a, 300.0);
                  } catch (InSufficientFundsException e) {
          			Assert.fail();
          		} catch (InterruptedException e) {
          			Assert.fail();
          		}
              }
        };
        t1.start();
        t2.start();
        
        while(t1.isAlive() || t2.isAlive()) {
        	try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        Assert.assertTrue("Correct balance on account with id 1 ", a.getBalance()==(initialABal+100.0));
		Assert.assertTrue("Correct balance on account with id 2 ", b.getBalance()==(initialBBal-100.0));
        
    }
   
}
