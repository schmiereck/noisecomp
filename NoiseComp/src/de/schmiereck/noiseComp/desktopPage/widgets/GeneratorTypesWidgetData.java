package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>25.02.2004: created, smk</p>
 */
public class GeneratorTypesWidgetData
extends ListWidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	private GeneratorTypesData generatorTypesData;
	
	static private GeneratorTypesWidgetGraphic listWidgetGraphic = null;
	
	private GeneratorTypeData	activeGeneratorTypeData = null;

	private GeneratorTypeData selectedGeneratorTypeData = null;
	
	private GeneratorTypeSelectedListenerInterface generatorTypeSelectedListener = null;

	/**
	 * Constructor.
	 * 
	 */
	public GeneratorTypesWidgetData(ControllerData controllerData,
									  DataChangedObserver dataChangedObserver,
									int posX, int posY, int sizeX, int sizeY,
									ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData,
									GeneratorTypesData generatorTypesData)
	{
		super(controllerData, dataChangedObserver,
			  posX, posY, sizeX, sizeY, 16, verticalScrollbarData, horizontalScrollbarData);
		
		this.generatorTypesData = generatorTypesData;

		// Momentane Anzahl Inputs zu...
		// ...maximal mögliche Anzahl angezeigter Inputs.
		this.setVerticalScrollbarRange((float)this.getSizeY() / (float)this.getListEntryHeight());
	}

	/**
	 * @param pos
	 * @return
	 */
	private GeneratorTypeData getGeneratorTypeData(int pos)
	{
		GeneratorTypeData ret;
		
		if (this.generatorTypesData != null)
		{
			ret = (GeneratorTypeData)this.generatorTypesData.get(pos);
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
	private int getGeneratorTypesCount()
	{
		int ret;
		
		if (this.generatorTypesData != null)
		{
			ret = this.generatorTypesData.getSize();
		}
		else
		{
			ret = 0;
		}
		return ret;
	}
	/**
	 * @return the attribute {@link #activeGeneratorTypeData}.
	 */
	public GeneratorTypeData getActiveGeneratorTypeData()
	{
		return this.activeGeneratorTypeData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData#getListEntrysIterator()
	 * @return the attribute {@link #inputs}.
	 */
	public Iterator getListEntrysIterator()
	{
		Iterator ret;
		
		if (this.generatorTypesData != null)
		{
			ret = this.generatorTypesData.getGeneratorTypesIterator();
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
		if (GeneratorTypesWidgetData.listWidgetGraphic == null)
		{
			GeneratorTypesWidgetData.listWidgetGraphic = new GeneratorTypesWidgetGraphic();
		}
		return GeneratorTypesWidgetData.listWidgetGraphic;
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
		if (this.activeGeneratorTypeData != null)
		{
			this.activeGeneratorTypeData = null;
		}

		this.dataChangedVisible();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY)
	{
		// Some Input is Aktive (Rollover) ?
		if (this.activeGeneratorTypeData != null)
		{
			this.setGeneratorTypeData(this.activeGeneratorTypeData);
		}
		else
		{
			this.deselectGeneratorType();
		}
	}

	public void setGeneratorTypeData(GeneratorTypeData selectedInputData)
	{
		// Select the Generator.
		// Use this as the new selected Track.
		this.selectedGeneratorTypeData = selectedInputData;
		
		if (this.generatorTypeSelectedListener != null)
		{
			this.generatorTypeSelectedListener.notifyGeneratorTypeSelected(this, this.selectedGeneratorTypeData);
		}

		this.dataChangedVisible();
	}

	/**
	 * @return the attribute {@link #selectedGeneratorTypeData}.
	 */
	public GeneratorTypeData getSelectedGeneratorTypeData()
	{
		return this.selectedGeneratorTypeData;
	}

	public void deselectGeneratorType()
	{
		GeneratorTypeData generatorTypeData = this.selectedGeneratorTypeData;

		// Deselect the Generator.
		this.selectedGeneratorTypeData = null;

		this.activeGeneratorTypeData = null;
		if (this.generatorTypeSelectedListener != null)
		{
			this.generatorTypeSelectedListener.notifyGeneratorTypeDeselected(this, generatorTypeData);
		}

		this.dataChangedVisible();
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
		GeneratorTypeData generatorTypeData = null;

		// Die Positionsnummer des Generators in der Liste.
		int pos = (pointerPosY / this.getListEntryHeight()) + ((int)this.getVerticalScrollerPos());
		
		// Innerhalb der Anzahl der vorhandneen generatoren ?
		if (pos < this.getGeneratorTypesCount())
		{
			generatorTypeData = this.getGeneratorTypeData(pos);
		}
		
		this.activeGeneratorTypeData = generatorTypeData;

		this.dataChangedVisible();
	}

	/**
	 * @param generatorTypeSelectedListener is the new value for attribute {@link #generatorTypeSelectedListener} to set.
	 */
	public void setGeneratorTypeSelectedListener(GeneratorTypeSelectedListenerInterface generatorInputSelectedListener)
	{
		this.generatorTypeSelectedListener = generatorInputSelectedListener;
	}

	/**
	 * 
	 */
	public void removeSelectedGeneratorType()
	{
		synchronized (this)
		{
			GeneratorTypeData generatorTypeData = this.getSelectedGeneratorTypeData();
			
			if (generatorTypeData != null)
			{
				this.deselectGeneratorType();
				
				this.generatorTypesData.removeGeneratorType(generatorTypeData);
				
				this.setVerticalScrollerLength(this.getGeneratorTypesCount());

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
}
