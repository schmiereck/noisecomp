package de.schmiereck.noiseComp.desktopPage;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
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
	private DesktopData desktopData;
	
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
	private InputWidgetData pressedButtonData = null;
	
	/**
	 * Wenn ein Button mit der Maus überfahren wird, nachdem er angeklickt wurde,
	 * wurde ist steht hier welche das war,<br/>
	 * ansonsten ist der Wert null.
	 * 
	 * @see #pressedButtonData
	 */
	private InputWidgetData activeButtonData = null;
	
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
	public DesktopPageData(DesktopData desktopData, int desktopSizeX, int desktopSizeY)
	{
		this.desktopData = desktopData;
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
	/*
	 * @see #pointerPosX
	 * @see #pointerPosY
	 *
	public void setPointerPos(int posX, int posY)
	{
		this.pointerPosX = posX;
		this.pointerPosY = posY;
	}
	*/
	/**
	 * @return the attribute {@link #desktopData}.
	 */
	public int getPointerPosX()
	{
		return this.desktopData.getPointerPosX();
	}
	/**
	 * @return the attribute {@link #desktopData}.
	 */
	public int getPointerPosY()
	{
		return this.desktopData.getPointerPosY();
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
	public InputWidgetData getPressedButtonData()
	{
		return this.pressedButtonData;
	}

	/**
	 * @see #activeButtonData
	 */
	public void setActiveButtonData(InputWidgetData buttonData)
	{
		this.activeButtonData = buttonData;
	}

	/**
	 * @see #activeButtonData
	 */
	public InputWidgetData getActiveButtonData()
	{
		return this.activeButtonData;
	}

	/**
	 * @see #focusedWidgetData
	 */
	public void setFocusedWidgetData(WidgetData focusedWidgetData)
	{
		//this.focusedWidgetData = focusedWidgetData;
		boolean changed;
		if (this.focusedWidgetData != focusedWidgetData)
		{
			changed = true;
		}
		else
		{
			changed = false;
		}
		
		if (changed == true)
		{	
			if (this.focusedWidgetData != null)
			{
				if (this.focusedWidgetData instanceof FocusedWidgetListenerInterface)
				{
					((FocusedWidgetListenerInterface)this.focusedWidgetData).notifyDefocusedWidget(this.focusedWidgetData);
				}
			}
		}
		
		this.focusedWidgetData = focusedWidgetData;
		
		if (focusedWidgetData != null)
		{
			if (changed == true)
			{	
				if (focusedWidgetData instanceof FocusedWidgetListenerInterface)
				{
					((FocusedWidgetListenerInterface)focusedWidgetData).notifyFocusedWidget(focusedWidgetData);
				}
			}
		}
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

	/**
	 * @param dir
	 */
	public void focusWalk(int dir)
	{
		if (this.focusedWidgetData == null)
		{
			this.setFocusToFirstWidget();
		}
		else
		{
			boolean foundFocusedWidget = false;
			WidgetData prevAcceptWidgetData = null;
			WidgetData nextAcceptWidgetData = null;
			Iterator widgetsIterator = this.widgets.iterator();
			
			while (widgetsIterator.hasNext())
			{
				WidgetData widgetData = (WidgetData)widgetsIterator.next();

				// Is this the actual focused widget ?
				if (this.focusedWidgetData == widgetData)
				{
					foundFocusedWidget = true;
				}
				else
				{
					if (foundFocusedWidget == true)
					{
						if (widgetData.getAcceptFocus() == true)
						{
							nextAcceptWidgetData = widgetData;
							break;
						}
					}
					else
					{
						if (widgetData.getAcceptFocus() == true)
						{
							prevAcceptWidgetData = widgetData;
						}
					}
				}
			}

			// Search next widget after actual ?
			if (dir == 1)
			{
				if (nextAcceptWidgetData != null)
				{	
					this.setFocusedWidgetData(nextAcceptWidgetData);
				}
				else
				{
					if (prevAcceptWidgetData != null)
					{	
						this.setFocusedWidgetData(prevAcceptWidgetData);
					}
				}
			}
			else
			{
				// Search previouse widget bevor actual ?
				
				if (prevAcceptWidgetData != null)
				{	
					this.setFocusedWidgetData(prevAcceptWidgetData);
				}
				else
				{
					if (nextAcceptWidgetData != null)
					{	
						this.setFocusedWidgetData(nextAcceptWidgetData);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private void setFocusToFirstWidget()
	{
		Iterator widgetsIterator = this.widgets.iterator();
		
		while (widgetsIterator.hasNext())
		{
			WidgetData widgetData = (WidgetData)widgetsIterator.next();
			
			if (widgetData.getAcceptFocus() == true)
			{
				this.setFocusedWidgetData(widgetData);
				break;
			}
		}
	}

	/**
	 * 
	 */
	public void submitPage()
	{
		if (this.focusedWidgetData != null)
		{
			/*
			if (this.focusedWidgetData instanceof FunctionButtonData)
			{
				FunctionButtonData functionButtonData = (FunctionButtonData)this.focusedWidgetData;
				
				if (functionButtonData.getButtonActionLogicListener() != null)
				{
					functionButtonData.getButtonActionLogicListener().notifyButtonReleased(functionButtonData);
				}
			}
			*/
			if (this.focusedWidgetData instanceof SubmitWidgetListenerInterface)
			{
				((SubmitWidgetListenerInterface)this.focusedWidgetData).notifySubmit();
			}
		}
	}
	/**
	 * @return the attribute {@link #desktopData}.
	 */
	public DesktopData getDesktopData()
	{
		return this.desktopData;
	}
}
