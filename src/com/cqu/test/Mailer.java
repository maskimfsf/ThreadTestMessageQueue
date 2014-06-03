package com.cqu.test;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mailer extends ThreadEx{
	
	private Map<Integer, Worker> workerMap;
	
    private BlockingQueue<Message> msgQueue;
	
	public Mailer(Map<Integer, Worker> workerList) {
		// TODO Auto-generated constructor stub
		this.workerMap=workerList;
		
		msgQueue=new ArrayBlockingQueue<Message>(100, true);
	}

	@Override
	protected void runProcess() {
		// TODO Auto-generated method stub
		while(isRunning==true)
		{
			Message msg=null;
			try {
				msg = msgQueue.take();
				if(msg!=null)
				{
					disposeMessage(msg);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}
		}
		
		System.out.println("Message mailer stopped!");
	}
	
	private void disposeMessage(Message msg)
	{
		this.workerMap.get(msg.getIdReceiver()).addMessage(msg);
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
	
	public String messageContent(Message msg)
	{
		return "from "+this.workerMap.get(msg.getIdSender()).getName()+
				" to "+this.workerMap.get(msg.getIdReceiver()).getName()+
				" value "+msg.getValue();
	}
}
