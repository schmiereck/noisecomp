/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

/**
 * <p>
 * 	Timeline Content Changed Listener Interface.
 * </p>
 * <p>
 * 	Notify Listener if the Timeline Content Changed because of recalulating samples.
 * </p>
 * 
 * @author smk
 * @version <p>28.10.2010:	created, smk</p>
 */
public interface TimelineContentChangedListenerInterface
{
	/**
	 * @param bufferStart
	 * 			is the changed Buffer start.
	 * @param bufferEnd
	 * 			is the changed Buffer end.
	 */
	void notifyTimelineContentChanged(long bufferStart, long bufferEnd);
}
