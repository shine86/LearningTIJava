package l210803;

public abstract class Robot implements Runnable{
	protected Assembler assembler;
	private RobotPool pool;
	private boolean engage = false;
	
	public Robot(RobotPool pool) {
		super();
		this.pool = pool;
	}
	
	public Robot assignAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}
	
	public synchronized void engage() {
		engage = true;
		notifyAll();
	}
	
	abstract protected void performService();
	
	private synchronized void powerDown() throws InterruptedException {
		engage = false;
		assembler = null;
		pool.relase(this);
		while(engage == false) {
			wait();
		}
	}

	@Override
	public void run() {
		try {
			powerDown();
			while(!Thread.interrupted()) {
				performService();
				assembler.getBarrier().await();
				powerDown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exiting " + this + " via interrupt");
		}
		System.out.println(this + " off");
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	

}
