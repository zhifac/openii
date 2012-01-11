package org.mitre.affinity.view.menu.event;

import org.eclipse.swt.widgets.MenuItem;

/**
 * @author CBONACETO
 *
 */
public interface AffinityMenuItemListener {
	public void menuItemSelected(MenuItem menuItem, Integer menuItemID, Integer parentMenuID);
}
