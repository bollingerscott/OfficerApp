package com.pss.wsu.officersupport;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AudioRecording extends Activity {
	private MediaPlayer mediaPlayer;
	private MediaRecorder mediaRecorder;
	Date date;
	Timestamp timestamp;
	//This is used to store the recorded audio
	File output; 
	//Used to store the absolute path of the recording
	String outputFile;
	//Used to determine if a recording is currently taking place
	boolean isRecording = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_recording);
		final ImageButton record = (ImageButton) findViewById(R.id.record);
		final ImageButton play = (ImageButton) findViewById(R.id.play);
		final ImageButton stop = (ImageButton) findViewById(R.id.stop);
		//Using the sd card DCIM directory to write the recorded audio files
		final File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		// Giving the filename name as the current time
		date=new Date();
		timestamp=new Timestamp(date.getTime());
		output = new File(path,"audio_recording"+timestamp.toString() );
		outputFile=output.getAbsolutePath();
		
		//Declaring save button and set the onclickListener
		Button save=(Button)findViewById(R.id.saveRecording);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//saving the wheezing recording to database
				//Check whether the file exists or not
				if(output.exists())
				{
					//adding the record to the wheezing recordings table
					//addRowtoWheezingRecordingsTable(outputFile, timestamp);
					quickMessage("Successfully saved");
					finish();
				}
				else
				{
					quickMessage("First record the wheezing sound. Try again");
				}
				
			}
		});


		
		record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!isRecording){
					//record.setImageResource(R.drawable.blue_record);
				}else{
					record.setImageResource(R.drawable.record);
				}
				buttonTapped(v);
				
			}
			
						

		});
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (play.isSelected()){
		            play.setSelected(false);
		            //...Handle toggle off
		        } else {
		            play.setSelected(true);
		            //...Handled toggle on
		        }
				buttonTapped(v);
			}
		});
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				record.setImageResource(R.drawable.record);
				if (stop.isSelected()){
		            stop.setSelected(false);
		            //...Handle toggle off
		        } else {
		            stop.setSelected(true);
		            //...Handled toggle on
		        }
				buttonTapped(v);

			}
		});
		
	}

	
	
	private void ditchMediaRecorder() {
		if (mediaRecorder != null) {
			mediaRecorder.release();
		}

	}

	private void beginRecording() throws Exception {
		if (output.exists()) {
			output.delete();
		}
			isRecording = true;
			ditchMediaRecorder();
			mediaRecorder = new MediaRecorder();
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.setOutputFile(outputFile);
			mediaRecorder.prepare();
			mediaRecorder.start();
			
					
		
	}

	private void playRecording() throws Exception, IOException {
		if(mediaPlayer != null){
			mediaPlayer.release();
		}
		ditchRecording();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(outputFile);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}
	
	private void stopRecording() {
		if(mediaRecorder != null){
		mediaRecorder.stop();
		}
		isRecording = false;
	}
	
	
	private void ditchRecording() throws Exception, IOException {
		if(mediaPlayer != null){
			mediaPlayer.release();
		}
	}
	

	private void buttonTapped(View view) {
		switch (view.getId()) {
		case R.id.record:
			try {
				if(!isRecording){
					beginRecording();
				}else{
					stopRecording();
				}
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			break;
		case R.id.play:
			try {
				if(!isRecording){
					playRecording();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.stop:
			try {
				if(isRecording){
					stopRecording();
				}
							
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			break;
		
		}
	}
		

	/*
	 * A function to display Toast Messages.
	 * 
	 * By having it run on the UI thread, we will be sure that the message is
	 * displays no matter what thread tries to use it.
	 */
	public void quickMessage(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});

	}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.audio_recording, menu);
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
}
