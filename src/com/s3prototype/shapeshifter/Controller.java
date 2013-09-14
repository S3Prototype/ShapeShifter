package com.s3prototype.shapeshifter;

public class Controller {
	
	static boolean buttonPressed;
	
	static class Axis{
		static final int
			x = 0, y = 1, z = 2;
		
		static final int NUM_AXES = 3;
	}//Axes class
	
	static int player = 0;
	
	static class Stick{
		static final int
		left = 0, right = 2;
		
		static final int NUM_STICKS = 2;
	}
	
	static double joystick[][] = new double[Stick.NUM_STICKS][Axis.NUM_AXES];
	
	static boolean motionEvent = false;
}//Controller
