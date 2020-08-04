package tankrotationexample.game;



import tankrotationexample.GameConstants;
import tankrotationexample.game.Resources.Resource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{

    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private int initialX;
    private int initialY;

    private int R = 2;
    private final float ROTATIONSPEED = 3.0f;
    private Rectangle hitBox;
    private ArrayList<Bullet> ammo;
    private int lives = 3;
    private int health = 5;


    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean ShootReady;

    private boolean doublePower = false;
    private boolean shield = false;
    private int shieldCounter = 0;

    private boolean isCollided;
    private TankMovement tankMove;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
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

    void setX(int x){ this.x = x; }
    void setY(int y) { this. y = y;}
    int getX(){return x;}
    int getY(){return y;}

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }
    public int getHealth() { return health; }
    public int getLives() { return lives;}
    public void setHealth(int health) { this.health = health; }
    public void setLives(int lives) { this.lives = lives; }
    public void setDoublePower(boolean bool) {
        doublePower = bool;
    }
    public void setSpeed(int speed) { this.R = speed; }
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
            System.out.println("shot bullet");
            ShootReady = false;
            Bullet b = new Bullet(x,y,angle, Resource.getResourceImage("bullet"));
            this.ammo.add(b);
            if(doublePower) {
                b = new Bullet(x,y,angle, Resource.getResourceImage("bullet"));
                this.ammo.add(b);
            }
        }
        this.ammo.forEach(bullet -> bullet.update());
        CollisionDetector.checkBulletCollisions(ammo);
        this.ammo.removeIf(bullet -> !bullet.doesExist());

        if(shieldCounter > 1000) {shield = false;}
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void move(boolean forwards) {
        int[] newCoord;
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        if(forwards) {   //If the tank is moving forwards
            newCoord = tankMove.moveForwards(x, y, vx, vy, isCollided);
        }
        else {  //If the tank is moving backwards
            newCoord = tankMove.moveBackwards(x, y, vx, vy, isCollided);
        }
        this.setX(newCoord[0]);
        this.setY(newCoord[1]);
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

    public void nextLife() {
        lives--;
        setHealth(5);
        this.setX(initialX);
        this.setY(initialY);
        this.hitBox.setLocation(initialX,initialY);
    }

    public void isCollided() {

        isCollided = true;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", vx =" + vx + ", vy=" + vy + ", angle=" + angle;
    }

    @Override
    public int getState() {return 1;}

    @Override
    public void lowerState() { if(!shield) { health--;} }


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
//        g2d.rotate(Math.toRadians(angle));
    }



}
