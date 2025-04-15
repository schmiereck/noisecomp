package de.schmiereck.screenTools;

import de.schmiereck.screenTools.scheduler.SchedulerLogicTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>30.05.2004: created, smk</p>
 */
public class AllTests
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for de.schmiereck.screenTools");
		//$JUnit-BEGIN$
		suite.addTestSuite(SchedulerLogicTest.class);
		//$JUnit-END$
		return suite;
	}
}
