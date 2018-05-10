package l210803;

public class WheelRobot extends Robot{

	public WheelRobot(RobotPool pool) {
		super(pool);
	}

	@Override
	protected void performService() {
		System.out.println(this + " installing Wheel");
		assembler.getCar().addWheel();
	}

}
