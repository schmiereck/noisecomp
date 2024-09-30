package de.schmiereck.screenTools.graphic;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.schmiereck.screenTools.input.InputDeviceInterface;

/**
 * TODO docu
 *
 * @author smk
 * @version 07.01.2004
 */
public interface GraficInputListener
extends MouseListener, MouseMotionListener, KeyListener
{

	/**
	 * @param inputDevice
	 */
	void setInputDevice(InputDeviceInterface inputDevice);
}