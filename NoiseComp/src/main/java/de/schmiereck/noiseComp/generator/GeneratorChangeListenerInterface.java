package de.schmiereck.noiseComp.generator;
/*
 * Created on 27.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Interface that Objects has to implements, wants to notify from
 * 	{@link de.schmiereck.noiseComp.generator.GeneratorChangeObserver}  
 * 	if the generator data changed.
 * </p>
 * 
 * @author smk
 * @version <p>27.03.2005:	created, smk</p>
 */
public interface GeneratorChangeListenerInterface
{

	/**
	 * Called if the given generator changed.
	 * 
	 * @param generator
	 * 			is the changed generator.
	 * @param changedStartTimePos
	 * 			is the start time pos the data in generator changed.
	 * @param changedEndTimePos
	 * 			is the end time pos the data in generator changed.
	 */
	void notifyGeneratorChanged(Generator generator, 
	                            float changedStartTimePos, float changedEndTimePos);
}
