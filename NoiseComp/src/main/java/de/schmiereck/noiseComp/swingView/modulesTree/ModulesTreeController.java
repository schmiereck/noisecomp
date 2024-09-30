/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulesTree;

import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModuleGenerator;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appView.AppView;

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
			MouseListener popupListener = new ModuleTreeMouseListener(this,
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
	 * 			are the generator Types.
	 */
	public void addGeneratorTypes(List<GeneratorTypeData> generatorTypes)
	{
		//==========================================================================================
		this.modulesTreeModel.removeGeneratorNodes();
		this.modulesTreeModel.removeModuleodes();

		//------------------------------------------------------------------------------------------
		Iterator<GeneratorTypeData> generatorTypesIterator = generatorTypes.iterator();
		
		this.importGeneratorTypes(generatorTypesIterator);
		
		//==========================================================================================
	}

	/**
	 * @param generatorTypesIterator
	 * 			is the generator Types iterator.
	 */
	public void importGeneratorTypes(Iterator<GeneratorTypeData> generatorTypesIterator)
	{
		//==========================================================================================
		DefaultTreeModel treeModel = this.modulesTreeModel.getTreeModel();
		
		//------------------------------------------------------------------------------------------
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = (GeneratorTypeData)generatorTypesIterator.next();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			String folderPath = generatorTypeData.getFolderPath();
			
			DefaultMutableTreeNode insertTreeNode = this.modulesTreeModel.getModulesTreeNode();
			
			// Folder-Path?
			if (folderPath != null)
			{
				String folderNames[] = folderPath.split("\\/");
				
				for (int folderPos = 3; folderPos < folderNames.length; folderPos++)
				{
					String folderName = folderNames[folderPos];
					
					DefaultMutableTreeNode folderTreeNode = this.modulesTreeModel.searchFolderTreeNode(insertTreeNode, folderName);
					
					// Not found?
					if (folderTreeNode == null)
					{
						DefaultMutableTreeNode newFolderTreeNode = new DefaultMutableTreeNode(folderName);
						
						this.modulesTreeModel.addModuleNode(insertTreeNode, 
						                                    newFolderTreeNode);
						
						insertTreeNode = newFolderTreeNode;
					}
					else
					{
						insertTreeNode = folderTreeNode;
					}
				}
			}

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			Class< ? extends Generator> generatorClass = generatorTypeData.getGeneratorClass();
			
			// Module?
			if (generatorClass != null)
			{
				// Uses generator.toString() to view Label, so write a function for this.
				DefaultMutableTreeNode modulereeNode = new DefaultMutableTreeNode(generatorTypeData);
				
				if (generatorTypeData instanceof ModuleGeneratorTypeData)
				{			
//					String folderPath2 = generatorTypeData.getFolderPath();
					
					this.modulesTreeModel.addModuleNode(insertTreeNode,
					                                    modulereeNode);
				}
				else
				{
					this.modulesTreeModel.addGeneratoreNode(modulereeNode);
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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
	 * Update Edited ModuleTree-Entry.
	 */
	public void updateEditedModulereeEntry(ModuleGeneratorTypeData editedModuleGeneratorTypeData)
	{
		//==========================================================================================
		DefaultTreeModel treeModel = (DefaultTreeModel)this.modulesTreeView.getModel();

		//==========================================================================================
		DefaultMutableTreeNode treeNode;
		
//		ModuleGeneratorTypeData editedModuleGeneratorTypeData = this.appModel.getEditedModuleGeneratorTypeData();
		
		TreePath treePath = this.modulesTreeModel.searchModulereeNode(editedModuleGeneratorTypeData);
		
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

		//treeModel.reload(treeNode);
		//this.modulesTree.repaint();
		
		//==========================================================================================
	}

	/**
	 * Update Edited Folder-Tree-Entry.
	 */
	public void updateEditedFolderTreeNode(DefaultMutableTreeNode editedFolderTreeNode,
	                                       String folderName)
	{
		//==========================================================================================
		DefaultTreeModel treeModel = (DefaultTreeModel)this.modulesTreeView.getModel();

		//==========================================================================================
		DefaultMutableTreeNode treeNode;
		
		TreePath treePath = this.modulesTreeModel.searchFolderTreeNode(editedFolderTreeNode);
		
		if (treePath != null)
		{
			treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();

			editedFolderTreeNode.setUserObject(folderName);
		}
		else
		{
			treeNode = null;
		}
		
		//------------------------------------------------------------------------------------------
		treeModel.nodeChanged(treeNode);

		//treeModel.reload(treeNode);
		//this.modulesTree.repaint();
		
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
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 */
	public void doInsertModule(String folderPath)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		final ModuleGeneratorTypeData moduleGeneratorTypeData = 
			ModuleGenerator.createModuleGeneratorTypeData(folderPath);
	
		//moduleeneratorTypeData.setIsMainModuleGeneratorType(true);
		
		moduleGeneratorTypeData.setGeneratorTypeName("Module (new)");
	
		soundService.addGeneratorType(moduleGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		DefaultMutableTreeNode folderTreeNode = this.modulesTreeModel.searchModuleTreeNode(folderPath);
		
		DefaultMutableTreeNode modulereeNode = 
			this.modulesTreeModel.insertModule(folderTreeNode,
			                                   moduleGeneratorTypeData);
		
		this.modulesTreeView.setSelectionPath(modulereeNode);
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeView.notifyEditModuleisteners(moduleGeneratorTypeData);

		//==========================================================================================
	}

	/**
	 * Do Create Folder.
	 */
	public void doCreateFolder()
	{
		//==========================================================================================
		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
		DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

		this.appController.doCreateFolder(folderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * Do cut Folder or Module.
	 */
	public void doCut()
	{
		//==========================================================================================
		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
		DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

		this.appController.doCutModule(folderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * Do paste Folder or Module.
	 */
	public void doPaste()
	{
		//==========================================================================================
		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
		DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

		this.appController.doPasteModule(folderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * Delete selected module or folder.<br/>
	 * Alert if Module is used by other modules or it is the Main-Module.
	 */
	public void doDelete()
	{
		//==========================================================================================
		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
		
		Object userObject = treeNode.getUserObject();
		
		// Delete Module?
		if (userObject instanceof ModuleGeneratorTypeData)
		{
			this.doDeleteModule(treeNode);
		}
		else
		{
			// Delete Folder:
			
			this.doDeleteFolder(treeNode);
		}
		//==========================================================================================
	}

	/**
	 * Do Rename Module.
	 * 
	 */
	public void doRename()
	{
		//==========================================================================================
		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
		
		DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

		this.appController.doRenameModule(folderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * Do Create Folder.
	 * 
	 * @param editedFolderTreeNode
	 * 			is the edited Folder Tree-Node.
	 * @param folderName
	 * 			is the folder name.
	 */
	public void doCreateFolder(DefaultMutableTreeNode editedFolderTreeNode,
	                           String folderName)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		String folderPath = this.modulesTreeModel.makeFolderPath(editedFolderTreeNode);
		
		soundService.addFolder(folderPath,
		                       folderName);

		//------------------------------------------------------------------------------------------
//		TreePath selectionPath = this.modulesTreeView.getSelectionPath();
//		
//		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
//		
//		if (treeNode != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//Object userObject = treeNode.getUserObject();
			
			DefaultMutableTreeNode newFolderTreeNode = new DefaultMutableTreeNode(folderName);

			this.modulesTreeModel.addModuleNode(editedFolderTreeNode,
			                                    newFolderTreeNode);
			
			this.modulesTreeView.setSelectionPath(newFolderTreeNode);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
		//==========================================================================================
	}

	/**
	 * @param cutFolderTreeNode
	 * 			is the cut folder Tree-Node.
	 * @param pasteFolderTreeNode
	 * 			is the paste folder Tree-Node.
	 */
	public void doPasteFolder(DefaultMutableTreeNode cutFolderTreeNode, 
	                          DefaultMutableTreeNode pasteFolderTreeNode)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		// Update Service:
		
		String cutFolderPath = this.modulesTreeModel.makeFolderPath(cutFolderTreeNode);
		String pasteFolderPath = this.modulesTreeModel.makeFolderPath(pasteFolderTreeNode);
		
		soundService.moveFolder(cutFolderPath,
		                        pasteFolderPath);
		
		//------------------------------------------------------------------------------------------
		// Update View:
		
		this.modulesTreeModel.moveFolder(cutFolderTreeNode, pasteFolderTreeNode);

		this.modulesTreeView.setSelectionPath(cutFolderTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @param cutModuleTreeNode
	 * 			is the cut module Tree-Node.
	 * @param pasteFolderTreeNode
	 * 			is the paste folder Tree-Node.
	 * @param moduleGeneratorTypeData
	 * 			is the Module.
	 */
	public void doPasteModule(DefaultMutableTreeNode cutModuleTreeNode, 
	                          DefaultMutableTreeNode pasteFolderTreeNode,
	                          ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		// Update Service:
		
		DefaultMutableTreeNode cutFolderTreeNode = (DefaultMutableTreeNode)cutModuleTreeNode.getParent();
		
		String cutFolderPath = this.modulesTreeModel.makeFolderPath(cutFolderTreeNode);
		String pasteFolderPath = this.modulesTreeModel.makeFolderPath(pasteFolderTreeNode);
		
		soundService.moveModule(cutFolderPath,
		                        pasteFolderPath,
		                        moduleGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		// Update View:
		
		this.modulesTreeModel.moveFolder(cutModuleTreeNode, pasteFolderTreeNode);

		this.modulesTreeView.setSelectionPath(cutModuleTreeNode);
		
		//==========================================================================================
	}

	/**
	 * @param cutTreeNode
	 * 			is the cut module od folder Tree-Node.
	 * @param pasteFolderTreeNode
	 * 			is the paste folder Tree-Node.
	 */
	public void pasteModule(DefaultMutableTreeNode cutTreeNode, 
	                        DefaultMutableTreeNode pasteFolderTreeNode)
	{
		//==========================================================================================
		Object userObject = cutTreeNode.getUserObject();
		
		// Paste Module?
		if (userObject instanceof ModuleGeneratorTypeData)
		{
			ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)userObject;
			
			this.doPasteModule(cutTreeNode,
			                   pasteFolderTreeNode,
			                   moduleGeneratorTypeData);
		}
		else
		{
			// Paste Folder:
			
			this.doPasteFolder(cutTreeNode,
			                   pasteFolderTreeNode);
		}
		//==========================================================================================
	}

	/**
	 * Delete module.<br/>
	 * Alert if Module is used by other modules or it is the Main-Module.
	 * 
	 * @param moduleTreeNode
	 * 			is the module Tree-Node.
	 */
	private void doDeleteModule(DefaultMutableTreeNode moduleTreeNode)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		Object userObject = moduleTreeNode.getUserObject();
		
		ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)userObject;
		
		// Module is used by other modules?
		if (soundService.checkModuleIsUsed(moduleGeneratorTypeData) == true)
		{
			//--------------------------------------------------------------------------------------
			// Alert Module is used by other modules.
			
			AppView appView = appController.getAppView();
			
			JOptionPane.showMessageDialog(appView, "Module is used by other modules.");
			
			//--------------------------------------------------------------------------------------
		}
		else
		{
			// Module is Main-Module?
			if (moduleGeneratorTypeData.getIsMainModuleGeneratorType() == true)
			{
				//----------------------------------------------------------------------------------
				// Alert Module is main module.
				
				AppView appView = appController.getAppView();
				
				JOptionPane.showMessageDialog(appView, "Module is main module.");
				
				//----------------------------------------------------------------------------------
			}
			else
			{
				//----------------------------------------------------------------------------------
				// Remove Module:
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Service:
				
				soundService.removeGeneratorType(moduleGeneratorTypeData);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// View:
				
				DefaultMutableTreeNode parenTreeNode = (DefaultMutableTreeNode)moduleTreeNode.getParent();
				
				this.modulesTreeModel.deleteModule(moduleTreeNode);
				
				this.modulesTreeView.setSelectionPath(parenTreeNode);
				
				//-----------------------------------------------------------------------------------
			}
		}
		
		//==========================================================================================
	}

	/**
	 * Delete folder.<br/>
	 * Alert if one Module in folder is used by other modules or it is the Main-Module.
	 * 
	 * @param moduleTreeNode
	 * 			is the folder Tree-Node.
	 */
	private void doDeleteFolder(DefaultMutableTreeNode folderTreeNode)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
//		for (int childPos = 0; childPos < folderTreeNode.getChildCount(); childPos++)
//		{
//			DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode)folderTreeNode.getChildAt(childPos);
//		}

		String folderPath = this.modulesTreeModel.makeFolderPath(folderTreeNode);
		
		// Folder is used by other modules?
		if (soundService.checkModuleInFolderIsUsed(folderPath) == true)
		{
			//--------------------------------------------------------------------------------------
			// Alert Module is used by other modules.
			
			AppView appView = appController.getAppView();
			
			JOptionPane.showMessageDialog(appView, "Sub-Module in folder is used by other modules.");
			
			//--------------------------------------------------------------------------------------
		}
		else
		{
			// Module in Folder is Main-Module?
			if (soundService.checkModuleInFolderIsMainModule(folderPath) == true)
			{
				//----------------------------------------------------------------------------------
				// Alert Module is main module.
				
				AppView appView = appController.getAppView();
				
				JOptionPane.showMessageDialog(appView, "Sub-Module in folder is main module.");
				
				//----------------------------------------------------------------------------------
			}
			else
			{
				//----------------------------------------------------------------------------------
				// Remove Module:
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Service:
				
				soundService.removeFolder(folderPath);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// View:
				
				DefaultMutableTreeNode parenTreeNode = (DefaultMutableTreeNode)folderTreeNode.getParent();
				
				this.modulesTreeModel.deleteFolder(folderTreeNode);
				
				this.modulesTreeView.setSelectionPath(parenTreeNode);
				
				//-----------------------------------------------------------------------------------
			}
		}
		
		//==========================================================================================
	}
}
