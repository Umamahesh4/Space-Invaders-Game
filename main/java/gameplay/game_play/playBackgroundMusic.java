package gameplay.game_play;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public class playBackgroundMusic {
    public playBackgroundMusic(){


    }
    private Clip backgroundMusic;

    public void playbackgroundmusic(String filePath){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }

}


