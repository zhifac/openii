package org.mitre.affinity.controller;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.AffinityConstants;
import org.mitre.affinity.view.IClusterObjectGUI;
import org.mitre.affinity.view.craigrogram.Cluster2DViewPane;
import org.mitre.affinity.view.craigrogram.ClusterRadiusDlg;
import org.mitre.affinity.view.dendrogram.DendrogramCanvas;
import org.mitre.affinity.view.menu.AffinityMenu;
import org.mitre.affinity.AffinityConstants.TextSize;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public abstract class BasicAffinityController<K extends Comparable<K>, V> implements IAffinityController<K, V> {
	
	/** Reference to the pane with the Craigrogram */	
	protected Cluster2DViewPane<K, V> craigrogram;
	
	/** Reference to the pane with the Dendrogram */	
	protected DendrogramCanvas<K, V> dendrogram;
	
	/** Reference to the menu */
	protected AffinityMenu menu;
	
	/** Reference to the application window (if any) */
	protected Composite parentWindow;
	
	/**	Dialog to set the cluster radius in the Craigrogram */
	protected ClusterRadiusDlg clusterRadiusDlg;
	
	public BasicAffinityController(Composite parentWindow, Cluster2DViewPane<K, V> craigrogram, 
			DendrogramCanvas<K, V> dendrogram, AffinityMenu menu) {
		this.parentWindow = parentWindow;
		this.craigrogram = craigrogram;
		this.dendrogram = dendrogram;
		this.menu = menu;
		if(menu != null) {
			menu.addMenuItemListener(this);
			menu.addMenuListener(this);
		}
	}
	
	@Override
	public void menuItemSelected(MenuItem menuItem, Integer menuItemID, Integer parentMenuID) {
		processMenuItemSelectedEvent(menuItem, menuItemID, parentMenuID);
	}
	
	@Override
	public void menuShown(Menu menu, Integer menuID, Integer parentMenuID) {
		processMenuShownEvent(menu, menuID, parentMenuID);
	}
	
	/**
	 * @param menuItem
	 * @param menuItemID
	 * @param parentMenuID
	 * @return whether the menu item was processed
	 */
	protected boolean processMenuItemSelectedEvent(MenuItem menuItem, Integer menuItemID, Integer parentMenuID) {
		if(parentMenuID != null && menuItemID != null) {
			switch(parentMenuID) {
			case AffinityMenu.FILE_MENU:
				switch(menuItemID) {
				case AffinityMenu.FILE_EXIT_ITEM:
					//Exit the application
					if(parentWindow != null && parentWindow.getShell() != null) {
						parentWindow.getShell().dispose();
						return true;
					}
				}
				break;
			case AffinityMenu.VIEW_CLUSTER2DOPTIONS_MENU:
				if(craigrogram != null) {
					switch(menuItemID) {
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_TEXTSIZE_ITEM:
						//Adjust text size in Craigrogram
						if(menuItem.getText().startsWith("Small")) { 
							craigrogram.setClusterObjectNamesVisible(true);
							craigrogram.setTextSize(TextSize.Small);
						} else if(menuItem.getText().startsWith("Normal")) {
							craigrogram.setClusterObjectNamesVisible(true);
							craigrogram.setTextSize(TextSize.Normal);
						} else if(menuItem.getText().startsWith("Large")) {
							craigrogram.setClusterObjectNamesVisible(true);
							craigrogram.setTextSize(TextSize.Large);
						} else {
							//hide text
							craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(false);
						}
						return true;		
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_ZOOM_ITEM:
						//Adjust zoom in Craigrogram
						if(menuItem.getData("ZoomLevel") != null) {
							int level = (Integer)menuItem.getData("ZoomLevel");
							if(level == -1) {
								//Fit in window was selected
								craigrogram.fitInWindow();
							}
							else {
								//Set zoom level to selected level
								craigrogram.setZoomPercent(AffinityConstants.ZOOM_LEVELS[level]);
							}
							return true;
						}
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_SHOW_TOOLBAR_ITEM:
						//Show/hide Craigrogram toolbar
						craigrogram.setShowToolBar(menuItem.getSelection());
						craigrogram.redraw();
						return true;
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_SHOW_OVERVIEW_ITEM:
						//Show/hide overview view in Craigrogram
						craigrogram.getClusterObject2DPlot().setShowOverview(menuItem.getSelection());
						craigrogram.redraw();
						return true;
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_SHOW_NAMES_ITEM:
						//Show/hide cluster object names in Craigrogram
						boolean selected = menuItem.getSelection();				
						craigrogram.getClusterObject2DPlot().setClusterObjectNamesVisible(selected);					
						List<IClusterObjectGUI<K, V>> clusterObjects = craigrogram.getClusterObject2DPlot().getClusterObjects();
						for(IClusterObjectGUI<K, V> c : clusterObjects) {
							c.setShowLabel(selected);			
						}		
						craigrogram.redraw();
						return true;
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_SHOW_CLUSTERS_ITEM:
						//Show/hide clusters in Craigrogram
						craigrogram.getClusterObject2DPlot().setShowClusters(menuItem.getSelection());
						craigrogram.redraw();
						return true;
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_FILL_CLUSTERS_ITEM:
						//Set whether to fill clusters in Craigrogram
						craigrogram.getClusterObject2DPlot().setFillClusters(menuItem.getSelection());
						craigrogram.redraw();
						return true;
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_CONCAVE_PATHS_ITEM:
						//Set whether to follow concave paths in Craigrogram
						selected = menuItem.getSelection();				
						craigrogram.getClusterObject2DPlot().setUseConcavitySkipLocic(!selected);
						craigrogram.getClusterObject2DPlot().setUseClusterObjectSkipLogic(!selected);
						craigrogram.redraw();
						return true;
					case AffinityMenu.VIEW_CLUSTER2DOPTIONS_CLUSTER_SPACING_ITEM:
						//Show dialog to set cluster spacing in Craigrogram
						if(clusterRadiusDlg == null || clusterRadiusDlg.isDisposed()) {
							clusterRadiusDlg = new ClusterRadiusDlg(parentWindow.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
						}
						clusterRadiusDlg.setRadius(craigrogram.getClusterObject2DPlot().getRadiusIncrement());
						if(!clusterRadiusDlg.isVisible()) {					
							ClusterRadiusDlg.DialogModel model = (ClusterRadiusDlg.DialogModel)clusterRadiusDlg.setVisible(true);
							if(model.buttonPushed == ClusterRadiusDlg.DialogModel.ButtonType.OK) {
								craigrogram.getClusterObject2DPlot().setRadiusIncrement(model.radius);						
							}					
							craigrogram.getClusterObject2DPlot().redraw();
						}
						return true;
					}
				}
				break;
			case AffinityMenu.VIEW_DENDROGRAMDOPTIONS_MENU:
				if(dendrogram != null) {
					switch(menuItemID) {
					case AffinityMenu.VIEW_DENDROGRAMOPTIONS_ALTERNATING_COLORS_ITEM:
						dendrogram.setShowClusters(menuItem.getSelection());
						dendrogram.redraw();
						return true;
					case AffinityMenu.VIEW_DENDROGRAMOPTIONS_SHADE_BACKGROUNDS_ITEM:
						dendrogram.setFillClusters(menuItem.getSelection());
						dendrogram.redraw();
						return true;
					}
				}
				break;
			}
		}
		return false;
	}
	
	protected boolean processMenuShownEvent(Menu menu, Integer menuID, Integer parentMenuID) {
		if(this.menu != null && menuID != null) {
			switch(menuID) {
			case AffinityMenu.VIEW_CLUSTER2DOPTIONS_MENU:
				if(craigrogram != null) {
					this.menu.setCurrentCraigrogramOptions(craigrogram.isShowToolBar(), 
							craigrogram.getClusterObject2DPlot().isShowOverview(), 
							craigrogram.getClusterObject2DPlot().isShowClusters(), 
							craigrogram.getClusterObject2DPlot().isFillClusters(),
							!craigrogram.getClusterObject2DPlot().isUseConcavitySkipLocic());
					return true;
				}
			case AffinityMenu.VIEW_DENDROGRAMDOPTIONS_MENU:
				if(dendrogram != null) {
					this.menu.setCurrentDendrogramOptions(dendrogram.isShowClusters(), 
							dendrogram.isFillClusters());
					return true;
				}
			case AffinityMenu.VIEW_CLUSTER2DOPTIONS_TEXTSIZE_MENU:
				if(craigrogram != null) {
					this.menu.setCurrentCraigrogramTextSize(craigrogram.getTextSize(), 
							craigrogram.getClusterObject2DPlot().isClusterObjectNamesVisible());					
					return true;
				}
			case AffinityMenu.VIEW_CLUSTER2DOPTIONS_ZOOM_MENU:
				if(craigrogram != null) {
					this.menu.setCurrentCraigrogramZoom(craigrogram.getZoomPercent());
					return true;
				}
			}
		}
		return false;
	}	
}