/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

/**
 * <p>
 * 	Input-Entries Add Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>17.03.2011:	created, smk</p>
 */
public interface InputEntriesAddListenerInterface
{

	/**
	 * @param entryPos
	 * 			is the entry pos.
	 * @param inputEntryModel
	 * 			is the added input entry.
	 */
	void notifyAddInputEntry(int entryPos,
	                         InputEntryModel inputEntryModel);
}
