package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.Document;
import org.bs.mdi.DocumentWindow;
import org.bs.mdi.DocumentWindowFactory;
import org.bs.mdi.swing.SwingDocumentWindow;
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
public class NoiseCompDocumentWindowFactory
	implements DocumentWindowFactory
{
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentWindowFactory#create(org.bs.mdi.Document)
	 */
	public DocumentWindow create(Document document)
	{
		return new SwingDocumentWindow(document);
	}
}
