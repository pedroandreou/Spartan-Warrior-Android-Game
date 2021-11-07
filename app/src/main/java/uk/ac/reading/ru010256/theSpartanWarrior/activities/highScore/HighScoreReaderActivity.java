package uk.ac.reading.ru010256.theSpartanWarrior.activities.highScore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import uk.ac.reading.ru010256.theSpartanWarrior.R;

public class HighScoreReaderActivity extends Activity {

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

        // Set the layout of HighScoreReaderActivity class
        setContentView(R.layout.activity_high_score_reader);

        final String TAG = "HighScoreReaderActivity";
        // Toast testing Freebase connection
        Toast.makeText(HighScoreReaderActivity.this, "Freebase Connection OK!", Toast.LENGTH_LONG).show();

        //retrieve TextView object as a variable txtDisp-  we use this to display text on Screen
        final TextView txtShow = findViewById(R.id.textShow);

        //Reading first name and last name object (EditText) from activity (GUI)
        final EditText txtUsrID, txtName, txtScore;
        txtUsrID = findViewById(R.id.txtUsrID);
        txtName = findViewById(R.id.txtName);
        txtScore = findViewById(R.id.txtScore);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Android Score");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //String value = dataSnapshot.getValue(String.class); //Unused

                        ArrayList value = new ArrayList();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String  data =  ds.getKey() + ": ";
                            for (DataSnapshot dsChildren : ds.getChildren()) {
                                data = data + " " + dsChildren.getValue();
                            }
                            value.add(data); // storing into ArrayList
                        }
                        //Collections.shuffle(value); // shuffle the array list
                        int i = 0;
                        Iterator iter = value.iterator(); // iterator for array list
                        String txtPrint = ""; // collect 5 String in five lines
                        while (iter.hasNext() && i < 5) {// iterate for five names in database
                            txtPrint = txtPrint + iter.next() + "\n"; // "\n" will enable line
                            i++; // increasing count♦
                        }

                        Log.d(TAG, "Value is: " + txtPrint);
                        txtShow.setText("Status : Data Retrieved" + "\n\n" + txtPrint);// display name on screen

                        txtUsrID.setText("");
                        txtName.setText("");
                        txtScore.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
}