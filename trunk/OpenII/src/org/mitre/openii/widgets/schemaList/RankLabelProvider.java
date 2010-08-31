package org.mitre.openii.widgets.schemaList;

import java.util.HashMap;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

public class RankLabelProvider extends CellLabelProvider {

	private HashMap<Integer, Integer> rankings;

	/** Constructs the model label provider */
	public RankLabelProvider(HashMap<Integer, Integer> rankings) {
		this.rankings = rankings;
	}
	
	/** Returns the image associated with the specified element */
	public Image getImage(Object element) {
		return null;
	}

	/** Returns the name of the model associated with the specified element */
	public String getText(Object schemaID) {
		Integer rank = rankings.get(schemaID);
		return rank.toString();
	}

	/** Indicates that the label is not influenced by an element property */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/** Display the image and text for the cell element */
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		cell.setImage(getImage(element));
		cell.setText(getText(element));
	}

	// Unused functions
	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}
