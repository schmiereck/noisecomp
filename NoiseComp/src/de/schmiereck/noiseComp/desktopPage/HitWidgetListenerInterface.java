package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;

/**
 * Informs about the position of a Hit (Mouse Rollover).
 *
 * @author smk
 * @version 08.02.2004
 */
public interface HitWidgetListenerInterface
{

	/**
	 * This function is called, if a new Mouse position over the Widget is calculated.
	 *   
	 * @param activeWidgetData
	 * @param pointerPosX is relative to the X-Position of the Widget.
	 * @param pointerPosY is relative to the Y-Position of the Widget.
	 */
	public void notifyHitWidget(WidgetData activeWidgetData, int pointerPosX, int pointerPosY);
	
}