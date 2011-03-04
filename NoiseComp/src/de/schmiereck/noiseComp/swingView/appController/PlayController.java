/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.soundData.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.SwingMain;
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
	
	private SoundSchedulerLogic soundSchedulerLogic = null;

	private final TimelinesTimeRuleController timelinesTimeRuleController;
	
	private final PlaybackPosChangedListenerInterface	playbackPosChangedListener;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor
	 */
	public PlayController(final TimelinesTimeRuleController timelinesTimeRuleController)
	{
		//==========================================================================================
		this.timelinesTimeRuleController = timelinesTimeRuleController;
		
		//==========================================================================================
	    //------------------------------------------------------------------------------------------
		this.playbackPosChangedListener = new PlaybackPosChangedListenerInterface()
		{
			@Override
			public void notifyPlaybackPosChanged(float actualTime)
			{
				TimelinesTimeRuleModel timelinesTimeRuleModel = timelinesTimeRuleController.getTimelinesTimeRuleModel();
				
				TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();
				
				double endTime = endTimeMarkerSelectEntryModel.getTimeMarker();
				
				SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
				
				doPlaybackTimeChanged(actualTime);
				
				Timeline outputTimeline = soundSourceLogic.getOutputTimeline();
				
				float generatorEndTimePos = outputTimeline.getGeneratorEndTimePos();
				
				if ((actualTime > generatorEndTimePos) || (actualTime > endTime))
				{
					doStopSound();
				}
			}
		};
		//==========================================================================================
	}

	public synchronized void doPlaySound()
	{
		//==========================================================================================
		TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//==========================================================================================
		if (this.soundSchedulerLogic == null)
		{
			//--------------------------------------------------------------------------------------
			TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
			
			double playTime = playTimeMarkerSelectEntryModel.getTimeMarker();
			
			//--------------------------------------------------------------------------------------
			SoundData soundData = SwingMain.getSoundData();
			
			this.soundSchedulerLogic = new SoundSchedulerLogic(25, soundData);

			this.soundSchedulerLogic.submitPlaybackPos((float)playTime);
			
			this.soundSchedulerLogic.addPlaybackPosChangedListener(this.playbackPosChangedListener);
			
			this.soundSchedulerLogic.startThread();

			this.soundSchedulerLogic.startPlayback();
			
			//--------------------------------------------------------------------------------------
		}
		else
		{	
			//--------------------------------------------------------------------------------------
			this.soundSchedulerLogic.resumePlayback();
			
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	public synchronized void doStopSound()
	{
		//==========================================================================================
		TimelinesTimeRuleModel timelinesTimeRuleModel = this.timelinesTimeRuleController.getTimelinesTimeRuleModel();
		
		//==========================================================================================
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.stopPlayback();
			
			this.soundSchedulerLogic.stopThread();
			
			this.soundSchedulerLogic = null;

			//--------------------------------------------------------------------------------------
			// Set to start marker.
			
			TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
			
			double startTime = startTimeMarkerSelectEntryModel.getTimeMarker();
			
			this.doPlaybackTimeChanged((float)startTime); //0.0F);
		}
		//==========================================================================================
	}

	public synchronized void doPauseSound()
	{
		//==========================================================================================
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.pausePlayback();
		}
		//==========================================================================================
	}

	/**
	 * @param playbackPos
	 * 			is the playbackPos in seconds.
	 */
	public synchronized void doSubmitPlaybackPos(float playbackPos)
	{
		//==========================================================================================
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.submitPlaybackPos(playbackPos);
		}
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
}
