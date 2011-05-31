/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import de.schmiereck.noiseComp.frequences.ToneFrequences;
import de.schmiereck.noiseComp.frequences.ToneFrequences.Tone;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

/**
 * <p>
 * 	Input-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Edit Model.
	 */
	private final InputEditModel inputEditModel;
	
	/**
	 * Input-Edit View.
	 */
	private final InputEditView inputEditView;
	
	private final AppModelChangedObserver appModelChangedObserver;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputSelectModel
	 * 			is the InputSelectModel.
	 * @param appModelChangedObserver 
	 * 			is the AppModelChangedObserver.
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model.
	 */
	public InputEditController(final InputSelectModel inputSelectModel, 
	                           final AppModelChangedObserver appModelChangedObserver,
	                           final SelectedTimelineModel selectedTimelineModel)
	{
		//==========================================================================================
		this.appModelChangedObserver = appModelChangedObserver;
		
		this.inputEditModel = new InputEditModel(selectedTimelineModel);
		
		this.inputEditView = new InputEditView(this.inputEditModel);
		
		//------------------------------------------------------------------------------------------
		this.inputEditView.getInputTypeComboBox().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					JComboBox cb = (JComboBox)e.getSource();
					InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)cb.getSelectedItem();
					
					if (inputTypeSelectItem != null)
					{
						InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
						
						if (inputTypeData != null)
						{
							JComboBox inputTypeValueTextField = inputEditView.getInputTypeValueTextField();
//							
//							String valueStr = InputUtils.makeStringValue(inputTypeValueTextField.getText());
//							
//							if (valueStr == null)
							{
								Float defaultValue = inputTypeData.getDefaultValue();
								
								String defaultValueStr = OutputUtils.makeFloatEditText(defaultValue);
								
								inputTypeValueTextField.setSelectedItem(defaultValueStr);
							}
						}
						
						inputEditModel.setInputTypeData(inputTypeData);
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.inputEditView.getInputTypeValueTextField().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
//					JComboBox cb = (JComboBox)e.getSource();
					JComboBox inputTypeValueTextField = inputEditView.getInputTypeValueTextField();
					
					if (inputTypeValueTextField != null)
					{
						String valueStr;
						
						Object selectedItem = inputTypeValueTextField.getSelectedItem();
						
						if (selectedItem instanceof ValueSelectItem)
						{
							ValueSelectItem valueSelectItem = (ValueSelectItem)selectedItem;
							
							valueStr = valueSelectItem.getValue();
						}
						else
						{
							valueStr = InputUtils.makeStringValue((String)selectedItem);
						}
						
//						if (valueStr == null)
						{
//							inputTypeValueTextField.setSelectedItem(valueStr);
							inputEditModel.setValue(valueStr);
						}
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
			{
				@Override
				public void notifyModelPropertyChanged()
				{
					TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					
					List<InputTypeSelectItem> inputTypeSelectItems;
					
					if (selectedTimelineSelectEntryModel != null)
					{
						Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
						
						inputTypeSelectItems = 
							updateEditedInputType(selectedTimeline);
					}
					else
					{
						inputTypeSelectItems = null;
					}
					
					inputEditModel.setInputTypeSelectItems(inputTypeSelectItems);
				}
			}
		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEditModel}.
	 */
	public InputEditModel getInputEditModel()
	{
		return this.inputEditModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEditView}.
	 */
	public InputEditView getInputEditView()
	{
		return this.inputEditView;
	}

	/**
	 * Update the Edited-Input in the Input-Edit-Model if a input is selected.
	 * 
	 * @param editedModuleGeneratorTypeData 
	 * 			is the edited ModuleGenerator-Type Data.
	 * @param selectedTimeline 
	 * 			is the Selected Timeline.
	 * @param inputData
	 * 			is the edited input data.
	 * @param editInput
	 * 			<code>true</code> if a input edited.
	 */
	public void updateEditedInput(ModuleGeneratorTypeData editedModuleGeneratorTypeData, 
	                              Timeline selectedTimeline, 
	                              InputData inputData,
	                              boolean editInput)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		//List<InputTypeSelectItem> inputTypeSelectItems;
		//InputTypeData inputTypeData;
		List<GeneratorSelectItem> generatorSelectItems;
		Timeline inputTimeline;
		List<ValueSelectItem> valueSelectItems;
		String value;
		List<ModuleInputTypeSelectItem> moduleInputTypeSelectItems;
		InputTypeData moduleInputTypeData;
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Make InputType-SelectItems:
		{
			//inputTypeSelectItems = this.updateEditedInputType(selectedTimeline);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		if (editInput == true)
		{
//			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//			// Input-Type:
//			
//			if (inputData != null)
//			{
//				inputTypeData = inputData.getInputTypeData();
//			}
//			else
//			{
//				inputTypeData = null;
//			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make Generator-SelectItems:
			{
				generatorSelectItems = new Vector<GeneratorSelectItem>();
//				Iterator<Generator> generatorsIterator = editedModuleGeneratorTypeData.getGeneratorsIterator();
				Iterator<Timeline> timelinesIterator = timelineManagerLogic.getTimelinesIterator();
				if (timelinesIterator != null)
				{
					GeneratorSelectItem noSelectItem = new GeneratorSelectItem(null);
					generatorSelectItems.add(noSelectItem);
					while (timelinesIterator.hasNext())
					{
						Timeline timeline = timelinesIterator.next();
						
						// Edited generator is not input generator?
						if (selectedTimeline != timeline)
						{
							// Do not serve the edited generator as input generator.
							
							// Output generators of edited generator is not a input generator?
							if (selectedTimeline.checkIsOutputTimeline(timeline) == false)
							{
								// Do not serve the output generators of edited generator as input generator.
								generatorSelectItems.add(new GeneratorSelectItem(timeline));
							}
						}
					}
					
					Collections.sort(generatorSelectItems);
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Input-Timeline:
			
			if (inputData != null)
			{
//				inputTimeline = inputData.getInputGenerator();
				inputTimeline = selectedTimeline.searchInputTimeline(inputData);
			}
			else
			{
				inputTimeline = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make Value-SelectItems:
			{
				valueSelectItems = new Vector<ValueSelectItem>();
				
				valueSelectItems.add(new ValueSelectItem("0,0"));
				valueSelectItems.add(new ValueSelectItem("0,25"));
				valueSelectItems.add(new ValueSelectItem("0,5"));
				valueSelectItems.add(new ValueSelectItem("0,75"));
				valueSelectItems.add(new ValueSelectItem("1,0"));
				valueSelectItems.add(new ValueSelectItem("2,0"));
				
				for (int octave = ToneFrequences.OCTAVE_MIN; octave <= ToneFrequences.OCTAVE_MAX; octave++)
				{
					Tone[] tones = ToneFrequences.Tone.values();
					
					for (Tone tone : tones)
					{
						float frequence = ToneFrequences.makeFrequence(octave, tone);
						
						String frequenceValue = OutputUtils.makeFloatEditText(frequence);
						
						valueSelectItems.add(new ValueSelectItem(frequenceValue, octave, tone));
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Value:
			
			if (inputData != null)
			{
				MultiValue multiValue = new MultiValue();
				multiValue.floatValue = inputData.getInputValue();
				multiValue.stringValue = inputData.getInputStringValue();
				value = OutputUtils.makeMultiValueEditText(multiValue);
			}
			else
			{
				value = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make ModuleInputType-SelectItems:
			{
				moduleInputTypeSelectItems = new Vector<ModuleInputTypeSelectItem>();
				Iterator<InputTypeData> moduleInputTypeIterator = editedModuleGeneratorTypeData.getInputTypesIterator();
				if (moduleInputTypeIterator != null)
				{
					ModuleInputTypeSelectItem noSelectItem = new ModuleInputTypeSelectItem(null);
					moduleInputTypeSelectItems.add(noSelectItem);
					while (moduleInputTypeIterator.hasNext())
					{
						InputTypeData inputTypeData2 = moduleInputTypeIterator.next();
						
						moduleInputTypeSelectItems.add(new ModuleInputTypeSelectItem(inputTypeData2));
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// ModuleInput-Type:
			
			if (inputData != null)
			{
				moduleInputTypeData = inputData.getInputModuleInputTypeData();
			}
			else
			{
				moduleInputTypeData = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		else
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//inputTypeSelectItems = null;
//			inputTypeData = null;
			generatorSelectItems = null;
			inputTimeline = null;
			valueSelectItems = null;
			value = null;
			moduleInputTypeSelectItems = null;
			moduleInputTypeData = null;
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}

		//------------------------------------------------------------------------------------------
		//this.inputEditModel.setInputTypeSelectItems(inputTypeSelectItems);
//		this.inputEditModel.setInputTypeData(inputTypeData);
		this.inputEditModel.setGeneratorSelectItems(generatorSelectItems);
		this.inputEditModel.setInputTimeline(inputTimeline);
		this.inputEditModel.setValueSelectItems(valueSelectItems);
		this.inputEditModel.setValue(value);
		this.inputEditModel.setModuleInputTypeSelectItems(moduleInputTypeSelectItems);
		this.inputEditModel.setModuleInputTypeData(moduleInputTypeData);
		
		//==========================================================================================
	}

	/**
	 * Make InputType-SelectItems.
	 * 
	 * @param selectedTimeline
	 * 			is the selected timeline.
	 * @return
	 * 		the InputTypeSelectItems.
	 */
	private List<InputTypeSelectItem> updateEditedInputType(Timeline selectedTimeline)
	{
		//==========================================================================================
		List<InputTypeSelectItem> inputTypeSelectItems;
		
		if (selectedTimeline != null)
		{
			inputTypeSelectItems = new Vector<InputTypeSelectItem>();
			Generator selectedGenerator = selectedTimeline.getGenerator();
			GeneratorTypeData generatorTypeData = selectedGenerator.getGeneratorTypeData();
			Iterator<InputTypeData> inputTypeIterator = generatorTypeData.getInputTypesIterator();
			if (inputTypeIterator != null)
			{
				InputTypeSelectItem noSelectItem = new InputTypeSelectItem(null);
				inputTypeSelectItems.add(noSelectItem);
				while (inputTypeIterator.hasNext())
				{
					InputTypeData inputTypeData2 = inputTypeIterator.next();
					
					inputTypeSelectItems.add(new InputTypeSelectItem(inputTypeData2));
				}
			}
		}
		else
		{
			inputTypeSelectItems = null;
		}
		//==========================================================================================
		return inputTypeSelectItems;
	}

	/**
	 * Submit edited Input.
	 * 
	 * @param inputSelectModel
	 * 			is the input Select Model.
	 * @param selectedTimeline
	 * 			is the selected Timeline.
	 */
	public void doSubmit(final InputSelectModel inputSelectModel,
	                     final Timeline selectedTimeline)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		// Input is selceted?
		if (inputSelectModel.getSelectedRowNo() != null)
		{
			InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)this.inputEditView.getInputTypeComboBox().getSelectedItem();
			GeneratorSelectItem inputGeneratorSelectItem = (GeneratorSelectItem)this.inputEditView.getInputGeneratorComboBox().getSelectedItem();
			Object valueSelectedValue = this.inputEditView.getInputTypeValueTextField().getSelectedItem();
			ModuleInputTypeSelectItem moduleInputTypeSelectItem = (ModuleInputTypeSelectItem)this.inputEditView.getModuleInputTypeComboBox().getSelectedItem();
				
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
			Timeline inputTimeline = inputGeneratorSelectItem.getTimeline();
			MultiValue multiValue;
			{
				String valueStr;
				
				if (valueSelectedValue instanceof ValueSelectItem)
				{
					ValueSelectItem valueSelectItem = (ValueSelectItem)valueSelectedValue;
					
					valueStr = valueSelectItem.getValue();
				}
				else
				{
					valueStr = valueSelectedValue.toString();
				}
				
				multiValue = InputUtils.makeMultiValue(valueStr);
			}
			InputTypeData moduleInputTypeData = moduleInputTypeSelectItem.getInputTypeData();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Input-Data:
			
			Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
			
			// Input selected?
			if (selectedRowNo != null)
			{
				InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
				
				InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(selectedRowNo);
				
				InputData inputData = inputSelectEntryModel.getInputData();
				
				// Existing Input selected?
				if (inputData != null)
				{
					// Update selected Input:
					
					timelineManagerLogic.updateInput(selectedTimeline,
					                                 inputData,
					                                 inputTimeline, 
					                                 inputTypeData, 
					                                 multiValue.floatValue, multiValue.stringValue,
					                                 moduleInputTypeData);
	//				inputData.setInputGenerator(inputGenerator);
	//				inputData.setInputValue(multiValue.floatValue, multiValue.stringValue);
	//				inputData.setInputModuleInputTypeData(moduleInputTypeData);
				}
				else
				{
					// Insert new Input:
					inputData = 
						timelineManagerLogic.addGeneratorInput(selectedTimeline,
						                                       inputTimeline, 
						                                       inputTypeData, 
						                                       multiValue.floatValue, multiValue.stringValue,
						                                       moduleInputTypeData);
					
					inputSelectEntryModel.setInputData(inputData);
				}
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Input-Edit-Model:
			
			this.inputEditModel.setInputTypeData(inputTypeSelectItem.getInputTypeData());
			this.inputEditModel.setInputTimeline(inputTimeline);
			{
				String valueStr = OutputUtils.makeMultiValueEditText(multiValue);
				this.inputEditModel.setValue(valueStr);
			}
			this.inputEditModel.setModuleInputTypeData(moduleInputTypeSelectItem.getInputTypeData());
			
			//--------------------------------------------------------------------------------------
			this.appModelChangedObserver.notifyAppModelChanged();
		}
		//==========================================================================================
	}

	/**
	 * Do create new input of selected input type.
	 */
	public void doCreateNewInput(final InputSelectController inputSelectController)
	{
		//==========================================================================================
		InputTypeData inputTypeData;
		
		inputTypeData = this.inputEditModel.getInputTypeData();
		
		if (inputTypeData != null)
		{
			inputSelectController.doCreateNewInput(inputTypeData);
		}
		else
		{
			JOptionPane.showMessageDialog(this.inputEditView,
			                              "Please select a input type.", 
			                              "No input type", 
			                              JOptionPane.OK_OPTION);
		}
		//==========================================================================================
	}

}
