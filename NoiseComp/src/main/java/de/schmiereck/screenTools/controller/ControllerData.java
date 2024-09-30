package de.schmiereck.screenTools.controller;

import java.util.Iterator;

/**
 * TODO docu
 *
 * @author smk
 * @version 07.01.2004
 */
public abstract class ControllerData
{
	
	/**
	 * Millisekunden die der Grafik-Thread zwischen zwei Frames schlafen konnte.
	 */
	private long graphicSleepMillis = 0;

	/**
	 * Millisekunden die der Berechnungs-Thread zwischen zwei Berechnungen schlafen konnte.
	 */
	private long calcSleepMillis = 0;
	
	/**
	 * @return die Anzahl der verwalteten {@link de.schmiereck.ballblazer.objects.ObjectData} Objekte.
	 */
	public abstract int getObjectsCount();

	/**
	 * @return einen Iterator über die Liste der verwalteten {@link de.schmiereck.ballblazer.objects.ObjectData} Objekte.
	 */
	public abstract Iterator getObjectsIterator();

	public abstract int getFieldWidth();

	public abstract int getFieldHeight();
	

	/**
	 * @see #graphicSleepMillis
	 */
	public void setGraphicSleepMillis(long sleepMillis)
	{
		this.graphicSleepMillis = sleepMillis;
	}

	/**
	 * @see #graphicSleepMillis
	 */
	public long getGraphicSleepMillis()
	{
		return this.graphicSleepMillis;
	}

	/**
	 * @see #calcSleepMillis
	 */
	public void setCalcSleepMillis(long sleepMillis)
	{
		this.calcSleepMillis = sleepMillis;
	}

	/**
	 * @see #graphicSleepMillis
	 */
	public long getCalcSleepMillis()
	{
		return this.calcSleepMillis;
	}
}
