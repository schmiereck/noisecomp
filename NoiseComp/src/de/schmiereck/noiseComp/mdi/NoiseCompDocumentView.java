package de.schmiereck.noiseComp.mdi;

import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import org.bs.mdi.Document;
import org.bs.mdi.DocumentWindow;
import org.bs.mdi.swing.SwingDocumentView;
import org.bs.mdi.swing.SwingDocumentWindow;
import de.schmiereck.noiseComp.MainModel;
import de.schmiereck.noiseComp.MainView;
import de.schmiereck.screenTools.Runner;
import de.schmiereck.screenTools.RunnerSchedulers;
import de.schmiereck.screenTools.graphic.GraphicComponent;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphicException;

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
public class NoiseCompDocumentView
extends SwingDocumentView
{
	private MainModel mainModel;
	private MainView mainView;
	private GraphicComponent graphicComponent;
	
	boolean listenerActive = true;	// should text events ("actions") be triggered?

	public NoiseCompDocumentView(Document document) 
	{
		super(document);		
		
		this.setFocusable(true);
		this.setEnabled(true);
		this.enableInputMethods(true);
		
		NoiseCompDocumentData noiseCompDocumentData = (NoiseCompDocumentData)document.getData();
		
		this.mainModel = noiseCompDocumentData.getMainModel();
		
		this.setLayout(new BorderLayout());
		
		try
		{
			this.mainView = new MainView(false, mainModel, this);
		}
		catch (MultiBufferFullScreenGraphicException ex)
		{
			throw new RuntimeException("while init view", ex);
		}
		
		//JApplet a = new JApplet();
		//JTextArea t = new JTextArea();	
		this.graphicComponent = new GraphicComponent(this.mainView.getMultiBufferGraphic(),
																 this.mainModel.getControllerData());
		JScrollPane scrollPane = new JScrollPane(this.graphicComponent);
		this.add(scrollPane, BorderLayout.CENTER);

		this.enableEvents(AWTEvent.KEY_EVENT_MASK | 
							  AWTEvent.FOCUS_EVENT_MASK | 
							  AWTEvent.INPUT_METHOD_EVENT_MASK |
							  //AWTEvent.INPUT_METHODS_ENABLED_MASK |
							  AWTEvent.MOUSE_EVENT_MASK |
							  AWTEvent.MOUSE_MOTION_EVENT_MASK);
		this.graphicComponent.addMouseListener(this.mainModel.getInputListener());
		this.graphicComponent.addMouseMotionListener(this.mainModel.getInputListener());
		this.graphicComponent.addKeyListener(this.mainModel.getInputListener());
		
		this.addKeyListener(new java.awt.event.KeyAdapter()
							    {
							      public void keyPressed(KeyEvent e)
							      {
							        System.out.println("DOC-WIN FRAME KEY PRESSES");
							      }
							    });		

		 //this.getRootPane().addKeyListener(this.mainModel.getInputListener());
		
		this.mainModel.getControllerLogic().addDataChangedListener(graphicComponent);
		
		//JFrame mainFrame = this.mainView.getMultiBufferGraphic().getMainFrame();
		
		//this.textArea.getDocument().addDocumentListener(new MyDocumentListener());		
		//this.textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));		
	
		//this.add(new JScrollPane(mainFrame), BorderLayout.CENTER);
		//this.add(new JScrollPane(mainFrame.getContentPane()), BorderLayout.CENTER);		
		//this.add(mainFrame, BorderLayout.CENTER);		
		
		//----------------------------------------------------------------------
		// run:
		/*
		RunnerSchedulers runnerSchedulers;
		
		runnerSchedulers = Runner.start(mainModel.getControllerData(), 
										this.mainView.getControllerLogic(), 
										this.mainView.getMultiBufferGraphic(), 
										this.mainView.getInputListener(), 
										this.mainView.getWaiter(), 24, 16,
										false, 
										false,
										-1, -1);
		
		//Runner.stop(runnerSchedulers);
		 */
	}
	
 	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#setWindow(org.bs.mdi.DocumentWindow)
	 */
	public void setWindow(DocumentWindow window)
	{
		SwingDocumentWindow docWin = ((SwingDocumentWindow)window);
		
		//docWin.setEnabled(true);
		//docWin.enableInputMethods(true);
		//docWin.setFocusable(true);
		
		docWin.addKeyListener(this.mainModel.getInputListener());
		/*
		 docWin.addKeyListener(new java.awt.event.KeyAdapter()
						    {
						      public void keyPressed(KeyEvent e)
						      {
						        System.out.println("JINTERNAL FRAME KEY PRESSES");
						      }
						    });		
		
		 //JInternalFrame internalFrame = new JInternalFrame([...]);
		docWin.registerKeyboardAction(new ActionListener()
									  {

										public void actionPerformed(ActionEvent e)
										{
									        System.out.println("JINTERNAL FRAME actionPerformed 1");
											e.getSource();
										}
									  
									  },
									  KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, true), 
									  JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		docWin.registerKeyboardAction(new java.awt.event.ActionListener() 
									{
							        	public void actionPerformed(ActionEvent e) 
							        	{
							        		System.out.println("JINTERNAL FRAME actionPerformed 2");
							        	}
									}, 
									KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, true),
									JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	    */
		super.setWindow(window);
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		System.out.println(this.getBounds());
		
		this.mainView.getMultiBufferGraphic().drawGraphic(g, 
														  this.mainModel.getControllerData(),
														  this.getBounds(),
														  30L);
		
		g.drawRect(10, 10, 30, 50);
	}
	 */
	
    //protected void processComponentKeyEvent(KeyEvent e)
   // {
   // 	super.processComponentKeyEvent(e);
   //     System.out.println("JINTERNAL FRAME processComponentKeyEvent");
   //}

	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#syncWithData()
	 */
	public void syncWithData() 
	{	
		this.listenerActive = false;
		
		/*
		int oldCaret = textArea.getCaretPosition();
		
		textArea.setText(((NoiseCompDocumentData)this.getDocument().getData()).getText());
		textArea.setCaretPosition(oldCaret);
		*/
		
		this.graphicComponent.notifyDataChanged();
		
		this.listenerActive = true;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.ActionProcessor#applyAction(org.bs.mdi.Action)
	 */
	public void applyAction(org.bs.mdi.Action action) 
	{		
		if (action instanceof NoiseCompAction) 
		{
			NoiseCompAction ea = (NoiseCompAction)action;		
		
			switch (ea.getType()) 
			{
				case NoiseCompAction.INSERT:
				case NoiseCompAction.PASTE:				
					//deleteText(ea.getStartOffset(), ea.getEndOffset());
					//insertText(ea.getStartOffset(), ea.getNewText());								
					break;
				case NoiseCompAction.REMOVE:
				case NoiseCompAction.CUT:
				case NoiseCompAction.DELETE:				
					//deleteText(ea.getStartOffset(), ea.getEndOffset());							
					break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.ActionProcessor#undoAction(org.bs.mdi.Action)
	 */
	public void undoAction(org.bs.mdi.Action action) 
	{
		if (action instanceof NoiseCompAction) 
		{		
			NoiseCompAction ea = (NoiseCompAction)action;	
			
			switch (ea.getType()) 
			{
				case NoiseCompAction.INSERT:
				case NoiseCompAction.PASTE:
				{
					//deleteText(ea.getStartOffset(), ea.getStartOffset() + ea.getNewText().length());
				
					//if (ea.getOldText() != null) 
					//{
					//	insertText(ea.getStartOffset(), ea.getOldText());
					//}
					break;
				}
				case NoiseCompAction.REMOVE:
				case NoiseCompAction.CUT:
				case NoiseCompAction.DELETE:
				{
					//insertText(ea.getStartOffset(), ea.getOldText());
					break;
				}
			}		
		}
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#copy()
	 */
	public org.bs.mdi.Action copy() 
	{
		NoiseCompAction act;
		/*
		String selectedText = textArea.getSelectedText();
		
		if (selectedText == null) 
			return null;
		
		act = new NoiseCompAction(null,
								  false,
								  EditorAction.COPY,
								  null, 
								  selectedText,
								  textArea.getSelectionStart(),
								  textArea.getSelectionEnd());
		*/
		
		// Nothing selected for clipboard.
		act = null;
		
		return act;
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#cut()
	 */
	public org.bs.mdi.Action cut() 
	{
		NoiseCompAction act;

		/*
		String selectedText = textArea.getSelectedText();
		if (selectedText == null) return null;
		listenerActive = false;
		act =
			new EditorAction(
				null,
				false,
				EditorAction.CUT,
				selectedText,
				null,
				textArea.getSelectionStart(),
				textArea.getSelectionEnd());		
		
		notifyObservers(act, false);		
		listenerActive = true;
		*/
		
		// Nothing selected for clipboard.
		act = null;
		
		return act;
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#paste(org.bs.mdi.Action)
	 */
	public void paste(org.bs.mdi.Action action) 
	{
		if (action != null) 
		{
			listenerActive = false;
			
			/*
			NoiseCompAction originalAction = (NoiseCompAction) action;
			String selectedText = textArea.getSelectedText();
			boolean selected = selectedText != null;
			int start =
				(selected)
					? textArea.getSelectionStart()
					: textArea.getCaretPosition();
			int end =
				(selected)
					? textArea.getSelectionEnd()
					: textArea.getCaretPosition();
					NoiseCompAction newAction =
				new NoiseCompAction(
					null,
					false,
					EditorAction.PASTE,
					selectedText,
					originalAction.getNewText(),
					start,
					end);		
			notifyObservers(newAction, false);		
			*/
			
			listenerActive = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#delete()
	 */
	public void delete() 
	{
		/*
		String selectedText = textArea.getSelectedText();
		if (selectedText == null)
			return;
		listenerActive = false;
		EditorAction act =
			new EditorAction(
				null,
				false,
				EditorAction.DELETE,
				selectedText,
				null,
				textArea.getSelectionStart(),
				textArea.getSelectionEnd());		
		notifyObservers(act, false);
		listenerActive = true;
		*/
	}

	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#isCopyPossible()
	 */
	public boolean isCopyPossible() 
	{
		/*
		return (textArea.getSelectedText() != null);
		*/
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#isCutPossible()
	 */
	public boolean isCutPossible() 
	{
		/*
		return isCopyPossible();
		*/
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#isPastePossible()
	 */
	public boolean isPastePossible() 
	{
		/*
		return true;
		*/
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.bs.mdi.DocumentView#isDeletePossible()
	 */
	public boolean isDeletePossible() 
	{
		/*
		return isCutPossible();
		*/
		return false;
	}
	
	/*
	protected void insertText(int offset, String text) 
	{
		listenerActive = false;
		textArea.insert(text, offset);		
		listenerActive = true;
	}
	*/
	
	/*
	protected void deleteText(int startoffset, int endoffset) 
	{
		if (startoffset == endoffset) return;
		listenerActive = false;		
		textArea.replaceRange(null, startoffset, endoffset);
		listenerActive = true;		
	}
	*/
	
	private NoiseCompDocumentView getViewInstance() 
	{
		return this;
	}
	
	class MyDocumentListener 
	implements DocumentListener 
	{
		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		public void changedUpdate(DocumentEvent e) 
		{
		};

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		public void insertUpdate(DocumentEvent e) 
		{
			if (listenerActive)
			{
				/*
				try 
				{
					NoiseCompDocumentData data = (NoiseCompDocumentData)getDocument().getData();
					
					String newText = e.getDocument().getText(e.getOffset(), e.getLength());
					String oldText = textArea.getSelectedText();
					int oldTextLen = (oldText == null) ? 0 : oldText.length();
					NoiseCompAction act =
						new NoiseCompAction(
							getViewInstance(),
							true,
							NoiseCompAction.INSERT,
							oldText,
							newText,
							e.getOffset(),
							e.getOffset() + oldTextLen);				
					notifyObservers(act, false);
				} 
				catch (BadLocationException ignored) 
				{
				}
				*/
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate(DocumentEvent e) 
		{
			if (listenerActive)
			{
				/*
				NoiseCompDocumentData data =
					(NoiseCompDocumentData) getDocument().getData();
				String newText = null;
				String oldText =
					data.getText().substring(
						e.getOffset(),
						e.getOffset() + e.getLength());
				NoiseCompAction act =
					new NoiseCompAction(
						getViewInstance(),
						true,
						NoiseCompAction.REMOVE,
						oldText,
						newText,
						e.getOffset(),
						e.getOffset() + e.getLength());			
				notifyObservers(act, false);
				*/
			}
		}
	}
}
