package de.schmiereck.noiseComp.mdi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.bs.mdi.Application;
import org.bs.mdi.Document;
import org.bs.mdi.DocumentView;
import org.bs.mdi.DocumentWindow;
import org.bs.mdi.MainWindow;
import org.bs.mdi.swing.SwingMainWindow;

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
	ActionMonitor actionMonitor = new ActionMonitor();
	
	public NoiseCompMainWindow() 
	{
		super();
		constructMenu();				

		//this.addKeyListener(new XXX23());
	}

	/*
	class XXX23 implements KeyListener
	{
		public void keyTyped(KeyEvent e)
		{
			e.getSource();
		}
		public void keyPressed(KeyEvent e)
		{
			e.getSource();
		}
		public void keyReleased(KeyEvent e)
		{
			e.getSource();
		}
		
	}
	*/
	
	protected void constructMenu() 
	{
		JMenuItem m;
		
		{
			JMenu extraMenu = new JMenu(Application.getResources().i18n("Extras"));
			
			extraMenu.setMnemonic(Application.getResources().getMnemonic("Extras"));
			extraMenu.add(getMenuItem("Duplicate View"));
			
			this.getMenubar().add(extraMenu);
		}
		{
			JMenu helpMenu = new JMenu(Application.getResources().i18n("Help"));
			
			helpMenu.setMnemonic(Application.getResources().getMnemonic("Help"));
			helpMenu.add(getMenuItem("About..."));				
			
			this.getMenubar().add(helpMenu);
		}
	}
	
	protected JMenuItem getMenuItem(String key) 
	{
		JMenuItem item = new JMenuItem(Application.getResources().i18n(key));
	
		item.setMnemonic(Application.getResources().getMnemonic(key));
		item.setActionCommand(key);
		item.addActionListener(actionMonitor);
		
		return item;
	}
	
	class ActionMonitor 
	implements ActionListener 
	{
		public void actionPerformed(ActionEvent ae) 
		{
			String id = ae.getActionCommand();
			
			if (id.equals("About..."))
			{
				showMessage(MainWindow.INFO, null, Application.tr("About Simple Text Editor"));
			}
			else
			{
				if (id.equals("Duplicate View")) 
				{
					DocumentWindow win = Application.getCurrentWindow();
					
					if (win != null)
					{
						Document doc = win.getDocument();
						
						DocumentView view = Application.getDocumentViewFactory().create(doc);
						DocumentWindow window = Application.getDocumentWindowFactory().create(doc);				
						
						view.setWindow(window);
						window.setView(view);
						window.setTitle(doc.getFilename());				
						
						doc.addView(view);
						doc.syncViewsWithData();
					}
				}
			}
		}
	}

}
