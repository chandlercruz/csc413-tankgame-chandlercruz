package Spectankular.game.DisplayClasses;

import Spectankular.game.GameObjects.PowerUps.DoublePower;
import Spectankular.game.GameObjects.PowerUps.Medpack;
import Spectankular.game.GameObjects.PowerUps.Shield;
import Spectankular.game.GameObjects.PowerUps.Speed;
import Spectankular.game.GameObjects.Walls.BreakWall;
import Spectankular.game.GameObjects.Walls.UnBreakWall;
import Spectankular.game.Resources.Resource;
import Spectankular.game.TRE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapLoader {
    /**
     * Loads in GameObjects (power ups, walls) to map
     */
    public static void loadMap() {
        try {
            InputStreamReader isr  = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null) { throw new IOException("no data in file"); }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {
                        case "2":
                            TRE.gameObjects.add(new BreakWall(curCol*32, curRow*32, Resource.getResourceImage("break")));
                            break;
                        case "3":
                        case "9":
                            TRE.gameObjects.add(new UnBreakWall(curCol*32, curRow*32, Resource.getResourceImage("unbreak")));
                            break;
                        case "4":
                            TRE.gameObjects.add(new DoublePower(curCol*32, curRow*32, Resource.getResourceImage("powerup")));
                            break;
                        case "5":
                            TRE.gameObjects.add(new Speed(curCol*32, curRow*32, Resource.getResourceImage("speed")));
                            break;
                        case "6":
                            TRE.gameObjects.add(new Medpack(curCol*32, curRow*32, Resource.getResourceImage("medpack")));
                            break;
                        case "7":
                            TRE.gameObjects.add(new Shield(curCol*32, curRow*32, Resource.getResourceImage("shield")));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
