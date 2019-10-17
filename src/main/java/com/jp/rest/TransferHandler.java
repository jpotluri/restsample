package com.jp.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/transfer")
public class TransferHandler {

	/*
	 * Expected JSON message format
	 * transfer:{
	 * 	"senderAccountId":
	 *  "receiverAccountId":
	 *  "amount":
	 * }
	 * 
	 */
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String transfer(@Context Request request, String json){
        ObjectMapper mapper = new ObjectMapper();

    	//JSON file to Java object
        TransferInfo transferObj;
		try {
			transferObj = mapper.readValue(json, TransferInfo.class);
		} catch (JsonParseException e) {
            return "Transfer failed - Bad Request ";
		} catch (JsonMappingException e) {
            return "Transfer failed - Bad Request ";
		} catch (IOException e) {
            return "Transfer failed - Bad Request ";
		}

		BankRepository repo = BankRepository.getInstance(true);
        Account senderAccount = repo.findById(transferObj.getSenderAccountId());
        Account receiverAccount = repo.findById(transferObj.getReceiverAccountId());
        
        if(senderAccount==null || receiverAccount==null) {
            return "Transfer failed - Bad Request ";
        }
        
        if(senderAccount.getId()==receiverAccount.getId()) {
            return "No transfer required with in same accounts ";
        }
        
        try {
			Account.transfer(senderAccount, receiverAccount, transferObj.amount);
		}catch (InSufficientFundsException e) {
            return "Transfer failed due to insufficient funds ";
		} catch (InterruptedException e) {
            return "Transfer failed - Please try again later ";
		}
        
        return "Successfully transferred the requested amount ";
    }

}