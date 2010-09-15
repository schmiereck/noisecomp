/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.basicEditView;

import java.awt.GridBagConstraints;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <p>
 * 	Basic-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>10.09.2010:	created, smk</p>
 */
public class BasicEditView
extends JPanel
{

	/**
	 * Constructor.
	 * 
	 */
	public BasicEditView()
	{
//		this.setLayout(new GridBagLayout());
		
	}

	/**
	 * @param gridy
	 * @param component
	 */
	public void addField(int gridy, JComponent component)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 1;
		constraints.gridy = gridy;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1.0D;
		constraints.weighty = 0.0D;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		this.add(component, constraints);
	}

	/**
	 * @param gridy
	 * @param labelText
	 */
	public void addLabel(int gridy, String labelText)
	{
		//==========================================================================================
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = gridy;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.1D;
		constraints.weighty = 0.0D;
		constraints.anchor = GridBagConstraints.EAST;
		
		this.add(new JLabel(labelText), constraints);
		
		//==========================================================================================
	}
	/**
	 * @param labelText
	 * 			is the Label Text.
	 * @return
	 */
	public JTextField addTextField(int gridy, String labelText)
	{
		//==========================================================================================
		this.addLabel(gridy, labelText);

		//------------------------------------------------------------------------------------------
		JTextField textField = new JTextField(30);

		this.addField(gridy, textField);
		
		//==========================================================================================
		return textField;
	}
}
