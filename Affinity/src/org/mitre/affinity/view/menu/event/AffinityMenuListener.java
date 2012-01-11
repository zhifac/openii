package org.mitre.affinity.view.menu.event;

import org.eclipse.swt.widgets.Menu;

/**
 * @author CBONACETO
 *
 */
public interface AffinityMenuListener {
	public void menuShown(Menu menu, Integer menuID, Integer parentMenuID);
}