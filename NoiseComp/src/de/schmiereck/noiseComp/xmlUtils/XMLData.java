package de.schmiereck.noiseComp.xmlUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * <p>
 * 	Basisfunktionen für einfache Zugriffe auf XML Daten.
 * </p> 
 * 
 * @author smk
 * @version <p>16.01.2004:	created</p>
 * 			<p>2004-02-21: Changeed for JDK1.5: org.apache.xpath.XPathAPI to com.sun.org.apache.xpath.internal.XPathAPI, smk</p>
 */
public class XMLData
{
	static public Document createDocument()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// XML Syntax Überprüfung einschalten
		//factory.setValidating(true);   
		//factory.setNamespaceAware(true);

		DocumentBuilder builder;
		try
		{
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException ex)
		{
			throw new XMLException("build document to read", ex);
		}

		//InputStream multilangsXMLStream = this.getClass().getResourceAsStream(fileName);

		Document document;

		document = builder.newDocument();
		
		return document;
	}
	
	/**
	 * @throws XMLException
	 * 
	 * @param contextNode
	 * @param xPathStr
	 * @return
	 */
	static public Node selectSingleNode(Node contextNode, String xPathStr)
	{
		Node retNode;
		try
		{
			// Changeed for JDK1.5, smk
			retNode = com.sun.org.apache.xpath.internal.XPathAPI.selectSingleNode(contextNode, xPathStr);
			//retNode = org.apache.xpath.XPathAPI.selectSingleNode(contextNode, xPathStr);
		}
		catch (TransformerException ex)
		{
			throw new XMLException("select the node \"" +
											contextNode.getNamespaceURI() + xPathStr +
											"\"", ex);
		}
		return retNode;
	}

	/**
	 * @throws XMLException
	 * 
	 * @param contextNode
	 * @param xPathStr
	 * @return
	 */
	static public NodeList selectNodeList(Node contextNode, String xPathStr)
	{
		NodeList retNodeList;
		try
		{
			// Changeed for JDK1.5, smk
			retNodeList = com.sun.org.apache.xpath.internal.XPathAPI.selectNodeList(contextNode, xPathStr);
			//retNodeList = org.apache.xpath.XPathAPI.selectNodeList(contextNode, xPathStr);
		}
		catch (TransformerException ex)
		{
			throw new XMLException("select the node list \"" +
											contextNode.getNamespaceURI() + xPathStr +
											"\"", ex);
		}
		return retNodeList;
	}

	/**
	 * @throws XMLException
	 * 
	 * @param contextNode
	 * @param xPathStr
	 * @return
	 */
	static public NodeIterator selectNodeIterator(Node contextNode, String xPathStr)
	{
		NodeIterator retIterator;
		try
		{
			// Changeed for JDK1.5, smk
			retIterator = com.sun.org.apache.xpath.internal.XPathAPI.selectNodeIterator(contextNode, xPathStr);
			//retIterator = org.apache.xpath.XPathAPI.selectNodeIterator(contextNode, xPathStr);
		}
		catch (TransformerException ex)
		{
			throw new XMLException("select the node iterator \"" +
											contextNode.getNamespaceURI() + xPathStr +
											"\"", ex);
		}
		return retIterator;
	}

	/**
	 * @param contextNode
	 * @param xPathStr
	 * @return
	 */
	public static String selectSingleNodeText(Node contextNode, String xPathStr)
	{
		String ret;
		
		Node node = selectSingleNode(contextNode, xPathStr + "/text()");
		
		if (node != null)
		{
			ret = node.getNodeValue();
		}
		else
		{
			ret = null;
		}
		
		return ret;
	}

	/**
	 * @param contextNode
	 * @param xPathStr
	 * @return
	 */
	public static Float selectSingleNodeFloat(Node contextNode, String xPathStr)
	{
		Float ret;
		
		Node node = selectSingleNode(contextNode, xPathStr + "/text()");
		
		if (node != null)
		{
			String text = node.getNodeValue();
			
			if (text != null)
			{	
				ret = Float.valueOf(text);
			}
			else
			{	
				ret = null;
			}
}
		else
		{
			ret = null;
		}
		
		return ret;
	}

	/**
	 * @param contextNode
	 * @param xPathStr
	 * @return
	 */
	public static Integer selectSingleNodeInteger(Node contextNode, String xPathStr)
	{
		Integer ret;
		
		Node node = selectSingleNode(contextNode, xPathStr + "/text()");
		
		if (node != null)
		{
			String text = node.getNodeValue();

			if (text != null)
			{	
				ret = Integer.valueOf(text);
			}
			else
			{	
				ret = null;
			}
		}
		else
		{
			ret = null;
		}
		
		return ret;
	}
	
	public static Node appendNode(Document document, Node contextNode, String nodeName)
	{
		Node node = document.createElement(nodeName);
	
		contextNode.appendChild(node);
		
		return node;
	}

	public static Node appendTextNode(Document document, Node contextNode, String nodeName, String text)
	{
		Node node = XMLData.appendNode(document, contextNode, nodeName);
		
		if (text != null)
		{	
			Node textNode = document.createTextNode(text);

			node.appendChild(textNode);
		}
		
		return node;
	}

	public static Node appendCharDataNode(Document document, Node contextNode, String nodeName, String charData)
	{
		Node node = XMLData.appendNode(document, contextNode, nodeName);
		
		if (charData != null)
		{	
			Node textNode = document.createCDATASection(charData);

			node.appendChild(textNode);
		}
		
		return node;
	}

	public static Node appendFloatNode(Document document, Node contextNode, String nodeName, Float value)
	{
		Node textNode;
		
		if (value != null)
		{	
			String text = String.valueOf(value);

			textNode = XMLData.appendTextNode(document, contextNode, nodeName, text);
		}
		else
		{	
			textNode = XMLData.appendNode(document, contextNode, nodeName);
		}
		
		return textNode;
	}

	public static Node appendFloatNode(Document document, Node contextNode, String nodeName, float value)
	{
		String text = String.valueOf(value);
		
		Node textNode = XMLData.appendTextNode(document, contextNode, nodeName, text);

		return textNode;
	}

	public static Node appendIntegerNode(Document document, Node contextNode, String nodeName, Integer value)
	{
		Node textNode;
		
		if (value != null)
		{	
			String text = String.valueOf(value);
		
			textNode = XMLData.appendTextNode(document, contextNode, nodeName, text);
		}
		else
		{	
			textNode = XMLData.appendNode(document, contextNode, nodeName);
		}

		return textNode;
	}

	public static Node appendIntegerNode(Document document, Node contextNode, String nodeName, int value)
	{
		String text = String.valueOf(value);
			
		Node textNode = XMLData.appendTextNode(document, contextNode, nodeName, text);

		return textNode;
	}
}
