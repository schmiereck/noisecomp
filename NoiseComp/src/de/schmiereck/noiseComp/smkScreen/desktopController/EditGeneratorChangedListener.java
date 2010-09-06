package de.schmiereck.noiseComp.smkScreen.desktopController;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * Implement this if you need to notifyed, if the edited modul is changed.<br/>
 * Register the Object by {@link de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData#registerEditGeneratorChangedListener(EditGeneratorChangedListener)}.
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public interface EditGeneratorChangedListener
{

	/**
	 * Called if the edited modul is changed.
	 */
	void notifyEditGeneratorChanged(ModulGeneratorTypeData editModulTypeData);
}
