package de.schmiereck.noiseComp;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/*
 * Created on 02.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Wave-Resource Loader.
 * </p>
 * 
 * @author smk
 * @version <p>02.04.2005:	created, smk</p>
 */
public class WaveResourceLoader
{
	public static File getFileResource(String fileName) 
	throws URISyntaxException
	{
		URL fileURL = WaveResourceLoader.class.getResource("/" + fileName);
		File file = new File(fileURL.toURI());
		
		return file;
	}
}
