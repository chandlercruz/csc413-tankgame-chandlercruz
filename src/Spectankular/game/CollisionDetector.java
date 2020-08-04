package Spectankular.game;

import Spectankular.game.GameObjects.Bullet;
import Spectankular.game.GameObjects.PowerUps.PowerUp;
import Spectankular.game.GameObjects.Walls.BreakWall;
import Spectankular.game.GameObjects.Walls.Wall;
import Spectankular.game.TankClasses.Tank;

import java.util.ArrayList;

public class CollisionDetector {
    /**
     * Checks to see if any tanks have collided with walls and
     * then removes any power ups that the player may have
     * picked up
     */
    public static void checkWallCollisions() {
        Tank t1 = TRE.tanks.get(TRE.tanks.size()-2);
        Tank t2 = TRE.tanks.get(TRE.tanks.size()-1);

        //Checking if any tanks have collided with walls
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

        //Removing any obtained power ups from the map
        TRE.gameObjects.removeIf(gameObject -> (gameObject.getState()<1));
    }

    /**
     * Checks to see if any bullets have collided with tanks or walls.
     * Power ups do not affect bullets. Then it lowers the state (health) of
     * any tanks or breakable walls that have been hit by bullets and removes
     * those bullets and broken walls from the map.
     * @param ammo
     */
    public static void checkBulletCollisions(ArrayList<Bullet> ammo){
        ammo.forEach(bullet -> {
            //Checking if any bullets have collided with tanks
            TRE.tanks.forEach(tank -> {
                if(tank.getHitBox().intersects(bullet.getHitBox()) && bullet.getDeadly()) {
                    tank.lowerState();
                    bullet.setExists(false);
                }
            });

            //Checking if any bullets have collided with Walls and lowers the state (health) if it's breakable
            TRE.gameObjects.forEach(gameObject -> {
                if((gameObject instanceof Wall) && gameObject.getHitBox().intersects(bullet.getHitBox())) {
                    gameObject.lowerState();
                    bullet.setExists(false);
                }
            });
        });

        //Removing any broken walls and bullets from the map
        TRE.gameObjects.removeIf(gameObject -> (gameObject instanceof BreakWall && gameObject.getState()<1));
    }
}
