package de.schmiereck.noiseComp.desctopController;

import de.schmiereck.noiseComp.desctopInput.DesctopInputListener;
import de.schmiereck.noiseComp.desctopPage.ButtonPressedCallbackInterface;
import de.schmiereck.noiseComp.desctopPage.DesctopPageLogic;
import de.schmiereck.noiseComp.desctopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desctopPage.widgets.GeneratorsGraphicData;
import de.schmiereck.noiseComp.desctopPage.widgets.TrackGraficData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
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
public class DesctopControllerLogic
extends ControllerLogic
implements ButtonPressedCallbackInterface
{
	private DesctopControllerData controllerData;
	
	private SoundSchedulerLogic soundSchedulerLogic = null;
	
	/**
	 * Constructor.
	 * 
	 * @param waiter
	 */
	public DesctopControllerLogic(DesctopControllerData controllerData, DesctopInputListener inputListener, SchedulerWaiter waiter, String playerName)
	{
		super(waiter);
		
		this.controllerData = controllerData;
		
		SoundData soundData = this.controllerData.getSoundData();
		GeneratorsGraphicData generatorsGraphicData = this.controllerData.getGeneratorsGraphicData();
		
		OutputGenerator outputGenerator = this.createGenerators(generatorsGraphicData, soundData, soundData.getFrameRate());
		
		soundData.setOutputGenerator(outputGenerator);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerLogic#calc(de.schmiereck.screenTools.controller.ControllerData, long, double)
	 */
	public void calc(ControllerData controllerData, long actualWaitPerFramesMillis, double runSeconds)
	{
		controllerData.setCalcSleepMillis(actualWaitPerFramesMillis);
		
		DesctopControllerData desctopControllerData = (DesctopControllerData)controllerData;
		
		DesctopPageLogic.calcWidgets(desctopControllerData.getActiveDesctopPageData());
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
		this.controllerData.getActiveDesctopPageData().setPointerPos(posX, posY);
	}

	/**
	 * Wird vom Input-Listener aufgerufen, wenn die Maustaste gedrückt wurde.
	 */
	public void pointerPressed()
	{
		DesctopPageLogic.pointerPressed(this.controllerData.getActiveDesctopPageData());
	}

	public void pointerReleased()
	{
		DesctopPageLogic.pointerReleased(this.controllerData.getActiveDesctopPageData(), this);
	}
	
	public void buttonPressed(ButtonData pressedButtonData)
	{
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
							this.controllerData.setActiveDesctopPageData(this.controllerData.getSelectGeneratorDesctopPageData());
						}
						else
						{
							if ("cancel".equals(pressedButtonData.getName()) == true)
							{
								this.controllerData.setActiveDesctopPageData(this.controllerData.getMainDesctopPageData());
							}
							else
							{
								if ("addSinus".equals(pressedButtonData.getName()) == true)
								{
									this.controllerData.setActiveDesctopPageData(this.controllerData.getMainDesctopPageData());

									SoundData soundData = this.controllerData.getSoundData();
									GeneratorsGraphicData generatorsGraphicData = this.controllerData.getGeneratorsGraphicData();
									
									this.addGenerator(new SinusGenerator("sinus" + generatorsGraphicData.getTracksCount(), 
											1.0F, soundData.getFrameRate()));
								}
								else
								{
									if ("addFader".equals(pressedButtonData.getName()) == true)
									{
										this.controllerData.setActiveDesctopPageData(this.controllerData.getMainDesctopPageData());

										SoundData soundData = this.controllerData.getSoundData();
										GeneratorsGraphicData generatorsGraphicData = this.controllerData.getGeneratorsGraphicData();
										
										this.addGenerator(new FaderGenerator("fader" + generatorsGraphicData.getTracksCount(), 
												soundData.getFrameRate()));
									}
									else
									{
										if ("addMixer".equals(pressedButtonData.getName()) == true)
										{
											this.controllerData.setActiveDesctopPageData(this.controllerData.getMainDesctopPageData());

											SoundData soundData = this.controllerData.getSoundData();
											GeneratorsGraphicData generatorsGraphicData = this.controllerData.getGeneratorsGraphicData();
											
											this.addGenerator(new MixerGenerator("mixer" + generatorsGraphicData.getTracksCount(), 
													soundData.getFrameRate()));
										}
										else
										{
											if ("addOutput".equals(pressedButtonData.getName()) == true)
											{
												this.controllerData.setActiveDesctopPageData(this.controllerData.getMainDesctopPageData());

												SoundData soundData = this.controllerData.getSoundData();
												GeneratorsGraphicData generatorsGraphicData = this.controllerData.getGeneratorsGraphicData();
												
												this.addGenerator(new OutputGenerator("output" + generatorsGraphicData.getTracksCount(), 
														soundData.getFrameRate()));
											}
											else
											{
												if ("remove".equals(pressedButtonData.getName()) == true)
												{
													this.controllerData.getGeneratorsGraphicData().removeSelectedTrack();
												}
												else
												{
													if ("exit".equals(pressedButtonData.getName()) == true)
													{
														this.doEndGame();
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

	private void addGenerator(Generator generator)
	{
		GeneratorsGraphicData generatorsGraphicData = this.controllerData.getGeneratorsGraphicData();

		//sinusGenerator.setStartTimePos(generatorsGraphicData.getTracksCount() - 6);
		//sinusGenerator.setEndTimePos(generatorsGraphicData.getTracksCount() - 3);
		generator.setStartTimePos(0.0F);
		generator.setEndTimePos(1.0F);
		
		this.addGenerator(generatorsGraphicData, generator);
	}
	
	private void addGenerator(GeneratorsGraphicData generatorsGraphicData, Generator generator)
	{
		generatorsGraphicData.addTrack(new TrackGraficData(generator));
		
		//this.controllerData.getGeneratorsScrollbarData().setScrollerLength(generatorsGraphicData.getGeneratorsSize());
		//this.controllerData.getGeneratorsScrollbarData().setScrollerSize(12);
	}

	private OutputGenerator createGenerators(GeneratorsGraphicData generatorsGraphicData, SoundData soundData, float frameRate)
	{
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:

		//---------------------------------
		FaderGenerator faderInGenerator = new FaderGenerator("faderIn", frameRate);
		
		faderInGenerator.setStartTimePos(0.0F);
		faderInGenerator.setEndTimePos(2.0F);
		
		faderInGenerator.setStartFadeValue(0.0F);
		faderInGenerator.setEndFadeValue(1.0F);
		
		this.addGenerator(generatorsGraphicData, faderInGenerator);
		
		//---------------------------------
		FaderGenerator faderOutGenerator = new FaderGenerator("faderOut", frameRate);
		
		faderOutGenerator.setStartTimePos(2.0F);
		faderOutGenerator.setEndTimePos(5.0F);
		
		faderOutGenerator.setStartFadeValue(1.0F);
		faderOutGenerator.setEndFadeValue(0.0F);
		
		this.addGenerator(generatorsGraphicData, faderOutGenerator);
		
		//---------------------------------
		SinusGenerator sinusGenerator = new SinusGenerator("sinus", 262F, frameRate);

		sinusGenerator.setStartTimePos(0.0F);
		sinusGenerator.setEndTimePos(5.0F);
		
		this.addGenerator(generatorsGraphicData, sinusGenerator);
		
		//---------------------------------
		SinusGenerator sinus2Generator = new SinusGenerator("sinus2", 131F, frameRate);
		
		sinus2Generator.setStartTimePos(0.0F);
		sinus2Generator.setEndTimePos(5.0F);
		
		this.addGenerator(generatorsGraphicData, sinus2Generator);
		
		//---------------------------------
		SinusGenerator sinus3Generator = new SinusGenerator("sinus3", 70F, frameRate);
		
		sinus3Generator.setStartTimePos(0.0F);
		sinus3Generator.setEndTimePos(5.0F);
		
		this.addGenerator(generatorsGraphicData, sinus3Generator);
		
		//---------------------------------
		MixerGenerator mixerGenerator = new MixerGenerator("mixer", frameRate);
		
		mixerGenerator.setStartTimePos(0.0F);
		mixerGenerator.setEndTimePos(5.0F);
		
		mixerGenerator.addVolumeInput(faderInGenerator);
		mixerGenerator.addVolumeInput(faderOutGenerator);
		
		mixerGenerator.addSignalInput(sinusGenerator);
		mixerGenerator.addSignalInput(sinus2Generator);
		mixerGenerator.addSignalInput(sinus3Generator);
		
		this.addGenerator(generatorsGraphicData, mixerGenerator);
		
		//---------------------------------
		OutputGenerator outputGenerator = new OutputGenerator("output", frameRate);

		outputGenerator.setStartTimePos(0.0F);
		outputGenerator.setEndTimePos(5.0F);
		
		outputGenerator.setSignalInput(mixerGenerator);
		
		this.addGenerator(generatorsGraphicData, outputGenerator);
		
		return outputGenerator;
	}
}
