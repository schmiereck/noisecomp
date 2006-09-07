package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.Application;
import org.bs.mdi.FileFormat;
import de.schmiereck.noiseComp.file.FileOperationInterface;

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
public class NoiseCompFileFormat
extends FileFormat
{
	static String extensions[] = 
	{	
		FileOperationInterface.FILE_EXTENSION
	};
	static String description = Application.tr("NoiseComp XML Files");
	
	public NoiseCompFileFormat() 
	{
		super(extensions, description);
	}
}
