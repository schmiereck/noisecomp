/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * <p>
 * 	Modul-Tree Mouse-Listener.
 * </p>
 * 
 * see: http://download.oracle.com/javase/tutorial/uiswing/components/menu.html#popupapi
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public class ModulTreeMouseListener
extends MouseAdapter 
{
	//**********************************************************************************************
	// Fields:

	private final ModulesTreeView modulesTreeView;
	
	/**
	 * Modules-Category popup menu.
	 */
	private JPopupMenu modulesCategoryPopupMenu;
	
	/**
	 * Module popup menu.
	 */
	private JPopupMenu modulePopupMenu;
	
	/**
	 * Generator popup menu.
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
	public ModulTreeMouseListener(final ModulesTreeController modulesTreeController,
	                              final ModulesTreeView modulesTreeView)
	{
		//==========================================================================================
		this.modulesTreeView = modulesTreeView;
		
		//==========================================================================================
		// Modules-Category Popup Menu:
		
		this.modulesCategoryPopupMenu = new JPopupMenu();
		{
			JMenuItem menuItem = new JMenuItem("Insert Modul");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doInsertModul();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.modulesCategoryPopupMenu.add(menuItem);
		}
		{
			JMenuItem menuItem = new JMenuItem("Insert Folder");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doInsertFolder();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.modulesCategoryPopupMenu.add(menuItem);
		}
		this.modulesCategoryPopupMenu.addSeparator();
		{
			JMenuItem menuItem = new JMenuItem("Cut");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doCut();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.modulesCategoryPopupMenu.add(menuItem);
		}
		{
			JMenuItem menuItem = new JMenuItem("Paste");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doPaste();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.modulesCategoryPopupMenu.add(menuItem);
		}
		{
			JMenuItem menuItem = new JMenuItem("Delete");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//--------------------------------------------------------------------------
						modulesTreeController.doDelete();
						
						//--------------------------------------------------------------------------
					}
			 	}
			);
			this.modulesCategoryPopupMenu.add(menuItem);
		}
		this.modulesCategoryPopupMenu.addSeparator();
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
			this.modulesCategoryPopupMenu.add(menuItem);
		}
		//==========================================================================================
		// Modul Popup Menu:
		
		this.modulePopupMenu = new JPopupMenu();
		{
			JMenuItem menuItem = new JMenuItem("Edit Modul");
			menuItem.addActionListener
			(
			 	new ActionListener()
			 	{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// Notify edit modul event to listeners.
						//JMenuItem jMenuItem = (JMenuItem)e.getSource();
						
						TreePath selectionPath = modulesTreeView.getSelectionPath();
						
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						Object userObject = treeNode.getUserObject();
						
						ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)userObject;

						modulesTreeView.notifyEditModulListeners(modulGeneratorTypeData);
					}
			 		
			 	}
			);
			this.modulePopupMenu.add(menuItem);
		}
		//==========================================================================================
		// Genertor Popup Menu:
		
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
						// Notify edit modul event to listeners.
						//JMenuItem jMenuItem = (JMenuItem)e.getSource();
						
						TreePath selectionPath = modulesTreeView.getSelectionPath();
						
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						Object userObject = treeNode.getUserObject();
						
						GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;

						modulesTreeView.notifyDoEditGeneratorListeners(generatorTypeData);
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
				
				if (userObject instanceof ModulGeneratorTypeData)
				{
//					ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)userObject;

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
						
						if (modulesTreeNode == treeNode)
						{
							this.modulesCategoryPopupMenu.show(e.getComponent(),
							                                   e.getX(), e.getY());
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
					
					if (userObject instanceof ModulGeneratorTypeData)
					{
//						// Update the node edited bevor: 
//						DefaultTreeModel treeModel = (DefaultTreeModel)modulesTreeView.getModel();
//						treeModel.nodeChanged(oldTreeNode);
						
						ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)userObject;
					
						// Notify edit modul event to listeners.
						this.modulesTreeView.notifyEditModulListeners(modulGeneratorTypeData);
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
}
