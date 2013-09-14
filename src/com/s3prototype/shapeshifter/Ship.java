package com.s3prototype.shapeshifter;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
	Bitmap bitmap;
	
	Ship(int color){
		bullets = new ArrayList<Bullet>();
		for(int i = 0; i < maxAmmo; i++){
			bullets.add(new Bullet());
		}
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
		
		isMoving = true;
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
		if(xVel > 1){
			xVel--;
		} else if(xVel < 1){
			xVel++;
		}

		if(yVel > 1){
			yVel--;
		} else if(yVel < 1){
			yVel++;
		}
		
		if(xVel >= -1 && xVel <= 1){
			xVel = 0;
		}//if()
		
		if(yVel >= -1 && yVel <=1){
			yVel = 0;
		}//if()
		
		if(xVel + yVel == 0){
			isMoving = false;
		}
	}//stop()
	
	void draw(Canvas c){
		float x = (float) this.x;
		float y = (float) this.y;
		float width = (float) this.width;
		float height = (float) this.height;
		

	      Matrix matrix = new Matrix();
	      matrix.postRotate((float) angle);
	      int bWidth = bitmap.getWidth();
	      int bHeight = bitmap.getHeight();
	     Bitmap drawBMP = Bitmap.createBitmap(bitmap, (int)x, (int)y, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	     c.drawBitmap(bitmap, matrix, null);
		/*c.drawRect(x - width/2, y - height/2, x + width/2, y + height/2, paint);*/
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
				bullet.x = x;
				bullet.y = y;
				break;// Break the for-loop
			}// if(!bullet.live)
		}// for()
	}//shoot()
	
}//Ship
