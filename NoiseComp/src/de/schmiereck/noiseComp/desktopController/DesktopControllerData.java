package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.actions.AddGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.GroupGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorTypesWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputsWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
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
	 * The supported generator types.
	 * @see #createBaseGeneratorTypes()
	 */
	private GeneratorTypesData generatorTypesData  = new GeneratorTypesData();
	
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
	
	/**
	 * Dialog to save a sound file.
	 */
	private DesktopPageData saveDesktopPageData;
	/**
	 * Dialog to load a sound file.
	 */
	private DesktopPageData loadDesktopPageData;
	/**
	 * Dialog with the main sound editing controlls.
	 */
	private DesktopPageData mainDesktopPageData				= null;
	/**
	 * Dialog to select a new sound generator.
	 */
	private DesktopPageData selectGeneratorPageData	= null;
	/**
	 * Dialog to edit a Generator Group == a Module.
	 */
	private DesktopPageData groupGeneratorPageData = null;
	
	private InputlineData generatorNameInputlineData = null;
	private InputlineData generatorStartTimeInputlineData = null;
	private InputlineData generatorEndTimeInputlineData = null;
	private InputsWidgetData generatorInputsData = null;
	private SelectData generatorInputNameSelectData = null;
	private SelectData generatorInputTypeSelectData = null;
	private InputlineData generatorInputValueInputlineData = null;
	
	private FunctionButtonData newButtonData	= null;
	private FunctionButtonData exitButtonData	= null;
	private FunctionButtonData loadButtonData	= null;
	private FunctionButtonData saveButtonData	= null;

	private FunctionButtonData zoomInButtonData	= null;
	private FunctionButtonData zoomOutButtonData	= null;
	

	/**
	 * Dialog:	Main:	Add-Generator-Button.
	 */
	private FunctionButtonData	addGeneratorButtonData	= 	null;

	/**
	 * Dialog:	Main:	Remove-Generator-Button.
	 */
	private FunctionButtonData	removeGeneratorbuttonData	= 	null;

	/**
	 * Dialog:	Main:	Group-Generators-Button.
	 */
	private FunctionButtonData	groupGeneratorbuttonData	= 	null;

	/**
	 * Dialog:	Main:	Set-Generator-Button.
	 */
	private FunctionButtonData setGeneratorButtonData	= null;

	/**
	 * Dialog:	Main:	Set-Generator-Input-Button.
	 */
	private FunctionButtonData setInputButtonData	= null;
	
	/**
	 * Dialog: Save: 
	 */
	private InputlineData saveFileNameInputlineData = null;
	/**
	 * Dialog: Save: 
	 */
	private FunctionButtonData saveFileButtonData = null;

	/**
	 * Dialog: Load File: fileName-Inputline
	 */
	private InputlineData loadFileNameInputlineData = null;
	/**
	 * Dialog: Load File: Load-Button
	 */
	private FunctionButtonData loadFileButtonData = null;
	/**
	 * Dialog: Load File: Cancel-Button
	 */
	private FunctionButtonData loadCancelButtonData = null;

	/**
	 * Dialog: Save File: Cancel-Button
	 */
	private FunctionButtonData saveCancelButtonData = null;
	
	/**
	 * Dialog: Select Generator: Cancel-Button
	 */
	private FunctionButtonData selectCancelButtonData = null;
	
	/**
	 * Dialog: Select Generator: Add-Button
	 */
	private FunctionButtonData selectAddButtonData = null;
	
	/**
	 * Dialog: Select Generator: GeneratorTypes-List
	 */
	private GeneratorTypesWidgetData	generatorTypesListData;

	/**
	 * Dialog: Group: Cancel-Button
	 */
	private FunctionButtonData	cancelGroupButtonData;

	/**
	 * Dialog: Group: Name-Inputline
	 */
	private InputlineData	groupNameInputlineData;

	/**
	 * Dialog: Group: Save-Button
	 */
	private FunctionButtonData	saveGroupButtonData;
	
	/**
	 * The main desktop data, shared for all pages.
	 */
	private DesktopData	desktopData;

	private int scrollbarWidth			= 20;
	private int scrollbarWidth2			= 15;

	/**
	 * Constructor.
	 * 
	 * @param soundData
	 */
	public DesktopControllerData(SoundData soundData)
	{
		this.soundData = soundData;

		this.createBaseGeneratorTypes();
		
		this.desktopData = new DesktopData();
		
		this.mainDesktopPageData = this.createMainPage(desktopData);
		this.selectGeneratorPageData = this.createSelectGeneratorPage(desktopData);
		this.groupGeneratorPageData = this.createGroupGeneratorPage(desktopData);
		this.saveDesktopPageData = this.createSavePage(desktopData);
		this.loadDesktopPageData = this.createLoadPage(desktopData);
		
		this.activeDesktopPageData = this.mainDesktopPageData;
	}

	public void createBaseGeneratorTypes()
	{
		this.generatorTypesData.clear();
		
		this.generatorTypesData.addGeneratorTypeData(FaderGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(MixerGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(OutputGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(SinusGenerator.createGeneratorTypeData());
	}

	private DesktopPageData createMainPage(DesktopData desktopData)
	{
		int topMenuSizeY			= 32 * 2;
		int bottomMenuSizeY			= 32 * 4;
		int generatorsLabelSizeX	= 100;

		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Top Pane:
			PaneData topMenuPaneData = new PaneData(0, 0, this.getFieldWidth(), topMenuSizeY);
			desktopPageData.addWidgetData(topMenuPaneData);

			{
				this.addGeneratorButtonData = new FunctionButtonData("add", "Add...", 100, 10, 90, 20);
				desktopPageData.addWidgetData(this.addGeneratorButtonData);
			}
			{
				this.removeGeneratorbuttonData = new FunctionButtonData("remove", "Remove", 200, 10, 90, 20);
				desktopPageData.addWidgetData(this.removeGeneratorbuttonData);
			}
			{
				this.groupGeneratorbuttonData = new FunctionButtonData("group", "Group...", 300, 10, 90, 20);
				desktopPageData.addWidgetData(this.groupGeneratorbuttonData);
			}
			
			{
				this.newButtonData = new FunctionButtonData("new", "New", this.getFieldWidth() - 300, 10, 90, 20);
				desktopPageData.addWidgetData(this.newButtonData);
			}
			{
				this.loadButtonData = new FunctionButtonData("load", "Load...", this.getFieldWidth() - 200, 10, 90, 20);
				desktopPageData.addWidgetData(this.loadButtonData);
			}
			{
				this.saveButtonData = new FunctionButtonData("save", "Save...", this.getFieldWidth() - 200, 40, 90, 20);
				desktopPageData.addWidgetData(this.saveButtonData);
			}
			
			{
				this.exitButtonData = new FunctionButtonData("exit", "Exit", this.getFieldWidth() - 100, 10, 90, 20);
				desktopPageData.addWidgetData(this.exitButtonData);
			}
			
			{
				this.zoomInButtonData = new FunctionButtonData("zoomIn", "+", this.getFieldWidth() - 375, 40, 20, 20);
				desktopPageData.addWidgetData(this.zoomInButtonData);
				this.zoomInButtonData.setAcceptFocus(false);
			}
			{
				this.zoomOutButtonData = new FunctionButtonData("zoomOut", "-", this.getFieldWidth() - 350, 40, 20, 20);
				desktopPageData.addWidgetData(this.zoomOutButtonData);
				this.zoomOutButtonData.setAcceptFocus(false);
			}
			
			FunctionButtonData playButtonData = new FunctionButtonData("play", "Play", 100, 40, 90, 20);
			
			desktopPageData.addWidgetData(playButtonData);

			FunctionButtonData pauseButtonData = new FunctionButtonData("pause", "Pause", 200, 40, 90, 20);
			
			desktopPageData.addWidgetData(pauseButtonData);
			{
				FunctionButtonData stopButtonData = new FunctionButtonData("stop", "Stop", 300, 40, 90, 20);
				desktopPageData.addWidgetData(stopButtonData);
			}
		}
		{
			// Add Bottom Pane
			
			int posY = this.getFieldHeight() - bottomMenuSizeY;
			
			PaneData bottomMenuPaneData = new PaneData(0, posY, this.getFieldWidth(), bottomMenuSizeY);
			desktopPageData.addWidgetData(bottomMenuPaneData);

			{
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
					this.setGeneratorButtonData = new FunctionButtonData("set", "Set", 170, posY + 70, 50, 18);
					desktopPageData.addWidgetData(this.setGeneratorButtonData);

					this.generatorNameInputlineData.setDefaultSubmitWidgetInterface(this.setGeneratorButtonData);
					this.generatorStartTimeInputlineData.setDefaultSubmitWidgetInterface(this.setGeneratorButtonData);
					this.generatorEndTimeInputlineData.setDefaultSubmitWidgetInterface(this.setGeneratorButtonData);
				}
			}
			{
				LabelData inputsLabelData = new LabelData("Inputs:", 250, posY + 10, 30, 16);
				desktopPageData.addWidgetData(inputsLabelData);
			}
			ScrollbarData inputsVScrollbarData;
			{
				inputsVScrollbarData = new ScrollbarData("inputsVScroll", 530 - this.scrollbarWidth2, posY + 10, 
						this.scrollbarWidth2, 100, true);
				desktopPageData.addWidgetData(inputsVScrollbarData);
			}
			{
				this.generatorInputsData = new InputsWidgetData(280, posY + 10, 250 - this.scrollbarWidth2, 100, inputsVScrollbarData, null, this.generatorTypesData);
				desktopPageData.addWidgetData(this.generatorInputsData);
			}

			{
				LabelData generatorInputNameLabelData = new LabelData("Name:", 590, posY + 10, 60, 16);
				desktopPageData.addWidgetData(generatorInputNameLabelData);
			}
			{
				this.generatorInputNameSelectData = new SelectData("inputName", 660, posY + 10, 130, 16);
				desktopPageData.addWidgetData(this.generatorInputNameSelectData);
			}
			{
				LabelData generatorInputTypeLabelData = new LabelData("Type:", 590, posY + 30, 60, 16);
				desktopPageData.addWidgetData(generatorInputTypeLabelData);
			}
			{
				this.generatorInputTypeSelectData = new SelectData("inputType", 660, posY + 30, 130, 16);
				desktopPageData.addWidgetData(this.generatorInputTypeSelectData);
			}
			{
				LabelData generatorInputTypeLabelData = new LabelData("Value:", 590, posY + 50, 60, 16);
				desktopPageData.addWidgetData(generatorInputTypeLabelData);
			}
			{
				this.generatorInputValueInputlineData = new InputlineData("inputValue", 660, posY + 50, 130, 16);
				desktopPageData.addWidgetData(this.generatorInputValueInputlineData);
			}
			
			{
				this.setInputButtonData = new FunctionButtonData("setInput", "Update", 720, posY + 70, 70, 18);
				desktopPageData.addWidgetData(this.setInputButtonData);

				this.generatorInputNameSelectData.setDefaultSubmitWidgetInterface(setInputButtonData);
				this.generatorInputTypeSelectData.setDefaultSubmitWidgetInterface(setInputButtonData);
				this.generatorInputValueInputlineData.setDefaultSubmitWidgetInterface(setInputButtonData);
			}
			{
				FunctionButtonData addInputButtonData = new FunctionButtonData("addInput", "Add as new", 700, posY + 90, 90, 18);
				desktopPageData.addWidgetData(addInputButtonData);
			}
			
			{
				FunctionButtonData newInputButtonData = new FunctionButtonData("newInput", "New", 540, posY + 40, 60, 18);
				desktopPageData.addWidgetData(newInputButtonData);
			}
			{
				FunctionButtonData removeInputButtonData = new FunctionButtonData("removeInput", "Remove", 540, posY + 90, 60, 18);
				desktopPageData.addWidgetData(removeInputButtonData);
			}
		}
		
		{
			// Add Genrators and Scrollbars:
			int generatorsSizeY = this.getFieldHeight() - (topMenuSizeY + this.scrollbarWidth + bottomMenuSizeY);
			
			this.generatorsVScrollbarData = 
				new ScrollbarData("generatorsVScroll", this.getFieldWidth() - this.scrollbarWidth, topMenuSizeY, 
						this.scrollbarWidth, generatorsSizeY, true);
			
			this.generatorsHScrollbarData = 
				new ScrollbarData("generatorsHScroll", 
						generatorsLabelSizeX, topMenuSizeY + generatorsSizeY, 
						this.getFieldWidth() - (generatorsLabelSizeX + this.scrollbarWidth), this.scrollbarWidth, false);
			
			this.generatorsHScrollbarData.setScrollStep(0.5F);
			
			this.tracksData = 
				new TracksData(0, topMenuSizeY, 
						this.getFieldWidth() - this.scrollbarWidth, generatorsSizeY, 
						generatorsLabelSizeX,
						this.soundData,
						this.generatorsVScrollbarData, this.generatorsHScrollbarData);
			
			desktopPageData.addWidgetData(this.tracksData);
			
			desktopPageData.addWidgetData(this.generatorsVScrollbarData);
			
			desktopPageData.addWidgetData(this.generatorsHScrollbarData);
		}
		return desktopPageData;
	}

	private DesktopPageData createSelectGeneratorPage(DesktopData desktopData)
	{
		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Page:
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			this.selectCancelButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(this.selectCancelButtonData);
		}
/*
		{
			FunctionButtonData buttonData = new FunctionButtonData("addSinus", "Add Sinus Generator", 100, 100, 250, 20);
			desktopPageData.addWidgetData(buttonData);
		}
		{
			FunctionButtonData buttonData = new FunctionButtonData("addFader", "Add Fader Generator", 100, 130, 250, 20);
			desktopPageData.addWidgetData(buttonData);
		}
		{
			FunctionButtonData buttonData = new FunctionButtonData("addMixer", "Add Mixer Generator", 100, 160, 250, 20);
			desktopPageData.addWidgetData(buttonData);
		}
		{
			FunctionButtonData buttonData = new FunctionButtonData("addOutput", "Add Output Generator", 100, 190, 250, 20);
			desktopPageData.addWidgetData(buttonData);
		}
*/
		{
			LabelData labelData = new LabelData("Generator-Types:", 100, 100, 100, 20);
			desktopPageData.addWidgetData(labelData);
		}
		ScrollbarData verticalScrollbarData;
		{
			verticalScrollbarData = new ScrollbarData("generatorTypesVScroll", 800 - this.scrollbarWidth2, 120, 
					this.scrollbarWidth2, 400, true);
			desktopPageData.addWidgetData(verticalScrollbarData);
		}
		{
			this.generatorTypesListData = new GeneratorTypesWidgetData(100, 120, 700 - this.scrollbarWidth2, 400, verticalScrollbarData, null, this.generatorTypesData);
			desktopPageData.addWidgetData(this.generatorTypesListData);
		}
		{
			this.selectAddButtonData = new FunctionButtonData("selectAdd", "Add selected", 100, 70, 100, 20);
			desktopPageData.addWidgetData(this.selectAddButtonData);
		}
		
		return desktopPageData;
	}

	private DesktopPageData createGroupGeneratorPage(DesktopData desktopData)
	{
		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Panel:
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			this.cancelGroupButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(this.cancelGroupButtonData);
		}

		{
			LabelData nameLabelData = new LabelData("Name:", 10, 100, 60, 16);
			desktopPageData.addWidgetData(nameLabelData);
		}
		{
			this.groupNameInputlineData = new InputlineData("groupName", 70, 100, 150, 16);
			desktopPageData.addWidgetData(this.groupNameInputlineData);
		}
		
		{
			this.saveGroupButtonData = new FunctionButtonData("groupSave", "Save Group", 70, 160, 250, 20);
			desktopPageData.addWidgetData(this.saveGroupButtonData);
		}
		
		return desktopPageData;
	}

	private DesktopPageData createSavePage(DesktopData desktopData)
	{
		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Page:
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			this.saveCancelButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(this.saveCancelButtonData);
		}

		{
			LabelData nameLabelData = new LabelData("Name:", 10, 100, 60, 16);
			desktopPageData.addWidgetData(nameLabelData);
		}
		{
			this.saveFileNameInputlineData = new InputlineData("saveFileName", 70, 100, 150, 16);
			desktopPageData.addWidgetData(this.saveFileNameInputlineData);
		}
		
		{
			this.saveFileButtonData = new FunctionButtonData("saveFile", "Save", 100, 160, 250, 20);
			desktopPageData.addWidgetData(this.saveFileButtonData);
		}
		return desktopPageData;
	}
	
	private DesktopPageData createLoadPage(DesktopData desktopData)
	{
		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Page:
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			this.loadCancelButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(this.loadCancelButtonData);
		}

		{
			LabelData nameLabelData = new LabelData("Name:", 10, 100, 60, 16);
			desktopPageData.addWidgetData(nameLabelData);
		}
		{
			this.loadFileNameInputlineData = new InputlineData("loadFileName", 70, 100, 150, 16);
			desktopPageData.addWidgetData(this.loadFileNameInputlineData);
		}
		
		{
			this.loadFileButtonData = new FunctionButtonData("loadFile", "Load", 100, 160, 250, 20);
			desktopPageData.addWidgetData(this.loadFileButtonData);
		}
		return desktopPageData;
	}
	
	public void setActionListeners(
			AddGeneratorButtonActionLogicListener addGeneratorButtonActionLogicListener,
			RemoveGeneratorButtonActionLogicListener removeGeneratorButtonActionLogicListener,
			GroupGeneratorButtonActionLogicListener groupGeneratorButtonActionLogicListener,
			
			ExitButtonActionLogicListener exitButtonActionLogicListener,
			NewButtonActionLogicListener newButtonActionLogicListener,

			ZoomInButtonActionLogicListener zoomInButtonActionLogicListener,
			ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener,
		   
			SetGeneratorButtonActionLogicListener setGeneratorButtonActionLogicListener,
			SetInputButtonActionLogicListener setInputButtonActionLogicListener,
		   
			SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener,
			SelectAddButtonActionLogicListener selectAddButtonActionLogicListener,
			
			SaveButtonActionLogicListener saveButtonActionLogicListener,
			SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener,
			SaveFileButtonActionLogicListener saveFileButtonActionLogicListener,
		   
			LoadButtonActionLogicListener loadButtonActionLogicListener,
			LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener,
			LoadFileButtonActionLogicListener loadFileButtonActionLogicListener,

			CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener,
			SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener
			)
	{
		this.addGeneratorButtonData.addActionLogicListener(addGeneratorButtonActionLogicListener);
		this.removeGeneratorbuttonData.addActionLogicListener(removeGeneratorButtonActionLogicListener);
		this.groupGeneratorbuttonData.addActionLogicListener(groupGeneratorButtonActionLogicListener);
		
		this.exitButtonData.addActionLogicListener(exitButtonActionLogicListener);
		this.newButtonData.addActionLogicListener(newButtonActionLogicListener);
		
		this.zoomInButtonData.addActionLogicListener(zoomInButtonActionLogicListener);
		this.zoomOutButtonData.addActionLogicListener(zoomOutButtonActionLogicListener);
		
		this.setGeneratorButtonData.addActionLogicListener(setGeneratorButtonActionLogicListener);
		this.setInputButtonData.addActionLogicListener(setInputButtonActionLogicListener);
		
		this.selectCancelButtonData.addActionLogicListener(selectCancelButtonActionLogicListener);
		this.selectAddButtonData.addActionLogicListener(selectAddButtonActionLogicListener);
		
		this.saveButtonData.addActionLogicListener(saveButtonActionLogicListener);
		this.saveCancelButtonData.addActionLogicListener(saveCancelButtonActionLogicListener);
		this.saveFileButtonData.addActionLogicListener(saveFileButtonActionLogicListener);

		this.loadButtonData.addActionLogicListener(loadButtonActionLogicListener);
		this.loadCancelButtonData.addActionLogicListener(loadCancelButtonActionLogicListener);
		this.loadFileButtonData.addActionLogicListener(loadFileButtonActionLogicListener);

		this.cancelGroupButtonData.addActionLogicListener(cancelGroupButtonActionLogicListener);
		this.saveGroupButtonData.addActionLogicListener(saveGroupButtonActionLogicListener);
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
	 * @return the attribute {@link #generatorsHScrollbarData}.
	 */
	public ScrollbarData getTimelineScrollbarData()
	{
		return this.generatorsHScrollbarData;
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
	 * @return the attribute {@link #selectGeneratorPageData}.
	 */
	public DesktopPageData getSelectGeneratorPageData()
	{
		return this.selectGeneratorPageData;
	}

	/**
	 * @see #saveDesktopPageData
	 */
	public DesktopPageData getSaveDesktopPageData()
	{
		return this.saveDesktopPageData;
	}

	/**
	 * @see #loadDesktopPageData
	 */
	public DesktopPageData getLoadDesktopPageData()
	{
		return this.loadDesktopPageData;
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
	public InputsWidgetData getGeneratorInputsData()
	{
		return this.generatorInputsData;
	}
	/**
	 * @return the attribute {@link #generatorInputNameSelectData}.
	 */
	public SelectData getGeneratorInputNameSelectData()
	{
		return this.generatorInputNameSelectData;
	}
	/**
	 * @return the attribute {@link #generatorInputTypeSelectData}.
	 */
	public SelectData getGeneratorInputTypeSelectData()
	{
		return this.generatorInputTypeSelectData;
	}
	/**
	 * @return the attribute {@link #generatorInputValueInputlineData}.
	 */
	public InputlineData getGeneratorInputValueInputlineData()
	{
		return this.generatorInputValueInputlineData;
	}
	/**
	 * @return the attribute {@link #desktopData}.
	 */
	public DesktopData getDesktopData()
	{
		return this.desktopData;
	}
	/**
	 * @return the attribute {@link #saveFileNameInputlineData}.
	 */
	public InputlineData getSaveFileNameInputlineData()
	{
		return this.saveFileNameInputlineData;
	}
	/**
	 * @return the attribute {@link #loadFileNameInputlineData}.
	 */
	public InputlineData getLoadFileNameInputlineData()
	{
		return this.loadFileNameInputlineData;
	}
	
	public GeneratorTypeData searchGeneratorTypeData(String generatorTypeClassName)
	{
		return this.generatorTypesData.searchGeneratorTypeData(generatorTypeClassName);
	}
	/**
	 * @return the attribute {@link #groupGeneratorPageData}.
	 */
	public DesktopPageData getGroupGeneratorPageData()
	{
		return this.groupGeneratorPageData;
	}
	/**
	 * @return the attribute {@link #generatorTypesListData}.
	 */
	public GeneratorTypesWidgetData getGeneratorTypesListData()
	{
		return this.generatorTypesListData;
	}
	/**
	 * @return the attribute {@link #groupNameInputlineData}.
	 */
	public InputlineData getGroupNameInputlineData()
	{
		return this.groupNameInputlineData;
	}
	/**
	 * @return the attribute {@link #generatorTypesData}.
	 */
	public GeneratorTypesData getGeneratorTypesData()
	{
		return this.generatorTypesData;
	}
}

