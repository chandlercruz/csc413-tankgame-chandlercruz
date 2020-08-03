package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakWall extends Wall {
    int x,y;
    Rectangle hitBox;
    BufferedImage wallImage;

    public UnBreakWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        this.hitBox = new Rectangle(x,y,this.wallImage.getWidth(), this.wallImage.getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public int getState() {
        return 3;
    }

    @Override
    public void lowerState() {}

    @Override
    public void update() {}

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.wallImage,x,y,null);
    }
}
