/*
 * www.schmiereck.de (c) 2010
 */
package demo2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * <p>
 * 	http://192.9.162.102/thread.jspa?messageID=10896446
 * </p>
 * 
 * @author smk
 * @version <p>02.11.2010: created, smk</p>
 */
@SuppressWarnings("serial")
public class ViewportExampleView
	extends JFrame
{
	private DrawPanelView	drawPanelView	= new DrawPanelView();

	private VerticalRule	verticalRule	= new VerticalRule();

	private HorizontalRule	horizontalRule	= new HorizontalRule();

	public ViewportExampleView()
	{
		super("Viewport Example");
		
		//==========================================================================================
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//------------------------------------------------------------------------------------------
		this.horizontalRule.setPreferredSize(new Dimension(1500, 30));
		
		//------------------------------------------------------------------------------------------
		this.verticalRule.setPreferredSize(new Dimension(30, 1000));
		
		//------------------------------------------------------------------------------------------
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setColumnHeaderView(this.horizontalRule);
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setRowHeaderView(this.verticalRule);
		
		scrollPane.setViewportView(this.drawPanelView);
		
		//------------------------------------------------------------------------------------------
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(this.getSouthPanel(), BorderLayout.SOUTH);
		
		this.setSize(700, 600);
		
		// setExtendedState(MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		
		//==========================================================================================
	}

	private JPanel getSouthPanel()
	{
		JPanel panel = new JPanel();
		final JTextField field = new JTextField(30);
		field.addActionListener(new ActionListener()
		{
			Pattern	pattern	= Pattern.compile("\\s*((?:-)?\\d+)\\s+((?:-)?\\d+)\\s+(\\d+)\\s+(\\d+)\\s*");

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DrawPanelModel	drawPanelModel = drawPanelView.getDrawPanelModel();
				
				Matcher matcher = pattern.matcher(field.getText());
				if (matcher.matches())
				{
					Rectangle rect = 
						new Rectangle(Integer.parseInt(matcher.group(1)), 
						              Integer.parseInt(matcher.group(2)),
						              Integer.parseInt(matcher.group(3)), 
						              Integer.parseInt(matcher.group(4)));
					
					drawPanelModel.setRect(rect);
					
					// -- mark this --
					drawPanelView.scrollRectToVisible(rect);
					drawPanelView.repaint();
				}
				else
				{
					drawPanelModel.setRect(null);
					
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		panel.add(new JLabel("Rectangle: (Formatted as : x y width height) "));
		panel.add(field);
		return panel;
	}

}