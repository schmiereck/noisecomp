/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView;

import java.awt.HeadlessException;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;

/**
 * <p>
 * 	App View.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * see: http://java.sun.com/docs/books/tutorial/uiswing/examples/components/index.html#ScrollDemo
 * see: http://download.oracle.com/javase/tutorial/uiswing/components/tree.html
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class AppView
extends JFrame
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modules Tree.
	 */
	private JTree modulesTree;
	
	/**
	 * Modules-Tree Scroll-Pane.
	 */
	private JScrollPane modulesTreeScrollPane;
	
	/**
	 * Modul-Timeline Split Pane.
	 */
	private JSplitPane modulSplitPane;
	
	/**
	 * Timeline View.
	 */
	private TimelineScrollPanelView timelineView;

	/**
	 * Timeline-Edit Split Pane.
	 */
	private JSplitPane timelineSplitPane;

	/**
	 * Timeline Edit Panel.
	 */
	private JPanel timelineEditPanel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @throws HeadlessException
	 * 			if an Error occurse.
	 */
	public AppView()
		throws HeadlessException
	{
		//==========================================================================================
		// Modul Select Panel:
		
		{
			// http://download.oracle.com/javase/tutorial/uiswing/components/tree.html
		    
		    this.modulesTree = new JTree();
		    
		    this.modulesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		    this.createNodes(this.modulesTree);
		    
		    this.modulesTreeScrollPane = new JScrollPane(this.modulesTree);
		}
		
		{
			//--------------------------------------------------------------------------------------
			// Timeline View:
			
			this.timelineView = new TimelineScrollPanelView();
	
			//--------------------------------------------------------------------------------------
			// Timeline Edit Panel:
			
			this.timelineEditPanel = new JPanel();
			this.timelineEditPanel.add(new JLabel("Timeline Edit"));
			
			//--------------------------------------------------------------------------------------
			// Timeline Split Pane:
			
			this.timelineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
			                                        this.timelineView,
			                                        this.timelineEditPanel);
			
			this.timelineSplitPane.setOneTouchExpandable(true);
	
			this.timelineSplitPane.setDividerLocation(400);
		}
		
	    //------------------------------------------------------------------------------------------
		this.modulSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
		                                     this.modulesTreeScrollPane, //this.modulSelectPanel,
		                                     this.timelineSplitPane);
		  
		this.modulSplitPane.setOneTouchExpandable(true);

		this.add(this.modulSplitPane);
		
		this.modulSplitPane.setDividerLocation(200);
		
	    //------------------------------------------------------------------------------------------
//	    this.add(this.timelinePanel);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		//==========================================================================================
	}

	/**
	 * @param modulesTree2
	 */
	private void createNodes(JTree tree)
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
		
		Iterator<GeneratorTypeData> generatorTypesIterator = soundService.getGeneratorTypesIterator();
		
		while (generatorTypesIterator.hasNext())
		{
			GeneratorTypeData generatorTypeData = (GeneratorTypeData)generatorTypesIterator.next();
			
	    	DefaultMutableTreeNode modulTreeNode = new DefaultMutableTreeNode(generatorTypeData.getGeneratorTypeName());
	    	modulesTreeNode.add(modulTreeNode);
		}
		
		//==========================================================================================
		DefaultTreeModel treeModel = new DefaultTreeModel(modulesTreeNode);

		tree.setModel(treeModel);
	}
}
