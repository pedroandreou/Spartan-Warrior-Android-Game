package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums;

import lombok.Getter;
import lombok.Setter;
import uk.ac.reading.ru010256.theSpartanWarrior.stats.Health;
import uk.ac.reading.ru010256.theSpartanWarrior.stats.Speed;

public class GamePawnModel {

    @Getter
    @Setter
    float positionX;

    @Getter
    @Setter
    float positionY;

    @Getter
    @Setter
    private Health health;

    @Getter
    @Setter
    private String imageName;

    @Getter
    @Setter
    private Speed speed;

    @Getter
    @Setter
    private boolean isPlayer;

    @Getter
    @Setter
    private boolean canMove;

    @Getter
    @Setter
    private boolean isCollectable;


    public GamePawnModel(float positionX, float positionY, String imageName, boolean isCollectable, Health health, Speed speed, boolean isPlayer) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.health = health;
        this.imageName =  imageName;
        this.isCollectable = isCollectable;
        this.speed = speed;
        this.isPlayer = isPlayer;
        this.canMove = true;
    }

}
