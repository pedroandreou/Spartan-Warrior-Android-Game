package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.fileLoaders;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonLoader {

    /**
     * The getJsonFromAssets method gets the json files from the assets folder of the project
     * @param context
     * @param fileName
     */
    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}
