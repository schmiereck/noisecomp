/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;


/**
 * <p>
 * 	Timeline-Edit Model.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelineEditModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Generator Name.
	 */
	private String generatorName = null;

	/**
	 * {@link #generatorName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorNameChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Generator StartTimePos.
	 */
	private Float generatorStartTimePos = null;

	/**
	 * {@link #generatorStartTimePos} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorStartTimePosChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Generator EndTimePos.
	 */
	private Float generatorEndTimePos = null;

	/**
	 * {@link #generatorEndTimePos} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorEndTimePosChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final InputsTabelModel inputsTabelModel = new InputsTabelModel();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelineEditModel()
	{
		
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorName}.
	 */
	public String getGeneratorName()
	{
		return this.generatorName;
	}

	/**
	 * @param generatorName 
	 * 			to set {@link #generatorName}.
	 */
	public void setGeneratorName(String generatorName)
	{
		this.generatorName = generatorName;
		
		// Notify listeners.
		this.generatorNameChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorNameChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorNameChangedNotifier()
	{
		return this.generatorNameChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorStartTimePos}.
	 */
	public Float getGeneratorStartTimePos()
	{
		return this.generatorStartTimePos;
	}

	/**
	 * @param generatorStartTimePos 
	 * 			to set {@link #generatorStartTimePos}.
	 */
	public void setGeneratorStartTimePos(Float startTimePos)
	{
		this.generatorStartTimePos = startTimePos;
		
		// Notify listeners.
		this.generatorStartTimePosChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorEndTimePos}.
	 */
	public Float getGeneratorEndTimePos()
	{
		return this.generatorEndTimePos;
	}

	/**
	 * @param generatorEndTimePos 
	 * 			to set {@link #generatorEndTimePos}.
	 */
	public void setGeneratorEndTimePos(Float endTimePos)
	{
		this.generatorEndTimePos = endTimePos;
		
		// Notify listeners.
		this.generatorEndTimePosChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorStartTimePosChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorStartTimePosChangedNotifier()
	{
		return this.generatorStartTimePosChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorEndTimePosChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorEndTimePosChangedNotifier()
	{
		return this.generatorEndTimePosChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputsTabelModel}.
	 */
	public TableModel getInputsTabelModel()
	{
		return this.inputsTabelModel;
	}

	/**
	 * Clear Inputs.
	 */
	public void clearInputs()
	{
		this.inputsTabelModel.clearInputs();
	}

	/**
	 * @param inputData
	 * 			is the Input Data.
	 */
	public void addInputData(InputData inputData)
	{
		this.inputsTabelModel.addInputData(inputData);
	}
}
