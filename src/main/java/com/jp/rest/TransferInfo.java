package com.jp.rest;

public class TransferInfo {
	int senderAccountId;
	int receiverAccountId;
	String transferReason;
	double amount;
	public int getSenderAccountId() {
		return senderAccountId;
	}
	public void setSenderAccountId(int senderAccountId) {
		this.senderAccountId = senderAccountId;
	}
	public int getReceiverAccountId() {
		return receiverAccountId;
	}
	public void setReceiverAccountId(int receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}
	public String getTransferReason() {
		return transferReason;
	}
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
		
}
