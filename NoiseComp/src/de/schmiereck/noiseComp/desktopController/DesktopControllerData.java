package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorsGraphicData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.screenTools.controller.ControllerData;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.01.2004
 */
public class DesktopControllerData
extends ControllerData
{
	private SoundData	soundData = null;

	private boolean playSound = false;

	/**
	 * Daten der gerade angezeigte Seite.
	 */
	private DesktopPageData activeDesctopPageData = null; 
	
	/**
	 * Vertikale Scrollbar des Generator-Widgets.
	 */
	private ScrollbarData generatorsVScrollbarData;
	
	/**
	 * Horizontale Scrollbar des Generator-Widgets.
	 */
	private ScrollbarData generatorsHScrollbarData;
	
	private GeneratorsGraphicData generatorsGraphicData;
	
	private DesktopPageData mainDesctopPageData				= null;
	private DesktopPageData selectGeneratorDesctopPageData	= null;

	/**
	 * Constructor.
	 * 
	 * @param soundData
	 */
	public DesktopControllerData(SoundData soundData)
	{
		this.soundData = soundData;

		this.mainDesctopPageData = this.createMainPage();
		this.selectGeneratorDesctopPageData = this.createSelectGeneratorPage();
		
		this.activeDesctopPageData = this.mainDesctopPageData;
	}

	private DesktopPageData createSelectGeneratorPage()
	{
		DesktopPageData	desctopPageData = new DesktopPageData(this.getFieldWidth(), this.getFieldHeight());

		{
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desctopPageData.addWidgetData(paneData);
		}
		
		{
			ButtonData addButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			desctopPageData.addWidgetData(addButtonData);
		}

		{
			ButtonData addButtonData = new FunctionButtonData("addSinus", "Add Sinus Generator", 100, 100, 250, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("addFader", "Add Fader Generator", 100, 130, 250, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("addMixer", "Add Mixer Generator", 100, 160, 250, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("addOutput", "Add Output Generator", 100, 190, 250, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		return desctopPageData;
	}
	
	private DesktopPageData createMainPage()
	{
		int topMenuSizeY			= 32 * 2;
		int bottomMenuSizeY			= 32 * 4;
		int scrollbarWidth			= 20;
		int generatorsLabelSizeX	= 100;

		DesktopPageData	desctopPageData = new DesktopPageData(this.getFieldWidth(), this.getFieldHeight());

		{
			PaneData topMenuPaneData = new PaneData(0, 0, this.getFieldWidth(), topMenuSizeY);
			desctopPageData.addWidgetData(topMenuPaneData);
		}
		{
			PaneData bottomMenuPaneData = new PaneData(0, this.getFieldHeight() - bottomMenuSizeY, this.getFieldWidth(), bottomMenuSizeY);
			desctopPageData.addWidgetData(bottomMenuPaneData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("add", "Add...", 100, 10, 90, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("remove", "Remove", 200, 10, 90, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("exit", "Exit", this.getFieldWidth() - 100, 10, 90, 20);
			desctopPageData.addWidgetData(addButtonData);
		}
		
		ButtonData playButtonData = new FunctionButtonData("play", "Play", 100, 40, 90, 20);
		
		desctopPageData.addWidgetData(playButtonData);

		ButtonData pauseButtonData = new FunctionButtonData("pause", "Pause", 200, 40, 90, 20);
		
		desctopPageData.addWidgetData(pauseButtonData);

		ButtonData stopButtonData = new FunctionButtonData("stop", "Stop", 300, 40, 90, 20);
		
		desctopPageData.addWidgetData(stopButtonData);
		
		int generatorsSizeY = this.getFieldHeight() - (topMenuSizeY + scrollbarWidth + bottomMenuSizeY);
		
		this.generatorsVScrollbarData = 
			new ScrollbarData("generatorsVScroll", this.getFieldWidth() - scrollbarWidth, topMenuSizeY, 
					scrollbarWidth, generatorsSizeY, true);
		
		this.generatorsHScrollbarData = 
			new ScrollbarData("generatorsHScroll", 
					generatorsLabelSizeX, topMenuSizeY + generatorsSizeY, 
					this.getFieldWidth() - (generatorsLabelSizeX + scrollbarWidth), scrollbarWidth, false);
		
		this.generatorsHScrollbarData.setScrollStep(0.5F);
		
		this.generatorsGraphicData = 
			new GeneratorsGraphicData(0, topMenuSizeY, 
					this.getFieldWidth() - scrollbarWidth, generatorsSizeY, 
					generatorsLabelSizeX,
					this.soundData,
					this.generatorsVScrollbarData, this.generatorsHScrollbarData);
		
		desctopPageData.addWidgetData(this.generatorsGraphicData);
		
		desctopPageData.addWidgetData(this.generatorsVScrollbarData);
		
		desctopPageData.addWidgetData(this.generatorsHScrollbarData);
		return desctopPageData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerData#getObjectsCount()
	 */
	public int getObjectsCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerData#getObjectsIterator()
	 */
	public Iterator getObjectsIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerData#getFieldWidth()
	 */
	public int getFieldWidth()
	{
		return 800;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerData#getFieldHeight()
	 */
	public int getFieldHeight()
	{
		return 600;
	}

	/**
	 * @return the attribute {@link #soundData}.
	 */
	public SoundData getSoundData()
	{
		return this.soundData;
	}
	/**
	 * @return the attribute {@link #activeDesctopPageData}.
	 */
	public DesktopPageData getActiveDesctopPageData()
	{
		return this.activeDesctopPageData;
	}
	/**
	 * @return the attribute {@link #generatorsVScrollbarData}.
	 */
	public ScrollbarData getGeneratorsScrollbarData()
	{
		return this.generatorsVScrollbarData;
	}
	/**
	 * @return the attribute {@link #generatorsGraphicData}.
	 */
	public GeneratorsGraphicData getGeneratorsGraphicData()
	{
		return this.generatorsGraphicData;
	}
	/**
	 * @return the attribute {@link #mainDesctopPageData}.
	 */
	public DesktopPageData getMainDesctopPageData()
	{
		return this.mainDesctopPageData;
	}
	/**
	 * @return the attribute {@link #selectGeneratorDesctopPageData}.
	 */
	public DesktopPageData getSelectGeneratorDesctopPageData()
	{
		return this.selectGeneratorDesctopPageData;
	}
	/**
	 * @param activeDesctopPageData is the new value for attribute {@link #activeDesctopPageData} to set.
	 */
	public void setActiveDesctopPageData(DesktopPageData activeDesctopPageData)
	{
		this.activeDesctopPageData = activeDesctopPageData;
	}
}

