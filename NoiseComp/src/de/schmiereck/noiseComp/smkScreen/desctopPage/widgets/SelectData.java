package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.util.Iterator;
import de.schmiereck.dataTools.VectorHash;
import de.schmiereck.noiseComp.smkScreen.desctopPage.FocusedWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.SubmitWidgetListenerInterface;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * <p>
 * 	Manages the data of a widget to select a single entry from a list.<br/>
 * </p>
 * Uses a list of 
 * {@link de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.SelectEntryData}-Objects.
 *
 * @author smk
 * @version 21.02.2004
 */
public class SelectData
extends InputWidgetData
implements FocusedWidgetListenerInterface, SubmitWidgetListenerInterface
{
	/**
	 * Selected position in the input select list.<br/>
	 * If {@link #useEmptyEntry} is true, the value can be -1.
	 */
	private int inputPos = 0;
	
	/**
	 * List of {@link SelectEntryData}-Objects.
	 */
	private VectorHash selectEntrys	= new VectorHash();
	
	/**
	 * Default Button, if the widget is submited.<br/>
	 * Normaly a {@link FunctionButtonData}-Object who perform the submit.
	 */
	private SubmitWidgetListenerInterface defaultSubmitWidgetInterface = null;

	/**
	 * if this attribute is true, {@link #inputPos} may contains -1.
	 */
	private boolean	useEmptyEntry = false;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public SelectData(ControllerData controllerData,
					  DataChangedObserver dataChangedObserver,
					  String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(controllerData, dataChangedObserver,
			  name, posX, posY, sizeX, sizeY);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyFocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyFocusedWidget(WidgetData widgetData)
	{
		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyDefocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyDefocusedWidget(WidgetData widgetData)
	{
		this.dataChangedVisible();
	}

	/**
	 * @return the attribute {@link #inputPos}.
	 */
	public int getInputPos()
	{
		return this.inputPos;
	}

	/**
	 * @see #inputPos
	 */
	public void setInputPos(int inputPos)
	{
		this.inputPos = inputPos;

		this.dataChangedVisible();
	}
	
	/**
	 * @param inputPos
	 * @return
	 */
	public SelectEntryData getSelectEntryData(int inputPos)
	{
		SelectEntryData selectEntryData;
		
		if (inputPos > -1)
		{	
			if (inputPos < this.selectEntrys.size())
			{	
				selectEntryData = (SelectEntryData)this.selectEntrys.get(inputPos);
			}
			else
			{
				selectEntryData = null;
			}
		}
		else
		{
			selectEntryData = null;
		}
		
		return selectEntryData;
	}

	/**
	 * @param value
	 */
	public void setInputPosByValue(Object value)
	{
		int pos = 0;
		
		if (this.useEmptyEntry == true)
		{
			if (value == null)
			{
				pos = -1;
			}
		}
		
		if (pos != -1)
		{	
			Iterator selectEntrysIterator = this.selectEntrys.iterator();
			
			while (selectEntrysIterator.hasNext())
			{
				SelectEntryData selectEntryData = (SelectEntryData)selectEntrysIterator.next();
				
				if (value == null)
				{
					if (selectEntryData.getValue() == null)
					{	
						break;
					}
				}
				else
				{	
					if (value.equals(selectEntryData.getValue()) == true)
					{
						break;
					}
				}
				pos++;
			}
		}
		this.setInputPos(pos);
	}

	/**
	 * @sse #selectEntrys
	 * @see #inputPos
	 */
	public SelectEntryData getSelectedEntryData()
	{
		return this.getSelectEntryData(this.inputPos);
	}

	/**
	 * 
	 */
	public void clearSelectEntrys()
	{
		this.selectEntrys.clear();
		this.setInputPos(0);
	}

	/**
	 * @param selectEntryData
	 */
	public void addSelectEntryData(SelectEntryData selectEntryData)
	{
		this.selectEntrys.add(selectEntryData.getValue(), selectEntryData);

		this.dataChangedVisible();
	}

	/**
	 * @param dir	1:	scroll Down<br/>
	 * 				-1:	scroll Up
	 */
	public void scrollInputPos(int dir)
	{
		if (dir == 1)
		{	
			if (this.inputPos < (this.selectEntrys.size() - 1))
			{
				this.inputPos++;
			}
		}
		else
		{
			if (this.inputPos > 0)
			{
				this.inputPos--;
			}
			else
			{
				if (this.useEmptyEntry == true)
				{
					this.inputPos = -1;
				}
			}
		}

		this.dataChangedVisible();
	}

	/**
	 * @see #selectEntrys
	 */
	public int getSelectEntrysCount()
	{
		return this.selectEntrys.size();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface#notifySubmit()
	 */
	public void notifySubmit()
	throws MainActionException
	{
		if (this.defaultSubmitWidgetInterface != null)
		{	
			this.defaultSubmitWidgetInterface.notifySubmit();
		}
	}
	
	/**
	 * @param defaultSubmitWidgetInterface is the new value for attribute {@link #defaultSubmitWidgetInterface} to set.
	 */
	public void setDefaultSubmitWidgetInterface(SubmitWidgetListenerInterface defaultSubmitWidgetInterface)
	{
		this.defaultSubmitWidgetInterface = defaultSubmitWidgetInterface;
	}

	/**
	 * @see #useEmptyEntry
	 */
	public void setUseEmptyEntry(boolean useEmptyEntry)
	{
		this.useEmptyEntry = useEmptyEntry;

		this.dataChangedVisible();
	}

	/**
	 * @see #useEmptyEntry
	 */
	public boolean getUseEmptyEntry()
	{
		return this.useEmptyEntry;
	}
}
