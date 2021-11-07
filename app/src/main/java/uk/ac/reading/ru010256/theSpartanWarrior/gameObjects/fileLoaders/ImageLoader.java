package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.fileLoaders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.ImageType;
import uk.ac.reading.ru010256.theSpartanWarrior.GameView;

import uk.ac.reading.ru010256.theSpartanWarrior.R;

public class ImageLoader {

    public HashMap<ImageType, Bitmap> repository = new HashMap<>();

    /**
     * The imageLoader constructor loads all the bitmaps and put them in HashMap with the ImageType as the key
     * @param gameView
     */
    public ImageLoader(GameView gameView) {

        //Prepare the image of the Spartan Warrior that looks on the right so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.WARRIOR_RIGHT, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.warrior01));

        //Prepare the image of the Spartan Warrior that looks on the left so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.WARRIOR_LEFT, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.warrior02));

        //Prepare the image of the Hazard so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.HAZARD, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.hazard));

        //Prepare the image of the Wall so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.WALL, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.wall));

        //Prepare the image of the horizontal Door so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.DOOR_HORIZONTAL, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.door1));

        //Prepare the image of the vertical Door so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.DOOR_VERTICAL, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.door2));

        //Prepare the image of the key so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.KEY, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.key));

        //Prepare the image of the key so we can draw it on the screen (using a canvas)
        this.repository.put(ImageType.BLACK_SOUP, BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.blacksoup));
    }
}
