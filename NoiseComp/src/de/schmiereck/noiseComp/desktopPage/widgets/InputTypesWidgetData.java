package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.InputTypesData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>21.03.2004: created, smk</p>
 */
public class InputTypesWidgetData
extends ListWidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	private InputTypesData inputTypesData;
	
	static private InputTypesWidgetGraphic listWidgetGraphic = null;
	
	private InputTypeData	activeInputTypeData = null;

	private InputTypeData selectedInputTypeData = null;
	
	private InputTypeSelectedListenerInterface inputTypeSelectedListener = null;

	/**
	 * Constructor.
	 * 
	 */
	public InputTypesWidgetData(int posX, int posY, int sizeX, int sizeY,
			ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData,
			InputTypesData inputTypesData)
	{
		super(posX, posY, sizeX, sizeY, 16, verticalScrollbarData, horizontalScrollbarData);
		
		this.inputTypesData = inputTypesData;

		// Momentane Anzahl Inputs zu...
		// ...maximal mögliche Anzahl angezeigter Inputs.
		this.setVerticalScrollbarRange((float)this.getSizeY() / (float)this.getListEntryHeight());
	}

	/**
	 * @param pos
	 * @return
	 */
	private InputTypeData getInputTypeData(int pos)
	{
		InputTypeData ret;
		
		if (this.inputTypesData != null)
		{
			ret = (InputTypeData)this.inputTypesData.getInputTypeDataByPos(pos);
		}
		else
		{
			ret = null;
		}
		return ret;
	}

	/**
	 * @return
	 */
	private int getInputTypesCount()
	{
		int ret;
		
		if (this.inputTypesData != null)
		{
			ret = this.inputTypesData.getInputTypesSize();
		}
		else
		{
			ret = 0;
		}
		return ret;
	}
	/**
	 * @return the attribute {@link #activeInputTypeData}.
	 */
	public InputTypeData getActiveInputTypeData()
	{
		return this.activeInputTypeData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData#getListEntrysIterator()
	 * @return the attribute {@link #inputs}.
	 */
	public Iterator getListEntrysIterator()
	{
		Iterator ret;
		
		if (this.inputTypesData != null)
		{
			ret = this.inputTypesData.getInputTypesIterator();
		}
		else
		{
			ret = null;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData#getGraphicInstance()
	 */
	public ListWidgetGraphic getGraphicInstance()
	{
		if (InputTypesWidgetData.listWidgetGraphic == null)
		{
			InputTypesWidgetData.listWidgetGraphic = new InputTypesWidgetGraphic();
		}
		return InputTypesWidgetData.listWidgetGraphic;
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
		if (this.activeInputTypeData != null)
		{
			this.activeInputTypeData = null;
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY)
	{
		// Some Input is Aktive (Rollover) ?
		if (this.activeInputTypeData != null)
		{
			this.setInputTypeData(this.activeInputTypeData);
		}
		else
		{
			this.deselectInputType();
		}
	}

	public void setInputTypeData(InputTypeData selectedInputData)
	{
		// Select the Input.
		// Use this as the new selected Track.
		this.selectedInputTypeData = selectedInputData;
		
		if (this.inputTypeSelectedListener != null)
		{
			this.inputTypeSelectedListener.notifyInputTypeSelected(this, this.selectedInputTypeData);
		}
	}

	/**
	 * @return the attribute {@link #selectedInputTypeData}.
	 */
	public InputTypeData getSelectedInputTypeData()
	{
		return this.selectedInputTypeData;
	}

	public void deselectInputType()
	{
		InputTypeData inputTypeData = this.selectedInputTypeData;

		// Deselect the Input.
		this.selectedInputTypeData = null;

		this.activeInputTypeData = null;
		if (this.inputTypeSelectedListener != null)
		{
			this.inputTypeSelectedListener.notifyInputTypeDeselected(this, inputTypeData);
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyReleasedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyReleasedWidget(WidgetData selectedWidgetData)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface#notifyHitWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyHitWidget(WidgetData activeWidgetData, int pointerPosX, int pointerPosY)
	{
		InputTypeData inputTypeData = null;

		// Die Positionsnummer des Inputs in der Liste.
		int pos = (pointerPosY / this.getListEntryHeight()) + ((int)this.getVerticalScrollerPos());
		
		// Innerhalb der Anzahl der vorhandneen inputen ?
		if (pos < this.getInputTypesCount())
		{
			inputTypeData = this.getInputTypeData(pos);
		}
		
		this.activeInputTypeData = inputTypeData;
	}
	/**
	 * @param inputTypeSelectedListener is the new value for attribute {@link #inputTypeSelectedListener} to set.
	 */
	public void setInputTypeSelectedListener(InputTypeSelectedListenerInterface inputInputSelectedListener)
	{
		this.inputTypeSelectedListener = inputInputSelectedListener;
	}

	/**
	 * 
	 */
	public void removeSelectedInputType()
	{
		synchronized (this)
		{
			InputTypeData inputTypeData = this.getSelectedInputTypeData();
			
			if (inputTypeData != null)
			{
				this.deselectInputType();
				
				this.inputTypesData.removeInputTypeData(inputTypeData);
				
				this.setVerticalScrollerLength(this.getInputTypesCount());
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyDragWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyDragWidget(WidgetData selectedWidgetData, int pointerPosX, int pointerPosY)
	{
		// TODO Auto-generated method stub
		
	}
}
