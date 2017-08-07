package Fitting.TimeSeries;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.imglib2.util.Pair;
import net.imglib2.util.ValuePair;

public class ExtractSeries {

	
	public static ArrayList< Pair< Double, Double > > loadMT( final File file )
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

	
	
	
}
