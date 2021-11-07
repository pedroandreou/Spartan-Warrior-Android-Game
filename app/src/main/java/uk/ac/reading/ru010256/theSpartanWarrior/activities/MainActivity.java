package uk.ac.reading.ru010256.theSpartanWarrior.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.reading.ru010256.theSpartanWarrior.GameThread;
import uk.ac.reading.ru010256.theSpartanWarrior.GameView;
import uk.ac.reading.ru010256.theSpartanWarrior.R;
import uk.ac.reading.ru010256.theSpartanWarrior.TheGame;


public class MainActivity extends Activity {

    private static final int MENU_RESUME = 1;
    private static final int MENU_START = 2;
    private static final int MENU_STOP = 3;

    private GameThread mGameThread;
    private GameView mGameView;
    private MediaPlayer mediaPlayer;


    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not appear title bar on the screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Stop the screen from auto-turning off
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the layout of MainActivity class
        setContentView(R.layout.activity_main);

        // Create new media player to play the background music
        this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.background);
        // It starts and never stops till the player loses and needs to go back to the main menu
        this.mediaPlayer.start();

        mGameView = (GameView)findViewById(R.id.gamearea);
        mGameView.setMStatusView((TextView)findViewById(R.id.text));
        mGameView.setMScoreView((TextView)findViewById(R.id.score));
        mGameView.setLife1((ImageView)findViewById(R.id.life1));
        mGameView.setLife2((ImageView)findViewById(R.id.life2));
        mGameView.setLife3((ImageView)findViewById(R.id.life3));

        this.startGame(mGameView, null, savedInstanceState);
    }

    private void startGame(GameView gView, GameThread gThread, Bundle savedInstanceState) {

        //Set up a new game, we don't care about previous states
        mGameThread = new TheGame(mGameView);
        mGameView.setThread(mGameThread);
        mGameThread.setState(GameThread.STATE_READY);
        mGameView.startSensor((SensorManager)getSystemService(Context.SENSOR_SERVICE));
    }

    /*
     * Activity state functions
     */
    @Override
    protected void onPause() {
        super.onPause();

        if(mGameThread.getMMode() == GameThread.STATE_RUNNING) {
            mGameThread.setState(GameThread.STATE_PAUSE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mGameView.cleanup();
        mGameView.removeSensor((SensorManager)getSystemService(Context.SENSOR_SERVICE));
        mGameThread = null;
        mGameView = null;
    }

    /*
     * UI Functions
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

       menu.add(0, MENU_START, 0, R.string.menu_start);
       menu.add(0, MENU_STOP, 0, R.string.menu_stop);
       menu.add(0, MENU_RESUME, 0, R.string.menu_resume);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case MENU_START:
                mGameThread.doStart();
                return true;
            case MENU_STOP:
                System.exit(0);
                mGameThread.setState(GameThread.STATE_LOSE,  getText(R.string.message_stopped));
                return true;
            case MENU_RESUME:
                mGameThread.unpause();
                return true;
        }

        return false;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // Do nothing if nothing is selected
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