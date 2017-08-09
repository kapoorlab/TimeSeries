package Fitting.TimeSeries;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import chirpModels.UserChirpModel;
import net.imglib2.util.Pair;
import net.imglib2.util.ValuePair;

public class Mainpeakfitter {

	
	
	public static void main(String[] args){
		
		final ArrayList< Pair< Double, Double > > untimeseries = ExtractSeries.gatherdata(new File("/Users/varunkapoor/Documents/Ines_Fourier/FFT_Varun_Exp1All/Exp1_cell25.txt"));
		
		final ArrayList< Pair< Double, Double > > timeseries = ExtractSeries.Normalize(untimeseries);
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		
		int totaltime = timeseries.size();
		
		dataset.addSeries( drawPoints( timeseries ) );
		final JFreeChart chart = makeChart( dataset );

		
	
		
		FunctionFitter chirp = new FunctionFitter(timeseries, UserChirpModel.Linear);
		chirp.checkInput();
		chirp.process();
		double[] fitparams = chirp.getResult();
		
		double poly;
		final ArrayList< Pair< Double, Double > > fitpoly = new ArrayList< Pair< Double, Double > >();
		for (int i = 0; i < timeseries.size(); ++i){
			
			Double time = timeseries.get(i).getA();
			
			poly = fitparams[i] * Math.cos(
					fitparams[totaltime] * time + (fitparams[totaltime + 1] - fitparams[totaltime + 2]) * time * time / (2 * totaltime) + fitparams[totaltime + 3]) + fitparams[totaltime + 4];
			
			fitpoly.add(new ValuePair<Double, Double> (time, poly));
		}
		
		
		dataset.addSeries(drawPoints(fitpoly, "Fits"));
		
		setColor( chart, 0, new Color( 64, 64, 64 ) );
		setStroke( chart, 0, 1f );
		
		
		display( chart, new Dimension( 500, 400 ) );
	}
	
	public static XYSeries drawPoints( final List< Pair< Double, Double > > timeseries ) { return drawPoints( timeseries, "Intensity vs Time" ); }
	public static XYSeries drawPoints( final List< Pair< Double, Double > > timeseries, final String name )
	{
		XYSeries series = new XYSeries( name );

		if (timeseries!=null){
		for ( final Pair< Double, Double > timepoint : timeseries )
			series.add( timepoint.getA(), timepoint.getB() );
		}
		return series;
	}
	public static void setColor( final JFreeChart chart, final int seriesIndex, final Color col )
	{
		final XYPlot plot = chart.getXYPlot();
		final XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint( seriesIndex, col );
	}

	public static void setStroke( final JFreeChart chart, final int seriesIndex, final float stroke )
	{
		final XYPlot plot = chart.getXYPlot();
		final XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesStroke( seriesIndex, new BasicStroke( stroke ) );
	}

	public static void setShape( final JFreeChart chart, final int seriesIndex, final Shape shape )
	{
		final XYPlot plot = chart.getXYPlot();
		final XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesShape( seriesIndex, shape );
	}
	public static JFreeChart makeChart( final XYSeriesCollection dataset ) { return makeChart( dataset, "XY Chart", "x-axis", "y-axis" ); }
	public static JFreeChart makeChart( final XYSeriesCollection dataset, final String title, final String x, final String y )
	{
		final JFreeChart chart = ChartFactory.createXYLineChart(title, x, y, dataset, PlotOrientation.VERTICAL, true, true, false); 

		return chart;
	}
	
	public static JFrame display( final JFreeChart chart ) { return display( chart, new Dimension( 800, 500 ) ); }
	public static JFrame display( final JFreeChart chart, final Dimension d )
	{
		final JPanel panel = new JPanel();
		final ChartPanel chartPanel = new ChartPanel(
				chart,
				d.width - 10,
				d.height - 35,
				ChartPanel.DEFAULT_MINIMUM_DRAW_WIDTH,
				ChartPanel.DEFAULT_MINIMUM_DRAW_HEIGHT,
				ChartPanel.DEFAULT_MAXIMUM_DRAW_WIDTH,
				ChartPanel.DEFAULT_MAXIMUM_DRAW_HEIGHT,
				ChartPanel.DEFAULT_BUFFER_USED,
				true,  // properties
				true,  // save
				true,  // print
				true,  // zoom
				true   // tooltips
				);
		panel.add( chartPanel );

		final JFrame frame = new JFrame();
		frame.setContentPane( panel );
		frame.validate();
		frame.setSize( d );

		frame.setVisible( true );
		return frame;
	}

}
