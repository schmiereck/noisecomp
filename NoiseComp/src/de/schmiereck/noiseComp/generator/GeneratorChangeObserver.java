package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Vector;

/*
 * Created on 27.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Verwaltet eine Liste von Objekten die das {@link GeneratorChangeListenerInterface}-Interface
 * 	implementieren. Diese werden Benachrichtigt, wenn der überwachte Generator
 * 	einen changeEvent() meldet.
 * </p>
 * 
 * @author smk
 * @version <p>27.03.2005:	created, smk</p>
 */
public class GeneratorChangeObserver
{
	/**
	 * Liste aus Objekten die das {@link GeneratorChangeListenerInterface}-Interface
	 * implementieren.
	 */
	private Vector generatorChangeListeners = null;
	
	public void registerGeneratorChangeListener(GeneratorChangeListenerInterface generatorChangeListener)
	{
		synchronized (this)
		{
			if (this.generatorChangeListeners == null)
			{
				this.generatorChangeListeners = new Vector();
			}
		}
		
		this.generatorChangeListeners.add(generatorChangeListener);
	}
	
	public void removeGeneratorChangeListener(GeneratorChangeListenerInterface generatorChangeListener)
	{
		synchronized (this)
		{
			if (this.generatorChangeListeners != null)
			{
				this.generatorChangeListeners.remove(generatorChangeListener);
			}
		}
	}
	
	public void changedEvent(Generator generator, float startTimePos, float endTimePos)
	{
		synchronized (this)
		{
			if (this.generatorChangeListeners != null)
			{
				Iterator generatorChangeListenersIterator = this.generatorChangeListeners.iterator();
				
				while (generatorChangeListenersIterator.hasNext())
				{
					GeneratorChangeListenerInterface generatorChangeListener = (GeneratorChangeListenerInterface)generatorChangeListenersIterator.next();
					
					generatorChangeListener.notifyGeneratorChanged(generator, startTimePos, endTimePos);
				}
			}
		}
	}
}
