package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputsData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
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
	private DesktopPageData activeDesktopPageData = null; 
	
	/**
	 * Vertikale Scrollbar des Generator-Widgets.
	 */
	private ScrollbarData generatorsVScrollbarData;
	
	/**
	 * Horizontale Scrollbar des Generator-Widgets.
	 */
	private ScrollbarData generatorsHScrollbarData;
	
	private TracksData tracksData;
	
	private DesktopPageData mainDesktopPageData				= null;
	private DesktopPageData selectGeneratorDesktopPageData	= null;

	private InputlineData generatorNameInputlineData = null;
	private InputlineData generatorStartTimeInputlineData = null;
	private InputlineData generatorEndTimeInputlineData = null;
	private InputsData generatorInputsData = null;
	private InputlineData generatorInputNameInputlineData = null;
	private InputlineData generatorInputTypeInputlineData = null;
	
	/**
	 * Constructor.
	 * 
	 * @param soundData
	 */
	public DesktopControllerData(SoundData soundData)
	{
		this.soundData = soundData;

		this.mainDesktopPageData = this.createMainPage();
		this.selectGeneratorDesktopPageData = this.createSelectGeneratorPage();
		
		this.activeDesktopPageData = this.mainDesktopPageData;
	}

	private DesktopPageData createSelectGeneratorPage()
	{
		DesktopPageData	desktopPageData = new DesktopPageData(this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Page:
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			ButtonData addButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(addButtonData);
		}

		{
			ButtonData addButtonData = new FunctionButtonData("addSinus", "Add Sinus Generator", 100, 100, 250, 20);
			desktopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("addFader", "Add Fader Generator", 100, 130, 250, 20);
			desktopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("addMixer", "Add Mixer Generator", 100, 160, 250, 20);
			desktopPageData.addWidgetData(addButtonData);
		}
		{
			ButtonData addButtonData = new FunctionButtonData("addOutput", "Add Output Generator", 100, 190, 250, 20);
			desktopPageData.addWidgetData(addButtonData);
		}
		return desktopPageData;
	}
	
	private DesktopPageData createMainPage()
	{
		int topMenuSizeY			= 32 * 2;
		int bottomMenuSizeY			= 32 * 4;
		int scrollbarWidth			= 20;
		int scrollbarWidth2			= 15;
		int generatorsLabelSizeX	= 100;

		DesktopPageData	desktopPageData = new DesktopPageData(this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Top Pane:
			PaneData topMenuPaneData = new PaneData(0, 0, this.getFieldWidth(), topMenuSizeY);
			desktopPageData.addWidgetData(topMenuPaneData);

			{
				ButtonData addButtonData = new FunctionButtonData("add", "Add...", 100, 10, 90, 20);
				desktopPageData.addWidgetData(addButtonData);
			}
			{
				ButtonData addButtonData = new FunctionButtonData("remove", "Remove", 200, 10, 90, 20);
				desktopPageData.addWidgetData(addButtonData);
			}
			{
				ButtonData addButtonData = new FunctionButtonData("exit", "Exit", this.getFieldWidth() - 100, 10, 90, 20);
				desktopPageData.addWidgetData(addButtonData);
			}
			
			ButtonData playButtonData = new FunctionButtonData("play", "Play", 100, 40, 90, 20);
			
			desktopPageData.addWidgetData(playButtonData);

			ButtonData pauseButtonData = new FunctionButtonData("pause", "Pause", 200, 40, 90, 20);
			
			desktopPageData.addWidgetData(pauseButtonData);
			{
				ButtonData stopButtonData = new FunctionButtonData("stop", "Stop", 300, 40, 90, 20);
				desktopPageData.addWidgetData(stopButtonData);
			}
		}
		{
			// Add Bottom Pane
			
			int posY = this.getFieldHeight() - bottomMenuSizeY;
			
			PaneData bottomMenuPaneData = new PaneData(0, posY, this.getFieldWidth(), bottomMenuSizeY);
			desktopPageData.addWidgetData(bottomMenuPaneData);

			{
				LabelData generatorNameLabelData = new LabelData("Name:", 10, posY + 10, 60, 16);
				desktopPageData.addWidgetData(generatorNameLabelData);
			}
			{
				this.generatorNameInputlineData = new InputlineData("name", 70, posY + 10, 150, 16);
				desktopPageData.addWidgetData(this.generatorNameInputlineData);
			}

			{
				LabelData generatorNameLabelData = new LabelData("Start-Time:", 10, posY + 30, 60, 16);
				desktopPageData.addWidgetData(generatorNameLabelData);
			}
			{
				this.generatorStartTimeInputlineData = new InputlineData("startTime", 70, posY + 30, 150, 16);
				desktopPageData.addWidgetData(this.generatorStartTimeInputlineData);
			}
			
			{
				LabelData generatorNameLabelData = new LabelData("End-Time:", 10, posY + 50, 60, 16);
				desktopPageData.addWidgetData(generatorNameLabelData);
			}
			{
				this.generatorEndTimeInputlineData = new InputlineData("endTime", 70, posY + 50, 150, 16);
				desktopPageData.addWidgetData(this.generatorEndTimeInputlineData);
			}
			
			{
				ButtonData stopButtonData = new FunctionButtonData("set", "Set", 170, posY + 70, 50, 18);
				desktopPageData.addWidgetData(stopButtonData);
			}
			
			{
				LabelData inputsLabelData = new LabelData("Inputs:", 250, posY + 10, 30, 16);
				desktopPageData.addWidgetData(inputsLabelData);
			}
			ScrollbarData inputsVScrollbarData;
			{
				inputsVScrollbarData = new ScrollbarData("inputsVScroll", 530 - scrollbarWidth2, posY + 10, 
						scrollbarWidth2, 100, true);
				desktopPageData.addWidgetData(inputsVScrollbarData);
			}
			{
				this.generatorInputsData = new InputsData(280, posY + 10, 250 - scrollbarWidth2, 100, inputsVScrollbarData, null);
				desktopPageData.addWidgetData(this.generatorInputsData);
			}

			{
				LabelData generatorInputNameLabelData = new LabelData("Name:", 590, posY + 10, 50, 16);
				desktopPageData.addWidgetData(generatorInputNameLabelData);
			}
			{
				this.generatorInputNameInputlineData = new InputlineData("inputName", 640, posY + 10, 150, 16);
				desktopPageData.addWidgetData(this.generatorInputNameInputlineData);
			}
			{
				LabelData generatorInputTypeLabelData = new LabelData("Type:", 590, posY + 30, 60, 16);
				desktopPageData.addWidgetData(generatorInputTypeLabelData);
			}
			{
				this.generatorInputTypeInputlineData = new InputlineData("inputType", 640, posY + 30, 150, 16);
				desktopPageData.addWidgetData(this.generatorInputTypeInputlineData);
			}

			{
				ButtonData setInputButtonData = new FunctionButtonData("setInput", "Set", 740, posY + 50, 50, 18);
				desktopPageData.addWidgetData(setInputButtonData);
			}
			{
				ButtonData addInputButtonData = new FunctionButtonData("newInput", "New", 540, posY + 40, 60, 18);
				desktopPageData.addWidgetData(addInputButtonData);
			}
			{
				ButtonData addInputButtonData = new FunctionButtonData("addInput", "Add", 540, posY + 65, 60, 18);
				desktopPageData.addWidgetData(addInputButtonData);
			}
			{
				ButtonData removeInputButtonData = new FunctionButtonData("removeInput", "Remove", 540, posY + 90, 60, 18);
				desktopPageData.addWidgetData(removeInputButtonData);
			}
		}
		
		{
			// Add Genrators and Scrollbars:
			int generatorsSizeY = this.getFieldHeight() - (topMenuSizeY + scrollbarWidth + bottomMenuSizeY);
			
			this.generatorsVScrollbarData = 
				new ScrollbarData("generatorsVScroll", this.getFieldWidth() - scrollbarWidth, topMenuSizeY, 
						scrollbarWidth, generatorsSizeY, true);
			
			this.generatorsHScrollbarData = 
				new ScrollbarData("generatorsHScroll", 
						generatorsLabelSizeX, topMenuSizeY + generatorsSizeY, 
						this.getFieldWidth() - (generatorsLabelSizeX + scrollbarWidth), scrollbarWidth, false);
			
			this.generatorsHScrollbarData.setScrollStep(0.5F);
			
			this.tracksData = 
				new TracksData(0, topMenuSizeY, 
						this.getFieldWidth() - scrollbarWidth, generatorsSizeY, 
						generatorsLabelSizeX,
						this.soundData,
						this.generatorsVScrollbarData, this.generatorsHScrollbarData);
			
			desktopPageData.addWidgetData(this.tracksData);
			
			desktopPageData.addWidgetData(this.generatorsVScrollbarData);
			
			desktopPageData.addWidgetData(this.generatorsHScrollbarData);
		}
		return desktopPageData;
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
	 * @return the attribute {@link #activeDesktopPageData}.
	 */
	public DesktopPageData getActiveDesktopPageData()
	{
		return this.activeDesktopPageData;
	}
	/**
	 * @return the attribute {@link #generatorsVScrollbarData}.
	 */
	public ScrollbarData getGeneratorsScrollbarData()
	{
		return this.generatorsVScrollbarData;
	}
	/**
	 * @return the attribute {@link #tracksData}.
	 */
	public TracksData getTracksData()
	{
		return this.tracksData;
	}
	/**
	 * @return the attribute {@link #mainDesktopPageData}.
	 */
	public DesktopPageData getMainDesktopPageData()
	{
		return this.mainDesktopPageData;
	}
	/**
	 * @return the attribute {@link #selectGeneratorDesktopPageData}.
	 */
	public DesktopPageData getSelectGeneratorDesktopPageData()
	{
		return this.selectGeneratorDesktopPageData;
	}
	/**
	 * @param activeDesktopPageData is the new value for attribute {@link #activeDesktopPageData} to set.
	 */
	public void setActiveDesktopPageData(DesktopPageData activeDesktopPageData)
	{
		this.activeDesktopPageData = activeDesktopPageData;
	}
	/**
	 * @return the attribute {@link #generatorNameInputlineData}.
	 */
	public InputlineData getGeneratorNameInputlineData()
	{
		return this.generatorNameInputlineData;
	}

	/**
	 * @return the attribute {@link #generatorStartTimeInputlineData}.
	 */
	public InputlineData getGeneratorStartTimeInputlineData()
	{
		return this.generatorStartTimeInputlineData;
	}

	/**
	 * @return the attribute {@link #generatorEndTimeInputlineData}.
	 */
	public InputlineData getGeneratorEndTimeInputlineData()
	{
		return this.generatorEndTimeInputlineData;
	}
	/**
	 * @return the attribute {@link #generatorInputsData}.
	 */
	public InputsData getGeneratorInputsData()
	{
		return this.generatorInputsData;
	}
	/**
	 * @return the attribute {@link #generatorInputNameInputlineData}.
	 */
	public InputlineData getGeneratorInputNameInputlineData()
	{
		return this.generatorInputNameInputlineData;
	}
	/**
	 * @return the attribute {@link #generatorInputTypeeInputlineData}.
	 */
	public InputlineData getGeneratorInputTypeInputlineData()
	{
		return this.generatorInputTypeInputlineData;
	}
}

