
import javax.swing.SwingUtilities;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

public class DrumKitFinal {
	public static void main(String[] args) {
		// Classes LaunchPage and NewWindow followed from tutorial by BroCode on youtube
		// Create the GUI on the Event Dispatch Thread
		// Print available audio mixers and support info (helps debugging why Clip isn't available)
		try {
			Mixer.Info[] mixers = AudioSystem.getMixerInfo();
			System.out.println("Available audio mixers:");
			for (Mixer.Info m : mixers) {
				System.out.println("- " + m.getName() + ": " + m.getDescription());
			}
		} catch (Exception e) {
			System.err.println("Failed to list audio mixers: " + e.getMessage());
		}

		// If running in a headless environment (no DISPLAY), print guidance and exit gracefully
		if (java.awt.GraphicsEnvironment.isHeadless()) {
			System.err.println("Cannot open GUI: running in headless environment (no DISPLAY).\n" +
					"If you are running on a Linux server or inside a container, forward X11 or run locally with a GUI.");
			return;
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LaunchPage();
			}
		});
	}
}