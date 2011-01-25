/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.swingView.appController.AppController;

/**
 * <p>
 * 	Modules Tree Controller.
 * </p>
 * 
 * @author smk
 * @version <p>14.09.2010:	created, smk</p>
 */
public class ModulesTreeController
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Modules Tree View.
	 */
	private final ModulesTreeView modulesTreeView;

	/**
	 * Modules Tree Model.
	 */
	private final ModulesTreeModel modulesTreeModel;
	
	/**
	 * App Controller.
	 */
	private final AppController appController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 */
	public ModulesTreeController(final AppController appController)
	{
		//==========================================================================================
		this.appController = appController;
		
		DefaultTreeModel treeModel = this.createTreeModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		DefaultMutableTreeNode generatorsTreeNode = new DefaultMutableTreeNode("Generators");

		DefaultMutableTreeNode modulesTreeNode = new DefaultMutableTreeNode("Modules");

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.modulesTreeModel = new ModulesTreeModel(treeModel,
		                                             generatorsTreeNode,
		                                             modulesTreeNode);
		
		this.modulesTreeView = new ModulesTreeView(this.modulesTreeModel);
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeModel.addEditModuleChangedListener(this.modulesTreeView);
		
		//------------------------------------------------------------------------------------------
		{
			// Add listener to components that can bring up popup menus.
			MouseListener popupListener = new ModulTreeMouseListener(this,
			                                                         this.modulesTreeView);
			//output.addMouseListener(popupListener);
			//menuBar.addMouseListener(popupListener);
			this.modulesTreeView.addMouseListener(popupListener);
		}

		//==========================================================================================
	}
	
	/**
	 * Create Tree-Model.
	 * 
	 * @return
	 * 			the Tree-Model.
	 */
	private DefaultTreeModel createTreeModel()
	{
		//==========================================================================================
		DefaultMutableTreeNode fileTreeNode = new DefaultMutableTreeNode("File");

		//------------------------------------------------------------------------------------------
		DefaultTreeModel treeModel = new DefaultTreeModel(fileTreeNode);
		
		//==========================================================================================
		return treeModel;
	}
	
	/**
	 * @param generatorTypes
	 * 			are the  generator Types.
	 */
	public void addGeneratorTypes(List<GeneratorTypeData> generatorTypes)
	{
		//==========================================================================================
		DefaultTreeModel treeModel = this.modulesTreeModel.getTreeModel();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeModel.removeGeneratorNodes();
		this.modulesTreeModel.removeModulNodes();

		//------------------------------------------------------------------------------------------
		for (GeneratorTypeData generatorTypeData : generatorTypes)
		{
			// Uses generator.toString() to view Label, so write a function for this.
			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode(generatorTypeData);
			
			if (generatorTypeData instanceof ModulGeneratorTypeData)
			{			
				this.modulesTreeModel.addModuleNode(modulTreeNode);
			}
			else
			{
				this.modulesTreeModel.addGeneratoreNode(modulTreeNode);
			}
		}
		
		//------------------------------------------------------------------------------------------
		treeModel.reload();
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #modulesTreeView}.
	 */
	public ModulesTreeView getModulesTreeView()
	{
		return this.modulesTreeView;
	}

	/**
	 * Update Edited Modul-Tree-Entry.
	 */
	public void updateEditedModulTreeEntry(ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		//==========================================================================================
		DefaultTreeModel treeModel = (DefaultTreeModel)this.modulesTreeView.getModel();

		//==========================================================================================
		DefaultMutableTreeNode treeNode;
		
//		ModulGeneratorTypeData editedModulGeneratorTypeData = this.appModel.getEditedModulGeneratorTypeData();
		
		TreePath treePath = this.modulesTreeModel.searchModulTreeNode(editedModulGeneratorTypeData);
		
		if (treePath != null)
		{
			treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
		}
		else
		{
			treeNode = null;
		}
		
		//------------------------------------------------------------------------------------------
		treeModel.nodeChanged(treeNode);
		//		treeModel.reload(treeNode);
				
		//		this.modulesTree.repaint();
		
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
	 * Changed model notfier because of AppModelChanged.
	 * 
	 */
	public void doInsertModul()
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		final ModulGeneratorTypeData modulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();
	
		//modulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		modulGeneratorTypeData.setGeneratorTypeName("Module (new)");
	
		soundService.addGeneratorType(modulGeneratorTypeData);
		
		//--------------------------------------------------------------------------
		this.modulesTreeModel.insertModul(modulGeneratorTypeData);
		
		//--------------------------------------------------------------------------
		this.modulesTreeView.notifyEditModulListeners(modulGeneratorTypeData);

		//==========================================================================================
	}

	/**
	 * 
	 */
	public void doInsertFolder()
	{
		//==========================================================================================
		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
		
		if (treeNode != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//Object userObject = treeNode.getUserObject();
			
			DefaultMutableTreeNode newFolderTreeNode = new DefaultMutableTreeNode("Folder (new)");

			DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)treeNode;

			this.modulesTreeModel.addFolderNode(folderTreeNode,
			                                    newFolderTreeNode);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
		//==========================================================================================
	}

	/**
	 * 
	 */
	public void doCut()
	{
		//==========================================================================================
		// TODO Auto-generated method stub
		
		//==========================================================================================
	}

	/**
	 * 
	 */
	public void doPaste()
	{
		//==========================================================================================
		// TODO Auto-generated method stub
		
		//==========================================================================================
	}

	/**
	 * 
	 */
	public void doDelete()
	{
		//==========================================================================================
		// TODO Auto-generated method stub
		
		//==========================================================================================
	}

	/**
	 * Do Rename Module.
	 */
	public void doRename()
	{
		//==========================================================================================
		this.appController.doRenameModule();
		
		//==========================================================================================
	}

}
