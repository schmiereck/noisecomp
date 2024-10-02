/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.service.PlaySoundService;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.soundData.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundData.SoundDataLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
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
	
	private final TimelinesTimeRuleController timelinesTimeRuleController;

	private final SoundSourceLogic soundSourceLogic;

	private final SoundService soundService;

	private final PlaySoundService playSoundService;

	private final PlaybackPosChangedListenerInterface	playbackPosChangedListener;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor
	 */
	public PlayController(final TimelinesTimeRuleController timelinesTimeRuleController,
						  final SoundSourceLogic soundSourceLogic,
						  final SoundDataLogic soundDataLogic,
						  final SoundService soundService) {
		//==========================================================================================
		this.timelinesTimeRuleController = timelinesTimeRuleController;
		this.soundSourceLogic = soundSourceLogic;
		this.soundService = soundService;
		this.playSoundService = new PlaySoundService(soundDataLogic);

		//==========================================================================================
	    //------------------------------------------------------------------------------------------
		this.playbackPosChangedListener = new PlaybackPosChangedListenerInterface()
		{
			@Override
			public void notifyPlaybackPosChanged(float actualTime)
			{
				TimelinesTimeRuleModel timelinesTimeRuleModel = timelinesTimeRuleController.getTimelinesTimeRuleModel();
				
				TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
				TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();
				
				double startTime = startTimeMarkerSelectEntryModel.getTimeMarker();
				double endTime = endTimeMarkerSelectEntryModel.getTimeMarker();
				
				//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();

				doPlaybackTimeChanged(actualTime);

				Timeline outputTimeline = soundSourceLogic.getOutputTimeline();

				float generatorEndTimePos = outputTimeline.getGeneratorEndTimePos();

				if ((actualTime > generatorEndTimePos) || (actualTime > endTime))
				{
					//SoundService soundService = SoundService.getInstance();

					if (soundService.retrieveLooped() == true)
					{
						//soundService.submitPlaybackPos(startTime);
						playSoundService.submitPlaybackPos(startTime);
					}
					else
					{
						doStopSound();
					}
				}
			}
		};
		//==========================================================================================
	}

	public synchronized void doPlaySound()
	{
		//==========================================================================================
		//SoundService soundService = SoundService.getInstance();
		
		TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//==========================================================================================
		if (playSoundService.getPlaySound() == true) {
			//--------------------------------------------------------------------------------------
			this.playSoundService.resumePlayback();
			
			//--------------------------------------------------------------------------------------
		} else {
			//--------------------------------------------------------------------------------------
			TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
			
			double playTime = playTimeMarkerSelectEntryModel.getTimeMarker();
			
			//--------------------------------------------------------------------------------------
			this.playSoundService.startPlayback(playTime,
			                           this.playbackPosChangedListener);
			
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	public synchronized void doStopSound()
	{
		//==========================================================================================
		TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//==========================================================================================
		System.out.println("stopPlayback: BEGIN");
		this.playSoundService.stopPlayback();
		System.out.println("stopPlayback: END");

//		if (this.soundSchedulerLogic != null)
//		{
//			this.soundSchedulerLogic.stopPlayback();
//			
//			this.soundSchedulerLogic.stopThread();
//			
//			this.soundSchedulerLogic = null;
//
			//--------------------------------------------------------------------------------------
			// Set to start marker.
			
			TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
			
			double startTime = startTimeMarkerSelectEntryModel.getTimeMarker();
			
			this.doPlaybackTimeChanged((float)startTime); //0.0F);
//		}
		//==========================================================================================
	}

	public synchronized void doPauseSound()
	{
		//==========================================================================================
		this.playSoundService.pauseSound();

		//==========================================================================================
	}

	/**
	 * @param playbackPos
	 * 			is the playbackPos in seconds.
	 */
	public synchronized void doSubmitPlaybackPos(float playbackPos)
	{
		//==========================================================================================
		this.playSoundService.submitPlaybackPos(playbackPos);
		
		//==========================================================================================
	}

	/**
	 * @param actualTime
	 * 			the actual play time in seconds.
	 */
	public void doPlaybackTimeChanged(float actualTime)
	{
		//==========================================================================================
		TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//==========================================================================================
		TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
		
		playTimeMarkerSelectEntryModel.setTimeMarker(actualTime);
		
		//==========================================================================================
	}

	/**
	 * @param looped
	 * 			<code>true</code> if sound looped between start and end time.
	 */
	public void doLoopSound(boolean looped)
	{
		//==========================================================================================
		this.soundService.submitLoopSound(looped);
		
		//==========================================================================================
	}
}
