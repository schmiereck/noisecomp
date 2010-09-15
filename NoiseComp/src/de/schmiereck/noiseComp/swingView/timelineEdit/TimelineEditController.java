/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.timelines.SelectedTimelineChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelModel;

/**
 * <p>
 * 	Timeline-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelineEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Timeline-Edit View.
	 */
	private final TimelineEditView	timelineEditView;

	/**
	 * Timeline-Edit Model.
	 */
	private final TimelineEditModel	timelineEditModel;

	/**
	 * Timelines-Draw-Panel Model.
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel 
	 * 			is the App Controller.
	 * @param timelinesDrawPanelModel 
	 * 			is the Timeline Draw-Panel Model.
	 */
	public TimelineEditController(final AppController appController,
	                              final TimelinesDrawPanelModel timelinesDrawPanelModel)
	{
		//==========================================================================================
		this.timelineEditModel = new TimelineEditModel();
		this.timelineEditView = new TimelineEditView(this.timelineEditModel);

		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		//------------------------------------------------------------------------------------------
		// Selected Timeline changed -> updated model:
		
		this.timelinesDrawPanelModel.addSelectedTimelineChangedListener
		(
		 	new SelectedTimelineChangedListenerInterface()
		 	{
				@Override
				public void selectedTimelineChanged()
				{
					String generatorName;
					Float generatorStartTimePos;
					Float generatorEndTimePos;

					Iterator<InputData> inputsIterator;
					
					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					if (timelineGeneratorModel != null)
					{
						Generator generator = 
							appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());
						
						generatorName = generator.getName();
						generatorStartTimePos = generator.getStartTimePos();
						generatorEndTimePos = generator.getEndTimePos();
						
						inputsIterator = generator.getInputsIterator();
					}
					else
					{
						generatorName = null;
						generatorStartTimePos = null;
						generatorEndTimePos = null;
						
						inputsIterator = null;
					}

					timelineEditModel.setGeneratorName(generatorName);
					timelineEditModel.setGeneratorStartTimePos(generatorStartTimePos);
					timelineEditModel.setGeneratorEndTimePos(generatorEndTimePos);
					
					timelineEditModel.clearInputs();
					
					if (inputsIterator != null)
					{
						while (inputsIterator.hasNext())
						{
							InputData inputData = (InputData)inputsIterator.next();
							
							timelineEditModel.addInputData(inputData);
						}
					}
				}
		 	}
		);
		
		//------------------------------------------------------------------------------------------
		// Update-Button: Update Timeline-Generator-Model and Generator:
		
		this.timelineEditView.getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					if (timelineGeneratorModel != null)
					{
						Generator generator = 
							appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());

						String generatorName = timelineEditView.getGeneratorNameTextField().getText();
						Float generatorStartTimePos = Float.parseFloat(timelineEditView.getGeneratorStartTimePosTextField().getText());
						Float generatorEndTimePos = Float.parseFloat(timelineEditView.getGeneratorEndTimePosTextField().getText());
						
						timelineGeneratorModel.setName(generatorName);
						timelineGeneratorModel.setStartTimePos(generatorStartTimePos);
						timelineGeneratorModel.setEndTimePos(generatorEndTimePos);
						
						// Update Generator.
						generator.setName(generatorName);
						generator.setStartTimePos(generatorStartTimePos);
						generator.setEndTimePos(generatorEndTimePos);
					}
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEditView}.
	 */
	public TimelineEditView getTimelineEditView()
	{
		return this.timelineEditView;
	}
}
