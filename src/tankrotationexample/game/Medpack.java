package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Medpack extends PowerUp {
    int x, y;
    int state = 1;
    Rectangle hitBox;
    BufferedImage img;

    public Medpack(int x, int y, BufferedImage image) {
        super();
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void poweredUp(Tank tank) {
        if(tank.getHealth() < 5) {
            tank.setHealth(tank.getHealth()+1);
        }
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
