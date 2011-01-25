/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;

/**
 * <p>
 * 	Modules Tree Model.
 * </p>
 * 
 * @author smk
 * @version <p>14.09.2010:	created, smk</p>
 */
public class ModulesTreeModel
{
	//**********************************************************************************************
	// Fields:

	private final DefaultTreeModel treeModel;
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Tree selection Path.
	 */
	private TreePath selectionPath = null;
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Edited Modul-Generator-Type Data.
	 */
	private ModulGeneratorTypeData editedModulGeneratorTypeData;
	
	private List<EditModuleChangedListener> editModuleChangedListeners = new Vector<EditModuleChangedListener>();

	//----------------------------------------------------------------------------------------------
	private final DefaultMutableTreeNode generatorsTreeNode;

	private final DefaultMutableTreeNode modulesTreeNode;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param treeModel
	 * 			is the tree Model.
	 */
	public ModulesTreeModel(final DefaultTreeModel treeModel,
	                        final DefaultMutableTreeNode generatorsTreeNode,
	                        final DefaultMutableTreeNode modulesTreeNode)
	{
		//==========================================================================================
		this.treeModel = treeModel;
		
		this.generatorsTreeNode = generatorsTreeNode;
		this.modulesTreeNode = modulesTreeNode;
		
		//------------------------------------------------------------------------------------------
		DefaultMutableTreeNode fileTreeNode = (DefaultMutableTreeNode)this.treeModel.getRoot();
		
		fileTreeNode.add(generatorsTreeNode);
		fileTreeNode.add(modulesTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #editedModulGeneratorTypeData}.
	 */
	public ModulGeneratorTypeData getEditedModulGeneratorTypeData()
	{
		return this.editedModulGeneratorTypeData;
	}

	/**
	 * @param editedModulGeneratorTypeData 
	 * 			to set {@link #editedModulGeneratorTypeData}.
	 */
	public void setEditedModulGeneratorTypeData(ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		//==========================================================================================
//		ModulGeneratorTypeData editedModulGeneratorTypeData = this.getEditedModulGeneratorTypeData();
		
		TreePath selectionTreePath = this.searchModulTreeNode(editedModulGeneratorTypeData);
			
		this.setSelectionPath(selectionTreePath);
		
		//------------------------------------------------------------------------------------------
		this.editedModulGeneratorTypeData = editedModulGeneratorTypeData;
		
		for (EditModuleChangedListener editModuleChangedListener : this.editModuleChangedListeners)
		{
			editModuleChangedListener.notifyEditModulChanged(this,
			                                                 selectionTreePath);
		}
		//==========================================================================================
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the Modul-Generator-Type.
	 * @return
	 * 			the tree path or <code>null</code> if the Modul-Generator-Type is not found.
	 */
	public TreePath searchModulTreeNode(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		TreePath treePath;
		
		if (modulGeneratorTypeData != null)
		{
//			DefaultTreeModel treeModel = (DefaultTreeModel)this.getModel();
			
			DefaultMutableTreeNode rootTreeNode = (DefaultMutableTreeNode)this.treeModel.getRoot();
			
			ModulTreePath modulTreePath = this.searchModulTreeNode(rootTreeNode, modulGeneratorTypeData);
			
			treePath = modulTreePath.createTreePath();
		}
		else
		{
			treePath = null;
		}
		
		//==========================================================================================
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
		//==========================================================================================
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
		
		//==========================================================================================
		return retTreePath;
	}

	/**
	 * @param editModuleChangedListener 
	 * 			to add to {@link #editModuleChangedListeners}.
	 */
	public void addEditModuleChangedListener(EditModuleChangedListener editModuleChangedListener)
	{
		this.editModuleChangedListeners.add(editModuleChangedListener);
	}

	/**
	 * @return 
	 * 			returns the {@link #treeModel}.
	 */
	public DefaultTreeModel getTreeModel()
	{
		return this.treeModel;
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the new Modul-Generator-Type Data.
	 */
	public void insertModul(final ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
//		DefaultTreeModel treeModel = (DefaultTreeModel)this.modulesTreeView.getModel();
		
		DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode(modulGeneratorTypeData);
	
//		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
//		DefaultMutableTreeNode parentTreeNode = (DefaultMutableTreeNode)this.selectionPath.getLastPathComponent();
//		DefaultMutableTreeNode parentTreeNode = (DefaultMutableTreeNode)this.treeModel.getRoot();
		DefaultMutableTreeNode parentTreeNode = this.modulesTreeNode;
	
		int parentChildCount = parentTreeNode.getChildCount();
		
		this.treeModel.insertNodeInto(modulTreeNode, parentTreeNode, parentChildCount);

		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #selectionPath}.
	 */
	public TreePath getSelectionPath()
	{
		return this.selectionPath;
	}

	/**
	 * @param selectionPath 
	 * 			to set {@link #selectionPath}.
	 */
	public void setSelectionPath(TreePath selectionPath)
	{
		this.selectionPath = selectionPath;
	}

	/**
	 * 
	 */
	public void removeGeneratorNodes()
	{
		this.generatorsTreeNode.removeAllChildren();
	}

	/**
	 * 
	 */
	public void removeModulNodes()
	{
		this.modulesTreeNode.removeAllChildren();
	}

	/**
	 * @param modulTreeNode
	 * 			is the modul TreeNode to add to {@link #modulesTreeNode}.
	 */
	public void addModuleNode(DefaultMutableTreeNode modulTreeNode)
	{
		this.modulesTreeNode.add(modulTreeNode);
	}

	/**
	 * @param generatorTreeNode
	 * 			is the generator TreeNode to add to {@link #generatorsTreeNode}..
	 */
	public void addGeneratoreNode(DefaultMutableTreeNode generatorTreeNode)
	{
		this.generatorsTreeNode.add(generatorTreeNode);
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorsTreeNode}.
	 */
	public DefaultMutableTreeNode getGeneratorsTreeNode()
	{
		return this.generatorsTreeNode;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulesTreeNode}.
	 */
	public DefaultMutableTreeNode getModulesTreeNode()
	{
		return this.modulesTreeNode;
	}

	/**
	 * @param folderTreeNode
	 * 			is the selected folder Tree-Node.
	 * @param newFolderTreeNode
	 * 			is the new folder Tree-Node.
	 */
	public void addFolderNode(DefaultMutableTreeNode folderTreeNode, 
	                          DefaultMutableTreeNode newFolderTreeNode)
	{
		this.treeModel.insertNodeInto(newFolderTreeNode, folderTreeNode, 0);
		//folderTreeNode.add(newFolderTreeNode);
	}

}
