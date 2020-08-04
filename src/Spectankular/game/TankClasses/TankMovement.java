package Spectankular.game.TankClasses;

import Spectankular.GameConstants;

public class TankMovement {
    private int prevX;
    private int prevY;

    private boolean movingUp;
    private boolean movingLeft;
    private boolean movingStraightX;
    private boolean movingStraightY;
    private boolean stuck;

    public TankMovement(int x, int y) {
        this.prevX = x;
        this.prevY = y;
    }

    /**
     * Checks what direction tank is moving in based off prior
     * coordinates.
     */
    private void directionCheck(int x, int y) {
        if((prevX - x) > 0) { movingLeft = true; }
        else {movingLeft = false; }

        if((prevY - y) > 0) { movingUp = true; }
        else {movingUp = false; }

        if(prevX == x) {movingStraightX = true;}
        else {movingStraightX = false;}
        if(prevY == y) {movingStraightY = true;}
        else {movingStraightY = false;}

        prevX = x;
        prevY = y;
    }

    /**
     * Moves the tank forward or backwards, and if the tank is colliding with a wall
     * it repositions the tank based off:
     *      1. Direction tank is hitting the wall
     *      2. If the tank is supposed to moving in a certain direction but isn't
     *      3. If the tank is at a corner of a wall and the border
     */
    public int[] move(int x, int y, int vx, int vy, boolean isCollided, boolean forwards) {
        if(forwards) {
            x += vx;
            y += vy;
            this.directionCheck(x, y);
            if(isCollided) {
                if(vx >= 0 && vy >= 0) {
                    if((movingUp || movingLeft) && !hitBorder(x, y)) {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    } else{
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else if(vx > 0 && vy < 0) {
                    if((!movingUp || movingLeft) && !hitBorder(x, y)) {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
                else if(vx < 0 && vy > 0) {
                    if((movingUp || !movingLeft) && !hitBorder(x, y)) {
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else {
                    if((!movingUp || !movingLeft) && !hitBorder(x, y)) {
                        x -= (vx + GameConstants.COLLISION_OFFSET);
                        y -= (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x -= (vx - GameConstants.COLLISION_OFFSET);
                        y -= (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
            }

            int[] moveDistance = {x, y};
            return moveDistance;
        }
        else {
            x -= vx;
            y -= vy;
            this.directionCheck(x, y);
            if(isCollided) {
                if(vx >= 0 && vy >= 0) {
                    if((!movingUp || !movingLeft) && !hitBorder(x, y)) {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                }
                else if((vx > 0 && vy < 0) && !hitBorder(x, y)) {
                    if(movingUp || !movingLeft) {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
                else if((vx < 0 && vy > 0) && !hitBorder(x, y)) {
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
                    if((movingUp || movingLeft) && !hitBorder(x, y)) {
                        x += (vx + GameConstants.COLLISION_OFFSET);
                        y += (vy + GameConstants.COLLISION_OFFSET);
                    }
                    else {
                        x += (vx - GameConstants.COLLISION_OFFSET);
                        y += (vy - GameConstants.COLLISION_OFFSET);
                    }
                }
            }

            int[] moveDistance = {x, y};
            return moveDistance;
        }
    }

    /**
     * Checks if the tank is at the border
     */
    private boolean hitBorder(int x, int y) {
        if (x < 256 || y < 384 || x >= GameConstants.WORLD_WIDTH - 352 || y >= GameConstants.WORLD_HEIGHT - 448) {
            return true;
        }
        else { return false;}
    }
}
