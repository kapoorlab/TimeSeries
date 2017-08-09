package chirpModels;

public class LinearChirp implements ChirpFitFunction {

	public double val(double t,double[] I, double[] a, int totaltime, int timeindex) {

		double Sinusoid = I[timeindex] * Math.cos(
				a[0] * t + (a[1] - a[2]) * t * t / (2 * totaltime) + a[3]) + a[4];
		
		return Sinusoid;
	}

	/*
	 * Gradient function
	 * 
	 */
	public double grad(double t, double[] I, double[] a, int totaltime, int k, int timeindex) {

		

		if (k == 0) {

			double Sinusoid = -I[timeindex] * Math.sin(a[0] * t
					+ (a[1] - a[2]) * t * t / (2 * totaltime) + a[3]) * t;

			return Sinusoid;

		}

		else if (k ==  1) {

			double Sinusoid = -I[timeindex] * Math.sin(a[0] * t
					+ (a[1] - a[2]) * t * t / (2 * totaltime) + a[3]) * t * t
					/ (2 * totaltime);

			return Sinusoid;

		}

		else if (k ==  2) {

			double Sinusoid = I[timeindex] * Math.sin(a[0] * t
					+ (a[1] - a[2]) * t * t / (2 * totaltime) + a[3])* t * t
					/ (2 * totaltime);

			return Sinusoid;

		}

		else if (k == 3) {

			double Sinusoid = -I[timeindex] * Math.sin(a[0] * t
					+ (a[1] - a[2]) * t * t / (2 * totaltime) + a[3]) ;

			return Sinusoid;

		}
		
		else if (k == 4){
			
			return 1;
		}
		
		
		else return 0;
	}

}
