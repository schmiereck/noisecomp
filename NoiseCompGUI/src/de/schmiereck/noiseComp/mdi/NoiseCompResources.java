package de.schmiereck.noiseComp.mdi;

import java.util.Locale;
import java.util.ResourceBundle;
import org.bs.mdi.DefaultResources;
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
public class NoiseCompResources
	extends DefaultResources
{
		/* (non-Javadoc)
		 * @see org.bs.mdi.DefaultResources#getApplicationResourceBundle(java.util.Locale)
		 */
		protected ResourceBundle getApplicationResourceBundle(Locale locale) 
		{
			return ResourceBundle.getBundle("translations.editor", getLocale());
		}
}
