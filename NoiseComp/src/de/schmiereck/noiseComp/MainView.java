package de.schmiereck.noiseComp;

import java.awt.Container;
import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
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
	
	private DesktopInputListener inputListener;
	
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
	public MainView(boolean useFullScreen, 
					MainModel documentModel,
					Container frameView) 
	throws MultiBufferFullScreenGraphicException
	{
		DesktopControllerData controllerData = documentModel.getControllerData();
		
		this.multiBufferGraphic = new DesktopGraphic(useFullScreen);
		
		this.multiBufferGraphic.initGraphicBuffer(frameView);
		
		this.multiBufferGraphic.initGrafic(controllerData);
		
		this.inputListener = new DesktopInputListener();

		this.inputListener.setGameControllerLogic(documentModel.getControllerLogic());
		
		this.inputListener.setGraphic(this.multiBufferGraphic.getScaleX(),
									  this.multiBufferGraphic.getScaleY(),
									  this.multiBufferGraphic.getCenterX(),
									  this.multiBufferGraphic.getCenterY());
		
		this.multiBufferGraphic.addInputListener(this.inputListener, useFullScreen);
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
