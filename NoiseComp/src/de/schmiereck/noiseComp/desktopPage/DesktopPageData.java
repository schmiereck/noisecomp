package de.schmiereck.noiseComp.desktopPage;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;


/**
 * <p>
 * 	Basisklasse einer Desctop-Seite.
 * </p>
 * <p>
 * 	Verwaltet eine Liste von {@link WidgetData}-Objekten die 
 * 	Ein- und Ausgabe-Controlls darstellen, sowie den Status der Seite (Focus, ...).
 * </p>
 *
 * @author smk
 * @version 29.01.2004
 */
public class DesktopPageData
{
	/**
	 * X-Größe des Desctops.
	 * @see #desctopSizeY
	 */
	private int desctopSizeX;
	
	/**
	 * Y-Größe des Desctops.
	 * @see #desctopSizeX
	 */
	private int desctopSizeY;
	
	/**
	 * X-Position des Mauszeigers.
	 * @see #pointerPosY
	 */
	private int pointerPosX = 0;

	/**
	 * Y-Position des Mauszeigers.
	 * @see #pointerPosX
	 */
	private int pointerPosY = 0;

	/**
	 * List of {@link WidgetData}-Objects.
	 */
	private Vector widgets = new Vector();

	/**
	 * Wenn ein Widget mit der Maus überfahren wird, nachdem er angeklickt wurde,
	 * wurde ist steht hier welche das war,<br/>
	 * ansonsten ist der Wert null.
	 * 
	 * @see #pressedButtonData
	 */
	private WidgetData activeWidgetData = null;

	/**
	 * Wenn ein Button angeklickt wurde ist steht hier welcher das war,<br/>
	 * ansonsten ist der Wert null.
	 */
	private ButtonData pressedButtonData = null;
	
	/**
	 * Wenn ein Button mit der Maus überfahren wird, nachdem er angeklickt wurde,
	 * wurde ist steht hier welche das war,<br/>
	 * ansonsten ist der Wert null.
	 * 
	 * @see #pressedButtonData
	 */
	private ButtonData activeButtonData = null;
	
	/**
	 * Wenn eine Scrollbar angeklickt wurde ist steht hier welche das war,<br/>
	 * ansonsten ist der Wert null.
	 * 
	 * @see #activeScrollbarPart
	 */
	private ScrollbarData activeScrollbarData = null;

	/**
	 * Wenn eine Scrollbar angeklickt wurde steht hier, welches Element der Bar
	 * ausgewählt wurde:<br/>
	 * 0:	None<br/>
	 * 1:	Up-Scroller<br/>
	 * 2:	Scroller<br/>
	 * 3:	Down-Scroller<br/>
	 */
	private int activeScrollbarPart = 0;

	/**
	 * A reference to the focused Widget or null.
	 */
	private WidgetData focusedWidgetData = null;
	
	/**
	 * true, wenn die Maustaste gerade gedrückt gehalten wird.
	 */
	private boolean pointerPressed = false;

	/**
	 * Constructor.
	 * 
	 * @see #desctopSizeX
	 * @see #desctopSizeY
	 */
	public DesktopPageData(int desctopSizeX, int desctopSizeY)
	{
		this.desctopSizeX = desctopSizeX;
		this.desctopSizeY = desctopSizeY;
	}

	/**
	 * @return the attribute {@link #desctopSizeX}.
	 */
	public int getDesctopSizeX()
	{
		return this.desctopSizeX;
	}
	/**
	 * @return the attribute {@link #desctopSizeY}.
	 */
	public int getDesctopSizeY()
	{
		return this.desctopSizeY;
	}
	/**
	 * @see #pointerPosX
	 * @see #pointerPosY
	 */
	public void setPointerPos(int posX, int posY)
	{
		this.pointerPosX = posX;
		this.pointerPosY = posY;
	}
	/**
	 * @return the attribute {@link #pointerPosX}.
	 */
	public int getPointerPosX()
	{
		return this.pointerPosX;
	}
	/**
	 * @return the attribute {@link #pointerPosY}.
	 */
	public int getPointerPosY()
	{
		return this.pointerPosY;
	}
	
	/**
	 * Fügt der Seite ein neues Widget hinzu.
	 */
	public void addWidgetData(WidgetData widgetData)
	{
		this.widgets.add(widgetData);
	}
	
	/**
	 * Liefert einen iterator über alle Widgets der Seite.
	 */
	public Iterator getWidgetsIterator()
	{
		return this.widgets.iterator();
	}

	/**
	 * @see #pointerPressed
	 */
	public void setPointerPressed(boolean pointerPressed)
	{
		this.pointerPressed = pointerPressed;
		
		if (pointerPressed == true)
		{	
			if (this.activeButtonData != null)
			{
				this.pressedButtonData = this.activeButtonData;
			}
			else
			{
				this.pressedButtonData = null;
			}
		}
		else
		{
			this.pressedButtonData = null;
		}
	}
	
	/**
	 * @see #pressedButtonData
	 */
	public ButtonData getPressedButtonData()
	{
		return this.pressedButtonData;
	}

	/**
	 * @see #activeButtonData
	 */
	public void setActiveButtonData(ButtonData buttonData)
	{
		this.activeButtonData = buttonData;
	}

	/**
	 * @see #activeButtonData
	 */
	public ButtonData getActiveButtonData()
	{
		return this.activeButtonData;
	}

	/**
	 * @see #focusedWidgetData
	 */
	public void setFocusedWidgetData(WidgetData widgetData)
	{
		this.focusedWidgetData = widgetData;
	}
	
	/**
	 * @see #focusedWidgetData
	 */
	public WidgetData getFocusedWidgetData()
	{
		return this.focusedWidgetData;
	}
	
	/**
	 * @param hitScrollbarData
	 * @param hitScrollbarPart
	 */
	public void setActiveScrollbarData(ScrollbarData hitScrollbarData, int hitScrollbarPart)
	{
		this.activeScrollbarData = hitScrollbarData;
		this.activeScrollbarPart = hitScrollbarPart;
	}
	/**
	 * @return the attribute {@link #activeScrollbarData}.
	 */
	public ScrollbarData getActiveScrollbarData()
	{
		return this.activeScrollbarData;
	}
	/**
	 * @return the attribute {@link #activeScrollbarPart}.
	 */
	public int getActiveScrollbarPart()
	{
		return this.activeScrollbarPart;
	}
	
	/**
	 * @see #pointerPressed
	 */
	public boolean getPointerPressed()
	{
		return this.pointerPressed;
	}

	/**
	 * @return the attribute {@link #activeWidgetData}.
	 */
	public WidgetData getActiveWidgetData()
	{
		return this.activeWidgetData;
	}
	/**
	 * @param activeWidgetData is the new value for attribute {@link #activeWidgetData} to set.
	 */
	public void setActiveWidgetData(WidgetData activeWidgetData)
	{
		boolean changed;
		if (this.activeWidgetData != activeWidgetData)
		{
			changed = true;
		}
		else
		{
			changed = false;
		}
		
		if (changed == true)
		{	
			if (this.activeWidgetData != null)
			{
				if (this.activeWidgetData instanceof ActivateWidgetListenerInterface)
				{
					((ActivateWidgetListenerInterface)this.activeWidgetData).notifyDeactivateWidget(this.activeWidgetData);
				}
			}
		}
		
		this.activeWidgetData = activeWidgetData;
		
		if (changed == true)
		{	
			if (activeWidgetData != null)
			{
				if (activeWidgetData instanceof ActivateWidgetListenerInterface)
				{
					((ActivateWidgetListenerInterface)activeWidgetData).notifyActivateWidget(activeWidgetData);
				}
			}
		}
	}

}
