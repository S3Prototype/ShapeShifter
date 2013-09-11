package com.s3prototype.shapeshifter;

public class Entity {
	double x;
	double y;
	boolean live = false;
	double width, height;
	
	Entity(){
		x = y = 100;
		width = height = 90;
	}
	
	Entity(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	Entity(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	Entity(double x, double y, double width, double height, boolean live){
		this(x, y, width, height);
		this.live = live;
	}
	
}//Entity
