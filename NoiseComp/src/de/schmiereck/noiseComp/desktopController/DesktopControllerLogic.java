package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
import de.schmiereck.noiseComp.desktopPage.ButtonPressedCallbackInterface;
import de.schmiereck.noiseComp.desktopPage.DesktopPageLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorInputSelectedListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputsData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectEntryData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.ControllerLogic;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesktopControllerLogic
extends ControllerLogic
implements ButtonPressedCallbackInterface, GeneratorSelectedListenerInterface, GeneratorInputSelectedListenerInterface
{
	private DesktopControllerData controllerData;
	
	private SoundSchedulerLogic soundSchedulerLogic = null;
	
	/**
	 * Constructor.
	 * 
	 * @param waiter
	 */
	public DesktopControllerLogic(DesktopControllerData controllerData, DesktopInputListener inputListener, SchedulerWaiter waiter, String playerName)
	{
		super(waiter);
		
		this.controllerData = controllerData;
		
		SoundData soundData = this.controllerData.getSoundData();
		TracksData tracksData = this.controllerData.getTracksData();
		
		OutputGenerator outputGenerator = this.createGenerators(tracksData, soundData, soundData.getFrameRate());
		
		this.controllerData.getTracksData().setGeneratorSelectedListener(this);
		
		this.controllerData.getGeneratorInputsData().setGeneratorInputSelectedListener(this);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#calc(de.schmiereck.screenTools.controller.ControllerData, long, double)
	 */
	public void calc(ControllerData controllerData, long actualWaitPerFramesMillis, double runSeconds)
	{
		controllerData.setCalcSleepMillis(actualWaitPerFramesMillis);
		
		DesktopControllerData desktopControllerData = (DesktopControllerData)controllerData;
		
		DesktopPageLogic.calcWidgets(desktopControllerData.getActiveDesktopPageData());
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#initGameData(de.schmiereck.screenTools.controller.ControllerData)
	 */
	public void initGameData(ControllerData controllerData)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#openGameServerConnection()
	 */
	public void openGameServerConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#closeGameServerConnection()
	 */
	public void closeGameServerConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#openGameClientConnection()
	 */
	public void openGameClientConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#closeGameClientConnection()
	 */
	public void closeGameClientConnection()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#getIsGameClientConnected()
	 */
	public boolean getIsGameClientConnected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#getIsGameServerConnected()
	 */
	public boolean getIsGameServerConnected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 */
	public void doEndGame()
	{
		this.stopWaitGame();
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn sich die Mausposition geändert hat.
	 */
	public void movePointer(int posX, int posY)
	{
		this.controllerData.getDesktopData().setPointerPos(posX, posY);
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn die Maustaste gedrückt wurde.
	 */
	public void pointerPressed()
	{
		DesktopPageLogic.pointerPressed(this.controllerData.getActiveDesktopPageData());
	}

	public void pointerReleased()
	{
		DesktopPageLogic.pointerReleased(this.controllerData.getActiveDesktopPageData(), this);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ButtonPressedCallbackInterface#buttonPressed(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void buttonPressed(InputWidgetData pressedButtonData)
	{
		/*
		Button b = new Button();
		ActionListener l = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub

			}
		};
		b.addActionListener(l);
		*/
		if (pressedButtonData != null)
		{
			if ("play".equals(pressedButtonData.getName()) == true)
			{
				if (this.soundSchedulerLogic == null)
				{
					SoundData soundData = this.controllerData.getSoundData();
					
					this.soundSchedulerLogic = new SoundSchedulerLogic(20, soundData);
					
					this.soundSchedulerLogic.startThread();

					this.soundSchedulerLogic.startPlayback();
				}
				else
				{	
					this.soundSchedulerLogic.resumePlayback();
				}
			}
			else
			{
				if ("pause".equals(pressedButtonData.getName()) == true)
				{
					if (this.soundSchedulerLogic != null)
					{
						this.soundSchedulerLogic.pausePlayback();
					}
				}
				else
				{
					if ("stop".equals(pressedButtonData.getName()) == true)
					{
						if (this.soundSchedulerLogic != null)
						{
							this.soundSchedulerLogic.stopPlayback();
							
							this.soundSchedulerLogic.stopThread();
							
							this.soundSchedulerLogic = null;
						}
					}
					else
					{
						if ("add".equals(pressedButtonData.getName()) == true)
						{
							this.controllerData.setActiveDesktopPageData(this.controllerData.getSelectGeneratorDesktopPageData());
						}
						else
						{
							/*
							if ("cancel".equals(pressedButtonData.getName()) == true)
							{
								this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
							}
							else
							*/
							{
								if ("addSinus".equals(pressedButtonData.getName()) == true)
								{
									this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());

									SoundData soundData = this.controllerData.getSoundData();
									TracksData tracksData = this.controllerData.getTracksData();
									
									SinusGenerator sinusGenerator = new SinusGenerator("sinus" + tracksData.getTracksCount(), 
											soundData.getFrameRate());
									//sinusGenerator.addInputValue(1.0F, SinusGenerator.INPUT_TYPE_FREQ);
									
									this.addDefaultInputs(sinusGenerator);
									
									this.addGenerator(sinusGenerator);
								}
								else
								{
									if ("addFader".equals(pressedButtonData.getName()) == true)
									{
										this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());

										SoundData soundData = this.controllerData.getSoundData();
										TracksData tracksData = this.controllerData.getTracksData();
										
										FaderGenerator faderGenerator = new FaderGenerator("fader" + tracksData.getTracksCount(), 
												soundData.getFrameRate());
										
										this.addDefaultInputs(faderGenerator);
										
										this.addGenerator(faderGenerator);
									}
									else
									{
										if ("addMixer".equals(pressedButtonData.getName()) == true)
										{
											this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());

											SoundData soundData = this.controllerData.getSoundData();
											TracksData tracksData = this.controllerData.getTracksData();
											
											MixerGenerator mixerGenerator = new MixerGenerator("mixer" + tracksData.getTracksCount(), 
													soundData.getFrameRate());
											
											this.addDefaultInputs(mixerGenerator);
											
											this.addGenerator(mixerGenerator);
										}
										else
										{
											if ("addOutput".equals(pressedButtonData.getName()) == true)
											{
												this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());

												SoundData soundData = this.controllerData.getSoundData();
												TracksData tracksData = this.controllerData.getTracksData();
												
												OutputGenerator outputGenerator = new OutputGenerator("output" + tracksData.getTracksCount(), 
														soundData.getFrameRate());
												
												this.addDefaultInputs(outputGenerator);
												
												this.addGenerator(outputGenerator);
											}
											else
											{
												if ("remove".equals(pressedButtonData.getName()) == true)
												{
													this.controllerData.getTracksData().removeSelectedTrack();
												}
												else
												{
													/*
													if ("set".equals(pressedButtonData.getName()) == true)
													{
														this.doSetGeneratorData();
													}
													else
													*/
													{
														/*
														if ("exit".equals(pressedButtonData.getName()) == true)
														{
															this.doEndGame();
														}
														else
														*/
														{
															if ("removeInput".equals(pressedButtonData.getName()) == true)
															{
																InputsData generatorInputsData = this.controllerData.getGeneratorInputsData();
																
																if (generatorInputsData != null)
																{	
																	generatorInputsData.removeSelectedInput();
																}
															}
															else
															{
																if ("newInput".equals(pressedButtonData.getName()) == true)
																{
																	InputsData generatorInputsData = this.controllerData.getGeneratorInputsData();
																	
																	this.newInput(generatorInputsData);
																}
																else
																{
																	if ("addInput".equals(pressedButtonData.getName()) == true)
																	{
																		InputsData generatorInputsData = this.controllerData.getGeneratorInputsData();
																		
																		this.addInput(generatorInputsData);
																	}
																	else
																	{
																		/*
																		if ("setInput".equals(pressedButtonData.getName()) == true)
																		{
																			InputsData generatorInputsData = this.controllerData.getGeneratorInputsData();
																			
																			this.setInput(generatorInputsData);
																		}
																		*/
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void addGenerator(Generator generator)
	{
		TracksData tracksData = this.controllerData.getTracksData();

		//sinusGenerator.setStartTimePos(generatorsGraphicData.getTracksCount() - 6);
		//sinusGenerator.setEndTimePos(generatorsGraphicData.getTracksCount() - 3);
		//generator.setStartTimePos(0.0F);
		//generator.setEndTimePos(1.0F);
		
		this.addGenerator(tracksData, generator);
	}
	
	private void addGenerator(TracksData tracksData, Generator generator)
	{
		tracksData.addTrack(new TrackData(generator));
		
		if (OutputGenerator.class.equals(generator.getClass()))
		{	
			OutputGenerator outputGenerator = (OutputGenerator)generator;
			
			SoundData soundData = this.controllerData.getSoundData();
			
			soundData.setOutputGenerator(outputGenerator);
		}
		//this.controllerData.getGeneratorsScrollbarData().setScrollerLength(generatorsGraphicData.getGeneratorsSize());
		//this.controllerData.getGeneratorsScrollbarData().setScrollerSize(12);
	}

	private OutputGenerator createGenerators(TracksData tracksData, SoundData soundData, float frameRate)
	{
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:

		//---------------------------------
		FaderGenerator faderInGenerator = new FaderGenerator("faderIn", frameRate);
		
		faderInGenerator.setStartTimePos(0.0F);
		faderInGenerator.setEndTimePos(2.0F);
		
		faderInGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_START_VALUE);
		faderInGenerator.addInputValue(1.0F, FaderGenerator.INPUT_TYPE_END_VALUE);
		//faderInGenerator.setStartFadeValue(0.0F);
		//faderInGenerator.setEndFadeValue(1.0F);
		
		this.addGenerator(tracksData, faderInGenerator);

		//---------------------------------
		FaderGenerator faderOutGenerator = new FaderGenerator("faderOut", frameRate);
		
		faderOutGenerator.setStartTimePos(2.0F);
		faderOutGenerator.setEndTimePos(5.0F);
		
		faderOutGenerator.addInputValue(1.0F, FaderGenerator.INPUT_TYPE_START_VALUE);
		faderOutGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_END_VALUE);
		//faderOutGenerator.setStartFadeValue(1.0F);
		//faderOutGenerator.setEndFadeValue(0.0F);
		
		this.addGenerator(tracksData, faderOutGenerator);
		
		//---------------------------------
		SinusGenerator sinusGenerator = new SinusGenerator("sinus", frameRate);
		sinusGenerator.addInputValue(262F, SinusGenerator.INPUT_TYPE_FREQ);
		//sinusGenerator.setSignalFrequency(262F);
		
		sinusGenerator.setStartTimePos(0.0F);
		sinusGenerator.setEndTimePos(5.0F);
		
		this.addGenerator(tracksData, sinusGenerator);
		
		//---------------------------------
		SinusGenerator sinus2Generator = new SinusGenerator("sinus2", frameRate);
		sinus2Generator.addInputValue(131F, SinusGenerator.INPUT_TYPE_FREQ);
		//sinus2Generator.setSignalFrequency(131F);
		
		sinus2Generator.setStartTimePos(0.0F);
		sinus2Generator.setEndTimePos(5.0F);
		
		this.addGenerator(tracksData, sinus2Generator);
		
		//---------------------------------
		SinusGenerator sinus3Generator = new SinusGenerator("sinus3", frameRate);
		sinus3Generator.addInputValue(70F, SinusGenerator.INPUT_TYPE_FREQ);
		//sinus3Generator.setSignalFrequency(70F);
		
		sinus3Generator.setStartTimePos(0.0F);
		sinus3Generator.setEndTimePos(5.0F);
		
		this.addGenerator(tracksData, sinus3Generator);
		
		//---------------------------------
		MixerGenerator mixerGenerator = new MixerGenerator("mixer", frameRate);
		
		mixerGenerator.setStartTimePos(0.0F);
		mixerGenerator.setEndTimePos(5.0F);
		
		mixerGenerator.addVolumeInput(faderInGenerator);
		mixerGenerator.addVolumeInput(faderOutGenerator);
		
		mixerGenerator.addSignalInput(sinusGenerator);
		mixerGenerator.addSignalInput(sinus2Generator);
		mixerGenerator.addSignalInput(sinus3Generator);
		
		this.addGenerator(tracksData, mixerGenerator);
		
		//---------------------------------
		OutputGenerator outputGenerator = new OutputGenerator("output", frameRate);

		outputGenerator.setStartTimePos(0.0F);
		outputGenerator.setEndTimePos(5.0F);
		
		outputGenerator.setSignalInput(mixerGenerator);
		
		this.addGenerator(tracksData, outputGenerator);
		
		return outputGenerator;
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
		Vector inputs;
		
		if (trackGraficData != null)
		{
			generator = trackGraficData.getGenerator();
			
			if (generator != null)
			{	
				name = generator.getName();
				generatorTypeData = this.controllerData.searchGeneratorTypeData(generator);
				startTime = Float.toString(generator.getStartTimePos());
				endTime = Float.toString(generator.getEndTimePos());
				inputs = generator.getInputs();
			}
			else
			{
				name = "";
				generatorTypeData = null;
				startTime = "";
				endTime = "";
				inputs = null;
			}
		}
		else
		{
			generator = null;
			generatorTypeData = null;
			name = "";
			startTime = "";
			endTime = "";
			inputs = null;
		}
		
		this.controllerData.getGeneratorNameInputlineData().setInputText(name);
		this.controllerData.getGeneratorStartTimeInputlineData().setInputText(startTime);
		this.controllerData.getGeneratorEndTimeInputlineData().setInputText(endTime);
		this.controllerData.getGeneratorInputsData().setGeneratorInputs(generator, inputs);

		SelectData selectData = this.controllerData.getGeneratorInputTypeSelectData();
		
		selectData.clearSelectEntrys();
		
		if (generatorTypeData != null)
		{	
			Iterator inputTypesDataIterator = generatorTypeData.getInputTypesDataIterator();
			
			while (inputTypesDataIterator.hasNext())
			{
				InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
				
				SelectEntryData selectEntryData = new SelectEntryData(Integer.valueOf(inputTypeData.getInputType()), 
						inputTypeData.getInputTypeName(),
						inputTypeData);
				
				selectData.addSelectEntryData(selectEntryData);
			}
		}	
		//selectData.setInputPosByValue(Integer.valueOf(inputType));
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorSelectedListenerInterface#notifyGeneratorDeselected(de.schmiereck.noiseComp.desktopPage.widgets.TrackData)
	 */
	public void notifyGeneratorDeselected(TrackData trackData)
	{
		this.controllerData.getGeneratorNameInputlineData().setInputText("");
		this.controllerData.getGeneratorInputTypeSelectData().clearSelectEntrys();
		this.controllerData.getGeneratorStartTimeInputlineData().setInputText("");
		this.controllerData.getGeneratorEndTimeInputlineData().setInputText("");
	}

	/**
	 * @param dir 	1: 	to move right<br/>
	 * 				-1:	to move left<br/>
	 */
	public void doMoveCursor(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).moveCursor(dir);
			}
		}
	}

	/**
	 * @param dir	0:	begin<br/>
	 * 				1:	end
	 */
	public void doChangeCursorPos(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).changeCursorPos(dir);
			}
		}
	}
	
	/**
	 * @param dir	1:	scroll Down<br/>
	 * 				-1:	scroll Up
	 */
	public void doScrollCursor(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof SelectData)
			{
				((SelectData)focusedWidgetData).scrollInputPos(dir);
			}
		}
	}
	
	/**
	 * @param dir 	1: 	delete right from cursor<br/>
	 * 				-1:	delete left from cursor<br/>
	 */
	public void doDelete(int dir)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).deleteChar(dir);
			}
		}
	}

	/**
	 * @param c
	 */
	public void doInputChar(char c)
	{
		WidgetData focusedWidgetData = this.controllerData.getActiveDesktopPageData().getFocusedWidgetData();
		
		if (focusedWidgetData != null)
		{
			if (focusedWidgetData instanceof InputlineData)
			{
				((InputlineData)focusedWidgetData).inputChar(c);
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorInputSelectedListenerInterface#notifyGeneratorInputSelected(InputsData, de.schmiereck.noiseComp.generator.InputData)
	 */
	public void notifyGeneratorInputSelected(InputsData inputsData, InputData selectedInputData)
	{
		String inputGeneratorName;
		String inputValue;
		int inputType;
		//GeneratorTypeData generatorTypeData;
		
		if (selectedInputData != null)
		{
			Generator generator = selectedInputData.getInputGenerator();
			
			if (generator != null)
			{	
				inputGeneratorName = generator.getName();
			}
			else
			{
				inputGeneratorName = null;
			}
			
			inputType = selectedInputData.getInputType();
			//generatorTypeData = this.controllerData.searchGeneratorTypeData(inputsData.getSelectedGenerator());
			
			if (selectedInputData.getInputValue() != null)
			{	
				inputValue = String.valueOf(selectedInputData.getInputValue());
			}
			else
			{	
				inputValue = null;
			}
		}
		else
		{
			//generatorTypeData = null;
			inputGeneratorName = "";
			inputType = 0;
			inputValue = null;
		}
		
		this.controllerData.getGeneratorInputNameInputlineData().setInputText(inputGeneratorName);
		
		SelectData selectData = this.controllerData.getGeneratorInputTypeSelectData();
		/*
		selectData.clearSelectEntrys();
		
		if (generatorTypeData != null)
		{	
			Iterator inputTypesDataIterator = generatorTypeData.getInputTypesDataIterator();
			
			while (inputTypesDataIterator.hasNext())
			{
				InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
				
				SelectEntryData selectEntryData = new SelectEntryData(Integer.valueOf(inputTypeData.getInputType()), 
						inputTypeData.getInputTypeName(),
						inputTypeData);
				
				selectData.addSelectEntryData(selectEntryData);
			}
		}
		*/	
		selectData.setInputPosByValue(Integer.valueOf(inputType));

		this.controllerData.getGeneratorInputValueInputlineData().setInputText(inputValue);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.GeneratorInputSelectedListenerInterface#notifyGeneratorInputDeselected(InputsData, de.schmiereck.noiseComp.generator.InputData)
	 */
	public void notifyGeneratorInputDeselected(InputsData inputsData, InputData deselectedInputData)
	{
		this.controllerData.getGeneratorInputNameInputlineData().setInputText("");
		this.controllerData.getGeneratorInputTypeSelectData().setInputPos(0);
		this.controllerData.getGeneratorInputValueInputlineData().setInputText("");
	}

	/**
	 * Prepare Input-Setting to add a new Input.
	 * 
	 * @param generatorInputsData
	 */
	private void newInput(InputsData generatorInputsData)
	{
		if (generatorInputsData != null)
		{	
			generatorInputsData.deselectInput();
		}
	}
	
	/**
	 * Add a new Input witn the Input-Settings.
	 * 
	 * TODO replace the RuntimeExceptions with Message-Boxes, smk 
	 * @param generatorInputsData
	 */
	private void addInput(InputsData generatorInputsData)
	{
		if (generatorInputsData != null)
		{	
			String inputGeneratorName = this.controllerData.getGeneratorInputNameInputlineData().getInputText();
			SelectEntryData selectedEntryData = this.controllerData.getGeneratorInputTypeSelectData().getSelectedEntryData();
			Integer inputType = (Integer)selectedEntryData.getValue();
			String inputGeneratorValueStr = this.controllerData.getGeneratorInputValueInputlineData().getInputText();
			
			Float inputGeneratorValue;
			
			if (inputGeneratorValueStr != null)
			{
				inputGeneratorValue = Float.valueOf(inputGeneratorValueStr);
			}
			else
			{
				inputGeneratorValue = null;
			}

			TrackData selectedTrackData = this.controllerData.getTracksData().getSelectedTrackData();
			
			if (selectedTrackData != null)
			{
				Generator selectedGenerator = selectedTrackData.getGenerator();
				
				InputData selectedInputData = this.addInput(selectedGenerator, inputGeneratorName, inputType, inputGeneratorValue);
				
				this.controllerData.getGeneratorInputsData().setSelectedInputData(selectedInputData);
			}
			else
			{
				throw new RuntimeException("no generator selected");
			}
		}
	}
	
	public InputData addInput(Generator generator, String inputGeneratorName, Integer inputType, Float inputValue)
	{
		InputData inputData;
		
		TrackData inputTrackData;
		
		if (inputGeneratorName != null)
		{
			if (inputGeneratorName.length() > 0)
			{
				inputTrackData = this.controllerData.getTracksData().searchTrackData(inputGeneratorName);

				// Found no Track with the name of the Input ?
				if (inputTrackData == null)
				{
					throw new RuntimeException("input generator \"" + inputGeneratorName + "\" not found");
				}
			}
			else
			{
				inputTrackData = null;
			}
		}
		else
		{
			inputTrackData = null;
		}
		
		Generator inputGenerator;
		
		// Found a Track with the name of the Input ?
		if (inputTrackData != null)
		{
			inputGenerator = inputTrackData.getGenerator();
		}
		else
		{
			inputGenerator = null;
		}
		
		inputData = generator.addInputGenerator(inputGenerator, inputType.intValue(), inputValue);
		
		return inputData;
	}

	/**
	 * Set the new Input-Settings for the selected Input.
	 * If there is no selected input, {@link #addInput(InputsData)} is called.
	 *
	 * TODO replace the RuntimeExceptions with Message-Boxes, smk 
	 * @param generatorInputsData
	 */
	public void setInput(InputsData generatorInputsData)
	{
		if (generatorInputsData != null)
		{	
			InputData selectedInputData = this.controllerData.getGeneratorInputsData().getSelectedInputData();
			
			if (selectedInputData != null)
			{	
				String name = this.controllerData.getGeneratorInputNameInputlineData().getInputText();
				SelectEntryData selectedEntryData = this.controllerData.getGeneratorInputTypeSelectData().getSelectedEntryData();
				Integer inputType = (Integer)selectedEntryData.getValue();
				//String type = this.controllerData.getGeneratorInputTypeInputlineData().getInputText();
	
				String inputGeneratorValueStr = this.controllerData.getGeneratorInputValueInputlineData().getInputText();
				
				Float inputGeneratorValue;
				
				if (inputGeneratorValueStr != null)
				{
					if (inputGeneratorValueStr.length() > 0)
					{	
						inputGeneratorValue = Float.valueOf(inputGeneratorValueStr);
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
				
				Generator inputGenerator;
				
				TrackData selectedTrackData = this.controllerData.getTracksData().getSelectedTrackData();
				
				if (selectedTrackData != null)
				{
					// Select the name of a generator ?
					if (name != null)
					{	
						if (name.length() > 0)
						{	
							TrackData inputTrackData;
							inputTrackData = this.controllerData.getTracksData().searchTrackData(name);
							
							// Found no Track with the name of the Input ?
							if (inputTrackData == null)
							{
								throw new RuntimeException("input generator \"" + name + "\" not found");
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
					
					if ((inputGenerator == null) && (inputGeneratorValue == null))
					{
						throw new RuntimeException("no input generator and no value for the input \"" + name + "\"");
					}
					
					selectedInputData.setInputGenerator(inputGenerator);
					selectedInputData.setInputType(inputType);
					selectedInputData.setInputValue(inputGeneratorValue);
				}
				else
				{
					throw new RuntimeException("no generator selected");
				}
			}
			else
			{
				this.addInput(generatorInputsData);
			}
		}
	}

	/**
	 * @param i
	 */
	public void doFocusWalk(int dir)
	{
		this.controllerData.getActiveDesktopPageData().focusWalk(dir);
	}

	/**
	 * 
	 */
	public void doSubmitPage()
	{
		this.controllerData.getActiveDesktopPageData().submitPage();
	}

	/**
	 * @param generator
	 */
	private void addDefaultInputs(Generator generator)
	{
		GeneratorTypeData generatorTypeData = this.controllerData.searchGeneratorTypeData(generator);
		
		Iterator inputTypesDataIterator = generatorTypeData.getInputTypesDataIterator();
		
		while (inputTypesDataIterator.hasNext())
		{
			InputTypeData inputTypeData = (InputTypeData)inputTypesDataIterator.next();
			
			for (int pos = 0; pos < inputTypeData.getInputCountMin(); pos++)
			{
				generator.addInputValue(inputTypeData.getDefaultValue(), inputTypeData.getInputType());
			}
		}
	}
	
}
