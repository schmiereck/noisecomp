package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.Document;
import org.bs.mdi.DocumentData;
import org.bs.mdi.DocumentDataFactory;

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
public class NoiseCompDocumentDataFactory
implements DocumentDataFactory
{
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentDataFactory#create(org.bs.mdi.Document)
	 */
	public DocumentData create(Document document) 
	{
		return new NoiseCompDocumentData(document);
	}
}
