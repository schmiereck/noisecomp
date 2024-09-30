package de.schmiereck.screenTools.controller;

import java.util.Iterator;
import java.util.Vector;

/*
 * Created on 28.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Notify his Listeners, if some visible data is changed.
 * </p>
 * 
 * @author smk
 * @version <p>28.03.2005:	created, smk</p>
 */
public class DataChangedObserver
{
	/**
	 * List of {@link DataChangedListener}-Objects.
	 */
	private Vector dataChangedListeners = new Vector();

	public void addDataChangedListener(DataChangedListener dataChangedListener)
	{
		this.dataChangedListeners.add(dataChangedListener);
	}
	
	public void dataChanged(ControllerData controllerData)
	{
		Iterator dataChangedListenersIterator = this.dataChangedListeners.iterator();
		
		while (dataChangedListenersIterator.hasNext())
		{
			DataChangedListener dataChangedListener = (DataChangedListener)dataChangedListenersIterator.next();
			
			dataChangedListener.notifyDataChanged(controllerData);
		}
	}
	
	public void dataChanged(ControllerData controllerData,
							int posX, int posY, int sizeX, int sizeY)
	{
		Iterator dataChangedListenersIterator = this.dataChangedListeners.iterator();
		
		while (dataChangedListenersIterator.hasNext())
		{
			DataChangedListener dataChangedListener = (DataChangedListener)dataChangedListenersIterator.next();
			
			dataChangedListener.notifyDataChanged(controllerData,
												  posX, posY, sizeX, sizeY);
		}
	}
}
