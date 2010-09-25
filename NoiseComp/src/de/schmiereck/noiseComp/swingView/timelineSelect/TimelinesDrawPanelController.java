/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.Iterator;
import java.util.List;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;

/**
 * <p>
 * 	Timelines Draw-Panel Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelinesDrawPanelController
{
	//**********************************************************************************************
	// Fields:
	
	private final ModulesTreeModel modulesTreeModel;
	
	private TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	final private TimelinesDrawPanelView timelinesDrawPanelView;
	
	private final ModelPropertyChangedListener timelineGeneratorModelChangedListener =
	 	new ModelPropertyChangedListener()
 	{
		@Override
		public void notifyModelPropertyChanged()
		{
			timelinesDrawPanelView.repaint();
		}
 	};
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param modulesTreeModel 
	 * 			is the Modules-Tree Model.
	 * @param inputEditModel 
	 * 			is the Input-Edit Model.
	 */
	public TimelinesDrawPanelController(ModulesTreeModel modulesTreeModel)
	                                    //InputEditModel inputEditModel)
	{
		//==========================================================================================
		this.modulesTreeModel = modulesTreeModel;
		
		this.timelinesDrawPanelModel = new TimelinesDrawPanelModel();
		
	    this.timelinesDrawPanelView = new TimelinesDrawPanelView(this.timelinesDrawPanelModel);
	    
	    //------------------------------------------------------------------------------------------
	    this.timelinesDrawPanelView.addDoTimelineSelectedListeners
	    (
	     	new DoTimelineSelectedListenerInterface()
	     	{
				@Override
				public void timelineSelected(TimelineGeneratorModel timelineGeneratorModel)
				{
					timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(timelineGeneratorModel);
				}
	     	}
	    );
//	    //------------------------------------------------------------------------------------------
//	    inputEditModel.getValueChangedNotifier().addModelPropertyChangedListener
//	    (
//	     	this.timelineGeneratorModelChangedListener
//	    );
	    //------------------------------------------------------------------------------------------
	    this.timelinesDrawPanelView.addChangeTimelinesPositionListeners
	    (
	     	new DoChangeTimelinesPositionListenerInterface()
	     	{
				@Override
				public void changeTimelinesPosition(TimelineGeneratorModel selectedTimelineGeneratorModel,
													TimelineGeneratorModel newTimelineGeneratorModel)
				{
					doChangeTimelinesPosition(selectedTimelineGeneratorModel,
					                          newTimelineGeneratorModel);
				}
	     	}
	    );
	    //==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesDrawPanelModel}.
	 */
	public TimelinesDrawPanelModel getTimelinesDrawPanelModel()
	{
		return this.timelinesDrawPanelModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesDrawPanelView}.
	 */
	public TimelinesDrawPanelView getTimelinesDrawPanelView()
	{
		return this.timelinesDrawPanelView;
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators()
	{
		this.timelinesDrawPanelModel.clearTimelineGenerators();
	}

	/**
	 * @param timelineGeneratorModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineGeneratorModel(TimelineGeneratorModel timelineGeneratorModel)
	{
		this.timelinesDrawPanelModel.addTimelineGeneratorModel(timelineGeneratorModel);
		
		timelineGeneratorModel.getNameChangedNotifier().addModelPropertyChangedListener(this.timelineGeneratorModelChangedListener);
		timelineGeneratorModel.getStartTimePosChangedNotifier().addModelPropertyChangedListener(this.timelineGeneratorModelChangedListener);
		timelineGeneratorModel.getEndTimePosChangedNotifier().addModelPropertyChangedListener(this.timelineGeneratorModelChangedListener);
		
		// TODO remove listeners if timeline is removed.
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelChangedListener}.
	 */
	public ModelPropertyChangedListener getTimelineGeneratorModelChangedListener()
	{
		return this.timelineGeneratorModelChangedListener;
	}

	/**
	 * @return
	 * 			the Selected Timeline Generator.
	 */
	public Generator getSelectedTimelineGenerator()
	{
		Generator selectedGenerator;
		
		TimelineGeneratorModel selectedTimelineGeneratorModel = this.timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
		
		if (selectedTimelineGeneratorModel != null)
		{
			String selectedTimelineGeneratorName = selectedTimelineGeneratorModel.getName();
			
			ModulGeneratorTypeData editedModulGeneratorTypeData = this.modulesTreeModel.getEditedModulGeneratorTypeData();
			
			selectedGenerator = null;
			
			Iterator<Generator> generatorsIterator = editedModulGeneratorTypeData.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext())
			{
				Generator generator = generatorsIterator.next();
				
				if (generator.getName().equals(selectedTimelineGeneratorName))
				{
					selectedGenerator = generator;
					break;
				}
			}
		}
		else
		{
			selectedGenerator = null;
		}
		
		return selectedGenerator;
	}
	
	/**
	 * 
	 * @param firstTimelineGeneratorModel
	 * 			is the first Timeline-Generator Model.
	 * @param secondTimelineGeneratorModel
	 * 			is the second Timeline-Generator Model.
	 */
	public void doChangeTimelinesPosition(TimelineGeneratorModel firstTimelineGeneratorModel,
	                                      TimelineGeneratorModel secondTimelineGeneratorModel)
	{
		//==========================================================================================
		int firstTimelinePos = firstTimelineGeneratorModel.getTimelinePos();
		int secondTimelinePos = secondTimelineGeneratorModel.getTimelinePos();
		
		//------------------------------------------------------------------------------------------
		// Update edited Modul Data:
		
		ModulGeneratorTypeData editedModulGeneratorTypeData = this.modulesTreeModel.getEditedModulGeneratorTypeData();
		
//		Generator firstGenerator = selectedTimelineGeneratorModel.getGenerator();
//		Generator secondGenerator = newTimelineGeneratorModel.getGenerator();
		
		editedModulGeneratorTypeData.switchTracksByPos(firstTimelinePos,
		                                               secondTimelinePos);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Draw Model:
		
		List<TimelineGeneratorModel> timelineGeneratorModels = this.timelinesDrawPanelModel.getTimelineGeneratorModels();
		
		timelineGeneratorModels.set(firstTimelinePos, secondTimelineGeneratorModel);
		timelineGeneratorModels.set(secondTimelinePos, firstTimelineGeneratorModel);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Generator Models:
		
		firstTimelineGeneratorModel.setTimelinePos(secondTimelinePos);
		secondTimelineGeneratorModel.setTimelinePos(firstTimelinePos);
		
		//------------------------------------------------------------------------------------------
		// TODO Should be triggered by timelinesDrawPanel Model update.
		this.timelinesDrawPanelView.repaint();
		
		// TODO Update TimelinesGeneratorsRule.
		
		//==========================================================================================
	}
}
