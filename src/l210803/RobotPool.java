package l210803;

import java.util.HashSet;
import java.util.Set;

public class RobotPool {
	private Set<Robot> pool = new HashSet<>();

	public void hire(Class<? extends Robot> class1, Assembler assembler) throws InterruptedException {
		for (Robot robot : pool) {
			if (robot.getClass().equals(class1)) {
				pool.remove(robot);
				robot.assignAssembler(assembler);
				robot.engage();
				return;
			}
		}
		wait();
		hire(class1, assembler);
	}

	public synchronized void relase(Robot robot) {
		add(robot);
	}

	private synchronized void add(Robot robot) {
		pool.add(robot);
		notifyAll();
	}
	
	

}
