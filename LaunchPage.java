import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class LaunchPage implements ActionListener{
	
	HashMap<JButton, DrumSounds> drumMap = new HashMap<>();

	
	// Do not load audio at field initialization (may crash GUI if audio unsupported);
	// initialize lazily in actionPerformed.
	DrumSounds snareSound = null;
	DrumSounds cymbalOneSound = null;
	DrumSounds cymbalTwoSound = null;


	// this opens the window and the button for the snare and snare image
	JFrame frame = new JFrame();
	ImageIcon snareIcon = new ImageIcon(getClass().getResource("/assets/snare1.png"));
	JButton snareButton = new JButton(snareIcon);
	
	ImageIcon cymbaloneIcon = new ImageIcon(getClass().getResource("/assets/cymbal1.png"));
	JButton cymbaloneButton = new JButton(cymbaloneIcon);
	
	ImageIcon cymbaltwoIcon = new ImageIcon(getClass().getResource("/assets/cymbal2.png"));
	JButton cymbaltwoButton = new JButton(cymbaltwoIcon);

	// Debug: quick test button to verify host audio playback via generated sine wave
	JButton testSoundButton = new JButton("Test Sound");

	
	LaunchPage(){
		// If there's no display available (e.g., running inside a headless container),
		// avoid creating Swing windows to prevent HeadlessException.
		if (java.awt.GraphicsEnvironment.isHeadless()) {
			System.err.println("LaunchPage: No display found; will not create GUI (headless environment).\n" +
				"Run the program in a desktop environment or enable X11 forwarding to show the window.");
			return;
		}
		
//		DrumSounds snareSound = new DrumSounds(getClass().getResource("/assets/snaresound2.wav"));
//		DrumSounds cymbalOneSound = new DrumSounds(getClass().getResource("/assets/cymbalsound1.wav"));
//		DrumSounds cymbalTwoSound = new DrumSounds(getClass().getResource("/assets/cymbalsound2.wav"));
//
//		drumMap.put(snareButton, snareSound);
//		drumMap.put(cymbaloneButton, cymbalOneSound);
//		drumMap.put(cymbaltwoButton, cymbalTwoSound);

		
		//System.out.println(new File("assets/snaresound1.wav").getAbsolutePath());
		
		//first two coordinates set where on the screen is the button, last two set the size of the button
		snareButton.setBounds(630,500,200,200);
		snareButton.setFocusable(false);
		snareButton.addActionListener(this);
		
		frame.add(snareButton);
		
		//cymbalone (left)
		cymbaloneButton.setBounds(500,300,200,200);
		cymbaloneButton.setFocusable(false);
		cymbaloneButton.addActionListener(this);
		
		frame.add(cymbaloneButton);
		
		//cymbaltwo (right)
		cymbaltwoButton.setBounds(760,300,200,200);
		cymbaltwoButton.setFocusable(false);
		cymbaltwoButton.addActionListener(this);
				
		frame.add(cymbaltwoButton);
		// test button
		testSoundButton.setBounds(20,20,120,40);
		testSoundButton.setFocusable(false);
		testSoundButton.addActionListener(this);
		frame.add(testSoundButton);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		
//		if(e.getSource()==snareButton) {
//			
//			
//			// this opens the window once button clicked
//			NewWindow myWindow = new NewWindow();
//		}
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				if (e.getSource() == testSoundButton) {
					System.out.println("Test Sound button clicked; generating sine tone...");
					new Thread(new Runnable(){
						public void run() {
							AudioTest.playSineTone(440, 300); // Play 440Hz for 300ms
						}
					}).start();
				}
		
//		JButton source = (JButton) e.getSource();
//	    if (drumMap.containsKey(source)) {
//	        drumMap.get(source).play();

		if (e.getSource() == snareButton) {
			System.out.println("Snare button clicked!");
			if (snareSound == null) {
				URL res = getClass().getResource("/assets/snaresound2.wav");
				if (res == null) {
					try {
						java.io.File f = new java.io.File("assets/snaresound2.wav");
						if (f.exists()) {
							res = f.toURI().toURL();
							System.out.println("LaunchPage: snare resource fallback to file url=" + res);
						}
					} catch (java.net.MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("LaunchPage: snare resource URL=" + res);
				if (res != null) {
					snareSound = new DrumSounds(res);
				} else {
					System.err.println("LaunchPage: snare sound resource not found");
				}
			}
			System.out.println("LaunchPage: calling snareSound.play()");
			if (snareSound != null) snareSound.play();
		}

		if (e.getSource() == cymbaloneButton) {
			System.out.println("Cymbal one clicked!");
			if (cymbalOneSound == null) {
				URL res = getClass().getResource("/assets/snaresound1.wav");
				if (res == null) {
					try {
						java.io.File f = new java.io.File("assets/snaresound1.wav");
						if (f.exists()) {
							res = f.toURI().toURL();
							System.out.println("LaunchPage: cymbal1 resource fallback to file url=" + res);
						}
					} catch (java.net.MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("LaunchPage: cymbal1 resource URL=" + res);
				if (res != null) {
					cymbalOneSound = new DrumSounds(res);
				} else {
					System.err.println("LaunchPage: cymbal one sound resource not found");
				}
			}
			if (cymbalOneSound != null) cymbalOneSound.play();
		}

		if (e.getSource() == cymbaltwoButton) {
			System.out.println("Cymbal two clicked!");
			if (cymbalTwoSound == null) {
				URL res = getClass().getResource("/assets/snaresound1.wav");
				if (res == null) {
					try {
						java.io.File f = new java.io.File("assets/snaresound1.wav");
						if (f.exists()) {
							res = f.toURI().toURL();
							System.out.println("LaunchPage: cymbal2 resource fallback to file url=" + res);
						}
					} catch (java.net.MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("LaunchPage: cymbal2 resource URL=" + res);
				if (res != null) {
					cymbalTwoSound = new DrumSounds(res);
				} else {
					System.err.println("LaunchPage: cymbal two sound resource not found");
				}
			}
			if (cymbalTwoSound != null) cymbalTwoSound.play();
		}
	}

	
}