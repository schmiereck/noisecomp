package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

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
	 * Widget wurde mit der Maus überfahren.
	 */
	public void notifyDeactivateWidget(WidgetData widgetData);

}
