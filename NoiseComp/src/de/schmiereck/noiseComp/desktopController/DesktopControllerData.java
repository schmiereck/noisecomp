package de.schmiereck.noiseComp.desktopController;

import java.util.Iterator;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.actions.AddGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.GroupGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PauseButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PlayButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.EditModulPageData;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.MainPageData;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.SelectGeneratorPageData;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.screenTools.controller.ControllerData;

/**
 * <p>
 * 	Manages the data of the desctop controller {@link de.schmiereck.noiseComp.desktopController.DesktopControllerLogic}.
 * </p>
 *
 *	TODO seperate the other pages in packages (like editModulPage, selectGeneratorPage, ...), smk
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
	 * The supported generator types.<br/>
	 * There are many references to this Object on other places.
	 * Don't kill this object, use clear() to remove the list of types.
	 * 
	 * @see #createBaseGeneratorTypes()
	 */
	private GeneratorTypesData generatorTypesData  = new GeneratorTypesData();
	
	/**
	 * Daten der gerade angezeigte Seite.
	 */
	private DesktopPageData activeDesktopPageData = null; 
	
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
	private MainPageData mainDesktopPageData				= null;
	/**
	 * Dialog to select a new sound generator.
	 */
	private SelectGeneratorPageData selectGeneratorPageData	= null;
	/**
	 * Dialog to edit a Generator Group == a Module.
	 */
	private EditModulPageData editModulPageData = null;
	
	
	
	
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
	 * The main desktop data, shared for all pages.
	 */
	private DesktopData	desktopData;

	/**
	 * List of the main generators in the project.
	 */
	private Generators	mainGenerators = null;

	private EditData editData = new EditData();
	
	/**
	 * Constructor.
	 * 
	 * @param soundData
	 */
	public DesktopControllerData(SoundData soundData, Generators mainGenerators)
	{
		this.soundData = soundData;
		this.mainGenerators = mainGenerators;
		
		this.createBaseGeneratorTypes();
		
		this.desktopData = new DesktopData();
		
		this.mainDesktopPageData = this.createMainPage(desktopData);
		this.selectGeneratorPageData = this.createSelectGeneratorPage(desktopData);
		this.editModulPageData = this.createEditModulPage(desktopData);
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
		this.generatorTypesData.addGeneratorTypeData(RectangleGenerator.createGeneratorTypeData());
		this.generatorTypesData.addGeneratorTypeData(CutGenerator.createGeneratorTypeData());
	}

	private MainPageData createMainPage(DesktopData desktopData)
	{
		/*
		int topMenuSizeY			= 32 * 2;
		int bottomMenuSizeY			= 32 * 4;
		int generatorsLabelSizeX	= 100;

		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Top Pane:
			PaneData topMenuPaneData = new PaneData(0, 0, this.getFieldWidth(), topMenuSizeY);
			desktopPageData.addWidgetData(topMenuPaneData);

			{
				this.modulGeneratorTextWidgetData = new TextWidgetData("---", 100, 0, 400, 16);
				desktopPageData.addWidgetData(this.modulGeneratorTextWidgetData);
			}
			{
				this.addGeneratorButtonData = new FunctionButtonData("add", "Select...", 100, 16, 90, 20);
				desktopPageData.addWidgetData(this.addGeneratorButtonData);
			}
			{
				//this.removeGeneratorbuttonData = new FunctionButtonData("remove", "Remove", 200, 16, 90, 20);
				//desktopPageData.addWidgetData(this.removeGeneratorbuttonData);
			}
			{
				this.groupGeneratorbuttonData = new FunctionButtonData("group", "Modul Edit...", 300, 16, 90, 20);
				desktopPageData.addWidgetData(this.groupGeneratorbuttonData);
			}
			
			{
				this.newButtonData = new FunctionButtonData("new", "New", this.getFieldWidth() - 300, 16, 90, 20);
				desktopPageData.addWidgetData(this.newButtonData);
			}
			{
				this.loadButtonData = new FunctionButtonData("load", "Load...", this.getFieldWidth() - 200, 16, 90, 20);
				desktopPageData.addWidgetData(this.loadButtonData);
			}
			{
				this.saveButtonData = new FunctionButtonData("save", "Save...", this.getFieldWidth() - 200, 40, 90, 20);
				desktopPageData.addWidgetData(this.saveButtonData);
			}
			
			{
				this.exitButtonData = new FunctionButtonData("exit", "Exit", this.getFieldWidth() - 100, 16, 90, 20);
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

			{
				this.playButtonData = new FunctionButtonData("play", "Play", 100, 40, 90, 20);
				desktopPageData.addWidgetData(this.playButtonData);
			}
			{
				this.pauseButtonData = new FunctionButtonData("pause", "Pause", 200, 40, 90, 20);
				desktopPageData.addWidgetData(this.pauseButtonData);
			}
			{
				this.stopButtonData = new FunctionButtonData("stop", "Stop", 300, 40, 90, 20);
				desktopPageData.addWidgetData(this.stopButtonData);
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
					this.removeGeneratorbuttonData = new FunctionButtonData("remove", "Remove", 70, posY + 70, 90, 18);
					desktopPageData.addWidgetData(this.removeGeneratorbuttonData);
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
				inputsVScrollbarData = new ScrollbarData("inputsVScroll", 530 - desktopData.getScrollbarWidth2(), posY + 10, 
						desktopData.getScrollbarWidth2(), 100, true);
				desktopPageData.addWidgetData(inputsVScrollbarData);
			}
			{
				this.generatorInputsData = new InputsWidgetData(280, posY + 10, 250 - desktopData.getScrollbarWidth2(), 100, inputsVScrollbarData, null, this.generatorTypesData);
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
				LabelData labelData = new LabelData("Modul-Input:", 590, posY + 70, 60, 16);
				desktopPageData.addWidgetData(labelData);
			}
			{
				this.generatorInputModulInputSelectData = new SelectData("inputModulInput", 660, posY + 70, 130, 16);
				desktopPageData.addWidgetData(this.generatorInputModulInputSelectData);
			}
			
			{
				this.setInputButtonData = new FunctionButtonData("setInput", "Update", 625, posY + 90, 70, 18);
				desktopPageData.addWidgetData(this.setInputButtonData);

				this.generatorInputNameSelectData.setDefaultSubmitWidgetInterface(this.setInputButtonData);
				this.generatorInputTypeSelectData.setDefaultSubmitWidgetInterface(this.setInputButtonData);
				this.generatorInputValueInputlineData.setDefaultSubmitWidgetInterface(this.setInputButtonData);
			}
			{
				this.addInputButtonData = new FunctionButtonData("addInput", "Add as new", 700, posY + 90, 90, 18);
				desktopPageData.addWidgetData(this.addInputButtonData);
			}
			
			{
				this.newInputButtonData = new FunctionButtonData("newInput", "New", 540, posY + 40, 60, 18);
				desktopPageData.addWidgetData(this.newInputButtonData);
			}
			{
				this.removeInputButtonData = new FunctionButtonData("removeInput", "Remove", 540, posY + 90, 60, 18);
				desktopPageData.addWidgetData(this.removeInputButtonData);
			}
			{
				this.generatorInputTypeDescriptionTextWidgetData = new TextWidgetData("---", 150, posY + 110, 400, 16);
				desktopPageData.addWidgetData(this.generatorInputTypeDescriptionTextWidgetData);
			}
		}
		
		{
			// Add Generators and Scrollbars:
			int generatorsSizeY = this.getFieldHeight() - (topMenuSizeY + desktopData.getScrollbarWidth() + bottomMenuSizeY);
			
			this.generatorsVScrollbarData = 
				new ScrollbarData("generatorsVScroll", this.getFieldWidth() - desktopData.getScrollbarWidth(), topMenuSizeY, 
						desktopData.getScrollbarWidth(), generatorsSizeY, true);
			
			this.generatorsHScrollbarData = 
				new ScrollbarData("generatorsHScroll", 
						generatorsLabelSizeX, topMenuSizeY + generatorsSizeY, 
						this.getFieldWidth() - (generatorsLabelSizeX + desktopData.getScrollbarWidth()), desktopData.getScrollbarWidth(), false);
			
			this.generatorsHScrollbarData.setScrollStep(0.5F);
			
			this.tracksListWidgetData = 
				new TracksListWidgetData(0, topMenuSizeY, 
						this.getFieldWidth() - desktopData.getScrollbarWidth(), generatorsSizeY, 
						generatorsLabelSizeX,
						this.soundData,
						this.generatorsVScrollbarData, this.generatorsHScrollbarData);
			
			desktopPageData.addWidgetData(this.tracksListWidgetData);
			
			desktopPageData.addWidgetData(this.generatorsVScrollbarData);
			
			desktopPageData.addWidgetData(this.generatorsHScrollbarData);
		}
		return desktopPageData;
		*/
		return new MainPageData(desktopData, this, this.getFieldWidth(), this.getFieldHeight());
	}

	private SelectGeneratorPageData createSelectGeneratorPage(DesktopData desktopData)
	{
		/*
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
		{
			LabelData labelData = new LabelData("Generator-Types:", 100, 100, 100, 20);
			desktopPageData.addWidgetData(labelData);
		}
		ScrollbarData verticalScrollbarData;
		{
			verticalScrollbarData = new ScrollbarData("generatorTypesVScroll", 780 - desktopData.getScrollbarWidth()2, 120, 
					desktopData.getScrollbarWidth()2, 400, true);
			desktopPageData.addWidgetData(verticalScrollbarData);
		}
		{
			this.generatorTypesListData = new GeneratorTypesWidgetData(100, 120, 680 - desktopData.getScrollbarWidth()2, 400, verticalScrollbarData, null, this.generatorTypesData);
			desktopPageData.addWidgetData(this.generatorTypesListData);
		}
		{
			this.selectAddButtonData = new FunctionButtonData("selectAdd", "Add selected", 100, 70, 100, 20);
			desktopPageData.addWidgetData(this.selectAddButtonData);
		}
		{
			this.selectMainEditButtonData = new FunctionButtonData("selectMainEdit", "Edit Main Modul", 210, 40, 220, 20);
			desktopPageData.addWidgetData(this.selectMainEditButtonData);
		}
		{
			this.selectEditButtonData = new FunctionButtonData("selectEdit", "Edit Modul", 210, 70, 100, 20);
			desktopPageData.addWidgetData(this.selectEditButtonData);
		}
		{
			this.selectRemoveButtonData = new FunctionButtonData("selectRemove", "Remove Modul", 320, 70, 110, 20);
			desktopPageData.addWidgetData(this.selectRemoveButtonData);
		}
		
		return desktopPageData;
		*/
		return new SelectGeneratorPageData(desktopData, this.getFieldWidth(), this.getFieldHeight(),
				this.generatorTypesData);
	}

	private EditModulPageData createEditModulPage(DesktopData desktopData)
	{
		/*
		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Panel:
			PaneData paneData = new PaneData(0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
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
			this.saveGroupButtonData = new FunctionButtonData("groupSave", "Save Group", 70, 560, 250, 20);
			desktopPageData.addWidgetData(this.saveGroupButtonData);
		}
		{
			this.cancelGroupButtonData = new FunctionButtonData("cancel", "Cancel", 500, 560, 90, 20);
			desktopPageData.addWidgetData(this.cancelGroupButtonData);
		}
		
		return desktopPageData;
		*/
		return new EditModulPageData(desktopData, this, this.getFieldWidth(), this.getFieldHeight());
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

		this.saveFileNameInputlineData.setDefaultSubmitWidgetInterface(this.saveFileButtonData);
		
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

		this.loadFileNameInputlineData.setDefaultSubmitWidgetInterface(this.loadFileButtonData);
		
		return desktopPageData;
	}
	
	public void setActionListeners(
			AddGeneratorButtonActionLogicListener addGeneratorButtonActionLogicListener,
			RemoveGeneratorButtonActionLogicListener removeGeneratorButtonActionLogicListener,
			GroupGeneratorButtonActionLogicListener groupGeneratorButtonActionLogicListener,

			PlayButtonActionLogicListener playButtonActionLogicListener,
			PauseButtonActionLogicListener pauseButtonActionLogicListener,
			StopButtonActionLogicListener stopButtonActionLogicListener,
			
			ExitButtonActionLogicListener exitButtonActionLogicListener,
			NewButtonActionLogicListener newButtonActionLogicListener,

			ZoomInButtonActionLogicListener zoomInButtonActionLogicListener,
			ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener,
		   
			SetGeneratorButtonActionLogicListener setGeneratorButtonActionLogicListener,
			SetInputButtonActionLogicListener setInputButtonActionLogicListener,
			RemoveInputButtonActionLogicListener removeInputButtonActionLogicListener,
			NewInputButtonActionLogicListener newInputButtonActionLogicListener,
			AddInputButtonActionLogicListener addInputButtonActionLogicListener,
			
			SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener,
			SelectAddButtonActionLogicListener selectAddButtonActionLogicListener,
			SelectEditButtonActionLogicListener selectEditButtonActionLogicListener,
			SelectMainEditButtonActionLogicListener selectMainEditButtonActionLogicListener,
			SelectRemoveButtonActionLogicListener selectRemoveButtonActionLogicListener,
			
			SaveButtonActionLogicListener saveButtonActionLogicListener,
			SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener,
			SaveFileButtonActionLogicListener saveFileButtonActionLogicListener,
		   
			LoadButtonActionLogicListener loadButtonActionLogicListener,
			LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener,
			LoadFileButtonActionLogicListener loadFileButtonActionLogicListener,

			CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener,
			SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener,
			
			InputTypeEditSaveActionListener inputTypeEditSaveActionListener
			)
	{
		this.mainDesktopPageData.setActionListeners(addGeneratorButtonActionLogicListener,
		removeGeneratorButtonActionLogicListener,
		groupGeneratorButtonActionLogicListener,
		
		playButtonActionLogicListener,
		pauseButtonActionLogicListener,
		stopButtonActionLogicListener,
		
		exitButtonActionLogicListener,
		newButtonActionLogicListener,
		
		zoomInButtonActionLogicListener,
		zoomOutButtonActionLogicListener,
		
		setGeneratorButtonActionLogicListener,
		setInputButtonActionLogicListener,
		removeInputButtonActionLogicListener,
		newInputButtonActionLogicListener,
		addInputButtonActionLogicListener,
		
		saveButtonActionLogicListener,
		loadButtonActionLogicListener);
		
		this.selectGeneratorPageData.setActionListeners(selectCancelButtonActionLogicListener,
				selectAddButtonActionLogicListener,
				selectEditButtonActionLogicListener,
				selectMainEditButtonActionLogicListener,
				selectRemoveButtonActionLogicListener);
		
		this.saveCancelButtonData.addActionLogicListener(saveCancelButtonActionLogicListener);
		this.saveFileButtonData.addActionLogicListener(saveFileButtonActionLogicListener);

		this.loadCancelButtonData.addActionLogicListener(loadCancelButtonActionLogicListener);
		this.loadFileButtonData.addActionLogicListener(loadFileButtonActionLogicListener);

		this.editModulPageData.setActionListeners(cancelGroupButtonActionLogicListener, 
				saveGroupButtonActionLogicListener,
				inputTypeEditSaveActionListener);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerData#getObjectsCount()
	 */
	public int getObjectsCount()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.controller.ControllerData#getObjectsIterator()
	 */
	public Iterator getObjectsIterator()
	{
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
	 * @return the attribute {@link #mainDesktopPageData}.
	 */
	public MainPageData getMainDesktopPageData()
	{
		return this.mainDesktopPageData;
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
	 * @return the attribute {@link #editModulPageData}.
	 */
	public EditModulPageData getEditModulPageData()
	{
		return this.editModulPageData;
	}
	/**
	 * @return the attribute {@link #selectGeneratorPageData}.
	 */
	public SelectGeneratorPageData getSelectGeneratorPageData()
	{
		return this.selectGeneratorPageData;
	}
	/**
	 * @return the attribute {@link #generatorTypesData}.
	 */
	public GeneratorTypesData getGeneratorTypesData()
	{
		return this.generatorTypesData;
	}

	/**
	 * @return
	 */
	public Generators getMainGenerators()
	{
		return this.mainGenerators;
	}
	/**
	 * @param mainGenerators is the new value for attribute {@link #mainGenerators} to set.
	 */
	public void setMainGenerators(Generators mainGenerators)
	{
		this.mainGenerators = mainGenerators;
	}

	/**
	 * 
	 */
	public void clearTracks()
	{
		this.mainDesktopPageData.clearTracks();

		Generators generators = new Generators();
		
		///this.soundData.setGenerators(generators);
		
		this.setMainGenerators(generators);
	}
	
	private String popupRuntimeExceptionText = null;
	
	/**
	 * @param ex
	 */
	public void setPopupRuntimeException(PopupRuntimeException ex)
	{
		this.popupRuntimeExceptionText = ex.getMessage();
		
		throw ex;
	}
	/**
	 * @return the attribute {@link #popupRuntimeExceptionText}.
	 */
	public String getPopupRuntimeExceptionText()
	{
		return this.popupRuntimeExceptionText;
	}

	/**
	 * @return
	 */
	public int getTracksCount()
	{
		return this.mainDesktopPageData.getTracksListWidgetData().getTracksCount();
	}
	/**
	 * @return the attribute {@link #editData}.
	 */
	public EditData getEditData()
	{
		return this.editData;
	}
}

