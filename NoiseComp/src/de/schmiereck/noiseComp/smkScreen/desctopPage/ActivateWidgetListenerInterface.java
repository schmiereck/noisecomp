package de.schmiereck.noiseComp.smkScreen.desctopPage;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.WidgetData;

/**
 * Informs if a widget is actived by mouse rollover.<br/>
 * The functions are only called once if the active state is changed.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ActivateWidgetListenerInterface
{
	/**
	 * Widget wurde mit der Maus �berfahren.
	 */
	public void notifyActivateWidget(WidgetData widgetData);

	/**
	 * Die Maus wurde vom Widget weggefahren.
	 */
	public void notifyDeactivateWidget(WidgetData widgetData);

}
