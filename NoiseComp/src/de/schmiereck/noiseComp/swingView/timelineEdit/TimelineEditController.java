/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.Timeline;

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
		// Selected Timeline changed: Update Timeline-Edit Model:
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					List<GeneratorTypeSelectItem> generatorTypeSelectItems;
					GeneratorTypeData generatorTypeData;
					String generatorName;
					Float generatorStartTimePos;
					Float generatorEndTimePos;

					TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
					
					if (timelineSelectEntryModel != null)
					{
						String name = timelineSelectEntryModel.getName();
						
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
						generatorStartTimePos = timelineSelectEntryModel.getStartTimePos();
						generatorEndTimePos = timelineSelectEntryModel.getEndTimePos();
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
//		//------------------------------------------------------------------------------------------
//		// Timeline-Edit Update-Button: Update Generator, Update Timeline-Edit Model and Timeline-Generator Model:
//		
//		this.timelineEditView.getUpdateButton().addActionListener
//		(
//		 	new ActionListener()
//		 	{
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
//					doUpdateEditModel(appController, timelinesDrawPanelModel);
//				}
//		 	}
//		);
//		//------------------------------------------------------------------------------------------
//		// Timeline-Edit GeneratorStartTimePosChanged: Notify Timeline:
//		
//		this.timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
//		(
//		 	new ModelPropertyChangedListener()
//		 	{
//				@Override
//				public void notifyModelPropertyChanged()
//				{
//					TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
//					
//					if (timelineSelectEntryModel != null)
//					{
//						String name = timelineSelectEntryModel.getName();
//						
//						Generator generator = appController.retrieveGeneratorOfEditedModul(name);
//						
//						generator
//					}
//				}
//		 	}
//		);
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

	/**
	 * Timeline-Edit Update-Button: Update Generator, Update Timeline-Edit Model and Timeline-Generator Model:
	 * 
	 * @param appController
	 * @param timelinesDrawPanelModel
	 */
	public void doUpdateEditModel(final ModulGeneratorTypeData editedModulGeneratorTypeData,
	                              //final Generator generator,
	                              final TimelinesDrawPanelModel timelinesDrawPanelModel)
	{
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
//		TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
		TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
//		TimelineEditView timelineEditView = timelineEditController.getTimelineEditView();
		
		if (timelineSelectEntryModel != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			GeneratorTypeData generatorTypeData;
			{
				GeneratorTypeSelectItem generatorTypeSelectItem = 
					(GeneratorTypeSelectItem)this.timelineEditView.getGeneratorTypeComboBox().getSelectedItem();
				generatorTypeData = generatorTypeSelectItem.getGeneratorTypeData();
			}
			String generatorName = this.timelineEditView.getGeneratorNameTextField().getText();
			Float generatorStartTimePos = Float.parseFloat(this.timelineEditView.getGeneratorStartTimePosTextField().getText());
			Float generatorEndTimePos = Float.parseFloat(this.timelineEditView.getGeneratorEndTimePosTextField().getText());
			
			if (generatorTypeData == null)
			{
				throw new RuntimeException("generatorTypeData not selected.");
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Generator:
			
//			Generator generator = 
//				appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());
			Timeline timeline = timelineSelectEntryModel.getTimeline();
			
			if (timeline == null)
			{
				Float soundFrameRate = SwingMain.getSoundData().getFrameRate();
				
				timeline = soundSourceLogic.createTimeline(generatorTypeData,
				                                           soundFrameRate,
				                                           generatorName); 
			}
			else
			{
				soundSourceLogic.updateName(timeline, generatorName);
			}
//			Generator generator = timeline.getGenerator();
//			
//			// Create new generator?
//			if (generator == null)
//			{
//				Float soundFrameRate = SwingMain.getSoundData().getFrameRate();
//				
//				generator = generatorTypeData.createGeneratorInstance(generatorName, 
//				                                                      soundFrameRate);
//				
////				generator = new Generator(generatorName, 
////				                          soundFrameRate, 
////				                          generatorTypeData);
//				
////				ModulGeneratorTypeData editedModulGeneratorTypeData = appController.getEditedModulGeneratorTypeData();
//				
//				timeline = soundSourceLogic.addGenerator(generator);
//				
//				editedModulGeneratorTypeData.addGenerator(generator);
//			}
//			else
//			{
//				generator.setName(generatorName);
//			}
//			generator.setStartTimePos(generatorStartTimePos);
//			generator.setEndTimePos(generatorEndTimePos);
			soundSourceLogic.updateTimePos(timeline, generatorStartTimePos, generatorEndTimePos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Timeline-Edit Model:
			
			timelineEditModel.setGeneratorTypeData(generatorTypeData);
			timelineEditModel.setGeneratorName(generatorName);
			timelineEditModel.setGeneratorStartTimePos(generatorStartTimePos);
			timelineEditModel.setGeneratorEndTimePos(generatorEndTimePos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Timeline-Model:
			
			timelineSelectEntryModel.setTimeline(timeline);
//			timelineGeneratorModel.setGeneratorTypeData(generatorTypeData);
			timelineSelectEntryModel.setName(generatorName);
			timelineSelectEntryModel.setStartTimePos(generatorStartTimePos);
			timelineSelectEntryModel.setEndTimePos(generatorEndTimePos);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEditModel}.
	 */
	public TimelineEditModel getTimelineEditModel()
	{
		return this.timelineEditModel;
	}
}
