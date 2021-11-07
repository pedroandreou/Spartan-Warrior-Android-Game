package uk.ac.reading.ru010256.theSpartanWarrior.stats;

import lombok.Getter;
import uk.ac.reading.ru010256.theSpartanWarrior.GameView;
import uk.ac.reading.ru010256.theSpartanWarrior.integerStat.IHealth;

public class Health implements IHealth {

    @Getter
    private int currentHealth;
    @Getter
    private int maxHealth;

    public Health(int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    @Override
    public void increment(int amount) {
        if (currentHealth != maxHealth){
            currentHealth += amount;
        }
    }

    @Override
    public void decrement(int amount) {
        if (currentHealth > 0){
            currentHealth -= amount;
        }
    }
}
