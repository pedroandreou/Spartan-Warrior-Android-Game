package uk.ac.reading.ru010256.theSpartanWarrior;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends Activity {

    private GameThread mGameThread;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //do not appear title bar on the screen

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //stop the screen from auto-turning off

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.menu_main); //set the UI of MainActivity class

        //get the startButton
        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        startButton.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);

        //get the exitButton
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get the highScoreButton
        Button highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //still need to write
            }
        });
    }
}
