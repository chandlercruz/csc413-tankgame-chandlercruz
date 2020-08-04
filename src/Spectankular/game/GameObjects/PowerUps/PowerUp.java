package Spectankular.game.GameObjects.PowerUps;

import Spectankular.game.GameObjects.GameObject;
import Spectankular.game.TankClasses.Tank;

public abstract class PowerUp extends GameObject {
    /**
     * Function which gives tank intended effect
     * based off the power up type
     */
    public abstract void poweredUp(Tank tank);

    @Override
    public void update() {}
}
