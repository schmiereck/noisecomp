/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.moduleInputs;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.moduleInputTypeEdit.ModuleInputTypeEditView;
import de.schmiereck.noiseComp.swingView.moduleInputTypeSelect.ModuleInputTypeSelectView;

/**
 * <p>
 * 	ModuleInputs View.
 * </p>
 * 
 * @author smk
 * @version <p>12.09.2010:	created, smk</p>
 */
public class ModuleInputTypesView
extends JDialog  {
	//**********************************************************************************************
	// Fields:

	private final JSplitPane splitPane;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * Constructs an invisible Dialog.
	 * 
	 * @param appView 
	 * 			this is the App View.
	 */
	public ModuleInputTypesView(final AppView appView) {
		super(appView, false);
		//==========================================================================================
		this.setVisible(false);
		
		this.setTitle("Module-Inputs");
//		this.setModal(false);
		
		this.setSize(400, 300);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Split-Pane:
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.splitPane.setOneTouchExpandable(true);
		this.splitPane.setResizeWeight(1.0D);
		
		this.add(this.splitPane);
		
		//==========================================================================================
	}

	/**
	 * @param moduleInputTypeSelectView
	 * 			set the ModuleInput-Type Select View.
	 */
	public void setModuleInputTypeSelectView(ModuleInputTypeSelectView moduleInputTypeSelectView)
	{
		JScrollPane scrollpane = new JScrollPane(moduleInputTypeSelectView);

		int vScrollBarWidth = scrollpane.getVerticalScrollBar().getPreferredSize().width;
		Dimension dimTable = moduleInputTypeSelectView.getPreferredSize();
		dimTable.setSize(dimTable.getWidth() + vScrollBarWidth, 
		                 dimTable.getHeight());
		
		scrollpane.setBorder(BorderFactory.createTitledBorder("Input-Types:"));
				
		this.splitPane.setLeftComponent(scrollpane);
	}

	/**
	 * @param moduleInputTypeEditView
	 * 			set the ModuleInput-Type Edit View.
	 */
	public void setModuleInputTypeEditView(ModuleInputTypeEditView moduleInputTypeEditView)
	{
		this.splitPane.setRightComponent(moduleInputTypeEditView);
	}
}
