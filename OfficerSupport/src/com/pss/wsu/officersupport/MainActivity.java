package com.pss.wsu.officersupport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Called when user taps Case List button
	public void goToCases(View view)
	{
		Intent intent = new Intent(this, CaseList.class);
		startActivity(intent);
	}
	
	public void goToMap(View view){
		Intent intent = new Intent(this, Map.class);
		startActivity(intent);
	}
	
	public void goToRecording(View view){
		Intent i = new Intent(this, AudioRecording.class);
		startActivity(i);
	}
	
	public void goToPhotoVideo(View view){
		Intent i = new Intent(this, PhotoVideoActivity.class);
		startActivity(i);
	}
}
