package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class HUD {
    public static void displayHUD(ArrayList<Tank> tanks, Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        for(int i=0;i<tanks.get(tanks.size()-2).getLives();i++){
            g2d.drawImage(Resource.getResourceImage("heart"),(i*40),0,null);
        }
        for(int i=0;i<tanks.get(tanks.size()-1).getLives();i++){
            g2d.drawImage(Resource.getResourceImage("heart"),(GameConstants.GAME_SCREEN_WIDTH/2+i*40),0,null);
        }
    }
}
