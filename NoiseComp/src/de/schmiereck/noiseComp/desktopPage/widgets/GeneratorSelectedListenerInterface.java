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
	 * @param trackGraficData
	 */
	void notifyGeneratorSelected(TrackGraficData trackGraficData);

	/**
	 * @param trackGraficData
	 */
	void notifyGeneratorDeselected(TrackGraficData trackGraficData);

}
