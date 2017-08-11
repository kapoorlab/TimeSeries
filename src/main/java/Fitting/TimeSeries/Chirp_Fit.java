package Fitting.TimeSeries;

import javax.swing.JFrame;

import ij.ImageJ;
import ij.plugin.PlugIn;


public class Chirp_Fit implements PlugIn {

	public void run(String arg) {
		
			new ImageJ();
			

			    JFrame frame = new JFrame("");
			   ChirpFileChooser panel = new ChirpFileChooser();
			  
			    frame.getContentPane().add(panel,"Center");
			    frame.setSize(panel.getPreferredSize());
			    
		}
		
	}

