package Spectankular.game.TankClasses;



import Spectankular.GameConstants;
import Spectankular.game.CollisionDetector;
import Spectankular.game.GameObjects.Bullet;
import Spectankular.game.GameObjects.GameObject;
import Spectankular.game.Resources.Resource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {

    //Variables for tank's position/movement
    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private int initialX;
    private int initialY;
    private boolean isCollided;
    private TankMovement tankMove;

    private int r = 2;
    private final float ROTATIONSPEED = 3.0f;
    private Rectangle hitBox;
    private ArrayList<Bullet> ammo;
    private int lives = 3;
    private int health = 5;

    //Variables for controls
    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean ShootReady;

    //Variables for power up states
    private boolean doublePower = false;
    private boolean shield = false;
    private int shieldCounter = 0;

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        this.ammo = new ArrayList<>();
        ShootReady = true;

        this.initialX = x;
        this.initialY = y;
        this.tankMove = new TankMovement(x, y);
    }

    //Setters and getters
    public void setX(int x){ this.x = x; }
    public void setY(int y) { this. y = y;}
    public int getX(){return x;}
    public int getY(){return y;}

    public void isCollided() {
        isCollided = true;
    }
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }
    @Override
    public int getState() { return health; }
    @Override
    public void lowerState() { if(!shield) { health--;} }
    public int getLives() { return lives;}
    public void setHealth(int health) { this.health = health; }
    public void setLives(int lives) { this.lives = lives; }
    public void setDoublePower(boolean bool) {
        doublePower = bool;
    }
    public void setSpeed(int speed) { this.r = speed; }
    public void setShield() {
        shield = true;
        shieldCounter = 0;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void toggleShootReady() { this.ShootReady = true; }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    @Override
    public void update() {
        if (this.UpPressed) {
            this.move(true);
        }
        if (this.DownPressed) {
            this.move(false);
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && ShootReady == true) {
            ShootReady = false;
            Bullet b = new Bullet(x,y,angle, Resource.getResourceImage("bullet"));
            this.ammo.add(b);
            //If Double Power Up has been obtained, 2 bullets are sent out with each shot
            if(doublePower) {
                b = new Bullet(x,y,angle, Resource.getResourceImage("bullet"));
                this.ammo.add(b);
            }
        }
        //Updates movement for each bullet fired by tank
        this.ammo.forEach(bullet -> bullet.update());
        //Checks if bullets have hit anything after update
        CollisionDetector.checkBulletCollisions(ammo);
        //Removes bullets that have collided from the map
        this.ammo.removeIf(bullet -> !bullet.doesExist());

        //Timer for shield
        if(shieldCounter > 1000) {shield = false;}
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void move(boolean forwards) {
        int[] newCoordinates;
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        newCoordinates = tankMove.move(x, y, vx, vy, isCollided, forwards);
        this.setX(newCoordinates[0]);
        this.setY(newCoordinates[1]);
        checkBorder();
        this.hitBox.setLocation(x,y);
        isCollided = false;
    }

    private void checkBorder() {
        if (x < 32) {
            x = 32;
        }
        if (x >= GameConstants.WORLD_WIDTH - 96) {
            x = GameConstants.WORLD_WIDTH - 96;
        }
        if (y < 32) {
            y = 32;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 128) {
            y = GameConstants.WORLD_HEIGHT - 128;
        }
    }

    /**
     * Resets tank health and position after death
     */
    public void nextLife() {
        lives--;
        setHealth(5);
        this.setX(initialX);
        this.setY(initialY);
        this.hitBox.setLocation(initialX,initialY);
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", vx =" + vx + ", vy=" + vy + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        if(shield) {
            g2d.drawImage(Resource.getResourceImage("shield2"), x, y, null);
            shieldCounter++;
        }

        this.ammo.forEach(bullet -> bullet.drawImage(g));
        g2d.setColor(Color.RED);
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
    }



}
