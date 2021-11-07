package uk.ac.reading.ru010256.theSpartanWarrior.stats;

import lombok.Getter;
import lombok.Setter;

public class Speed {

    @Getter
    @Setter
    private float speedX;

    @Getter
    @Setter
    private float speedY;

    public Speed(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }
}
