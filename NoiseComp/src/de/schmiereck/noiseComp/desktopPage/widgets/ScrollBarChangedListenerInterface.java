package de.schmiereck.noiseComp.desktopPage.widgets;
/*
 * Created on 03.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Is called, if a attribute of the scrollbar is changed.
 * </p>
 * 
 * @author smk
 * @version <p>03.04.2005:	created, smk</p>
 */
public interface ScrollBarChangedListenerInterface
{
	void notifyScrollbarChanged(ScrollbarData scrollbarData);
}
