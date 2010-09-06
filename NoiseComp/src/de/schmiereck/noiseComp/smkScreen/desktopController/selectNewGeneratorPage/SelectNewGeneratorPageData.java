package de.schmiereck.noiseComp.smkScreen.desktopController.selectNewGeneratorPage;

import de.schmiereck.noiseComp.smkScreen.desctopPage.DesktopPageData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.LabelData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.PaneData;
import de.schmiereck.noiseComp.smkScreen.desktop.DesktopData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * <p>
 * 	Eingabe der Daten eines neuen Generator-Moduls,
 * 	das der Liste hinzugef�gt werden soll.
 * </p>
 * 
 * @author smk
 * @version <p>21.03.2004: created, smk</p>
 */
public class SelectNewGeneratorPageData
extends DesktopPageData
{
	/**
	 * Dialog: Select Generator: Cancel-Button
	 */
	private FunctionButtonData selectCancelButtonData = null;
	
	/**
	 * Dialog: Select Generator: Add-Button
	 */
	private FunctionButtonData selectAddButtonData = null;
	/**
	 * Dialog: Select Generator: Insert-Button
	 */
	private FunctionButtonData selectInsertButtonData = null;
	/**
	 * Dialog: Select Generator: Edit-Button
	 */
	private FunctionButtonData selectEditButtonData = null;
	/**
	 * Dialog: Select Generator: ConsoleMain-Edit-Button
	 */
	private FunctionButtonData selectMainEditButtonData = null;
	/**
	 * Dialog: Select Generator: Remove-Button
	 */
	private FunctionButtonData selectRemoveButtonData = null;
	
	/**
	 * Constructor.
	 * 
	 */
	public SelectNewGeneratorPageData(DesktopControllerData desktopControllerData,
									  DataChangedObserver dataChangedObserver, 
									  DesktopData desktopData, int desktopSizeX, int desktopSizeY)
	{
		super(desktopData, desktopSizeX, desktopSizeY);

		{
			// Add ConsoleMain Page:
			PaneData paneData = new PaneData(desktopControllerData, dataChangedObserver, 0, 0, this.getDesktopSizeX(), this.getDesktopSizeY());
			this.addWidgetData(paneData);
		}
		
		{
			this.selectCancelButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "cancel", "Cancel", 100, 10, 90, 20);
			this.addWidgetData(this.selectCancelButtonData);
		}
		{
			LabelData labelData = new LabelData(desktopControllerData, dataChangedObserver, "Generator-Type:", 100, 100, 100, 20);
			this.addWidgetData(labelData);
		}
		
		{
			this.selectAddButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "selectAdd", "Add selected...", 100, 40, 100, 20);
			this.addWidgetData(this.selectAddButtonData);
		}
		
	}

	public void setActionListeners(
			SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener,
			SelectAddButtonActionLogicListener selectAddButtonActionLogicListener
		)
	{
		this.selectCancelButtonData.addActionLogicListener(selectCancelButtonActionLogicListener);
		this.selectAddButtonData.addActionLogicListener(selectAddButtonActionLogicListener);
	}
}
