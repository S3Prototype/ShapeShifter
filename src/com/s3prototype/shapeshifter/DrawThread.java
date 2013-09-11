package com.s3prototype.shapeshifter;

import java.util.concurrent.locks.ReentrantLock;

import tv.ouya.console.api.OuyaController;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
						c.drawColor(Color.WHITE);
						ship[0].update(c);
						checkController();
						//update shtuff
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				surfaceHolder.unlockCanvasAndPost(c);
			}//if c != null
		}// while()
	}
	
	public void checkController(){
		OuyaController c = OuyaController.getControllerByDeviceId(deviceID);
		
			float xAxis = c.getAxisValue(OuyaController.AXIS_LS_X);
			float yAxis = c.getAxisValue(OuyaController.AXIS_LS_Y);
			
			if(c.buttonChangedThisFrame(OuyaController.BUTTON_A)){
				ship[0].shoot();
			}
			
				//if a^2 + b^2 > c^2, it's out of the deadzone
			if(xAxis * xAxis + yAxis * yAxis < 
			   OuyaController.STICK_DEADZONE * OuyaController.STICK_DEADZONE){
				ship[0].stop();
				return;
			}//if()
			
			ship[0].move(xAxis, yAxis);
	}

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
