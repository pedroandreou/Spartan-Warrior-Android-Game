package uk.ac.reading.ru010256.theSpartanWarrior.activities.highScore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.reading.ru010256.theSpartanWarrior.GameThread;
import uk.ac.reading.ru010256.theSpartanWarrior.R;
import uk.ac.reading.ru010256.theSpartanWarrior.activities.MainActivity;
import uk.ac.reading.ru010256.theSpartanWarrior.activities.MenuActivity;

public class HighScoreWriterActivity extends Activity {

    private long userScore;

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

        // Set the layout of HighScoreWriterActivity class
        setContentView(R.layout.activity_high_score_writer);

        // Get the high score from intent
        Intent intent = getIntent();
        final String userScore = intent.getStringExtra("high_score");

        final String TAG = "HighScoreWriterActivity";
        // Toast testing Freebase connection
        Toast.makeText(HighScoreWriterActivity.this, "Freebase Connection OK!", Toast.LENGTH_LONG).show();

        // retrieve TextView object as a variable txtDisp-  we use this to display text on Screen
        final TextView txtShow = findViewById(R.id.textShow);

        // Reading first name and last name object (EditText) from activity (GUI)
        final EditText txtUsrID, txtName, txtScore;
        txtUsrID = findViewById(R.id.txtUsrID);
        txtName = findViewById(R.id.txtName);
        txtScore = findViewById(R.id.txtScore);

        // Retrieve write button
        Button btnWrite;
        btnWrite = findViewById(R.id.btnWrite);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Android Score");

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usrID, name, score;
                usrID = txtUsrID.getText().toString();
                name = txtName.getText().toString();
                score = userScore;

                // Set the value to an object
                DataObject obj =  new DataObject(name,score);

                if(usrID.equalsIgnoreCase("")) {
                    txtShow.setText("Status : Enter Value");// display name on screen
                }else{
                    //Write values to the database
                    myRef.child(usrID).setValue(obj);
                    txtShow.setText("Status : Data Stored");// display name on screen
                    Toast.makeText(HighScoreWriterActivity.this, "Data Stored OK!", Toast.LENGTH_LONG).show();
                }

                // Get the user back to the Main Menu
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

}