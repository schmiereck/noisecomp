package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.Document;
import org.bs.mdi.DocumentView;
import org.bs.mdi.DocumentViewFactory;

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
public class NoiseCompDocumentViewFactory
implements DocumentViewFactory 
{
	public DocumentView create(Document doc) 
	{
		return new NoiseCompDocumentView(doc);
	}
}
