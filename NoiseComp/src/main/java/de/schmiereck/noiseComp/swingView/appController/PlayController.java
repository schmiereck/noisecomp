/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.service.PlaySoundService;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.soundScheduler.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.timeline.Timeline;

import java.util.Objects;

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
						  final SoundSourceData soundSourceData,
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
			public void notifyPlaybackPosChanged(float actualTime) {
				final TimelinesTimeRuleModel timelinesTimeRuleModel = PlayController.this.timelinesTimeRuleController.getTimelinesTimeRuleModel();

				final TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
				final TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();

				final double startTime = startTimeMarkerSelectEntryModel.getTimeMarker();
				final double endTime = endTimeMarkerSelectEntryModel.getTimeMarker();

				PlayController.this.doPlaybackTimeChanged(actualTime);

				final Timeline outputTimeline = soundSourceData.getOutputTimeline();

				if (Objects.nonNull(outputTimeline)) {
					final float generatorEndTimePos = outputTimeline.getGeneratorEndTimePos();

					if ((actualTime > generatorEndTimePos) || (actualTime > endTime)) {
						if (soundService.retrieveLooped()) {
							//soundService.submitPlaybackPos(startTime);
							PlayController.this.playSoundService.submitPlaybackPos(startTime);
						} else {
							PlayController.this.doStopSound();
						}
					}
				} else {
					PlayController.this.doStopSound();
				}
			}
		};
		//==========================================================================================
	}

	public synchronized void doPlaySound(final SoundSourceData soundSourceData)
	{
		//==========================================================================================
		//SoundService soundService = SoundService.getInstance();
		
		TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//==========================================================================================
		if (this.playSoundService.retrievePlaySound() == true) {
			//--------------------------------------------------------------------------------------
			this.playSoundService.resumePlayback();
			
			//--------------------------------------------------------------------------------------
		} else {
			//--------------------------------------------------------------------------------------
			TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
			
			double playTime = playTimeMarkerSelectEntryModel.getTimeMarker();
			
			//--------------------------------------------------------------------------------------
			this.playSoundService.startPlayback(soundSourceData,
					playTime,
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
