package com.s3prototype.shapeshifter;

import java.util.concurrent.locks.ReentrantLock;

import com.s3prototype.shapeshifter.Controller.Axis;
import com.s3prototype.shapeshifter.Controller.Stick;

import tv.ouya.console.api.OuyaController;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;

public class LauncherActivity extends Activity  {
	
	GameView gameView;
	DrawThread drawThread;
	final ReentrantLock threadLock = new ReentrantLock();
/*	boolean shouldLoadFile = false;
	static boolean fileWasSaved = false;*/
	boolean gameWasStarted = false;
	
	@Override
	protected void onCreate(Bundle savedData) {
		super.onCreate(savedData);
		OuyaController.init(this);
		//drawThread = new DrawThread(getApplicationContext(), null, null, null);
		gameView = new GameView(LauncherActivity.this, threadLock);
		drawThread = gameView.drawThread;//We'll save data from this later
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(gameView);
		Log.d("ON CREATE:", "CALLED");
		gameWasStarted = true;
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}//onResume()
	
	@Override
	protected void onPause() {
  		super.onPause();
	}//onPause()
	
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
	    boolean handled = OuyaController.onGenericMotionEvent(event);
		Controller.player = OuyaController.getPlayerNumByDeviceId(event.getDeviceId());  
	    return handled || super.onGenericMotionEvent(event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = OuyaController.onKeyDown(keyCode, event);
		return handled ||super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean handled = OuyaController.onKeyUp(keyCode, event);
		return handled ||super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
	
}//LauncherActivity
