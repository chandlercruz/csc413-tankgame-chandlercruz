package Spectankular.game.Resources;

import Spectankular.game.TRE;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, Resource> resources;

    static {
        Resource.resources = new HashMap<>();

        //adding all the resources to the HashMap
        try {
            Resource.resources.put("tank1", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank/Tank1.gif")))));
            Resource.resources.put("tank2", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank/Tank2.gif")))));
            Resource.resources.put("bullet", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank/Shell.gif")))));
            Resource.resources.put("break", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Walls/Wall2.gif")))));
            Resource.resources.put("break2", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Walls/Wall3.gif")))));
            Resource.resources.put("unbreak", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Walls/Wall1.gif")))));
            Resource.resources.put("powerup", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("PowerUps/Pickup.gif")))));
            Resource.resources.put("speed", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("PowerUps/Speed.gif")))));
            Resource.resources.put("heart", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("UserInterface/Heart.gif")))));
            Resource.resources.put("shield", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("PowerUps/Shield.gif")))));
            Resource.resources.put("shield2", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("PowerUps/Shield2.gif")))));
            Resource.resources.put("medpack", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("PowerUps/Medpack.gif")))));
            Resource.resources.put("background", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")))));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }
    }

    //returns BufferedImage of requested texture
    public static BufferedImage getResourceImage(String key) {
        BufferedImage retImg;
        if(Resource.resources.get(key) instanceof Texture) {
            retImg = ((Texture) Resource.resources.get(key)).getTexture();
        }
        else { retImg = null; }
        return retImg;
    }
}
