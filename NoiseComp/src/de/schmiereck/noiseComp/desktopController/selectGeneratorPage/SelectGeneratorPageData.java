package de.schmiereck.noiseComp.desktopController.selectGeneratorPage;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
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
import de.schmiereck.screenTools.controller.DataChangedObserver;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>21.03.2004: created, smk</p>
 */
public class SelectGeneratorPageData
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
	 * Dialog: Select Generator: GeneratorTypes-List
	 */
	private GeneratorTypesWidgetData	generatorTypesListData;
	
	private GeneratorTypesData generatorTypesData;
	
	/**
	 * Constructor.
	 * 
	 * @param desktopData
	 * @param desktopSizeX
	 * @param desktopSizeY
	 */
	public SelectGeneratorPageData(DesktopControllerData desktopControllerData,
								   DataChangedObserver dataChangedObserver, 
								   DesktopData desktopData, int desktopSizeX, int desktopSizeY,
								   GeneratorTypesData generatorTypesData)
	{
		super(desktopData, desktopSizeX, desktopSizeY);

		this.generatorTypesData = generatorTypesData;
		
		{
			// Add Main Page:
			PaneData paneData = new PaneData(desktopControllerData, dataChangedObserver, 0, 0, this.getDesktopSizeX(), this.getDesktopSizeY());
			this.addWidgetData(paneData);
		}
		
		{
			this.selectCancelButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "cancel", "Cancel", 100, 10, 90, 20);
			this.addWidgetData(this.selectCancelButtonData);
		}
		{
			LabelData labelData = new LabelData(desktopControllerData, dataChangedObserver, "Generator-Types:", 100, 100, 100, 20);
			this.addWidgetData(labelData);
		}
		ScrollbarData verticalScrollbarData;
		{
			verticalScrollbarData = new ScrollbarData(desktopControllerData, dataChangedObserver, "generatorTypesVScroll", 780 - desktopData.getScrollbarWidth2(), 120, 
					desktopData.getScrollbarWidth2(), 400, true);
			this.addWidgetData(verticalScrollbarData);
		}
		{
			this.generatorTypesListData = new GeneratorTypesWidgetData(desktopControllerData, dataChangedObserver, 100, 120, 680 - desktopData.getScrollbarWidth2(), 400, 
																	   verticalScrollbarData, 
																	   null, 
																	   generatorTypesData);
			this.addWidgetData(this.generatorTypesListData);
		}
		
		{
			this.selectAddButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "selectAdd", "Add selected...", 100, 40, 100, 20);
			this.addWidgetData(this.selectAddButtonData);
		}
		{
			this.selectMainEditButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "selectMainEdit", "Edit Main Modul...", 210, 40, 220, 20);
			this.addWidgetData(this.selectMainEditButtonData);
		}
		
		{
			this.selectInsertButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "selectInsert", "Insert...", 100, 70, 100, 20);
			this.addWidgetData(this.selectInsertButtonData);
		}
		{
			this.selectEditButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "selectEdit", "Edit...", 210, 70, 100, 20);
			this.addWidgetData(this.selectEditButtonData);
		}
		{
			this.selectRemoveButtonData = new FunctionButtonData(desktopControllerData, dataChangedObserver, "selectRemove", "Remove", 320, 70, 110, 20);
			this.addWidgetData(this.selectRemoveButtonData);
		}
	}

	public void setActionListeners(
			SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener,
			SelectAddButtonActionLogicListener selectAddButtonActionLogicListener,
			SelectInsertButtonActionLogicListener selectInsertButtonActionLogicListener,
			SelectEditButtonActionLogicListener selectEditButtonActionLogicListener,
			SelectMainEditButtonActionLogicListener selectMainEditButtonActionLogicListener,
			SelectRemoveButtonActionLogicListener selectRemoveButtonActionLogicListener
		)
	{
		this.selectCancelButtonData.addActionLogicListener(selectCancelButtonActionLogicListener);
		this.selectAddButtonData.addActionLogicListener(selectAddButtonActionLogicListener);
		this.selectInsertButtonData.addActionLogicListener(selectInsertButtonActionLogicListener);
		this.selectEditButtonData.addActionLogicListener(selectEditButtonActionLogicListener);
		this.selectMainEditButtonData.addActionLogicListener(selectMainEditButtonActionLogicListener);
		this.selectRemoveButtonData.addActionLogicListener(selectRemoveButtonActionLogicListener);
	}

	/**
	 * @return the attribute {@link #generatorTypesListData}.
	 */
	public GeneratorTypesWidgetData getGeneratorTypesListData()
	{
		return this.generatorTypesListData;
	}
	/**
	 * @return returns the {@link #generatorTypesData}.
	 */
	public GeneratorTypesData getGeneratorTypesData()
	{
		return this.generatorTypesData;
	}
}
