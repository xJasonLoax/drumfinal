import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class DrumSounds {

    private Clip clip;

    public DrumSounds(URL url) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url); //Use URL directly
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }



	public DrumSounds(String string) {
		// TODO Auto-generated constructor stub
	}



	public void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Stop if already playing
            }
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start(); // Play the sound
        }
    }

}
