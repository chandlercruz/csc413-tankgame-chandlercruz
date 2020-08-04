package tankrotationexample.game.Resources;

import tankrotationexample.game.TRE;

import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, Resource> resources;


    static {
        Resource.resources = new HashMap<>();

        try {
            Resource.resources.put("tank1", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank1.gif")))));
            Resource.resources.put("tank2", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank2.gif")))));
            Resource.resources.put("bullet", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Shell.gif")))));
            Resource.resources.put("break", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall2.gif")))));
            Resource.resources.put("break2", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall3.gif")))));
            Resource.resources.put("unbreak", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall1.gif")))));
            Resource.resources.put("powerup", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("pickup.gif")))));
            Resource.resources.put("speed", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Speed.gif")))));
            Resource.resources.put("heart", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Heart.gif")))));
            Resource.resources.put("shield", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Shield.gif")))));
            Resource.resources.put("shield2", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Shield2.gif")))));
            Resource.resources.put("medpack", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Medpack.gif")))));
            Resource.resources.put("background", new Texture(read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")))));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }
    }

    public static BufferedImage getResourceImage(String key) {
        BufferedImage retImg;
        if(Resource.resources.get(key) instanceof Texture) {
            retImg = ((Texture) Resource.resources.get(key)).getTexture();
        }
        else { retImg = null; }
        return retImg;
    }
}
