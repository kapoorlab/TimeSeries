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
	public int maxiter = 500;
	public double lambda = 1e-3;
	public double termepsilon = 1e-2;
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
		
		try {
			LevenbergMarquardtSolverChirp.solve(T, LMparam, timeseries.size(), I, UserChoiceFunction, lambda,
					termepsilon, maxiter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	
			System.out.println("Frequency (hrs):" + 6.28/((LMparam[timeseries.size()]) * 60));
			System.out.println("Chirp Frequ start (hrs):" + 6.28/((LMparam[timeseries.size() + 2]) * 60));
			System.out.println("Chirp Frequ end (hrs):" + 6.28/((LMparam[timeseries.size() + 2]) * 60));
			System.out.println("Phase:" + ((LMparam[timeseries.size()+3])));
			System.out.println("Back:" + ((LMparam[timeseries.size()+4])));


			System.out.println("Frequency :" + LMparam[timeseries.size()]);
			System.out.println("Chirp Frequ start :" + LMparam[timeseries.size() + 1]);
			System.out.println("Chirp Frequ end :" + LMparam[timeseries.size() + 2]);
			System.out.println("Phase:" + ((LMparam[timeseries.size()+3])));
			System.out.println("Back:" + ((LMparam[timeseries.size()+4])));
		
		
		return false;
	}

	public double[] getResult() {
		
        // Output the function fit results
		
		return LMparam;
	}
	
	
	
	
	

}
