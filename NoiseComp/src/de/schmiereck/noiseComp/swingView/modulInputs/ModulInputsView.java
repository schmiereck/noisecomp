/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputs;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import de.schmiereck.noiseComp.swingView.appView.AppView;

/**
 * <p>
 * 	Modul-Inputs View.
 * </p>
 * 
 * @author smk
 * @version <p>12.09.2010:	created, smk</p>
 */
public class ModulInputsView
extends JDialog
{
	/**
	 * Constructor.
	 * 
	 * Contructs a invisble Dialog.
	 * 
	 * @param appView 
	 * 			this is the App View.
	 */
	public ModulInputsView(AppView appView)
	{
		super(appView, false);
		this.setVisible(false);
		
		this.setTitle("Modul-Inputs");
//		this.setModal(false);
		
		this.setSize(150, 150);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
