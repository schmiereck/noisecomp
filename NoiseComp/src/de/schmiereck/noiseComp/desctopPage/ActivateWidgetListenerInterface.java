package de.schmiereck.noiseComp.desctopPage;

import de.schmiereck.noiseComp.desctopPage.widgets.WidgetData;

/**
 * TODO docu
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ActivateWidgetListenerInterface
{
	/**
	 * Die Maus wurde vom Widget weggefahren.
	 */
	public void notifyActivateWidget(WidgetData widgetData);

	/**
	 * Widget wurde mit der Maus �berfahren.
	 */
	public void notifyDeactivateWidget(WidgetData widgetData);

}
