package de.schmiereck.noiseComp.generator;
/*
 * Created on 27.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Interface das von Objekten implementiert werden muss, die vom
 * 	{@link de.schmiereck.noiseComp.generator.GeneratorChangeObserver} �ber 
 * 	�nderungen an dem �berwachten Generator benachrichtigt werden wollen.
 * </p>
 * 
 * @author smk
 * @version <p>27.03.2005:	created, smk</p>
 */
public interface GeneratorChangeListenerInterface
{

	/**
	 * Wird aufgerufen, wenn sich der angegebene Genrator ge�ndert hat.
	 * 
	 * @param generator
	 * 			ist der Generator, der die Ver�nderung meldet.
	 * @param startTimePos
	 * 			ist der Zeitpunkt ab dem sich Daten im angegebenen Generator ge�ndert haben.
	 * @param endTimePos
	 * 			ist der Zeitpunkt bis zu dem sich Daten im angegebenen Generator ge�ndert haben.
	 */
	void notifyGeneratorChanged(Generator generator, float startTimePos, float endTimePos);
}
