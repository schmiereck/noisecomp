/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

/**
 * <p>
 * 	Input-Entries Remove Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>17.03.2011:	created, smk</p>
 */
public interface InputEntriesRemoveListenerInterface
{

	/**
	 * @param inputNo
	 * 			is the number of the input entry.
	 * @param inputEntryModel
	 * 			is the removed input entry.
	 */
	void notifyRemoveInputEntry(int inputNo,
	                            InputEntryModel inputEntryModel);
}
