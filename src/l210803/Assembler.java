package l210803;

import java.util.concurrent.CyclicBarrier;

public class Assembler implements Runnable{
	private CarQueue chassisQueue, finishingQueue;
	private Car car;
	private CyclicBarrier barrier = new CyclicBarrier(4);
	private RobotPool robotPool;
	
	
	public Assembler(CarQueue chassisQueue, CarQueue finishingQueue, RobotPool robotPool) {
		this.chassisQueue = chassisQueue;
		this.finishingQueue = finishingQueue;
		this.robotPool = robotPool;
	}

	public Car getCar() {
		return car;
	}

	public CyclicBarrier getBarrier() {
		return barrier;
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				car = chassisQueue.take();
				
				robotPool.hire(EngineRobot.class, this);
				robotPool.hire(DriveTrainRobot.class, this);
				robotPool.hire(WheelRobot.class, this);
				barrier.await();
				
				finishingQueue.put(car);
			}
		} catch (Exception e) {
			System.out.println("Exiting Assembler via interrupt");
		}
		System.out.println("Assembler off");
	}

}
