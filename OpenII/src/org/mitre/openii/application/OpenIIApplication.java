package org.mitre.openii.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * Class for controlling all aspects of the application's execution
 */
public class OpenIIApplication implements IApplication
{
	/** Class for defining the window layout for the workbench advisor */
	public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
	{
		@Override
		public void createWindowContents(Shell shell)
		{
			super.createWindowContents(shell);
			shell.setImage(OpenIIActivator.getImage("OpenII.gif"));
			Window.setDefaultImage(OpenIIActivator.getImage("OpenII.gif"));
		}

		/** Constructs the workbench window advisor */
	    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
	    	{ super(configurer); }
	    
	    /** Defines the layout of the workbench */
	    public void preWindowOpen()
	    {
	        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
	        configurer.setInitialSize(new Point(1024, 768));
	        configurer.setShowCoolBar(false);
	        configurer.setShowStatusLine(false);
	    }    
	}
	
	/** Class for defining which perspectives should be displayed in the workbench */
	private class OpenIIWorkbenchAdvisor extends WorkbenchAdvisor
	{
		private static final String PERSPECTIVE_ID = "org.mitre.openii.plugin.OpenIIPerspective";

		/** Creates the workbench advisor */
	    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
	    	{ return new ApplicationWorkbenchWindowAdvisor(configurer); }
	 
		/** Initializes how the workbench perspective should be displayed */
		public void initialize(IWorkbenchConfigurer configurer)
		{
			super.initialize(configurer);
	    	PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		}
	    
	    /** Defines the initial perspective for the workbench advisor */
		public String getInitialWindowPerspectiveId()
			{ return PERSPECTIVE_ID; }
	}
	
	/** Starts the OpenII application */
	public Object start(IApplicationContext context)
	{
		Display display = PlatformUI.createDisplay();
		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display, new OpenIIWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART)
				return IApplication.EXIT_RESTART;
			return IApplication.EXIT_OK;
		}
		finally { display.dispose(); }
	}

	/** Stops the OpenII application */
	public void stop()
	{
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
