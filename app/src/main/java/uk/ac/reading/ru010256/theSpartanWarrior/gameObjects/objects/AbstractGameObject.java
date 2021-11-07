package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects;

import android.graphics.Bitmap;

import lombok.Getter;
import lombok.Setter;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums.GameObjectType;

abstract class AbstractGameObject {

    @Getter
    @Setter
    protected float positionX;

    @Getter
    @Setter
    protected float positionY;

    @Getter
    @Setter
    protected Bitmap bitmap;

    @Getter
    @Setter
    protected GameObjectType type;

    @Getter
    @Setter
    protected boolean isCollectable;

    public AbstractGameObject(float positionX, float positionY, Bitmap image, boolean isCollectable) {
            this.positionX = positionX;
            this.positionY = positionY;
            this.bitmap = image;
            this.isCollectable = isCollectable;
            this.type = GameObjectType.NONE;
    }
}
