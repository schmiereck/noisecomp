/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulesTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;

/**
 * <p>
 * 	ModuleTree Mouse-Listener.
 * </p>
 * 
 * see: http://download.oracle.com/javase/tutorial/uiswing/components/menu.html#popupapi
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public class ModuleTreeMouseListener
extends MouseAdapter 
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modules Tree-Model.
	 */
	private final ModulesTreeView modulesTreeView;
	
	/**
	 * Main Modules-Category Popup-Menu.
	 */
	private JPopupMenu modulesCategoryPopupMenu;
	
	/**
	 * Folder Popup-Menu.
	 */
	private JPopupMenu folderPopupMenu;
	
	/**
	 * Module Popup-Menu.
	 */
	private JPopupMenu modulePopupMenu;
	
	/**
	 * Generator Popup-Menu.
	 */
	private JPopupMenu generatorPopupMenu;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulesTreeController
	 * 			is the Modules Tree Controller.
	 * @param modulesTreeView
	 * 			is the Modules Tree View.
	 */
	public ModuleTreeMouseListener(final ModulesTreeController modulesTreeController,
	                               final ModulesTreeView modulesTreeView)
	{
		//==========================================================================================
		this.modulesTreeView = modulesTreeView;
		
		//==========================================================================================
		// Main-Modules-Category, Module and -Folder Popup Menu.
		this.modulesCategoryPopupMenu = new JPopupMenu();
		// Folder Popup-Menu.
		this.folderPopupMenu = new JPopupMenu();
		// Module Popup-Menu.
		this.modulePopupMenu = new JPopupMenu();
		
		{
			ActionListener actionListener =
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						final ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
						
						//--------------------------------------------------------------------------
						TreePath selectionPath = modulesTreeView.getSelectionPath();
						
						DefaultMutableTreeNode folderTreeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						String folderPath = modulesTreeModel.makeFolderPath(folderTreeNode);
						
						modulesTreeController.doInsertModule(folderPath);
						
						//--------------------------------------------------------------------------
					}
			 	};
			{
				JMenuItem menuItem = new JMenuItem("Insert Module");
				menuItem.addActionListener(actionListener);
				this.modulesCategoryPopupMenu.add(menuItem);
			}
			{
				JMenuItem menuItem = new JMenuItem("Insert Module");
				menuItem.addActionListener(actionListener);
				this.folderPopupMenu.add(menuItem);
			}
		}
		{
			ActionListener actionListener =
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doCreateFolder();
						
						//--------------------------------------------------------------------------
					}
			 	};
			{
				JMenuItem menuItem = new JMenuItem("Insert Folder");
				menuItem.addActionListener(actionListener);
				this.modulesCategoryPopupMenu.add(menuItem);
			}
			{
				JMenuItem menuItem = new JMenuItem("Insert Folder");
				menuItem.addActionListener(actionListener);
				this.folderPopupMenu.add(menuItem);
			}
		}
		this.modulesCategoryPopupMenu.addSeparator();
		this.folderPopupMenu.addSeparator();
		{
			ActionListener actionListener =
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doCut();
						
						//--------------------------------------------------------------------------
					}
			 	};
			{
				JMenuItem menuItem = new JMenuItem("Cut Module");
				menuItem.addActionListener(actionListener);
				this.modulePopupMenu.add(menuItem);
			}
			{
				JMenuItem menuItem = new JMenuItem("Cut Folder");
				menuItem.addActionListener(actionListener);
				this.folderPopupMenu.add(menuItem);
			}
		}
		{
			ActionListener actionListener =
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doPaste();
						
						//--------------------------------------------------------------------------
					}
			 	};
			{
				JMenuItem menuItem = new JMenuItem("Paste");
				menuItem.addActionListener(actionListener);
				this.modulesCategoryPopupMenu.add(menuItem);
			}
			{
				JMenuItem menuItem = new JMenuItem("Paste");
				menuItem.addActionListener(actionListener);
				this.folderPopupMenu.add(menuItem);
			}
		}
		{
			ActionListener actionListener =
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doDelete();
						
						//--------------------------------------------------------------------------
					}
			 	};
			{
				JMenuItem menuItem = new JMenuItem("Delete");
				menuItem.addActionListener(actionListener);
				this.modulePopupMenu.add(menuItem);
			}
			{
				JMenuItem menuItem = new JMenuItem("Delete");
				menuItem.addActionListener(actionListener);
				this.folderPopupMenu.add(menuItem);
			}
		}
		this.folderPopupMenu.addSeparator();
		{
			JMenuItem menuItem = new JMenuItem("Rename...");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doRename();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.folderPopupMenu.add(menuItem);
		}
		//==========================================================================================
		// Module Popup Menu:
		
		{
			JMenuItem menuItem = new JMenuItem("Edit Module");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						// Notify edit module event to listeners.
						//JMenuItem jMenuItem = (JMenuItem)e.getSource();
						
						TreePath selectionPath = modulesTreeView.getSelectionPath();
						
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						Object userObject = treeNode.getUserObject();
						
						ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)userObject;

						modulesTreeView.notifyEditModuleisteners(moduleGeneratorTypeData);
						//--------------------------------------------------------------------------
					}
			 		
			 	}
			);
			this.modulePopupMenu.add(menuItem);
		}
		//==========================================================================================
		// Generator Popup Menu:
		
		this.generatorPopupMenu = new JPopupMenu();
		{
			JMenuItem menuItem = new JMenuItem("Edit Generator");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						// Notify edit module event to listeners.
						//JMenuItem jMenuItem = (JMenuItem)e.getSource();
						
						TreePath selectionPath = modulesTreeView.getSelectionPath();
						
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						Object userObject = treeNode.getUserObject();
						
						GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;

						modulesTreeView.notifyDoEditGeneratorListeners(generatorTypeData);
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.generatorPopupMenu.add(menuItem);
		}
		//==========================================================================================
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) 
	{
		//==========================================================================================
		int row = this.modulesTreeView.getRowForLocation(e.getX(), e.getY());

		// Row selected?
		if (row != -1)
		{
			//--------------------------------------------------------------------------------------
//			TreePath oldSelectionPath = modulesTreeView.getSelectionPath();
//			DefaultMutableTreeNode oldTreeNode = (DefaultMutableTreeNode)oldSelectionPath.getLastPathComponent();
			
			this.modulesTreeView.setSelectionRow(row);
			
			TreePath selectionPath = this.modulesTreeView.getSelectionPath();
			
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
			
			//--------------------------------------------------------------------------------------
			// Popup?
			if (e.isPopupTrigger()) 
			{
				Object userObject = treeNode.getUserObject();
				
				if (userObject instanceof ModuleGeneratorTypeData)
				{
//					ModuleGeneratorTypeData moduleeneratorTypeData = (ModuleGeneratorTypeData)userObject;

					this.modulePopupMenu.show(e.getComponent(),
					                          e.getX(), e.getY());
				}
				else
				{
					if (userObject instanceof GeneratorTypeData)
					{
//						GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;
	
						this.generatorPopupMenu.show(e.getComponent(),
						                             e.getX(), e.getY());
					}
					else
					{
						ModulesTreeModel modulesTreeModel = this.modulesTreeView.getModulesTreeModel();
						DefaultMutableTreeNode modulesTreeNode = modulesTreeModel.getModulesTreeNode();
						
						// "Modules" Tree-Node?
						if (modulesTreeNode == treeNode)
						{
							this.modulesCategoryPopupMenu.show(e.getComponent(),
							                                   e.getX(), e.getY());
						}
						else
						{
							// Folder Tree-Node?
							if (this.checkIsFolderTreeNode(modulesTreeNode, treeNode) == true)
							{
								this.folderPopupMenu.show(e.getComponent(),
								                                 e.getX(), e.getY());
							}
						}
					}
				}
			}
			else
			{
				//----------------------------------------------------------------------------------
				// Doubleclick?
				if (e.getClickCount() == 2)
				{
					this.modulesTreeView.setSelectionRow(row);
					
					Object userObject = treeNode.getUserObject();
					
					if (userObject instanceof ModuleGeneratorTypeData)
					{
//						// Update the node edited bevor: 
//						DefaultTreeModel treeModel = (DefaultTreeModel)modulesTreeView.getModel();
//						treeModel.nodeChanged(oldTreeNode);
						
						ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)userObject;
					
						// Notify edit module event to listeners.
						this.modulesTreeView.notifyEditModuleisteners(moduleGeneratorTypeData);
					}
					else
					{
						if (userObject instanceof GeneratorTypeData)
						{
							//GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;
						}
					}
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @param modulesTreeNode
	 * 			is the "Modules" Tree-Node.
	 * @param treeNode
	 * 			is the Tree-Node.
	 * @return
	 * 			<code>true</code> if given Tree-Node is a Folder Tree-Node.
	 */
	private boolean checkIsFolderTreeNode(DefaultMutableTreeNode modulesTreeNode, DefaultMutableTreeNode treeNode)
	{
		//==========================================================================================
		boolean isFolderTreeNode;
		
		DefaultMutableTreeNode checkedTreeNode = (DefaultMutableTreeNode)treeNode.getParent();
		
		isFolderTreeNode = false;
		
		while (checkedTreeNode != null)
		{
			if (checkedTreeNode == modulesTreeNode)
			{
				isFolderTreeNode = true;
				break;
			}
			
			checkedTreeNode = (DefaultMutableTreeNode)checkedTreeNode.getParent();
		}
		
		//==========================================================================================
		return isFolderTreeNode;
	}
}
