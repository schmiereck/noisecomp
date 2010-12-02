/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeSelect;

import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;

/**
 * <p>
 * 	Modul-Input-Type Select Controller.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModulInputTypeSelectController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Input-Type Select Model.
	 */
	private final ModulInputTypeSelectModel inputTypeSelectModel;
	
	/**
	 * Modul-Input-Type Select View.
	 */
	private final ModulInputTypeSelectView inputTypeSelectView;
	
	private final AppModelChangedObserver appModelChangedObserver;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ModulInputTypeSelectController(final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.inputTypeSelectModel = new ModulInputTypeSelectModel();
		this.inputTypeSelectView = new ModulInputTypeSelectView(this.inputTypeSelectModel);
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
//							appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());
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
		// Modul-Input-Type Select Model Selected-Row changed -> update Modul-Input-Type Select View:
		
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
	public ModulInputTypeSelectModel getInputTypeSelectModel()
	{
		return this.inputTypeSelectModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectView}.
	 */
	public ModulInputTypeSelectView getInputTypeSelectView()
	{
		return this.inputTypeSelectView;
	}


	/**
	 * @param modulGeneratorTypeData
	 * 			is the Modul-Generator-Type Data.
	 */
	public void doEditModuleChanged(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		this.inputTypeSelectModel.clearInputs();
		
		if (modulGeneratorTypeData != null)
		{
			Iterator<InputTypeData> inputTypesIterator = modulGeneratorTypeData.getInputTypesIterator();
	
	//		this.inputTypeSelectModel.setSelectedRowNo(null);
			
			if (inputTypesIterator != null)
			{
				while (inputTypesIterator.hasNext())
				{
					InputTypeData inputTypeData = inputTypesIterator.next();
					
					ModulInputTypeSelectEntryModel selectEntryModel = 
						new ModulInputTypeSelectEntryModel(inputTypeData);
					
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
	public InputTypeData getSelectedModulInputType()
	{
		//==========================================================================================
		InputTypeData inputTypeData;
		
		ModulInputTypeSelectEntryModel selectEntryModel = this.inputTypeSelectModel.getSelectedRow();
		
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
	public void doInputTypeUpdated(ModulInputTypeSelectModel selectModel)
	{
		//==========================================================================================
		Integer selectedRowNo = this.inputTypeSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			ModulInputTypeSelectEntryModel selectEntryModel = this.inputTypeSelectModel.getSelectedRow();
			
			ModulInputTypeTabelModel modulInputTypeTabelModel = this.inputTypeSelectModel.getModulInputTypeTabelModel();
			
			selectEntryModel.updateInputData();
			
			modulInputTypeTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
		}
		//==========================================================================================
	}

	/**
	 * Remove Selected Entry.
	 * 
	 * @param editedModulGeneratorTypeData
	 * 			is the edited Modul-Generator-Type Data.
	 */
	public void doRemoveSelectedEntry(ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		//==========================================================================================
		ModulInputTypeSelectEntryModel selectEntryModel = this.inputTypeSelectModel.getSelectedRow();
		
		if (selectEntryModel != null)
		{
			ModulInputTypeTabelModel tabelModel = this.inputTypeSelectModel.getModulInputTypeTabelModel();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Modul Input-Type Data:
			
			InputTypeData inputTypeData = selectEntryModel.getInputTypeData();
			
			if (inputTypeData != null)
			{
				editedModulGeneratorTypeData.removeInputTypeData(inputTypeData);
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
		ModulInputTypeSelectModel modulInputTypeSelectModel = this.getInputTypeSelectModel();
		
		ModulInputTypeTabelModel tabelModel = modulInputTypeSelectModel.getModulInputTypeTabelModel();
		
		ModulInputTypeSelectEntryModel selectEntryModel = new ModulInputTypeSelectEntryModel(null);
		
		int rowNo = tabelModel.addInputData(selectEntryModel);
		
		modulInputTypeSelectModel.setSelectedRowNo(rowNo);
		//==========================================================================================
	}
}
