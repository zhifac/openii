package org.mitre.affinity.view.menu.schemas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.mitre.affinity.view.menu.AffinityMenu;

/**
 * @author CBONACETO
 *
 */
public class AffinityMenu_Schemas extends AffinityMenu {
	
	public static final int FILE_SAVE_SCHEMA_STATS_ITEM = AffinityMenu.NEXT_MENU_ITEM_ID;
	
	public AffinityMenu_Schemas(Decorations decorations, boolean createSaveStatsItem,
			boolean createFileExitItem) {
		super(decorations);
		createFileMenu(createSaveStatsItem, createFileExitItem);
	}	
	
	protected void createFileMenu(boolean createSaveStatsItem, boolean createFileExitItem) {
		super.createFileMenu("&File", false);
		//Add save statistics item to file menu to save ground truth information
		if(createSaveStatsItem) {
			createMenuItem(fileMenu, "Save Statistics", SWT.NONE,
					FILE_SAVE_SCHEMA_STATS_ITEM, FILE_MENU, true);
		}
		
		if(createFileExitItem) {
			addExitItemToFileMenu();
		}		
		createViewMenu("&View", true, true);
	}
}