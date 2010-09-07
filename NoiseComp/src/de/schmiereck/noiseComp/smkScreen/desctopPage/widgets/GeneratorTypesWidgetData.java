package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import java.util.Iterator;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.smkScreen.desctopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.HitWidgetListenerInterface;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * Generator-Types Widget Data.
 *
 * @author smk
 * @version <p>25.02.2004: created, smk</p>
 */
public class GeneratorTypesWidgetData
extends ListWidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
//	private GeneratorTypesData generatorTypesData;
	
	static private GeneratorTypesWidgetGraphic listWidgetGraphic = null;
	
	private GeneratorTypeData activeGeneratorTypeData = null;

	private GeneratorTypeData selectedGeneratorTypeData = null;
	
	private GeneratorTypeSelectedListenerInterface generatorTypeSelectedListener = null;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public GeneratorTypesWidgetData(ControllerData controllerData,
	                                DataChangedObserver dataChangedObserver,
									int posX, int posY, int sizeX, int sizeY,
									ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData)
	{
		super(controllerData, dataChangedObserver,
			  posX, posY, sizeX, sizeY, 16, verticalScrollbarData, horizontalScrollbarData);
		
//		this.generatorTypesData = generatorTypesData;

		// Momentane Anzahl Inputs zu...
		// ...maximal m�gliche Anzahl angezeigter Inputs.
		this.setVerticalScrollbarRange((float)this.getSizeY() / (float)this.getListEntryHeight());
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
	public Iterator<GeneratorTypeData> getListEntrysIterator()
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		Iterator<GeneratorTypeData> ret;
		
		ret = soundService.retrieveGeneratorTypesIterator();

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
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		GeneratorTypeData generatorTypeData = null;

		// Die Positionsnummer des Generators in der Liste.
		int pos = (pointerPosY / this.getListEntryHeight()) + ((int)this.getVerticalScrollerPos());
		
		// Innerhalb der Anzahl der vorhandneen generatoren ?
		if (pos < soundService.getGeneratorTypesCount())
		{
			generatorTypeData = soundService.getGeneratorTypeData(pos);
		}
		
		this.activeGeneratorTypeData = generatorTypeData;

		this.dataChangedVisible();
		//==========================================================================================
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
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		synchronized (this)
		{
			GeneratorTypeData generatorTypeData = this.getSelectedGeneratorTypeData();
			
			if (generatorTypeData != null)
			{
				this.deselectGeneratorType();
				
				soundService.removeGeneratorType(generatorTypeData);
				
				this.setVerticalScrollerLength(soundService.getGeneratorTypesCount());

				this.dataChangedVisible();
			}
		}
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyDragWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyDragWidget(WidgetData selectedWidgetData, int pointerPosX, int pointerPosY)
	{
	}
}
