/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
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
		DefaultTreeModel treeModel = this.createTreeModel();
		
		this.modulesTreeModel = new ModulesTreeModel(treeModel);
		this.modulesTreeView = new ModulesTreeView(this.modulesTreeModel);
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeModel.addEditModuleChangedListener(this.modulesTreeView);
		
		//==========================================================================================
	}
	
	/**
	 * @param tree
	 * 			is the Tree.
	 */
	private DefaultTreeModel createTreeModel()
	{
		//==========================================================================================
		DefaultMutableTreeNode modulesTreeNode = new DefaultMutableTreeNode("Modules");

		//------------------------------------------------------------------------------------------
		DefaultTreeModel treeModel = new DefaultTreeModel(modulesTreeNode);

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
		
		DefaultMutableTreeNode modulesTreeNode = (DefaultMutableTreeNode)treeModel.getRoot();
		
		modulesTreeNode.removeAllChildren();

//		//------------------------------------------------------------------------------------------
//		{
//			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode("Modul 1");
//			modulesTreeNode.add(modulTreeNode);
//			{
//				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator A");
//				modulTreeNode.add(generatorTreeNode);
//			}
//			{
//				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator B");
//				modulTreeNode.add(generatorTreeNode);
//			}
//		}
//		
//		{
//			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode("Modul 2");
//			modulesTreeNode.add(modulTreeNode);
//			{
//				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator C");
//				modulTreeNode.add(generatorTreeNode);
//			}
//			{
//				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator D");
//				modulTreeNode.add(generatorTreeNode);
//			}
//		}
//		
		//------------------------------------------------------------------------------------------
//		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();

		for (GeneratorTypeData generatorTypeData : generatorTypes)
		{
			//benutzt toString() für das Label, also eine eigene Funktion dafür schreiben
			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode(generatorTypeData);
			modulesTreeNode.add(modulTreeNode);
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
		
		TreePath treePath = this.modulesTreeView.searchModulTreeNode(editedModulGeneratorTypeData);
		
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

}
