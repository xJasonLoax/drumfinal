import javax.sound.sampled.*;

public class AudioTest {
    public static void playSineTone(int hz, int msecs) {
        final float SAMPLE_RATE = 44100f;
        final int SAMPLE_SIZE = 2; // bytes
        final int bufferSize = (int) (SAMPLE_RATE * msecs / 1000) * SAMPLE_SIZE;

        byte[] buf = new byte[bufferSize];
        double angle = 0;
        double increment = 2.0 * Math.PI * hz / SAMPLE_RATE;
        int idx = 0;

        for (int i = 0; i < bufferSize / 2; i++) {
            short value = (short) (Math.sin(angle) * Short.MAX_VALUE);
            buf[idx++] = (byte) (value & 0xFF);
            buf[idx++] = (byte) ((value >> 8) & 0xFF);
            angle += increment;
        }

        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(format);
            sdl.start();
            sdl.write(buf, 0, buf.length);
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException e) {
            System.err.println("AudioTest: error opening SourceDataLine: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
