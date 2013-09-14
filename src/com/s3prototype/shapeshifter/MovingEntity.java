package com.s3prototype.shapeshifter;

public class MovingEntity extends Entity{
	
	public static class Direction{
		public static final int
			Left = 0, Right = 1,
			Up   = 2, Down  = 3;
	}//Direction class
	
	double xVel, yVel;
	private int maxSpeed = 15;
	double angle;
	boolean isMoving = false;
	
	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public MovingEntity(){
		
	}

	public MovingEntity(double x, double y, final int maxSpeed){
		super(x, y);
		this.maxSpeed = maxSpeed;
	}
	
	MovingEntity(double x, double y){
		super(x, y);
		maxSpeed = 10;
	}
	
	MovingEntity(double x, double y, double width, double height){
		super(x, y, width, height);
	}
	
	MovingEntity(double x, double y, double width, double height, boolean live){
		super(x, y, width, height, live);
	}
	
}//MovingEntity
