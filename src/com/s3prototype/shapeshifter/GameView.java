package com.s3prototype.shapeshifter;

import java.util.concurrent.locks.ReentrantLock;

import tv.ouya.console.api.OuyaController;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	Context context;
	DrawThread drawThread;
	SurfaceHolder sHolder;
	int sWidth, sHeight;
	LauncherActivity sActivity;
		//These are the dimensions for the galaxy s II screen,
		//which the game is based off. Scale the game to fit the screen,
		//but the game is designed for this viewport size.
	final static double baseWidth = 800, baseHeight = 480;
	public ReentrantLock threadLock;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		drawThread.onTouch(event);
		return super.onTouchEvent(event);
	}

	public GameView(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	public GameView(LauncherActivity activity, ReentrantLock lock) {
		super(activity.getApplicationContext());
		sActivity = activity;
		this.context = sActivity.getApplicationContext();
		threadLock = lock;
		sHolder = getHolder();
		sHolder.addCallback(this);
		//drawThread = new DrawThread(context, this, sHolder, sActivity);
		Log.d("GAMVIEW CONSTRUCTOR: ", "CALLED");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		sWidth = width;
		sHeight = height;
		sHolder = holder;
		sHolder.addCallback(this);
		
		drawThread.scaledX = baseWidth/sWidth;
		drawThread.scaledY = baseHeight/sHeight;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(drawThread != null){
			if (drawThread.getState() == Thread.State.NEW) {
				drawThread.start();
			}
		} else {
			drawThread = new DrawThread(context, this, sHolder, sActivity);
			drawThread.threadLock = threadLock;
			Log.d("SURFACE CREATED:", "A drawthread was just made");
			drawThread.start();
		}//else
		drawThread.setOkToRun(true);
	}//surfaceCreated

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setOkToRun(false);
		try{
			drawThread.join();
			drawThread = null;
		} catch (InterruptedException e){
			
		}
	}//surfaceDestroyed()

}//class GameView
