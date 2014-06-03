package com.cqu.test;

import java.util.HashMap;
import java.util.Map;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, Worker> workers=new HashMap<Integer, Worker>();
		
		for(int i=0;i<Worker.WORKER_COUNT;i++)
		{
			workers.put(i, new Worker(i, "Worker"+i));
		}
		
		Mailer mailer=new Mailer(workers);
		
		for(int i=0;i<Worker.WORKER_COUNT;i++)
		{
			Worker worker=workers.get(i);
			worker.setMailer(mailer);
			worker.start();
		}
		
		mailer.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		for(Worker worker : workers.values())
		{
			worker.stopRunning();
		}
		mailer.stopRunning();
	}
}
