package com.s3prototype.shapeshifter;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ship extends MovingEntity{

	//A ship needs a color, a bullet array, ammo, and health
	int sColor = Color.BLACK; 
	Paint paint;
	final int maxHealth = 3;
	int health = maxHealth;
	final int maxAmmo = 3;
	int ammo = maxAmmo;
	ArrayList<Bullet> bullets;
	
	Ship(int color){
		bullets = new ArrayList<Bullet>();
		sColor = color;
		paint = new Paint();
		paint.setColor(sColor);
	}
	 
	void move(double xAxis, double yAxis){
		if(xAxis > 0){
			if(xVel < getMaxSpeed())
				xVel++;
		} else if(xAxis < 0){
			if(xVel > 0 - getMaxSpeed())
				xVel--;
		}
		
		if(yAxis > 0){
			if(yVel < getMaxSpeed())
				yVel++;
		} else if(yAxis < 0){
			if(yVel > 0 - getMaxSpeed())
				yVel--;
		} 
	}//move()
	
	void turn(double xAxis){
		if(xAxis > 0){
			angle += 10;
		} else if(xAxis < 0){
			angle -= 10;
		} else {
			
		}
	}//turn()
	
	void stop(){
		if(xVel > 0){
			xVel--;
		} else if(xVel < 0){
			xVel++;
		}

		if(yVel > 0){
			yVel--;
		} else if(yVel < 0){
			yVel++;
		}
		
		if(xVel > -.80 && xVel < .80){
			xVel = 0;
		}//if()
		
		if(yVel > -.95 && yVel < .95){
			yVel = 0;
		}//if()
	}//stop()
	
	void draw(Canvas c){
		float x = (float) this.x;
		float y = (float) this.y;
		float width = (float) this.width;
		float height = (float) this.height;
		c.drawRect(x - width/2, y - height/2, x + width/2, y + height/2, paint);
	}//draw()
	
	void update(Canvas c){
		x += xVel;
		y += yVel;
		updateBullets(c);
		draw(c);
	}
	
	void updateBullets(Canvas c){
		for(Bullet bullet : bullets){
			bullet.update(angle, c);
		}
	}
	
	void shoot(){
		for (Bullet bullet : bullets) {
			if (!bullet.live) {
				bullet.live = true;
				bullet.hasBeenCorrected = false;
				--ammo;
				break;// Break the for-loop
			}// if(!bullet.live)
		}// for()
	}//shoot()
	
}//Ship
