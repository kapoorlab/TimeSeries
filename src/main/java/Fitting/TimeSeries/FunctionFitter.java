package Fitting.TimeSeries;

import java.util.ArrayList;

import net.imglib2.algorithm.BenchmarkAlgorithm;
import net.imglib2.algorithm.OutputAlgorithm;
import net.imglib2.util.Pair;

public class FunctionFitter extends BenchmarkAlgorithm implements OutputAlgorithm<double[]> {

	final ArrayList<Pair<Double, Double>> timeseries;
	final double deltat;
	
	
	/**
	 * 
	 * @param timeseries input the time series
	 * deltat = spacing in time between succeding points
	 */
	
	public FunctionFitter(final ArrayList<Pair<Double, Double>> timeseries){
		
		this.timeseries = timeseries;
		
		this.deltat = ( timeseries.get(timeseries.size() - 1).getA() - timeseries.get(0).getA()) / (timeseries.size() - 1);
		
	}
	
	
	public boolean checkInput() {
		
		if (timeseries.size() == 0)
		return false;
		
		return true;
	}

	public boolean process() {
		
		// Run the gradient descent using Chirp function fit
		
		return false;
	}

	public double[] getResult() {
		
        // Output the function fit results
		
		return null;
	}
	
	
	
	
	
	

}
