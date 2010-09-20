/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
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
		this.modulesTreeModel = new ModulesTreeModel();
		this.modulesTreeView = new ModulesTreeView(this.modulesTreeModel);
		
		createNodes(this.modulesTreeView);
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeModel.addEditModuleChangedListener(this.modulesTreeView);
		
		//==========================================================================================
	}
	
	/**
	 * @param tree
	 * 			is the Tree.
	 */
	private static void createNodes(JTree tree)
	{
		DefaultMutableTreeNode modulesTreeNode = new DefaultMutableTreeNode("Modules");

		{
			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode("Modul 1");
			modulesTreeNode.add(modulTreeNode);
			{
				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator A");
				modulTreeNode.add(generatorTreeNode);
			}
			{
				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator B");
				modulTreeNode.add(generatorTreeNode);
			}
		}
		
		{
			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode("Modul 2");
			modulesTreeNode.add(modulTreeNode);
			{
				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator C");
				modulTreeNode.add(generatorTreeNode);
			}
			{
				DefaultMutableTreeNode generatorTreeNode = new DefaultMutableTreeNode("Generator D");
				modulTreeNode.add(generatorTreeNode);
			}
		}
		
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();

		for (GeneratorTypeData generatorTypeData : generatorTypes)
		{
			//benutzt toString() für das Label, also eine eigene Funktion dafür schreiben
			DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode(generatorTypeData);
			modulesTreeNode.add(modulTreeNode);
		}
		
		//==========================================================================================
		DefaultTreeModel treeModel = new DefaultTreeModel(modulesTreeNode);

		tree.setModel(treeModel);
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
//		ModulGeneratorTypeData editedModulGeneratorTypeData = this.appModel.getEditedModulGeneratorTypeData();
		
		TreePath treePath = this.modulesTreeView.searchModulTreeNode(editedModulGeneratorTypeData);
		
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
		
		DefaultTreeModel treeModel = (DefaultTreeModel)this.modulesTreeView.getModel();
		
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
