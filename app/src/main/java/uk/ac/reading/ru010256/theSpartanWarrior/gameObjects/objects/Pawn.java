package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects;

import android.graphics.Bitmap;

import lombok.Getter;
import lombok.Setter;
import uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.objects.GameObject;
import uk.ac.reading.ru010256.theSpartanWarrior.stats.Health;
import uk.ac.reading.ru010256.theSpartanWarrior.stats.Speed;

public class Pawn extends GameObject {

    @Getter
    @Setter
    private Health health;

    @Getter
    @Setter
    private Speed speed;

    @Getter
    @Setter
    private boolean isPlayer;

    @Getter
    @Setter
    private boolean canMove;

    public Pawn(float positionX, float positionY, Bitmap image, boolean isCollectable, Health health, Speed speed, boolean isPlayer) {
        super(positionX, positionY, image, isCollectable);
        this.health = health;
        this.speed = speed;
        this.isPlayer = isPlayer;
        this.canMove = true;
    }
}