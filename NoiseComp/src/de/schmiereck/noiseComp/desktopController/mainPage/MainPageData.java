package de.schmiereck.noiseComp.desktopController.mainPage;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.actions.AddGeneratorButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.GroupGeneratorButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PauseButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PlayButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputsWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.desktopPage.widgets.SelectData;
import de.schmiereck.noiseComp.desktopPage.widgets.TextWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksListWidgetData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>12.04.2004: created, smk</p>
 */
public class MainPageData
	extends DesktopPageData
{
	/**
	 * Dialog:	Main:	Edited Modul-Generator-Name.
	 */
	private TextWidgetData	modulGeneratorTextWidgetData = null;
	
	/**
	 * Dialog:	Main:	Add-Generator-Button.
	 */
	private FunctionButtonData	addGeneratorButtonData	= 	null;

	/**
	 * Dialog:	Main:	Remove-Generator-Button.
	 */
	private FunctionButtonData	removeGeneratorbuttonData	= 	null;
	
	/**
	 * Dialog:	Main:	Play-Button.
	 */
	private FunctionButtonData playButtonData	= null;
	/**
	 * Dialog:	Main:	Pause-Button.
	 */
	private FunctionButtonData pauseButtonData	= null;
	/**
	 * Dialog:	Main:	Stop-Button.
	 */
	private FunctionButtonData stopButtonData	= null;
	
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
	 * Dialog:	Main:	Remove-Generator-Input-Button.
	 */
	private FunctionButtonData removeInputButtonData	= null;
	/**
	 * Dialog:	Main:	New-Generator-Input-Button.
	 */
	private FunctionButtonData newInputButtonData	= null;
	/**
	 * Dialog:	Main:	Add-Generator-Input-Button.
	 */
	private FunctionButtonData addInputButtonData	= null;
	/**
	 * Dialog:	Main:	Generator-Input-Type-Description.
	 */
	private TextWidgetData generatorInputTypeDescriptionTextWidgetData = null;

	//private FunctionButtonData newButtonData	= null;
	//private FunctionButtonData exitButtonData	= null;
	//private FunctionButtonData loadButtonData	= null;
	//private FunctionButtonData saveButtonData	= null;

	private FunctionButtonData zoomInButtonData	= null;
	private FunctionButtonData zoomOutButtonData	= null;

	private InputlineData generatorNameInputlineData = null;
	private InputlineData generatorStartTimeInputlineData = null;
	private InputlineData generatorEndTimeInputlineData = null;
	private ScrollbarData inputsVScrollbarData = null;
	private InputsWidgetData generatorInputsData = null;
	private SelectData generatorInputNameSelectData = null;
	private SelectData generatorInputTypeSelectData = null;
	private InputlineData generatorInputValueInputlineData = null;
	private SelectData	generatorInputModulInputSelectData = null;

	/**
	 * Vertikale Scrollbar des Generator-Widgets.
	 */
	private ScrollbarData generatorsVScrollbarData;
	
	/**
	 * Horizontale Scrollbar des Generator-Widgets.
	 */
	private ScrollbarData generatorsHScrollbarData;
	
	private TracksListWidgetData tracksListWidgetData;
	
	private DesktopControllerData desktopControllerData;
	
	/**
	 * Constructor.
	 * 
	 * @param desktopData
	 * @param desktopSizeX
	 * @param desktopSizeY
	 */
	public MainPageData(DesktopData desktopData, DesktopControllerData desktopControllerData, int desktopSizeX, int desktopSizeY)
	{
		super(desktopData, desktopSizeX, desktopSizeY);
		
		this.desktopControllerData = desktopControllerData;
		
		int topMenuSizeY			= 32 * 2;
		int bottomMenuSizeY			= 32 * 4;
		int generatorsLabelSizeX	= 100;

		//DesktopPageData	this = new DesktopPageData(desktopData, this.getDesktopSizeX(), this.getDesktopSizeY());

		{
			// Add Top Pane:
			PaneData topMenuPaneData = new PaneData(0, 0, this.getDesktopSizeX(), topMenuSizeY);
			this.addWidgetData(topMenuPaneData);

			{
				this.modulGeneratorTextWidgetData = new TextWidgetData("---", 100, 0, 400, 16);
				this.addWidgetData(this.modulGeneratorTextWidgetData);
			}
			{
				this.addGeneratorButtonData = new FunctionButtonData("add", "Select...", 100, 16, 90, 20);
				this.addWidgetData(this.addGeneratorButtonData);
			}
			{
				//this.removeGeneratorbuttonData = new FunctionButtonData("remove", "Remove", 200, 16, 90, 20);
				//this.addWidgetData(this.removeGeneratorbuttonData);
			}
			{
				this.groupGeneratorbuttonData = new FunctionButtonData("group", "Modul Edit...", 300, 16, 90, 20);
				this.addWidgetData(this.groupGeneratorbuttonData);
			}
			
			{
				//this.newButtonData = new FunctionButtonData("new", "New", this.getDesktopSizeX() - 300, 16, 90, 20);
				//this.addWidgetData(this.newButtonData);
			}
			{
				//this.loadButtonData = new FunctionButtonData("load", "Load...", this.getDesktopSizeX() - 200, 16, 90, 20);
				//this.addWidgetData(this.loadButtonData);
			}
			{
				//this.saveButtonData = new FunctionButtonData("save", "Save...", this.getDesktopSizeX() - 200, 40, 90, 20);
				//this.addWidgetData(this.saveButtonData);
			}
			
			{
				//this.exitButtonData = new FunctionButtonData("exit", "Exit", this.getDesktopSizeX() - 100, 16, 90, 20);
				//this.addWidgetData(this.exitButtonData);
			}
			
			{
				this.zoomInButtonData = new FunctionButtonData("zoomIn", "+", this.getDesktopSizeX() - 375, 40, 20, 20);
				this.addWidgetData(this.zoomInButtonData);
				this.zoomInButtonData.setAcceptFocus(false);
			}
			{
				this.zoomOutButtonData = new FunctionButtonData("zoomOut", "-", this.getDesktopSizeX() - 350, 40, 20, 20);
				this.addWidgetData(this.zoomOutButtonData);
				this.zoomOutButtonData.setAcceptFocus(false);
			}

			{
				this.playButtonData = new FunctionButtonData("play", "Play", 100, 40, 90, 20);
				this.addWidgetData(this.playButtonData);
			}
			{
				this.pauseButtonData = new FunctionButtonData("pause", "Pause", 200, 40, 90, 20);
				this.addWidgetData(this.pauseButtonData);
			}
			{
				this.stopButtonData = new FunctionButtonData("stop", "Stop", 300, 40, 90, 20);
				this.addWidgetData(this.stopButtonData);
			}
		}
		{
			// Add Bottom Pane
			
			int posY = this.getDesktopSizeY() - bottomMenuSizeY;
			
			PaneData bottomMenuPaneData = new PaneData(0, posY, this.getDesktopSizeX(), bottomMenuSizeY);
			this.addWidgetData(bottomMenuPaneData);

			{
				{
					LabelData generatorLabelData = new LabelData("Generator:", 10, posY + 10, 60, 16);
					this.addWidgetData(generatorLabelData);
				}
				{
					LabelData generatorNameLabelData = new LabelData("Name:", 10, posY + 30, 60, 16);
					this.addWidgetData(generatorNameLabelData);
				}
				{
					this.generatorNameInputlineData = new InputlineData("name", 70, posY + 30, 150, 16);
					this.addWidgetData(this.generatorNameInputlineData);
				}
				
				{
					LabelData generatorNameLabelData = new LabelData("Start-Time:", 10, posY + 50, 60, 16);
					this.addWidgetData(generatorNameLabelData);
				}
				{
					this.generatorStartTimeInputlineData = new InputlineData("startTime", 70, posY + 50, 150, 16);
					this.addWidgetData(this.generatorStartTimeInputlineData);
				}
				
				{
					LabelData generatorNameLabelData = new LabelData("End-Time:", 10, posY + 70, 60, 16);
					this.addWidgetData(generatorNameLabelData);
				}
				{
					this.generatorEndTimeInputlineData = new InputlineData("endTime", 70, posY + 70, 150, 16);
					this.addWidgetData(this.generatorEndTimeInputlineData);
				}
				
				{
					this.removeGeneratorbuttonData = new FunctionButtonData("remove", "Remove", 70, posY + 90, 90, 18);
					this.addWidgetData(this.removeGeneratorbuttonData);
				}
				{
					this.setGeneratorButtonData = new FunctionButtonData("set", "Set", 170, posY + 90, 50, 18);
					this.addWidgetData(this.setGeneratorButtonData);

					this.generatorNameInputlineData.setDefaultSubmitWidgetInterface(this.setGeneratorButtonData);
					this.generatorStartTimeInputlineData.setDefaultSubmitWidgetInterface(this.setGeneratorButtonData);
					this.generatorEndTimeInputlineData.setDefaultSubmitWidgetInterface(this.setGeneratorButtonData);
				}
			}
			{
				LabelData inputsLabelData = new LabelData("Inputs:", 250, posY + 10, 30, 16);
				this.addWidgetData(inputsLabelData);
			}
			{
				this.inputsVScrollbarData = new ScrollbarData("inputsVScroll", 530 - desktopData.getScrollbarWidth2(), posY + 10, 
						desktopData.getScrollbarWidth2(), 100, true);
				this.addWidgetData(this.inputsVScrollbarData);
			}
			{
				this.generatorInputsData = new InputsWidgetData(280, posY + 10, 250 - desktopData.getScrollbarWidth2(), 100, inputsVScrollbarData, null, desktopControllerData.getGeneratorTypesData());
				this.addWidgetData(this.generatorInputsData);
			}

			{
				LabelData generatorInputNameLabelData = new LabelData("Input: Generator:", 590, posY + 10, 60, 16);
				this.addWidgetData(generatorInputNameLabelData);
			}
			{
				this.generatorInputNameSelectData = new SelectData("inputName", 660, posY + 10, 130, 16);
				this.addWidgetData(this.generatorInputNameSelectData);
			}
			{
				LabelData generatorInputTypeLabelData = new LabelData("Type:", 590, posY + 30, 60, 16);
				this.addWidgetData(generatorInputTypeLabelData);
			}
			{
				this.generatorInputTypeSelectData = new SelectData("inputType", 660, posY + 30, 130, 16);
				this.addWidgetData(this.generatorInputTypeSelectData);
			}
			{
				LabelData generatorInputTypeLabelData = new LabelData("Value:", 590, posY + 50, 60, 16);
				this.addWidgetData(generatorInputTypeLabelData);
			}
			{
				this.generatorInputValueInputlineData = new InputlineData("inputValue", 660, posY + 50, 130, 16);
				this.addWidgetData(this.generatorInputValueInputlineData);
			}
			{
				LabelData labelData = new LabelData("Modul-Input:", 590, posY + 70, 60, 16);
				this.addWidgetData(labelData);
			}
			{
				this.generatorInputModulInputSelectData = new SelectData("inputModulInput", 660, posY + 70, 130, 16);
				this.addWidgetData(this.generatorInputModulInputSelectData);
			}
			
			{
				this.setInputButtonData = new FunctionButtonData("setInput", "Update", 625, posY + 90, 70, 18);
				this.addWidgetData(this.setInputButtonData);

				this.generatorInputNameSelectData.setDefaultSubmitWidgetInterface(this.setInputButtonData);
				this.generatorInputTypeSelectData.setDefaultSubmitWidgetInterface(this.setInputButtonData);
				this.generatorInputValueInputlineData.setDefaultSubmitWidgetInterface(this.setInputButtonData);
			}
			{
				this.addInputButtonData = new FunctionButtonData("addInput", "Add as new", 700, posY + 90, 90, 18);
				this.addWidgetData(this.addInputButtonData);
			}
			
			{
				this.newInputButtonData = new FunctionButtonData("newInput", "New", 540, posY + 40, 60, 18);
				this.addWidgetData(this.newInputButtonData);
			}
			{
				this.removeInputButtonData = new FunctionButtonData("removeInput", "Remove", 540, posY + 90, 60, 18);
				this.addWidgetData(this.removeInputButtonData);
			}
			{
				this.generatorInputTypeDescriptionTextWidgetData = new TextWidgetData("---", 150, posY + 110, 400, 16);
				this.addWidgetData(this.generatorInputTypeDescriptionTextWidgetData);
			}
		}
		
		{
			// Add Generators and Scrollbars:
			int generatorsSizeY = this.getDesktopSizeY() - (topMenuSizeY + desktopData.getScrollbarWidth() + bottomMenuSizeY);
			
			this.generatorsVScrollbarData = 
				new ScrollbarData("generatorsVScroll", this.getDesktopSizeX() - desktopData.getScrollbarWidth(), topMenuSizeY, 
						desktopData.getScrollbarWidth(), generatorsSizeY, true);
			
			this.generatorsHScrollbarData = 
				new ScrollbarData("generatorsHScroll", 
						generatorsLabelSizeX, topMenuSizeY + generatorsSizeY, 
						this.getDesktopSizeX() - (generatorsLabelSizeX + desktopData.getScrollbarWidth()), desktopData.getScrollbarWidth(), false);
			
			this.generatorsHScrollbarData.setScrollStep(0.5F);
			
			this.tracksListWidgetData = 
				new TracksListWidgetData(0, topMenuSizeY, 
						this.getDesktopSizeX() - desktopData.getScrollbarWidth(), generatorsSizeY, 
						generatorsLabelSizeX,
						desktopControllerData.getSoundData(),
						this.generatorsVScrollbarData, this.generatorsHScrollbarData);
			
			this.addWidgetData(this.tracksListWidgetData);
			
			this.addWidgetData(this.generatorsVScrollbarData);
			
			this.addWidgetData(this.generatorsHScrollbarData);
		}
	}

	public void setActionListeners(
			AddGeneratorButtonActionLogicListener addGeneratorButtonActionLogicListener,
			RemoveGeneratorButtonActionLogicListener removeGeneratorButtonActionLogicListener,
			GroupGeneratorButtonActionLogicListener groupGeneratorButtonActionLogicListener,

			PlayButtonActionLogicListener playButtonActionLogicListener,
			PauseButtonActionLogicListener pauseButtonActionLogicListener,
			StopButtonActionLogicListener stopButtonActionLogicListener,
			
			//ExitButtonActionLogicListener exitButtonActionLogicListener,
			//NewButtonActionLogicListener newButtonActionLogicListener,

			ZoomInButtonActionLogicListener zoomInButtonActionLogicListener,
			ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener,

			SetGeneratorButtonActionLogicListener setGeneratorButtonActionLogicListener,
			SetInputButtonActionLogicListener setInputButtonActionLogicListener,
			RemoveInputButtonActionLogicListener removeInputButtonActionLogicListener,
			NewInputButtonActionLogicListener newInputButtonActionLogicListener,
			AddInputButtonActionLogicListener addInputButtonActionLogicListener

			//SaveButtonActionLogicListener saveButtonActionLogicListener,
			//LoadButtonActionLogicListener loadButtonActionLogicListener
			/*
			SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener,
			SelectAddButtonActionLogicListener selectAddButtonActionLogicListener,
			SelectEditButtonActionLogicListener selectEditButtonActionLogicListener,
			SelectMainEditButtonActionLogicListener selectMainEditButtonActionLogicListener,
			SelectRemoveButtonActionLogicListener selectRemoveButtonActionLogicListener,
			
			SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener,
			SaveFileButtonActionLogicListener saveFileButtonActionLogicListener,
			
			LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener,
			LoadFileButtonActionLogicListener loadFileButtonActionLogicListener,

			CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener,
			SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener,
			
			InputTypeEditSaveActionListener inputTypeEditSaveActionListener
			*/
	)
	{
		this.addGeneratorButtonData.addActionLogicListener(addGeneratorButtonActionLogicListener);
		this.removeGeneratorbuttonData.addActionLogicListener(removeGeneratorButtonActionLogicListener);
		this.groupGeneratorbuttonData.addActionLogicListener(groupGeneratorButtonActionLogicListener);

		this.playButtonData.addActionLogicListener(playButtonActionLogicListener);
		this.pauseButtonData.addActionLogicListener(pauseButtonActionLogicListener);
		this.stopButtonData.addActionLogicListener(stopButtonActionLogicListener);
		
		//this.exitButtonData.addActionLogicListener(exitButtonActionLogicListener);
		//this.newButtonData.addActionLogicListener(newButtonActionLogicListener);
		
		this.zoomInButtonData.addActionLogicListener(zoomInButtonActionLogicListener);
		this.zoomOutButtonData.addActionLogicListener(zoomOutButtonActionLogicListener);
		
		this.setGeneratorButtonData.addActionLogicListener(setGeneratorButtonActionLogicListener);
		this.setInputButtonData.addActionLogicListener(setInputButtonActionLogicListener);
		this.removeInputButtonData.addActionLogicListener(removeInputButtonActionLogicListener);
		this.newInputButtonData.addActionLogicListener(newInputButtonActionLogicListener);
		this.addInputButtonData.addActionLogicListener(addInputButtonActionLogicListener);

		//this.saveButtonData.addActionLogicListener(saveButtonActionLogicListener);
		//this.loadButtonData.addActionLogicListener(loadButtonActionLogicListener);
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
	 * @return the attribute {@link #tracksListWidgetData}.
	 */
	public TracksListWidgetData getTracksListWidgetData()
	{
		return this.tracksListWidgetData;
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
	 * @return the attribute {@link #generatorInputTypeDescriptionTextWidgetData}.
	 */
	public TextWidgetData getGeneratorInputTypeDescriptionTextWidgetData()
	{
		return this.generatorInputTypeDescriptionTextWidgetData;
	}

	/**
	 * @return the attribute {@link #modulGeneratorTextWidgetData}.
	 */
	public TextWidgetData getModulGeneratorTextWidgetData()
	{
		return this.modulGeneratorTextWidgetData;
	}
	/**
	 * @return the attribute {@link #generatorInputModulInputSelectData}.
	 */
	public SelectData getGeneratorInputModulInputSelectData()
	{
		return this.generatorInputModulInputSelectData;
	}

	/**
	 * @return
	 */
	public ModulGeneratorTypeData getEditModulTypeData()
	{
		return this.desktopControllerData.getEditData().getEditModulTypeData();
	}

	/**
	 * 
	 */
	public void clearTracks()
	{
		this.getTracksListWidgetData().clearTracks();
	}
	/**
	 * @return the attribute {@link #inputsVScrollbarData}.
	 */
	public ScrollbarData getInputsVScrollbarData()
	{
		return this.inputsVScrollbarData;
	}
	/**
	 * @return the attribute {@link #desktopControllerData}.
	 */
	public DesktopControllerData getDesktopControllerData()
	{
		return this.desktopControllerData;
	}
}
