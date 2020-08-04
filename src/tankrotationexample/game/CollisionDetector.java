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
                        ((PowerUp) gameObject).poweredUp(tank);
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
                    tank.lowerState();
                    bullet.setExists(false);
                    System.out.println( tank.toString() + " Health: " + tank.getHealth() + " Lives: " + tank.getLives());
                }
            });

            TRE.gameObjects.forEach(gameObject -> {
                if((gameObject instanceof Wall) && gameObject.getHitBox().intersects(bullet.getHitBox())) {
                    gameObject.lowerState();
                    bullet.setExists(false);
                }
            });
        });
        TRE.gameObjects.removeIf(gameObject -> (gameObject instanceof BreakWall && gameObject.getState()<1));
    }
}
