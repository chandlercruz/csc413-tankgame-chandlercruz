package tankrotationexample.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();

        try {
            Resource.resources.put("tank1", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank1.gif"))));
            Resource.resources.put("tank2", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank2.gif"))));
            Resource.resources.put("bullet", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Shell.gif"))));
            Resource.resources.put("break", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall2.gif"))));
            Resource.resources.put("break2", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall3.gif"))));
            Resource.resources.put("unbreak", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall1.gif"))));
            Resource.resources.put("powerup", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("pickup.gif"))));;
            Resource.resources.put("background", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp"))));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }
    }

    public static BufferedImage getResourceImage(String key) {
        return Resource.resources.get(key);
    }
}
