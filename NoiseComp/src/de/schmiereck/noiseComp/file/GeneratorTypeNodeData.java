package de.schmiereck.noiseComp.file;

import org.w3c.dom.Node;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/*
 * Created on 17.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Verwaltet die beim laden im ersten Schritt eingelesenen
 * 	GeneratorTypes um zu wissen, wie sie heissen.
 * </p>
 * 
 * @author smk
 * @version <p>17.04.2005:	created, smk</p>
 */
public class GeneratorTypeNodeData
{
	private ModulGeneratorTypeData modulGeneratorTypeData;
	private Node generatorTypeNode;
	
	/**
	 * Constructor.
	 * 
	 */
	public GeneratorTypeNodeData(ModulGeneratorTypeData modulGeneratorTypeData,
								 Node generatorTypeNode)
	{
		this.modulGeneratorTypeData = modulGeneratorTypeData;
		this.generatorTypeNode = generatorTypeNode;
	}

	/**
	 * @return returns the {@link #modulGeneratorTypeData}.
	 */
	protected ModulGeneratorTypeData getModulGeneratorTypeData()
	{
		return this.modulGeneratorTypeData;
	}

	/**
	 * @return returns the {@link #generatorTypeNode}.
	 */
	protected Node getGeneratorTypeNode()
	{
		return this.generatorTypeNode;
	}
}
