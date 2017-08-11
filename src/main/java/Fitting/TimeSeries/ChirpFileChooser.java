package Fitting.TimeSeries;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;



public class ChirpFileChooser extends JPanel {
	
	

	JPanel panelCont = new JPanel();
	JPanel panelIntro = new JPanel();
	JFileChooser chooserA;
	String choosertitleA;
	File[] AllMovies;
	public NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
	
	public ChirpFileChooser() {
		
		final JFrame frame = new JFrame("Welcome to the Chirp Function Fitter");
		JButton Measureserial = new JButton("Input the directory of TXT files of Intensity Profiles");
		panelCont.add(panelIntro, "1");
		/* Instantiation */
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();

		panelIntro.setLayout(layout);
		/* Location */

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1.5;
		++c.gridy;
		
		
		++c.gridy;
		c.insets = new Insets(10, 10, 10, 0);
		panelIntro.add(Measureserial, c);
		panelIntro.setVisible(true);
		Measureserial.addActionListener(new MeasureserialListener(frame));
		
		frame.addWindowListener(new FrameListener(frame));
		frame.add(panelCont, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	protected class MeasureserialListener implements ActionListener {

		final Frame parent;

		public MeasureserialListener(Frame parent) {

			this.parent = parent;

		}

		public void actionPerformed(final ActionEvent arg0) {
			chooserA = new JFileChooser();

			chooserA.setCurrentDirectory(new java.io.File("."));
			chooserA.setDialogTitle(choosertitleA);
			chooserA.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//
			// disable the "All files" option.
			//
			chooserA.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Intensity Files", "txt");

			chooserA.setFileFilter(filter);
			chooserA.showOpenDialog(parent);

			AllMovies = chooserA.getSelectedFile().listFiles(new FilenameFilter() {

				public boolean accept(File pathname, String filename) {

					return (filename.endsWith(".txt") );
				}
			});

			
			new InteractiveChirpFit(AllMovies).run(null);
			
			
		}
		
		
	}
	protected class FrameListener extends WindowAdapter {
		final Frame parent;

		public FrameListener(Frame parent) {
			super();
			this.parent = parent;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			close(parent);
		}
	}
	protected final void close(final Frame parent) {
		if (parent != null)
			parent.dispose();

	}

	
	public Dimension getPreferredSize() {
		return new Dimension(800, 300);
	}
	
	
}
