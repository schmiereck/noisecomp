package de.schmiereck.noiseComp.file;

import org.w3c.dom.Node;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;

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
	private ModuleGeneratorTypeData moduleGeneratorTypeData;
	private Node generatorTypeNode;
	
	/**
	 * Constructor.
	 * 
	 */
	public GeneratorTypeNodeData(ModuleGeneratorTypeData moduleGeneratorTypeData,
								 Node generatorTypeNode)
	{
		this.moduleGeneratorTypeData = moduleGeneratorTypeData;
		this.generatorTypeNode = generatorTypeNode;
	}

	/**
	 * @return returns the {@link #moduleGeneratorTypeData}.
	 */
	protected ModuleGeneratorTypeData getModuleGeneratorTypeData()
	{
		return this.moduleGeneratorTypeData;
	}

	/**
	 * @return returns the {@link #generatorTypeNode}.
	 */
	protected Node getGeneratorTypeNode()
	{
		return this.generatorTypeNode;
	}
}
