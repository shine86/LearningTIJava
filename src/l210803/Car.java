package l210803;

public class Car {
	private final int id;
	private boolean engine = false, driveTrain = false, wheels = false;
	public Car(int id) {
		this.id = id;
	}
	
	public synchronized int getId() {
		return id;
	}
	
	public synchronized void addEngine() {
		engine = true;
	}
	
	public synchronized void addDriveTrain() {
		driveTrain = true;
	}
	
	public synchronized void addWheel() {
		wheels = true;
	}
	
	@Override
	public String toString() {
		return "Car" + id + " [engine:" + engine + " driveTrain:" + driveTrain + " wheels:" + wheels + "]";
	}
	
	

}
