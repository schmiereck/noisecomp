package de.schmiereck.noiseComp.desktopController;

import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * Implement this if you need to notifyed, if the edited modul is changed.<br/>
 * Register the Object by {@link de.schmiereck.noiseComp.desktopController.DesktopControllerData#registerEditGeneratorChangedListener(EditGeneratorChangedListener)}.
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public interface EditGeneratorChangedListener
{

	/**
	 * Called if the edited modul is changed.
	 * 
	 * @param editModulTypeData
	 * @param editGenerators
	 */
	void notifyEditGeneratorChanged(ModulGeneratorTypeData editModulTypeData, Generators editGenerators);
}
