package listeners;

import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import Fitting.TimeSeries.InteractiveChirpFit;

public class LowFrequencyListener implements AdjustmentListener {

	final InteractiveChirpFit parent;
	final Label  label;
	
	public LowFrequencyListener(final InteractiveChirpFit parent, final Label label, final Scrollbar bar){
		
		this.parent = parent;
		this.label = label;
		bar.addMouseListener(new StandardMouseListener(parent));
		
	}
	
	public void adjustmentValueChanged( final AdjustmentEvent event )
	{
		
		parent.dataset.removeAllSeries();
		parent.Lowfrequ = InteractiveChirpFit.computeValueFromScrollbarPosition(
				event.getValue(),
				InteractiveChirpFit.MAX_SLIDER,
				InteractiveChirpFit.MIN_FREQU,
				InteractiveChirpFit.MAX_FREQU );
		
		label.setText( "Low Frequency (hrs) = " + parent.Lowfrequ );
	}
	
}
