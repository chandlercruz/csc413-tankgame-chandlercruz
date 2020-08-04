package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {
    public abstract void poweredUp(Tank tank);

    @Override
    public void update() {}
}
