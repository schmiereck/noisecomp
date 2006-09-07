package de.schmiereck.noiseComp.mdi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import org.bs.mdi.Application;
import org.bs.mdi.Document;
//import org.bs.mdi.DocumentData;
//import org.bs.mdi.DocumentView;
import org.bs.mdi.DocumentWindow;
//import org.bs.mdi.MDIMessage;
import org.bs.mdi.MainWindow;
import org.bs.mdi.MessageDispatcher;
import org.bs.mdi.swing.SwingCommandButton;
import org.bs.mdi.swing.SwingCommandMenu;
import org.bs.mdi.swing.SwingDefaultCommands;
import org.bs.mdi.swing.SwingDocumentWindow;
import org.bs.mdi.swing.SwingMainWindow;
import org.bs.mdi_example.EditorCommands;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.editModulPage.EditModulPageData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.mdi.selectGenerator.SelectGeneratorDialog;
import de.schmiereck.screenTools.controller.ControllerData;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class NoiseCompMainWindow
extends SwingMainWindow
{
	private LocalActionMonitor localActionMonitor = new LocalActionMonitor();
	
	private final static String ACMD_PLAY 	= "ACMD_PLAY";
	private final static String ACMD_PAUSE 	= "ACMD_PAUSE";
	private final static String ACMD_STOP 	= "ACMD_STOP";

	private final static String ACMD_ZOOM_IN 	= "ACMD_ZOOM_IN";
	private final static String ACMD_ZOOM_OUT 	= "ACMD_ZOOM_OUT";
	
	private final static String ACMD_SELECT_GENERATOR 	= "ACMD_SELECT_GENERATOR";
	private final static String ACMD_EDIT_MODUL 		= "ACMD_EDIT_MODUL";

	private final static String ACMD_DUPPLICATE_VIEW 	= "Duplicate View";
	private final static String ACMD_ABOUT 				= "About...";
	
	private JButton cmdButtonPlay;
	private JButton cmdButtonPause;
	private JButton cmdButtonStop;
	
	private JButton cmdButtonZoomIn;
	private JButton cmdButtonZoomOut;
	
	private JButton cmdButtonSelectGenerator;
	private JButton cmdButtonEditModul;

	//private JMenuItem	cmdMenuItemDuplicateView;

	public NoiseCompMainWindow() 
	{
		super();
		
		this.createLocalMenu();		

		// http://java.sun.com/docs/books/tutorial/uiswing/components/toolbar.html
		
		JToolBar toolBar = new JToolBar("mainToolBar", JToolBar.HORIZONTAL);
	
		this.createLocalToolButtons(toolBar);
		
		this.add(toolBar, BorderLayout.PAGE_START);
	}

	private void createLocalToolButtons(JToolBar toolBar) 
	{
		//----------------------------------------------------------------------
		// Play:
		{
			this.cmdButtonPlay = this.createLocalToolButton(null,
														   ACMD_PLAY,
														   "Play the sound of the open modul.", "Play");
			toolBar.add(this.cmdButtonPlay);
		}
		//----------------------------------------------------------------------
		// Pause:
		{
			this.cmdButtonPause = this.createLocalToolButton(null,
															ACMD_PAUSE,
															"Pause the sound of the open modul.", "Pause");
			toolBar.add(this.cmdButtonPause);
		}
		//----------------------------------------------------------------------
		// Stop:
		{
			this.cmdButtonStop = this.createLocalToolButton(null,
														   ACMD_STOP,
														   "Stop the sound of the open modul.", "Stop");
			toolBar.add(this.cmdButtonStop);
		}
		//----------------------------------------------------------------------
		// Separator:
		{
			toolBar.addSeparator();
		}
		//----------------------------------------------------------------------
		// Zoom-In:
		{
			this.cmdButtonZoomIn = this.createLocalToolButton(null,
															 ACMD_ZOOM_IN,
															 "Zoom in the waveforms.",
															 "+");
			toolBar.add(this.cmdButtonZoomIn);
		}
		//----------------------------------------------------------------------
		// Zoom-Out:
		{
			this.cmdButtonZoomOut = this.createLocalToolButton(null,
															  ACMD_ZOOM_OUT,
															  "Zoom out the waveforms.",
															  "-");
			toolBar.add(this.cmdButtonZoomOut);
		}
		//----------------------------------------------------------------------
		// Separator:
		{
			toolBar.addSeparator();
		}
		//----------------------------------------------------------------------
		// Select-Generator:
		{
			this.cmdButtonSelectGenerator = this.createLocalToolButton(null,
													   ACMD_SELECT_GENERATOR,
													   "Select Generator to add or to edit.",
													   "Select Generator...");
			toolBar.add(this.cmdButtonSelectGenerator);
		}
		//----------------------------------------------------------------------
		// Edit-Modul:
		{
			this.cmdButtonEditModul = this.createLocalToolButton(null,
													   ACMD_EDIT_MODUL,
													   "Edit the open modul.",
													   "Edit Module...");
			toolBar.add(this.cmdButtonEditModul);
		}
	}
	
	private JButton createLocalToolButton(String imageName,
										  String actionCommand,
										  String toolTipText,
										  String altText) 
	{
	    //Create and initialize the button.
	    JButton button = new JButton();
	    
	    button.setActionCommand(actionCommand);
	    button.setToolTipText(toolTipText);
	    //button.addActionListener(this);
	    button.addActionListener(localActionMonitor);

		if (imageName != null)
		{
		    //Look for the image.
		    String imgLocation = "toolbarButtonGraphics/navigation/"
		                         + imageName
		                         + ".gif";
		    URL imageURL = NoiseCompMainWindow.class.getResource(imgLocation);

		    if (imageURL != null) 
		    {   
		    	//image found
		        button.setIcon(new ImageIcon(imageURL, altText));
		    } 
		    else 
		    {
		    	//no image found
		        button.setText(altText);
		        System.err.println("Resource not found: " + imgLocation);
		    }
		}
	    else 
	    {
	    	//no image found
	        button.setText(altText);
	    }

		button.setFocusable(false);
	    return button;
	}
	
	private void createLocalMenu() 
	{
		/*
		JMenuItem m;
		
		{
			Menu extraMenu = new Menu(Application.getResources().i18n("Extras"));
			
			//extraMenu.setMnemonic(Application.getResources().getMnemonic("Extras"));
			extraMenu.setLabel(Application.getResources().getMnemonic("Extras"));
			{
				this.cmdMenuItemDuplicateView = this.createLocalMenuItem(ACMD_DUPPLICATE_VIEW);
				
				extraMenu.add(this.cmdMenuItemDuplicateView);
			}
			
			this.getMenuBar().add(extraMenu);
		}
		{
			JMenu helpMenu = new JMenu(Application.getResources().i18n("Help"));
			
			helpMenu.setMnemonic(Application.getResources().getMnemonic("Help"));
			helpMenu.add(this.createLocalMenuItem(ACMD_ABOUT));				
			
			this.getMenuBar().add(helpMenu);
		}
		*/
		JMenu extrasMenu = SwingCommandMenu.createMenu(((EditorCommands)getCommands()).getShowExtrasMenuCommand());
		
		extrasMenu.add(SwingCommandButton.createMenuItem(((EditorCommands)getCommands()).getCloneViewCommand()));
		getJMenuBar().add(extrasMenu);
		
		JMenu helpMenu = SwingCommandMenu.createMenu(((EditorCommands)getCommands()).getShowHelpMenuCommand());
		
		helpMenu.add(SwingCommandButton.createMenuItem(((EditorCommands)getCommands()).getHelpAboutCommand()));
		getJMenuBar().add(helpMenu);
	}

	protected JMenuItem createLocalMenuItem(String key) 
	{
		JMenuItem item = new JMenuItem(Application.getResources().i18n(key));
	
		item.setMnemonic(Application.getResources().getMnemonic(key));
		item.setActionCommand(key);
		item.addActionListener(this.localActionMonitor);
		
		return item;
	}

	public void processMessage(Object source, int type, Object argument) 
	{
		super.processMessage(source, type, argument);
		
		switch (type)
		{
			//case MDIMessage.WINDOW_OPENED:
			//case MDIMessage.WINDOW_CLOSED:
			//case MDIMessage.WINDOW_CREATED:
			case MessageDispatcher.WINDOW_SELECTED:
			{
				//DocumentWindow documentWindow = Application.getCurrentWindow();
				SwingDocumentWindow documentWindow = (SwingDocumentWindow)argument;

				// Fenster ist ausgewählt?
				if (documentWindow != null)
				{
					//documentWindow.setFocusable(true);
					documentWindow.setEnabled(true);
					
					this.updateFunctionStatus(true);
				}
				else
				{
					this.updateFunctionStatus(false);
				}
				break;
			}
			case MessageDispatcher.APP_INIT:
			{
				this.updateFunctionStatus(false);
				break;
			}
		}
	//	SwingDocumentWindow win;
	//	switch (type) {
	//	case MessageDispatcher.DOCUMENT_SELECTED:
	}
	/* (non-Javadoc)
	 * @see org.bs.mdi.MDIMessageReceiver#handleMDIMessage(org.bs.mdi.MDIMessage)
	public void handleMDIMessage(MDIMessage message) 
	{
		// Nachrichten vom MDI-System bearbeiten:
		
		super.handleMDIMessage(message);
		
		switch (message.getType())
		{
			//case MDIMessage.WINDOW_OPENED:
			//case MDIMessage.WINDOW_CLOSED:
			//case MDIMessage.WINDOW_CREATED:
			case MDIMessage.WINDOW_SELECTED:
			{
				//DocumentWindow documentWindow = Application.getCurrentWindow();
				SwingDocumentWindow documentWindow = (SwingDocumentWindow)message.getArgument();

				// Fenster ist ausgewählt?
				if (documentWindow != null)
				{
					documentWindow.setFocusable(true);
					
					this.updateFunctionStatus(true);
				}
				else
				{
					this.updateFunctionStatus(false);
				}
				break;
			}
			case MDIMessage.APP_INIT:
			{
				this.updateFunctionStatus(false);
				break;
			}
		}
	}
	 */
	
	/**
	 * Update (enable or disable) the state of all application functions 
	 * in menus and toolbars depending on the document view ().
	 * 
	 * @param documentIsSelected
	 * 			is true, if a document view is selected.
	 */
	private void updateFunctionStatus(boolean documentIsSelected)
	{
		this.cmdButtonPlay.setEnabled(documentIsSelected);
		this.cmdButtonPause.setEnabled(documentIsSelected);
		this.cmdButtonStop.setEnabled(documentIsSelected);
		
		this.cmdButtonZoomIn.setEnabled(documentIsSelected);
		this.cmdButtonZoomOut.setEnabled(documentIsSelected);
		
		this.cmdButtonSelectGenerator.setEnabled(documentIsSelected);
		this.cmdButtonEditModul.setEnabled(documentIsSelected);

		((EditorCommands)this.getCommands()).getCloneViewCommand().setAvailable(documentIsSelected);
		
		//this.cmdMenuItemDuplicateView.setEnabled(documentIsSelected);
	}

	protected SwingDefaultCommands createCommands() 
	{
		return new EditorCommands();
	}
	
	class LocalActionMonitor 
	implements ActionListener 
	{
		public void actionPerformed(ActionEvent ae) 
		{
			// Nachrichten von Applikations eigenen Menüs und Toolsbars bearbeiten:
			
			String acmd = ae.getActionCommand();
			/*
			if (ACMD_ABOUT.equals(acmd)) 
			{
				showMessage(MainWindow.INFO, 
							null, 
							Application.tr("NoiseComp Editor\nby Thomas Schmiereck (thomas@schmiereck.de)\ncreated in 2003 - 2005\navailable at www.sourceforge.org"));
			}
			else
			{
				if (ACMD_DUPPLICATE_VIEW.equals(acmd)) 
				{
					DocumentWindow documentWindow = Application.getCurrentWindow();
					
					if (documentWindow != null)
					{
						Document doc = documentWindow.getDocument();
						
						DocumentView view = Application.getDocumentViewFactory().create(doc);
						DocumentWindow window = Application.getDocumentWindowFactory().create(doc);				
						
						view.setWindow(window);
						window.setView(view);
						window.setTitle(doc.getFilename());				
						
						doc.addView(view);
						doc.syncViewsWithData();
					}
				}
				else
			*/
				{
					if (ACMD_ZOOM_IN.equals(acmd)) 
					{
						DocumentWindow documentWindow = Application.getCurrentWindow();
						
						if (documentWindow != null)
						{
							Document doc = documentWindow.getDocument();
							
							NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
							
							documentData.getMainModel().getControllerLogic().getMainPageLogic().doChangeZoom(2.0F);
						}
					}
					else
					{
						if (ACMD_ZOOM_OUT.equals(acmd)) 
						{
							DocumentWindow documentWindow = Application.getCurrentWindow();
							
							if (documentWindow != null)
							{
								Document doc = documentWindow.getDocument();
								
								NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
								
								documentData.getMainModel().getControllerLogic().getMainPageLogic().doChangeZoom(0.5F);
							}
						}
						else
						{
							if (ACMD_PLAY.equals(acmd)) 
							{
								DocumentWindow documentWindow = Application.getCurrentWindow();
								
								if (documentWindow != null)
								{
									Document doc = documentWindow.getDocument();
									
									NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
									
									documentData.getMainModel().getControllerLogic().playSound();
								}
							}
							else
							{
								if (ACMD_PAUSE.equals(acmd)) 
								{
									DocumentWindow documentWindow = Application.getCurrentWindow();
									
									if (documentWindow != null)
									{
										Document doc = documentWindow.getDocument();
										
										NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
										
										documentData.getMainModel().getControllerLogic().pauseSound();
									}
								}
								else
								{
									if (ACMD_STOP.equals(acmd)) 
									{
										DocumentWindow documentWindow = Application.getCurrentWindow();
										
										if (documentWindow != null)
										{
											Document doc = documentWindow.getDocument();
											
											NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
											
											documentData.getMainModel().getControllerLogic().stopSound();
										}
									}
									else
									{
										if (ACMD_SELECT_GENERATOR.equals(acmd)) 
										{
											DocumentWindow documentWindow = Application.getCurrentWindow();
											
											if (documentWindow != null)
											{
												Document doc = documentWindow.getDocument();
												
												NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
												
												showSelectGenerator(documentData);											}
										}
										else
										{
											if (ACMD_EDIT_MODUL.equals(acmd)) 
											{
												DocumentWindow documentWindow = Application.getCurrentWindow();
												
												if (documentWindow != null)
												{
													Document doc = documentWindow.getDocument();
													
													NoiseCompDocumentData documentData = (NoiseCompDocumentData)doc.getData();
													
													DesktopControllerData controllerData = (DesktopControllerData)documentData.getMainModel().getControllerData();
													
													EditModulPageData editModulPageData = controllerData.getEditModulPageData();
													/*
													ModulGeneratorTypeData editModulTypeData = controllerData.getEditData().getEditModulTypeData();
													
													String generatorTypeName;
													
													if (editModulTypeData != null)
													{	
														generatorTypeName = editModulTypeData.getGeneratorTypeName();
													}
													else
													{
														generatorTypeName = "";
													}
													editModulPageData.getGroupNameInputlineData().setInputText(generatorTypeName);
													*/

													controllerData.setActiveDesktopPageData(editModulPageData);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			//}
		}
	}

	/**
	 * @param documentData
	 */
	private void showSelectGenerator(NoiseCompDocumentData documentData)
	{
		DesktopControllerData controllerData = (DesktopControllerData)documentData.getMainModel().getControllerData();
		
		controllerData.setActiveDesktopPageData(controllerData.getSelectGeneratorPageData());
		
		SelectGeneratorDialog selectGeneratorDialog = new SelectGeneratorDialog(this);

		selectGeneratorDialog.setVisible(true);
	}

}
