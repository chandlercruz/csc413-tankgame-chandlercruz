package tankrotationexample.game;

import java.util.ArrayList;

public class CollisionDetector {
    public static void checkWallCollisions() {
        Tank t1 = TRE.tanks.get(TRE.tanks.size()-2);
        Tank t2 = TRE.tanks.get(TRE.tanks.size()-1);

        TRE.gameObjects.forEach(gameObject -> {
            TRE.tanks.forEach(tank -> {
                if(tank.getHitBox().intersects(gameObject.getHitBox())) {
                    if(gameObject instanceof Wall) {
                        tank.isCollided();
                    }
                    else if(gameObject instanceof PowerUp) {
                        tank.poweredUp();
                        gameObject.lowerState();

                    }
                }
            });

        });
        TRE.gameObjects.removeIf(gameObject -> (gameObject.getState()<1));
    }

    public static void checkBulletCollisions(ArrayList<Bullet> ammo){
        ammo.forEach(bullet -> {
            TRE.tanks.forEach(tank -> {
                if(tank.getHitBox().intersects(bullet.getHitBox()) && bullet.getDeadly()) {
                    tank.setHealth(tank.getHealth()-1);
                    bullet.setExists(false);
                    System.out.println( tank.toString() + " Health: " + tank.getHealth());
                }
            });

            TRE.gameObjects.forEach(gameObject -> {
                if(gameObject instanceof BreakWall && ((BreakWall) gameObject).hitBox.intersects(bullet.getHitBox())) {
                    ((BreakWall) gameObject).lowerState();
                    bullet.setExists(false);
                }
            });
        });
        TRE.gameObjects.removeIf(gameObject -> (gameObject instanceof BreakWall && ((BreakWall) gameObject).getState()<1));
    }
}
