package de.schmiereck.screenTools.scheduler;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;

/**
 * TODO docu
 *
 * @author smk
 * @version 24.12.2003
 */
public abstract class SchedulerLogic 
	implements Runnable
{
	private Thread thread = null;
	
	private boolean isRunning = false;
	
	/**
	 * Counts of calling {@link #notifyRunSchedul(long)}.
	 */
	private long runCounter	= 0L;
	
	/**
	 * Gewünschte Framerate:<br/>
	 * Anzahl der Frames pro Sekunde die dargestellt werden sollen.
	 */
	private int targetFramesPerSecond;
	
	public SchedulerLogic(int targetFramesPerSecond)
	{
		this.targetFramesPerSecond = targetFramesPerSecond;
	}

	public void startThread()
	{
		if (this.thread != null)
		{
			throw new RuntimeException("run thread already startet");
		}

		this.thread = new Thread(this);
		
		this.runCounter = 0;
		
		this.thread.start();
	}
	
	public void stopThread()
	{
		if (this.thread == null)
		{
			throw new RuntimeException("run thread not startet");
		}

		this.thread = null;
		
		while (this.isRunning == true)
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace(System.err);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		this.isRunning = true;
		
		// Enthält die 'Systemzeit' des Programms.
		// Der Scheduler versucht die 'wirkliche' Zeit mit dieser
		// zu syncronisieren.
		long tm = System.currentTimeMillis();

		// Millisekunden, die zwischen zwei Frames gewartet werden soll,
		// um die gewümschte Framerate hinzubekommen.
		long targetWaitPerFramesMillis = 1000 / this.targetFramesPerSecond;
		
		// Millisekunden die momentan zwischen zwei Frames gewartet wird.
		long actualWaitPerFramesMillis = targetWaitPerFramesMillis;
		
		while (Thread.currentThread() == this.thread)  {
			try {
				tm += actualWaitPerFramesMillis;
				long sleepMillis = tm - System.currentTimeMillis();
				
				// Hat das Zeichnen des Frames länger gedauert
				// als die momentane Framerate erlaubt ? 
				if (sleepMillis < 0) {
					// Setze die Wartezeit zwischen den Frames hoch (Framerate runtersetzen).
					actualWaitPerFramesMillis += 1;//((-sleepMillis) / 2);
				} else {
					// Hat das Zeichnen des Frames kürzer gedauert,
					// als die momentane Framerate Zeit zur Verfügung stellt?
					if (sleepMillis >= 0) {
						// Ist es das Ziel, weniger Zeit für einen Frame zu benötigen
						// als momentan zur verfügung gestellt wird?
						if (targetWaitPerFramesMillis < actualWaitPerFramesMillis)
						{
							// Setze die Wartezeit zwischen den Frames runter (Framerate hochsetzen).
							actualWaitPerFramesMillis -= 1;//((-sleepMillis) / 2);
						}
					}
				}
				
				Thread.sleep(Math.max(0, sleepMillis));
				
				this.runCounter++;
				this.notifyRunSchedul(actualWaitPerFramesMillis);
			}
			catch (InterruptedException ex) {
				ex.printStackTrace(System.err);
			}
		}
		this.isRunning = false;
	}

	/**
	 * @param actualWaitPerFramesMillis
	 */
	public abstract void notifyRunSchedul(long actualWaitPerFramesMillis);

	/**
	 * @return the attribute {@link #runCounter}.
	 */
	public long getRunCounter()
	{
		return this.runCounter;
	}
}
