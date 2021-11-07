package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import lombok.var;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.GamePawnModel;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.GameObjectType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.ImageType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.PawnObjectType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.fileLoaders.ImageLoader;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.fileLoaders.JsonLoader;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.GameObject;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.Pawn;

public class GameObjectMgr {

    public HashMap<PawnObjectType, List<Pawn>> pawnPool = new HashMap<>();
    public HashMap<GameObjectType, List<GameObject>> gameObjectPool = new HashMap<>();

    private ImageLoader imageRepository;
    private AddToPool addToPool;
    private Context context;
    private JsonLoader jsonLoader;

    public int dirtyGameObjects = 0;

    /**
     * The GameObjectMgr constructor gets the json files by parsing them into Java Objects
     * randomizes the levels
     * adds the objects to the two pools
     * @param imageRepository
     * @param context
     */
    public GameObjectMgr(ImageLoader imageRepository, Context context){
        this.imageRepository = imageRepository;
        this.context = context;

        // Create AddToPool object to use it for adding the corresponding objects to the two pools
        addToPool = new AddToPool(this);

        // Create jsonLoader object to load GameObjects from JSON files
        this.jsonLoader = new JsonLoader();

        // Create Gson object to parse JSON Into Java Objects
        Gson gson = new Gson();

        // Randomize the levels between 1 (inclusive) and 4 (exclusive)
        Random r = new Random();
        int fileStart = 1;
        int fileEnd = 4;
        int randomFile = r.nextInt(fileEnd - fileStart) + fileStart;

        // the type that the json files will be converted to
        Type listType = new TypeToken<List<GamePawnModel>>() {}.getType();

        // initialising the jsonList
        List<GamePawnModel> jsonList = new Gson().fromJson(this.jsonLoader.getJsonFromAssets(this.context, randomFile + ".json"), listType);

        // Loop through the ArrayList of GameObjectModel where all the objects from JSON files are and add them to the two pools accordingly
        for(GamePawnModel go : jsonList) {
            // Convert GameObjectModel to Pawn Object
            Pawn pawn = convertToPawn(go);

            // Add it to the corresponding pool accordingly to the ImageName
            switch (go.getImageName()){
                case "HAZARD":
                    // Add Hazard to pawnPool
                    addToPool.addSinglePawnObject(PawnObjectType.HAZARD, pawn);
                    break;
                case "WARRIOR_RIGHT":
                    // Add Main Player to the Pawn Pool
                    addToPool.addSinglePawnObject(PawnObjectType.PLAYER, pawn);
                    break;
                case "WALL":
                    // Add Wall Object to gameObjectPool
                    addToPool.addSingleGameObject(GameObjectType.WALL, pawn);
                    break;
                case "DOOR_HORIZONTAL":
                    // Add Vertical Door Object to gameObjectPool
                    addToPool.addSingleGameObject(GameObjectType.DOOR_HORIZONTAL, pawn);
                    break;
                case "DOOR_VERTICAL":
                    // Add Vertical Door Object to gameObjectPool
                    addToPool.addSingleGameObject(GameObjectType.DOOR_VERTICAL, pawn);
                    break;
                case "KEY":
                    // Add KEY Object to gameObjectPool
                    addToPool.addSingleGameObject(GameObjectType.KEY, pawn);
                    break;
                case "BLACK_SOUP":
                    // Add KEY Object to gameObjectPool
                    addToPool.addSingleGameObject(GameObjectType.BLACK_SOUP, pawn);
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * The convertToPawn method converts the json object to an Object
     * @param model
     */
    private Pawn convertToPawn(GamePawnModel model) {
        // The conversion is needed because the bitmap of an object cannot be loaded from the json file, as the whole buffer will be needed
        // Therefore the imageType is used in the json files which will then be used to define the real bitmap of the corresponding object
        return new Pawn(model.getPositionX(), model.getPositionY(), this.imageRepository.repository.get(getImageType(model.getImageName())),
                model.isCollectable(), model.getHealth(), model.getSpeed(), model.isPlayer());
    }

    /**
     * The getImageType method checks and returns the ImageType of the object according to the value that was used in the json file
     * @param name
     */
    private ImageType getImageType(String name) {
        // Loop through the ImageType enum
        for (ImageType imageType : ImageType.values()) {
            // Check if a name equals to an imageType
            if (imageType.getValue().equals(name)) {
                return imageType;
            }
        }

        return null;
    }

    /**
     * The deleteDirtyGameObjects method checks and deletes any dirty object from the list
     */
    public void deleteDirtyGameObjects() {
        // Loop through the whole gameObjectPool's values
        for (List<GameObject> gameObjectList : this.gameObjectPool.values()) {
            // Loop till the end of the gameObjectPool's size by assigning each object to a gameObject instance  and then checking if it's dirty
            for (int i = 0; i < gameObjectList.size(); i++) {
                GameObject gameObject = gameObjectList.get(i);

                // Check if any gameObject is dirty
                if (gameObject.isDirty) {
                    gameObjectList.remove(gameObject); // Remove it from the List
                    this.dirtyGameObjects++; // dirtGameObject has been found so increase the counter
                }
            }
        }
    }

    public void clearPools(){
        this.gameObjectPool.clear();
        this.pawnPool.clear();
    }
}
