package de.schmiereck.noiseComp.smkScreen;

import java.awt.Container;

import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopGraphic;
import de.schmiereck.noiseComp.smkScreen.desktopInput.DesktopInputListener;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphic;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphicException;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	ConsoleMain View.
 * </p>
 * <p>
 * 	Manages multi buffered grafik and input listeners.
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class MainView
{
	private DesktopGraphic multiBufferGraphic;
	
	private DesktopInputListener inputListener;
	
	/**
	 * Constructor.
	 * 
	 * @throws MultiBufferFullScreenGraphicException
	 */
	public MainView(boolean useFullScreen, MainController documentController) 
	throws MultiBufferFullScreenGraphicException
	{
		this(useFullScreen, 
		     documentController, 
			 MultiBufferFullScreenGraphic.createFrameView(useFullScreen));
	}
	
	/**
	 * Constructor.
	 * 
	 * @param useFullScreen
	 * 			<code>true</code>: Fullscreen benutzen.
	 * 
	 * @throws MultiBufferFullScreenGraphicException
	 */
	public MainView(boolean useFullScreen, 
					MainController documentController,
					Container frameView) 
	throws MultiBufferFullScreenGraphicException
	{
		DesktopControllerData controllerData = documentController.getControllerData();
		
		this.multiBufferGraphic = new DesktopGraphic(useFullScreen);
		
		this.multiBufferGraphic.initGraphicBuffer(frameView);
		
		this.multiBufferGraphic.initGrafic(controllerData);
		
		this.inputListener = new DesktopInputListener();

		this.inputListener.setGameControllerLogic(documentController.getControllerLogic());
		
		this.inputListener.setGraphic(this.multiBufferGraphic.getScaleX(),
									  this.multiBufferGraphic.getScaleY(),
									  this.multiBufferGraphic.getCenterX(),
									  this.multiBufferGraphic.getCenterY());
		
//		this.multiBufferGraphic.addInputListener(this.inputListener, useFullScreen);
	}

	/**
	 * @return returns the {@link #multiBufferGraphic}.
	 */
	public DesktopGraphic getMultiBufferGraphic()
	{
		return this.multiBufferGraphic;
	}

	/**
	 * @return returns the {@link #inputListener}.
	 */
	public DesktopInputListener getInputListener()
	{
		return this.inputListener;
	}
}
