package de.schmiereck.noiseComp.desktopPage.widgets;

/**
 * TODO docu
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
