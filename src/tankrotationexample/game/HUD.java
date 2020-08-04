package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.game.Resources.Resource;

import java.awt.*;
import java.util.ArrayList;

public class HUD {
    public void displayHUD(ArrayList<Tank> tanks, Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.GREEN);

        int counter = 0;
        for (Tank tank : tanks) {
            for(int i=0;i<tank.getLives();i++) {
                g2d.drawImage(Resource.getResourceImage("heart"),(counter*GameConstants.GAME_SCREEN_WIDTH/2) + (i*40),0,null);
            }
            for(int i=0;i<tank.getHealth();i++) {
                g2d.fillRect((counter*GameConstants.GAME_SCREEN_WIDTH/2) + 130+i*20, 0, 20, 32);
            }
            counter++;
        }


    }
}
