package de.schmiereck.noiseComp.file;

import org.w3c.dom.Node;

import de.schmiereck.noiseComp.generator.Generator;

/**
 * Load-File-Generator Node Data.
 *
 * @author smk
 * @version 21.02.2004
 */
public class LoadFileGeneratorNodeData
{
	private Generator generator;
	private Node generatorNode;
	

	/**
	 * Constructor.
	 * 
	 * @param generator
	 * @param generatorNode
	 */
	public LoadFileGeneratorNodeData(Generator generator, Node generatorNode)
	{
		this.generator = generator;
		this.generatorNode = generatorNode;
	}

	/**
	 * @return the attribute {@link #generator}.
	 */
	public Generator getGenerator()
	{
		return this.generator;
	}
	/**
	 * @return the attribute {@link #generatorNode}.
	 */
	public Node getGeneratorNode()
	{
		return this.generatorNode;
	}
}
