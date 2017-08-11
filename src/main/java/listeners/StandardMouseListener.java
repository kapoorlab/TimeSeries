package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Fitting.TimeSeries.InteractiveChirpFit;

/**
 * Updates when mouse is released
 * 
 * @author spreibi
 *
 */
public class StandardMouseListener implements MouseListener
{
	final InteractiveChirpFit parent;

	public StandardMouseListener( final InteractiveChirpFit parent )
	{
		this.parent = parent;
	}

	public void mouseReleased( MouseEvent arg0 )
	{
		
		parent.updateCHIRP();
	}

	public void mousePressed( MouseEvent arg0 ){}

	public void mouseExited( MouseEvent arg0 ) {}

	public void mouseEntered( MouseEvent arg0 ) {}

	public void mouseClicked( MouseEvent arg0 ) {}
}
