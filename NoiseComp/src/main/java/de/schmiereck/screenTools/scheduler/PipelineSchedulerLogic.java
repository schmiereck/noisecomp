package de.schmiereck.screenTools.scheduler;

/**
 * <p>
 * 	Starts two Threads to manage a pipeline.<br/>
 * 	This two threads are named &quot;calc&quot; and &quot;out&quot;.<br/>
 * </p>
 * <p>
 * 	The &quot;out&quot; outThread gives the calculated stream to output
 * 	and manages the time to the next time slice.
 * </p>
 * <p>
 * 	The &quot;calc&quot; outThread is called twice as often as the &quot;out&quot;
 * 	outThread, to calculate the data in the buffers.
 * </p>
 *
 * @author smk
 * @version <p>30.05.2004: created, smk</p>
 */
public abstract class PipelineSchedulerLogic
implements Runnable
{
	private Thread calcThread = null;

	/**
	 * Counts of calling {@link #notifyRunSchedulCalc(long)}.
	 */
	private long runCounterCalc	= 0L;

	private Thread outThread = null;
	
	private boolean isRunning = false;
	
	/**
	 * Counts of calling {@link #notifyRunSchedulOut(long)}.
	 */
	private long runCounterOut	= 0L;
	
	/**
	 * Milliseconds actually calculated for the out outThread to wait beween
	 * the calls of {@link #notifyRunSchedulOut(long)}.<br/>
	 * Is also used from the calc outThread!
	 */
	private long actualWaitPerFramesMillis	= 0;
	
	/**
	 * Constructor.
	 * 
	 * @param targetFramesPerSecond
	 */
	public PipelineSchedulerLogic(int targetFramesPerSecond) 
	{
		this.targetFramesPerSecond = targetFramesPerSecond;
	}
	
	/**
	 * Gewünschte Framerate:<br/>
	 * Anzahl der Frames pro Sekunde die dargestellt werden sollen.
	 */
	private int targetFramesPerSecond;

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#startThread()
	 */
	public void startThread()
	{
		if (this.calcThread != null)
		{
			throw new RuntimeException("calc thread already startet");
		}
		if (this.outThread != null)
		{
			throw new RuntimeException("out thread already startet");
		}

		this.calcThread = new Thread(this);
		
		this.runCounterCalc = 0;
		
		this.calcThread.start();
	}

	private void startOutThread()
	{
		this.outThread = new Thread(this);
		
		this.runCounterOut = 0;
		
		this.outThread.start();
	}
	
	public void stopThread()
	{
		if (this.outThread == null)
		{
			throw new RuntimeException("out thread not startet");
		}

		this.outThread = null;
		
		if (this.calcThread == null)
		{
			throw new RuntimeException("calc thread not startet");
		}

		this.calcThread = null;

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
	public void run()
	{
		if (Thread.currentThread() == this.outThread)
		{
			this.isRunning = true;
			
			// Enthält die 'Systemzeit' des Programms.
			// Der Scheduler versucht die 'wirkliche' Zeit mit dieser
			// zu syncronisieren.
			long tm = System.currentTimeMillis();
	
			// Millisekunden, die zwischen zwei Frames gewartet werden soll,
			// um die gewümschte Framerate hinzubekommen.
			long targetWaitPerFramesMillis = 1000 / this.targetFramesPerSecond;
			
			// Millisekunden die momentan zwischen zwei Frames gewartet wird.
			this.actualWaitPerFramesMillis = targetWaitPerFramesMillis;
			
			//while (null != this.outThread)
			while (Thread.currentThread() == this.outThread)
			{
				try
				{
					tm += this.actualWaitPerFramesMillis;
					long sleepMillis = tm - System.currentTimeMillis();
					
					// Hat das Zeichnen des Frames länger gedauert
					// als die momentane Framerate erlaubt ? 
					if (sleepMillis < 0)
					{	
						// Setze die Wartezeit zwischen den Frames hoch (Framerate runtersetzen).
						this.actualWaitPerFramesMillis += 1;//((-sleepMillis) / 2);
					}
					else
					{
						// Hat das Zeichnen des Frames kürzer gedauert,
						// als die momentane Framerate Zeit zur Verf�gung stellt ?
						if (sleepMillis >= 0)
						{	
							// Ist es das Ziel, weniger Zeit für einen Frame zu benötigen
							// als momentan zur verfügung gestellt wird ?
							if (targetWaitPerFramesMillis < this.actualWaitPerFramesMillis)
							{
								// Setze die Wartezeit zwischen den Frames runter (Framerate hochsetzen).
								this.actualWaitPerFramesMillis -= 1;//((-sleepMillis) / 2);
							}
						}
					}
					
					Thread.sleep(Math.max(0, sleepMillis));
					
					this.runCounterOut++;
					this.notifyRunSchedulOut(this.actualWaitPerFramesMillis);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace(System.err);
				}
			}
	
			this.isRunning = false;
		}
		else
		{
			long tm = System.currentTimeMillis();

			while (Thread.currentThread() == this.calcThread)
			{
				long waitPerFramesMillis = this.actualWaitPerFramesMillis / 2;

				tm += waitPerFramesMillis;

				long sleepMillis = tm - System.currentTimeMillis();

				long d1 = System.currentTimeMillis();
				this.runCounterCalc++;
				this.notifyRunSchedulCalc(sleepMillis);
				long d2 = System.currentTimeMillis();

				System.out.println("XXX: " + waitPerFramesMillis + " / " + sleepMillis + " : " + (d2 - d1));
				
				//tm = ctm;

				// Out thread not started jet ?
				if (this.outThread == null)
				{
					// Start out thread after first calculate is done.
					this.startOutThread();
				}
				
				try
				{
					Thread.sleep(Math.max(0, sleepMillis));
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace(System.err);
				}
			}
		}
	}

	public abstract void notifyRunSchedulOut(long actualWaitPerFramesMillis);

	public abstract void notifyRunSchedulCalc(long actualWaitPerFramesMillis);

	/**
	 * @return the attribute {@link #runCounterCalc}.
	 */
	public long getRunCounterCalc()
	{
		return this.runCounterCalc;
	}

	/**
	 * @return the attribute {@link #runCounterOut}.
	 */
	public long getRunCounterOut()
	{
		return this.runCounterOut;
	}
}
