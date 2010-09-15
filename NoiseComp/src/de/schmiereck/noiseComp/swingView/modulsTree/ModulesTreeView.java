/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;

/**
 * <p>
 * 	Modules Tree View.
 * </p>
 * 
 * http://download.oracle.com/javase/tutorial/uiswing/components/tree.html
 * 
 * @author smk
 * @version <p>14.09.2010:	created, smk</p>
 */
public class ModulesTreeView
extends JTree
implements EditModuleChangedListener
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Do Edit-Module Listeners.
	 */
	private List<DoEditModuleListener> doEditModuleListeners = new Vector<DoEditModuleListener>();

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ModulesTreeView()
	{
		//==========================================================================================
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		this.setCellRenderer(new DefaultTreeCellRenderer()
		{
			@Override
			public Component getTreeCellRendererComponent(JTree tree, 
														  Object value, 
														  boolean selected,
														  boolean expanded, 
														  boolean leaf, 
														  int row,
														  boolean hasFocus)
			{
				// Die Originalmethode die Standardeinstellungen machen lassen
				super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				
				// Den Wert des Knotens abfragen
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value;
				Object userObject = treeNode.getUserObject();
				
				if (userObject instanceof GeneratorTypeData)
				{
					GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;
					
					this.setText(generatorTypeData.getGeneratorTypeName());
				}
				
				return this;
			}
		});
		//------------------------------------------------------------------------------------------
		{
			// Add listener to components that can bring up popup menus.
			MouseListener popupListener = new ModulTreeMouseListener(this);
			//output.addMouseListener(popupListener);
			//menuBar.addMouseListener(popupListener);
			this.addMouseListener(popupListener);
		}
		//==========================================================================================
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the module to edit.
	 */
	public void notifyEditModulListeners(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		for (DoEditModuleListener doEditModuleListener : this.doEditModuleListeners)
		{
			doEditModuleListener.notifyEditModul(modulGeneratorTypeData);
		}
	}

	/**
	 * @param doEditModuleListener
	 * 			to add to {@link #doEditModuleListeners}.
	 */
	public void addDoEditModuleListener(DoEditModuleListener doEditModuleListener)
	{
		this.doEditModuleListeners.add(doEditModuleListener);
	}

	/**
	 * @param generatorTypeData
	 * 			is the generator to edit.
	 */
	public void notifyDoEditGeneratorListeners(GeneratorTypeData generatorTypeData)
	{
		// TODO impl.
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener#notifyEditModulChanged(de.schmiereck.noiseComp.swingView.appModel.AppModel)
	 */
	@Override
	public void notifyEditModulChanged(ModulesTreeModel modulesTreeModel)
	{
	}
	
}
