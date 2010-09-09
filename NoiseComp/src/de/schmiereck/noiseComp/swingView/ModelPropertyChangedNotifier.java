/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import java.util.List;
import java.util.Vector;


/**
 * <p>
 * 	Model-Property Changed Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class ModelPropertyChangedNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Model-Property changed listeners.
	 */
	private List<ModelPropertyChangedListener> modelPropertyChangedListeners = new Vector<ModelPropertyChangedListener>();

	//**********************************************************************************************
	// Functions:

	/**
	 * Notify {@link #modelPropertyChangedListeners}.
	 */
	public void notifyModelPropertyChangedListeners()
	{
		for (ModelPropertyChangedListener modelPropertyChangedListener : this.modelPropertyChangedListeners)
		{
			modelPropertyChangedListener.notifyModelPropertyChanged();
		}
	}

	/**
	 * @param generatorNameChangedListener
	 * 			to add to {@link #modelPropertyChangedListeners}.
	 */
	public void addModelPropertyChangedListener(ModelPropertyChangedListener generatorNameChangedListener)
	{
		this.modelPropertyChangedListeners.add(generatorNameChangedListener);
	}
}
