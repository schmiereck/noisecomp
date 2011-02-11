/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * <p>
 * 	Modul Tree-Path.
 * </p>
 * 
 * @author smk
 * @version <p>17.09.2010:	created, smk</p>
 */
public class ModuleTreePath
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Child Tree-Path.
	 */
	private final ModuleTreePath childTreePath;
	
	/**
	 * Tree-Node.
	 */
	private final DefaultMutableTreeNode treeNode;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * Path by adding Root/ Parent.
	 * 
	 * @param treeNode
	 * 			is the root Tree-Node.
	 * @param childTreePath
	 * 			is a Child Tree-Path.
	 */
	public ModuleTreePath(DefaultMutableTreeNode treeNode,
	                     ModuleTreePath childTreePath)
	{
		this.treeNode = treeNode;
		this.childTreePath = childTreePath;
	}

	/**
	 * Constructor.
	 * 
	 * @param treeNode
	 * 			is a single node path.
	 */
	public ModuleTreePath(DefaultMutableTreeNode treeNode)
	{
		this.treeNode = treeNode;
		this.childTreePath = null;
	}

	/**
	 * @return 
	 * 			returns the {@link #childTreePath}.
	 */
	public ModuleTreePath getChildTreePath()
	{
		return this.childTreePath;
	}

	/**
	 * @return 
	 * 			returns the {@link #treeNode}.
	 */
	public DefaultMutableTreeNode getTreeNode()
	{
		return this.treeNode;
	}

	/**
	 * @return
	 * 			the tree Path.
	 */
	public TreePath createTreePath()
	{
		TreePath treePath = this.createTreePath(null);
		
		return treePath;
	}

	/**
	 * @param parentTreePath
	 * 			is the parent Tree-Path.
	 * @return
	 * 			the tree Path.
	 */
	private TreePath createTreePath(TreePath parentTreePath)
	{
		TreePath retTreePath;
		
		if (parentTreePath != null)
		{
			retTreePath = parentTreePath.pathByAddingChild(this.treeNode);
		}
		else
		{
			retTreePath = new TreePath(this.treeNode);
		}

		if (this.childTreePath != null)
		{
			retTreePath = this.childTreePath.createTreePath(retTreePath);
		}
		
		return retTreePath;
	}
	
}
