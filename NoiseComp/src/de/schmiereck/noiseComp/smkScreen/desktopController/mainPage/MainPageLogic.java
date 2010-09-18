package de.schmiereck.noiseComp.smkScreen.desktopController.mainPage;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.GeneratorInputSelectedListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.GeneratorSelectedListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputsWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.SelectData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.SelectEntryData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.EditData;
import de.schmiereck.noiseComp.smkScreen.desktopController.EditGeneratorChangedListener;
import de.schmiereck.screenTools.controller.ControllerLogic;

/**
 * Main Page Logic.
 *
 * @author smk
 * @version <p>12.04.2004: created, smk</p>
 */
public class MainPageLogic
implements GeneratorSelectedListenerInterface,
GeneratorInputSelectedListenerInterface,
EditGeneratorChangedListener
{
	private ControllerLogic controllerLogic;
	private MainPageData mainPageData;

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public MainPageLogic(ControllerLogic controllerLogic, 
						 MainPageData mainPageData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.mainPageData = mainPageData;
		
		this.registerEditGeneratorChangedListener(this);
		
		this.mainPageData.getTracksListWidgetData().registerGeneratorSelectedListener(this);
		
		this.mainPageData.getGeneratorInputsData().registerGeneratorInputSelectedListener(this);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface#notifyGeneratorSelected(de.schmiereck.noiseComp.desktopPage.widgets.TrackData)
	 */
	public void notifyGeneratorSelected(TrackData trackGraficData)
	{
		Generator generator;
		GeneratorTypeData generatorTypeData;
		String name;
		String startTime;
		String endTime;
		//Vector inputs;
		
		if (trackGraficData != null)
		{
			generator = trackGraficData.getGenerator();
			
			if (generator != null)
			{	
				name = generator.getName();
				//generatorTypeData = this.mainPageData.searchGeneratorTypeData(generator);
				generatorTypeData = generator.getGeneratorTypeData();
				startTime = Float.toString(generator.getStartTimePos());
				endTime = Float.toString(generator.getEndTimePos());
				//inputs = generator.getInputs();
			}
			else
			{
				name = "";
				generatorTypeData = null;
				startTime = "";
				endTime = "";
				//inputs = null;
			}
		}
		else
		{
			generator = null;
			generatorTypeData = null;
			name = "";
			startTime = "";
			endTime = "";
			// = null;
		}
		
		this.mainPageData.getGeneratorNameInputlineData().setInputText(name);
		this.mainPageData.getGeneratorStartTimeInputlineData().setInputText(startTime);
		this.mainPageData.getGeneratorEndTimeInputlineData().setInputText(endTime);
		this.mainPageData.getGeneratorInputsData().setGeneratorInputs(generator);

		//--------------------------------------------------
		// Build the Generator-Names select List:
		{
			SelectData inputNameSelectData = this.mainPageData.getGeneratorInputNameSelectData();
			
			inputNameSelectData.clearSelectEntrys();
			
			inputNameSelectData.setUseEmptyEntry(true);
			
			if (generatorTypeData != null)
			{	
				Iterator<TrackData> tracksDataIterator = this.mainPageData.getTracksListWidgetData().getListEntrysIterator();
				
				while (tracksDataIterator.hasNext())
				{
					TrackData trackData = tracksDataIterator.next();
					
					SelectEntryData selectEntryData = new SelectEntryData(trackData.getName(), 
							trackData.getName(),
							trackData);
					
					inputNameSelectData.addSelectEntryData(selectEntryData);
				}
			}
		}

		//--------------------------------------------------
		// Build the Generator-Input-Types select List:
		{
			SelectData inputTypeSelectData = this.mainPageData.getGeneratorInputTypeSelectData();
			
			inputTypeSelectData.clearSelectEntrys();
			
			if (generatorTypeData != null)
			{	
				Iterator<InputTypeData> inputTypesDataIterator = generatorTypeData.getInputTypesIterator();
				
				while (inputTypesDataIterator.hasNext())
				{
					InputTypeData inputTypeData = inputTypesDataIterator.next();
					
					SelectEntryData selectEntryData = new SelectEntryData(Integer.valueOf(inputTypeData.getInputType()), 
							inputTypeData.getInputTypeName(),
							inputTypeData);
					
					inputTypeSelectData.addSelectEntryData(selectEntryData);
				}
			}	
		}
		//--------------------------------------------------
		// Build the Generator-Modul-Inputs select List:
		{
			SelectData inputModulInputSelectData = this.mainPageData.getGeneratorInputModulInputSelectData();
			
			inputModulInputSelectData.clearSelectEntrys();
			
			inputModulInputSelectData.setUseEmptyEntry(true);
			
			ModulGeneratorTypeData modulGeneratorTypeData = this.mainPageData.getEditModulTypeData();			
			
			if (modulGeneratorTypeData != null)
			{
				Iterator<InputTypeData> inputTypesIterator = modulGeneratorTypeData.getInputTypesIterator();
				
				if (inputTypesIterator != null)
				{	
					while (inputTypesIterator.hasNext())
					{
						InputTypeData inputTypeData = inputTypesIterator.next();
						
						SelectEntryData selectEntryData = new SelectEntryData(inputTypeData.getInputTypeName(), 
								inputTypeData.getInputTypeName(),
								inputTypeData);
						
						inputModulInputSelectData.addSelectEntryData(selectEntryData);
					}
				}
			}
		}
		
		this.notifyInputsChanged();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface#notifyGeneratorDeselected(de.schmiereck.noiseComp.desktopPage.widgets.TrackData)
	 */
	public void notifyGeneratorDeselected(TrackData trackData)
	{
		this.mainPageData.getGeneratorNameInputlineData().setInputText("");
		this.mainPageData.getGeneratorInputNameSelectData().clearSelectEntrys();
		this.mainPageData.getGeneratorInputTypeSelectData().clearSelectEntrys();
		this.mainPageData.getGeneratorStartTimeInputlineData().setInputText("");
		this.mainPageData.getGeneratorEndTimeInputlineData().setInputText("");
		this.mainPageData.getGeneratorInputsData().setGeneratorInputs(null);
		this.mainPageData.getGeneratorInputModulInputSelectData().clearSelectEntrys();
		
		this.notifyInputsChanged();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorTypeSelectedListenerInterface#notifyGeneratorInputSelected(InputsWidgetData, de.schmiereck.noiseComp.generator.InputData)
	 */
	public void notifyGeneratorInputSelected(InputsWidgetData inputsData, InputData selectedInputData)
	{
		DesktopControllerData desktopControllerData = this.mainPageData.getDesktopControllerData();

		String inputGeneratorName;
		String inputGeneratorTypeDescription;
		String inputValue;
		int inputType;
		InputTypeData inputModulInputTypeData;
		
		if (selectedInputData != null)
		{
			InputTypeData inputTypeData = selectedInputData.getInputTypeData();
			inputType = inputTypeData.getInputType();
			
			Generator generator = selectedInputData.getInputGenerator();
			
			if (generator != null)
			{	
				inputGeneratorName = generator.getName();

//				GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
				
				inputGeneratorTypeDescription = inputTypeData.getInputTypeDescription();
			}
			else
			{
				inputGeneratorName = null;
				inputGeneratorTypeDescription = null;
			}
			
			if (selectedInputData.getInputValue() != null)
			{	
				inputValue = String.valueOf(selectedInputData.getInputValue());
			}
			else
			{	
				if (selectedInputData.getInputStringValue() != null)
				{	
					inputValue = selectedInputData.getInputStringValue();
				}
				else
				{	
					inputValue = null;
				}
			}
			
			inputModulInputTypeData = selectedInputData.getInputModulInputTypeData();
		}
		else
		{
			inputGeneratorName = null;
			inputGeneratorTypeDescription = null;
			inputType = 0;
			inputValue = null;
			inputModulInputTypeData = null;
		}
		
		//this.mainPageData.getGeneratorInputNameInputlineData().setInputText(inputGeneratorName);
		this.mainPageData.getGeneratorInputNameSelectData().setInputPosByValue(inputGeneratorName);
		
		SelectData selectData = this.mainPageData.getGeneratorInputTypeSelectData();

		selectData.setInputPosByValue(Integer.valueOf(inputType));

		this.mainPageData.getGeneratorInputValueInputlineData().setInputText(inputValue);

		//DDD
		if (inputModulInputTypeData != null)
		{	
			this.mainPageData.getGeneratorInputModulInputSelectData().setInputPosByValue(inputModulInputTypeData.getInputTypeName());
		}
		else
		{
			this.mainPageData.getGeneratorInputModulInputSelectData().setInputPos(-1);
		}
		
		this.mainPageData.getGeneratorInputTypeDescriptionTextWidgetData().setLabelText(inputGeneratorTypeDescription);

		this.controllerLogic.dataChanged(desktopControllerData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorTypeSelectedListenerInterface#notifyGeneratorInputDeselected(InputsWidgetData, de.schmiereck.noiseComp.generator.InputData)
	 */
	public void notifyGeneratorInputDeselected(InputsWidgetData inputsData, InputData deselectedInputData)
	{
		DesktopControllerData desktopControllerData = this.mainPageData.getDesktopControllerData();

		//this.mainPageData.getGeneratorInputNameInputlineData().setInputText("");
		this.mainPageData.getGeneratorInputNameSelectData().setInputPos(0);
		this.mainPageData.getGeneratorInputTypeSelectData().setInputPos(0);
		this.mainPageData.getGeneratorInputValueInputlineData().setInputText("");
		this.mainPageData.getGeneratorInputModulInputSelectData().setInputPos(-1);

		this.controllerLogic.dataChanged(desktopControllerData);
	}
	/*
	public void addTrackData(TrackData trackData)
	{
		TracksListWidgetData tracksListWidgetData = this.mainPageData.getTracksListWidgetData();
		
		tracksListWidgetData.addTrack(trackData);
	}
	*/
	/**
	 * Set the new Input-Settings for the selected Input.
	 * If there is no selected input, {@link #addInput(InputsWidgetData)} is called.
	 *
	 * TODO replace the RuntimeExceptions with Message-Boxes, smk 
	 * @param generatorInputsData
	 * @param insertNew	true, if the input should by inserted as a new input.<br/>
	 * 					false, if the selected input should by updated.
	 */
	public void setInput(/*InputsWidgetData generatorInputsData, */boolean insertNew)
	{
		InputsWidgetData generatorInputsData = this.mainPageData.getGeneratorInputsData();
		
		if (generatorInputsData != null)
		{	
			TrackData selectedTrackData = this.mainPageData.getTracksListWidgetData().getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				//---------------------------------------------------
				// Input-Generator-Name:

				SelectEntryData inputGeneratorSelectEntryData = this.mainPageData.getGeneratorInputNameSelectData().getSelectedEntryData();

				String inputGeneratorName;

				if (inputGeneratorSelectEntryData != null)
				{
					inputGeneratorName = (String)inputGeneratorSelectEntryData.getValue();
				}
				else
				{
					inputGeneratorName = null;
				}
				
				//---------------------------------------------------
				//Input-Generator:

				Generator inputGenerator;
				
				// Select the name of a generator ?
				if (inputGeneratorName != null)
				{	
					if (inputGeneratorName.length() > 0)
					{	
						TrackData inputTrackData;
						inputTrackData = this.mainPageData.getTracksListWidgetData().searchTrackData(inputGeneratorName);
						
						// Found no Track with the name of the Input ?
						if (inputTrackData == null)
						{
							throw new PopupRuntimeException("input generator \"" + inputGeneratorName + "\" not found");
						}
						inputGenerator = inputTrackData.getGenerator();
					}
					else
					{
						inputGenerator = null;
					}
				}
				else
				{
					inputGenerator = null;
				}
				
				//---------------------------------------------------
				// Input-Type:

				SelectEntryData selectedEntryData = this.mainPageData.getGeneratorInputTypeSelectData().getSelectedEntryData();
				InputTypeData inputTypeData = (InputTypeData)selectedEntryData.getEntry();
//				Integer inputType = (Integer)selectedEntryData.getValue();
				
				//---------------------------------------------------
				// Input-Value:

				String inputGeneratorValueStr = this.mainPageData.getGeneratorInputValueInputlineData().getInputText();
				
				Float inputGeneratorValue;
				
				if (inputGeneratorValueStr != null)
				{
					if (inputGeneratorValueStr.length() > 0)
					{	
						try
						{
							inputGeneratorValue = Float.valueOf(inputGeneratorValueStr);
						}
						catch (java.lang.NumberFormatException ex)
						{
							// Ignore and use String.
							inputGeneratorValue = null;
						}
					}
					else
					{
						inputGeneratorValue = null;
					}
				}
				else
				{
					inputGeneratorValue = null;
				}
				
				//---------------------------------------------------
				// Modul-Input:

				SelectEntryData inputGeneratorInputModulInputSelectData = this.mainPageData.getGeneratorInputModulInputSelectData().getSelectedEntryData();
				InputTypeData inputModulInputTypeData;
				
				if (inputGeneratorInputModulInputSelectData != null)
				{	
					//DDD
					inputModulInputTypeData = (InputTypeData)inputGeneratorInputModulInputSelectData.getEntry();
				}
				else
				{
					inputModulInputTypeData = null;
				}

				
				//---------------------------------------------------

				if ((inputGenerator == null) && 
						((inputGeneratorValue == null) && ((inputGeneratorValueStr == null))) && 
						(inputModulInputTypeData == null))
				{
					throw new PopupRuntimeException("no input generator, no value and no modul input type for the input \"" + inputGeneratorName + "\"");
				}

				InputData selectedInputData = this.mainPageData.getGeneratorInputsData().getSelectedInputData();

				//No Input selected or a insert is requested.
				if ((selectedInputData == null) || (insertNew == true))
				{	
					// Insert new input:

					Generator selectedGenerator = selectedTrackData.getGenerator();

					selectedInputData = this.addInput(selectedGenerator, 
													  inputGenerator, 
													  inputTypeData, 
													  inputGeneratorValue, 
													  inputGeneratorValueStr,
													  inputModulInputTypeData);
				}
				else
				{
					// Update selected input:

					selectedInputData.setInputGenerator(inputGenerator);
					//selectedInputData.setInputTypeData(inputTypeData);
					selectedInputData.setInputValue(inputGeneratorValue, inputGeneratorValueStr);
					selectedInputData.setInputModulInputTypeData(inputModulInputTypeData);
				}
			}
			else
			{
				throw new PopupRuntimeException("no generator selected");
			}
		}
		else
		{
			throw new PopupRuntimeException("no generator selected");
		}
		
//		this.notifyInputsChanged();
	}

	/**
	 * TODO notify the listeners that a new input is added (create Listener-Interface), smk
	 * 
	 * @param selectedGenerator
	 * @param inputGeneratorName
	 * @param inputTypeData
	 * @param inputGeneratorValue
	 * @return
	 */
	public InputData addInput(Generator selectedGenerator, 
	                          Generator inputGenerator, 
	                          InputTypeData inputTypeData, 
	                          Float inputGeneratorValue, 
	                          String inputGeneratorValueStr,
	                          InputTypeData inputModulInputTypeData)
	{
		//Generators generators = this.mainPageData.getTracksListWidgetData().getGenerators();
		
		//InputData inputData = generators.addInput(selectedGenerator, inputGenerator, inputTypeData, inputGeneratorValue, inputModulInputTypeData);
		InputData inputData = selectedGenerator.addInputGenerator(inputGenerator, inputTypeData, 
																  inputGeneratorValue, inputGeneratorValueStr,
																  inputModulInputTypeData);
		
		this.mainPageData.getGeneratorInputsData().setSelectedInputData(inputData);
		
		this.notifyInputsChanged();
		
		return inputData;
	}

	/**
	 * Notify that inputs changed.
	 * Recalculates the scrollbar length of the inputs select list.
	 */
	private void notifyInputsChanged()
	{
		{
			int len;
			
			TrackData selectedTrackData = this.mainPageData.getTracksListWidgetData().getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				len = selectedTrackData.getGenerator().getInputsCount();
			}
			else
			{
				len = 0;
			}
			
			this.mainPageData.getInputsVScrollbarData().setScrollerLength(len);
		}
		{
			DesktopControllerData desktopControllerData = this.mainPageData.getDesktopControllerData();
	
			this.controllerLogic.dataChanged(desktopControllerData);
		}
	}

	/**
	 * Changes the actual zoom factor throu the given factor.
	 * 
	 * @param changeZoomFactor is the factor for the zoom factor ;-)
	 */
	public void doChangeZoom(float changeZoomFactor)
	{
		DesktopControllerData desktopControllerData = this.mainPageData.getDesktopControllerData();

		float generatorScaleX = this.mainPageData.getTracksListWidgetData().getGeneratorScaleX();

		generatorScaleX = generatorScaleX * changeZoomFactor;
		
		this.mainPageData.getTracksListWidgetData().setGeneratorScaleX(generatorScaleX);
		
		ScrollbarData scrollbarData = this.mainPageData.getTimelineScrollbarData();
		
		// Anzahl Sekunden die erscrollt werden können.
//		float time = scrollbarData.getScrollerLength();
		int visiblePoints = scrollbarData.getScreenScrollerLength();
		
		// Anzahl Angezeigte Sekunden berechnen.
		float visibleTime = visiblePoints / generatorScaleX;
		
		scrollbarData.setScrollerSize(visibleTime);

		this.controllerLogic.dataChanged(desktopControllerData);
	}

	/**
	 * List of {@link EditGeneratorChangedListener}-Interface Objects
	 * that are notifyed if {@link #setEditGenerators(ModulGeneratorTypeData, Generators)} is called.
	 */
	private Vector<EditGeneratorChangedListener> editGeneratorChangedListeners	= null;
	
	public void registerEditGeneratorChangedListener(EditGeneratorChangedListener editGeneratorChangedListener)
	{
		if (this.editGeneratorChangedListeners == null)
		{
			this.editGeneratorChangedListeners = new Vector<EditGeneratorChangedListener>();
		}
		
		this.editGeneratorChangedListeners.add(editGeneratorChangedListener);
	}
	
	/**
	 * @see #editModulTypeData
	 * @see #editGenerators
	 */
	public void triggerEditGeneratorChanged(EditData editData)
	{
		ModulGeneratorTypeData editModulTypeData = editData.getEditModulTypeData();
		
		if (editModulTypeData != null)
		{
			//Generators editGenerators = editModulTypeData.getGenerators();
		
			if (this.editGeneratorChangedListeners != null)
			{
				Iterator<EditGeneratorChangedListener> editGeneratorChangedListenersIterator = this.editGeneratorChangedListeners.iterator();
				
				while (editGeneratorChangedListenersIterator.hasNext())
				{
					EditGeneratorChangedListener editGeneratorChangedListener = editGeneratorChangedListenersIterator.next();
					
					editGeneratorChangedListener.notifyEditGeneratorChanged(editModulTypeData);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopController.EditGeneratorChangedListener#notifyEditGeneratorChanged(de.schmiereck.noiseComp.generator.ModulGeneratorTypeData)
	 */
	public void notifyEditGeneratorChanged(ModulGeneratorTypeData editModulTypeData)
	{
		DesktopControllerData desktopControllerData = this.mainPageData.getDesktopControllerData();
		
		///desktopControllerData.getSoundData().setGenerators(editGenerators);
		
		if (editModulTypeData != null)
		{	
			this.mainPageData.getModulGeneratorTextWidgetData().setLabelText(editModulTypeData.getGeneratorTypeName());
		}
		else
		{
			this.mainPageData.getModulGeneratorTextWidgetData().setLabelText("<ConsoleMain Modul>");
		}

		this.controllerLogic.dataChanged(desktopControllerData);
	}

	/**
	 * 
	public void clearTracks()
	{
		this.mainPageData.clearTracks();
	}
	 */

	public void storeGeneratorEditData()
	{
		TrackData trackData = this.mainPageData.getTracksListWidgetData().getSelectedTrackData();
		
		if (trackData != null)
		{	
			Generator generator = trackData.getGenerator();
			
			String name = this.mainPageData.getGeneratorNameInputlineData().getInputText();
			String startTime = this.mainPageData.getGeneratorStartTimeInputlineData().getInputText();
			String endTime = this.mainPageData.getGeneratorEndTimeInputlineData().getInputText();

			float startTimePos = Float.parseFloat(startTime);
			float endTimePos = Float.parseFloat(endTime);
			
			generator.setName(name);
			generator.setStartTimePos(startTimePos);
			generator.setEndTimePos(endTimePos);
			
			//XXX 
			//this.notifyInputsChanged();
		}
	}

	/**
	 * Prepare Input-Setting to add a new Input.
	 * 
	 */
	public void newInput()
	{
		InputsWidgetData generatorInputsData = this.mainPageData.getGeneratorInputsData();
		
		if (generatorInputsData != null)
		{	
			generatorInputsData.deselectInput();
		}
	}

	/**
	 * 
	 */
	public void removeSelectedInput()
	{
		InputsWidgetData generatorInputsData = this.mainPageData.getGeneratorInputsData();
		
		if (generatorInputsData != null)
		{	
			generatorInputsData.removeSelectedInput();
		}
	}

	/**
	 * 
	 */
	public void removeSelectedTrack()
	{
//		Generator selectedGenerator = 
			this.mainPageData.getTracksListWidgetData().removeSelectedTrack();
		/*
		DesktopControllerData desktopControllerData = this.mainPageData.getDesktopControllerData();

		EditData editData = desktopControllerData.getEditData();
		
		ModulGeneratorTypeData editModulTypeData = editData.getEditModulTypeData();
		
		if (editModulTypeData != null)
		{
			editModulTypeData.removeGenerator(selectedGenerator);
		}
		*/
	}

	/**
	 * Set this Tracks for editing.
	 */
	public void setEditModulGeneratorTypeData(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		this.mainPageData.getTracksListWidgetData().setTracksData(modulGeneratorTypeData.getTracksData());
	}
}
