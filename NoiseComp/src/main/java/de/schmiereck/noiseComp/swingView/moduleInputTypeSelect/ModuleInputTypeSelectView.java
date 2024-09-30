/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.moduleInputTypeSelect;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <p>
 * 	ModuleInput-Type Select View.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeSelectView
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
	 * @param moduleInputTypeSelectModel
	 * 			is the ModuleInput-Type Select Model.
	 */
	public ModuleInputTypeSelectView(final ModuleInputTypeSelectModel moduleInputTypeSelectModel)
	{
		//==========================================================================================
		super(moduleInputTypeSelectModel.getModuleInputTypeTabelModel());
		
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
						
						moduleInputTypeSelectModel.setSelectedRowNo(selectedRowNo);
		            }
				}
		 	}
		);
		
		//==========================================================================================
	}
}
