package org.openii.schemr.client;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.openii.schemr.client.action.BuildIndexAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    // Actions - important to allocate these only in makeActions, and then use them
    // in the fill methods.  This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction newWindowAction;
    private IWorkbenchAction launchPreferencesAction;
    private BuildIndexAction rebuildIndexAction;
//    private SearchAction searchAction;
//    private ResultVizSelectAction resultVizSelectAction;
    

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }
    
    protected void makeActions(final IWorkbenchWindow window) {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.

        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        
        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(newWindowAction);

//        launchPreferencesAction = ActionFactory.PREFERENCES.create(window);
        
        rebuildIndexAction = new BuildIndexAction(window, "Rebuild Index");
        
//        searchAction = new SearchAction(window, "Search for something", SearchBoxView.ID);
//      searchAction = new SearchAction(window, "Search for Schemas");
//      register(searchAction);
        
//        resultVizSelectAction = new ResultVizSelectAction(window, "Open Result", ResultVizView.ID);
//        register(resultVizSelectAction);
    }
    
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
        MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
        
        menuBar.add(fileMenu);
        // Add a group marker indicating where action set menus will appear.
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(helpMenu);
        
        // File
        fileMenu.add(rebuildIndexAction);
        fileMenu.add(new Separator());
        fileMenu.add(newWindowAction);
//        fileMenu.add(launchPreferencesAction);
//        fileMenu.add(searchAction);
//        fileMenu.add(resultVizSelectAction);
//        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        
        // Help
        helpMenu.add(aboutAction);
    }
    
//    protected void fillCoolBar(ICoolBarManager coolBar) {
//        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
//        coolBar.add(new ToolBarContributionItem(toolbar, "main"));
//        toolbar.add(searchAction);
//    }
}
