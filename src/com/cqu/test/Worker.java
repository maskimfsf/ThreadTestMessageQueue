package com.cqu.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Worker extends ThreadEx{
	
	public static final int WORKER_COUNT=10;
	public static final int QUEUE_CAPACITY=50;
	
	private int id;
	private String name;
	
	private Mailer mailer;
	
	private BlockingQueue<Message> msgQueue;
	
	public Worker(int id, String name) {
		// TODO Auto-generated constructor stub
		super();
		
		this.id=id;
		this.name=name;
		
		msgQueue=new ArrayBlockingQueue<Message>(QUEUE_CAPACITY, true);
	}
	
	public String getName()
	{
		return this.name;
	}

	@Override
	protected void runProcess() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
		}
		
		while(isRunning==true)
		{
			Message msgToSend=new Message(this.id, this.randomInteger(Worker.WORKER_COUNT), this.name);
			sendMessage(msgToSend);
			//System.out.println("Message Sent In "+this.name+": "+this.mailer.messageContent(msgToSend));
			
			Message msgGot = null;
			try {
				msgGot = msgQueue.take();
				if(msgGot!=null)
				{
					disposeMessage(msgGot);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}
		}
		
		System.out.println(this.name+" stopped!");
	}
	
	private int randomInteger(int maxValue)
	{
		return (int) (Math.random()*maxValue);
	}
	
	private void disposeMessage(Message msg)
	{
		System.out.println("Message Got In "+this.name+": "+ this.mailer.messageContent(msg));
	}
	
	public void addMessage(Message msg)
	{
		try {
			this.msgQueue.put(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
		}
	}
	
	public void setMailer(Mailer mailer)
	{
		this.mailer=mailer;
	}
	
	private void sendMessage(Message msg)
	{
		this.mailer.addMessage(msg);
	}
}
