package tankrotationexample.game;

import tankrotationexample.game.Resources.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall{
    int x,y;
    int state = 2;
    Rectangle hitBox;
    BufferedImage wallImage;

    public BreakWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        this.hitBox = new Rectangle(x,y,this.wallImage.getWidth(), this.wallImage.getHeight());
    }

    @Override
    public void lowerState() {state--;}

    @Override
    public int getState() {return state;}

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void update() {
        if(state == 1) {
            wallImage = Resource.getResourceImage("break2");
        }
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.wallImage,x,y,null);
    }
}
