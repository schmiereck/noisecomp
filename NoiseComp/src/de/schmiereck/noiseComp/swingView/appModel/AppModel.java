/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.timelines.TimelineDrawPanelModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;

/**
 * <p>
 * 	App Model.
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public class AppModel
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Edited Modul-Generator-Type Data.
	 */
	private ModulGeneratorTypeData editedModulGeneratorTypeData;
	
	private List<EditModuleChangedListener> editModuleChangedListeners = new Vector<EditModuleChangedListener>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #editedModulGeneratorTypeData}.
	 */
	public ModulGeneratorTypeData getEditedModulGeneratorTypeData()
	{
		return this.editedModulGeneratorTypeData;
	}

	/**
	 * @param editedModulGeneratorTypeData 
	 * 			to set {@link #editedModulGeneratorTypeData}.
	 */
	public void setEditedModulGeneratorTypeData(ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		this.editedModulGeneratorTypeData = editedModulGeneratorTypeData;
		
		for (EditModuleChangedListener editModuleChangedListener : this.editModuleChangedListeners)
		{
			editModuleChangedListener.notifyEditModulChanged(this);
		}
	}

	/**
	 * @param editModuleChangedListener 
	 * 			to add to {@link #editModuleChangedListeners}.
	 */
	public void addEditModuleChangedListener(EditModuleChangedListener editModuleChangedListener)
	{
		this.editModuleChangedListeners.add(editModuleChangedListener);
	}

}