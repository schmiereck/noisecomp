package de.schmiereck.noiseComp.desktopController;

import de.schmiereck.noiseComp.desktopController.editModulPage.EditModulPageData;
import de.schmiereck.noiseComp.desktopController.mainPage.MainPageData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.screenTools.controller.ControllerData;

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
	//private Generators	editGenerators = null;
	/**
	 * Currently edited modul type in the project.<br/>
	 * Is null, if main generators is edited.
	 */
	private ModulGeneratorTypeData editModulTypeData = null;
	
	/**
	 * Is the main modul of all modules.
	 */
	private ModulGeneratorTypeData mainModulTypeData = null;

	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 */
	public EditData(DesktopControllerData controllerData)
	{
		this.controllerData = controllerData;
	}
	
	/**
	 * @see #editGenerators
	public Generators getEditGenerators()
	{
		Generators generators;
		
		if (this.editModulTypeData != null)
		{
			generators = this.editModulTypeData.getGenerators();
		}
		else
		{
			generators = null;
		}
		
		return generators;
	}
	 */

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
	public void setEditGenerators(Generators editGenerators)
	{
		this.editModulTypeData = null;
		this.editGenerators = editGenerators;
	}
	 */

	/**
	 * @see #editModulTypeData
	 * @see #editGenerators
	 */
	public void setEditModulGenerator(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		this.editModulTypeData = modulGeneratorTypeData;
		//this.editGenerators = this.editModulTypeData.getGenerators();

		//----------------------------------------------------------------------
		// Init EditModul-Page:
		
		EditModulPageData editModulPageData = this.controllerData.getEditModulPageData();
		
		String generatorTypeName;
		
		if (modulGeneratorTypeData != null)
		{	
			generatorTypeName = modulGeneratorTypeData.getGeneratorTypeName();
		}
		else
		{
			generatorTypeName = "";
		}
		editModulPageData.getGroupNameInputlineData().setInputText(generatorTypeName);

		//----------------------------------------------------------------------
		// Init Main-Page:
		
		MainPageData mainPageData = this.controllerData.getMainDesktopPageData();
		
		mainPageData.getModulGeneratorTextWidgetData().setLabelText(generatorTypeName);
		
		mainPageData.getTracksListWidgetData().setTracksData(modulGeneratorTypeData.getTracksData());
	}

	/**
	 * @return returns the {@link #mainModulTypeData}.
	 */
	public ModulGeneratorTypeData getMainModulTypeData()
	{
		return this.mainModulTypeData;
	}
	/**
	 * @param mainModulTypeData to set {@link #mainModulTypeData}.
	 */
	public void setMainModulTypeData(ModulGeneratorTypeData mainModulTypeData)
	{
		this.mainModulTypeData = mainModulTypeData;
	}
}
