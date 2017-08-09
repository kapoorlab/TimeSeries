package Fitting.TimeSeries;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import net.imglib2.util.Pair;
import net.imglib2.util.ValuePair;

public class ExtractSeries {

	
	public static ArrayList< Pair< Double, Double > > gatherdata( final File file )
	{
		final ArrayList< Pair< Double, Double > > points = new ArrayList< Pair< Double, Double > >();

		try
		{
			BufferedReader in = Util.openFileRead( file );

			while( in.ready() )
			{
				String line = in.readLine().trim();

				while ( line.contains( "\t\t" ) )
					line = line.replaceAll( "\t\t", "\t" );

				if ( line.length() >= 3 && line.matches( "[0-9].*" ) )
				{
					final String[] split = line.trim().split( "\t" );

					final double timepoint = Double.parseDouble( split[ 0 ] );
					final double value = Double.parseDouble( split[ 1 ] );

					points.add( new ValuePair< Double, Double >( timepoint, value ) );
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return null;
		}

		Collections.sort( points, new Comparator< Pair< Double, Double > >()
		{
			
			public int compare( final Pair< Double, Double > o1, final Pair< Double, Double > o2 )
			{
				return o1.getA().compareTo( o2.getA() );
			}
		} );

		return points;
	}
	
	
	public static ArrayList< Pair< Double, Double > > Normalize (ArrayList< Pair< Double, Double > > points){
		
		
		Pair<Double, Double> minmax = minmax(points);
		final ArrayList< Pair< Double, Double > > Normpoints = new ArrayList< Pair< Double, Double > >();

		
		
		for (final Pair< Double, Double > p : points){
			
			Normpoints.add(new ValuePair<Double, Double>(p.getA(), p.getB() / (minmax.getB() - minmax.getA())));
			
		}
		
		return Normpoints;
	}
	
	public static Pair<Double, Double> minmax (ArrayList< Pair< Double, Double > > points){
		
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		
		
		for (final Pair< Double, Double > p : points){
			
			min = Math.min(min, p.getB());
			max = Math.max(max, p.getB());
		}
		
		Pair<Double, Double> minmax = new ValuePair<Double,Double>(min, max);
		
		return minmax;
	}

	public static double[] initialguess(ArrayList< Pair< Double, Double > > points, final int totaltime){
		
		double[] initialparameters = new double[5];
		
		
		
		
		double Frequency = 0.001;
		double expectedsigma = 0.0001;
		double startChirp = Frequency - expectedsigma/2;
		double endChirp = Frequency + expectedsigma/2;
		double phase = 0;
		
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		
		System.out.println(totaltime);
		
		for (final Pair< Double, Double > p : points){
			
			min = Math.min(min, p.getB());
			max = Math.max(max, p.getB());
		}
		
		
			
			
		
		initialparameters[0] = Frequency;
		initialparameters[1] = startChirp;
		initialparameters[2] = endChirp;
		initialparameters[3] = phase;
		initialparameters[4] = min;
		
		return initialparameters;
	}
	
	
}
