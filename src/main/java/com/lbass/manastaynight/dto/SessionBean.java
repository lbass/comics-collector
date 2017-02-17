package com.lbass.manastaynight.dto;

public class SessionBean {
	private int seq;
	private String collecDate;
	private String LastChpaterURI;
	private boolean success;
	
	public String getCollecDate() {
		return collecDate;
	}
	public void setCollecDate(String collecDate) {
		this.collecDate = collecDate;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getLastChpaterURI() {
		return LastChpaterURI;
	}
	public void setLastChpaterURI(String lastChpaterURI) {
		LastChpaterURI = lastChpaterURI;
	}
	@Override
	public String toString() {
		return "SessionBean [seq=" + seq + ", collecDate=" + collecDate + ", success=" + success + "]";
	}
	
	
}
