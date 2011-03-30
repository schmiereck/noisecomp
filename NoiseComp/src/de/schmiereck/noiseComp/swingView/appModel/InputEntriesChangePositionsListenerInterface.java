/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

/**
 * <p>
 * 	InputEntriesChangePositionsListener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>29.03.2011:	created, smk</p>
 */
public interface InputEntriesChangePositionsListenerInterface
{
	/**
	 * @param selectedInputEntryModel
	 * 			is the selected Input-Entry.
	 * @param targetInputEntryModel
	 * 			is the target Input-Entry.
	 */
	void notifyChangePositions(final InputEntryModel selectedInputEntryModel, 
	                           final InputEntryModel targetInputEntryModel);
}
