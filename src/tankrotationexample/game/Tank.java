package tankrotationexample.game;



import tankrotationexample.GameConstants;

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

    private final int R = 2;
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

    private boolean isCollided;
    private boolean movingUp;
    private boolean movingLeft;
    private boolean movingStraightX;
    private boolean movingStraightY;
    private int prevX;
    private int prevY;


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

        this.prevX = x;
        this.prevY = y;
        this.initialX = x;
        this.initialY = y;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    void setX(int x){ this.x = x; }

    void setY(int y) { this. y = y;}

    int getX(){return x;}
    int getY(){return y;}

    public int getHealth() { return health; }
    public int getLives() { return lives;}
    public void setHealth(int health) { this.health = health; }
    public void setLives(int lives) { this.lives = lives; }

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

    void unToggleShootReady(){ this.ShootReady = false; }

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
            if(doublePower) {
                b = new Bullet(x,y,angle, Resource.getResourceImage("bullet"));
                this.ammo.add(b);
            }
        }
        this.ammo.forEach(bullet -> bullet.update());
        CollisionDetector.checkBulletCollisions(ammo);
        this.ammo.removeIf(bullet -> !bullet.doesExist());
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void directionCheck(int x, int y) {
        if((prevX - x) > 0) { movingLeft = true; }
        else {movingLeft = false; }

        if((prevY - y) > 0) { movingUp = true; }
        else {movingUp = false; }

        if(prevX == x) {movingStraightX = true;}
        else {movingStraightX = false;}
        if(prevY == y) {movingStraightY = true;}
        else {movingStraightY = false;}

        System.out.println("moving up =" + movingUp + ", moving left =" + movingLeft);
        prevX = x;
        prevY = y;
    }

    private void move(boolean forwards) {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        if(forwards) {   //If the tank is moving forwards
            x += vx;
            y += vy;
            this.directionCheck(x, y);
            if(isCollided) {
                System.out.println("Colliding Forwards " + this.toString());
                if(vx >= 0 && vy >= 0) {
                    if(movingUp || movingLeft) {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    } else{
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else if(vx > 0 && vy < 0) {
                    if(!movingUp || movingLeft) {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
                else if(vx < 0 && vy > 0) {
                    if(movingUp || !movingLeft) {
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else {
                    if(!movingUp || !movingLeft) {
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
            }
        }
        else {  //If the tank is moving backwards
            x -= vx;
            y -= vy;
            this.directionCheck(x, y);
            if(isCollided) {
                System.out.println("Colliding Backwards " + this.toString());

                if(vx >= 0 && vy >= 0) {
                    if(!movingUp || !movingLeft) {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else if(vx > 0 && vy < 0) {
                    if(movingUp || !movingLeft) {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
                else if(vx < 0 && vy > 0) {
                    if(!movingUp || movingLeft) {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else {
                    if(movingUp || movingLeft) {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
            }
        }
        checkBorder();
        this.hitBox.setLocation(x,y);
        isCollided = false;
    }

    private void checkBorder() {
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

    public void nextLife() {
        lives--;
        setHealth(5);
        this.setX(initialX);
        this.setY(initialY);
    }

    public void isCollided() {

        isCollided = true;
    }

    public void poweredUp() {
        doublePower = true;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", vx =" + vx + ", vy=" + vy + ", angle=" + angle;
    }

    @Override
    public int getState() {return 1;}

    @Override
    public void lowerState() {health--;}


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        this.ammo.forEach(bullet -> bullet.drawImage(g));

        g2d.setColor(Color.RED);
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
//        g2d.rotate(Math.toRadians(angle));
    }



}
