package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * Manages the data of a scrollbar widget.
 * 
 * .- -
 * |  |
 * |  |
 * |  |
 * |  -scrollerPos
 * |  -
 * |  |
 * |  |scrollerSize (count of entrys may visible on screen)
 * |  |
 * |  -
 * |
 * `-scrollerLength (count of entrys in the main list)
 *
 * @author smk
 * @version 26.01.2004
 */
public class ScrollbarData
extends InputWidgetData
implements ClickedWidgetListenerInterface, ActivateWidgetListenerInterface, HitWidgetListenerInterface
{
	/**
	 * Position des Scrollers innerhalb von {@link #scrollerLength}.
	 */
	private float scrollerPos = 0.0F;

	/**
	 * Länge des Scrollers (ganze Bar).
	 */
	private float scrollerLength = 1.0F;

	/**
	 * Größe des Scrollers (Größe des Scroll-Buttons) im Verhältnis zu {@link #scrollerLength}.
	 */
	private float scrollerSize = 1.0F;
	
	/**
	 * Schrittweite um die gescrollt wird, wenn auf die Up-/Down-Buttons geklickt wird.
	 */
	private float scrollStep = 1.0F;

	/**
	 * true, wenn Vertical gescrollt werden soll,
	 * sonst wird Horizontal gescrollt.
	 */
	private boolean doScrollVertical;

	/**
	 * No Part of Scrollbar.
	 */
	public static final int SCROLLBAR_PART_NONE		= 0;
	/**
	 * Scroll-Up Button of Scrollbar.
	 */
	public static final int SCROLLBAR_PART_UP		= 1;
	/**
	 * Scroller Button of Scrollbar.
	 */
	public static final int SCROLLBAR_PART_SCROLLER	= 2;
	/**
	 * Scroll-Down Button of Scrollbar.
	 */
	public static final int SCROLLBAR_PART_DOWN		= 3;
	
	/**
	 * Wenn eine Scrollbar angeklickt wurde steht hier, welches Element der Bar
	 * ausgewählt wurde:<br/>
	 * 0:	{@link #SCROLLBAR_PART_NONE}:	None<br/>
	 * 1:	{@link #SCROLLBAR_PART_UP}:	Up-Scroller<br/>
	 * 2:	{@link #SCROLLBAR_PART_SCROLLER}:	Scroller<br/>
	 * 3:	{@link #SCROLLBAR_PART_DOWN}:	Down-Scroller<br/>
	 */
	private int activeScrollbarPart = 0;

	/**
	 * The last screen drag pos (if x or is y depands on {@link #doScrollVertical}.
	 * 
	 * #see #dragMode
	 */
	private int lastDragPos = 0;

	/**
	 * true, if scroller is in drag mode by mouse.
	 * 
	 * #see #lastDragPos
	 */
	public boolean dragMode = false;
	
	/**
	 * Is the width and height of the up- and down-button rectangle.
	 */
	private int buttonWidth;
	
	/**
	 * Constructor.
	 * 
	 */
	public ScrollbarData(DataChangedObserver dataChangedObserver,
						 String name, int posX, int posY, int sizeX, int sizeY, boolean doScrollVertical)
	{
		super(dataChangedObserver,
			  name, posX, posY, sizeX, sizeY);
	
		this.doScrollVertical = doScrollVertical;

		if (this.getDoScrollVertical() == true)
		{
			this.buttonWidth = this.getSizeX();
		}
		else
		{
			this.buttonWidth = this.getSizeY();
		}
	}

	/**
	 * @return the attribute {@link #scrollerLength}.
	 */
	public float getScrollerLength()
	{
		return this.scrollerLength;
	}
	/**
	 * @param scrollerLength is the new value for attribute {@link #scrollerLength} to set.
	 */
	public void setScrollerLength(float scrollerLength)
	{
		this.scrollerLength = scrollerLength;
		
		if ((this.scrollerPos + this.scrollerSize) > this.scrollerLength)
		{
			this.scrollerPos = this.scrollerLength - this.scrollerSize;
			
			if (this.scrollerPos < 0)
			{
				this.scrollerPos = 0;
			}
		}

		this.dataChangedVisible();
		this.scrollBarChanged();
	}
	/**
	 * @return the attribute {@link #scrollerPos}.
	 */
	public float getScrollerPos()
	{
		return this.scrollerPos;
	}
	/**
	 * @return the attribute {@link #scrollerSize}.
	 */
	public float getScrollerSize()
	{
		return this.scrollerSize;
	}
	/**
	 * @param scrollerPos is the new value for attribute {@link #scrollerPos} to set.
	 */
	public void setScrollerPos(float scrollerPos)
	{
		if (scrollerPos >= 0.0F)
		{	
			this.scrollerPos = scrollerPos;
		}
		else
		{
			this.scrollerPos = 0.0F;
		}

		this.dataChangedVisible();
		this.scrollBarChanged();
	}

	/**
	 * @param scrollerSize is the new value for attribute {@link #scrollerSize} to set.
	 */
	public void setScrollerSize(float scrollerSize)
	{
		this.scrollerSize = scrollerSize;

		this.dataChangedVisible();
		this.scrollBarChanged();
	}

	/**
	 * @return
	 */
	public int getScreenScrollerPos()
	{
		int screenLength = this.getScreenScrollerLength();
		
		//  screenLength     screenPos
		// -------------- = -----------
		// scrollerLength   scrollerPos
		
		int screenPos = (int)((this.scrollerPos * screenLength) / this.scrollerLength);
		
		return screenPos + this.buttonWidth;
	}

	/**
	 * @see #scrollerSize
	 * @see #scrollerLength
	 * @return the size of the scroller-button in points.
	 */
	public int getScreenScrollerSize()
	{
		int screenLength = this.getScreenScrollerLength();

		//  screenLength     screenSize
		// -------------- = -----------
		// scrollerLength   scrollerSize
		
		int screenSize;
		
		if (this.scrollerSize >= this.scrollerLength)
		{
			screenSize = screenLength;
		}
		else
		{	
			screenSize = (int)((screenLength * this.scrollerSize) / this.scrollerLength);
		}

		return screenSize;
	}
	
	/**
	 * @return the length of the howl scrollbar in points.
	 */
	public int getScreenScrollerLength() 
	{
		int screenLength;

		if (this.getDoScrollVertical() == true)
		{
			screenLength = this.getSizeY() - (this.buttonWidth * 2);
		}
		else
		{
			screenLength = this.getSizeX() - (this.buttonWidth * 2);
		}
		return screenLength;
	}

	/**
	 * @return the attribute {@link #doScrollVertical}.
	 */
	public boolean getDoScrollVertical()
	{
		return this.doScrollVertical;
	}
	/**
	 * @return the attribute {@link #scrollStep}.
	 */
	public float getScrollStep()
	{
		return this.scrollStep;
	}
	/**
	 * @param scrollStep is the new value for attribute {@link #scrollStep} to set.
	 */
	public void setScrollStep(float scrollStep)
	{
		this.scrollStep = scrollStep;
		this.scrollBarChanged();
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY)
	{
		int activeScrollbarPart = this.getActiveScrollbarPart();
		
		if (activeScrollbarPart == ScrollbarData.SCROLLBAR_PART_UP)
		{				
			float pos = this.getScrollerPos();
			
			if (pos > 0)
			{
				pos -= this.getScrollStep();
				this.setScrollerPos(pos);
			}
		}
		else
		{
			if (activeScrollbarPart == ScrollbarData.SCROLLBAR_PART_DOWN)
			{				
				float pos = this.getScrollerPos();
				
				if (pos < (this.getScrollerLength() - this.getScrollerSize()))
				{
					pos += this.getScrollStep();
					this.setScrollerPos(pos);
				}
			}
			else
			{
				if (activeScrollbarPart == ScrollbarData.SCROLLBAR_PART_SCROLLER)
				{
					this.dragMode = true;
					if (this.getDoScrollVertical() == true)
					{	
						this.lastDragPos = pointerPosY;
					}
					else
					{	
						this.lastDragPos = pointerPosX;
					}

					this.dataChangedVisible();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyReleasedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyReleasedWidget(WidgetData selectedWidgetData)
	{
		this.dragMode = false;

		this.dataChangedVisible();
	}

	/**
	 * @return the attribute {@link #activeScrollbarPart}.
	 */
	public int getActiveScrollbarPart()
	{
		return this.activeScrollbarPart;
	}
	/**
	 * @param activeScrollbarPart is the new value for attribute {@link #activeScrollbarPart} to set.
	 */
	public void setActiveScrollbarPart(int activeScrollbarPart)
	{
		this.activeScrollbarPart = activeScrollbarPart;

		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface#notifyActivateWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyActivateWidget(WidgetData widgetData)
	{
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface#notifyDeactivateWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyDeactivateWidget(WidgetData widgetData)
	{
		this.setActiveScrollbarPart(ScrollbarData.SCROLLBAR_PART_NONE);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface#notifyHitWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyHitWidget(WidgetData activeWidgetData, int pointerPosX, int pointerPosY)
	{
		int hitScrollbarPart = ScrollbarData.calcHitScrollbarPart(pointerPosX, pointerPosY, this);
		
		this.setActiveScrollbarPart(hitScrollbarPart);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyDragWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyDragWidget(WidgetData selectedWidgetData, int pointerPosX, int pointerPosY)
	{
		if (this.dragMode == true)
		{	
			int dragOffset;
		
			if (this.getDoScrollVertical() == true)
			{	
				dragOffset = pointerPosY - this.lastDragPos;
				this.lastDragPos = pointerPosY;
			}
			else
			{	
				dragOffset = pointerPosX - this.lastDragPos;
				this.lastDragPos = pointerPosX;
			}
			this.scrollByScreen(dragOffset);

			this.dataChangedVisible();
		}
	}
	
	/**
	 * @param dragOffset
	 */
	private void scrollByScreen(int dragOffset)
	{
		int sizeX = this.getSizeX();
		int sizeY = this.getSizeY();
		int screenSize;
		
		if (this.getDoScrollVertical() == true)
		{	
			screenSize = sizeY;
		}
		else
		{	
			screenSize = sizeX;
		}

		// this.scrollerLength          x
		// -------------------- = ----------
		//      screenSize        dragOffset
		
		float scroll = (this.scrollerLength * dragOffset) / screenSize;

		float pos = this.getScrollerPos();
		this.setScrollerPos(pos + scroll);
	}

	private static int calcHitScrollbarPart(int pointerPosX, int pointerPosY, ScrollbarData scrollbarData)
	{
		int sizeX = scrollbarData.getSizeX();
		int sizeY = scrollbarData.getSizeY();
		
		int hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_NONE;
		
		if (scrollbarData.getDoScrollVertical() == true)
		{	
			int width = sizeX;
			
			if (pointerPosY <= width)
			{
				// 1:	Up-Scroller
				hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_UP;
			}
			else
			{	
				if (pointerPosY >= (sizeY - width))
				{
					// 3:	Down-Scroller
					hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_DOWN;
				}
				else
				{	
					int scrollerPos = scrollbarData.getScreenScrollerPos();
					int scrollerSize = scrollbarData.getScreenScrollerSize();
					
					if ((pointerPosY >= (scrollerPos)) &&
						(pointerPosY <= (scrollerPos + scrollerSize)))
					{
						// 2:	Scroller
						hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_SCROLLER;
					}
				}
			}
		}
		else
		{
			int width = sizeY;
			
			if (pointerPosX <= width)
			{
				// 1:	Up-Scroller
				hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_UP;
			}
			else
			{	
				if (pointerPosX >= (sizeX - width))
				{
					// 3:	Down-Scroller
					hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_DOWN;
				}
				else
				{	
					int scrollerPos = scrollbarData.getScreenScrollerPos();
					int scrollerSize = scrollbarData.getScreenScrollerSize();
					
					if ((pointerPosX >= (scrollerPos)) &&
						(pointerPosX <= (scrollerPos + scrollerSize)))
					{
						// 2:	Scroller
						hitScrollbarPart = ScrollbarData.SCROLLBAR_PART_SCROLLER;
					}
				}
			}
		}
		return hitScrollbarPart;
	}

	private void scrollBarChanged()
	{
		if (this.scrollBarChangedListener != null)
		{
			this.scrollBarChangedListener.notifyScrollbarChanged(this);
		}
	}
	
	private ScrollBarChangedListenerInterface scrollBarChangedListener = null;
	
	public void addScrollBarChangedListener(ScrollBarChangedListenerInterface scrollBarChangedListener)
	{
		if (this.scrollBarChangedListener != null)
		{
			throw new RuntimeException("double use of this.scrollBarChangedListener, change implementation!");
		}
		this.scrollBarChangedListener = scrollBarChangedListener;
	}
}
