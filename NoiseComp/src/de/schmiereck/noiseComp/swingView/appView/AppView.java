/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appView;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditView;

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
implements EditModuleChangedListener
{
	//**********************************************************************************************
	// Fields:

	/**
	 * App Model
	 */
	private AppModel appModel;
	
//	/**
//	 * Timeline Draw Panel Model.
//	 */
//	private TimelinesDrawPanelModel timelinesDrawPanelModel;
	
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
//	private TimelinesScrollPanelView timelineView;

	/**
	 * Timeline-Edit Split Pane.
	 */
	private JSplitPane timelineSplitPane;

	/**
	 * Timeline Edit Panel.
	 */
	private TimelineEditView timelineEditView;
	
	/**
	 * Do Edit-Module Listeners.
	 */
	private List<DoEditModuleListener> doEditModuleListeners = new Vector<DoEditModuleListener>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appModel
	 * 			is the App Model.
	 * @throws HeadlessException
	 * 			if an Error occurse.
	 */
	public AppView(AppModel appModel)
		throws HeadlessException
	{
		//==========================================================================================
		this.appModel = appModel;
//		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		//==========================================================================================
		// Modul Select Panel:
		
		{
			// http://download.oracle.com/javase/tutorial/uiswing/components/tree.html
			
			this.modulesTree = new JTree();
			
			this.modulesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

			this.modulesTree.setCellRenderer(new DefaultTreeCellRenderer()
			{
				@Override
				public Component getTreeCellRendererComponent(JTree tree, 
															  Object value, 
															  boolean selected,
															  boolean expanded, 
															  boolean leaf, 
															  int row,
															  boolean hasFocus)
				{
					// Die Originalmethode die Standardeinstellungen machen lassen
					super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
					
					// Den Wert des Knotens abfragen
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value;
					Object userObject = treeNode.getUserObject();
					
					if (userObject instanceof GeneratorTypeData)
					{
						GeneratorTypeData generatorTypeData = (GeneratorTypeData)userObject;
						
						this.setText(generatorTypeData.getGeneratorTypeName());
					}
					
					return this;
				}
			});
			
			this.createNodes(this.modulesTree);
			
			this.modulesTreeScrollPane = new JScrollPane(this.modulesTree);
			
			this.pack();
		}
		
		{
//			//--------------------------------------------------------------------------------------
//			// Timeline View:
//			
//			this.timelineView = new TimelinesScrollPanelView(timelinesDrawPanelView);
	
			//--------------------------------------------------------------------------------------
			// Timeline Edit Panel:
			
			this.timelineEditView = new TimelineEditView();
			
			//--------------------------------------------------------------------------------------
			// Timeline Split Pane:
			
			this.timelineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			
			this.timelineSplitPane.setOneTouchExpandable(true);
		}
		
		//------------------------------------------------------------------------------------------
		this.modulSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
											 this.modulesTreeScrollPane, //this.modulSelectPanel,
											 this.timelineSplitPane);
		  
		this.modulSplitPane.setOneTouchExpandable(true);

		this.add(this.modulSplitPane);
		
		this.modulSplitPane.setDividerLocation(200);
		
		//------------------------------------------------------------------------------------------
		{
			// Add listener to components that can bring up popup menus.
			MouseListener popupListener = new ModulTreeMouseListener(this);
			//output.addMouseListener(popupListener);
			//menuBar.addMouseListener(popupListener);
			this.modulesTree.addMouseListener(popupListener);
		}
		//------------------------------------------------------------------------------------------
//		this.add(this.timelinePanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//==========================================================================================
	}

	public void setTimelineComponent(Component timelineComponent)
	{
		this.timelineSplitPane.setLeftComponent(timelineComponent);
		
		this.timelineSplitPane.setDividerLocation(400);
//		this.timelineSplitPane.revalidate();
	}
	
	/**
	 * @param tree
	 * 			is the Tree.
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
	 * 			returns the {@link #modulesTree}.
	 */
	public JTree getModulesTree()
	{
		return this.modulesTree;
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the module to edit.
	 */
	public void notifyEditModulListeners(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		for (DoEditModuleListener doEditModuleListener : this.doEditModuleListeners)
		{
			doEditModuleListener.notifyEditModul(modulGeneratorTypeData);
		}
	}

	/**
	 * @param doEditModuleListener
	 * 			to add to {@link #doEditModuleListeners}.
	 */
	public void addDoEditModuleListener(DoEditModuleListener doEditModuleListener)
	{
		this.doEditModuleListeners.add(doEditModuleListener);
	}

	/**
	 * @param generatorTypeData
	 * 			is the generator to edit.
	 */
	public void notifyDoEditGeneratorListeners(GeneratorTypeData generatorTypeData)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener#notifyEditModulChanged(de.schmiereck.noiseComp.swingView.appModel.AppModel)
	 */
	@Override
	public void notifyEditModulChanged(AppModel appModel)
	{
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEditView}.
	 */
	public TimelineEditView getTimelineEditView()
	{
		return this.timelineEditView;
	}

	/**
	 * @param timelineEditView 
	 * 			to set {@link #timelineEditView}.
	 */
	public void setTimelineEditView(TimelineEditView timelineEditView)
	{
		this.timelineEditView = timelineEditView;
		
		this.timelineSplitPane.setRightComponent(this.timelineEditView);
	}
}
