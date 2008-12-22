package openii.ygg.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.SWT;


/**
 * Hijacked a sample class to get our hooks into Eclipse.  This should be renamed or replaced.<p>
 */

public class SampleView extends ViewPart {

	/**
	 * The constructor.
	 */
	public SampleView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		new ParentComposite( parent, SWT.BORDER_SOLID );
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		//viewer.getControl().setFocus();
	}
}