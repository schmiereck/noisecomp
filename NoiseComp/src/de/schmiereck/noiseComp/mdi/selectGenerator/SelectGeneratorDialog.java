package de.schmiereck.noiseComp.mdi.selectGenerator;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import javax.swing.JDialog;
/*
 * Created on 28.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Dialog View to select a generator.
 * </p>
 * 
 * @author smk
 * @version <p>28.03.2005:	created, smk</p>
 */
public class SelectGeneratorDialog
	extends JDialog
{
	/**
	 * Constructor.
	 * 
	 * @throws java.awt.HeadlessException
	 */
	public SelectGeneratorDialog(Frame owner)
		throws HeadlessException
	{
		super(owner, "Select Generator", true);

		this.setSize(new Dimension(320, 400));
		
		//GridBagLayout gridBagLayout = new GridBagLayout();
		//this.setLayout(gridBagLayout);
	}
}
