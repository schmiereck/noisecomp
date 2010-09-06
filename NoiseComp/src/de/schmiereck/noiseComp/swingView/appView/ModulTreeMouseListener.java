/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
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

	private AppView appView;
	
	/**
	 * Modules popup menu.
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
	 * @param appView
	 * 			is the app View.
	 */
	public ModulTreeMouseListener(final AppView appView)
	{
		this.appView = appView;
		
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
						
						JTree modulesTree = appView.getModulesTree();
						
						TreePath selectionPath = modulesTree.getSelectionPath();
						
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						Object userObject = treeNode.getUserObject();
						
						ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)userObject;

						appView.notifyEditModulListeners(modulGeneratorTypeData);
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
						
						JTree modulesTree = appView.getModulesTree();
						
						TreePath selectionPath = modulesTree.getSelectionPath();
						
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
						
						Object userObject = treeNode.getUserObject();
						
						GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;

						appView.notifyEditGeneratorListeners(generatorTypeData);
					}
			 		
			 	}
			);
			this.generatorPopupMenu.add(menuItem);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) 
	{
		JTree modulesTree = this.appView.getModulesTree();
		
		int row = modulesTree.getRowForLocation(e.getX(), e.getY());

		if (row != -1)
		{
			modulesTree.setSelectionRow(row);
			
			TreePath selectionPath = modulesTree.getSelectionPath();
			
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
			
			if (e.isPopupTrigger()) 
			{
				Object userObject = treeNode.getUserObject();
				
				if (userObject instanceof ModulGeneratorTypeData)
				{
					ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)userObject;

					this.modulePopupMenu.show(e.getComponent(),
											   e.getX(), e.getY());
				}
				else
				{
					if (userObject instanceof GeneratorTypeData)
					{
						GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;
	
						this.generatorPopupMenu.show(e.getComponent(),
						                             e.getX(), e.getY());
					}
				}
			}
			else
			{
				// Doubleclick?
				if (e.getClickCount() == 2)
				{
					modulesTree.setSelectionRow(row);
					
					Object userObject = treeNode.getUserObject();
					
					if (userObject instanceof ModulGeneratorTypeData)
					{
						ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)userObject;
					
						// Notify edit modul event to listeners.
						this.appView.notifyEditModulListeners(modulGeneratorTypeData);
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
	}
}
