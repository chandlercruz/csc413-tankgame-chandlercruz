package Spectankular.game.GameObjects.PowerUps;

import Spectankular.game.TankClasses.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Speed extends PowerUp {
    int x, y;
    int state = 1;
    Rectangle hitBox;
    BufferedImage img;

    public Speed(int x, int y, BufferedImage image) {
        super();
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    /**
     * Increases tank speed to 3
     */
    @Override
    public void poweredUp(Tank tank) {
        tank.setSpeed(3);
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
