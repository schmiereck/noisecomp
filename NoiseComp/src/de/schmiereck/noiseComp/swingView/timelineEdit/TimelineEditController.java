/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.SwingMain;
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
					List<GeneratorTypeSelectItem> generatorTypeSelectItems;
					GeneratorTypeData generatorTypeData;
					String generatorName;
					Float generatorStartTimePos;
					Float generatorEndTimePos;

					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					if (timelineGeneratorModel != null)
					{
						String name = timelineGeneratorModel.getName();
						
						Generator generator = appController.retrieveGeneratorOfEditedModul(name);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						{
							generatorTypeSelectItems = new Vector<GeneratorTypeSelectItem>();
							List<GeneratorTypeData> generatorTypes = appController.retrieveGeneratorTypesForSelect();

							if (generatorTypes != null)
							{
								GeneratorTypeSelectItem noSelectItem = new GeneratorTypeSelectItem(null);
								generatorTypeSelectItems.add(noSelectItem);

								for (GeneratorTypeData generatorTypeData2 : generatorTypes)
								{
									generatorTypeSelectItems.add(new GeneratorTypeSelectItem(generatorTypeData2));
								}
							}
						}
						if (generator != null)
						{
							generatorTypeData = generator.getGeneratorTypeData();
						}
						else
						{
							generatorTypeData = null;
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						generatorName = name;
						generatorStartTimePos = timelineGeneratorModel.getStartTimePos();
						generatorEndTimePos = timelineGeneratorModel.getEndTimePos();
					}
					else
					{
						generatorTypeSelectItems = null;
						generatorTypeData = null;
						generatorName = null;
						generatorStartTimePos = null;
						generatorEndTimePos = null;
					}

					timelineEditModel.setGeneratorTypeSelectItems(generatorTypeSelectItems);
					timelineEditModel.setGeneratorTypeData(generatorTypeData);
					timelineEditModel.setGeneratorName(generatorName);
					timelineEditModel.setGeneratorStartTimePos(generatorStartTimePos);
					timelineEditModel.setGeneratorEndTimePos(generatorEndTimePos);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Update-Button: Update Timeline-Generator-Model and Generator:
		
		this.timelineEditView.getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
//					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
//					TimelineEditView timelineEditView = timelineEditController.getTimelineEditView();
					
					if (timelineGeneratorModel != null)
					{
						Generator generator = 
							appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());

						GeneratorTypeData generatorTypeData;
						{
							GeneratorTypeSelectItem generatorTypeSelectItem = 
								(GeneratorTypeSelectItem)timelineEditView.getGeneratorTypeComboBox().getSelectedItem();
							generatorTypeData = generatorTypeSelectItem.getGeneratorTypeData();
						}
						String generatorName = timelineEditView.getGeneratorNameTextField().getText();
						Float generatorStartTimePos = Float.parseFloat(timelineEditView.getGeneratorStartTimePosTextField().getText());
						Float generatorEndTimePos = Float.parseFloat(timelineEditView.getGeneratorEndTimePosTextField().getText());
						
						// Update Generator.
						
						// Create new generator?
						if (generator == null)
						{
							Float soundFrameRate = SwingMain.getSoundData().getFrameRate();
							
							generator = generatorTypeData.createGeneratorInstance(generatorName, 
							                                                      soundFrameRate);
							
//							generator = new Generator(generatorName, 
//							                          soundFrameRate, 
//							                          generatorTypeData);
							
							ModulGeneratorTypeData editedModulGeneratorTypeData = appController.getEditedModulGeneratorTypeData();
							
							editedModulGeneratorTypeData.addGenerator(generator);
						}
						else
						{
							generator.setName(generatorName);
						}
						generator.setStartTimePos(generatorStartTimePos);
						generator.setEndTimePos(generatorEndTimePos);

						// Update Timeline-Model.
//						timelineGeneratorModel.setGeneratorTypeData(generatorTypeData);
						timelineGeneratorModel.setName(generatorName);
						timelineGeneratorModel.setStartTimePos(generatorStartTimePos);
						timelineGeneratorModel.setEndTimePos(generatorEndTimePos);
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
