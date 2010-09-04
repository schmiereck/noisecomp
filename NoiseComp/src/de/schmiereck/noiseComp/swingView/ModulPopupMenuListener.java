/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;

/**
 * <p>
 * 	Modul Popup-Menu Listener.
 * </p>
 * 
 * see: http://download.oracle.com/javase/tutorial/uiswing/components/menu.html#popupapi
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public class ModulPopupMenuListener
extends MouseAdapter 
{
	//**********************************************************************************************
	// Fields:

	private JPopupMenu modulesPopupMenu;
	
	private JTree modulesTree;
	
	//**********************************************************************************************
	// Functions:

	public ModulPopupMenuListener(JTree modulesTree)
	{
		this.modulesTree = modulesTree;
		
		this.modulesPopupMenu = new JPopupMenu();
		{
			JMenuItem menuItem = new JMenuItem("A popup menu item");
			//menuItem.addActionListener(this);
			this.modulesPopupMenu.add(menuItem);
		}
		{
			JMenuItem menuItem = new JMenuItem("Another popup menu item");
			//menuItem.addActionListener(this);
			this.modulesPopupMenu.add(menuItem);
		}
	}
	
    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) 
    {
        int row = this.modulesTree.getRowForLocation(e.getX(), e.getY());

        if (row != -1)
        {
        	TreePath selectionPath = this.modulesTree.getSelectionPath();
        	
        	DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
        	
	        if (e.isPopupTrigger()) 
	        {
            	this.modulesTree.setSelectionRow(row);
            
		        Object userObject = treeNode.getUserObject();
		        
		        if (userObject instanceof GeneratorTypeData)
		        {
		        	GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;

		        	this.modulesPopupMenu.show(e.getComponent(),
	            	                           e.getX(), e.getY());
		        }
            }
	        else
	        {
	        	// Doubleclick?
	        	if (e.getClickCount() == 2)
	        	{
	        		this.modulesTree.setSelectionRow(row);
	        		
	        		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	        	}
	        }
        }
    }
}
