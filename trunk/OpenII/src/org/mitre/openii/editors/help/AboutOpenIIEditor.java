package org.mitre.openii.editors.help;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

/** Constructs the About OpenII Editor */
public class AboutOpenIIEditor extends EditorPart {

	private Browser browser;
	private Text location;
	private Button backButton, forwardButton, homeButton;
	private Label status;

	/** Initializes the About OpenII Editor */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException	{
		setSite(site);
		setInput(input);
		setPartName("About OpenII");
	}

	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent) {
		// create a grid on the parent for us to put the browser,
		// the location bar and the back/forward buttons.
		// our grid will have three columns:
		//  1. back button
		//  2. forward button
		//  3. location bar
		//  4. home button
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		parent.setLayout(gridLayout);

		// get images for our back/forward/home buttons
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();

		// back button
		{
			backButton = new Button(parent, SWT.NONE);
			backButton.setToolTipText("Back");
			backButton.setImage(images.getImage(ISharedImages.IMG_TOOL_BACK));
	      	backButton.addListener(SWT.Selection, new Listener() {
	      		public void handleEvent(Event event) {
	      			browser.back();
	      		}
	      	});
		}

		// forward button
		{
			forwardButton = new Button(parent, SWT.NONE);
			forwardButton.setToolTipText("Forward");
			forwardButton.setImage(images.getImage(ISharedImages.IMG_TOOL_FORWARD));
	      	forwardButton.addListener(SWT.Selection, new Listener() {
	      		public void handleEvent(Event event) {
	  				browser.forward();
	      		}
	      	});
		}

		// home button
		{
			homeButton = new Button(parent, SWT.NONE);
			homeButton.setToolTipText("Documentation Home");
			homeButton.setImage(images.getImage(ISharedImages.IMG_ETOOL_HOME_NAV));
			homeButton.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event event) {
			        try {
			        	browser.setUrl(getHomePage());
			        } catch (HomePageNotFoundException e) {
			        	browser.setText(e.getMessage());
			        }
				}
			});
		}

		// location bar
		{
			location = new Text(parent, SWT.NONE);
			location.setEditable(false);
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			location.setLayoutData(data);
		}

		// browser
		{
			browser = new Browser(parent, SWT.SINGLE | SWT.BORDER);
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalSpan = 4;
			browser.setLayoutData(data);
		}

		// status bar
		{
			status = new Label(parent, SWT.BORDER_SOLID);
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.horizontalSpan = 4;
			status.setLayoutData(data);
		}

      	// will detect a change in the location of the browser and update the location bar
      	LocationListener locationListener = new LocationListener() {
            public void changed(LocationEvent e) {
            	Browser browser = (Browser)e.widget;
            	location.setText(browser.getUrl());
            	backButton.setEnabled(browser.isBackEnabled());
            	forwardButton.setEnabled(browser.isForwardEnabled());
            }
            public void changing(LocationEvent event) {}
        };
        browser.addLocationListener(locationListener);

        // will display status messages at the bottom of the screen
        StatusTextListener statusListener = new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				status.setText(event.text);
			}
		};
		browser.addStatusTextListener(statusListener);

		// tell the browser where to go now
        try {
        	browser.setUrl(getHomePage());
        } catch (HomePageNotFoundException e) {
        	browser.setText(e.getMessage());
        }
	}
	
	/** determines our home page */
	private String getHomePage() throws HomePageNotFoundException {
		// get the base location of our application so we can send our users to the documentation
		String homeURL = getClass().getProtectionDomain().getCodeSource().getLocation().toString().replaceAll("\\%20"," ");
		String homePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("\\%20"," ");

		// remove any references to the openii jar
		homeURL = homeURL.replaceAll("org\\.mitre\\.openii.*\\.jar$", "");
		homePath = homePath.replaceAll("org\\.mitre\\.openii.*\\.jar$", "");

		// check to see if it is in a location one above us
		// this should match both the IDE and standalone
		File f = new File(homePath + "../OpenII Documentation");
		if (f.exists()) {
			return homeURL + "../OpenII Documentation/html/index.html";
		}
		throw new HomePageNotFoundException("<html><body>Could not find OpenII Documentation.</body></html>");
	}

	/** Launches the editor */
	static public void launchEditor() {
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(null,"AboutOpenIIEditor");
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}