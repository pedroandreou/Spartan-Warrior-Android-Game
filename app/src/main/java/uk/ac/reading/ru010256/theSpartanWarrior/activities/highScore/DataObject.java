package uk.ac.reading.ru010256.theSpartanWarrior.activities.highScore;

import lombok.Getter;
import lombok.Setter;

public class DataObject {

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String score;

    public DataObject(String name, String score) {
        this.name = name;
        this.score = score;
    }
}
