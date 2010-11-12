/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.timeline.Timeline;


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
	 * Edited timeline.<br/>
	 * <code>null</code> if new timeline is edited.
	 */
	private Timeline timeline = null;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Generator.
	 */
	private GeneratorTypeData generatorTypeData = null;

	/**
	 * {@link #generatorTypeData} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorTypeDataChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<GeneratorTypeSelectItem> generatorTypeSelectItems = new Vector<GeneratorTypeSelectItem>();

	/**
	 * {@link #generatorTypeSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorTypeSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();

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
	 * 			returns the {@link #timeline}.
	 */
	public Timeline getTimeline()
	{
		return this.timeline;
	}

	/**
	 * @param timeline 
	 * 			to set {@link #timeline}.
	 */
	public void setTimeline(Timeline timeline)
	{
		this.timeline = timeline;
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
		if (CompareUtils.compareWithNull(this.generatorName, generatorName) == false)
		{
			this.generatorName = generatorName;
			
			// Notify listeners.
			this.generatorNameChangedNotifier.notifyModelPropertyChangedListeners();
		}
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
		if (CompareUtils.compareWithNull(this.generatorStartTimePos, startTimePos) == false)
		{
			this.generatorStartTimePos = startTimePos;
			
			// Notify listeners.
			this.generatorStartTimePosChangedNotifier.notifyModelPropertyChangedListeners();
		}
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
		if (CompareUtils.compareWithNull(this.generatorEndTimePos, endTimePos) == false)
		{
			this.generatorEndTimePos = endTimePos;
			
			// Notify listeners.
			this.generatorEndTimePosChangedNotifier.notifyModelPropertyChangedListeners();
		}
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
	 * 			returns the {@link #generatorTypeSelectItems}.
	 */
	public List<GeneratorTypeSelectItem> getGeneratorTypeSelectItems()
	{
		return this.generatorTypeSelectItems;
	}

	/**
	 * @param generatorTypeSelectItems 
	 * 			to set {@link #generatorTypeSelectItems}.
	 */
	public void setGeneratorTypeSelectItems(List<GeneratorTypeSelectItem> generatorTypeSelectItems)
	{
		if (CompareUtils.compareWithNull(this.generatorTypeSelectItems, generatorTypeSelectItems) == false)
		{
			this.generatorTypeSelectItems = generatorTypeSelectItems;
			
			// Notify Listeners.
			this.generatorTypeSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypeSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorTypeSelectItemsChangedNotifier()
	{
		return this.generatorTypeSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypeData}.
	 */
	public GeneratorTypeData getGeneratorTypeData()
	{
		return this.generatorTypeData;
	}

	/**
	 * @param generatorTypeData 
	 * 			to set {@link #generatorTypeData}.
	 */
	public void setGeneratorTypeData(GeneratorTypeData generatorTypeData)
	{
		if (CompareUtils.compareWithNull(this.generatorTypeData, generatorTypeData) == false)
		{
			this.generatorTypeData = generatorTypeData;
			
			// Notify Listeners.
			this.generatorTypeDataChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorTypeDataChangedNotifier()
	{
		return this.generatorTypeDataChangedNotifier;
	}
}
