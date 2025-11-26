import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class DrumSounds {

    private Clip clip;
    private boolean loaded = false;
    private AudioFormat format = null;

    public DrumSounds(URL url) {
        init(url);
    }



    public DrumSounds(String resourcePath) {
        // Attempt to load as a resource on the classpath
        URL url = getClass().getResource(resourcePath);
        if (url == null) {
            // Try treating it as a file path fallback
            try {
                url = new java.io.File(resourcePath).toURI().toURL();
            } catch (java.net.MalformedURLException e) {
                e.printStackTrace();
            }
        }
        // Use the URL constructor
        if (url != null) {
            init(url);
        } else {
            System.err.println("DrumSounds: Could not find resource: " + resourcePath);
        }
    }

    private void init(URL url) {
        if (url == null) return;
        try {
            System.out.println("DrumSounds: init loading URL=" + url);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url); // Use URL directly
            AudioFormat baseFormat = audioInputStream.getFormat();
            System.out.println("DrumSounds: base format: " + baseFormat);
            // Convert to PCM_SIGNED, little-endian if necessary so Clip can play it.
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
            // Debug: list mixers and check for Clip support
            try {
                javax.sound.sampled.Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
                System.out.println("DrumSounds: available mixers:");
                for (javax.sound.sampled.Mixer.Info mi : mixInfos) {
                    System.out.println(" - " + mi.getName() + ": " + mi.getDescription());
                    javax.sound.sampled.Mixer m = AudioSystem.getMixer(mi);
                    if (m.isLineSupported(new Line.Info(Clip.class))) {
                        System.out.println("   - supports Clip");
                    } else {
                        System.out.println("   - DOES NOT support Clip");
                    }
                }
            } catch (Exception e) {
                System.err.println("DrumSounds: failed to list mixers: " + e.getMessage());
            }
            clip = AudioSystem.getClip();
            clip.open(decodedStream);
            format = decodedFormat;
            loaded = true;
            System.out.println("DrumSounds: Loaded audio: " + url + " format=" + format);
            System.out.println("DrumSounds: clip frame length=" + clip.getFrameLength() + " microseconds=" + clip.getMicrosecondLength());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // On some JVMs or platforms the audio format isn't supported by the available Clip
            System.err.println("DrumSounds: Audio format not supported by Clip: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Catch-all so we don't crash the EDT when audio fails to initialize.
            System.err.println("DrumSounds: Unexpected error while initializing audio: " + e.getMessage());
            e.printStackTrace();
        }
    }



	public void play() {
        if (!loaded || clip == null) {
            System.err.println("DrumSounds: play() called but clip not loaded.");
            return;
        }
        try {
            System.out.println("DrumSounds: play() - isRunning before play=" + clip.isRunning());
            if (clip.isRunning()) {
                clip.stop(); // Stop if already playing
            }
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start(); // Play the sound
            System.out.println("DrumSounds: play() - started; isRunning after play=" + clip.isRunning());
        } catch (Exception e) {
            System.err.println("DrumSounds: Error playing clip: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
