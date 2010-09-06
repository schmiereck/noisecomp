/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;



/**
 * <p>
 * 	{@link AppModel#getEditedModulGeneratorTypeData()} Changed Listener.
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public interface EditModuleChangedListener
{

	/**
	 * Edit Modul performed.
	 * 
	 * @param modulGeneratorTypeData
	 * 			is the edited modul.
	 */
	public void notifyEditModulChanged(AppModel appModel);
}
