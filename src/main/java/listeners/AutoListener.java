package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Fitting.TimeSeries.ExtractSeries;
import Fitting.TimeSeries.InteractiveChirpFit;
import Fitting.TimeSeries.Split;

public class AutoListener implements ActionListener {
	
	final InteractiveChirpFit parent;
	final int rowindex;
	
	public AutoListener(final InteractiveChirpFit parent, final int rowindex){
		
		this.parent = parent;
		this.rowindex = rowindex;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int nThreads = 1;
		// set up executor service
		final ExecutorService taskexecutor = Executors.newFixedThreadPool(nThreads);
		for (int trackindex = rowindex; trackindex < parent.inputfiles.length; ++trackindex){
			
			taskexecutor.execute(new Split(parent, trackindex));
			
		
		}
        taskexecutor.shutdown();
		
		
	}
	
	
	
	
	

}
