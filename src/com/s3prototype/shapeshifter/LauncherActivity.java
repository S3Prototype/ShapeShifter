package com.s3prototype.shapeshifter;

import java.util.concurrent.locks.ReentrantLock;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
	
}//LauncherActivity
