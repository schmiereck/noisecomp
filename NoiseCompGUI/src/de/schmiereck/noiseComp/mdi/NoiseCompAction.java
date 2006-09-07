package de.schmiereck.noiseComp.mdi;

import org.bs.mdi.Action;
import org.bs.mdi.ActionObservable;
import org.bs.mdi.Application;
import org.bs.mdi.Data;
import org.bs.mdi.View;

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
public class NoiseCompAction
extends Action
{

	public static final int UNDEFINED = 0;
	public static final int INSERT = 1;
	public static final int REMOVE = 2;
	public static final int COPY = 3; 	// clipboard
	public static final int CUT = 4; 	// clipboard
	public static final int PASTE = 5; 	// clipboard
	public static final int DELETE = 6; 	// clipboard
	
	public static final String descriptions[] = 
	{	
		"",
		Application.tr("Insert Text"), 
		Application.tr("Remove Text"), 
		Application.tr("Copy"), 
		Application.tr("Cut"),
		Application.tr("Paste"),
		Application.tr("Delete")
	};

	String newText;
	String oldText;
	int startoffset;
	int endoffset;
	int type;		
	boolean retarded;
	
	public NoiseCompAction(ActionObservable observable, boolean retard, 
						   int type, String oldText, String newText, int start, int end) 
	{
		super(observable);
		
		this.retarded = retard;
		this.type = type;
		this.newText = newText;
		this.oldText = oldText;
		this.startoffset = start;
		this.endoffset = end;			
	}
	
	public boolean isRetarded() 
	{
		return retarded;
	}
	
	public boolean clustersWith(Action action) 
	{
		NoiseCompAction newAction = (NoiseCompAction)action;
		
		boolean containsNewline = (newAction.newText != null && newAction.newText.indexOf('\n') != -1);
		boolean nextOffset = (startoffset + 1 == newAction.getStartOffset());
		boolean prevOffset = (startoffset - 1 == newAction.getStartOffset());
		boolean sameType = (getType() == newAction.getType());
		
		switch (newAction.getType()) 
		{
			case NoiseCompAction.INSERT:
				if (sameType && nextOffset && !containsNewline) 
				{
					return true;
				} 
				else 
				{
					return false;
				}
			case NoiseCompAction.REMOVE:
				if (sameType && prevOffset && !containsNewline) 
				{
					return true;
				} 
				else 
				{
					return false;
				}
			default:
				return false;
		}
	}
	
	public boolean isUndoable() 
	{
		return true;
	}

	public String getName() 
	{
		return descriptions[type];
	}
	
	public String toString() 
	{
		return getName() + ": \"" + newText + "\" <- \"" + oldText + "\"";
	}
	
	public int getType() 
	{
		return type;
	}

	public String getNewText() 
	{
		return newText;
	}
	
	public String getOldText() 
	{
		return oldText;
	}
	
	public int getStartOffset() 
	{
		return startoffset;
	}
	
	public int getEndOffset() 
	{
		return endoffset;
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.Action#applyTo(org.bs.mdi.Data)
	 */
	public void applyTo(Data data)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.Action#applyTo(org.bs.mdi.View)
	 */
	public void applyTo(View view)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.Action#undoFrom(org.bs.mdi.Data)
	 */
	public void undoFrom(Data data)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.Action#undoFrom(org.bs.mdi.View)
	 */
	public void undoFrom(View view)
	{
		// TODO Auto-generated method stub
		
	}
}
