package de.schmiereck.noiseComp.desktopController.selectGeneratorPage;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
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
	
	/**
	 * Constructor.
	 * 
	 * @param desktopData
	 * @param desktopSizeX
	 * @param desktopSizeY
	 */
	public SelectGeneratorPageData(DesktopData desktopData, int desktopSizeX, int desktopSizeY,
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
			LabelData labelData = new LabelData("Generator-Types:", 100, 100, 100, 20);
			this.addWidgetData(labelData);
		}
		ScrollbarData verticalScrollbarData;
		{
			verticalScrollbarData = new ScrollbarData("generatorTypesVScroll", 780 - desktopData.getScrollbarWidth2(), 120, 
					desktopData.getScrollbarWidth2(), 400, true);
			this.addWidgetData(verticalScrollbarData);
		}
		{
			this.generatorTypesListData = new GeneratorTypesWidgetData(100, 120, 680 - desktopData.getScrollbarWidth2(), 400, verticalScrollbarData, null, generatorTypesData);
			this.addWidgetData(this.generatorTypesListData);
		}
		{
			this.selectAddButtonData = new FunctionButtonData("selectAdd", "Add selected", 100, 70, 100, 20);
			this.addWidgetData(this.selectAddButtonData);
		}
		{
			this.selectMainEditButtonData = new FunctionButtonData("selectMainEdit", "Edit Main Modul", 210, 40, 220, 20);
			this.addWidgetData(this.selectMainEditButtonData);
		}
		{
			this.selectEditButtonData = new FunctionButtonData("selectEdit", "Edit Modul", 210, 70, 100, 20);
			this.addWidgetData(this.selectEditButtonData);
		}
		{
			this.selectRemoveButtonData = new FunctionButtonData("selectRemove", "Remove Modul", 320, 70, 110, 20);
			this.addWidgetData(this.selectRemoveButtonData);
		}
	}

	public void setActionListeners(
			SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener,
			SelectAddButtonActionLogicListener selectAddButtonActionLogicListener,
			SelectEditButtonActionLogicListener selectEditButtonActionLogicListener,
			SelectMainEditButtonActionLogicListener selectMainEditButtonActionLogicListener,
			SelectRemoveButtonActionLogicListener selectRemoveButtonActionLogicListener
		)
	{
		this.selectCancelButtonData.addActionLogicListener(selectCancelButtonActionLogicListener);
		this.selectAddButtonData.addActionLogicListener(selectAddButtonActionLogicListener);
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
}
