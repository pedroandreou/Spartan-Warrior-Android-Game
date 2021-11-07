package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects;

import java.util.ArrayList;
import java.util.List;

import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.GameObjectType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.PawnObjectType;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.GameObject;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.Pawn;


public class AddToPool {

    private GameObjectMgr gameObjectMgr;

    public AddToPool(GameObjectMgr gameObjectMgr) {
        this.gameObjectMgr = gameObjectMgr;
    }


    /**
     * The addSinglePawnObject method adds the Pawn object to the Pawn pool
     * @param type
     * @param gameObject
     */
    public void addSinglePawnObject(PawnObjectType type, Pawn gameObject) {
        // If Pawn type is null in the Pawn pool => make a new list of Pawns of that object, add the Pawn Object to the List and then add the List to the pool
        // with its type
        if (this.gameObjectMgr.pawnPool.get(type) == null) {
            List<Pawn> gameObjectList = new ArrayList<>();
            gameObjectList.add(gameObject);
            this.gameObjectMgr.pawnPool.put(type, gameObjectList);
            return;
        }

        // if the Pawn type exists already in the Pawn pool => just add the Pawn object to the corresponding List and add it to the Pawn pool
        List<Pawn> gameObjectList = this.gameObjectMgr.pawnPool.get(type);
        gameObjectList.add(gameObject);
        this.gameObjectMgr.pawnPool.put(type, gameObjectList);
    }

    /**
     * The addSingleGameObject method adds the gameObject object to the gameObject pool
     * @param type
     * @param gameObject
     */
    public void addSingleGameObject(GameObjectType type, GameObject gameObject) {
        // If gameObject type is null in the GameObject pool => make a new list of GameObjects of that object
        // add the GameObject to the List and then add the List to the GameObject pool with its type
        if (this.gameObjectMgr.gameObjectPool.get(type) == null) {
            List<GameObject> gameObjectList = new ArrayList<>();
            gameObject.setType(type);
            gameObjectList.add(gameObject);
            this.gameObjectMgr.gameObjectPool.put(type, gameObjectList);
            return;
        }

        // if the GameObject type exists already in the GameObject pool => just add the GameObject to the corresponding List and add it to the GameObject pool
        List<GameObject> gameObjectList = this.gameObjectMgr.gameObjectPool.get(type);
        gameObject.setType(type);
        gameObjectList.add(gameObject);
        this.gameObjectMgr.gameObjectPool.put(type, gameObjectList);
    }
}
