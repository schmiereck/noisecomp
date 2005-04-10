package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.ActionConverter;
import org.bs.mdi.Application;
//import org.bs.mdi.DocumentDataFactory;
//import org.bs.mdi.DocumentViewFactory;
//import org.bs.mdi.DocumentWindowFactory;
import org.bs.mdi.FileIOModule;
import org.bs.mdi.MainWindow;
import org.bs.mdi.Resources;
import org.bs.mdi.RootData;
import org.bs.mdi.RootView;

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
public class MainLogic
extends Application
{
	public static void main(String args[]) 
	{
		System.out.println("NoiseComp V0.1");
		
		MainLogic editor = new MainLogic();
		
		editor.run(args);
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#getName()
	 */
	public String getName() 
	{ 
		return "NoiseComp V0.1"; 
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createFileIOModules()
	 */
	protected FileIOModule[] createFileIOModules() 
	{
		FileIOModule[] modules = 
		{ 
			new NoiseCompFileIOModule() 
		};
		return modules;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createActionConverters()
	 */
	protected ActionConverter[] createActionConverters() 
	{
		ActionConverter[] converters = 
		{ 
				new NoiseCompActionConverter() 
		};
		
		return converters;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createMainWindow()
	 */
	protected MainWindow createMainWindow() 
	{
		return new NoiseCompMainWindow();
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createResources()
	 */
	protected Resources createResources() 
	{
		Resources retResources;
		
		try 
		{
			retResources = new NoiseCompResources();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			
			retResources = null;
		}
		
		return retResources;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createDocumentDataFactory()
	protected DocumentDataFactory createDocumentDataFactory() 
	{
		return new NoiseCompDocumentDataFactory();
	}
	 */
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createDocumentViewFactory()
	protected DocumentViewFactory createDocumentViewFactory() 
	{
		return new NoiseCompDocumentViewFactory();
	}
	 */
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createDocumentWindowFactory()
	protected DocumentWindowFactory createDocumentWindowFactory() 
	{
		return new NoiseCompDocumentWindowFactory();
	}
	 */

	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createRootData()
	 */
	public RootData createRootData()
	{
		return new NoiseCompDocumentData();//document);
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.Application#createRootView()
	 */
	public RootView createRootView()
	{
		return new NoiseCompDocumentView();//doc);
	}
}
