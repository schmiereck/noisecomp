/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.moduleInputTypeSelect;

import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;

/**
 * <p>
 * 	ModuleInput-Type Select Controller.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeSelectController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * ModuleInput-Type Select Model.
	 */
	private final ModuleInputTypeSelectModel inputTypeSelectModel;
	
	/**
	 * ModuleInput-Type Select View.
	 */
	private final ModuleInputTypeSelectView inputTypeSelectView;
	
	private final AppModelChangedObserver appModelChangedObserver;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ModuleInputTypeSelectController(final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.inputTypeSelectModel = new ModuleInputTypeSelectModel();
		this.inputTypeSelectView = new ModuleInputTypeSelectView(this.inputTypeSelectModel);
		{
			TableColumnModel columnModel = this.inputTypeSelectView.getColumnModel();
	
			TableColumn nameCol = columnModel.getColumn(0); 
			nameCol.setPreferredWidth(70); 
			
			TableColumn valueCol = columnModel.getColumn(1); 
			valueCol.setPreferredWidth(70); 
		}		
		this.appModelChangedObserver = appModelChangedObserver;
		
		//------------------------------------------------------------------------------------------
//		// Selected Timeline changed -> update Input-Select-Model:
//		
//		timelinesDrawPanelModel.addSelectedTimelineChangedListener
//		(
//		 	new SelectedTimelineChangedListenerInterface()
//		 	{
//				@Override
//				public void selectedTimelineChanged()
//				{
//					Iterator<InputData> inputsIterator;
//					
//					TimelineSelectEntryModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
//					
//					if (timelineGeneratorModel != null)
//					{
//						Generator generator = 
//							appController.retrieveGeneratorOfEditedModuletimelineGeneratorModel.getName());
//						
//						if (generator != null)
//						{
//							inputsIterator = generator.getInputsIterator();
//						}
//						else
//						{
//							inputsIterator = null;
//						}
//					}
//					else
//					{
//						inputsIterator = null;
//					}
//
//					inputSelectModel.clearInputs();
//					
//					if (inputsIterator != null)
//					{
//						while (inputsIterator.hasNext())
//						{
//							InputData inputData = (InputData)inputsIterator.next();
//							
//							InputSelectEntryModel inputSelectEntryModel = 
//								new InputSelectEntryModel(inputData);
//							
//							inputSelectModel.addInputData(inputSelectEntryModel);
//						}
//					}
//				}
//		 	}
//		);
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Select Model Selected-Row changed -> update ModuleInput-Type Select View:
		
		this.inputTypeSelectModel.getSelectedRowNoChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					Integer selectedRowNo = inputTypeSelectModel.getSelectedRowNo();
					
					ListSelectionModel selectionModel = inputTypeSelectView.getSelectionModel();
					
					if (selectedRowNo != null)
					{
						selectionModel.setSelectionInterval(selectedRowNo, selectedRowNo);
					}
					else
					{
						selectionModel.clearSelection();
					}
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectModel}.
	 */
	public ModuleInputTypeSelectModel getInputTypeSelectModel()
	{
		return this.inputTypeSelectModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectView}.
	 */
	public ModuleInputTypeSelectView getInputTypeSelectView()
	{
		return this.inputTypeSelectView;
	}


	/**
	 * @param moduleGeneratorTypeData
	 * 			is the ModuleGenerator-Type Data.
	 */
	public void doEditModuleChanged(ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		this.inputTypeSelectModel.clearInputs();
		
		if (moduleGeneratorTypeData != null)
		{
			Iterator<InputTypeData> inputTypesIterator = moduleGeneratorTypeData.getInputTypesIterator();
	
	//		this.inputTypeSelectModel.setSelectedRowNo(null);
			
			if (inputTypesIterator != null)
			{
				while (inputTypesIterator.hasNext())
				{
					InputTypeData inputTypeData = inputTypesIterator.next();
					
					ModuleInputTypeSelectEntryModel selectEntryModel = 
						new ModuleInputTypeSelectEntryModel(inputTypeData);
					
					this.inputTypeSelectModel.addInputData(selectEntryModel);
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the selected Input-Type Data.
	 */
	public InputTypeData getSelectedModuleInputType()
	{
		//==========================================================================================
		InputTypeData inputTypeData;
		
		ModuleInputTypeSelectEntryModel selectEntryModel = this.inputTypeSelectModel.getSelectedRow();
		
		if (selectEntryModel != null)
		{
			inputTypeData = selectEntryModel.getInputTypeData();
		}
		else
		{
			inputTypeData = null;
		}
		
		//==========================================================================================
		return inputTypeData;
	}

	/**
	 * @param selectModel
	 */
	public void doInputTypeUpdated(ModuleInputTypeSelectModel selectModel)
	{
		//==========================================================================================
		Integer selectedRowNo = this.inputTypeSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			ModuleInputTypeSelectEntryModel selectEntryModel = this.inputTypeSelectModel.getSelectedRow();
			
			ModuleInputTypeTabelModel moduleInputTypeTabelModel = this.inputTypeSelectModel.getModuleInputTypeTabelModel();
			
			selectEntryModel.updateInputData();
			
			moduleInputTypeTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
		}
		//==========================================================================================
	}

	/**
	 * Remove Selected Entry.
	 * 
	 * @param editedModuleGeneratorTypeData
	 * 			is the edited ModuleGenerator-Type Data.
	 */
	public void doRemoveSelectedEntry(ModuleGeneratorTypeData editedModuleGeneratorTypeData)
	{
		//==========================================================================================
		ModuleInputTypeSelectEntryModel selectEntryModel = this.inputTypeSelectModel.getSelectedRow();
		
		if (selectEntryModel != null)
		{
			ModuleInputTypeTabelModel tabelModel = this.inputTypeSelectModel.getModuleInputTypeTabelModel();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Module Input-Type Data:
			
			InputTypeData inputTypeData = selectEntryModel.getInputTypeData();
			
			if (inputTypeData != null)
			{
				editedModuleGeneratorTypeData.removeInputTypeData(inputTypeData);
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Input-Select-Model:
			
			Integer selectedRowNo = this.inputTypeSelectModel.getSelectedRowNo();
			
			tabelModel.removeInput(selectedRowNo);

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.appModelChangedObserver.notifyAppModelChanged();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 */
	public void doCreateNew()
	{
		//==========================================================================================
		ModuleInputTypeSelectModel moduleInputTypeSelectModel = this.getInputTypeSelectModel();
		
		ModuleInputTypeTabelModel tabelModel = moduleInputTypeSelectModel.getModuleInputTypeTabelModel();
		
		ModuleInputTypeSelectEntryModel selectEntryModel = new ModuleInputTypeSelectEntryModel(null);
		
		int rowNo = tabelModel.addInputData(selectEntryModel);
		
		moduleInputTypeSelectModel.setSelectedRowNo(rowNo);
		//==========================================================================================
	}
}
