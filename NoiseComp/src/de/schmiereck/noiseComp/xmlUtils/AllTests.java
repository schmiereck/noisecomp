package de.schmiereck.noiseComp.xmlUtils;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Prüft die Tool-Funktionen für den Zugriff auf XML-Dateien und den DOM im Speicher.
 *
 * @author smk
 * @version 17.01.2004
 */
public class AllTests
{

	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for de.schmiereck.jawiki.xmlUtils");
		//$JUnit-BEGIN$
		suite.addTestSuite(XMLDataTest.class);
		//$JUnit-END$
		return suite;
	}

}
