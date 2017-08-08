package chirpModels;

public interface ChirpFitFunction {

	
	
	
        // Defines the time-series as a chirp signal
		
		/**
		 * Evaluate this function at point <code>x</code>. The function is
		 * otherwise defined over an array of parameters <code>a</code>, that
		 * is the target of the fitting procedure.
		 * @param t  the time point of the time-series
		 * @param a  the set of parameters that defines the function
		 * @param totaltime the totaltime of the time-series
		 * @param timeindex is the time point index of the time-series
		 * @return  a double value, the function evaluated at <code>x</code>
		 *  
		 */
		public double val(double t, double[] a, int totaltime, int timeindex);
		

		/**
		 * Evaluate the gradient value of the function, taken with respect to the 
		 * <code>ak</code><sup>th</sup> parameter, evaluated at point <code>x</code>.
		 * @param t  the time point index of the time-series
		 * @param a  the set of parameters that defines the function
		 *  @param totaltime the totaltime of the time-series
		 * @param k the index of the parameter to compute the gradient 
		 * @param timeindex is the time point index of the time-series
		 * @return the kth component of the gradient <code>df(x,a)/da_k</code>
		 * @see #val(double[], double[])
		 */
		public double grad(double t, double[] a, int totaltime, int k, int timeindex);
	}

	

