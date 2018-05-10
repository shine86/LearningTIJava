package l210801;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TellerManager implements Runnable{
	private ExecutorService exec;
	private CustomerLine customers;
	private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
	private Queue<Teller> tellerDoingOtherThings = new LinkedList<>();
	private int adjustmentPeriod;
	private static Random rand = new Random();
	
	public TellerManager(ExecutorService exec, CustomerLine customers, int adjustmentPeriod) {
		super();
		this.exec = exec;
		this.customers = customers;
		this.adjustmentPeriod = adjustmentPeriod;
		
		Teller teller = new Teller(customers);
		exec.execute(teller);
		workingTellers.add(teller);
	}
	
	public void adjustTellerNumber() {
		if (customers.size() / workingTellers.size() > 2) {
			if (tellerDoingOtherThings.size() > 0) {
				Teller teller = tellerDoingOtherThings.remove();
				teller.serveCustomerLine();
				workingTellers.offer(teller);
				return;
			}
			
			Teller teller = new Teller(customers);
			exec.execute(teller);
			workingTellers.add(teller);
			return;
		}
		
		if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2) {
			reassignOneTeller();
		}
		
		if (customers.size() == 0) {
			while(workingTellers.size() > 0) {
				reassignOneTeller();
			}
		}
	}
	
	private void reassignOneTeller() {
		Teller teller = workingTellers.poll();
		teller.doSomethingElse();
		tellerDoingOtherThings.offer(teller);
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
				adjustTellerNumber();
				System.out.print(customers + " ( ");
				for (Teller teller : workingTellers) {
					System.out.print(teller.shortString() + " ");
				}
				System.out.println(")");
			}
		} catch(Exception e) {
			System.out.println(this + "interrupted");
		}
		
		System.out.println(this + "terminating");
	}
	
	@Override
	public String toString() {
		return "TellerMananger ";
	}
}
