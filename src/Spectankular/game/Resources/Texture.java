package Spectankular.game.Resources;

import java.awt.image.BufferedImage;

public class Texture extends Resource{
    BufferedImage image;

    public Texture(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getTexture() {
        return image;
    }
}
