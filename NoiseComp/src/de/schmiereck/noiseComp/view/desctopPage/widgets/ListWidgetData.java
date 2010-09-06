package de.schmiereck.noiseComp.view.desctopPage.widgets;

import java.util.Iterator;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * Basic Functions for a scrolling List Area.
 *
 * @author smk
 * @version 08.02.2004
 */
public abstract class ListWidgetData
extends WidgetData
implements ScrollBarChangedListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	private int listEntryHeight;
	
	private ScrollbarData verticalScrollbarData;
	private ScrollbarData horizontalScrollbarData;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public ListWidgetData(ControllerData controllerData,
						  DataChangedObserver dataChangedObserver,
						  int posX, int posY, int sizeX, int sizeY,
						  int listEntryHeight,
						  ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData)
	{
		super(controllerData, dataChangedObserver,
			  posX, posY, sizeX, sizeY, true);

		this.listEntryHeight = listEntryHeight;
		this.verticalScrollbarData = verticalScrollbarData;
		this.horizontalScrollbarData = horizontalScrollbarData;
		
		if (this.verticalScrollbarData != null)
		{
			this.verticalScrollbarData.addScrollBarChangedListener(this);
		}
		
		if (this.horizontalScrollbarData != null)
		{
			this.horizontalScrollbarData.addScrollBarChangedListener(this);
		}
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
		
		//this.dataChangedVisible();
	}
	
	public void setHorizontalScrollerLength(float length)
	{
		this.horizontalScrollbarData.setScrollerLength(length);

		//this.dataChangedVisible();
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
	
	/**
	 * @return the implementation of the Grafic to view this data object.
	 */
	public abstract ListWidgetGraphic getGraphicInstance();
	
	public void notifyScrollbarChanged(ScrollbarData scrollbarData)
	{
		this.dataChangedVisible();
	}
}
