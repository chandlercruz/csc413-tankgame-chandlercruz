package tankrotationexample.game.Resources;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    AudioInputStream sound;

    public Sound(AudioInputStream sound) {
        this.sound = sound;
    }

//    public static synchronized void playSound(final String url) {
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Clip clip = AudioSystem.getClip();
//                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
//                            Resource
//                    );
//                    clip.open(inputStream);
//                    clip.start();
//                } catch (Exception e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//        }).start();
//    }
}
