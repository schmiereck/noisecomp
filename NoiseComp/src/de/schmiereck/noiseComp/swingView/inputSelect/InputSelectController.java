/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.Iterator;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.timelines.SelectedTimelineChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelModel;

/**
 * <p>
 * 	Input-Select Controller.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Select Model.
	 */
	private final InputSelectModel inputSelectModel;
	
	/**
	 * Input-Select View.
	 */
	private final InputSelectView inputSelectView;
	
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
	public InputSelectController(final AppController appController,
	                             final TimelinesDrawPanelModel timelinesDrawPanelModel)
	{
		//==========================================================================================
		this.inputSelectModel = new InputSelectModel();

		this.inputSelectView = new InputSelectView(this.inputSelectModel);

		//------------------------------------------------------------------------------------------
		// Selected Timeline changed -> updated model:
		
		timelinesDrawPanelModel.addSelectedTimelineChangedListener
		(
		 	new SelectedTimelineChangedListenerInterface()
		 	{
				@Override
				public void selectedTimelineChanged()
				{
					Iterator<InputData> inputsIterator;
					
					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					if (timelineGeneratorModel != null)
					{
						Generator generator = 
							appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());
						
						inputsIterator = generator.getInputsIterator();
					}
					else
					{
						inputsIterator = null;
					}

					inputSelectModel.clearInputs();
					
					if (inputsIterator != null)
					{
						while (inputsIterator.hasNext())
						{
							InputData inputData = (InputData)inputsIterator.next();
							
							InputSelectEntryModel inputSelectEntryModel = 
								new InputSelectEntryModel(inputData);
							
							inputSelectModel.addInputData(inputSelectEntryModel);
						}
					}
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputSelectModel}.
	 */
	public InputSelectModel getInputSelectModel()
	{
		return this.inputSelectModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputSelectView}.
	 */
	public InputSelectView getInputSelectView()
	{
		return this.inputSelectView;
	}

}
