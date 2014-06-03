package com.cqu.test;

public class Message {
	
	private int idSender;
	private int idReceiver;
	private String value;
	
	public Message(int idSender, int idReceiver, String value) {
		super();
		this.idSender = idSender;
		this.idReceiver = idReceiver;
		this.value = value;
	}
	
	public int getIdSender() {
		return idSender;
	}
	
	public int getIdReceiver() {
		return idReceiver;
	}
	
	public String getValue() {
		return value;
	}
	
}
