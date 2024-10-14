/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulesTree;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;

/**
 * <p>
 * 	Modules Tree-Model.
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
	 * Edited ModuleGenerator-Type Data.
	 */
	private ModuleGeneratorTypeData editedModuleGeneratorTypeData;
	
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
	 * 			returns the {@link #editedModuleGeneratorTypeData}.
	 */
	public ModuleGeneratorTypeData getEditedModuleGeneratorTypeData()
	{
		return this.editedModuleGeneratorTypeData;
	}

	/**
	 * @param editedModuleGeneratorTypeData 
	 * 			to set {@link #editedModuleGeneratorTypeData}.
	 */
	public void setEditedModuleGeneratorTypeData(ModuleGeneratorTypeData editedModuleGeneratorTypeData)
	{
		//==========================================================================================
//		ModuleGeneratorTypeData editedModuleGeneratorTypeData = this.getEditedModuleGeneratorTypeData();
		
		TreePath selectionTreePath = this.searchModuleTreeNode(editedModuleGeneratorTypeData);
			
		this.setSelectionPath(selectionTreePath);
		
		//------------------------------------------------------------------------------------------
		this.editedModuleGeneratorTypeData = editedModuleGeneratorTypeData;
		
		for (EditModuleChangedListener editModuleChangedListener : this.editModuleChangedListeners)
		{
			editModuleChangedListener.notifyEditModuleChanged(this,
			                                                  selectionTreePath);
		}
		//==========================================================================================
	}

	/**
	 * @param moduleGeneratorTypeData
	 * 			is the ModuleGenerator-Type.
	 * @return
	 * 			the tree path or <code>null</code> if the ModuleGenerator-Type is not found.
	 */
	public TreePath searchModuleTreeNode(final ModuleGeneratorTypeData moduleGeneratorTypeData) {
		//==========================================================================================
		final TreePath treePath;
		
		if (moduleGeneratorTypeData != null) {
//			DefaultTreeModel treeModel = (DefaultTreeModel)this.getModel();

			final DefaultMutableTreeNode rootTreeNode = (DefaultMutableTreeNode)this.treeModel.getRoot();

			final ModuleTreePath moduleTreePath = this.searchModuleTreeNode(rootTreeNode, moduleGeneratorTypeData);
			
			treePath = moduleTreePath.createTreePath();
		} else {
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
	 * @param moduleGeneratorTypeData
	 * 			is the generator.
	 * @return
	 * 			the path or <code>null</code>.
	 */
	private ModuleTreePath searchModuleTreeNode(DefaultMutableTreeNode rootTreeNode,
												ModuleGeneratorTypeData moduleGeneratorTypeData) {
		//==========================================================================================
		ModuleTreePath retTreePath;
		
		Object userObject = rootTreeNode.getUserObject();
		
		if (moduleGeneratorTypeData == userObject) {
			retTreePath = new ModuleTreePath(rootTreeNode);//rootTreePath.pathByAddingChild(userObject);
		} else {
			retTreePath = null;
			
			for (int childPos = 0; childPos < rootTreeNode.getChildCount(); childPos++) {
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)rootTreeNode.getChildAt(childPos);
				
				ModuleTreePath treePath = this.searchModuleTreeNode(treeNode, moduleGeneratorTypeData);
				
				if (treePath != null) {
					retTreePath = new ModuleTreePath(rootTreeNode, treePath);
					break;
				}
			}
		}
		//==========================================================================================
		return retTreePath;
	}

	/**
	 * @param folderTreeNode
	 * 			is the ModuleGenerator-Type.
	 * @return
	 * 			the tree path or <code>null</code> if the ModuleGenerator-Type is not found.
	 */
	public TreePath searchFolderTreeNode(DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		TreePath treePath;
		
		if (folderTreeNode != null)
		{
//			DefaultTreeModel treeModel = (DefaultTreeModel)this.getModel();
			
			DefaultMutableTreeNode rootTreeNode = (DefaultMutableTreeNode)this.treeModel.getRoot();
			
			ModuleTreePath moduleTreePath = this.searchFolderTreeNode(rootTreeNode, folderTreeNode);
			
			treePath = moduleTreePath.createTreePath();
		}
		else
		{
			treePath = null;
		}
		
		//==========================================================================================
		return treePath;
	}

	/**
	 * Search given Folder starting from given node.
	 * 
	 * @param rootTreeNode
	 * 			is the node.
	 * @param folderTreeNode
	 * 			is the Folder.
	 * @return
	 * 			the path or <code>null</code>.
	 */
	private ModuleTreePath searchFolderTreeNode(DefaultMutableTreeNode rootTreeNode, 
	                                            DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		ModuleTreePath retTreePath;
		
		if (folderTreeNode == rootTreeNode)
		{
			retTreePath = new ModuleTreePath(rootTreeNode);//rootTreePath.pathByAddingChild(userObject);
		}
		else
		{
			retTreePath = null;
			
			for (int childPos = 0; childPos < rootTreeNode.getChildCount(); childPos++)
			{
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)rootTreeNode.getChildAt(childPos);
				
				ModuleTreePath treePath = this.searchFolderTreeNode(treeNode, folderTreeNode);
				
				if (treePath != null)
				{
					retTreePath = new ModuleTreePath(rootTreeNode, treePath);
					break;
				}
			}
		}
		
		//==========================================================================================
		return retTreePath;
	}

	/**
	 * @param parentTreeNode
	 * 			is the parent node.
	 * @param folderName
	 * 			is the child folder name.
	 * @return
	 * 			the folde Tree-Node.<br/>
	 * 			<code>null</code> if no folder with given name found.
	 */
	public DefaultMutableTreeNode searchFolderTreeNode(DefaultMutableTreeNode parentTreeNode, String folderName)
	{
		//==========================================================================================
		DefaultMutableTreeNode retFolderTreeNode;
		
		retFolderTreeNode = null;
		
		for (int childPos = 0; childPos < parentTreeNode.getChildCount(); childPos++)
		{
			TreeNode treeNode = parentTreeNode.getChildAt(childPos);
			
			// Folder?
			if (treeNode instanceof DefaultMutableTreeNode)
			{
				DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)treeNode;
				
				Object userObject = folderTreeNode.getUserObject();
				
				if (folderName.equals(userObject) == true)
				{
					retFolderTreeNode = folderTreeNode;
					break;
				}
			}
		}
		
		//==========================================================================================
		return retFolderTreeNode;
	}

	/**
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/File/folder1/folder2/"</code>.
	 * @return
	 * 			the folder Tree-Node.
	 */
	public DefaultMutableTreeNode searchModuleTreeNode(String folderPath)
	{
		//==========================================================================================
		DefaultMutableTreeNode retTreeNode;
		
		String[] folderNames = folderPath.split("\\/");
		
		DefaultTreeModel treeModel = this.getTreeModel();
		
		retTreeNode = (DefaultMutableTreeNode)treeModel.getRoot();
		
		for (int folderPos = 2; folderPos < folderNames.length; folderPos++)
		{
			String folderName = folderNames[folderPos];
			
			retTreeNode = 
				this.searchFolderTreeNode(retTreeNode, folderName);
		}
		
		//==========================================================================================
		return retTreeNode;
	}

	/**
	 * @param folderTreeNode
	 * 			is the Folder Tree-Node.
	 * @return
	 * 			the Folder-Path in format <code>"/folder1/folder2/"</code>.
	 */
	public String makeFolderPath(DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		String folderPath = "/";
		
		DefaultMutableTreeNode parenTreeNode = (DefaultMutableTreeNode)folderTreeNode;

		while (parenTreeNode != null)
		{
			folderPath = "/" + parenTreeNode.getUserObject() + folderPath;

			parenTreeNode = (DefaultMutableTreeNode)parenTreeNode.getParent();
		}
		
		//==========================================================================================
		return folderPath;
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
	 * @param parentFolderTreeNode
	 * 			is the  parentFolderTreeNode.
	 * @param moduleGeneratorTypeData
	 * 			is the new ModuleGenerator-Type Data.
	 * @return
	 * 			the Module Tree-Node.
	 */
	public DefaultMutableTreeNode insertModule(final DefaultMutableTreeNode parentFolderTreeNode, 
	                                           final ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
//		DefaultTreeModel treeModel = (DefaultTreeModel)this.modulesTreeView.getModel();
		
		DefaultMutableTreeNode newModulereeNode = new DefaultMutableTreeNode(moduleGeneratorTypeData);
	
//		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
//		DefaultMutableTreeNode parentTreeNode = (DefaultMutableTreeNode)this.selectionPath.getLastPathComponent();
//		DefaultMutableTreeNode parentTreeNode = (DefaultMutableTreeNode)this.treeModel.getRoot();
//		DefaultMutableTreeNode parentTreeNode = this.modulesTreeNode;
//		int parentChildCount = parentTreeNode.getChildCount();

		int parentChildCount = parentFolderTreeNode.getChildCount();
		
		this.treeModel.insertNodeInto(newModulereeNode, parentFolderTreeNode, parentChildCount);

		//==========================================================================================
		return newModulereeNode;
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
	public void removeModuleodes()
	{
		this.modulesTreeNode.removeAllChildren();
	}

//	/**
//	 * @param modulereeNode
//	 * 			is the module TreeNode to add to {@link #modulesTreeNode}.
//	 */
//	public void addModuleNode(DefaultMutableTreeNode modulereeNode)
//	{
//		this.modulesTreeNode.add(modulereeNode);
//	}

	/**
	 * @param generatorTreeNode
	 * 			is the generator TreeNode to add to {@link #generatorsTreeNode}..
	 */
	public void addGeneratoreNode(final DefaultMutableTreeNode generatorTreeNode) {
		this.generatorsTreeNode.add(generatorTreeNode);
	}

	/**
	 * @param folderTreeNode
	 * 			is the selected folder Tree-Node.
	 * @param generatorTreeNode
	 * 			is the generator TreeNode to add to {@link #generatorsTreeNode}..
	 */
	public void addGeneratoreNode(DefaultMutableTreeNode folderTreeNode, final DefaultMutableTreeNode generatorTreeNode) {
		this.treeModel.insertNodeInto(generatorTreeNode, folderTreeNode, 0);
		//this.generatorsTreeNode.add(generatorTreeNode);
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
	public void addModuleNode(DefaultMutableTreeNode folderTreeNode, 
	                          DefaultMutableTreeNode newFolderTreeNode)
	{
		this.treeModel.insertNodeInto(newFolderTreeNode, folderTreeNode, 0);
		//folderTreeNode.add(newFolderTreeNode);
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

	/**
	 * @param cutFolderTreeNode
	 * 			is the cut folder Tree-Node.
	 * @param pasteFolderTreeNode
	 * 			is the paste folder Tree-Node.
	 */
	public void moveFolder(DefaultMutableTreeNode cutFolderTreeNode, 
	                       DefaultMutableTreeNode pasteFolderTreeNode)
	{
		//==========================================================================================
		pasteFolderTreeNode.add(cutFolderTreeNode);
		
		this.treeModel.reload();
		
		//==========================================================================================
	}
	
	/**
	 * @param cutModuleTreeNode
	 * 			is the cut module Tree-Node.
	 * @param pasteFolderTreeNode
	 * 			is the paste folder Tree-Node.
	 */
	public void moveModule(DefaultMutableTreeNode cutModuleTreeNode, 
	                       DefaultMutableTreeNode pasteFolderTreeNode)
	{
		//==========================================================================================
		pasteFolderTreeNode.add(cutModuleTreeNode);
		
		this.treeModel.reload();
		
		//==========================================================================================
	}


	/**
	 * @param parenTreeNode
	 * 			is the Tree-Node.
	 */
	public void deleteModule(DefaultMutableTreeNode moduleTreeNode)
	{
		//==========================================================================================
		moduleTreeNode.removeFromParent();
		
		this.treeModel.reload();
		
		//==========================================================================================
	}

	/**
	 * @param folderTreeNode
	 * 			is the Tree-Node.
	 */
	public void deleteFolder(DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		folderTreeNode.removeFromParent();
		
		this.treeModel.reload();
		
		//==========================================================================================
	}
}
