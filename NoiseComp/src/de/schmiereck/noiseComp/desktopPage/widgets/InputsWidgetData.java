package de.schmiereck.noiseComp.desktopPage.widgets;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.noiseComp.desktopPage.ActivateWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface;
import de.schmiereck.noiseComp.desktopPage.HitWidgetListenerInterface;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.InputData;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.02.2004
 */
public class InputsWidgetData
extends ListWidgetData
implements ActivateWidgetListenerInterface, ClickedWidgetListenerInterface, HitWidgetListenerInterface
{
	private GeneratorTypesData generatorTypesData;
	
	/**
	 * List with {@link GeneratorTypeData}-Objects.
	 */
	private Vector inputs = null;

	private static InputsWidgetGraphic listWidgetGraphic = null;
	
	private InputData	activeInputData = null;

	private InputData selectedInputData = null;
	private Generator selectedGenerator = null;
	
	private GeneratorInputSelectedListenerInterface generatorInputSelectedListener = null;

	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public InputsWidgetData(int posX, int posY, int sizeX, int sizeY,
					  ScrollbarData verticalScrollbarData, ScrollbarData horizontalScrollbarData,
					  GeneratorTypesData generatorTypesData)
	{
		super(posX, posY, sizeX, sizeY, 16, verticalScrollbarData, horizontalScrollbarData);
		
		this.generatorTypesData = generatorTypesData;

		// Momentane Anzahl Inputs zu...
		// ...maximal mögliche Anzahl angezeigter Inputs.
		this.setVerticalScrollbarRange((float)this.getSizeY() / (float)this.getListEntryHeight());
	}

	/**
	 * @see #selectedGenerator
	 * @see #inputs
	 */
	public void setGeneratorInputs(Generator selectedGenerator, Vector inputs)
	{
		this.selectedGenerator = selectedGenerator;
		
		if (this.inputs != inputs)
		{	
			this.deselectInput();
			
			this.inputs = inputs;
		}
	}

	
	/**
	 * @param pos
	 * @return
	 */
	private InputData getInputData(int pos)
	{
		InputData ret;
		
		if (this.inputs != null)
		{
			ret = (InputData)this.inputs.get(pos);
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
	private int getInputsCount()
	{
		int ret;
		
		if (this.inputs != null)
		{
			ret = this.inputs.size();
		}
		else
		{
			ret = 0;
		}
		return ret;
	}
	/**
	 * @return the attribute {@link #activeInputData}.
	 */
	public InputData getActiveInputData()
	{
		return this.activeInputData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData#getListEntrysIterator()
	 * @return the attribute {@link #inputs}.
	 */
	public Iterator getListEntrysIterator()
	{
		Iterator ret;
		
		if (this.inputs != null)
		{
			ret = this.inputs.iterator();
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
		if (InputsWidgetData.listWidgetGraphic == null)
		{
			InputsWidgetData.listWidgetGraphic = new InputsWidgetGraphic();
		}
		return InputsWidgetData.listWidgetGraphic;
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
		if (this.activeInputData != null)
		{
			this.activeInputData = null;
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.ClickedWidgetListenerInterface#notifyClickedWidget(de.schmiereck.noiseComp.desktopPage.widgets.WidgetData, int, int)
	 */
	public void notifyClickedWidget(WidgetData widgetData, int pointerPosX, int pointerPosY)
	{
		// Some Input is Aktive (Rollover) ?
		if (this.activeInputData != null)
		{
			this.setSelectedInputData(this.activeInputData);
		}
		else
		{
			this.deselectInput();
		}
	}

	public void setSelectedInputData(InputData selectedInputData)
	{
		// Select the Generator.
		// Use this as the new selected Track.
		this.selectedInputData = selectedInputData;
		
		if (this.generatorInputSelectedListener != null)
		{
			this.generatorInputSelectedListener.notifyGeneratorInputSelected(this, this.selectedInputData);
		}
	}

	/**
	 * @return the attribute {@link #selectedInputData}.
	 */
	public InputData getSelectedInputData()
	{
		return this.selectedInputData;
	}

	/**
	 * @return
	 */
	public Generator getSelectedGenerator()
	{
		return this.selectedGenerator;
	}
	
	public void deselectInput()
	{
		InputData inputData = this.selectedInputData;

		// Deselect the Generator.
		this.selectedInputData = null;

		this.activeInputData = null;
		if (this.generatorInputSelectedListener != null)
		{
			this.generatorInputSelectedListener.notifyGeneratorInputDeselected(this, inputData);
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
		InputData inputData = null;

		// Die Positionsnummer des Generators in der Liste.
		int screenPos = (pointerPosY / this.getListEntryHeight());
		
		int pos = screenPos + ((int)this.getVerticalScrollerPos());

		// Innerhalb der Anzahl der vorhandnenen Generatoren ?
		if (pos < this.getInputsCount())
		{
			inputData = this.getInputData(pos);
		}
		
		this.activeInputData = inputData;
	}
	/**
	 * @param generatorInputSelectedListener is the new value for attribute {@link #generatorInputSelectedListener} to set.
	 */
	public void registerGeneratorInputSelectedListener(GeneratorInputSelectedListenerInterface generatorInputSelectedListener)
	{
		this.generatorInputSelectedListener = generatorInputSelectedListener;
	}

	/**
	 * 
	 */
	public void removeSelectedInput()
	{
		synchronized (this)
		{
			InputData selectedInputData = this.getSelectedInputData();
			
			if (selectedInputData != null)
			{
				this.deselectInput();
				
				this.inputs.remove(selectedInputData);
				
				//this.soundData.removeGeneratorInput(selectedInputData);
				
				this.setVerticalScrollerLength(this.getInputsCount());
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
