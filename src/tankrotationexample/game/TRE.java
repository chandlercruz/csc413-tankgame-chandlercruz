/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Launcher lf;
    static long tick = 0;

//    ArrayList<Wall> walls;
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<Tank> tanks;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.gameObjects.forEach(gameObject -> gameObject.update());
                this.tanks.forEach(tank -> tank.update());
                CollisionDetector.checkWallCollisions();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
               for (Tank tank : this.tanks) {
                   if(tank.getHealth() <= 0) {
                       this.lf.setFrame("end");
                       return;
                   }
               }
//                if(this.tick > 2000){
//                    this.lf.setFrame("end");
//                    return;
//                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.tanks.get(tanks.size()-2).setX(GameConstants.TANK_ONE_POSITION_X);
        this.tanks.get(tanks.size()-2).setY(GameConstants.TANK_ONE_POSITION_Y);

        this.tanks.get(tanks.size()-1).setX(GameConstants.TANK_TWO_POSITION_X);
        this.tanks.get(tanks.size()-1).setY(GameConstants.TANK_TWO_POSITION_Y);

        this.tanks.forEach(tank -> {
            tank.setHealth(5);
            tank.setLives(3);
        });
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        this.gameObjects = new ArrayList<>();
        this.tanks = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */

            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null) {
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {
                        case "2":
                            this.gameObjects.add(new BreakWall(curCol*32, curRow*32, Resource.getResourceImage("break")));
                            break;
                        case "3":
                        case "9":
                            this.gameObjects.add(new UnBreakWall(curCol*32, curRow*32, Resource.getResourceImage("unbreak")));
                            break;
                        case "4":
                            this.gameObjects.add(new PowerUp(curCol*32, curRow*32, Resource.getResourceImage("powerup")));

                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Tank t1 = new Tank(GameConstants.TANK_ONE_POSITION_X, GameConstants.TANK_ONE_POSITION_Y, 0, 0, 0, Resource.getResourceImage("tank1"));
        Tank t2 = new Tank(GameConstants.TANK_TWO_POSITION_X, GameConstants.TANK_TWO_POSITION_Y, 0, 0, 0, Resource.getResourceImage("tank2"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.tanks.add(t1);
        this.tanks.add(t2);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);

        this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));
        this.tanks.forEach(tank -> tank.drawImage(buffer));
        Tank t1 = (Tank) this.tanks.get(this.tanks.size()-2);
        Tank t2 = (Tank) this.tanks.get(this.tanks.size()-1);

        BufferedImage leftHalf = world.getSubimage(t1.getX()-256,t1.getY()-384,GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(t2.getX()-256,t2.getY()-384,GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(224,352,GameConstants.WORLD_WIDTH-256, GameConstants.WORLD_HEIGHT-384);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2,0,null);
        g2.scale(.10, .10);
        g2.drawImage(mm, 2112*2, 1056, null);

    }

}
