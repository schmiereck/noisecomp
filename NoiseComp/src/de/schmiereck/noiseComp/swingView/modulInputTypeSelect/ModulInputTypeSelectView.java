/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeSelect;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <p>
 * 	Modul-Input-Type Select View.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModulInputTypeSelectView
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
	 * @param modulInputTypeSelectModel
	 * 			is the Modul-Input-Type Select Model.
	 */
	public ModulInputTypeSelectView(final ModulInputTypeSelectModel modulInputTypeSelectModel)
	{
		//==========================================================================================
		super(modulInputTypeSelectModel.getModulInputTypeTabelModel());
		
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
						
						modulInputTypeSelectModel.setSelectedRowNo(selectedRowNo);
		            }
				}
		 	}
		);
		
		//==========================================================================================
	}
}
