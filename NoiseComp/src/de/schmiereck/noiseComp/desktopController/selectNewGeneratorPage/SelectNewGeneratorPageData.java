package de.schmiereck.noiseComp.desktopController.selectNewGeneratorPage;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectInsertButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.GeneratorTypesWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;

/**
 * <p>
 * 	Eingabe der Daten eines neuen Generator-Moduls,
 * 	das der Liste hinzugefügt werden soll.
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
	 * Dialog: Select Generator: Main-Edit-Button
	 */
	private FunctionButtonData selectMainEditButtonData = null;
	/**
	 * Dialog: Select Generator: Remove-Button
	 */
	private FunctionButtonData selectRemoveButtonData = null;
	
	/**
	 * Constructor.
	 * 
	 * @param desktopData
	 * @param desktopSizeX
	 * @param desktopSizeY
	 */
	public SelectNewGeneratorPageData(DesktopData desktopData, int desktopSizeX, int desktopSizeY,
			GeneratorTypesData generatorTypesData)
	{
		super(desktopData, desktopSizeX, desktopSizeY);

		{
			// Add Main Page:
			PaneData paneData = new PaneData(0, 0, this.getDesktopSizeX(), this.getDesktopSizeY());
			this.addWidgetData(paneData);
		}
		
		{
			this.selectCancelButtonData = new FunctionButtonData("cancel", "Cancel", 100, 10, 90, 20);
			this.addWidgetData(this.selectCancelButtonData);
		}
		{
			LabelData labelData = new LabelData("Generator-Type:", 100, 100, 100, 20);
			this.addWidgetData(labelData);
		}
		
		{
			this.selectAddButtonData = new FunctionButtonData("selectAdd", "Add selected...", 100, 40, 100, 20);
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
