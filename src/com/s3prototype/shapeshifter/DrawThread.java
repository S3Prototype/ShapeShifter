package com.s3prototype.shapeshifter;

import java.util.concurrent.locks.ReentrantLock;

import com.s3prototype.shapeshifter.Controller.Axis;
import com.s3prototype.shapeshifter.Controller.Stick;

import tv.ouya.console.api.OuyaController;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
	
	GameView drawSurface;
	SurfaceHolder surfaceHolder;
	Context surfaceContext;
	Paint paint;
	LauncherActivity surfaceActivity;
	public final String PLAYER_FILE = "playerInfo";
	public final String CPU_FILE = "cpuInfo";
	public final String PLAYER_X = "PLAYER_X";
	public final String PLAYER_Y = "Player_Y";
	public final String PLAYER_DX = "Player_DX";
	public final String PLAYER_DY = "Player_DY";
	public final String PLAYER_DREACHED = "Player_DREACHED";
	static boolean gameInitialized = false;
	boolean threadInitialized = false;
	double scaledX, scaledY;
	
	int deviceID = 0;
	
	boolean okToRun;
	
	public ReentrantLock threadLock;
	
	Ship ship[] = new Ship[1];
	
	public DrawThread(Context vContext, GameView sView, SurfaceHolder vHolder, LauncherActivity sActivity){
		surfaceContext = vContext;
		surfaceActivity = sActivity;
		drawSurface = sView;
		surfaceActivity = sActivity;
		surfaceHolder = vHolder;
		surfaceActivity.gameWasStarted = true;
		ship[0] = new Ship(Color.BLUE);
	}
	
	public void InitGame(){
		if(!threadInitialized){
			ship[0].bitmap = BitmapFactory.decodeResource(surfaceContext.getResources(), R.raw.blue_tri);
			gameInitialized = true;
			threadInitialized = true;
		}//if()
	}//InitGame()
	
	public void run() {
		InitGame();
		while (okToRun) {
			Canvas c = null;
			if (!surfaceHolder.getSurface().isValid()) {
				continue;
			}
			OuyaController.startOfFrame();
			c = surfaceHolder.lockCanvas();
			if (c != null) {
				synchronized (surfaceHolder) {
					try {
						c.drawColor(Color.GRAY);
						ship[0].update(c);
						ouyaCheck();
						//update shtuff
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				surfaceHolder.unlockCanvasAndPost(c);
			}//if c != null
		}// while()
	}
	
	public void ouyaCheck(){
		OuyaController c = OuyaController.getControllerByPlayer(Controller.player);
		double axisX = c.getAxisValue(OuyaController.AXIS_LS_X);
		double axisY = c.getAxisValue(OuyaController.AXIS_LS_Y);
		
		if(axisX * axisX + axisY * axisY < OuyaController.STICK_DEADZONE * OuyaController.STICK_DEADZONE) {
			  axisX = axisY = 0.0f;
			  ship[0].stop();
		} else {
			ship[0].move(axisX, axisY);
		}
		
		double rAxisX = c.getAxisValue(OuyaController.AXIS_RS_X);
		if(rAxisX * rAxisX > OuyaController.STICK_DEADZONE * OuyaController.STICK_DEADZONE) {
			  ship[0].turn(rAxisX);
		} 
		
		
		if(c.buttonPressedThisFrame(OuyaController.BUTTON_O)){
			ship[0].shoot();
		}
	}//ouyaCheck()

	public void onTouch(MotionEvent event){
	}//onTouch()
	
	public void setOkToRun(boolean status){
		okToRun = status;
/*		if(!okToRun){
			SharedPreferences prefs = surfaceActivity.getSharedPreferences(PLAYER_FILE, 0);
			SharedPreferences.Editor editor = prefs.edit();
			
			editor.putFloat(PLAYER_X, player.x);
			editor.putFloat(PLAYER_Y, player.y);
			editor.putFloat(PLAYER_DX, player.dstX);
			editor.putFloat(PLAYER_DY, player.dstY);
			editor.putBoolean(PLAYER_DREACHED, player.dstReached);
			editor.commit();
		}*/
	}//setOkToRun()
}//DrawThread
