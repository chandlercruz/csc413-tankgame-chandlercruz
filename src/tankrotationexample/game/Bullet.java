package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    int x, y, vx, vy;
    float angle;
    int R = 7;
    BufferedImage bulletImage;
    Rectangle hitBox;
    boolean exists = true;
    int state = 1;
    boolean deadly = false;
    int counter = 0;

    public Bullet(int x, int y, float angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        this.hitBox = new Rectangle(x,y,this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }

    @Override
    public void lowerState(){state--;}

    @Override
    public int getState() {return state;}

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void moveForward() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitBox.setLocation(x, y);
        counter++;
        if(counter > 20) {deadly = true;}
        checkBorder();
    }

    public boolean doesExist() {return exists;}
    public boolean getDeadly() {return deadly;}
    public void setExists(boolean exists) {this.exists = exists;}

    public void checkBorder() {
        if (x < 32 || y < 32 || x >= GameConstants.WORLD_WIDTH - 96 || y >= GameConstants.WORLD_HEIGHT - 128) {
            exists = false;
        }
    }

    public void update() {
        if(exists) moveForward();
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bulletImage.getWidth() / 2.0, this.bulletImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage, rotation, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(x,y,this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }
}
