package de.schmiereck.noiseComp.desktopController.editModulPage;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.EditGeneratorChangedListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputTypesWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectedListEntryInterface;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * Page to edit the data (name and input types) of a generator modul.<br/>
 * A generator modul is a group of generators of the generator type 
 * {@link de.schmiereck.noiseComp.generator.ModulGenerator}.
 *
 * @author smk
 * @version <p>14.03.2004: created, smk</p>
 */
public class EditModulPageData
extends DesktopPageData
implements SelectedListEntryInterface
{
	/**
	 * Dialog: Group: Cancel-Button
	 */
	private FunctionButtonData	cancelGroupButtonData;

	/**
	 * Dialog: Group: Name-Inputline
	 */
	private InputlineData	groupNameInputlineData;

	/**
	 * Dialog: Group: Save-Button
	 */
	private FunctionButtonData	saveGroupButtonData;

	private FunctionButtonData inputTypeEditSaveFunctionButtonData;

	private InputlineData 	inputTypeEditTypeInputlineData;
	private InputlineData 	inputTypeEditNameInputlineData;
	private InputlineData 	inputTypeEditCountMinInputlineData;
	private InputlineData 	inputTypeEditCountMaxInputlineData;
	private InputlineData	inputTypeEditDefaultValueInputlineData;
	private InputlineData 	inputTypeEditDescriptionInputlineData;
	
	/**
	 * Select Input Type: InputTypes-List
	 */
	private InputTypesWidgetData	inputTypesListWidgetData;

	private FunctionButtonData		editModulInsertButtonData	= null;
	private FunctionButtonData		editModulRemoveButtonData	= null;
	private FunctionButtonData		editModulEditButtonData	= null;
	
	/**
	 * Selected and edited Input Type Data of the Modul.
	 */
	private InputTypeData	selectedInputTypeData = null;

	
	/**
	 * Constructor.
	 * 
	 * @param desktopData
	 * @param desktopSizeX
	 * @param desktopSizeY
	 */
	public EditModulPageData(DesktopData desktopData, DesktopControllerData desktopControllerData, int desktopSizeX, int desktopSizeY)
	{
		super(desktopData, desktopSizeX, desktopSizeY);

		{
			// Add Main Panel:
			PaneData paneData = new PaneData(0, 0, this.getDesktopSizeX(), this.getDesktopSizeY());
			this.addWidgetData(paneData);
		}

		{
			this.cancelGroupButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			this.addWidgetData(this.cancelGroupButtonData);
		}
		
		{
			LabelData nameLabelData = new LabelData("Modul Name:", 10, 40, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.groupNameInputlineData = new InputlineData("groupName", 100, 40, 200, 16);
			this.addWidgetData(this.groupNameInputlineData);
		}
		{
			this.saveGroupButtonData = new FunctionButtonData("groupSave", "Save Modul", 100, 60, 200, 20);
			this.addWidgetData(this.saveGroupButtonData);
		}
		
		{
			this.editModulInsertButtonData = new FunctionButtonData("editModulNew", "New...", 100, 95, 100, 20);
			this.addWidgetData(this.editModulInsertButtonData);
		}
		{
			this.editModulEditButtonData = new FunctionButtonData("editModulEdit", "Edit...", 210, 95, 100, 20);
			this.addWidgetData(this.editModulEditButtonData);
		}
		{
			this.editModulRemoveButtonData = new FunctionButtonData("editModulRemove", "Remove", 320, 95, 110, 20);
			this.addWidgetData(this.editModulRemoveButtonData);
		}
		
		ScrollbarData verticalScrollbarData;
		{
			verticalScrollbarData = new ScrollbarData("inputTypesVScroll", 780 - desktopData.getScrollbarWidth2(), 120, 
					desktopData.getScrollbarWidth2(), 300, true);
			this.addWidgetData(verticalScrollbarData);
		}
		{
			LabelData nameLabelData = new LabelData("Input Types:", 10, 120, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypesListWidgetData = new InputTypesWidgetData(100, 120, 680 - desktopData.getScrollbarWidth2(), 300, verticalScrollbarData, null, this);
			this.addWidgetData(this.inputTypesListWidgetData);
		}

		{
			LabelData nameLabelData = new LabelData("Type:", 10, 440, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypeEditTypeInputlineData = new InputlineData("inputTypeEditType", 100, 440, 50, 16);
			this.addWidgetData(this.inputTypeEditTypeInputlineData);
		}
		{
			LabelData nameLabelData = new LabelData("Type Name:", 10, 460, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypeEditNameInputlineData = new InputlineData("inputTypeEditName", 100, 460, 150, 16);
			this.addWidgetData(this.inputTypeEditNameInputlineData);
		}
		{
			LabelData nameLabelData = new LabelData("Min:", 10, 480, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypeEditCountMinInputlineData = new InputlineData("inputTypeEditCountMin", 100, 480, 50, 16);
			this.addWidgetData(this.inputTypeEditCountMinInputlineData);
		}
		{
			LabelData nameLabelData = new LabelData("Max:", 10, 500, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypeEditCountMaxInputlineData = new InputlineData("inputTypeEditCountMax", 100, 500, 50, 16);
			this.addWidgetData(this.inputTypeEditCountMaxInputlineData);
		}
		{
			LabelData nameLabelData = new LabelData("Default Value:", 10, 520, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypeEditDefaultValueInputlineData = new InputlineData("inputTypeEditDefaultValue", 100, 520, 200, 16);
			this.addWidgetData(this.inputTypeEditDefaultValueInputlineData);
		}
		{
			LabelData nameLabelData = new LabelData("Description:", 10, 540, 90, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.inputTypeEditDescriptionInputlineData = new InputlineData("inputTypeEditDescription", 100, 540, 200, 16);
			this.addWidgetData(this.inputTypeEditDescriptionInputlineData);
		}
		
		{
			this.inputTypeEditSaveFunctionButtonData = new FunctionButtonData("inputTypeEditSave", "Save", 100, 570, 200, 20);
			this.addWidgetData(this.inputTypeEditSaveFunctionButtonData);
		}

		//-----------------------------------------------------------
		// Register Listeners:
		
		//EditModulPageLogic editModulPageLogic = new EditModulPageLogic(this);
		
		//this.inputTypesListWidgetData.registerInputTypeSelectedListener(editModulPageLogic);

		//desktopControllerData.registerEditGeneratorChangedListener(editModulPageLogic);
	}

	public void setActionListeners(
			CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener,
			SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener,
			InputTypeEditSaveActionListener inputTypeEditSaveActionListener
			)
	{
		this.cancelGroupButtonData.addActionLogicListener(cancelGroupButtonActionLogicListener);
		this.saveGroupButtonData.addActionLogicListener(saveGroupButtonActionLogicListener);
		this.inputTypeEditSaveFunctionButtonData.addActionLogicListener(inputTypeEditSaveActionListener);
	}

	/**
	 * @return the attribute {@link #groupNameInputlineData}.
	 */
	public InputlineData getGroupNameInputlineData()
	{
		return this.groupNameInputlineData;
	}
	/**
	 * @return the attribute {@link #selectedInputTypeData}.
	 */
	public InputTypeData getSelectedInputTypeData()
	{
		return this.selectedInputTypeData;
	}
	/**
	 * @param selectedInputTypeData is the new value for attribute {@link #selectedInputTypeData} to set.
	 */
	public void setSelectedInputTypeData(InputTypeData selectedInputTypeData)
	{
		this.selectedInputTypeData = selectedInputTypeData;

		if (this.selectedInputTypeData == null)
		{	
			this.getInputTypeEditTypeInputlineData().setInputText(null);
			this.getInputTypeEditNameInputlineData().setInputText(null);
			this.getInputTypeEditCountMinInputlineData().setInputText(null);
			this.getInputTypeEditCountMaxInputlineData().setInputText(null);
			this.getInputTypeEditDefaultValueInputlineData().setInputText(null);
			this.getInputTypeEditDescriptionInputlineData().setInputText(null);
		}
		else
		{
			Integer inputTypeEditType = Integer.valueOf(selectedInputTypeData.getInputType()); 
			String inputTypeEditName = selectedInputTypeData.getInputTypeName();
			Integer inputTypeEditCountMax = Integer.valueOf(selectedInputTypeData.getInputCountMax());
			Integer inputTypeEditCountMin = Integer.valueOf(selectedInputTypeData.getInputCountMin());
			Float inputTypeEditDefaultValue = selectedInputTypeData.getDefaultValue();
			String inputTypeEditDescription = selectedInputTypeData.getInputTypeDescription();
			
			this.getInputTypeEditTypeInputlineData().setInputInteger(inputTypeEditType);
			this.getInputTypeEditNameInputlineData().setInputText(inputTypeEditName);
			this.getInputTypeEditCountMinInputlineData().setInputText(this.makeCountText(inputTypeEditCountMin));
			this.getInputTypeEditCountMaxInputlineData().setInputText(this.makeCountText(inputTypeEditCountMax));
			this.getInputTypeEditDefaultValueInputlineData().setInputFloat(inputTypeEditDefaultValue);
			this.getInputTypeEditDescriptionInputlineData().setInputText(inputTypeEditDescription);
		}
		
		//this.inputTypesListWidgetData.setSelectedInputTypeData(selectedInputTypeData);
	}
	
	private String makeCountText(Integer inputTypeEditCount)
	{
		String text;
		if (inputTypeEditCount != null)
		{
			int value = inputTypeEditCount.intValue();
			
			if (value >= 0)
			{
				text = inputTypeEditCount.toString();
			}
			else
			{
				text = null;
			}
		}
		else
		{
			text = null;
		}
		return text;
	}

	/**
	 * @return the attribute {@link #inputTypeEditTypeInputlineData}.
	 */
	public InputlineData getInputTypeEditTypeInputlineData()
	{
		return this.inputTypeEditTypeInputlineData;
	}
	/**
	 * @return the attribute {@link #inputTypeEditDescriptionInputlineData}.
	 */
	public InputlineData getInputTypeEditDescriptionInputlineData()
	{
		return this.inputTypeEditDescriptionInputlineData;
	}
	/**
	 * @return the attribute {@link #inputTypeEditCountMaxInputlineData}.
	 */
	public InputlineData getInputTypeEditCountMaxInputlineData()
	{
		return this.inputTypeEditCountMaxInputlineData;
	}
	/**
	 * @return the attribute {@link #inputTypeEditCountMinInputlineData}.
	 */
	public InputlineData getInputTypeEditCountMinInputlineData()
	{
		return this.inputTypeEditCountMinInputlineData;
	}
	/**
	 * @return the attribute {@link #inputTypeEditNameInputlineData}.
	 */
	public InputlineData getInputTypeEditNameInputlineData()
	{
		return this.inputTypeEditNameInputlineData;
	}
	/**
	 * @return the attribute {@link #inputTypeEditSaveFunctionButtonData}.
	 */
	public FunctionButtonData getInputTypeEditSaveFunctionButtonData()
	{
		return this.inputTypeEditSaveFunctionButtonData;
	}
	/**
	 * @return the attribute {@link #inputTypeEditDefaultValueInputlineData}.
	 */
	public InputlineData getInputTypeEditDefaultValueInputlineData()
	{
		return this.inputTypeEditDefaultValueInputlineData;
	}
	/**
	 * @return the attribute {@link #inputTypesListWidgetData}.
	 */
	public InputTypesWidgetData getInputTypesListWidgetData()
	{
		return this.inputTypesListWidgetData;
	}
}
