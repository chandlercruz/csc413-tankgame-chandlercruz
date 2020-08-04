package tankrotationexample.game;

import tankrotationexample.GameConstants;

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

    public int[] moveForwards(int x, int y, int vx, int vy, boolean isCollided) {
        x += vx;
        y += vy;
        this.directionCheck(x, y);
        if(isCollided) {
            System.out.println("Colliding Forwards " + " x=" + x + ", y=" + y + ", vx =" + vx + ", vy=" + vy);
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

    public int[] moveBackwards(int x, int y, int vx, int vy, boolean isCollided) {
        x -= vx;
        y -= vy;
        this.directionCheck(x, y);
        if(isCollided) {
            System.out.println("Colliding Backwards " + " x=" + x + ", y=" + y + ", vx =" + vx + ", vy=" + vy);

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

    private boolean hitBorder(int x, int y) {
        if (x < 256 || y < 384 || x >= GameConstants.WORLD_WIDTH - 352 || y >= GameConstants.WORLD_HEIGHT - 448) {
            return true;
        }
        else { return false;}
    }
}
