package de.schmiereck.noiseComp.desktopController;

import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * Manages the information about the curently edited generators and modul.
 *
 * @author smk
 * @version <p>02.05.2004: created, smk</p>
 */
public class EditData
{
	/**
	 * List of the currently edited generators in the project.
	 */
	private Generators	editGenerators = null;
	/**
	 * Currently edited modul type in the project.<br/>
	 * Is null, if main generators is edited.
	 */
	private ModulGeneratorTypeData editModulTypeData = null;

	/**
	 * @see #editGenerators
	 */
	public Generators getEditGenerators()
	{
		return this.editGenerators;
	}
	/**
	 * @see #editModulTypeData
	 */
	public ModulGeneratorTypeData getEditModulTypeData()
	{
		return this.editModulTypeData;
	}
	
	/**
	 * @see #editModulTypeData
	 * @see #editGenerators
	 */
	public void setEditGenerators(Generators editGenerators)
	{
		this.editModulTypeData = null;
		this.editGenerators = editGenerators;
	}

	/**
	 * @see #editModulTypeData
	 * @see #editGenerators
	 */
	public void setEditModulGenerator(ModulGeneratorTypeData editModulTypeData)
	{
		this.editModulTypeData = editModulTypeData;
		this.editGenerators = editModulTypeData.getGenerators();
	}
}
