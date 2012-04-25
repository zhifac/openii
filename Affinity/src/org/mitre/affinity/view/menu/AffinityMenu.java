package org.mitre.affinity.view.menu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.view.menu.event.AffinityMenuItemListener;
import org.mitre.affinity.view.menu.event.AffinityMenuListener;
import org.mitre.affinity.AffinityConstants.TextSize;

/**
 * Basic Affinity menu implementation that creates a File menu with an 
 * Exit option and a View menu with Craigrogram and Dendrogram options sub-menus.
 * 
 * This class may be extended to create additional menus or add custom
 * options to the File and View menus.
 * 
 * @author CBONACETO
 *
 */
public class AffinityMenu {
	
	/** Menu ID constants */
	public static final int FILE_MENU = 0;
	public static final int VIEW_MENU = 1;
	public static final int VIEW_CLUSTER2DOPTIONS_MENU = 2;
	public static final int VIEW_CLUSTER2DOPTIONS_TEXTSIZE_MENU = 3;
	public static final int VIEW_CLUSTER2DOPTIONS_ZOOM_MENU = 4;
	public static final int VIEW_DENDROGRAMDOPTIONS_MENU = 5;
	public static final int HELP_MENU = 6;
	public static final int NEXT_MENU_ID = 7;
	
	/** Menu item ID constants */
	public static final int FILE_EXIT_ITEM = 0;	
	public static final int VIEW_CLUSTER2DOPTIONS_TEXTSIZE_ITEM = 1;
	public static final int VIEW_CLUSTER2DOPTIONS_ZOOM_ITEM = 2;
	public static final int VIEW_CLUSTER2DOPTIONS_SHOW_TOOLBAR_ITEM = 3;
	public static final int VIEW_CLUSTER2DOPTIONS_SHOW_OVERVIEW_ITEM = 4;
	public static final int VIEW_CLUSTER2DOPTIONS_SHOW_NAMES_ITEM =5;
	public static final int VIEW_CLUSTER2DOPTIONS_SHOW_CLUSTERS_ITEM = 6;
	public static final int VIEW_CLUSTER2DOPTIONS_FILL_CLUSTERS_ITEM = 7;
	public static final int VIEW_CLUSTER2DOPTIONS_CONCAVE_PATHS_ITEM = 8;
	public static final int VIEW_CLUSTER2DOPTIONS_CLUSTER_SPACING_ITEM = 9;
	public static final int VIEW_DENDROGRAMOPTIONS_ALTERNATING_COLORS_ITEM = 10;
	public static final int VIEW_DENDROGRAMOPTIONS_SHADE_BACKGROUNDS_ITEM = 11;	
	public static final int NEXT_MENU_ITEM_ID = 12;
	
	protected Decorations decorations;
	
	/** The menu bar */
	protected final Menu menuBar;
	
	/** The File menu */
	protected Menu fileMenu;
	
	/** The View menu */
	protected Menu viewMenu;	
	
	/** Default menu item listener dispatches events to listeners in the menuItemListeners list */
	protected MenuSelectionListener menuItemListener;
	
	/** Default menu listener dispatches events to listeners in the menuListeners list */
	protected MenuListener menuListener;
	
	/** List of listeners registered to receive menu item selection events */
	protected List<AffinityMenuItemListener> menuItemListeners;
	
	/** List of listeneres registered to receive menu events */
	protected List<AffinityMenuListener> menuListeners;
	
	/** Craigrogram menu items */	
	protected MenuItem smallTextItem; 
	protected MenuItem normalTextItem; 
	protected MenuItem largeTextItem;			
	protected MenuItem hideTextItem;	
	protected MenuItem[] zoomLevelItems;
	protected MenuItem fitInWindowItem;
	protected MenuItem showToolBarItem;
	protected MenuItem showOverviewItem;
	protected MenuItem showTitlesItem;
	protected MenuItem showClustersItem;
	protected MenuItem fillClustersItem;
	protected MenuItem followConcavePathsItem;
	/****/
	
	/** Dendrogram menu items */
	protected MenuItem showAltColorsItem;
	protected MenuItem shadeBackgroundsItem;
	/****/
	
	/**
	 * Create empty Affinity Menu
	 */
	protected AffinityMenu(Decorations decorations) {
		this.decorations = decorations;
		menuBar = new Menu(decorations, SWT.BAR);
		decorations.setMenuBar(menuBar);
		menuItemListener = new MenuSelectionListener();
		menuListener = new MenuListener();
		menuItemListeners = Collections.synchronizedList(new LinkedList<AffinityMenuItemListener>());
		//menuItemListeners = new LinkedList<AffinityMenuItemListener>();
		menuListeners = Collections.synchronizedList(new LinkedList<AffinityMenuListener>());
		//menuListeners = new LinkedList<AffinityMenuListener>();
	}
	
	public AffinityMenu(Decorations decorations, boolean createFileMenu, boolean createViewMenu) {
		this(decorations, createFileMenu, true, createViewMenu, true, true);
	}
	
	public AffinityMenu(Decorations decorations, boolean createFileMenu, boolean createFileExitItem,
			boolean createViewMenu, boolean createCraigrogramOptions, boolean createDendrogramOptions) {
		this.decorations = decorations;
		menuBar = new Menu(decorations, SWT.BAR);
		decorations.setMenuBar(menuBar);
		menuItemListener = new MenuSelectionListener();
		menuListener = new MenuListener();
		menuItemListeners = new LinkedList<AffinityMenuItemListener>();
		menuListeners = new LinkedList<AffinityMenuListener>();
		if(createFileMenu) {
			createFileMenu("&File", createFileExitItem);
		}
		if(createViewMenu) {
			createViewMenu("&View", createCraigrogramOptions, createDendrogramOptions);
		}
	}
	
	public Menu getMenuBar() {
		return menuBar;
	}
	
	public void setCurrentCraigrogramOptions(boolean showToolBar, boolean showOverview, boolean showClusters,
			boolean fillClusters, boolean followConcavePaths) {
		showToolBarItem.setSelection(showToolBar);
		showOverviewItem.setSelection(showOverview);
		showClustersItem.setSelection(showClusters);
		fillClustersItem.setSelection(fillClusters);
		followConcavePathsItem.setSelection(followConcavePaths);
	}
	
	public void setCurrentDendrogramOptions(boolean showAlternatingColors, boolean shadeBackgrounds) {
		showAltColorsItem.setSelection(showAlternatingColors);
		shadeBackgroundsItem.setSelection(shadeBackgrounds);	
	}
	
	public void setCurrentCraigrogramZoom(float zoom) {
		for(int i=0; i<zoomLevelItems.length; i++) {
			if(AffinityConstants.ZOOM_LEVELS[i] == zoom) {
				zoomLevelItems[i].setSelection(true);
			} else { 
				zoomLevelItems[i].setSelection(false);
			}
		}
	}
	
	public void setCurrentCraigrogramTextSize(TextSize textSize, boolean namesVisible) {
		smallTextItem.setSelection(false); 
		normalTextItem.setSelection(false); 
		largeTextItem.setSelection(false);			
		hideTextItem.setSelection(false); 
		if(namesVisible) {
			switch(textSize) {
				case Small: smallTextItem.setSelection(true); break;
				case Normal: normalTextItem.setSelection(true); break;
				case Large: largeTextItem.setSelection(true); break;
			}
		} else {
			hideTextItem.setSelection(true); 
		}
	}
	
	public synchronized void addMenuItemListener(AffinityMenuItemListener listener) {
		if(!menuItemListeners.contains(listener)) {
			menuItemListeners.add(listener);
		}
	}
	
	public synchronized void removeMenuItemListener(AffinityMenuItemListener listener) {
		menuItemListeners.remove(listener);
	}
	
	protected void fireMenuItemEvent(MenuItem menuItem, Integer menuItemID, Integer parentMenuID) {
		if(!menuItemListeners.isEmpty()) {			
			for(AffinityMenuItemListener listener : menuItemListeners.toArray(new AffinityMenuItemListener[menuItemListeners.size()])) {
				listener.menuItemSelected(menuItem, menuItemID, parentMenuID);
			}
		}
	}	
	
	public synchronized void addMenuListener(AffinityMenuListener listener) {
		if(!menuListeners.contains(listener)) {
			menuListeners.add(listener);
		}
	}
	
	public synchronized void removeMenuListener(AffinityMenuListener listener) {
		menuListeners.remove(listener);
	}
	
	protected void fireMenuEvent(Menu menu, Integer menuID, Integer parentMenuID, boolean menuShown) {		
		if(!menuListeners.isEmpty()) {
			if(menuShown) {
				for(AffinityMenuListener listener : menuListeners.toArray(new AffinityMenuListener[menuListeners.size()])) {
					listener.menuShown(menu, menuID, parentMenuID);
				}
			}
		}
	}	

	protected void createFileMenu(String menuName, boolean createExitItem) {
		if(fileMenu == null) {			
			fileMenu = createTopLevelMenu(menuName, SWT.DROP_DOWN, FILE_MENU);
			if(createExitItem) {
				addExitItemToFileMenu();
			}
		}
	}
	
	protected void addExitItemToFileMenu() {
		if(fileMenu != null) {
			createMenuItem(fileMenu, "E&xit", SWT.NONE, FILE_EXIT_ITEM, FILE_MENU, true);
		}
	}	
	
	protected void createViewMenu(String menuName, boolean createCraigrogramOptions, boolean createDendrogramOptions) {
		if(viewMenu == null) {
			viewMenu = createTopLevelMenu(menuName, SWT.DROP_DOWN, VIEW_MENU);
			if(createCraigrogramOptions) {
				addCraigrogramOptionsToViewMenu();
			}
			if(createDendrogramOptions) {
				addDendrogramOptionsToViewMenu();
			}
		}
	}
	
	protected void addCraigrogramOptionsToViewMenu() {
		if(viewMenu != null) {
			Menu craigrogramMenu = createSubMenu(viewMenu, "Craig-Ro-Gram Options", SWT.DROP_DOWN,
					VIEW_CLUSTER2DOPTIONS_MENU, VIEW_MENU, true);
			
			//Create text size sub-menu
			final Menu textSizeMenu = createSubMenu(craigrogramMenu, "Text Size", SWT.DROP_DOWN,
					VIEW_CLUSTER2DOPTIONS_TEXTSIZE_MENU, VIEW_CLUSTER2DOPTIONS_MENU, true);
			smallTextItem = createMenuItem(textSizeMenu, "Small", SWT.RADIO,
					VIEW_CLUSTER2DOPTIONS_TEXTSIZE_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			normalTextItem = createMenuItem(textSizeMenu, "Normal", SWT.RADIO,
					VIEW_CLUSTER2DOPTIONS_TEXTSIZE_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			normalTextItem.setSelection(true);
			textSizeMenu.setDefaultItem(normalTextItem);
			largeTextItem = createMenuItem(textSizeMenu, "Large", SWT.RADIO,
					VIEW_CLUSTER2DOPTIONS_TEXTSIZE_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			hideTextItem = createMenuItem(textSizeMenu, "Hide", SWT.RADIO,
					VIEW_CLUSTER2DOPTIONS_TEXTSIZE_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			
			//Create zoom sub-menu	
			final Menu zoomMenu = createSubMenu(craigrogramMenu, "Zoom", SWT.DROP_DOWN,
					VIEW_CLUSTER2DOPTIONS_ZOOM_MENU, VIEW_CLUSTER2DOPTIONS_MENU, true);
			zoomLevelItems = new MenuItem[AffinityConstants.ZOOM_LEVELS.length];
			int i = 0;
			for(int zoomLevel : AffinityConstants.ZOOM_LEVELS) {
				MenuItem zoomLevelItem = createMenuItem(zoomMenu, Integer.toString(zoomLevel) + "%", SWT.RADIO,
						VIEW_CLUSTER2DOPTIONS_ZOOM_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
				zoomLevelItem.setData("ZoomLevel", i);
				zoomLevelItems[i] = zoomLevelItem;
				i++;
			}
			fitInWindowItem = createMenuItem(zoomMenu, "Fit In Window", SWT.NONE,
					VIEW_CLUSTER2DOPTIONS_ZOOM_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			fitInWindowItem.setData("ZoomLevel", -1);
			
			//Create show tool bar check box
			showToolBarItem = createMenuItem(craigrogramMenu, "Show Tool Bar", SWT.CHECK,
					VIEW_CLUSTER2DOPTIONS_SHOW_TOOLBAR_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			showToolBarItem.setSelection(true);
			
			//Create show overview check box
			showOverviewItem = createMenuItem(craigrogramMenu, "Show Overview", SWT.CHECK,
					VIEW_CLUSTER2DOPTIONS_SHOW_OVERVIEW_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			showOverviewItem.setSelection(true);
			
			//Create show cluster object names check box
			showTitlesItem = createMenuItem(craigrogramMenu, "Show Object Names", SWT.CHECK,
					VIEW_CLUSTER2DOPTIONS_SHOW_NAMES_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true); 
			showTitlesItem.setSelection(true);
				
			//Create show clusters check box
			showClustersItem = createMenuItem(craigrogramMenu, "Show Clusters", SWT.CHECK,
					VIEW_CLUSTER2DOPTIONS_SHOW_CLUSTERS_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			showClustersItem.setSelection(true);
			
			//Create fill clusters check box
			fillClustersItem = createMenuItem(craigrogramMenu, "Fill Clusters", SWT.CHECK,
					VIEW_CLUSTER2DOPTIONS_FILL_CLUSTERS_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			fillClustersItem.setSelection(true);
			
			//Create follow concave paths check box
			followConcavePathsItem = createMenuItem(craigrogramMenu, "Follow Concave Paths", SWT.CHECK,
					VIEW_CLUSTER2DOPTIONS_CONCAVE_PATHS_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);
			followConcavePathsItem.setSelection(false);
			
			//Create set cluster spacing menu item
			createMenuItem(craigrogramMenu, "Set Cluster Spacing", SWT.NONE,
					VIEW_CLUSTER2DOPTIONS_CLUSTER_SPACING_ITEM, VIEW_CLUSTER2DOPTIONS_MENU, true);			
		}
	}
	
	protected void addDendrogramOptionsToViewMenu() {
		if(viewMenu != null) {			
			Menu dendrogramMenu = createSubMenu(viewMenu, "Dendrogram Options", SWT.DROP_DOWN,
					AffinityMenu.VIEW_DENDROGRAMDOPTIONS_MENU, AffinityMenu.VIEW_MENU, true);
			
			showAltColorsItem = createMenuItem(dendrogramMenu, "Show Alternating Colors", SWT.CHECK,
					AffinityMenu.VIEW_DENDROGRAMOPTIONS_ALTERNATING_COLORS_ITEM, 
					AffinityMenu.VIEW_DENDROGRAMDOPTIONS_MENU, true);
			showAltColorsItem.setSelection(true);
			
			shadeBackgroundsItem = createMenuItem(dendrogramMenu, "Shade Backgrounds", SWT.CHECK,
					AffinityMenu.VIEW_DENDROGRAMOPTIONS_SHADE_BACKGROUNDS_ITEM, 
					AffinityMenu.VIEW_DENDROGRAMDOPTIONS_MENU, true);
			shadeBackgroundsItem.setSelection(true);
		}
	}
	
	protected Menu createTopLevelMenu(String text, int style, int menuID) {
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText(text);			
		Menu menu = new Menu(menuBar.getParent(), style);
		menu.setData("menuID", menuID);
		item.setMenu(menu);
		return menu;
	}
	
	protected Menu createSubMenu(Menu parent, String text, int style, int menuID, int parentMenuID, 
			boolean registerWithMenuListener) {
		MenuItem menuItemForMenu = new MenuItem(parent, SWT.CASCADE);		
		menuItemForMenu.setText(text);		
		Menu menu = new Menu(decorations, style);
		menu.setData("menuID", menuID);
		menu.setData("parentMenuID", parentMenuID);
		menuItemForMenu.setMenu(menu);
		if(registerWithMenuListener) {
			menu.addMenuListener(menuListener);
		}
		return menu;
	}
	
	protected MenuItem createMenuItem(Menu parent, String text,  int style, int itemID, int parentMenuID,
			boolean registerWithMenuItemListener) {
		MenuItem menuItem = new MenuItem(parent, style);		
		menuItem.setText(text);
		menuItem.setData("itemID", itemID);
		menuItem.setData("parentMenuID", parentMenuID);
		if(registerWithMenuItemListener) {
			menuItem.addSelectionListener(menuItemListener);
		}
		return menuItem;
	}
	
	protected class MenuListener extends MenuAdapter {
		@Override
		public void menuHidden(MenuEvent event) {
		}
		@Override
		public void menuShown(MenuEvent event) {
			Menu menu = (Menu)event.widget;
			Integer menuID = null;
			if(menu.getData("menuID") != null) {
				menuID = (Integer)menu.getData("menuID");
			}
			Integer parentMenuID = null;
			if(menu.getData("parentMenuID") != null) {
				parentMenuID = (Integer)menu.getData("parentMenuID");
			}
			fireMenuEvent((Menu)event.widget, menuID, parentMenuID, true);
		}
	}
	
	protected class MenuSelectionListener extends SelectionAdapter {
		@Override
		public void widgetDefaultSelected(SelectionEvent event) {}		
		@Override
		public void widgetSelected(SelectionEvent event) {
			MenuItem menuItem = (MenuItem)event.widget;
			Integer itemID = null;
			if(menuItem.getData("itemID") != null) {
				itemID = (Integer)menuItem.getData("itemID");
			}
			Integer parentMenuID = null;
			if(menuItem.getData("parentMenuID") != null) {
				parentMenuID = (Integer)menuItem.getData("parentMenuID");
			}
			fireMenuItemEvent((MenuItem)event.widget, itemID, parentMenuID);
		}
	}
}