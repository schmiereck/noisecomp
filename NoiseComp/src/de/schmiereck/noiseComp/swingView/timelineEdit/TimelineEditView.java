/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * <p>
 * 	Timeline-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelineEditView
extends JPanel
{
	/**
	 * Constructor.
	 * 
	 */
	public TimelineEditView()
	{
		//==========================================================================================
		this.setLayout(new GridBagLayout());
		
		this.setBorder(BorderFactory.createTitledBorder("Generator:"));
		
		{
			JTextField textField = this.addTextField(0, "Name:");
			textField.setText("xxx");
		}
		{
			JTextField textField = this.addTextField(1, "Start-Time:");
			textField.setText("1.0");
		}
		{
			JTextField textField = this.addTextField(2, "End-Time:");
			textField.setText("2.0");
		}
		{
			this.addLabel(3, "Inputs:");
			
			TableModel dataModel = new AbstractTableModel() 
			{
				private String columnNames[] = new String[]{"Name", "Value"};
				
				public int getColumnCount() { return 2; }
				public int getRowCount() { return 5;}
				public String getColumnName(int column)
				{
					return this.columnNames[column];
				}
				public Object getValueAt(int row, int col) { return new Integer(row*col); }
			};
			JTable table = new JTable(dataModel);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

//			JTableHeader tableHeader = table.getTableHeader();
//		      
//			TableColumnModel columnModel = tableHeader.getColumnModel();
//		      
//			{
//				TableColumn column = columnModel.getColumn(0);
//				column.setHeaderValue("Name");
//			}
//			{
//				TableColumn column = columnModel.getColumn(1);
//				column.setHeaderValue("Value");
//			}
		      
			JScrollPane scrollpane = new JScrollPane(table);

			int vScrollBarWidth = scrollpane.getVerticalScrollBar().getPreferredSize().width;
			Dimension dimTable = table.getPreferredSize();
			dimTable.setSize(dimTable.getWidth() + vScrollBarWidth, 
			                 dimTable.getHeight());

			scrollpane.setPreferredSize(new Dimension(dimTable.width, 100));
			scrollpane.setMaximumSize(new Dimension(dimTable.width, 100));
			scrollpane.setMinimumSize(new Dimension(dimTable.width, 100));

			this.addField(3, scrollpane);
		}
		{
			JButton button = new JButton("Update");
			
			this.addField(4, button);
		}
		//==========================================================================================
	}

	/**
	 * @param labelText
	 * @return
	 */
	private JTextField addTextField(int gridy, String labelText)
	{
		//==========================================================================================
		this.addLabel(gridy, labelText);

		//------------------------------------------------------------------------------------------
		JTextField textField = new JTextField(30);

		this.addField(gridy, textField);
		
		//==========================================================================================
		return textField;
	}

	/**
	 * @param gridy
	 * @param component
	 */
	private void addField(int gridy, JComponent component)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 1;
		constraints.gridy = gridy;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1.0D;
		constraints.weighty = 0.0D;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		this.add(component, constraints);
	}

	/**
	 * @param gridy
	 * @param labelText
	 */
	private void addLabel(int gridy, String labelText)
	{
		//==========================================================================================
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = gridy;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.1D;
		constraints.weighty = 0.0D;
		constraints.anchor = GridBagConstraints.EAST;
		
		this.add(new JLabel(labelText), constraints);
		
		//==========================================================================================
	}
}
