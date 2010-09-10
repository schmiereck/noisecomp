/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.util.Iterator;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.appView.DoEditModuleListener;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditController;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditController;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesScrollPanelController;


/**
 * <p>
 * 	App Controller.
 * </p>
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public class AppController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Edit Controller.
	 */
	private final ModulEditController modulEditController;
	
	/**
	 * Timelines Scroll-Panel Controller.
	 */
	private final TimelinesScrollPanelController timelinesScrollPanelController;
	
	/**
	 * Timelines Draw-Panel Controller.
	 */
	private final TimelinesDrawPanelController timelinesDrawPanelController;
	
	/**
	 * Timeline-Edit Controller.
	 */
	private final TimelineEditController timelineEditController;
	
	/**
	 * App Model
	 */
	private final AppModel appModel;
	
	/**
	 * App View
	 */
	private final AppView appView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public AppController()
	{
		//==========================================================================================
		this.appModel = new AppModel();
		
		this.appView = new AppView(this.appModel);
		
		this.appView.setTitle("NoiseComp V2.0");
		this.appView.setSize(800, 600);
		this.appView.setLocationRelativeTo(null);
		this.appView.setVisible(true);
		
		//------------------------------------------------------------------------------------------
		this.modulEditController = new ModulEditController(this);
		
		this.appView.setModulEditView(this.modulEditController.getModulEditView());
		
		//------------------------------------------------------------------------------------------
		this.timelinesScrollPanelController = new TimelinesScrollPanelController();
		
		this.appView.setTimelineComponent(this.timelinesScrollPanelController.getTimelinesScrollPanelView());
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelController = new TimelinesDrawPanelController();
		
		this.timelinesScrollPanelController.setTimelinesScrollPanelController(this.timelinesDrawPanelController);
		
		//------------------------------------------------------------------------------------------
		this.timelineEditController = 
			new TimelineEditController(this,
			                           this.timelinesDrawPanelController.getTimelinesDrawPanelModel());
		
		this.appView.setTimelineEditView(this.timelineEditController.getTimelineEditView());
		
		//------------------------------------------------------------------------------------------
		this.appModel.addEditModuleChangedListener(this.appView);
		
		//==========================================================================================
		this.appView.addDoEditModuleListener
		(
		 	new DoEditModuleListener()
		 	{
				@Override
				public void notifyEditModul(ModulGeneratorTypeData modulGeneratorTypeData)
				{
					selectEditModule(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the edited modul.
	 */
	public void selectEditModule(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		this.appModel.setEditedModulGeneratorTypeData(modulGeneratorTypeData);
		
		this.timelinesDrawPanelController.clearTimelineGenerators();
		
		Iterator<Generator> generatorsIterator = modulGeneratorTypeData.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = generatorsIterator.next();
			
			TimelineGeneratorModel timelineGeneratorModel = 
				new TimelineGeneratorModel(generator.getName(),
				                           generator.getStartTimePos(),
				                           generator.getEndTimePos());
			
			this.timelinesDrawPanelController.addTimelineGeneratorModel(timelineGeneratorModel);
			
			
		}
		//==========================================================================================
	}

	/**
	 * @param generatorName
	 * 			is the Name of Generator.
	 * @return
	 * 			the genaror with given name.
	 */
	public Generator retrieveGeneratorOfEditedModul(String generatorName)
	{
		//==========================================================================================
		ModulGeneratorTypeData modulGeneratorTypeData = this.appModel.getEditedModulGeneratorTypeData();
		
		Generator generator = modulGeneratorTypeData.searchGenerator(generatorName);
		
		//==========================================================================================
		return generator;
	}
}
