package de.schmiereck.noiseComp.mdi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import org.bs.mdi.Action;
import org.bs.mdi.Data;
import org.bs.mdi.Document;
//import org.bs.mdi.DocumentData;
import org.bs.mdi.Printer;
import org.bs.mdi.RootData;
import org.bs.mdi_example.EditorAction;
import de.schmiereck.noiseComp.MainModel;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.EditData;
import de.schmiereck.noiseComp.generator.ASRPulseGenerator;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.WaveGenerator;
import de.schmiereck.screenTools.Runner;
import de.schmiereck.screenTools.controller.ControllerSchedulerLogic;

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
public class NoiseCompDocumentData
//extends Data 
extends RootData
{
	private MainModel mainModel;
	
	private ControllerSchedulerLogic gameSchedulerLogic;
	
	private Printer printer = new NoiseCompDocumentPrinter();
	
	private GeneratorTypesData generatorTypesData;
	
	public NoiseCompDocumentData()//Document document) 
	{
		super();//document);

		//======================================================================
		this.generatorTypesData  = new GeneratorTypesData();

		NoiseCompDocumentData.createBaseGeneratorTypes(this.generatorTypesData);
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("Main-Modul");

		this.generatorTypesData.addGeneratorTypeData(mainModulGeneratorTypeData);
		
		//----------------------------------------------------------------------
		// Build:
		
		boolean useFullScreen = false;
		//boolean useFullScreen = true;

		this.mainModel = new MainModel(this.generatorTypesData,
									   mainModulGeneratorTypeData);
		
		
		//----------------------------------------------------------------------
		// run:

		int controllerTargetFramesPerSecond = 16;
		
		this.gameSchedulerLogic = new ControllerSchedulerLogic(this.mainModel.getControllerData(), 
															   this.mainModel.getControllerLogic(), 
															   this.mainModel.getWaiter(), 
															   controllerTargetFramesPerSecond);
		
		this.mainModel.getControllerLogic().initGameData(this.mainModel.getControllerData());

		this.gameSchedulerLogic.startThread();
		/*
		RunnerSchedulers runnerSchedulers;
		
		runnerSchedulers = Runner.start(this.getControllerData(), 
										this.mainView.getControllerLogic(), 
										this.mainView.getMultiBufferGraphic(), 
										this.mainView.getInputListener(), 
										this.mainView.getWaiter(), 24, 16,
										false, 
										false,
										-1, -1);
		
		//Runner.stop(runnerSchedulers);
		 * 
		 */
		
		//document.setDirty(true);
	}

	private static void createBaseGeneratorTypes(GeneratorTypesData generatorTypesData)
	{
		generatorTypesData.clear();
		
		generatorTypesData.addGeneratorTypeData(FaderGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(MixerGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(OutputGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(SinusGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(RectangleGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(CutGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(WaveGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(ASRPulseGenerator.createGeneratorTypeData());
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.ActionProcessor#applyAction(org.bs.mdi.Action)
	 */
	public void applyAction(Action action) 
	{
		/*
		if (!(action instanceof EditorAction)) 
			return;
		
		EditorAction ea = (EditorAction)action;	
		StringBuffer sb = new StringBuffer(text);
		
		switch (ea.getType()) 
		{
			case EditorAction.INSERT:
			case EditorAction.PASTE:								
				sb.delete(ea.getStartOffset(), ea.getEndOffset());
				if (ea.getNewText() != null) sb.insert(ea.getStartOffset(), ea.getNewText());				
				text = sb.toString();
				break;
			case EditorAction.REMOVE:
			case EditorAction.CUT:
			case EditorAction.DELETE:
				sb.delete(ea.getStartOffset(), ea.getEndOffset());
				text = sb.toString();
				break;
		}
		*/		
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.ActionProcessor#undoAction(org.bs.mdi.Action)
	 */
	public void undoAction(Action action) 
	{
		/*
		if (!(action instanceof EditorAction)) 
			return;
		
		EditorAction ea = (EditorAction)action;			
		StringBuffer sb = new StringBuffer(text);
		
		switch (ea.getType()) 
		{
			case EditorAction.INSERT:
			case EditorAction.PASTE:
				sb.delete(ea.getStartOffset(), ea.getStartOffset() + ea.getNewText().length());				
				if (ea.getOldText() != null) sb.insert(ea.getStartOffset(), ea.getOldText());
				text = sb.toString();				
				break;
			case EditorAction.REMOVE:
			case EditorAction.CUT:
			case EditorAction.DELETE:				
				sb.insert(ea.getStartOffset(), ea.getOldText());
				text = sb.toString();				
				break;
		}
		*/		
	}
	
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentData#getPrinter()
	 */
	public Printer getPrinter() 
	{
		return this.printer;
	}
	
	
	class NoiseCompDocumentPrinter 
	implements Printer 
	{
		
		Font printerFont = new Font("Monospaced", Font.PLAIN, 12);
		int lineHeight = 14;
		
		public int getNumPages(PageFormat format) 
		{
			/*
			int lines = 1;									
			int h = (int)format.getImageableHeight(); 
			
			for (int i=0; i<text.length(); i++) 
			{
				if (text.charAt(i) == '\n') lines++;
			}
			
			return ((lines*14)/h)+1;
			*/
			return 1;
		}
	
		public boolean print(java.awt.Graphics g, PageFormat format, int pageindex) 
		{
			String s = null;
			int originX = (int)format.getImageableX();
			int originY = (int)format.getImageableY();
			int height = (int)format.getImageableHeight();
			int width = (int)format.getImageableWidth();			
			int linesPerPage = height/14; 
			Graphics2D g2d = (Graphics2D)g;
				
			Rectangle oldClip = g2d.getClipBounds();
			
			g2d.setClip(originX, originY, 
						width, oldClip.y + oldClip.height - originY);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
								 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
								 RenderingHints.VALUE_FRACTIONALMETRICS_ON);

			g2d.setColor(Color.BLACK);
			g2d.setFont(printerFont);

			g2d.drawString("GeneratorTypes Count: " + getGeneratorTypesData().getSize(), 
						   50, 50);
			
			/*
			g2d.setColor(Color.BLACK);
			g2d.setFont(printerFont);
			int lineY = originY + lineHeight;
			int printedLines = 0;
			LineNumberReader reader = new LineNumberReader(new StringReader(text));
			try {
				for (int i=0; i<linesPerPage*pageindex; i++) {
					reader.readLine();		
				}
			} catch (IOException e) {}
			do {
				try {
					s = reader.readLine();								
					if (s != null) g2d.drawString(s, originX, lineY);
				} catch (IOException e) {
				}
				lineY += lineHeight;
				printedLines++;				
			} while (s != null && printedLines < linesPerPage);		
			g2d.setClip(oldClip);
			*/
			
			return true;
		}
	}

	public GeneratorTypesData getGeneratorTypesData()
	{
		return this.generatorTypesData;
	}

	public ModulGeneratorTypeData getMainModulTypeData()
	{
		EditData editData = this.mainModel.getControllerData().getEditData();
		
		return editData.getMainModulTypeData();
	}

	public float getFrameRate()
	{
		return this.mainModel.getSoundData().getFrameRate();
	}
	/**
	 * @return returns the {@link #mainModel}.
	 */
	public MainModel getMainModel()
	{
		return this.mainModel;
	}

	public void setMainModulTypeData(ModulGeneratorTypeData mainModulTypeData)
	{
		EditData editData = this.mainModel.getControllerData().getEditData();
		
		editData.setMainModulTypeData(mainModulTypeData);
	}

	public void selectMainModul() //Generators mainGenerators)
	{
		//this.mainModel.getControllerLogic().updateEditModul(mainGenerators);
		//this.mainModel.getControllerLogic().selectGeneratorsToEdit();
		this.mainModel.getControllerLogic().selectMainModul();
	}
}
