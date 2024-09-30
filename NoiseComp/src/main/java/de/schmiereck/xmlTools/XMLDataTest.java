package de.schmiereck.xmlTools;


import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import junit.framework.TestCase;

/**
 * TODO docu
 *
 * @author smk
 * @version 17.01.2004
 */
public class XMLDataTest 
extends TestCase
{
	private Document document;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();

		this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		{
			Node rootNode = this.document.createElement("root");
			this.document.appendChild(rootNode);
	
			{
				Node elementsNode = this.document.createElement("elements");
				rootNode.appendChild(elementsNode);
	
				{
					Node elementNode = this.document.createElement("element");
					elementsNode.appendChild(elementNode);

					Node elementTextNode = this.document.createTextNode("ABC");
					elementNode.appendChild(elementTextNode);
				}
				{
					Node elementNode = this.document.createElement("element");
					elementsNode.appendChild(elementNode);

					Node elementTextNode = this.document.createTextNode("XYZ");
					elementNode.appendChild(elementTextNode);
				}
				{
					Node elementNode = this.document.createElement("element");
					elementsNode.appendChild(elementNode);

					Node elementTextNode = this.document.createTextNode("LMN");
					elementNode.appendChild(elementTextNode);
				}
			}
		}
	}

	/**
	 * Ein neues Dokument anlegen.
	 *
	 */
	public void testCreateDocument()
	{
		Document document = XMLData.createDocument();
		
		assertNotNull(document);
	}

	/**
	 * Einen einzelnen Node anhand eines XPath-Ausdrucks ausw�hlen.
	 *
	 */
	public void testSelectSingleNode()
	{
		Node elementNode = XMLData.selectSingleNode(this.document, "/root/elements/element[text()='XYZ']/text()");
		
		assertEquals("XYZ", elementNode.getNodeValue());
	}

	/**
	 * Eine Liste von Nodes anhand eines XPath-Ausdrucks ausw�hlen.
	 *
	 */
	public void testSelectNodeList()
	{
		NodeList elementNodeList = XMLData.selectNodeList(this.document, "/root/elements/element");
		
		assertEquals(3, elementNodeList.getLength());
	}

	/**
	 * Eine Liste von Nodes anhand eines XPath-Ausdrucks ausw�hlen.
	 *
	 */
	public void testSelectNodeIterator()
	{
		//NodeIterator elementNodeIterator = XMLData.selectNodeIterator(this.document, "/root/elements/element");
		NodeList elementNodeList = XMLData.selectNodeList(this.document, "/root/elements/element");

		int nodeNr = 0;
		//Node elementNode; while ((elementNode = elementNodeIterator.nextNode()) != null)
		for (int pos = 0; pos < elementNodeList.getLength(); pos++) {
			Node elementNode = elementNodeList.item(pos);
			assertEquals("element", elementNode.getNodeName());
			
			nodeNr++;
		}
	}

	/**
	 * Den Text-Wert eines einzelnen Nodes anhand eines XPath-Ausdrucks ausw�hlen.
	 *
	 */
	public void testSelectSingleNodeText()
	{
		String value = XMLData.selectSingleNodeText(this.document, "/root/elements/element[text()='XYZ']");
		
		assertEquals("XYZ", value);
	}

	/**
	 * H�ngt einen neuen Node an einen bestehenden an.
	 *
	 */
	public void testAppendNode()
	{
		Node elementsNode = XMLData.selectSingleNode(this.document, "/root/elements");
		
		Node elementNode = XMLData.appendNode(this.document, elementsNode, "element");

		assertEquals("elements", elementNode.getParentNode().getNodeName());
	}

	/**
	 * H�ngt einen neuen Node mit einem neuen TextNode an einen bestehenden an.
	 *
	 */
	public void testAppendTextNode()
	{
		Node elementsNode = XMLData.selectSingleNode(this.document, "/root/elements");
		
		Node elementNode = XMLData.appendTextNode(this.document, elementsNode, "element", "SMK");

		assertEquals("SMK", elementNode.getFirstChild().getNodeValue());
		assertEquals(Node.TEXT_NODE, elementNode.getFirstChild().getNodeType());
	}

	public void testAppendCharDataNode()
	{
		Node elementsNode = XMLData.selectSingleNode(this.document, "/root/elements");
		
		Node elementNode = XMLData.appendCharDataNode(this.document, elementsNode, "element", "<C> H A R </S>");

		assertEquals("<C> H A R </S>", elementNode.getFirstChild().getNodeValue());
		assertEquals(Node.CDATA_SECTION_NODE, elementNode.getFirstChild().getNodeType());
	}

}
