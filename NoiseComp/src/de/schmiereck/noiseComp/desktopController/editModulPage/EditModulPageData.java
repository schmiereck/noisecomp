package de.schmiereck.noiseComp.desktopController.editModulPage;

import de.schmiereck.noiseComp.desktop.DesktopData;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopPage.DesktopPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.FunctionButtonData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputTypesWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.InputlineData;
import de.schmiereck.noiseComp.desktopPage.widgets.LabelData;
import de.schmiereck.noiseComp.desktopPage.widgets.PaneData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>14.03.2004: created, smk</p>
 */
public class EditModulPageData
extends DesktopPageData
{
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
	 * Select Input Type: InputTypes-List
	 */
	private InputTypesWidgetData	inputTypesListData;
	
	/**
	 * Constructor.
	 * 
	 * @param desktopData
	 * @param desktopSizeX
	 * @param desktopSizeY
	 */
	public EditModulPageData(DesktopData desktopData, int desktopSizeX, int desktopSizeY)
	{
		super(desktopData, desktopSizeX, desktopSizeY);

		{
			// Add Main Panel:
			PaneData paneData = new PaneData(0, 0, this.getDesktopSizeX(), this.getDesktopSizeY());
			this.addWidgetData(paneData);
		}
		
	
		{
			LabelData nameLabelData = new LabelData("Name:", 10, 100, 60, 16);
			this.addWidgetData(nameLabelData);
		}
		{
			this.groupNameInputlineData = new InputlineData("groupName", 70, 100, 150, 16);
			this.addWidgetData(this.groupNameInputlineData);
		}

		ScrollbarData verticalScrollbarData;
		{
			verticalScrollbarData = new ScrollbarData("inputTypesVScroll", 780 - desktopData.getScrollbarWidth2(), 120, 
					desktopData.getScrollbarWidth2(), 400, true);
			this.addWidgetData(verticalScrollbarData);
		}
		{
			this.inputTypesListData = new InputTypesWidgetData(100, 120, 680 - desktopData.getScrollbarWidth2(), 400, verticalScrollbarData, null, null);
			this.addWidgetData(this.inputTypesListData);
		}
		
		{
			this.saveGroupButtonData = new FunctionButtonData("groupSave", "Save Modul", 70, 560, 250, 20);
			this.addWidgetData(this.saveGroupButtonData);
		}
		{
			this.cancelGroupButtonData = new FunctionButtonData("cancel", "Cancel", 500, 560, 90, 20);
			this.addWidgetData(this.cancelGroupButtonData);
		}
	}

	public void setActionListeners(
			CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener,
			SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener
			)
	{
		this.cancelGroupButtonData.addActionLogicListener(cancelGroupButtonActionLogicListener);
		this.saveGroupButtonData.addActionLogicListener(saveGroupButtonActionLogicListener);
	}

	/**
	 * @return the attribute {@link #groupNameInputlineData}.
	 */
	public InputlineData getGroupNameInputlineData()
	{
		return this.groupNameInputlineData;
	}
}
