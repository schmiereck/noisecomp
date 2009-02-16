package de.schmiereck.noiseComp.view.desktopController;

import java.util.Iterator;
import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.view.desctopPage.DesktopPageData;
import de.schmiereck.noiseComp.view.desctopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.view.desctopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.view.desctopPage.widgets.LabelData;
import de.schmiereck.noiseComp.view.desctopPage.widgets.PaneData;
import de.schmiereck.noiseComp.view.desktop.DesktopData;
import de.schmiereck.noiseComp.view.desktopController.actions.old.AddGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.GroupGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.LoadButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.LoadCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.PauseButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.PlayButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.SaveCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.actions.old.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.editModulPage.EditModulPageData;
import de.schmiereck.noiseComp.view.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.view.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.MainPageData;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.old.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.mainPage.actions.old.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.SelectGeneratorPageData;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions.SelectInsertButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.view.desktopController.selectNewGeneratorPage.SelectNewGeneratorPageData;
import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedListener;
import de.schmiereck.screenTools.controller.DataChangedObserver;
import de.schmiereck.screenTools.graphic.GraphicMediator;

/**
 * <p>
 * 	Manages the data of the desctop controller {@link de.schmiereck.noiseComp.view.desktopController.DesktopControllerLogic}.
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
	private GeneratorTypesData generatorTypesData;
	
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
	 * Dialog to select a new sound generator.
	 */
	private SelectNewGeneratorPageData selectNewGeneratorPageData	= null;
	
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
	//private Generators	mainGenerators = null;
	
	private EditData editData;

	/**
	 * List of {@link DataChangedListener}-Objects.
	 */
	private DataChangedObserver dataChangedObserver;
	
	/**
	 * Constructor.
	 * 
	 * @param dataChangedObserver
	 * 			is the Observer.
	 * @param soundData
	 * 			is the Sound Data.
	 * @param generatorTypesData
	 * 			are the generator types.
	 */
	public DesktopControllerData(DataChangedObserver dataChangedObserver, 
								 SoundData soundData, 
								 GeneratorTypesData generatorTypesData,
								 GraphicMediator graphicMediator)
	{
		this.dataChangedObserver = dataChangedObserver;
		this.generatorTypesData  = generatorTypesData;
		
		this.soundData = soundData;
		//this.mainGenerators = mainGenerators;
		//this.mainModulTypeData = mainModulTypeData;
		
		this.desktopData = new DesktopData(graphicMediator);
		
		this.editData = new EditData(this);
		
		this.mainDesktopPageData = this.createMainPage(dataChangedObserver, 
													   desktopData);
		this.selectGeneratorPageData = this.createSelectGeneratorPage(dataChangedObserver, 
																	  desktopData);
		this.selectNewGeneratorPageData = this.createNewSelectGeneratorPage(dataChangedObserver, 
																			desktopData);
		this.editModulPageData = this.createEditModulPage(dataChangedObserver, 
														  desktopData);
		this.saveDesktopPageData = this.createSavePage(desktopData);
		this.loadDesktopPageData = this.createLoadPage(desktopData);
		
		this.activeDesktopPageData = this.mainDesktopPageData;
	}

	private MainPageData createMainPage(DataChangedObserver dataChangedObserver, 
										DesktopData desktopData)
	{
		return new MainPageData(dataChangedObserver, desktopData, this, this.getFieldWidth(), this.getFieldHeight());
	}

	private SelectGeneratorPageData createSelectGeneratorPage(DataChangedObserver dataChangedObserver, 
															  DesktopData desktopData)
	{
		return new SelectGeneratorPageData(this,
										   dataChangedObserver, desktopData, this.getFieldWidth(), this.getFieldHeight(),
										   this.generatorTypesData);
	}

	private SelectNewGeneratorPageData createNewSelectGeneratorPage(DataChangedObserver dataChangedObserver, 
																	DesktopData desktopData)
	{
		return new SelectNewGeneratorPageData(this,
											  dataChangedObserver, desktopData, this.getFieldWidth(), this.getFieldHeight(),
											  this.generatorTypesData);
	}

	private EditModulPageData createEditModulPage(DataChangedObserver dataChangedObserver, 
												  DesktopData desktopData)
	{
		return new EditModulPageData(this,
									 dataChangedObserver, desktopData, this.getFieldWidth(), this.getFieldHeight());
	}
	
	private DesktopPageData createSavePage(DesktopData desktopData)
	{
		DesktopPageData	desktopPageData = new DesktopPageData(desktopData, this.getFieldWidth(), this.getFieldHeight());

		{
			// Add Main Page:
			PaneData paneData = new PaneData(this, dataChangedObserver, 0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			this.saveCancelButtonData = new FunctionButtonData(this, dataChangedObserver, "cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(this.saveCancelButtonData);
		}

		{
			LabelData nameLabelData = new LabelData(this, dataChangedObserver, "Name:", 10, 100, 60, 16);
			desktopPageData.addWidgetData(nameLabelData);
		}
		{
			this.saveFileNameInputlineData = new InputlineData(this, dataChangedObserver, "saveFileName", 70, 100, 150, 16);
			desktopPageData.addWidgetData(this.saveFileNameInputlineData);
		}
		
		{
			this.saveFileButtonData = new FunctionButtonData(this, dataChangedObserver, "saveFile", "Save", 100, 160, 250, 20);
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
			PaneData paneData = new PaneData(this, dataChangedObserver, 0, 0, this.getFieldWidth(), this.getFieldHeight());
			desktopPageData.addWidgetData(paneData);
		}
		
		{
			this.loadCancelButtonData = new FunctionButtonData(this, dataChangedObserver, "cancel", "Cancel", 100, 10, 90, 20);
			desktopPageData.addWidgetData(this.loadCancelButtonData);
		}

		{
			LabelData nameLabelData = new LabelData(this, dataChangedObserver, "Name:", 10, 100, 60, 16);
			desktopPageData.addWidgetData(nameLabelData);
		}
		{
			this.loadFileNameInputlineData = new InputlineData(this, dataChangedObserver, "loadFileName", 70, 100, 150, 16);
			desktopPageData.addWidgetData(this.loadFileNameInputlineData);
		}
		
		{
			this.loadFileButtonData = new FunctionButtonData(this, dataChangedObserver, "loadFile", "Load", 100, 160, 250, 20);
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
			SelectInsertButtonActionLogicListener selectInsertButtonActionLogicListener,
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
													loadButtonActionLogicListener
													);
		
		this.selectGeneratorPageData.setActionListeners(selectCancelButtonActionLogicListener,
				selectAddButtonActionLogicListener,
				selectInsertButtonActionLogicListener,
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
		
		this.dataChangedObserver.dataChanged(this);
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
	
	//public GeneratorTypeData searchGeneratorTypeData(String generatorTypeClassName)
	//{
	//	return this.generatorTypesData.searchGeneratorTypeData(generatorTypeClassName);
	//}
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
	 * @return the attribute {@link #selectNewGeneratorPageData}.
	 */
	public SelectNewGeneratorPageData getSelectNewGeneratorPageData()
	{
		return this.selectNewGeneratorPageData;
	}
	/**
	 * @return the attribute {@link #generatorTypesData}.
	public GeneratorTypesData getGeneratorTypesData()
	{
		return this.generatorTypesData;
	}
	 */
	
	/**
	 * @return
	public Generators getMainGenerators()
	{
		return this.mainGenerators;
	}
	 */
	/**
	 * @param mainGenerators is the new value for attribute {@link #mainGenerators} to set.
	public void setMainGenerators(Generators mainGenerators)
	{
		this.mainGenerators = mainGenerators;
	}
	 */

	/**
	 * 
	public void clearTracks()
	{
		this.mainDesktopPageData.clearTracks();

		Generators generators = new Generators();
		
		///this.soundData.setGenerators(generators);
		
		this.setMainGenerators(generators);
	}
	 */
	
	private String popupRuntimeExceptionText = null;
	
	/**
	 * @param ex
	 */
	public void setPopupRuntimeException(Exception ex)
	{
		this.popupRuntimeExceptionText = ex.getMessage();
		
		throw new PopupRuntimeException("HANDELED", ex);
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

