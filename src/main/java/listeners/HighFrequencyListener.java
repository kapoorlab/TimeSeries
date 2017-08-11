package listeners;

import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import Fitting.TimeSeries.InteractiveChirpFit;

public class HighFrequencyListener implements AdjustmentListener {

	final InteractiveChirpFit parent;
	final Label  label;
	final Scrollbar bar;
	
	public HighFrequencyListener(final InteractiveChirpFit parent, final Label label, final Scrollbar bar){
		
		this.parent = parent;
		this.label = label;
		this.bar = bar;
		bar.addMouseListener(new StandardMouseListener(parent));
		
	}
	
	public void adjustmentValueChanged( final AdjustmentEvent event )
	{
		parent.dataset.removeAllSeries();
		
		
		parent.Highfrequ = InteractiveChirpFit.computeValueFromScrollbarPosition(
				event.getValue(),
				InteractiveChirpFit.MAX_SLIDER,
				InteractiveChirpFit.MIN_CHIRP,
				InteractiveChirpFit.MAX_CHIRP );

		label.setText( "High Frequency (hrs) = " + parent.Highfrequ );
		
	}
	
}
