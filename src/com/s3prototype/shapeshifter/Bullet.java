package com.s3prototype.shapeshifter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends MovingEntity{
	int bColor = Color.BLACK;
	Paint paint;
	final double maxSpeed = 15;
	double radius;
	boolean hasBeenCorrected = false;
	
	Bullet(){
		this(Color.BLACK);
	}
	
	Bullet(int color){
		bColor = color;
		paint = new Paint();
		paint.setColor(color);
		radius = Math.sqrt( Math.pow(width, 2) + Math.pow(height, 2) );
	}
	
	Bullet(int color, double r){
		this(color);
		radius = r;
	}
	
	void setRadius(double r){
		radius = r;
	}
	
	void update(double angle, Canvas canvas){
		if(live){
			if(!hasBeenCorrected){
				double hypotenuse = maxSpeed;
				double opp = 0, adj = 0;
				
				angle *= (Math.PI / 180);//Convert to radians
				
				//cos(angle) = adj / hyp
				adj = Math.cos(angle) * hypotenuse;
				
				//sin(angle) = opp / hyp
				opp = Math.sin(angle) * hypotenuse;
				
				xVel = adj;
				yVel = opp;
				
				hasBeenCorrected = true;
			}//if(!hasBeenCorrected)
			x += xVel;
			y += yVel;
			draw(canvas);
		}//if(live)
	}//update()
	
	void checkBounds(){
		//Need screenWidth/Height for this...
	}
	
	void draw(Canvas c){
		c.drawCircle((float)x, (float)y, (float)radius, paint);
	}//draw()
	
}//Bullet
