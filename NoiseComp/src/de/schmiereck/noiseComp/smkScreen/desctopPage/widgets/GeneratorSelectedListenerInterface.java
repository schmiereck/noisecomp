package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import de.schmiereck.noiseComp.generator.TrackData;

/**
 * Informs the listener object, if a track is selected or deselected.
 *
 * @author smk
 * @version 08.02.2004
 */
public interface GeneratorSelectedListenerInterface
{

	/**
	 * @param trackData
	 */
	void notifyGeneratorSelected(TrackData trackData);

	/**
	 * @param trackData
	 */
	void notifyGeneratorDeselected(TrackData trackData);

}
