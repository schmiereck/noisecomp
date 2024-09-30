package de.schmiereck.screenTools.controller;

import java.util.Iterator;
import java.util.Vector;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * TODO docu
 *
 * @author smk
 * @version 08.01.2004
 */
public abstract class ControllerLogic
{
	private SchedulerWaiter waiter;

	/**
	 * List of {@link DataChangedListener}-Objects.
	 */
	private DataChangedObserver dataChangedObserver;

	public ControllerLogic(DataChangedObserver dataChangedObserver,
						   SchedulerWaiter waiter)
	{
		this.dataChangedObserver = dataChangedObserver;
		this.waiter = waiter;
	}
	
	/**
	 * @param controllerData Daten zum Berechnen.
	 * @param actualWaitPerFramesMillis sind die Millisekunden, die seit der letzten Berechnung vergangen sind.
	 * @param runSeconds Sekunden die seit der letzten Berechnung vergangen sind.
	 */
	public abstract void calc(ControllerData controllerData, long actualWaitPerFramesMillis, double runSeconds);
	
	/**
	 * Initialisieren der Objekte in den Daten.
	 *
	 */
	public abstract void initGameData(ControllerData controllerData);

	/**
	 * Öffnen eines Sockets, so das die Anwendung als Server läuft.<br>
	 * Wartet ab hier auf eingehende Verbindungen von Clients.
	 * 
	 * @see #closeGameServerConnection()
	 */
	public abstract void openGameServerConnection();

	/**
	 * Schliessen des Sockets, so das die Anwendung nicht mehr als Server läuft.
	 * 
	 * @see #openGameServerConnection()
	 */
	public abstract void closeGameServerConnection();
	
	/**
	 * Öffnen eines Sockets, so das die Anwendung als Client läuft.
	 * 
	 * @see #closeGameClientConnection()
	 */
	public abstract void openGameClientConnection();
	
	/**
	 * Schliessen des Sockets, so das die Anwendung nicht mehr als Client läuft.
	 * 
	 * @see #openGameClientConnection()
	 */
	public abstract void closeGameClientConnection();
	
	/**
	 * @return true, wenn bereits eine Verbindung des Clients zum Server besteht.
	 */
	public abstract boolean getIsGameClientConnected();
	
	/**
	 * @return true, wenn bereits eine Verbindung des Servers zum Clients besteht.
	 */
	public abstract boolean getIsGameServerConnected();
	
	/**
	 * Wartet bis jemand den 'SchedulerWaiter' (@link #waiter) mit .notify() aufweckt.
	 * 
	 * @see #stopWaitGame()
	 */
	public void startWaitGame()
	{
		synchronized (this.waiter)
		{
			try
			{
				System.out.println("START WAIT");
				this.waiter.wait();
				System.out.println("END WAIT 1");
			}
			catch (InterruptedException e1)
			{
				System.out.println("END WAIT 2");
				e1.printStackTrace();
			}
			finally
			{
				if (this.getIsGameClientConnected() == true)
				{	
					this.closeGameClientConnection();
				}
				if (getIsGameServerConnected() == true)
				{	
					this.closeGameServerConnection();
				}
			}
		}
	}

	/**
	 * @see #startWaitGame()
	 */
	public void stopWaitGame()
	{
		synchronized (this.waiter)
		{
			this.waiter.notify();
		}		
	}

	public void addDataChangedListener(DataChangedListener dataChangedListener)
	{
		this.dataChangedObserver.addDataChangedListener(dataChangedListener);
	}
	
	public void dataChanged(ControllerData controllerData)
	{
		this.dataChangedObserver.dataChanged(controllerData);
	}
	
	public void dataChanged(ControllerData controllerData,
							int posX, int posY, int sizeX, int sizeY)
	{
		this.dataChangedObserver.dataChanged(controllerData,
											 posX, posY, sizeX, sizeY);
	}
	
	/**
	 * @return returns the {@link #dataChangedObserver}.
	 */
	public DataChangedObserver getDataChangedObserver()
	{
		return this.dataChangedObserver;
	}
}
