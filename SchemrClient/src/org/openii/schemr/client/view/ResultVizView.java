package org.openii.schemr.client.view;

import java.rmi.RemoteException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.openii.schemr.client.model.MatchSummary;
import org.openii.schemr.viz.PrefuseVisualizer;

import swingintegration.example.EmbeddedSwingComposite;

public class ResultVizView extends ViewPart implements IPropertyChangeListener {

	public static final String ID = "org.openii.schemr.client.view.resultVizView";

	private Composite _parent;
	private SchemaSwingComponent _swingHolder;	
	private MatchSummary _ms;
	
	@Override
	public void createPartControl(Composite parent) {		
		_parent = parent;
		// TODO this seems a bit hacky... must be some PDE facility for this
		SearchResultManager.getInstance().addPropertyChangeListener(this);
		update(null);
	}

	private void update(MatchSummary ms) {
		_ms = ms;
		if (_swingHolder != null) _swingHolder.dispose();
		_swingHolder = new SchemaSwingComponent(_parent, SWT.EMBEDDED);			
		_swingHolder.populate();
		_swingHolder.layout();
		_parent.layout();
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
		super.dispose();
		_swingHolder.dispose();
		_ms = null;
		SearchResultManager.getInstance().removePropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent e) {
		MatchSummary newMs = (MatchSummary) e.getNewValue();
		update(newMs);
	}
	
	public class SchemaSwingComponent extends EmbeddedSwingComposite {

		// TODO: this is quite a bit hacky as well
		private JComponent _viz = new JPanel();

		public SchemaSwingComponent(Composite parent, int style) {
			super(parent, style);
		}
		
		@Override
		protected JComponent createSwingComponent() {
			final Display display = Display.getDefault();
			display.syncExec (new Runnable () {
				public void run () {
					if (_ms == null) {
						_viz = new JPanel();
						_viz.add(new JLabel("Select search result"));
					} else {
						PrefuseVisualizer v;
						try {
							v = new PrefuseVisualizer(_ms);
							_viz = (JComponent) v.getDisplayComponent();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			});
			_viz.doLayout();
			return _viz;
		}
				
	} // SchemaSwingComponent class

}
