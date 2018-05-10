package l210803;

public class DriveTrainRobot extends Robot{

	public DriveTrainRobot(RobotPool pool) {
		super(pool);
	}

	@Override
	protected void performService() {
		System.out.println(this + " installing DriveTrain");
		assembler.getCar().addDriveTrain();
	}

}
