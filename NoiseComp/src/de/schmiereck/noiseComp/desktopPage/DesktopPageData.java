package de.schmiereck.noiseComp.desktopPage;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.WidgetData;


/**
 * <p>
 * 	Basisklasse einer Desktop-Seite.
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
	 * X-Größe des Desktops.
	 * @see #desktopSizeY
	 */
	private int desktopSizeX;
	
	/**
	 * Y-Größe des Desktops.
	 * @see #desktopSizeX
	 */
	private int desktopSizeY;
	
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
	 * Contains the clicked Widget as long as the Mouse Button is hold down,
	 * othewise null.
	 */
	private WidgetData selectedWidgetData = null;
	
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
	 * TODO remove this Scrollbar special stuff from this class, smk
	 */
	private ScrollbarData activeScrollbarData = null;

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
	 * @see #desktopSizeX
	 * @see #desktopSizeY
	 */
	public DesktopPageData(int desktopSizeX, int desktopSizeY)
	{
		this.desktopSizeX = desktopSizeX;
		this.desktopSizeY = desktopSizeY;
	}

	/**
	 * @return the attribute {@link #desktopSizeX}.
	 */
	public int getDesktopSizeX()
	{
		return this.desktopSizeX;
	}
	/**
	 * @return the attribute {@link #desktopSizeY}.
	 */
	public int getDesktopSizeY()
	{
		return this.desktopSizeY;
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
	public void setActiveScrollbarData(ScrollbarData hitScrollbarData)
	{
		// Is the already some Scrollbar active ?
		if (this.activeScrollbarData != null)
		{	
			// Reset active part to none.
			//this.activeScrollbarData.setActiveScrollbarPart(ScrollbarData.SCROLLBAR_PART_NONE);
		}
		this.activeScrollbarData = hitScrollbarData;
		if (this.activeScrollbarData != null)
		{	
			// Set the active part to given value.
			//this.activeScrollbarData.setActiveScrollbarPart(hitScrollbarPart);
		}
	}
	/**
	 * @return the attribute {@link #activeScrollbarData}.
	 */
	public ScrollbarData getActiveScrollbarData()
	{
		return this.activeScrollbarData;
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
	public void setActiveWidgetData(WidgetData activeWidgetData, int pointerPosX, int pointerPosY)
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
		
		if (activeWidgetData != null)
		{
			if (changed == true)
			{	
				if (activeWidgetData instanceof ActivateWidgetListenerInterface)
				{
					((ActivateWidgetListenerInterface)activeWidgetData).notifyActivateWidget(activeWidgetData);
				}
			}
			if (activeWidgetData instanceof HitWidgetListenerInterface)
			{
				((HitWidgetListenerInterface)activeWidgetData).notifyHitWidget(activeWidgetData, pointerPosX - activeWidgetData.getPosX(), pointerPosY - activeWidgetData.getPosY());
			}
		}
	}

	/**
	 * @return the attribute {@link #selectedWidgetData}.
	 */
	public WidgetData getSelectedWidgetData()
	{
		return this.selectedWidgetData;
	}
	/**
	 * @param selectedWidgetData is the new value for attribute {@link #selectedWidgetData} to set.
	 */
	public void setSelectedWidgetData(WidgetData selectedWidgetData)
	{
		this.selectedWidgetData = selectedWidgetData;
	}
}
