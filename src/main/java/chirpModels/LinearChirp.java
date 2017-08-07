package chirpModels;

public class LinearChirp implements ChirpFitFunction {

	public double val(double[] x, double[] a, double[] b) {
		
		final int totaltime = x.length;
		double Sinusoid = 0;
		for (int i = 0; i < x.length; i++) {
			
			Sinusoid += a[i] * Math.cos(a[totaltime] * x[i] + a[totaltime + 1] *x[i] * x[i] / 2 + a[totaltime + 2]);
			
		}
		
		
		return Sinusoid;
	}

	public double grad(double[] x, double[] a, double[] b, int k) {
		final int totaltime = x.length;
		
		if (k < totaltime){
			
			
			
			double Sinusoid = 0;
			for (int i = 0; i < x.length; i++) {
				
				Sinusoid += Math.cos(a[totaltime] * x[i] + a[totaltime + 1] *x[i] * x[i] / 2 + a[totaltime + 2]);
				
			}
			
			return  Sinusoid;
			
		}
		
		else if (k == totaltime ){
			
			double Sinusoid = 0;
			for (int i = 0; i < x.length; i++) {
				
				Sinusoid += -a[i] * Math.sin(a[totaltime] * x[i] + a[totaltime + 1] *x[i] * x[i] / 2 + a[totaltime + 2]) * x[i] * x[i] / 2;
				
			}
			
			return  Sinusoid;
					
					
		}
		
		else if (k == totaltime + 1){
			
			double Sinusoid = 0;
			for (int i = 0; i < x.length; i++) {
				
				Sinusoid += -a[i] * Math.sin(a[totaltime] * x[i] + a[totaltime + 1] *x[i] * x[i] / 2 + a[totaltime + 2]);
				
			}
			
			return  Sinusoid;
			
			
			
			
		}
		
     else {
			
			double Sinusoid = 0;
			for (int i = 0; i < x.length; i++) {
				
				Sinusoid += -a[i] * Math.sin(a[totaltime] * x[i] + a[totaltime + 1] *x[i] * x[i] / 2 + a[totaltime + 2]) * x[i];
				
			}
			
			return  Sinusoid;
			
			
			
			
		}
		
		
	
	}

}
