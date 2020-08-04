package Spectankular.game.DisplayClasses;

import Spectankular.GameConstants;
import Spectankular.game.TankClasses.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Camera {
    private HUD hud;

    //Stores camera offset in each axis
    private int[] cameraPositionX = {256, 256};
    private int[] cameraPositionY = {384, 384};

    public Camera(ArrayList<Tank> tanks) {
        this.hud = new HUD();

        //Repositions camera based off tanks' spawn points
        checkCameraPosition(tanks);
    }

    public void displayCamera(ArrayList<Tank> tanks, BufferedImage world, Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        checkCameraPosition(tanks);
        BufferedImage leftHalf = world.getSubimage(tanks.get(tanks.size()-2).getX()- cameraPositionX[0],tanks.get(tanks.size()-2).getY()- cameraPositionY[0], GameConstants.GAME_SCREEN_WIDTH/2 -2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(tanks.get(tanks.size()-1).getX()- cameraPositionX[1],tanks.get(tanks.size()-1).getY()- cameraPositionY[1],GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(0,0,GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT-32); //minimap

        g.drawImage(leftHalf,0,0,null);
        g.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2,0,null);
        hud.displayHUD(tanks, g);
        g2.scale(.1, .1);
        g.drawImage(mm, 2112*2, 1056, null);
    }

    /**
     * Adjusts offset for camera based off if the camera would extend
     * off the side of the map, and then it reverts the offset to normal if
     * the tank moves away from the edge of the map.
     */
    private void checkCameraPosition(ArrayList<Tank> tanks) {
        int counter = 0;
        for (Tank tank : tanks) { //Iterates through ArrayList for both tanks
            if(tank.getX()- cameraPositionX[counter] < 0) { //if the player hits the left wall, camera adjusts
                while(tank.getX()- cameraPositionX[counter] < 0) {
                    cameraPositionX[counter]--;
                }
            } else if(tank.getX()- cameraPositionX[counter] > 0 && cameraPositionX[counter] < 256){ //if player leaves left wall, camera adjusts
                while(tank.getX()- cameraPositionX[counter] > 0 && cameraPositionX[counter] < 256) {
                    cameraPositionX[counter]++;
                }
            } else if(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 > GameConstants.WORLD_WIDTH) { //if player hits right wall, camera adjusts
                while(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 > GameConstants.WORLD_WIDTH) {
                    cameraPositionX[counter]++;
                }
            } else if(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 < GameConstants.WORLD_WIDTH && cameraPositionX[counter] > 256) { //if player leaves right wall, camera adjusts
                while(tank.getX()- cameraPositionX[counter] +GameConstants.GAME_SCREEN_WIDTH/2 < GameConstants.WORLD_WIDTH && cameraPositionX[counter] > 256) {
                    cameraPositionX[counter]--;
                }
            }

            if(tank.getY()- cameraPositionY[counter] < 0) { //if player hits top wall, camera adjusts
                while(tank.getY()- cameraPositionY[counter] < 0) {
                    cameraPositionY[counter]--;
                }
            } else if(tank.getY()- cameraPositionY[counter] > 0 && cameraPositionY[counter] < 384){ //if player leaves top wall, camera adjusts
                while(tank.getY()- cameraPositionY[counter] > 0 && cameraPositionY[counter] < 384) {
                    cameraPositionY[counter]++;
                }
            } else if(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT > GameConstants.WORLD_HEIGHT) { //if player hits bottom wall, camera adjusts
                while(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT > GameConstants.WORLD_HEIGHT) {
                    cameraPositionY[counter]++;
                }
            } else if(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT < GameConstants.WORLD_HEIGHT && cameraPositionY[counter] > 384) { //if player leaves bottom wall, camera adjusts
                while(tank.getY()- cameraPositionY[counter] +GameConstants.GAME_SCREEN_HEIGHT < GameConstants.WORLD_HEIGHT && cameraPositionY[counter] > 384) {
                    cameraPositionY[counter]--;
                }
            }
            counter++;
        }
    }
}
