package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.Iterator;

/**
 * Basic Functions for a scrolling List Area.
 *
 * @author smk
 * @version 08.02.2004
 */
public abstract class ListWidgetData
extends WidgetData
{
	private int listEntryHeight;
	
	private ScrollbarData verticalScrollbarData;
	private ScrollbarData horizontalScrollbarData;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public ListWidgetData(int posX, int posY, int sizeX, int sizeY,
						  int listEntryHeight,
						  ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData)
	{
		super(posX, posY, sizeX, sizeY);

		this.listEntryHeight = listEntryHeight;
		this.verticalScrollbarData = verticalScrollbarData;
		this.horizontalScrollbarData = horizontalScrollbarData;
	}

	/**
	 * @return the attribute {@link #verticalScrollbarData}.
	 */
	public ScrollbarData getVerticalScrollbarData()
	{
		return this.verticalScrollbarData;
	}
	
	/**
	 * @return the attribute {@link #horizontalScrollbarData}.
	 */
	public ScrollbarData getHorizontalScrollbarData()
	{
		return this.horizontalScrollbarData;
	}

	public float getVerticalScrollerPos()
	{
		return this.verticalScrollbarData.getScrollerPos();
	}
	
	public void setVerticalScrollerLength(float length)
	{
		this.verticalScrollbarData.setScrollerLength(length);
	}
	
	public void setHorizontalScrollerLength(float length)
	{
		this.horizontalScrollbarData.setScrollerLength(length);
	}
	
	public float getHorizontalScrollerPos()
	{
		return this.horizontalScrollbarData.getScrollerPos();
	}
	
	/**
	 * @param range the Range of the vertical Scrollbar.
	 */
	public void setVerticalScrollbarRange(float range)
	{
		this.verticalScrollbarData.setScrollerSize(range);
	}

	/**
	 * @param range the Range of the horizontal Scrollbar.
	 */
	public void setHorizontalScrollbarRange(float range)
	{
		this.horizontalScrollbarData.setScrollerSize(range);
	}

	/**
	 * @see #listEntryHeight
	 */
	public int getListEntryHeight()
	{
		return this.listEntryHeight;
	}

	public abstract Iterator getListEntrysIterator();
	
	public abstract ListWidgetGraphic getGraphicInstance();
	
}
