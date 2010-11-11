/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulInsertedListener;

/**
 * <p>
 * 	App-Model Changed Observer.
 * </p>
 * 
 * @author smk
 * @version <p>10.11.2010:	created, smk</p>
 */
public class AppModelChangedObserver 
implements ModulInsertedListener, 
		   ModelPropertyChangedListener
{
	//**********************************************************************************************
	// Fields:

	private AppModel appModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appModel
	 * 			is the App-Model.
	 */
	public AppModelChangedObserver(AppModel appModel)
	{
		this.appModel = appModel;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.modulsTree.ModulInsertedListener#notifyModulInserted(de.schmiereck.noiseComp.generator.ModulGeneratorTypeData)
	 */
	@Override
	public void notifyModulInserted(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		this.appModel.setIsModelChanged(true);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener#notifyModelPropertyChanged()
	 */
	@Override
	public void notifyModelPropertyChanged()
	{
		this.appModel.setIsModelChanged(true);
	}
}
