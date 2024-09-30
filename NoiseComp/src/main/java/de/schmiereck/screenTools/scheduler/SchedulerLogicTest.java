package de.schmiereck.screenTools.scheduler;

import junit.framework.TestCase;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>30.05.2004: created, smk</p>
 */
public class SchedulerLogicTest
	extends TestCase
{
	public void testSchedulerLogicTest()
	{
		SchedulerLogic schedulerLogic = new SchedulerLogic(10)
		{
			/* (non-Javadoc)
			 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#notifyRunSchedul(long)
			 */
			public void notifyRunSchedul(long actualWaitPerFramesMillis)
			{
				System.out.println(this.getRunCounter() + " -> CALC: " + actualWaitPerFramesMillis);
			}
		};
		
		System.out.println("BEGIN");
		schedulerLogic.startThread();

		try
		{
			System.out.println("SLEEP");
			Thread.sleep(3000);
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}

		schedulerLogic.stopThread();
		System.out.println("END");
		
		assertTrue("In 3000ms the notify should fired min 29 times (with 10 a times per second target).", 
					schedulerLogic.getRunCounter() >= 29);
	}
}
