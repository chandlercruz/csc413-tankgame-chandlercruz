package Spectankular.game.GameObjects.Walls;

import Spectankular.game.GameObjects.GameObject;

import java.awt.*;

public abstract class Wall extends GameObject {
    public abstract void drawImage(Graphics g);
    public abstract void update();
}
