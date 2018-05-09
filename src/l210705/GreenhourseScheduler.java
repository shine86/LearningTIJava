package l210705;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GreenhourseScheduler {
	private volatile boolean light = false;
	private volatile boolean water = false;
	private String thermostat = "Day";
	public synchronized String getThermostat() {
		return thermostat;
	}
	
	public synchronized void setThermostat(String value) {
		thermostat = value;
	}
	
	ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
	
	public void schedule(Runnable event, long delay) {
		scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
	}
	
	public void repeat(Runnable event, long initialDelay, long period) {
		scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MICROSECONDS);
	}
	
	class LightOn implements Runnable {

		@Override
		public void run() {
			System.out.println("Turning on lights");
			light = true;
		}
	}
	
	class LightOff implements Runnable {

		@Override
		public void run() {
			System.out.println("Turning off lights");
			light = false;
		}
	}
	
	class WaterOn implements Runnable {

		@Override
		public void run() {
			System.out.println("Turning greenhourse water on");
			water = true;
		}
		
	}
	
	class WaterOff implements Runnable {

		@Override
		public void run() {
			System.out.println("Turning greenhourse water off");
			water = false;
		}
		
	}
	
	class ThermostatNight implements Runnable {

		@Override
		public void run() {
			System.out.println("Thermostat to night setting");
			setThermostat("night");
		}
		
	}
	
	class ThermostatDay implements Runnable {

		@Override
		public void run() {
			System.out.println("Thermostat to day setting");
			setThermostat("day");
		}
		
	}
	
	class Bell implements Runnable {

		@Override
		public void run() {
			System.out.println("Bing!");
		}
		
	}
	
	class DataPoint {
		final Calendar time;
		final float temperatrue;
		final float humidity;
		public DataPoint(Calendar time, float temperatrue, float humidity) {
			super();
			this.time = time;
			this.temperatrue = temperatrue;
			this.humidity = humidity;
		}
		
		@Override
		public String toString() {
			return time.getTime() + String.format(" temprature: %1$.1f humidity: %2$.2f", temperatrue, humidity);
		}
	}
	
	class Terminate implements Runnable {

		@Override
		public void run() {
			System.out.println("Terminating");
			scheduler.shutdownNow();
			
			new Thread() {
				@Override
				public void run() {
					for(DataPoint d : data) {
						System.out.println(d);
					}
				}
			}.start();
		}
	}
	
	private Calendar lastTime = Calendar.getInstance();
	
	{
		lastTime.set(Calendar.MINUTE, 30);
		lastTime.set(Calendar.SECOND, 0);
	}
	
	private float lastTemp = 65.0f;
	private int tempDirection = +1;
	private float lastHumidity = 50.0f;
	private int humidityDirection = +1;
	
	private Random rand = new Random(47);
	
	List<DataPoint> data = Collections.synchronizedList(new ArrayList<>());
	
	class CollectData implements Runnable {

		@Override
		public void run() {
			System.out.println("collecting data");
			
			synchronized(GreenhourseScheduler.this) {
				lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
				if (rand.nextInt(5) == 4) {
					tempDirection = -tempDirection;
				}
				lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
				
				if (rand.nextInt(5) == 4) {
					humidityDirection = - humidityDirection;
				}
				lastHumidity = lastHumidity + humidityDirection * rand.nextFloat();
				
				data.add(new DataPoint((Calendar)lastTime.clone(), lastTemp, lastHumidity));
			}
		}
		
	}
	
	public static void main(String[] args) {
		GreenhourseScheduler gh = new GreenhourseScheduler();
		gh.schedule(gh.new Terminate(), 5000);
		gh.repeat(gh.new Bell(), 0, 1000);
		gh.repeat(gh.new ThermostatNight(), 0, 2000);
		gh.repeat(gh.new LightOn(), 0, 200);
		gh.repeat(gh.new LightOn(), 0, 400);
		gh.repeat(gh.new WaterOn(), 0, 600);
		gh.repeat(gh.new WaterOff(), 0, 800);
		gh.repeat(gh.new ThermostatDay(), 0, 1400);
		gh.repeat(gh.new CollectData(), 500, 500);
	}
	
	
}
