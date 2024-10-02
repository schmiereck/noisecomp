package de.schmiereck.screenTools.scheduler;

import junit.framework.TestCase;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>30.05.2004: created, smk</p>
 */
public class PipelineSchedulerLogicTest
	extends TestCase
{
	public void testPipelineSchedulerLogic()
	{
		PipelineSchedulerLogic pipelineSchedulerLogic = new PipelineSchedulerLogic(10)
		{
			/* (non-Javadoc)
			 * @see de.schmiereck.screenTools.scheduler.PipelineSchedulerLogic#notifyRunSchedulOut(long)
			 */
			public void notifyRunSchedulOut(long actualWaitPerFramesMillis)
			{
				System.out.println(this.getOutLogic().getRunCounterOut() + " -> OUT: " + actualWaitPerFramesMillis);
				
				//try { Thread.sleep(200); } catch (InterruptedException ex) { ex.printStackTrace(); }
			}

			/* (non-Javadoc)
			 * @see de.schmiereck.screenTools.scheduler.PipelineSchedulerLogic#notifyRunSchedulCalc(long)
			 */
			public void notifyRunSchedulCalc(long actualWaitPerFramesMillis)
			{
				System.out.println(this.getCalcLogic().getRunCounterCalc() + " -> CALC: " + actualWaitPerFramesMillis);
			}
		};
		
		System.out.println("BEGIN");
		pipelineSchedulerLogic.startThread();

		try
		{
			System.out.println("SLEEP");
			Thread.sleep(3000);
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}

		pipelineSchedulerLogic.stopThread();
		System.out.println("END");
		
		assertTrue("In 3000ms the notify should fired min 29 times (with 10 a times per second target).", 
					pipelineSchedulerLogic.getOutLogic().getRunCounterOut() >= 29);
	}
}
