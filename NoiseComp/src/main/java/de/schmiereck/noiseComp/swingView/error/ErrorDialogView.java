/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.error;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import de.schmiereck.noiseComp.Version;

/**
 * <p>
 * 	Error-Dialog View.
 * </p>
 * 
 * @author smk
 * @version <p>16.03.2011:	created, smk</p>
 */
public class ErrorDialogView
extends JDialog
{
	//**********************************************************************************************
	// Fields:
	
	 private final JButton okButton;

	 private final JEditorPane jep;
	 
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ErrorDialogView(Frame parent, boolean modal)
		throws HeadlessException
	{
		super(parent, modal);
		
	    GridBagLayout gridBagLayout = new GridBagLayout();
	    
	    this.setLayout(gridBagLayout);
	    
	    this.setVisible(false);
	    this.setSize(640, 100);
	    this.setBackground(Color.WHITE);
	    this.setTitle("Error NoiseComp");
	    this.setResizable(true);

	    {
	    	JLabel label = new JLabel("NoiseComp",
	    	                          JLabel.CENTER);
		    
	    	label.setForeground(Color.BLUE);
	    	
	    	label.setFont(new Font("Arial", Font.BOLD, 18));
	    	
		    GridBagConstraints gbc = new GridBagConstraints();
		    
		    gbc.gridx = 1;
		    gbc.gridy = 1;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(0, 0, 0, 0);
		    
			gridBagLayout.setConstraints(label, gbc);
		    
			this.add(label);
	    }
	    {
	    	JLabel label = new JLabel("Version: " + Version.version,
	    	                          JLabel.LEFT);
		    
		    GridBagConstraints gbc = new GridBagConstraints();
		    
		    gbc.gridx = 1;
		    gbc.gridy = 2;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(0, 0, 0, 0);
		    
			gridBagLayout.setConstraints(label, gbc);
		    
			this.add(label);
	    }
	    {
	    	JLabel label = new JLabel("Send stacktrace to thomas@schmiereck.de",
	    	                          JLabel.LEFT);
		    
		    GridBagConstraints gbc = new GridBagConstraints();
		    
		    gbc.gridx = 1;
		    gbc.gridy = 3;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(18, 0, 0, 0);
		    
			gridBagLayout.setConstraints(label, gbc);
		    
			this.add(label);
	    }
	    {
		    GridBagConstraints gbc = new GridBagConstraints();
		    
		    gbc.gridx = 1;
		    gbc.gridy = 4;
		    gbc.fill = GridBagConstraints.HORIZONTAL | GridBagConstraints.VERTICAL;//GridBagConstraints.NONE;
		    //gbc.insets = new Insets(9, 0, 0, 0);
		    gbc.weightx = 1.0D;
		    gbc.weighty = 1.0D;
		    
//	    	JLabel label = new JLabel("Visit http://www.schmiereck.de/jawiki/user/coders/NoiseComp",
//	    	                          JLabel.LEFT);
//		    
//			gridBagLayout.setConstraints(label, gbc);
//		    
//			this.add(label);

	    	//http://www.coderanch.com/t/345547/GUI/java/Making-clickable-links-HTML-JLabel
	    	
	    	this.jep = new JEditorPane();
	    	this.jep.setContentType("text/html");
	    	this.jep.setEditable(false);  
	    	this.jep.setOpaque(false);  
	    	//this.jep.setAutoscrolls(true);
	    	this.jep.addHyperlinkListener(new HyperlinkListener() 
	    	{  
	    		/* (non-Javadoc)
	    		 * @see javax.swing.event.HyperlinkListener#hyperlinkUpdate(javax.swing.event.HyperlinkEvent)
	    		 */
	    		public void hyperlinkUpdate(HyperlinkEvent hle) 
	    		{  
	    			if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) 
	    			{  
	    				System.out.println(hle.getURL());
	    				
	    				URL url = hle.getURL();
	    				
//	    				try
//						{
//							jep.setPage(hle.getURL());
//						}
//						catch (IOException ex)
//						{
//							throw new RuntimeException(ex);
//						}
	    				
	    				try
						{
							Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
						}
						catch (IOException ex)
						{
							throw new RuntimeException(ex);
						}  
	    			}  
	    		}  
	    	});  
	    	
		    JScrollPane scrollPane = new JScrollPane();
		    scrollPane.setViewportView(this.jep);
		    scrollPane.setPreferredSize(new Dimension(600, 200));
	    	gridBagLayout.setConstraints(scrollPane, gbc);
		    
			this.add(scrollPane);
	    }
	    {
			this.okButton = new JButton();
			//this.okButton.setLabel("OK");
			
			GridBagConstraints gbc = new GridBagConstraints();
			
		    gbc.gridx = 1;
		    gbc.gridy = 5;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(18, 0, 0, 0);
 
			gridBagLayout.setConstraints(this.okButton, gbc);
		 
		    this.add(this.okButton);
	    }
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b)
	{
		if (b == true)
		{
			Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
			
			Dimension abounds = this.getSize();
			
	        this.setLocation((bounds.width - abounds.width) / 2, 
	                         (bounds.height - abounds.height) / 3); 
		}
		super.setVisible(b);
	}

	/**
	 * @return 
	 * 			returns the {@link #okButton}.
	 */
	public JButton getOkButton()
	{
		return this.okButton;
	}

	public void setStacktrace(String stacktrace)
	{
		String htmlStr = stacktrace.replaceAll("\\n", "<br/>");
		
		this.jep.setText("<div style=\"font-family:Verdana,Arial,Helvetica,sans-serif;font-size:11pt;\">" +
		                 "<div>" +
		                 "Stacktrace:" +
		                 "</div>" +
		                 "<code>" +
		                 htmlStr + 
		                 "</code>" +
		                 "</div>");
		this.pack();
	}
	
}
