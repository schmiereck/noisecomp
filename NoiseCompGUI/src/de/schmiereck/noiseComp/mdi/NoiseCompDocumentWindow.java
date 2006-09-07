package de.schmiereck.noiseComp.mdi;

import java.awt.AWTEvent;
import org.bs.mdi.Document;
import org.bs.mdi.swing.SwingDocumentWindow;
import org.bs.mdi.swing.TraditionalDocumentWindow;
import de.schmiereck.noiseComp.MainModel;
/*
 * Created on 28.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>28.03.2005:	created, smk</p>
 */
public class NoiseCompDocumentWindow
	extends TraditionalDocumentWindow
{
	/**
	 * Constructor.
	 * 
	 * @param document
	 */
	public NoiseCompDocumentWindow()//Document document)
	{
		super();//document);

		// Need this to retrieve kay events in the SwingDocumentView.
		//this.setFocusable(true);
		
		//this.setEnabled(true);
		//this.setFocusTraversalKeysEnabled(true);
		//this.setFocusTraversalPolicyProvider(true);
		
		//this.enableInputMethods(true);
		
		//this.enableEvents(AWTEvent.KEY_EVENT_MASK | 
		//					  AWTEvent.FOCUS_EVENT_MASK | 
		//					  AWTEvent.INPUT_METHOD_EVENT_MASK |
		//					  //AWTEvent.INPUT_METHODS_ENABLED_MASK |
		//					  AWTEvent.MOUSE_EVENT_MASK |
		//					  AWTEvent.MOUSE_MOTION_EVENT_MASK);

		//NoiseCompDocumentData noiseCompDocumentData = (NoiseCompDocumentData)this.getDocument().getData();
		
		//MainModel mainModel = noiseCompDocumentData.getMainModel();

		//this.addKeyListener(mainModel.getInputListener());
		//this.setFocusable(true);
	}
}
