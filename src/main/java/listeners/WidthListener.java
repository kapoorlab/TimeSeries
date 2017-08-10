package listeners;

import java.awt.TextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import Fitting.TimeSeries.InteractiveChirpFit;


public class WidthListener implements TextListener{
	
	final InteractiveChirpFit parent;
	
	public WidthListener(final InteractiveChirpFit parent){
		
		this.parent = parent;
	}
	

	@Override
	public void textValueChanged(TextEvent e) {
		final TextComponent tc = (TextComponent)e.getSource();
		 
		 tc.addKeyListener(new KeyListener(){
			 @Override
			    public void keyTyped(KeyEvent arg0) {
				   
			    }

			    @Override
			    public void keyReleased(KeyEvent arg0) {
			    	

			    }

			    @Override
			    public void keyPressed(KeyEvent arg0) {
			    	String s = tc.getText();
			    	if (arg0.getKeyChar() == KeyEvent.VK_ENTER)
					 {
						
					parent.Highfrequ = parent.Lowfrequ - Float.parseFloat(s) ;
					 parent.dataset.removeAllSeries();
					 parent.updateCHIRP();
					 
					 }

			    }
			});
			 
		 
		 
		 
		 
		
	}

}
