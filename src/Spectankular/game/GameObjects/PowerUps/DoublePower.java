package Spectankular.game.GameObjects.PowerUps;

import Spectankular.game.TankClasses.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DoublePower extends PowerUp {
    int x, y;
    int state = 1;
    Rectangle hitBox;
    BufferedImage img;

    public DoublePower(int x, int y, BufferedImage image) {
        super();
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    /**
     * Tank can fire 2 bullets at once with this,
     * effectively giving it double the power
     */
    @Override
    public void poweredUp(Tank tank) {
        tank.setDoublePower(true);
    }

    @Override
    public void lowerState() {state--;}

    @Override
    public int getState() {return state;}

    @Override
    public Rectangle getHitBox() {return hitBox.getBounds();}

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.img,x,y,null);
    }
}
