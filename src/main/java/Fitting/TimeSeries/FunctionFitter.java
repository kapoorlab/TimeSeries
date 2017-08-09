package Fitting.TimeSeries;

import java.util.ArrayList;

import chirpModels.ChirpFitFunction;
import chirpModels.LinearChirp;
import chirpModels.UserChirpModel;
import net.imglib2.algorithm.BenchmarkAlgorithm;
import net.imglib2.algorithm.OutputAlgorithm;
import net.imglib2.util.Pair;

public class FunctionFitter extends BenchmarkAlgorithm implements OutputAlgorithm<double[]> {

	final ArrayList<Pair<Double, Double>> timeseries;
	final double deltat;
	private final UserChirpModel model;
	public int maxiter = 2000;
	public double lambda = 1e-3;
	public double termepsilon = 1e-1;
	// Mask fits iteration param
	double[] LMparam;
	
	public void setMaxiter(int maxiter) {
		this.maxiter = maxiter;
	}

	public int getMaxiter() {
		return maxiter;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public double getLambda() {
		return lambda;
	}

	public void setTermepsilon(double termepsilon) {
		this.termepsilon = termepsilon;
	}

	public double getTermepsilon() {
		return termepsilon;
	}
	
	
	/**
	 * 
	 * @param timeseries input the time series
	 * deltat = spacing in time between succeding points
	 */
	
	public FunctionFitter(final ArrayList<Pair<Double, Double>> timeseries, UserChirpModel model){
		
		this.timeseries = timeseries;
		this.model = model;
		this.deltat = ( timeseries.get(timeseries.size() - 1).getA() - timeseries.get(0).getA()) / (timeseries.size() - 1);
		
	}
	
	
	public boolean checkInput() {
		
		if (timeseries.size() == 0)
		return false;
		
		return true;
	}

	public boolean process() {
		
		// Run the gradient descent using Chirp function fit
		double[] T = new double[timeseries.size()];
		double[] I = new double[timeseries.size()];
		
		
		for (int i = 0; i < timeseries.size(); ++i){
			
			T[i] = timeseries.get(i).getA();
			I[i] = timeseries.get(i).getB(); 
		}
		
	
		LMparam = ExtractSeries.initialguess(timeseries, timeseries.size());
		
		ChirpFitFunction UserChoiceFunction = null;
		if (model == UserChirpModel.Linear){
			
			UserChoiceFunction = new LinearChirp();
			
		}
		for (int i = 0; i < LMparam.length; ++i){
		System.out.println("Initial parameters:" + LMparam[i]);
		}
		try {
			LevenbergMarquardtSolverChirp.solve(T, LMparam, timeseries.size(), I, UserChoiceFunction, lambda,
					termepsilon, maxiter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i < LMparam.length; ++i){
			System.out.println("Fit parameters:" + LMparam[i]);
			}
		
		
		return false;
	}

	public double[] getResult() {
		
        // Output the function fit results
		
		return LMparam;
	}
	
	
	
	
	

}
