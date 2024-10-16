package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;

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
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Liste aus Objekten die das {@link GeneratorChangeListenerInterface}-Interface
	 * implementieren.<br/>
	 * Werden beachrichtigt wenn sich im {@link Generator} etwas ändert.
	 */
	private Vector<GeneratorChangeListenerInterface> generatorChangeListeners = null;
	
	//**********************************************************************************************
	// Functions:
	
	public synchronized void registerGeneratorChangeListener(GeneratorChangeListenerInterface generatorChangeListener)
	{
		if (this.generatorChangeListeners == null)
		{
			this.generatorChangeListeners = new Vector<GeneratorChangeListenerInterface>();
		}
		
		this.generatorChangeListeners.add(generatorChangeListener);
	}
	
	public synchronized void removeGeneratorChangeListener(GeneratorChangeListenerInterface generatorChangeListener)
	{
		if (this.generatorChangeListeners != null)
		{
			this.generatorChangeListeners.remove(generatorChangeListener);
		}
	}
	
	public synchronized void changedEvent(final SoundSourceData soundSourceData, final Generator generator,
										  final float changedStartTimePos, final float changedEndTimePos)
	{
		if (this.generatorChangeListeners != null) {

            for (final GeneratorChangeListenerInterface generatorChangeListener : this.generatorChangeListeners) {
                generatorChangeListener.notifyGeneratorChanged(soundSourceData, generator,
                        changedStartTimePos, changedEndTimePos);
            }
		}
	}
}
