package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.dataUtils.VectorHash;
import de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.SubmitWidgetListenerInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class SelectData
extends InputWidgetData
implements FocusedWidgetListenerInterface, SubmitWidgetListenerInterface
{
	/**
	 * Selected position in the input select list.
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
	 * Constructor.
	 * 
	 * @param name
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public SelectData(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(name, posX, posY, sizeX, sizeY);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyFocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyFocusedWidget(WidgetData widgetData)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.FocusedWidgetListenerInterface#notifyDefocusedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyDefocusedWidget(WidgetData widgetData)
	{
		// TODO Auto-generated method stub
		
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
	}
	
	/**
	 * @param inputPos
	 * @return
	 */
	public SelectEntryData getSelectEntryData(int inputPos)
	{
		SelectEntryData selectEntryData;
		
		if (inputPos < this.selectEntrys.size())
		{	
			selectEntryData = (SelectEntryData)this.selectEntrys.get(inputPos);
		}
		else
		{
			selectEntryData = null;
		}
		
		return selectEntryData;
	}

	/**
	 * @param integer
	 */
	public void setInputPosByValue(Integer value)
	{
		int pos = 0;
		Iterator selectEntrysIterator = this.selectEntrys.iterator();
		
		while (selectEntrysIterator.hasNext())
		{
			SelectEntryData selectEntryData = (SelectEntryData)selectEntrysIterator.next();
			
			if (value.equals(selectEntryData.getValue()) == true)
			{
				break;
			}
			pos++;
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
		}
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
}
