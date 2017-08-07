package chirpModels;

public class LinearChirp implements ChirpFitFunction {

	public double val(int t, double[] a, int totaltime) {

		double Sinusoid = a[t] * Math.cos(
				a[totaltime] * t + (a[totaltime + 1] - a[totaltime + 2]) * t * t / (2 * totaltime) + a[totaltime + 3]);

		return Sinusoid;
	}

	/*
	 * Gradient function
	 * 
	 */
	public double grad(int t, double[] a, int totaltime, int k) {

		if (k < totaltime) {

			double Sinusoid = Math.cos(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime + 2]) * t * t / (2 * totaltime) + a[totaltime + 3]);

			return Sinusoid;

		}

		else if (k == totaltime) {

			double Sinusoid = -a[t] * Math.sin(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime + 2]) * t * t / (2 * totaltime) + a[totaltime + 3]) * t * t
					/ (2 * totaltime);

			return Sinusoid;

		}

		else if (k == totaltime + 1) {

			double Sinusoid = -a[t] * Math.sin(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime + 2]) * t * t / (2 * totaltime) + a[totaltime + 3]) * t * t
					/ (2 * totaltime);

			return Sinusoid;

		}

		else if (k == totaltime + 2) {

			double Sinusoid = a[t] * Math.sin(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime + 2]) * t * t / (2 * totaltime) + a[totaltime + 3]);

			return Sinusoid;

		}

		else {

			double Sinusoid = -a[t] * Math.sin(a[totaltime] * t
					+ (a[totaltime + 1] - a[totaltime + 2]) * t * t / (2 * totaltime) + a[totaltime + 3]) * t;

			return Sinusoid;

		}

	}

}
