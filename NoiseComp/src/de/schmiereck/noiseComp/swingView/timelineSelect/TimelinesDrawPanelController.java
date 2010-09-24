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
}
