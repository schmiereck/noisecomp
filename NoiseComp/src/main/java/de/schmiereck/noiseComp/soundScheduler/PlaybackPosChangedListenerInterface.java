/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.soundScheduler;

/**
 * <p>
 * 	Playback-Pos Changed Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>29.11.2010:	created, smk</p>
 */
public interface PlaybackPosChangedListenerInterface
{

	/**
	 * @param actualTime
	 * 			the actual play time in seconds.
	 */
	void notifyPlaybackPosChanged(float actualTime);
}
