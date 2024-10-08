/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Input-Edit Model.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Generator.
	 */
	private InputTypeData inputTypeData = null;

	/**
	 * {@link #inputTypeData} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeDataChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<InputTypeSelectItem> inputTypeSelectItems = new Vector<InputTypeSelectItem>();

	/**
	 * {@link #inputTypeSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTypeSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Input Timeline.
	 */
	private Timeline inputTimeline = null;

	/**
	 * {@link #inputTimeline} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputTimelineChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<GeneratorSelectItem> generatorSelectItems = new Vector<GeneratorSelectItem>();

	/**
	 * {@link #generatorSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier generatorSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<ValueSelectItem> valueSelectItems = new Vector<ValueSelectItem>();

	/**
	 * {@link #valueSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier valueSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();

	/**
	 * Value.
	 */
	private String value = null;

	/**
	 * {@link #value} changed listeners.
	 */
	private final ModelPropertyChangedNotifier valueChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * ModuleInput Generator.
	 */
	private InputTypeData moduleInputTypeData = null;

	/**
	 * {@link #moduleInputTypeData} changed listeners.
	 */
	private final ModelPropertyChangedNotifier moduleInputTypeDataChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private List<ModuleInputTypeSelectItem> moduleInputTypeSelectItems = new Vector<ModuleInputTypeSelectItem>();

	/**
	 * {@link #moduleInputTypeSelectItems} changed listeners.
	 */
	private final ModelPropertyChangedNotifier moduleInputTypeSelectItemsChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	/**
	 * Selected-Timeline Model.
	 */
	final SelectedTimelineModel selectedTimelineModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model.
	 */
	public InputEditModel(final SelectedTimelineModel selectedTimelineModel)
	{
		//==========================================================================================
		this.selectedTimelineModel = selectedTimelineModel;
		
		//==========================================================================================
	}
	
	/**
	 * @return 
	 * 			returns the {@link #value}.
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * @param value 
	 * 			to set {@link #value}.
	 */
	public void setValue(String value)
	{
		//==========================================================================================
		if (CompareUtils.compareWithNull(this.value, value) == false)
		{
			this.value = value;
			
			// Notify listeners.
			this.valueChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #valueChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getValueChangedNotifier()
	{
		return this.valueChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorSelectItems}.
	 */
	public List<GeneratorSelectItem> getGeneratorSelectItems()
	{
		return this.generatorSelectItems;
	}

	/**
	 * @param generatorSelectItems 
	 * 			to set {@link #generatorSelectItems}.
	 */
	public void setGeneratorSelectItems(List<GeneratorSelectItem> generatorSelectItems)
	{
		//==========================================================================================
		this.generatorSelectItems = generatorSelectItems;
		
		// Notify Listeners.
		this.generatorSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getGeneratorSelectItemsChangedNotifier()
	{
		return this.generatorSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTimeline}.
	 */
	public Timeline getInputTimeline()
	{
		return this.inputTimeline;
	}

	/**
	 * @param inputTimeline 
	 * 			to set {@link #inputTimeline}.
	 */
	public void setInputTimeline(Timeline inputTimeline)
	{
		//==========================================================================================
		// Check is not possible, because the selection list changed, but the selected item
		// is sometimes the same.
		//if (this.inputTimeline != inputTimeline)
		{
			this.inputTimeline = inputTimeline;
			
			// Notify Listeners.
			this.inputTimelineChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTimelineChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTimelineChangedNotifier()
	{
		return this.inputTimelineChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectItems}.
	 */
	public List<InputTypeSelectItem> getInputTypeSelectItems()
	{
		return this.inputTypeSelectItems;
	}

	/**
	 * @param inputTypeSelectItems 
	 * 			to set {@link #inputTypeSelectItems}.
	 */
	public void setInputTypeSelectItems(List<InputTypeSelectItem> inputTypeSelectItems)
	{
		//==========================================================================================
		if (this.inputTypeSelectItems != inputTypeSelectItems)
		{
			this.inputTypeSelectItems = inputTypeSelectItems;
			
			// Notify Listeners.
			this.inputTypeSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeSelectItemsChangedNotifier()
	{
		return this.inputTypeSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeData}.
	 */
	public InputTypeData getInputTypeData()
	{
		return this.inputTypeData;
	}

	/**
	 * @param inputTypeData 
	 * 			to set {@link #inputTypeData}.
	 */
	public void setInputTypeData(InputTypeData inputTypeData)
	{
		//==========================================================================================
		// Check is not possible, because the selection list changed, but the selected item
		// is sometimes the same.
		//if (this.inputTypeData != inputTypeData)
		{
			this.inputTypeData = inputTypeData;
			
			// Notify Listeners.
			this.inputTypeDataChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputTypeDataChangedNotifier()
	{
		return this.inputTypeDataChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeSelectItems}.
	 */
	public List<ModuleInputTypeSelectItem> getModuleInputTypeSelectItems()
	{
		return this.moduleInputTypeSelectItems;
	}

	/**
	 * @param moduleInputTypeSelectItems 
	 * 			to set {@link #moduleInputTypeSelectItems}.
	 */
	public void setModuleInputTypeSelectItems(List<ModuleInputTypeSelectItem> moduleInputTypeSelectItems)
	{
		//==========================================================================================
		this.moduleInputTypeSelectItems = moduleInputTypeSelectItems;
		
		// Notify Listeners.
		this.moduleInputTypeSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModuleInputTypeSelectItemsChangedNotifier()
	{
		return this.moduleInputTypeSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeData}.
	 */
	public InputTypeData getModuleInputTypeData()
	{
		return this.moduleInputTypeData;
	}

	/**
	 * @param moduleInputTypeData 
	 * 			to set {@link #moduleInputTypeData}.
	 */
	public void setModuleInputTypeData(InputTypeData moduleInputTypeData)
	{
		//==========================================================================================
		// Check is not possible, because the selection list changed, but the selected item
		// is sometimes the same.
		//if (this.moduleInputTypeData != moduleInputTypeData)
		{
			this.moduleInputTypeData = moduleInputTypeData;
			
			// Notify Listeners.
			this.moduleInputTypeDataChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModuleInputTypeDataChangedNotifier()
	{
		return this.moduleInputTypeDataChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #valueSelectItems}.
	 */
	public List<ValueSelectItem> getValueSelectItems()
	{
		return this.valueSelectItems;
	}

	/**
	 * @param valueSelectItems 
	 * 			to set {@link #valueSelectItems}.
	 */
	public void setValueSelectItems(List<ValueSelectItem> valueSelectItems)
	{
		//==========================================================================================
		this.valueSelectItems = valueSelectItems;
		
		this.valueSelectItemsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #valueSelectItemsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getValueSelectItemsChangedNotifier()
	{
		return this.valueSelectItemsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedTimelineModel}.
	 */
	public SelectedTimelineModel getSelectedTimelineModel()
	{
		return this.selectedTimelineModel;
	}
}
