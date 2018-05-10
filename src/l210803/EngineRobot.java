package l210803;

public class EngineRobot extends Robot{

	public EngineRobot(RobotPool pool) {
		super(pool);
	}

	@Override
	protected void performService() {
		System.out.println(this + " installing Engine");
		assembler.getCar().addEngine();	
	}

}
