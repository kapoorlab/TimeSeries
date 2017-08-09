package chirpModels;

public class LinearChirp implements ChirpFitFunction {

	public double val(double t, double[] a, int totaltime, int timeindex) {

		double Sinusoid = a[timeindex] * Math.cos(Math.toRadians(a[totaltime] * t + (a[totaltime + 1] - a[totaltime])* t * t / (2 * totaltime) 
				+ a[totaltime + 2])) + a[totaltime + 3];
		
		return Sinusoid;
	}

	/*
	 * Gradient function
	 * 
	 */
	public double grad(double t, double[] a, int totaltime, int k, int timeindex) {

		if (k < totaltime) {

		
			double Sinusoid = Math.cos(Math.toRadians(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime]) * t * t / (2* totaltime)  + a[totaltime + 2]));
			return Sinusoid;

		}

		else if (k == totaltime) {

			double Sinusoid = -a[timeindex] * Math.sin(Math.toRadians(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime]) * t * t / (2 * totaltime)  + a[totaltime + 2])) * ( t -  t * t / (2 * totaltime) );

			return Sinusoid;

		}

		else if (k == totaltime + 1) {

			double Sinusoid = -a[timeindex] * Math.sin(Math.toRadians(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime]) * t * t / 2  + a[totaltime + 2])) * t * t
					/ (2 * totaltime) ;

			return Sinusoid;

		}

		

		else if (k == totaltime + 2) {

			double Sinusoid = -a[timeindex] * Math.sin(Math.toRadians(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime]) * t * t / (2 * totaltime) + a[totaltime + 2])) ;

			return Sinusoid;

		}
		
		else if (k == totaltime + 3){
			
			return 1;
		}
		
		
		else return 0;
	}

}
