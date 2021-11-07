package uk.ac.reading.ru010256.theSpartanWarrior;

//Other parts of the android libraries that we use

import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.GameObjectType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.GameObject;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.GameObjectMgr;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.fileLoaders.ImageLoader;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.ImageType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.Pawn;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.PawnObjectType;
import uk.ac.reading.ru010256.theSpartanWarrior.stats.Health;
import uk.ac.reading.ru010256.theSpartanWarrior.stats.Speed;

public class TheGame extends GameThread {

    private Speed initialSpeed;
    private GameObjectMgr gameObjectMgr;
    private ImageLoader imageLoader;
    private GameView gameView;

    private Pawn myPlayer;

    private boolean readyForNextLevel = false;
    private MediaPlayer mediaPlayer;

    /**
     * The TheGame constructor prepares things that will be used below
     * @param gameView
     */
    public TheGame(GameView gameView) {
        // House keeping
        super(gameView);

        this.gameView = gameView;
        this.imageLoader = new ImageLoader(this.gameView);
        this.gameObjectMgr = new GameObjectMgr(this.imageLoader, super.getMContext());
        // initialization of the player
        this.setUpPlayer();

        // initialization of the Health UI
        this.updateHealthUI(this.myPlayer.getHealth().getCurrentHealth());
    }

    /**
     * The setUpPlayer method sets the player pawn object to the player instance and the player's speed to the initialSpeed instance
     */
    public void setUpPlayer() {
        this.myPlayer = this.gameObjectMgr.pawnPool.get(PawnObjectType.PLAYER).get(0); //player
        this.initialSpeed = new Speed(this.myPlayer.getSpeed().getSpeedX(), this.myPlayer.getSpeed().getSpeedY());
    }

    // This is run before a new game (also after an old game)
    @Override
    public void setupBeginning() {


    }

    @Override
    protected void doDraw(Canvas canvas) {
        // If there isn't a canvas to do nothing
        if (canvas == null) return;

        // House keeping
        super.doDraw(canvas);

        // Draw all the gameObjects to the canvas by looping through the List gameObjectsPool
        for (List<GameObject> gameObjectList : this.gameObjectMgr.gameObjectPool.values()) {
            for (GameObject gameObject : gameObjectList) {
                canvas.drawBitmap(gameObject.getBitmap(), (Resources.getSystem().getDisplayMetrics().widthPixels / 1080) * gameObject.getPositionX(),
                        (Resources.getSystem().getDisplayMetrics().heightPixels / 1794) * gameObject.getPositionY(), null);
            }
        }

        // Draw all the pawnObjects to the canvas by looping through the List pawnObjectsPool
        for (List<Pawn> pawnList : this.gameObjectMgr.pawnPool.values()) {
            for (Pawn pawn : pawnList) {
                canvas.drawBitmap(pawn.getBitmap(), (Resources.getSystem().getDisplayMetrics().widthPixels / 1080) * pawn.getPositionX(),
                        (Resources.getSystem().getDisplayMetrics().heightPixels / 1794) * pawn.getPositionY(), null);
            }
        }
    }

    // This is run whenever the phone is touched by the user
    @Override
    protected void actionOnTouch(float x, float y) {
        // Move the Spartan Warrior

        // If finger touches on the left of Spartan Warrior, then Spartan Warrior goes to the left direction
        if (x < myPlayer.getPositionX()) {
            myPlayer.getSpeed().setSpeedX(-initialSpeed.getSpeedX());
            myPlayer.setBitmap(imageLoader.repository.get(ImageType.WARRIOR_LEFT));
        }

        // If finger touches on the right of Spartan Warrior, then Spartan Warrior goes to the right direction
        if (x >= myPlayer.getPositionX()) {
            myPlayer.getSpeed().setSpeedX(initialSpeed.getSpeedX());
            myPlayer.setBitmap(imageLoader.repository.get(ImageType.WARRIOR_RIGHT));
        }
    }

    // This is run just before the game "scenario" is printed on the screen
    @Override
    protected void updateGame(float secondsElapsed) {

        // Loop through the Pool of Pawn objects
        for (List<Pawn> pawnList : this.gameObjectMgr.pawnPool.values()) {
            for (Pawn pawn : pawnList) {

                // Move the Pawn's X and Y using the speed (pixel/sec)
                if (pawn.isPlayer()) {
                    if (myPlayer.getSpeed().getSpeedX() != 0 && myPlayer.isCanMove()) {
                        myPlayer.setPositionX(myPlayer.getPositionX() + secondsElapsed * myPlayer.getSpeed().getSpeedX());
                    }
                    myPlayer.setPositionY(myPlayer.getPositionY() + secondsElapsed * myPlayer.getSpeed().getSpeedY());
                } else {
                    pawn.setPositionX(pawn.getPositionX() + secondsElapsed * pawn.getSpeed().getSpeedX());
                    pawn.setPositionY(pawn.getPositionY() + secondsElapsed * pawn.getSpeed().getSpeedY());
                }

                // Change direction if there is collision between Pawn and GameObject
                for (List<GameObject> gameObjectList : this.gameObjectMgr.gameObjectPool.values()) {
                    for (GameObject gameObject : gameObjectList) {
                        if (pawn.isPlayer()) { // if Pawn is the Player => do not move diagonally
                            updateCollision(this.myPlayer, gameObject, false);
                        } else { // if the Pawn is not the Player => move diagonally
                            updateCollision(pawn, gameObject, true);
                        }
                    }
                }

                // Check if the pawn hits either the left side or the right side of the screen
                // But only do something if the pawn is moving towards that side of the screen
                // If it does that => change the direction of the ball in the X direction
                if ((pawn.getPositionX() <= pawn.getBitmap().getWidth() / 2 && pawn.getSpeed().getSpeedX() < 0) || (pawn.getPositionX() >= mCanvasWidth - pawn.getBitmap().getWidth() / 2 && pawn.getSpeed().getSpeedX() > 0)) {
                    pawn.getSpeed().setSpeedX(-pawn.getSpeed().getSpeedX());
                }

                // If the pawn goes out of the top of the screen and moves towards the top of the screen =>
                // change the direction of the pawn in the Y direction
                if (pawn.getPositionY() <= pawn.getBitmap().getWidth() / 2 && pawn.getSpeed().getSpeedY() < 0) {
                    pawn.getSpeed().setSpeedY(-pawn.getSpeed().getSpeedY());
                }

                // If the pawn goes out of the bottom of the screen => change the direction
                if (pawn.getPositionY() >= mCanvasHeight - 150) {
                    pawn.getSpeed().setSpeedY(-pawn.getSpeed().getSpeedY());
                }

                // If the Player hits another Pawn => collide and lose health
                if (!pawn.isPlayer()) {
                    if (updateCollision(myPlayer, pawn, false)) {
                        // Decrease the health of the Player
                        this.myPlayer.getHealth().decrement(1);

                        // Remove a life on the top of the screen of the UI
                        this.updateHealthUI(this.myPlayer.getHealth().getCurrentHealth());
                    }
                }
            }
        }

        // Delete any dirty game objects using multithreading
        Thread gameObjectMgrThread = new Thread() {
            public void run(){
                gameObjectMgr.deleteDirtyGameObjects();
            }
        };

        // Start the thread
        gameObjectMgrThread.start();

        // Redraw the canvas without the dirtyObjects
        if (this.gameObjectMgr.dirtyGameObjects > 0) {
            doDraw(super.getCanvasRun());
            this.gameObjectMgr.dirtyGameObjects = 0;
        }

    }

    /**
     * The updateHealthUI method updates the health's state
     * @param health
     */
    public void updateHealthUI(int health) {
        synchronized (monitor) {
            Message msg = gameView.getMHandler().obtainMessage();
            Bundle b = new Bundle();
            b.putBoolean("updateHealth", true);
            switch (health) {
                // If current health is equal to 0, then all lives will disappear
                case 0:
                    b.putInt("life1", View.INVISIBLE);
                    b.putInt("life2", View.INVISIBLE);
                    b.putInt("life3", View.INVISIBLE);
                    setState(STATE_LOSE);
                    break;
                // If current health is equal to 1, then only the first life will be visible
                case 1:
                    b.putInt("life1", View.VISIBLE);
                    b.putInt("life2", View.INVISIBLE);
                    b.putInt("life3", View.INVISIBLE);
                    break;
                // If current health is equal to 2, then only the first two lives will be visible
                case 2:
                    b.putInt("life1", View.VISIBLE);
                    b.putInt("life2", View.VISIBLE);
                    b.putInt("life3", View.INVISIBLE);
                    break;
                // If current health is equal to 3, then all the lives will be visible
                case 3:
                    b.putInt("life1", View.VISIBLE);
                    b.putInt("life2", View.VISIBLE);
                    b.putInt("life3", View.VISIBLE);
                    break;
            }
            msg.setData(b);
            gameView.getMHandler().sendMessage(msg);
        }
    }

    /**
    *Touch call back
     */
    @Override
    protected void onTouchUp() {
        if (myPlayer == null) {
            return;
        }
        myPlayer.getSpeed().setSpeedX(0);
    }


    /**
     * The updateCollision method controls the collision between a Pawn and a Game object
     * @param pawn
     * @param gameObject
     * @param canMoveDiagonal
     */
    private boolean updateCollision(Pawn pawn, GameObject gameObject, boolean canMoveDiagonal) {
        float x = gameObject.getPositionX();
        float y = gameObject.getPositionY();

        // This will store the min distance allowed between the Pawn and the GameObject
        // This is used to check collisions
        float mMinDistanceBetweenPawnAndObject = (gameObject.getBitmap().getWidth() / 2 + pawn.getBitmap().getWidth() / 2) * (gameObject.getBitmap().getWidth() / 2 + pawn.getBitmap().getWidth() / 2);


        // Get actual distance (without square root - remember?) between the Pawn and the GameObject being checked
        float distanceBetweenPawnAndObject = (x - pawn.getPositionX()) * (x - pawn.getPositionX()) + (y - pawn.getPositionY()) * (y - pawn.getPositionY());

        // Check if the actual distance is more than the allowed => collision
        if (mMinDistanceBetweenPawnAndObject >= distanceBetweenPawnAndObject) {

            // playerCollidedOnDirtyObject(pawn, gameObject);

            if (canMoveDiagonal) {
                // Get the present speed (this should also be the speed going away after the collision)
                float speedOfPawn = (float) Math.sqrt(pawn.getSpeed().getSpeedX() * pawn.getSpeed().getSpeedX() + pawn.getSpeed().getSpeedY() * pawn.getSpeed().getSpeedY());

                pawn.getSpeed().setSpeedX(pawn.getPositionX() - x);
                pawn.getSpeed().setSpeedY(pawn.getPositionY() - y);

                // Get the speed after the collision
                float newSpeedOfPawn = (float) Math.sqrt(pawn.getSpeed().getSpeedX() * pawn.getSpeed().getSpeedX() + pawn.getSpeed().getSpeedY() * pawn.getSpeed().getSpeedY());

                // using the fraction between the original speed and present speed to calculate the needed
                // velocities in X and Y to get the original speed but with the new angle.

                pawn.getSpeed().setSpeedX(pawn.getSpeed().getSpeedX() * speedOfPawn / newSpeedOfPawn);
                pawn.getSpeed().setSpeedY(pawn.getSpeed().getSpeedY() * speedOfPawn / newSpeedOfPawn);

                return true;
            }

            // If pawn is the Player
            if (pawn.isPlayer()) {
                // The collectable item will disappear when the player collides with it and checkForAudioSound will be called to play the music audio file
                if (gameObject.isCollectable()) {
                    gameObject.isDirty = true;
                    checkForAudioSound(gameObject);
                }

                // Check if ready for next level
                if( checkForNextLevel(gameObject) == false){
                    // Do collision when is not ready to go to the next level
                    this.playerCollidedOnX(pawn, gameObject);
                    this.playerCollidedOnY(pawn, gameObject);
                };
            }

            return true;
        }

        return false;
    }


    /**
     * The playerCollidedOnX method controls the collision on X direction between the Player and a gameObject
     * @param pawn
     * @param gameObject
     */
    private void playerCollidedOnX(Pawn pawn, GameObject gameObject) {
        float x = gameObject.getPositionX();

        // This will store the min distance allowed between the Pawn and the GameObject
        // This is used to check collisions
        float mMinDistanceBetweenPawnAndObject = (gameObject.getBitmap().getWidth() / 2 + pawn.getBitmap().getWidth() / 2) * (gameObject.getBitmap().getWidth() / 2 + pawn.getBitmap().getWidth() / 2);

        // Get actual distance (without square root - remember?) between the Pawn and the GameObject being checked in X direction
        float distanceBetweenPawnAndObject = (x - pawn.getPositionX()) * (x - pawn.getPositionX());

        // Check if the actual distance is more than the allowed => change direction by setting the speed to 0 to stop
        if (mMinDistanceBetweenPawnAndObject >= distanceBetweenPawnAndObject) {
            if (this.myPlayer.getSpeed().getSpeedX() > 0) {
                this.myPlayer.getSpeed().setSpeedX(0);
                this.myPlayer.setCanMove(false);
                return;
            }
            this.myPlayer.setCanMove(true);
        }
    }

    /**
     * The playerCollidedOnY method controls the collision on Y direction between the Player and a gameObject
     * @param pawn
     * @param gameObject
     */
    private void playerCollidedOnY(Pawn pawn, GameObject gameObject) {
        float y = gameObject.getPositionY();

        // This will store the min distance allowed between the Pawn and the GameObject
        // This is used to check collisions
        float mMinDistanceBetweenPawnAndObject = (gameObject.getBitmap().getHeight() / 2 + pawn.getBitmap().getHeight() / 2) * (gameObject.getBitmap().getHeight() / 2 + pawn.getBitmap().getHeight() / 2);

        // Get actual distance (without square root - remember?) between the Pawn and the GameObject being checked in Y direction
        float distanceBetweenPawnAndObject = (y - pawn.getPositionY()) * (y - pawn.getPositionY());

        // Check if the actual distance is more than the allowed => change direction by changing the speed
        if (mMinDistanceBetweenPawnAndObject >= distanceBetweenPawnAndObject) {
            if (this.myPlayer.getSpeed().getSpeedY() > 0) {
                this.myPlayer.getSpeed().setSpeedY(-initialSpeed.getSpeedY());
            } else {
                this.myPlayer.getSpeed().setSpeedY(initialSpeed.getSpeedY());
            }
        }
    }

    /**
     * The checkForAudioSound method checks which sound should be played
     * @param gameObject
     */
    private void checkForAudioSound(GameObject gameObject){
        if(gameObject.getBitmap() == imageLoader.repository.get(ImageType.BLACK_SOUP)){
            // Play the yummy music when black soup is collected
            this.mediaPlayer = MediaPlayer.create(super.getMContext(), R.raw.yummy);
            this.mediaPlayer.start();

            // When black soup is collected => health of player is increased
            this.myPlayer.getHealth().increment(1);

            // Add a life at the top of the screen of the UI
            this.updateHealthUI(this.myPlayer.getHealth().getCurrentHealth());
        }
        else if (gameObject.getBitmap() == imageLoader.repository.get(ImageType.KEY)){
            // Play the key music when key is collected
            this.mediaPlayer = MediaPlayer.create(super.getMContext(), R.raw.keys);
            this.mediaPlayer.start();

            // Ready to go to the next level when the key is collected
            this.readyForNextLevel = true;
        }
    }

    /**
     * The checkForNextLevel method checks if the level is completed to go to the next one
     * @param gameObject
     */
    private boolean checkForNextLevel(GameObject gameObject){
        // If Player collected the key already and touches the vertical or horizontal door => go to the next level
        if ((gameObject.getType().equals(GameObjectType.DOOR_HORIZONTAL) || (gameObject.getType().equals(GameObjectType.DOOR_VERTICAL)))  && readyForNextLevel == true){
            this.mediaPlayer.release();

            this.gameObjectMgr = new GameObjectMgr(this.imageLoader, super.getMContext()); // Create new gameObjectMgr to randomize and get the next level
            this.setUpPlayer(); // Set up the player's attributes according to the next level

            // Play the this is sparta sound when is ready to go to the next level
            this.mediaPlayer = MediaPlayer.create(super.getMContext(), R.raw.thisissparta);
            this.mediaPlayer.start();

            this.updateHealthUI(this.myPlayer.getHealth().getCurrentHealth()); // Update health UI
            doDraw(super.getCanvasRun()); // Redraw the canvas for the next level

            // Update Score
            super.updateScore(1);

            this.readyForNextLevel = false;
            return true;
        }
        return false;
    }
}