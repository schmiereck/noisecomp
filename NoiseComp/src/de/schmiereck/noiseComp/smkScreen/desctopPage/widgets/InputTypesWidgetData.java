package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.util.Iterator;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.InputTypesData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.HitWidgetListenerInterface;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

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
	private InputTypesData inputTypesData = null;
	
	static private InputTypesWidgetGraphic listWidgetGraphic = null;
	
	private InputTypeData	activeInputTypeData = null;

	//private InputTypeData selectedInputTypeData = null;
	
	private InputTypeSelectedListenerInterface inputTypeSelectedListener = null;

	private SelectedListEntryInterface selectedListEntry;
	
	/**
	 * Constructor.
	 * 
	 */
	public InputTypesWidgetData(ControllerData controllerData,
								  DataChangedObserver dataChangedObserver,
								int posX, int posY, int sizeX, int sizeY,
								ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData,
								SelectedListEntryInterface selectedListEntry)
	{
		super(controllerData, dataChangedObserver,
			  posX, posY, sizeX, sizeY, 16, verticalScrollbarData, horizontalScrollbarData);
		
		this.selectedListEntry = selectedListEntry;
		
		// Momentane Anzahl Inputs zu...
		// ...maximal m�gliche Anzahl angezeigter Inputs.
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

		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY)
	{
		// Use Some Aktive Input (Rollover):
		
		InputTypeData selectedInputTypeData = this.activeInputTypeData;

		this.setSelectedInputTypeData(selectedInputTypeData);
		
		if (this.inputTypeSelectedListener != null)
		{
			if (selectedInputTypeData == null)
			{
				this.inputTypeSelectedListener.notifyInputTypeDeselected(this, selectedInputTypeData);
			}
			else
			{	
				this.inputTypeSelectedListener.notifyInputTypeSelected(this, selectedInputTypeData);
			}
		}

		this.dataChangedVisible();
	}
	
	public void setSelectedInputTypeData(InputTypeData selectedInputData)
	{
		// Select the Input.
		// Use this as the new selected Track.
		//this.selectedInputTypeData = selectedInputData;
		this.selectedListEntry.setSelectedInputTypeData(selectedInputData);
	}

	/**
	 * @return the attribute {@link #selectedInputTypeData}.
	 */
	public InputTypeData getSelectedInputTypeData()
	{
		//return this.selectedInputTypeData;
		return this.selectedListEntry.getSelectedInputTypeData();
	}
	
	public void deselectInputType_old()
	{
		// Deselect the Input.
		//this.selectedInputTypeData = null;

		this.activeInputTypeData = null;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyReleasedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData)
	 */
	public void notifyReleasedWidget(WidgetData selectedWidgetData)
	{
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

		this.dataChangedVisible();
	}
	/**
	 * @param inputTypeSelectedListener is the new value for attribute {@link #inputTypeSelectedListener} to set.
	 */
	public void registerInputTypeSelectedListener(InputTypeSelectedListenerInterface inputInputSelectedListener)
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
				//this.deselectInputType();
				this.selectedListEntry.setSelectedInputTypeData(null);
				this.activeInputTypeData = null;
				
				this.inputTypesData.removeInputTypeData(inputTypeData);
				
				this.setVerticalScrollerLength(this.getInputTypesCount());

				this.dataChangedVisible();
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyDragWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyDragWidget(WidgetData selectedWidgetData, int pointerPosX, int pointerPosY)
	{
	}

	/**
	 * 
	 */
	public void notifyInputTypeAdded()
	{
	}
	/**
	 * @param inputTypesData is the new value for attribute {@link #inputTypesData} to set.
	 */
	public void setInputTypesData(InputTypesData inputTypesData)
	{
		this.inputTypesData = inputTypesData;

		this.dataChangedVisible();
	}
}
