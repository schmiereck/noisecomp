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

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.OutputUtils;

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
	//**********************************************************************************************
	// Fields:

	private final JTextField generatorNameTextField;
	private final JTextField generatorStartTimePosTextField;
	private final JTextField generatorEndTimePosTextField;
	
	/**
	 * Update Button.
	 */
	private JButton updateButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelineEditModel
	 * 			is the Timeline-Edit Model.
	 */
	public TimelineEditView(final TimelineEditModel timelineEditModel)
	{
		//==========================================================================================
		this.setLayout(new GridBagLayout());
		
		this.setBorder(BorderFactory.createTitledBorder("Generator:"));
		
		{
			this.generatorNameTextField = this.addTextField(0, "Name:");
			
			timelineEditModel.getGeneratorNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorNameTextField.setText(OutputUtils.makeStringText(timelineEditModel.getGeneratorName()));
					}
			 	}
			);
		}
		{
			this.generatorStartTimePosTextField = this.addTextField(1, "Start-Time:");
			
			timelineEditModel.getGeneratorStartTimePosChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorStartTimePosTextField.setText(OutputUtils.makeFloatText(timelineEditModel.getGeneratorStartTimePos()));
					}
			 	}
			);
		}
		{
			this.generatorEndTimePosTextField = this.addTextField(2, "End-Time:");
			
			timelineEditModel.getGeneratorEndTimePosChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						generatorEndTimePosTextField.setText(OutputUtils.makeFloatText(timelineEditModel.getGeneratorEndTimePos()));
					}
			 	}
			);
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
			this.updateButton = new JButton("Update");
			
			this.addField(4, this.updateButton);
		}
		//==========================================================================================
	}

	/**
	 * @param labelText
	 * 			is the Label Text.
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

	/**
	 * @return 
	 * 			returns the {@link #updateButton}.
	 */
	public JButton getUpdateButton()
	{
		return this.updateButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorEndTimePosTextField}.
	 */
	public JTextField getGeneratorEndTimePosTextField()
	{
		return this.generatorEndTimePosTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorNameTextField}.
	 */
	public JTextField getGeneratorNameTextField()
	{
		return this.generatorNameTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #generatorStartTimePosTextField}.
	 */
	public JTextField getGeneratorStartTimePosTextField()
	{
		return this.generatorStartTimePosTextField;
	}
}
