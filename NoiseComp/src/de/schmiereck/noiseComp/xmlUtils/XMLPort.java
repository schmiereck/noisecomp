package de.schmiereck.noiseComp.xmlUtils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>16.01.2004:	created</p>
 */
public class XMLPort
{
	/**
	 * @throws XMLException
	 * 
	 * @param fileName
	 * @return
	 */
	static public Document open(String fileName)
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
		try
		{
			//document = builder.parse(multilangsXMLStream);
			document = builder.parse(fileName);
		}
		catch (SAXException ex)
		{
			throw new XMLException("parse document: \"" + fileName + "\"", ex);
		}
		catch (IOException ex)
		{
			throw new XMLException("load document: \"" + fileName + "\"", ex);
		}
		
		return document;
	}

	/**
	 * @param fileName
	 * @param document
	 */
	public static void save(String fileName, Document document)
	{
		// Use a Transformer for output
		TransformerFactory tFactory = TransformerFactory.newInstance();
		
		try
		{
			/*
			 * Here, you created a transformer object, used the DOM to
			 * construct a source object, and used System.out to construct a
			 * result object. You then told the transformer to operate on
			 * the source object and output to the result object.
			 */
			Transformer transformer = tFactory.newTransformer();
			
			/*
			 * Astute reader Malcolm Gorman points out that, 
			 * as it is currently written, the transformation app won't 
			 * preserve the XML document's DOCTYPE setting. He proposes the
			 * following code to remedy the omission:
			 * (So, I create the document from scratch, so the doc don't
			 * have anything like that, smk)
			 */
			//String systemValue = (new File(document.getDoctype().getSystemId())).getName(); 
			//transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemValue); 

			File file = new File(fileName);
			
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			//StreamResult result = new StreamResult(System.out);
			
			try
			{
				transformer.transform(source, result);
			}
			catch (TransformerException ex) 
			{
				// Error generated by the parser
				throw new XMLException("save document transformation error: \"" + fileName + "\"", ex);
			}					
		} 
		catch (TransformerConfigurationException ex) 
		{
			// Error generated by the parser
			throw new XMLException("save document transformer factory error: \"" + fileName + "\"", ex);
		}
	}
}
