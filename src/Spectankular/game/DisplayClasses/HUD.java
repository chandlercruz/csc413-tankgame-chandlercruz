package Spectankular.game.DisplayClasses;

import Spectankular.GameConstants;
import Spectankular.game.Resources.Resource;
import Spectankular.game.TankClasses.Tank;

import java.awt.*;
import java.util.ArrayList;

public class HUD {
    public void displayHUD(ArrayList<Tank> tanks, Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.GREEN);

        int counter = 0;
        for (Tank tank : tanks) {
            //Draws hearts for # of lives
            for(int i=0;i<tank.getLives();i++) {
                g2d.drawImage(Resource.getResourceImage("heart"),(counter*GameConstants.GAME_SCREEN_WIDTH/2) + (i*40),0,null);
            }

            //Draws health bar
            for(int i = 0; i<tank.getState(); i++) {
                g2d.fillRect((counter*GameConstants.GAME_SCREEN_WIDTH/2) + 130+i*20, 0, 40, 32);
            }
            counter++;
        }


    }
}
