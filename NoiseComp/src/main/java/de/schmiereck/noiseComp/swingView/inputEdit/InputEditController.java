/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import de.schmiereck.noiseComp.frequences.ToneFrequences;
import de.schmiereck.noiseComp.frequences.ToneFrequences.Tone;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
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
public class InputEditController {
	//**********************************************************************************************
	// Fields:

	private final SoundSourceLogic soundSourceLogic;

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
	public InputEditController(final TimelinesDrawPanelController timelinesDrawPanelController,
							   final SoundSourceData soundSourceData,
							   final SoundSourceLogic soundSourceLogic,
	                           final InputSelectModel inputSelectModel, 
	                           final AppModelChangedObserver appModelChangedObserver,
	                           final SelectedTimelineModel selectedTimelineModel) {
		//==========================================================================================
		this.soundSourceLogic = soundSourceLogic;
		this.appModelChangedObserver = appModelChangedObserver;
		
		this.inputEditModel = new InputEditModel(selectedTimelineModel);
		
		this.inputEditView = new InputEditView(this.inputEditModel);
		
		//------------------------------------------------------------------------------------------
		this.inputEditView.getInputTypeComboBox().addActionListener(
		 	new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final JComboBox inputTypeSelectField = (JComboBox)e.getSource();
					final InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)inputTypeSelectField.getSelectedItem();
					
					if (inputTypeSelectItem != null) {
						final InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
						
						if (inputTypeData != null) {
							updateInputTypeDefaultValueField(inputTypeData);
						}
						
						inputEditModel.setInputTypeData(inputTypeData);
					}
				}
		 	}
		);
//		//------------------------------------------------------------------------------------------
//		this.inputEditView.getInputTypeValueTextField().addActionListener
//		(
//		 	new ActionListener()
//		 	{
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
////					JComboBox cb = (JComboBox)e.getSource();
//					JComboBox inputTypeValueTextField = inputEditView.getInputTypeValueTextField();
//					
//					if (inputTypeValueTextField != null)
//					{
//						String valueStr;
//						
//						Object selectedItem = inputTypeValueTextField.getSelectedItem();
//						
//						if (selectedItem instanceof ValueSelectItem)
//						{
//							ValueSelectItem valueSelectItem = (ValueSelectItem)selectedItem;
//							
//							valueStr = valueSelectItem.getValue();
//						}
//						else
//						{
//							valueStr = InputUtils.makeStringValue((String)selectedItem);
//						}
//						
////						if (valueStr == null)
//						{
////							inputTypeValueTextField.setSelectedItem(valueStr);
//							//inputEditModel.setValue(valueStr);
//						}
//					}
//				}
//		 	}
//		);
		//------------------------------------------------------------------------------------------
		selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();

					final List<InputTypeSelectItem> inputTypeSelectItems;
					
					if (selectedTimelineSelectEntryModel != null) {
						final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
						
						inputTypeSelectItems = updateEditedInputType(selectedTimeline);
					} else {
						inputTypeSelectItems = null;
					}
					
					inputEditModel.setInputTypeSelectItems(inputTypeSelectItems);
				}
			}
		);
		//------------------------------------------------------------------------------------------
		{
			final JButton updateButton = this.inputEditView.getUpdateButton();

			final TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();

			final InputEditUpdateAction inputEditUpdateAction = new InputEditUpdateAction(soundSourceData,
					this, inputSelectModel, timelinesDrawPanelModel);
			
			updateButton.setAction(inputEditUpdateAction);
		}
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
	 * Update the Edited-Input in the Input-Edit-Model if an input is selected.
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
	public void updateEditedInput(final ModuleGeneratorTypeInfoData editedModuleGeneratorTypeData,
								  final Timeline selectedTimeline,
								  final InputData inputData,
								  final boolean editInput) {
		//==========================================================================================
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		//List<InputTypeSelectItem> inputTypeSelectItems;
		//InputTypeData inputTypeData;
		final List<GeneratorSelectItem> generatorSelectItems;
		final Timeline inputTimeline;
		final List<ValueSelectItem> valueSelectItems;
		final String value;
		final List<ModuleInputTypeSelectItem> moduleInputTypeSelectItems;
		final InputTypeData moduleInputTypeData;
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Make InputType-SelectItems:
		{
			//inputTypeSelectItems = this.updateEditedInputType(selectedTimeline);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		if (editInput == true) {
//			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//			// Input-Type:
//			
//			if (inputData != null) {
//				inputTypeData = inputData.getInputTypeData();
//			} else {
//				inputTypeData = null;
//			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make Generator-SelectItems:
			{
				generatorSelectItems = new Vector<GeneratorSelectItem>();
//				Iterator<Generator> generatorsIterator = editedModuleGeneratorTypeData.getGeneratorsIterator();
				final Iterator<Timeline> timelinesIterator = timelineManagerLogic.getTimelinesIterator();
				if (timelinesIterator != null) {
					final GeneratorSelectItem noSelectItem = new GeneratorSelectItem(null);

					generatorSelectItems.add(noSelectItem);

					while (timelinesIterator.hasNext()) {
						final Timeline timeline = timelinesIterator.next();
						
						// Edited generator is not input generator?
						if (selectedTimeline != timeline) {
							// Do not serve the edited generator as input generator.
							
							// Output generators of edited generator is not a input generator?
							if (selectedTimeline.checkIsOutputTimeline(timeline) == false) {
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
			
			if (inputData != null) {
//				inputTimeline = inputData.getInputGenerator();
				inputTimeline = selectedTimeline.searchInputTimeline(inputData);
			} else {
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
				
				for (int octave = ToneFrequences.OCTAVE_MIN; octave <= ToneFrequences.OCTAVE_MAX; octave++) {
					final Tone[] tones = ToneFrequences.Tone.values();
					
					for (final Tone tone : tones) {
						final float frequence = ToneFrequences.makeFrequence(octave, tone);
						final String frequenceValue = OutputUtils.makeFloatEditText(frequence);
						
						valueSelectItems.add(new ValueSelectItem(frequenceValue, octave, tone));
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Value:
			
			if (inputData != null) {
				MultiValue multiValue = new MultiValue();
				multiValue.floatValue = inputData.getInputValue();
				multiValue.stringValue = inputData.getInputStringValue();
				value = OutputUtils.makeMultiValueEditText(multiValue);
			} else {
				value = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Make ModuleInputType-SelectItems:
			{
				moduleInputTypeSelectItems = new Vector<ModuleInputTypeSelectItem>();
				final Iterator<InputTypeData> moduleInputTypeIterator = editedModuleGeneratorTypeData.getInputTypesIterator();
				if (moduleInputTypeIterator != null) {
					final ModuleInputTypeSelectItem noSelectItem = new ModuleInputTypeSelectItem(null);
					moduleInputTypeSelectItems.add(noSelectItem);

					while (moduleInputTypeIterator.hasNext()) {
						InputTypeData inputTypeData2 = moduleInputTypeIterator.next();
						
						moduleInputTypeSelectItems.add(new ModuleInputTypeSelectItem(inputTypeData2));
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// ModuleInput-Type:
			
			if (inputData != null) {
				moduleInputTypeData = inputData.getInputModuleInputTypeData();
			} else {
				moduleInputTypeData = null;
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		} else {
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
	private List<InputTypeSelectItem> updateEditedInputType(final Timeline selectedTimeline) {
		//==========================================================================================
		final List<InputTypeSelectItem> inputTypeSelectItems;
		
		if (selectedTimeline != null) {
			inputTypeSelectItems = new Vector<InputTypeSelectItem>();
			final Generator selectedGenerator = selectedTimeline.getGenerator();
			final GeneratorTypeInfoData generatorTypeInfoData = selectedGenerator.getGeneratorTypeData();
			final Iterator<InputTypeData> inputTypeIterator = generatorTypeInfoData.getInputTypesIterator();
			if (inputTypeIterator != null) {
				final InputTypeSelectItem noSelectItem = new InputTypeSelectItem(null);
				inputTypeSelectItems.add(noSelectItem);

				while (inputTypeIterator.hasNext()) {
					final InputTypeData inputTypeData2 = inputTypeIterator.next();
					
					inputTypeSelectItems.add(new InputTypeSelectItem(inputTypeData2));
				}
			}
		} else {
			inputTypeSelectItems = null;
		}
		//==========================================================================================
		return inputTypeSelectItems;
	}

	/**
	 * Submit edited Input.
	 *
	 * @param selectedTimeline is the selected Timeline.
	 * @param inputSelectModel is the input Select Model.
	 */
	public void doSubmitInput(final SoundSourceData soundSourceData,
							  final Timeline selectedTimeline, final InputSelectModel inputSelectModel) {
		//==========================================================================================
		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		// Input is selected?
		if (Objects.nonNull(inputSelectModel.getSelectedRowNo())) {
			final InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)this.inputEditView.getInputTypeComboBox().getSelectedItem();
			final GeneratorSelectItem inputGeneratorSelectItem = (GeneratorSelectItem)this.inputEditView.getInputGeneratorComboBox().getSelectedItem();
			final Object valueSelectedValue = this.inputEditView.getInputTypeValueTextField().getSelectedItem();
			final ModuleInputTypeSelectItem moduleInputTypeSelectItem = (ModuleInputTypeSelectItem)this.inputEditView.getModuleInputTypeComboBox().getSelectedItem();
				
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
			final Timeline inputTimeline = inputGeneratorSelectItem.getTimeline();
			final MultiValue multiValue;
			{
				final String valueStr;
				
				if (valueSelectedValue instanceof ValueSelectItem) {
					ValueSelectItem valueSelectItem = (ValueSelectItem)valueSelectedValue;
					valueStr = valueSelectItem.getValue(); }
				else {
					valueStr = valueSelectedValue.toString();
				}
				
				multiValue = InputUtils.makeMultiValue(valueStr);
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Input-Data:
			final InputTypeData moduleInputTypeData = moduleInputTypeSelectItem.getInputTypeData();

			final Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
			
			// Input selected?
			if (Objects.nonNull(selectedRowNo)) {
				final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();

				final InputSelectEntryModel selectedInputSelectEntryModel = inputsTabelModel.getRow(selectedRowNo);

				final InputData selectedInputData = selectedInputSelectEntryModel.getInputData();
				
				// Existing Input selected?
				if (Objects.nonNull(selectedInputData)) {
					// Update selected Input:
					timelineManagerLogic.updateInput(soundSourceData,
							                         selectedTimeline,
					                                 selectedInputData,
					                                 inputTimeline,
					                                 inputTypeData,
					                                 multiValue.floatValue, multiValue.stringValue,
					                                 moduleInputTypeData);
	//				selectedInputData.setInputGenerator(inputGenerator);
	//				selectedInputData.setInputValue(multiValue.floatValue, multiValue.stringValue);
	//				selectedInputData.setInputModuleInputTypeData(moduleInputTypeData);
				} else {
					// Insert new Input:
					final InputData newInputData =
							timelineManagerLogic.addGeneratorInput(soundSourceData, selectedTimeline,
						                                       inputTimeline, 
						                                       inputTypeData, 
						                                       multiValue.floatValue, multiValue.stringValue,
						                                       moduleInputTypeData);
					
					selectedInputSelectEntryModel.setInputData(newInputData);
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
	public void doCreateNewInput(final SoundSourceData soundSourceData,
								 final InputSelectController inputSelectController) {
		//==========================================================================================
		final InputTypeData inputTypeData;
		
		inputTypeData = this.inputEditModel.getInputTypeData();
		
		if (Objects.nonNull(inputTypeData)) {
			inputSelectController.doCreateNewInput(soundSourceData, inputTypeData);

			this.updateInputTypeDefaultValueField(inputTypeData);
		} else {
			JOptionPane.showMessageDialog(this.inputEditView,
			                              "Please select a input type.", 
			                              "No input type", 
			                              JOptionPane.OK_OPTION);
		}
		//==========================================================================================
	}

	private void updateInputTypeDefaultValueField(final InputTypeData inputTypeData) {
		//==========================================================================================
		final JComboBox inputTypeValueTextField = this.inputEditView.getInputTypeValueTextField();
		
		//==========================================================================================
		//String valueStr = InputUtils.makeStringValue(inputTypeValueTextField.getText());
		//
		//if (valueStr == null)
		{
			final Float defaultValue = inputTypeData.getDefaultValue();
			final String defaultValueStr = OutputUtils.makeFloatEditText(defaultValue);
			
			inputTypeValueTextField.setSelectedItem(defaultValueStr);
		}
		//==========================================================================================
	}
}
