package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects;

import android.graphics.Bitmap;

import lombok.Getter;
import lombok.Setter;

public class GameObject extends AbstractGameObject {

    @Getter
    @Setter
    public boolean isDirty;

    public GameObject(float positionX, float positionY, Bitmap image, boolean isCollectable) {
        super(positionX, positionY, image, isCollectable);
        this.isDirty = false;
    }
}
