package de.schmiereck.noiseComp.desktopController.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackData;
import de.schmiereck.noiseComp.generator.Generator;

/**
 * TODO docu
 *
 * @author smk
 * @version 22.02.2004
 */
public class SetGeneratorButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SetGeneratorButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		TrackData trackData = this.controllerData.getTracksData().getSelectedTrackData();
		
		if (trackData != null)
		{	
			Generator generator = trackData.getGenerator();
			
			String name = this.controllerData.getGeneratorNameInputlineData().getInputText();
			String startTime = this.controllerData.getGeneratorStartTimeInputlineData().getInputText();
			String endTime = this.controllerData.getGeneratorEndTimeInputlineData().getInputText();

			float startTimePos = Float.parseFloat(startTime);
			float endTimePos = Float.parseFloat(endTime);
			
			generator.setName(name);
			generator.setStartTimePos(startTimePos);
			generator.setEndTimePos(endTimePos);
		}
	}

}
