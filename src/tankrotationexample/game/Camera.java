package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Camera {
    private HUD hud;
    private int[] cameraPositionX = {256, 256};
    private int[] cameraPositionY = {384, 384};

    public Camera(ArrayList<Tank> tanks) {
        this.hud = new HUD();
        checkCameraPosition(tanks);
    }

    public void displayCamera(ArrayList<Tank> tanks, BufferedImage world, Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        checkCameraPosition(tanks);
        BufferedImage leftHalf = world.getSubimage(tanks.get(tanks.size()-2).getX()- cameraPositionX[0],tanks.get(tanks.size()-2).getY()- cameraPositionY[0], GameConstants.GAME_SCREEN_WIDTH/2 -2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(tanks.get(tanks.size()-1).getX()- cameraPositionX[1],tanks.get(tanks.size()-1).getY()- cameraPositionY[1],GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(0,0,GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT-32);

        g.drawImage(leftHalf,0,0,null);
        g.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2,0,null);
        hud.displayHUD(tanks, g);
        g2.scale(.1, .1);
        g.drawImage(mm, 2112*2, 1056, null);
    }

    private void checkCameraPosition(ArrayList<Tank> tanks) {
        int counter = 0;
        for (Tank tank : tanks) {
            if(tank.getX()- cameraPositionX[counter] < 0) {
                while(tank.getX()- cameraPositionX[counter] < 0) {
                    cameraPositionX[counter]--;
                }
            } else if(tank.getX()- cameraPositionX[counter] > 0 && cameraPositionX[counter] < 256){
                while(tank.getX()- cameraPositionX[counter] > 0 && cameraPositionX[counter] < 256) {
                    cameraPositionX[counter]++;
                }
            } else if(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 > GameConstants.WORLD_WIDTH) {
                while(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 > GameConstants.WORLD_WIDTH) {
                    cameraPositionX[counter]++;
                }
            } else if(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 < GameConstants.WORLD_WIDTH && cameraPositionX[counter] > 256) {
                while(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 < GameConstants.WORLD_WIDTH && cameraPositionX[counter] > 256) {
                    cameraPositionX[counter]--;
                }
            }

            if(tank.getY()- cameraPositionY[counter] < 0) {
                while(tank.getY()- cameraPositionY[counter] < 0) {
                    cameraPositionY[counter]--;
                }
            } else if(tank.getY()- cameraPositionY[counter] > 0 && cameraPositionY[counter] < 384){
                while(tank.getY()- cameraPositionY[counter] > 0 && cameraPositionY[counter] < 384) {
                    cameraPositionY[counter]++;
                }
            } else if(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT > GameConstants.WORLD_HEIGHT) {
                while(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT > GameConstants.WORLD_HEIGHT) {
                    cameraPositionY[counter]++;
                }
            } else if(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT < GameConstants.WORLD_HEIGHT && cameraPositionY[counter] > 384) {
                while(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT < GameConstants.WORLD_HEIGHT && cameraPositionY[counter] > 384) {
                    cameraPositionY[counter]--;
                }
            }
            counter++;
        }
    }
}
