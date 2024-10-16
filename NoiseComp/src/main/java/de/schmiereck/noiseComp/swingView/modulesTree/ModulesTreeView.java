/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulesTree;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
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
	@SuppressWarnings("unused")
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
		
		this.setModel(this.modulesTreeModel.getTreeModel());
		
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
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Let the original mathod do the default settings.
				super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Calulate the type of node.
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value;
				Object userObject = treeNode.getUserObject();
				
				if (userObject instanceof GeneratorTypeInfoData)
				{
					GeneratorTypeInfoData generatorTypeInfoData = (GeneratorTypeInfoData)userObject;
					
					String labelDecoration = "";
					
					if (userObject instanceof ModuleGeneratorTypeInfoData)
					{
						ModuleGeneratorTypeInfoData moduleGeneratorTypeData = (ModuleGeneratorTypeInfoData)userObject;
						
						boolean isMainModule = moduleGeneratorTypeData.getIsMainModuleGeneratorType();
						
						if (isMainModule == true)
						{
							labelDecoration += " (Main)";
						}
					}
					
					// Edited?
					if (modulesTreeModel.getEditedModuleGeneratorTypeInfoData() == generatorTypeInfoData)
					{
						labelDecoration += " *";
					}	
					
					this.setText(generatorTypeInfoData.getGeneratorTypeName() + labelDecoration);
					
					// Update changed text width.
					//this.revalidate();
				}
				else
				{
			        Icon icon;
			        
//			        if (leaf) 
//			        {
//			            icon = this.getLeafIcon();
//			        } 
//			        else
			        {
			        	if (expanded) 
			        	{
			        		icon = this.getOpenIcon();
			        	} 
			        	else 
			        	{
			        		icon = this.getClosedIcon();
			        	}
			        }
			        
			        this.setIcon(icon);
			        this.setText(userObject.toString());
				}
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				return this;
			}
			
			/**
	          Add this override to recalculate the width of this JLabel.
	           The super class default behaviour miscalculates the width, and so the 
	           '...'  can appear. Instead, we 'simulate' the FontMetrics'                   
	           stringWidth() method, by using charWidth(), plus some initialization 
	           and padding
	         */
	    	public Dimension getPreferredSize() 
	    	{
	    		Dimension dim = super.getPreferredSize();
	    		FontMetrics fm = getFontMetrics(getFont());
	    		char[] chars = getText().toCharArray();
	 
	    		int w = getIconTextGap() + 16;
	    	
	    		for (char ch : chars)  
	    		{
	    			w += fm.charWidth(ch);
	    		}
	    		
	    		w += getText().length();
	    		dim.width = w;
			
	    		return dim;
	    	}
		});
		//==========================================================================================
	}

	/**
	 * @param moduleGeneratorTypeData
	 * 			is the module to edit.
	 */
	public void notifyEditModuleListeners(ModuleGeneratorTypeInfoData moduleGeneratorTypeData)
	{
		//==========================================================================================
		for (DoEditModuleListener doEditModuleListener : this.doEditModuleListeners)
		{
			doEditModuleListener.notifyEditModule(moduleGeneratorTypeData);
		}
		//==========================================================================================
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
	 * @param generatorTypeInfoData
	 * 			is the generator to edit.
	 */
	public void notifyDoEditGeneratorListeners(GeneratorTypeInfoData generatorTypeInfoData)
	{
		// TODO impl.
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener#notifyEditModulehanged(de.schmiereck.noiseComp.swingView.appModel.AppModel)
	 */
	@Override
	public void notifyEditModuleChanged(ModulesTreeModel modulesTreeModel,
	                                    TreePath selectionTreePath)
	{
		// XXX Move this to Model.
		//==========================================================================================
//		ModuleGeneratorTypeData editedModuleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
//		
//		TreePath treePath = this.searchModulereeNode(editedModuleGeneratorTypeData);
		
//		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			
		this.setSelectionPath(selectionTreePath);
		
		this.modulesTreeModel.setSelectionPath(selectionTreePath);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #modulesTreeModel}.
	 */
	public ModulesTreeModel getModulesTreeModel()
	{
		return this.modulesTreeModel;
	}

	/**
	 * @param treeNode
	 * 			is the Tree-Node.
	 */
	public void setSelectionPath(DefaultMutableTreeNode treeNode)
	{
		//==========================================================================================
		TreePath selectionTreePath = this.modulesTreeModel.searchFolderTreeNode(treeNode);
		
		this.setSelectionPath(selectionTreePath);
		
		//==========================================================================================
	}
	
}
