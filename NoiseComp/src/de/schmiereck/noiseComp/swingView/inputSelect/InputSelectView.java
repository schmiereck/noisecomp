/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <p>
 * 	Input-Select View.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectView
extends JTable
{
	//**********************************************************************************************
	// Fields:

//	@SuppressWarnings("unused")
//	private final JTable table;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputSelectModel
	 * 			is the Input-Select Model.
	 */
	public InputSelectView(final InputSelectModel inputSelectModel)
	{
		//==========================================================================================
		super(inputSelectModel.getInputsTabelModel());
		
//		this.table = new JTable();
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//------------------------------------------------------------------------------------------
		ListSelectionModel selectionModel = this.getSelectionModel();
		
		selectionModel.addListSelectionListener
		(
		 	new ListSelectionListener()
		 	{
				@Override
				public void valueChanged(ListSelectionEvent e)
				{
					if (e.getValueIsAdjusting() == false) 
					{
						Integer selectedRowNo;
						
						if (getRowCount() > 0)
						{
							int[] selectedRows = getSelectedRows();
							
							if (selectedRows.length > 0)
							{
								selectedRowNo = selectedRows[0];
							}
							else
							{
								selectedRowNo = null;
							}
						}
						else
						{
							selectedRowNo = null;
						}
						
						inputSelectModel.setSelectedRowNo(selectedRowNo);
//						inputSelectModel.getS
//						selectedTimelineModel.setSelectedInputEntry();
		            }
				}
		 	}
		);
		
		//==========================================================================================
	}

}
