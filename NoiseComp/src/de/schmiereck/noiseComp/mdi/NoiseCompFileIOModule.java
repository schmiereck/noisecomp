package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.Application;
import org.bs.mdi.Document;
import org.bs.mdi.DocumentData;
import org.bs.mdi.FileExporter;
import org.bs.mdi.FileFormat;
import org.bs.mdi.FileIOException;
import org.bs.mdi.FileLoader;
import org.bs.mdi.FileSaver;
import org.bs.mdi.MainWindow;
import de.schmiereck.noiseComp.file.LoadFileOperationLogic;
import de.schmiereck.noiseComp.file.SaveFileOperationLogic;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.xmlTools.XMLPortException;

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
public class NoiseCompFileIOModule
implements FileLoader, FileSaver, FileExporter
{
	static FileFormat[] formats = 
	{
		new NoiseCompFileFormat() 
	};

	/* (non-Javadoc)
	 * @see org.bs.mdi.FileLoader#load(java.lang.String, org.bs.mdi.Document)
	 */
	public DocumentData load(String fileName, Document document) 
	throws FileIOException 
	{
		NoiseCompDocumentData noiseCompDocumentData = new NoiseCompDocumentData(document);
		
		try 
		{
			//-----------------------------------------------------
			//noiseCompDocumentData.clearTracks();
			
			//-----------------------------------------------------
			GeneratorTypesData generatorTypesData = noiseCompDocumentData.getGeneratorTypesData();
			
			generatorTypesData.clear();
			
			//ModulGeneratorTypeData mainModulTypeData = noiseCompDocumentData.getMainModulTypeData();
			
			//mainModulTypeData.clearData();
			
			//Generators mainGenerators = mainModulTypeData.getGen();
			float frameRate = noiseCompDocumentData.getFrameRate();
			
			//-----------------------------------------------------
			// Loading NoiseComp XML file:
			
			ModulGeneratorTypeData mainModulTypeData;
			
			mainModulTypeData = LoadFileOperationLogic.loadNoiseCompFile(generatorTypesData,
																		 //mainModulTypeData,
																		 fileName,
																		 frameRate);


			//-----------------------------------------------------
			noiseCompDocumentData.setMainModulTypeData(mainModulTypeData);
			noiseCompDocumentData.selectMainModul();
		} 
		//catch (FileNotFoundException ex) 
		//{
		//	throw new FileIOException(FileIOException.ERR_NOSUCHFILE, fileName);
		//} 
		catch (Exception ex) 
		{
			throw new FileIOException(FileIOException.ERR_UNKNOWN, fileName);
		}
				
		return noiseCompDocumentData;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.FileSaver#save(org.bs.mdi.DocumentData, java.lang.String)
	 */
	public void save(DocumentData data, String fileName) 
	throws FileIOException 
	{
		NoiseCompDocumentData noiseCompDocumentData = (NoiseCompDocumentData)data;
		
		GeneratorTypesData generatorTypesData = noiseCompDocumentData.getGeneratorTypesData();
		
		///Generators generators = soundData.getGenerators();
		ModulGeneratorTypeData mainModulTypeData = noiseCompDocumentData.getMainModulTypeData();
		//Generators mainGenerators = mainModulTypeData.getGenerators();
		
		try 
		{
			SaveFileOperationLogic.saveFile(generatorTypesData,
											mainModulTypeData,
											fileName);
		} 
		//catch (FileNotFoundException ex) 
		//{
		//	// A FileNotFoundException while writing a file usually means "access denied" 
		//	throw new FileIOException(FileIOException.ERR_NOACCESS, fileName);
		//} 
		//catch (IOException ex) 
		//{			
		//	throw new FileIOException(FileIOException.ERR_UNKNOWN, fileName);
		//}
		catch (XMLPortException ex)
		{			
			throw new FileIOException(FileIOException.ERR_UNKNOWN, fileName);
		}
	}
	
	public void export(DocumentData data) 
	{
		Application.getMainWindow().showMessage(MainWindow.INFO, null, 
												"This is just a dummy exporting function.");
	}

	public FileFormat[] getSupportedFormats() 
	{
		return formats;
	}

	public boolean canHandle(String filename) 
	{
		return formats[0].accept(filename);
	}
	
	public String getDescription() 
	{
		return Application.tr("NoiseComp XML File");
	}
}
