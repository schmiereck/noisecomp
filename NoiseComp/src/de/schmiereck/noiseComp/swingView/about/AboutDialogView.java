/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.about;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import de.schmiereck.noiseComp.Version;

/**
 * <p>
 * 	The View of the About-Dialog.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class AboutDialogView
extends JDialog
{
	 private JButton okButton;

	/**
	 * Constructor.
	 * 
	 */
	public AboutDialogView(Frame parent, boolean modal)
		throws HeadlessException
	{
		super(parent, modal);
		
	    GridBagLayout gridBagLayout = new GridBagLayout();
	    
	    this.setLayout(gridBagLayout);
	    
	    this.setVisible(false);
	    this.setSize(320, 200);
	    this.setBackground(Color.WHITE);
	    this.setTitle("About NoiseComp");
	    this.setResizable(true);

	    {
	    	JLabel label = new JLabel("NoiseComp",
	    	                          JLabel.CENTER);
		    
	    	label.setForeground(Color.BLUE);
	    	
	    	label.setFont(new Font("Arial", Font.BOLD, 18));
	    	
		    GridBagConstraints gbc = new GridBagConstraints( );
		    
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
		    
		    GridBagConstraints gbc = new GridBagConstraints( );
		    
		    gbc.gridx = 1;
		    gbc.gridy = 2;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(0, 0, 0, 0);
		    
			gridBagLayout.setConstraints(label, gbc);
		    
			this.add(label);
	    }
	    {
	    	JLabel label = new JLabel("(c) Copyright by schmiereck 2004 - 2010",
	    	                          JLabel.LEFT);
		    
		    GridBagConstraints gbc = new GridBagConstraints( );
		    
		    gbc.gridx = 1;
		    gbc.gridy = 3;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(18, 0, 0, 0);
		    
			gridBagLayout.setConstraints(label, gbc);
		    
			this.add(label);
	    }
	    {
	    	JLabel label = new JLabel("Visit http://www.schmiereck.de",
	    	                          JLabel.LEFT);
		    
		    GridBagConstraints gbc = new GridBagConstraints( );
		    
		    gbc.gridx = 1;
		    gbc.gridy = 4;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(9, 0, 0, 0);
		    
			gridBagLayout.setConstraints(label, gbc);
		    
			this.add(label);
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

}
