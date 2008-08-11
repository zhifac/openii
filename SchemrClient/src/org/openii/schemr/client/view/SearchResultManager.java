package org.openii.schemr.client.view;

import java.util.ArrayList;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.openii.schemr.client.model.MatchSummary;

public class SearchResultManager {
	
	private static SearchResultManager instance = null;
	private SearchResultManager() {}

	public static SearchResultManager getInstance() {
		if (instance == null) {
			instance = new SearchResultManager();
		}
		return instance;
	}
	
	ArrayList<IPropertyChangeListener> _listeners = new ArrayList<IPropertyChangeListener>();

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		if(!_listeners.contains(listener)) _listeners.add(listener);
	}

	// A public method that allows listener registration
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		_listeners.remove(listener);
	}

	public void resultSelected(Object source, MatchSummary newMatchSummary) {

		for (IPropertyChangeListener p : _listeners) {
			
			Object oldValue = null;
			PropertyChangeEvent e = new PropertyChangeEvent(source, "ResultSelected", oldValue, newMatchSummary);
			p.propertyChange(e);
		}
	}


	
}
