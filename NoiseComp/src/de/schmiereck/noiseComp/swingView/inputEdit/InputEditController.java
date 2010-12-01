/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;

import de.schmiereck.noiseComp.frequences.ToneFrequences;
import de.schmiereck.noiseComp.frequences.ToneFrequences.Tone;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
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
	 */
	public InputEditController(final InputSelectModel inputSelectModel, 
	                           final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.appModelChangedObserver = appModelChangedObserver;
		
		this.inputEditModel = new InputEditModel();
		
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
	 * Update the Edited-Input in the Input-Edit-Model.
	 * 
	 * @param editedModulGeneratorTypeData 
	 * 			is the edited Modul-Generator-Type Data.
	 * @param selectedTimeline 
	 * 			is the Selected Timeline.
	 * @param inputData
	 * 			is the edited input data.
	 * @param editInput
	 * 			<code>true</code> if a input edited.
	 */
	public void updateEditedInput(ModulGeneratorTypeData editedModulGeneratorTypeData, 
	                              Timeline selectedTimeline, 
	                              InputData inputData,
	                              boolean editInput)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		List<InputTypeSelectItem> inputTypeSelectItems;
		InputTypeData inputTypeData;
		List<GeneratorSelectItem> generatorSelectItems;
		Timeline inputTimeline;
		List<ValueSelectItem> valueSelectItems;
		String value;
		List<ModulInputTypeSelectItem> modulInputTypeSelectItems;
		InputTypeData modulInputTypeData;
		
		if (editInput == true)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make InputType-SelectItems:
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
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Input-Type:
			
			if (inputData != null)
			{
				inputTypeData = inputData.getInputTypeData();
			}
			else
			{
				inputTypeData = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make Generator-SelectItems:
			{
				generatorSelectItems = new Vector<GeneratorSelectItem>();
//				Iterator<Generator> generatorsIterator = editedModulGeneratorTypeData.getGeneratorsIterator();
				Iterator<Timeline> timelinesIterator = timelineManagerLogic.getTimelinesIterator();
				if (timelinesIterator != null)
				{
					GeneratorSelectItem noSelectItem = new GeneratorSelectItem(null);
					generatorSelectItems.add(noSelectItem);
					while (timelinesIterator.hasNext())
					{
						Timeline timeline = timelinesIterator.next();
						
						generatorSelectItems.add(new GeneratorSelectItem(timeline));
					}
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
			// Make ModulInputType-SelectItems:
			{
				modulInputTypeSelectItems = new Vector<ModulInputTypeSelectItem>();
				Iterator<InputTypeData> modulInputTypeIterator = editedModulGeneratorTypeData.getInputTypesIterator();
				if (modulInputTypeIterator != null)
				{
					ModulInputTypeSelectItem noSelectItem = new ModulInputTypeSelectItem(null);
					modulInputTypeSelectItems.add(noSelectItem);
					while (modulInputTypeIterator.hasNext())
					{
						InputTypeData inputTypeData2 = modulInputTypeIterator.next();
						
						modulInputTypeSelectItems.add(new ModulInputTypeSelectItem(inputTypeData2));
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Modul-Input-Type:
			
			if (inputData != null)
			{
				modulInputTypeData = inputData.getInputModulInputTypeData();
			}
			else
			{
				modulInputTypeData = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		else
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			inputTypeSelectItems = null;
			inputTypeData = null;
			generatorSelectItems = null;
			inputTimeline = null;
			valueSelectItems = null;
			value = null;
			modulInputTypeSelectItems = null;
			modulInputTypeData = null;
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}

		//------------------------------------------------------------------------------------------
		this.inputEditModel.setInputTypeSelectItems(inputTypeSelectItems);
		this.inputEditModel.setInputTypeData(inputTypeData);
		this.inputEditModel.setGeneratorSelectItems(generatorSelectItems);
		this.inputEditModel.setInputTimeline(inputTimeline);
		this.inputEditModel.setValueSelectItems(valueSelectItems);
		this.inputEditModel.setValue(value);
		this.inputEditModel.setModulInputTypeSelectItems(modulInputTypeSelectItems);
		this.inputEditModel.setModulInputTypeData(modulInputTypeData);
		
		//==========================================================================================
	}

	/**
	 * Submit edited Input.
	 * 
	 * @param selectModel
	 * 			is the Select Model.
	 * @param selectedTimeline
	 * 			is the selected Timeline.
	 */
	public void doSubmit(final InputSelectModel selectModel,
	                     final Timeline selectedTimeline)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)this.inputEditView.getInputTypeComboBox().getSelectedItem();
		GeneratorSelectItem inputGeneratorSelectItem = (GeneratorSelectItem)this.inputEditView.getInputGeneratorComboBox().getSelectedItem();
		String valueStr = this.inputEditView.getInputTypeValueTextField().getSelectedItem().toString();
		ModulInputTypeSelectItem modulInputTypeSelectItem = (ModulInputTypeSelectItem)this.inputEditView.getModulInputTypeComboBox().getSelectedItem();
			
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
		Timeline inputTimeline = inputGeneratorSelectItem.getTimeline();
		MultiValue multiValue = InputUtils.makeMultiValue(valueStr);
		InputTypeData modulInputTypeData = modulInputTypeSelectItem.getInputTypeData();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Input-Data:
		
		Integer selectedRowNo = selectModel.getSelectedRowNo();
		
		// Input selected?
		if (selectedRowNo != null)
		{
			InputsTabelModel inputsTabelModel = selectModel.getInputsTabelModel();
			
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
				                                 modulInputTypeData);
//				inputData.setInputGenerator(inputGenerator);
//				inputData.setInputValue(multiValue.floatValue, multiValue.stringValue);
//				inputData.setInputModulInputTypeData(modulInputTypeData);
			}
			else
			{
				// Insert new Input:
				inputData = 
					timelineManagerLogic.addInputGenerator(selectedTimeline,
					                                       inputTimeline, 
					                                       inputTypeData, 
					                                       multiValue.floatValue, multiValue.stringValue,
					                                       modulInputTypeData);
				
				inputSelectEntryModel.setInputData(inputData);
			}
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input-Edit-Model:
		
		this.inputEditModel.setInputTypeData(inputTypeSelectItem.getInputTypeData());
		this.inputEditModel.setInputTimeline(inputGeneratorSelectItem.getTimeline());
		this.inputEditModel.setValue(valueStr);
		this.inputEditModel.setModulInputTypeData(modulInputTypeSelectItem.getInputTypeData());
		
		//------------------------------------------------------------------------------------------
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}

}
