/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spectankular.game;


import Spectankular.GameConstants;
import Spectankular.Launcher;
import Spectankular.game.DisplayClasses.MapLoader;
import Spectankular.game.GameObjects.GameObject;
import Spectankular.game.Resources.Resource;
import Spectankular.game.DisplayClasses.Camera;
import Spectankular.game.TankClasses.Tank;
import Spectankular.game.TankClasses.TankControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import static javax.imageio.ImageIO.read;

/**
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Launcher lf;
    private Camera camera;
    static long tick = 0;

    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<Tank> tanks;

    public TRE(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                this.gameObjects.forEach(gameObject -> gameObject.update()); //update stationary game objects
                this.tanks.forEach(tank -> tank.update());  //update tanks' movements
                CollisionDetector.checkWallCollisions(); //check for any collisions after updates
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds

                //Check to see if someone has won
                for (Tank tank : this.tanks) {
                    if (tank.getState() <= 0) {
                        if (tank.getLives() > 1) {
                            tank.nextLife();

                        } else {
                            this.lf.setFrame("end");
                            return;
                        }
                    }
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.tanks.get(tanks.size() - 2).setX(GameConstants.TANK_ONE_POSITION_X);
        this.tanks.get(tanks.size() - 2).setY(GameConstants.TANK_ONE_POSITION_Y);

        this.tanks.get(tanks.size() - 1).setX(GameConstants.TANK_TWO_POSITION_X);
        this.tanks.get(tanks.size() - 1).setY(GameConstants.TANK_TWO_POSITION_Y);

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
        MapLoader.loadMap();

        Tank t1 = new Tank(GameConstants.TANK_ONE_POSITION_X, GameConstants.TANK_ONE_POSITION_Y, 0, 0, 0, Resource.getResourceImage("tank1"));
        Tank t2 = new Tank(GameConstants.TANK_TWO_POSITION_X, GameConstants.TANK_TWO_POSITION_Y, 0, 0, 0, Resource.getResourceImage("tank2"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.tanks.add(t1);
        this.tanks.add(t2);
        this.camera = new Camera(tanks);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        //Drawing the background images
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                buffer.drawImage(Resource.getResourceImage("background"), i * 320, j * 240, null);
            }
        }
        this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));
        this.tanks.forEach(tank -> tank.drawImage(buffer));
        camera.displayCamera(tanks, world, g2);

    }

}
