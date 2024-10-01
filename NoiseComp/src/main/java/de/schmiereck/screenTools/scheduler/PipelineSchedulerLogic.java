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
{
	Thread calcThread = null;

	/**
	 * Counts of calling {@link #notifyRunSchedulCalc(long)}.
	 */
	private long runCounterCalc	= 0L;

	Thread outThread = null;
	
	boolean isRunning = false;
	
	/**
	 * Counts of calling {@link #notifyRunSchedulOut(long)}.
	 */
	private long runCounterOut	= 0L;
	
	/**
	 * Milliseconds actually calculated for the out outThread to wait beween
	 * the calls of {@link #notifyRunSchedulOut(long)}.<br/>
	 * Is also used from the calc outThread!
	 */
    long actualWaitPerFramesMillis	= 0;
	
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
    int targetFramesPerSecond;

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

		//final CalcLogic calcLogic = new CalcLogic(this);

		this.runCounterCalc = 0;
		//this.calcThread = new Thread(calcLogic::runCalc);
		this.calcThread = new Thread(this::run);

		this.calcThread.start();

		System.out.println("YYY: startOutThread.");
		this.startOutThread();
	}

	private void run() {
		final CalcLogic calcLogic = new CalcLogic(this);

		calcLogic.runCalc();

		this.outThread = null;
	}

	private void startOutThread()
	{
		final OutLogic outLogic = new OutLogic(this);

		this.runCounterOut = 0;
		this.outThread = new Thread(outLogic::runOut);

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

		//while (this.isRunning == true)
		//{
		//	try
		//	{
		//		System.out.println("isRunning....");
		//		Thread.sleep(10);
		//	}
		//	catch (InterruptedException e)
		//	{
		//		e.printStackTrace(System.err);
		//	}
		//}
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

	public void incRunCounterOut()
	{
		this.runCounterOut++;
	}

	public void incRunCounterCalc()
	{
		this.runCounterCalc++;
	}

	/**
	 * @return the attribute {@link #runCounterOut}.
	 */
	public long getRunCounterOut()
	{
		return this.runCounterOut;
	}

	public long getActualWaitPerFramesMillis() {
		return this.actualWaitPerFramesMillis;
	}

	public int getTargetFramesPerSecond() {
		return this.targetFramesPerSecond;
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}
