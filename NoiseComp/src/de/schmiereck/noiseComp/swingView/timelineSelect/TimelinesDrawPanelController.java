/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.Iterator;

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
//	    this.timelinesDrawPanelView.addDoTimelineSelectedListeners
//	    (
//	     	new DoTimelineSelectedListenerInterface()
//	     	{
//				@Override
//				public void timelineSelected(TimelineSelectEntryModel timelineGeneratorModel)
//				{
//					timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(timelineGeneratorModel);
//				}
//	     	}
//	    );
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
				public void changeTimelinesPosition(TimelineSelectEntryModel selectedTimelineSelectEntryModel,
													TimelineSelectEntryModel newTimelineSelectEntryModel)
				{
					if ((selectedTimelineSelectEntryModel != null) &&
						(newTimelineSelectEntryModel != null))
					{
						doChangeTimelinesPosition(selectedTimelineSelectEntryModel,
						                          newTimelineSelectEntryModel);
					}
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
	 * @param timelineSelectEntryModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineSelectEntryModel(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		this.timelinesDrawPanelModel.addTimelineSelectEntryModel(timelineSelectEntryModel);
		
		ModelPropertyChangedListener modelChangedListener = this.timelinesDrawPanelModel.getTimelineGeneratorModelChangedListener();
		
		timelineSelectEntryModel.getNameChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		timelineSelectEntryModel.getStartTimePosChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		timelineSelectEntryModel.getEndTimePosChangedNotifier().addModelPropertyChangedListener(modelChangedListener);
		
		// TODO remove listeners if timeline is removed.
	}

	/**
	 * @return
	 * 			the Selected Timeline Generator.
	 */
	public Generator getSelectedTimelineGenerator()
	{
		Generator selectedGenerator;
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
		
		if (selectedTimelineSelectEntryModel != null)
		{
			String selectedTimelineGeneratorName = selectedTimelineSelectEntryModel.getName();
			
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
	 * @param firstTimelineSelectEntryModel
	 * 			is the first Timeline-Generator Model.
	 * @param secondTimelineSelectEntryModel
	 * 			is the second Timeline-Generator Model.
	 */
	public void doChangeTimelinesPosition(TimelineSelectEntryModel firstTimelineSelectEntryModel,
	                                      TimelineSelectEntryModel secondTimelineSelectEntryModel)
	{
		//==========================================================================================
		int firstTimelinePos = firstTimelineSelectEntryModel.getTimelinePos();
		int secondTimelinePos = secondTimelineSelectEntryModel.getTimelinePos();
		
		//------------------------------------------------------------------------------------------
		// Update edited Modul Data:
		
		ModulGeneratorTypeData editedModulGeneratorTypeData = this.modulesTreeModel.getEditedModulGeneratorTypeData();
		
//		Generator firstGenerator = selectedTimelineGeneratorModel.getGenerator();
//		Generator secondGenerator = newTimelineGeneratorModel.getGenerator();
		
		editedModulGeneratorTypeData.switchTracksByPos(firstTimelinePos,
		                                               secondTimelinePos);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Draw Model:
		
//		List<TimelineSelectEntryModel> timelineGeneratorModels = this.timelinesDrawPanelModel.getTimelineGeneratorModels();
//		
//		timelineGeneratorModels.set(firstTimelinePos, secondTimelineGeneratorModel);
//		timelineGeneratorModels.set(secondTimelinePos, firstTimelineGeneratorModel);
		this.timelinesDrawPanelModel.changeTimelinesPosition(firstTimelinePos,
		                                                     secondTimelinePos);
		
//		//------------------------------------------------------------------------------------------
//		// Update Timeline-Generator Models:
//		
//		firstTimelineGeneratorModel.setTimelinePos(secondTimelinePos);
//		secondTimelineGeneratorModel.setTimelinePos(firstTimelinePos);
//		
		//------------------------------------------------------------------------------------------
		// Should be triggered by timelinesDrawPanel Model update.
//		this.timelinesDrawPanelView.repaint();
		
		// Update TimelinesGeneratorsRule.
		
		//==========================================================================================
	}

	/**
	 * 
	 */
	public void doCreateNew()
	{
		//==========================================================================================
		TimelinesDrawPanelModel timelinesDrawPanelModel = this.getTimelinesDrawPanelModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Timeline-Select-Model:
		
		int timelinePos = timelinesDrawPanelModel.getTimelineSelectEntryModels().size();
		
		TimelineSelectEntryModel timelineSelectEntryModel = 
			new TimelineSelectEntryModel(null,
			                           timelinePos,
			                           "(new)",
			                           0.0F,
			                           1.0F);
		
		this.addTimelineSelectEntryModel(timelineSelectEntryModel);
		
		timelinesDrawPanelModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
		
		//==========================================================================================
	}

	public void doTimelinesZoomIn()
	{
		//==========================================================================================
		float zoomX = this.timelinesDrawPanelModel.getZoomX();
		
		zoomX *= 1.5F;
		
		this.timelinesDrawPanelModel.setZoomX(zoomX);

		//==========================================================================================
	}

	public void doTimelinesZoomOut()
	{
		//==========================================================================================
		float zoomX = this.timelinesDrawPanelModel.getZoomX();
		
		zoomX /= 1.5F;
		
		this.timelinesDrawPanelModel.setZoomX(zoomX);

		//==========================================================================================
	}
}
