package uk.ac.reading.ru010256.theSpartanWarrior.gameObjects.enums;

import lombok.Getter;

public enum ImageType {
    WARRIOR_RIGHT("WARRIOR_RIGHT"),
    WARRIOR_LEFT("WARRIOR_LEFT"),
    HAZARD("HAZARD"),
    WALL("WALL"),
    DOOR_VERTICAL("DOOR_VERTICAL"),
    DOOR_HORIZONTAL("DOOR_HORIZONTAL"),
    KEY("KEY"),
    BLACK_SOUP("BLACK_SOUP");

    @Getter
    String value;
    ImageType(String value) {
        this.value = value;
    }

}
