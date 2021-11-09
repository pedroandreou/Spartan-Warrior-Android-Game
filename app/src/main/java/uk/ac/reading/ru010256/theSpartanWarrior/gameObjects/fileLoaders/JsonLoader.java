package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.fileLoaders;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonLoader {

    /**
     * The getJsonFromAssets method gets the json files from the assets folder of the project and stores it in a string
     * @param context
     * @param fileName
     */
    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            // Get file via input stream
            InputStream is = context.getAssets().open(fileName);

            // Get the available size of the input stream
            int size = is.available();

            // Create a new buffer with that size
            byte[] buffer = new byte[size];

            // Read the buffer via the input stream
            is.read(buffer);

            // close the input stream
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}
