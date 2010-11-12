// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.harmony.view.mappingPane.MappingPane;

/**
 * Manages the display of dialogs in Harmony (which are really internal frames)
 * @author CWOLF
 */
public class DialogManager extends DefaultDesktopManager implements InternalFrameListener
{
	/** Stores reference to the MappingPane */
	private MappingPane mappingPane = null;
	
	/** Keep track of locked dialogs */
	private HashMap<JInternalFrame, JInternalFrame> lockedDialogs = new HashMap<JInternalFrame,JInternalFrame>();
	
	/** Retrieves the Harmony menu bar */
	private void setMenuBarEnabled(boolean enabled)
	{
		// Get the menu bar
		Component component = mappingPane;
		while(!(component instanceof HarmonyFrame))
			component = component.getParent();
		JMenuBar menuBar = ((HarmonyFrame)component).getJMenuBar();
		
		// Enable/disable all menus
		for(int i=0; i<menuBar.getMenuCount(); i++)
			menuBar.getMenu(i).setEnabled(enabled);
	}
	
	/** Constructs the MappingPane manager */
	public DialogManager(MappingPane mappingPane)
		{ this.mappingPane = mappingPane; }

	/** Shows a dialog (internal frame) on top of the mapping pane */
	public void showDialog(JInternalFrame dialog)
		{ showDialog(dialog, null); }
	
	/** Shows a dialog (internal frame) on top of the mapping pane */
	public void showDialog(JInternalFrame dialog, JInternalFrame parentDialog)
	{
		Component parent = parentDialog==null ? mappingPane : parentDialog;
		
		// Disable the parent dialog and menu bar (cause modal type behavior)
		if(parentDialog!=null)
		{
			parentDialog.setEnabled(false);
			lockedDialogs.put(parentDialog,dialog);
		}
		setMenuBarEnabled(false);
		
		// Calculate the shift
		Integer xShift=parent.getX(), yShift=parent.getY();
		Component base = parent;
		while(!(base instanceof MappingPane))
		{
			base = base.getParent();
			xShift += base.getX(); yShift += base.getY();
		}
		
		// Place the dialog
		Integer x = xShift + (parent.getWidth()-dialog.getWidth())/2;
		Integer y = yShift + (parent.getHeight()-dialog.getHeight())/2;
		dialog.setLocation(x, y);

		// Display the dialog
		mappingPane.add(dialog,JLayeredPane.POPUP_LAYER);
		activateFrame(dialog);
		dialog.addInternalFrameListener(this);
		try { dialog.setSelected(true); } catch(Exception e) {}
	}

	/** Only activate if no dialog has higher priority */
	public void activateFrame(JInternalFrame dialog)
		{ if(!lockedDialogs.containsKey(dialog)) super.activateFrame(dialog); }

	/** Remove the priority flag */
	public void internalFrameClosed(InternalFrameEvent e)
	{
		// Search for parent dialog
		JInternalFrame dialog = e.getInternalFrame();
		for(JInternalFrame parentDialog : lockedDialogs.keySet())
			if(lockedDialogs.get(parentDialog).equals(dialog))
			{
				// Re-enable parent dialog
				lockedDialogs.remove(parentDialog);
				parentDialog.setEnabled(true);
				try { parentDialog.setSelected(true); } catch(Exception e2) {}
				break;
			}
		
		// Reactivate the menu bar if no more dialogs exist
		if(lockedDialogs.size()==0)
			setMenuBarEnabled(true);
	}
	
	// Unused event listeners
	public void internalFrameOpened(InternalFrameEvent e) {}
	public void internalFrameClosing(InternalFrameEvent e) {}
	public void internalFrameIconified(InternalFrameEvent e) {}
	public void internalFrameDeiconified(InternalFrameEvent e) {}
	public void internalFrameActivated(InternalFrameEvent e) {}
	public void internalFrameDeactivated(InternalFrameEvent e) {}
}