package uk.ac.reading.ru010256.theSpartanWarrior.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import uk.ac.reading.ru010256.theSpartanWarrior.GameThread;
import uk.ac.reading.ru010256.theSpartanWarrior.R;
import uk.ac.reading.ru010256.theSpartanWarrior.activities.highScore.HighScoreReaderActivity;

public class MenuActivity extends Activity {

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

        // Set the layout of MenuActivity class
        setContentView(R.layout.menu_main);

        // Get the startButton
        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        startButton.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);

        // Get the exitButton
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get the highScoreButton
        Button highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HighScoreReaderActivity.class);
                startActivity(intent);
            }
        });
    }
}
