import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class LaunchPage implements ActionListener{
	
	HashMap<JButton, DrumSounds> drumMap = new HashMap<>();

	
	DrumSounds snareSound = new DrumSounds("/assets/snaresound2.wav");


	// this opens the window and the button for the snare and snare image
	JFrame frame = new JFrame();
	ImageIcon snareIcon = new ImageIcon(getClass().getResource("/assets/snare1.png"));
	JButton snareButton = new JButton(snareIcon);
	
	ImageIcon cymbaloneIcon = new ImageIcon(getClass().getResource("/assets/cymbal1.png"));
	JButton cymbaloneButton = new JButton(cymbaloneIcon);
	
	ImageIcon cymbaltwoIcon = new ImageIcon(getClass().getResource("/assets/cymbal2.png"));
	JButton cymbaltwoButton = new JButton(cymbaltwoIcon);

	
	LaunchPage(){
		
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
		
//		JButton source = (JButton) e.getSource();
//	    if (drumMap.containsKey(source)) {
//	        drumMap.get(source).play();

	    if (e.getSource() == snareButton) {
	    	System.out.println("Snare button clicked!");

	        snareSound.play(); // Play snare sound
	    }
	}

	
}