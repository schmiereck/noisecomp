package de.schmiereck.noiseComp.generator;
/*
 * Created on 27.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Interface das von Objekten implementiert werden muss, die vom
 * 	{@link de.schmiereck.noiseComp.generator.GeneratorChangeObserver} über 
 * 	Änderungen an dem überwachten Generator benachrichtigt werden wollen.
 * </p>
 * 
 * @author smk
 * @version <p>27.03.2005:	created, smk</p>
 */
public interface GeneratorChangeListenerInterface
{

	/**
	 * Wird aufgerufen, wenn sich der angegebene Genrator geändert hat.
	 * 
	 * @param generator
	 * 			ist der Generator, der die Veränderung meldet.
	 * @param changedStartTimePos
	 * 			ist der Zeitpunkt ab dem sich Daten im angegebenen Generator geändert haben.
	 * @param changedEndTimePos
	 * 			ist der Zeitpunkt bis zu dem sich Daten im angegebenen Generator geäändert haben.
	 */
	void notifyGeneratorChanged(Generator generator, 
	                            float changedStartTimePos, float changedEndTimePos);
}
