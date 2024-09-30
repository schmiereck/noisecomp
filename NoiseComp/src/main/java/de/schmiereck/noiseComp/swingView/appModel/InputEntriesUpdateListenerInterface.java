/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

/**
 * <p>
 * 	Input-Entries Update Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>17.03.2011:	created, smk</p>
 */
public interface InputEntriesUpdateListenerInterface
{
	/**
	 * @param inputEntryModel
	 * 			is the updated input entry.
	 */
	void notifyUpdateInputEntry(InputEntryModel inputEntryModel);
}
