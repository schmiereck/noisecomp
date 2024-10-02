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
	final CalcLogic calcLogic;

	Thread outThread = null;
	final OutLogic outLogic;

	/**
	 * Milliseconds actually calculated for the out out-Thread to wait between
	 * the calls of {@link #notifyRunSchedulOut(long)}.<br/>
	 * Is also used from the calc-Thread!
	 */
    long actualWaitPerFramesMillis	= 0;

	/**
	 * Gewünschte Framerate:<br/>
	 * Anzahl der Frames pro Sekunde die dargestellt werden sollen.
	 */
	int targetFramesPerSecond;

	/**
	 * Constructor.
	 */
	public PipelineSchedulerLogic(final int targetFramesPerSecond)  {
		this.outLogic = new OutLogic(this);
		this.calcLogic = new CalcLogic(this);
		this.targetFramesPerSecond = targetFramesPerSecond;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#startThread()
	 */
	public void startThread() {
		if (this.calcThread != null) {
			throw new RuntimeException("calc thread already startet");
		}
		if (this.outThread != null) {
			throw new RuntimeException("out thread already startet");
		}

		//final CalcLogic calcLogic = new CalcLogic(this);

		//this.calcThread = new Thread(calcLogic::runCalc);
		this.calcThread = new Thread(this::run);

		this.calcThread.start();

		this.startOutThread();
	}

	private void run() {
		this.calcLogic.runCalc();

		this.outLogic.stopRunning();
		this.outThread = null;
	}

	private void startOutThread() {
		this.outThread = new Thread(this.outLogic::runOut);

		this.outThread.start();
	}
	
	public void stopThread() {
		if (this.outThread == null) {
			throw new RuntimeException("out thread not startet");
		}

		this.outLogic.stopRunning();
		this.outThread = null;
		
		if (this.calcThread == null) {
			throw new RuntimeException("calc thread not startet");
		}

		this.calcLogic.stopRunning();
		this.calcThread = null;
	}

	public abstract void notifyRunSchedulOut(long actualWaitPerFramesMillis);

	public abstract void notifyRunSchedulCalc(long actualWaitPerFramesMillis);

	public long getActualWaitPerFramesMillis() {
		return this.actualWaitPerFramesMillis;
	}

	public int getTargetFramesPerSecond() {
		return this.targetFramesPerSecond;
	}

	public CalcLogic getCalcLogic() {
		return this.calcLogic;
	}

	public OutLogic getOutLogic() {
		return this.outLogic;
	}
}
