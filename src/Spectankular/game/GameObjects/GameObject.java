package Spectankular.game.GameObjects;
import java.awt.*;

public abstract class GameObject {
    public abstract Rectangle getHitBox();
    public abstract int getState();
    public abstract void lowerState();
    public abstract void drawImage(Graphics g);
    public abstract void update();
}
