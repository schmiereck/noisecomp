package de.schmiereck.noiseComp;

import java.awt.Container;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphicException;

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
public class MainView
{
	private DesktopGraphic multiBufferGraphic;
	
	/**
	 * Constructor.
	 * @throws MultiBufferFullScreenGraphicException
	 * 
	 */
	public MainView(boolean useFullScreen, MainModel documentModel) 
	throws MultiBufferFullScreenGraphicException
	{
		this(useFullScreen, documentModel, 
			 MultiBufferFullScreenGraphic.createFrameView(useFullScreen));
	}
	
	/**
	 * Constructor.
	 * @throws MultiBufferFullScreenGraphicException
	 * 
	 */
	public MainView(boolean useFullScreen, MainModel documentModel,
					Container frameView) 
	throws MultiBufferFullScreenGraphicException
	{
		DesktopControllerData controllerData = documentModel.getControllerData();
		
		this.multiBufferGraphic = new DesktopGraphic(useFullScreen);
		
		this.multiBufferGraphic.initGraphicBuffer(frameView);
		
		this.multiBufferGraphic.initGrafic(controllerData);
		
		documentModel.getInputListener().setGraphic(this.multiBufferGraphic);
		this.multiBufferGraphic.addInputListener(documentModel.getInputListener(), useFullScreen);
	}

	/**
	 * @return returns the {@link #multiBufferGraphic}.
	 */
	public DesktopGraphic getMultiBufferGraphic()
	{
		return this.multiBufferGraphic;
	}
}
