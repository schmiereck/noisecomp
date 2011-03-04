/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.soundData.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Play Controller.
 * </p>
 * 
 * @author smk
 * @version <p>04.03.2011:	created, smk</p>
 */
public class PlayController
{
	//**********************************************************************************************
	// Fields:

//	private final PlaybackPosChangedListenerInterface	playbackPosChangedListener;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor
	 */
	public PlayController()
	{
		//==========================================================================================
//	    //------------------------------------------------------------------------------------------
//		this.playbackPosChangedListener = new PlaybackPosChangedListenerInterface()
//		{
//			@Override
//			public void notifyPlaybackPosChanged(float actualTime)
//			{
//				TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();
//				
//				double endTime = endTimeMarkerSelectEntryModel.getTimeMarker();
//				
//				SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
//				
//				doPlaybackTimeChanged(actualTime);
//				
//				Timeline outputTimeline = soundSourceLogic.getOutputTimeline();
//				
//				float generatorEndTimePos = outputTimeline.getGeneratorEndTimePos();
//				
//				if ((actualTime > generatorEndTimePos) || (actualTime > endTime))
//				{
//					doStopSound();
//				}
//			}
//		};
		//==========================================================================================
	}
}
