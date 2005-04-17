package de.schmiereck.noiseComp.file;

import java.util.HashMap;
import java.util.Iterator;

/*
 * Created on 17.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Verwaltet eine Liste von {@link {@link GeneratorTypeNodeData}-Objekten.
 * </p>
 * 
 * @author smk
 * @version <p>17.04.2005:	created, smk</p>
 */
public class GeneratorTypeNodesData
{	
	/**
	 * Verwaltet eine Liste von {@link GeneratorTypeNodeData}-Objekten
	 * mit ihrem {@link GeneratorTypeNodeData#generatorTypeClassName} als Key.
	 */
	private HashMap generatorTypeNodes = new HashMap();

	/**
	 * @see #generatorTypeNodes
	 */
	public void clear()
	{
		this.generatorTypeNodes.clear();
	}

	/**
	 * @see #generatorTypeNodes
	 */
	public void addTempGeneratorTypeData(GeneratorTypeNodeData tempGeneratorTypeData)
	{
		this.generatorTypeNodes.put(tempGeneratorTypeData.getModulGeneratorTypeData().getGeneratorTypeClassName(),
									tempGeneratorTypeData);
	}

	/**
	 * @see #generatorTypeNodes
	 * @return
	 * 		<code>null</code> if the generator type is not found.
	 */
	public GeneratorTypeNodeData searchGeneratorTypeData(String generatorTypeClassName)
	{
		return (GeneratorTypeNodeData)this.generatorTypeNodes.get(generatorTypeClassName);
	}

	/**
	 * @see #generatorTypeNodes
	 * @return
	 * 		a iterator about the list of {@link GeneratorTypeNodeData} objects.
	 */
	public Iterator getGeneratorTypeNodesIterator()
	{
		return this.generatorTypeNodes.values().iterator();
	}
}
