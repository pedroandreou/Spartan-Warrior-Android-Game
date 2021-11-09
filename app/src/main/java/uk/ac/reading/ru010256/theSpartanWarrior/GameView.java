package uk.ac.reading.ru010256.theSpartanWarrior;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

	@Getter
	private volatile GameThread thread;

	//Handle communication from the GameThread to the View/Activity Thread
	@Getter
	@Setter
	private Handler mHandler;
	
	//Pointers to the views
	@Getter
	@Setter
	private TextView mScoreView;

	@Getter
	@Setter
	private TextView mStatusView;

	@Setter
	private ImageView life1;

	@Setter
	private ImageView life2;

	@Setter
	private ImageView life3;

    Sensor accelerometer;
	//private SensorEventListener sensorAccelerometer;
    Sensor magnetometer;


	public GameView(Context context, AttributeSet attrs) throws IOException {
		super(context, attrs);

		//Get the holder of the screen and register interest
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		//Set up a handler for messages from GameThread
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message m) {
				Bundle bundle = m.getData();


				if(bundle.getBoolean("score")) {
					mScoreView.setText(bundle.getString("text"));
				} else if(bundle.getBoolean("updateHealth")) {
					int lifex1 = bundle.getInt("life1");
					switch (lifex1) {
						case View.VISIBLE:
							life1.setVisibility(View.VISIBLE);
							break;
						case View.INVISIBLE:
							life1.setVisibility(View.INVISIBLE);
							break;
						case View.GONE:
							life1.setVisibility(View.GONE);
							break;
					}

					int lifex2 = bundle.getInt("life2");
					switch (lifex2) {
						case View.VISIBLE:
							life2.setVisibility(View.VISIBLE);
							break;
						case View.INVISIBLE:
							life2.setVisibility(View.INVISIBLE);
							break;
						case View.GONE:
							life2.setVisibility(View.GONE);
							break;
					}

					int lifex3 = bundle.getInt("life3");
					switch(lifex3) {
						case View.VISIBLE:
							life3.setVisibility(View.VISIBLE);
							break;
						case View.INVISIBLE:
							life3.setVisibility(View.INVISIBLE);
							break;
						case View.GONE:
							life3.setVisibility(View.GONE);
							break;
					}
				} else {
					//So it is a status
                    int i = bundle.getInt("viz");
                    switch(i) {
                        case View.VISIBLE:
                            mStatusView.setVisibility(View.VISIBLE);
                            break;
                        case View.INVISIBLE:
                            mStatusView.setVisibility(View.INVISIBLE);
                            break;
                        case View.GONE:
                            mStatusView.setVisibility(View.GONE);
                            break;
                    }

                    mStatusView.setText(bundle.getString("text"));



				}
 			}
		};
	}
	
	//Used to release any resources.
	public void cleanup() {
		this.thread.setMRun(false);
		this.thread.cleanup();
		
		this.removeCallbacks(thread);
		thread = null;
		
		this.setOnTouchListener(null);
		
		SurfaceHolder holder = getHolder();
		holder.removeCallback(this);
	}
	
	/*
	 * Setters and Getters
	 */

	public void setThread(GameThread newThread) {

		thread = newThread;
		setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
                return thread != null && thread.onTouch(event);
            }
		});

        setClickable(true);
		setFocusable(true);
	}

	
	
	/*
	 * Screen functions
	 */
	
	//ensure that we go into pause state if we go out of focus
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if(thread!=null) {
			if (!hasWindowFocus)
				thread.pause();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(thread!=null) {
			thread.setMRun(true);
			
			if(thread.getState() == Thread.State.NEW){
				//Just start the new thread
				thread.start();
			}
			else {
				if(thread.getState() == Thread.State.TERMINATED){
					//Start a new thread
					//Should be this to update screen with old game: new GameThread(this, thread);
					//The method should set all fields in new thread to the value of old thread's fields 
					thread = new TheGame(this);
					thread.setMRun(true);
					thread.start();
				}
			}
		}
	}
	
	//Always called once after surfaceCreated. Tell the GameThread the actual size
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(thread!=null) {
			thread.setSurfaceSize(width, height);			
		}
	}

	/*
	 * Need to stop the GameThread if the surface is destroyed
	 * Remember this doesn't need to happen when app is paused on even stopped.
	 */
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
		boolean retry = true;
		if(thread!=null) {
			thread.setMRun(false);
		}
		
		//join the thread with this thread
		while (retry) {
			try {
				if(thread!=null) {
					thread.join();
				}
				retry = false;
			} 
			catch (InterruptedException e) {
				//naugthy, ought to do something...
			}
		}
	}
	
	/*
	 * Accelerometer
	 */
	public void startSensor(SensorManager sm) {

        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);

    }
	
	public void removeSensor(SensorManager sm) {
        sm.unregisterListener(this);

        accelerometer = null;
        magnetometer = null;
	}

    //A sensor has changed, let the thread take care of it
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(thread!=null) {
            thread.onSensorChanged(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// 
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.
