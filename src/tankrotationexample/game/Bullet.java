package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet {
    int x, y, vx, vy;
    float angle;
    int R = 7;
    BufferedImage bulletImage;
    Rectangle hitBox;

    public Bullet(int x, int y, float angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        this.hitBox = new Rectangle(x,y,this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }

    public void moveForward() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void checkBorder() {
        if (x < 256) {
            x = 256;
        }
        if (x >= GameConstants.WORLD_WIDTH - 352) {
            x = GameConstants.WORLD_WIDTH - 352;
        }
        if (y < 384) {
            y = 384;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 448) {
            y = GameConstants.WORLD_HEIGHT - 448;
        }
    }

    public void update() {
        moveForward();
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
