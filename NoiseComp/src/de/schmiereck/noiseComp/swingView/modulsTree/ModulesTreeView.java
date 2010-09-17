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
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
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
	 * Modules Tree Model.
	 */
	private final ModulesTreeModel modulesTreeModel;
	
	/**
	 * Do Edit-Module Listeners.
	 */
	private List<DoEditModuleListener> doEditModuleListeners = new Vector<DoEditModuleListener>();

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulesTreeModel
	 * 			is the Modules Tree Model.
	 */
	public ModulesTreeView(final ModulesTreeModel modulesTreeModel)
	{
		//==========================================================================================
		this.modulesTreeModel = modulesTreeModel;
		
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
					
					String labelDecoration;
					
					// Edited?
					if (modulesTreeModel.getEditedModulGeneratorTypeData() == generatorTypeData)
					{
						labelDecoration = " *";
					}	
					else
					{
						labelDecoration = "";
					}	
					
					this.setText(generatorTypeData.getGeneratorTypeName() + labelDecoration);
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
		ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
		
		TreePath treePath = this.searchModulTreeNode(editedModulGeneratorTypeData);
		
//		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			
		this.setSelectionPath(treePath);
	}

	/**
	 * @param editedModulGeneratorTypeData
	 * @return
	 */
	public TreePath searchModulTreeNode(ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		DefaultTreeModel treeModel = (DefaultTreeModel)this.getModel();
		
		DefaultMutableTreeNode rootTreeNode = (DefaultMutableTreeNode)treeModel.getRoot();
		
		ModulTreePath modulTreePath = this.searchModulTreeNode(rootTreeNode, editedModulGeneratorTypeData);
		
		TreePath treePath = modulTreePath.createTreePath();
		
		return treePath;
	}

	/**
	 * Search given generator starting from given node.
	 * 
	 * @param rootTreeNode
	 * 			is the node.
	 * @param modulGeneratorTypeData
	 * 			is the generator.
	 * @return
	 * 			the path or <code>null</code>.
	 */
	private ModulTreePath searchModulTreeNode(DefaultMutableTreeNode rootTreeNode, 
	                                          ModulGeneratorTypeData modulGeneratorTypeData)
	{
		ModulTreePath retTreePath;
		
		Object userObject = rootTreeNode.getUserObject();
		
		if (modulGeneratorTypeData == userObject)
		{
			retTreePath = new ModulTreePath(rootTreeNode);//rootTreePath.pathByAddingChild(userObject);
		}
		else
		{
			retTreePath = null;
			
			for (int childPos = 0; childPos < rootTreeNode.getChildCount(); childPos++)
			{
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)rootTreeNode.getChildAt(childPos);
				
				ModulTreePath treePath = this.searchModulTreeNode(treeNode, modulGeneratorTypeData);
				
				if (treePath != null)
				{
					retTreePath = new ModulTreePath(rootTreeNode, treePath);
					break;
				}
			}
		}
		
		return retTreePath;
	}
	
}
