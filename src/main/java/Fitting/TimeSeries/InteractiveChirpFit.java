package Fitting.TimeSeries;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

import chirpModels.UserChirpModel;
import ij.IJ;
import ij.ImageJ;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;
import listeners.AutoListener;
import listeners.Enablehigh;
import listeners.HighFrequencyListener;
import listeners.LowFrequencyListener;
import listeners.WidthListener;
import net.imglib2.util.Pair;
import net.imglib2.util.ValuePair;

public class InteractiveChirpFit implements PlugIn {

	public String usefolder = IJ.getDirectory("imagej");
	public String addToName = "ChirpFits";
	public final int scrollbarSize = 1000;
	public final int scrollbarSizebig = 1000;
	// steps per octave
	public static int standardSensitivity = 4;
	public int sensitivity = standardSensitivity;
	public ArrayList<Pair<Double, Double>> timeseries;
	// for scrollbars
	int FrequInt, ChirpInt, PhaseInt, BackInt;

	public boolean isDone;
	public static int MIN_SLIDER = 0;
	public static int MAX_SLIDER = 500;
	int row;
	public static double MIN_FREQU = 0.0;
	public static double MAX_FREQU = 30.0;

	public static double MIN_CHIRP = 0.0;
	public static double MAX_CHIRP = 40.0;
	public boolean enableHigh = false;
	public double Lowfrequ = 6.28 / (0.03 * 60);
	public double Highfrequ = Lowfrequ / 1.5;
	public double phase = 0;
	public double back = 0;

	public JProgressBar jpb;
	public JLabel label = new JLabel("Fitting..");
	public int Progressmin = 0;
	public int Progressmax = 100;
	public int max = Progressmax;
	public File userfile;
	Frame jFreeChartFrame;
	public NumberFormat nf;
	public XYSeriesCollection dataset;
	JFreeChart chart;
	ResultsTable rtAll;
	public File inputfile;
	public File[] inputfiles;
	public String inputdirectory;
	public JLabel inputLabelwidth;
	public TextField inputFieldwidth;

	public InteractiveChirpFit(final File[] file) {

		this.inputfiles = file;
		this.inputdirectory = file[0].getParent();

		
		nf = NumberFormat.getInstance(Locale.ENGLISH);
		nf.setMaximumFractionDigits(3);
	}

	@Override
	public void run(String arg0) {
		rtAll = new ResultsTable();
		jpb = new JProgressBar();

		Card();
	}

	public JTable table;
	public JFrame Cardframe = new JFrame("Welcome to Chirp Fits ");
	public JPanel panelCont = new JPanel();
	public JPanel panelFirst = new JPanel();

	public void Card() {

		CardLayout cl = new CardLayout();

		DefaultTableModel userTableModel = new DefaultTableModel(new Object[] { "Intensity Profiles File" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (int i = 0; i < inputfiles.length; ++i) {

			String[] currentfile = { (inputfiles[i].getName()) };
			userTableModel.addRow(currentfile);
		}

		table = new JTable(userTableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panelCont.setLayout(cl);

		panelCont.add(panelFirst, "1");

		panelFirst.setName("Chirp Fits");

		/* Instantiation */
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();

		final Scrollbar FREQU = new Scrollbar(Scrollbar.HORIZONTAL, this.FrequInt, 1, MIN_SLIDER, MAX_SLIDER + 1);

		final Scrollbar CHIRP = new Scrollbar(Scrollbar.HORIZONTAL, this.ChirpInt, 1, MIN_SLIDER, MAX_SLIDER + 1);

		final Label FREQULabel = new Label("Lower Frequency (hrs) = " + nf.format(this.Lowfrequ), Label.CENTER);
		final Label CHIRPLabel = new Label("Higher Frequency (hrs) = " + nf.format(this.Highfrequ), Label.CENTER);
		final JButton AutoFit = new JButton("Auto-Fit all files");
		inputLabelwidth = new JLabel("Enter expected peak width in hours");
		inputFieldwidth = new TextField();
		inputFieldwidth.setColumns(5);
		inputFieldwidth.setText(String.valueOf(1.5));

		Highfrequ = Lowfrequ - Float.parseFloat(inputFieldwidth.getText());

		panelFirst.setLayout(layout);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;

		++c.gridy;
		c.insets = new Insets(10, 10, 0, 50);
		panelFirst.add(scrollPane, c);

		++c.gridy;
		c.insets = new Insets(10, 10, 0, 50);
		panelFirst.add(FREQU, c);

		++c.gridy;
		c.insets = new Insets(10, 10, 0, 50);
		panelFirst.add(FREQULabel, c);

		++c.gridy;
		c.insets = new Insets(10, 10, 10, 0);
		panelFirst.add(inputLabelwidth, c);

		++c.gridy;
		c.insets = new Insets(10, 10, 10, 0);
		panelFirst.add(inputFieldwidth, c);

		++c.gridy;
		c.insets = new Insets(10, 10, 10, 0);
		panelFirst.add(AutoFit, c);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					row = target.getSelectedRow();
					// do some action if appropriate column
					if (row > 0)
						displayclicked(row);
					else
						displayclicked(0);
				}
			}
		});

		FREQU.addAdjustmentListener(new LowFrequencyListener(this, FREQULabel, FREQU));
		CHIRP.addAdjustmentListener(new HighFrequencyListener(this, CHIRPLabel, CHIRP));
		AutoFit.addActionListener(new AutoListener(this, row));
		inputFieldwidth.addTextListener(new WidthListener(this));

		Cardframe.add(panelCont, BorderLayout.CENTER);
		Cardframe.add(jpb, BorderLayout.PAGE_END);

		Cardframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Cardframe.pack();
		Cardframe.setVisible(true);
		Cardframe.pack();
	}

	public void displayclicked(final int trackindex) {

		inputfile = inputfiles[trackindex];
		inputdirectory = inputfiles[trackindex].getParent();
		timeseries = ExtractSeries.Normalize(ExtractSeries.gatherdata(inputfiles[trackindex]));

		this.dataset = new XYSeriesCollection();
		this.chart = Mainpeakfitter.makeChart(dataset, "Cell Intensity", "Timepoint", "Normalized Intensity");
		this.jFreeChartFrame = Mainpeakfitter.display(chart, new Dimension(800, 800));
		row = trackindex;
		updateCHIRP();

	}

	public void displaymuteclicked(final int trackindex) {

		inputfile = inputfiles[trackindex];
		inputdirectory = inputfiles[trackindex].getParent();
		timeseries = ExtractSeries.Normalize(ExtractSeries.gatherdata(inputfiles[trackindex]));

		this.dataset = new XYSeriesCollection();
		this.chart = Mainpeakfitter.makeChart(dataset, "Cell Intensity", "Timepoint", "Normalized Intensity");
		this.jFreeChartFrame = Mainpeakfitter.display(chart, new Dimension(800, 800));

		row = trackindex;
		updateCHIRPmute();

	}

	public void updateCHIRPmute() {

		FunctionFitter chirp = new FunctionFitter(this, timeseries, UserChirpModel.Linear, row, inputfiles.length);
		chirp.checkInput();
		chirp.setLowfrequency(2 * Math.PI / (Lowfrequ * 60));
		chirp.setHighfrequency(2 * Math.PI / (Highfrequ * 60));

		
		chirp.run();

	}

	public void updateCHIRP() {

		FunctionFitter chirp = new FunctionFitter(this, timeseries, UserChirpModel.Linear, row, inputfiles.length);
		chirp.checkInput();
		chirp.setLowfrequency(2 * Math.PI / (Lowfrequ * 60));
		chirp.setHighfrequency(2 * Math.PI / (Highfrequ * 60));
		
		chirp.execute();

	}

	public static double computeValueFromScrollbarPosition(final int scrollbarPosition, final int scrollbarMax,
			final double minValue, final double maxValue) {
		return minValue + (scrollbarPosition / (double) scrollbarMax) * (maxValue - minValue);
	}

	public static int computeScrollbarPositionFromValue(final int scrollbarMax, final double value,
			final double minValue, final double maxValue) {
		return (int) Math.round(((value - minValue) / (maxValue - minValue)) * scrollbarMax);
	}

	public int computeScrollbarPositionFromValue(final double sigma, final float min, final float max,
			final int scrollbarSize) {
		return round(((sigma - min) / (max - min)) * scrollbarSize);
	}

	public static int round(final double value) {
		return (int) (value + (0.5f * Math.signum(value)));
	}

	public static void main(String[] args) {

		new ImageJ();

		JFrame frame = new JFrame("");
		ChirpFileChooser panel = new ChirpFileChooser();

		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());

	}

}
