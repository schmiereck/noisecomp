package de.schmiereck.noiseComp.mdi;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.bs.mdi.Action;
import org.bs.mdi.ActionConverter;

/*
 * Created on 25.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * @author smk
 * @version <p>25.03.2005:	created, smk</p>
 */
public class NoiseCompActionConverter
implements ActionConverter
{
	public boolean canHandle(Action action) 
	{		
		boolean ret;
		
		if (action instanceof NoiseCompAction)
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
			
		return ret;
	}

	public boolean canHandle(Transferable transferable) 
	{
		boolean ret;
		
		// we can only handle text/plain data flavours
		if (transferable != null)
		{
			ret = false;

			DataFlavor flavors[] = transferable.getTransferDataFlavors();
			
			for (int i = 0; i < flavors.length; i++) 
			{
				DataFlavor dataFlavor = flavors[i];
				
				if (dataFlavor.getMimeType().startsWith("text/plain"))
				{
					ret = true;
					break;
				}
			}
		}
		else
		{
			ret = false;
		}

		return ret;
	}

	public Transferable toTransferable(Action action) 
	{
		Transferable retTransferable;
		
		if (!(action instanceof NoiseCompAction))
		{
			retTransferable = null;
		}
		else
		{
			retTransferable = new NoiseCompTransferable((NoiseCompAction)action);
		}
		
		return retTransferable;
	}

	public Action toAction(Transferable transferable) 
	{
		try 
		{
			Object o = transferable.getTransferData(DataFlavor.stringFlavor);
			String s = (String) o;
			
			return new NoiseCompAction(null, false, NoiseCompAction.UNDEFINED, null, s, 0, 0);
		} 
		catch (Exception ex) 
		{
		}
		return null;
	}
	
	
	class NoiseCompTransferable implements Transferable {
		String text;
		ArrayList flavors;
		
		public NoiseCompTransferable(NoiseCompAction action) {
			text = action.getNewText();
			flavors = new ArrayList();
			flavors.add(DataFlavor.stringFlavor);
			try {
				flavors.add(new DataFlavor("text/plain; charset=ISO-8859-1"));
				flavors.add(new DataFlavor("text/plain; charset=UTF-8"));
				flavors.add(new DataFlavor("text/plain; charset=US-ASCII"));
				flavors.add(new DataFlavor("text/plain; charset=ascii"));
			} catch (ClassNotFoundException e) {
			}
		}
		
		public Object getTransferData(DataFlavor flavor) throws IOException, UnsupportedFlavorException {
			if (flavor.equals(DataFlavor.stringFlavor)) {
				return text;
			} else if (flavor.isMimeTypeEqual("text/plain")) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, flavor.getParameter("charset"));
				PrintWriter wr = new PrintWriter(osw);
				wr.print(text);
				wr.flush();
				wr.close();
				return new ByteArrayInputStream(os.toByteArray());
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}
		
		public DataFlavor[] getTransferDataFlavors() {
			return (DataFlavor[])flavors.toArray(new DataFlavor[0]);
		}
		
		public boolean isDataFlavorSupported(DataFlavor flavor) { 
			for (int i=0; i<flavors.size(); i++) {
				if (flavor.equals(flavors.get(i))) return true;
			}
			return false;
		}
	}
}
