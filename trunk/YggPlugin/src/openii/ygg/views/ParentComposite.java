/*
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package openii.ygg.views;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.importers.Importer;
import org.mitre.schemastore.importers.ImporterException;
import org.mitre.schemastore.importers.ImporterManager;

public class ParentComposite extends org.eclipse.swt.widgets.Composite {
	private TabFolder tabFolder1;
	private TabItem tabItem1;
	private Label label1;
	private Button importbutton;
	private Button button1;
	private Label label3;
	private Text text1;
	private Combo combo1;
	private Group group3;
	private Label label2;
	private TabItem importers;
	private Label temp;
	private List schemaList;
	private Group group2;
	private TabItem showSchemas;
	private Button saveButton;
	private Text storeUrl;
	private Group group1;
	private SchemaStoreClient client = null;
	private ImporterManager manager = null;

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		ParentComposite inst = new ParentComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public ParentComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new FormLayout());
			this.setSize(705, 250);
			{
				tabFolder1 = new TabFolder(this, SWT.NONE);
				FormData tabFolder1LData = new FormData();
				tabFolder1LData.width = 672;
				tabFolder1LData.height = 201;
				tabFolder1LData.left =  new FormAttachment(0, 1000, 6);
				tabFolder1LData.top =  new FormAttachment(0, 1000, 23);
				tabFolder1.setLayoutData(tabFolder1LData);
				{
					tabItem1 = new TabItem(tabFolder1, SWT.NONE);
					tabItem1.setText("Configure Ygg");
					{
						group1 = new Group(tabFolder1, SWT.NONE);
						FormLayout group1Layout = new FormLayout();
						group1.setLayout(group1Layout);
						tabItem1.setControl(group1);
						group1.setText("Server Configuration");
						{
							saveButton = new Button(group1, SWT.PUSH | SWT.CENTER | SWT.BORDER);
							saveButton.setText("Save");
							FormData saveButtonLData = new FormData();
							saveButtonLData.width = 42;
							saveButtonLData.height = 27;
							saveButtonLData.left =  new FormAttachment(464, 1000, 0);
							saveButtonLData.top =  new FormAttachment(788, 1000, 0);
							saveButton.setLayoutData(saveButtonLData);
						}
						{
							storeUrl = new Text(group1, SWT.BORDER);
							storeUrl.setText("http://brainsrv2:8090/SchemaStore/services/SchemaStore");
							FormData storeUrlLData = new FormData();
							storeUrlLData.width = 373;
							storeUrlLData.height = 17;
							storeUrlLData.left =  new FormAttachment(0, 1000, 199);
							storeUrlLData.top =  new FormAttachment(0, 1000, 12);
							storeUrl.setLayoutData(storeUrlLData);
						}
						{
							label1 = new Label(group1, SWT.NONE);
							label1.setText("Schema Store Server URL");
							FormData label1LData = new FormData();
							label1LData.width = 167;
							label1LData.height = 21;
							label1LData.left =  new FormAttachment(0, 1000, 10);
							label1LData.top =  new FormAttachment(0, 1000, 12);
							label1.setLayoutData(label1LData);
						}
					}
				}
				{
					showSchemas = new TabItem(tabFolder1, SWT.NONE);
					showSchemas.setText("List Schemas");
					{
						group2 = new Group(tabFolder1, SWT.NONE);
						GridLayout group2Layout = new GridLayout();
						group2Layout.makeColumnsEqualWidth = true;
						group2Layout.numColumns = 2;
						group2.setLayout(group2Layout);
						showSchemas.setControl(group2);
						group2.setText("Loaded Schemas");
						{
							GridData list1LData = new GridData();
							list1LData.widthHint = 251;
							list1LData.heightHint = 171;
							schemaList = new List(group2, SWT.V_SCROLL | SWT.BORDER);
							// Display the schemas found within the repository
							try {
								client = new SchemaStoreClient("http://brainsrv2:8090/SchemaStore/services/SchemaStore");
								manager = new ImporterManager(client);
								for(Schema schema : client.getSchemas())
									schemaList.add(schema.getName());
							}
							catch(Exception e) { e.printStackTrace(); }
							schemaList.setLayoutData(list1LData);
						}
						{
							temp = new Label(group2, SWT.WRAP | SWT.BORDER);
							GridData tempLData = new GridData();
							tempLData.widthHint = 327;
							tempLData.heightHint = 86;
							tempLData.verticalAlignment = GridData.BEGINNING;
							tempLData.horizontalAlignment = GridData.BEGINNING;
							temp.setLayoutData(tempLData);
							temp.setText("Selected schema goes here");
							schemaList.addListener(SWT.Selection, new Listener() {
								 public void handleEvent(Event event)
								 {
									 temp.setText(schemaList.getSelection()[0] + " is selected.  Eventually this area will have a view of the schema...probably a tree view.");
								 }
							 });	
						}
					}
				}
				{
					importers = new TabItem(tabFolder1, SWT.NONE);
					importers.setText("Import Schema");
					{
						group3 = new Group(tabFolder1, SWT.NONE);
						FormLayout group3Layout = new FormLayout();
						group3.setLayout(group3Layout);
						importers.setControl(group3);
						group3.setText("group3");
						{
							importbutton = new Button(group3, SWT.PUSH | SWT.CENTER);
							importbutton.setText("Import");
							FormData importLData = new FormData();
							importLData.width = 103;
							importLData.height = 27;
							importLData.left =  new FormAttachment(0, 1000, 299);
							importLData.top =  new FormAttachment(0, 1000, 97);
							importbutton.setLayoutData(importLData);
							importbutton.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									System.out.println("importbutton.widgetSelected, event="+evt);
									//TODO add your code for importbutton.widgetSelected
							        File file = new File(text1.getText());
							        Importer tester = manager.getImporter(combo1.getText());
							        tester.setClient( client );
							        try {
										tester.importSchema(file.getName(), System.getProperty("user.name"), "Good description of the spreadsheet goes here", file.toURI());
									} catch (ImporterException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								public void widgetDefaultSelected(SelectionEvent evt) {
									System.out.println("importbutton.widgetDefaultSelected, event="+evt);
									//TODO add your code for importbutton.widgetDefaultSelected
								}
							});
						}
						{
							button1 = new Button(group3, SWT.PUSH | SWT.CENTER);
							button1.setText("Pick File....");
							FormData button1LData = new FormData();
							button1LData.width = 75;
							button1LData.height = 27;
							button1LData.left =  new FormAttachment(0, 1000, 307);
							button1LData.top =  new FormAttachment(0, 1000, 42);
							button1.setLayoutData(button1LData);
						}
						{
							label3 = new Label(group3, SWT.RIGHT);
							label3.setText("File:");
							FormData label3LData = new FormData();
							label3LData.width = 100;
							label3LData.height = 17;
							label3LData.left =  new FormAttachment(0, 1000, 4);
							label3LData.top =  new FormAttachment(0, 1000, 45);
							label3.setLayoutData(label3LData);
						}
						{
							text1 = new Text(group3, SWT.NONE);

						    FormData text1LData = new FormData();
							text1LData.width = 181;
							text1LData.height = 17;
							text1LData.left =  new FormAttachment(0, 1000, 110);
							text1LData.top =  new FormAttachment(0, 1000, 45);
							text1.setLayoutData(text1LData);
							SelectionListener listener = new SelectionListener() {
						        public void widgetSelected(SelectionEvent event) {
							          FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
							          fd.setText("Open");
							          fd.setFilterPath("C:/");
							          String[] filterExt = { "*.*", "*.xsd", "*.ddl", "*.sql", "*.owl", "*.txt", "*.xls" };
							          fd.setFilterExtensions(filterExt);
							          String selected = fd.open();
							          text1.setText(selected);
							        }

							        public void widgetDefaultSelected(SelectionEvent event) {
							        }
							      };
						    button1.addSelectionListener( listener);
						}
						{
							label2 = new Label(group3, SWT.NONE);
							FormData label2LData = new FormData();
							label2LData.width = 103;
							label2LData.height = 17;
							label2LData.left =  new FormAttachment(5, 1000, 0);
							label2LData.right =  new FormAttachment(159, 1000, 0);
							label2LData.top =  new FormAttachment(46, 1000, 0);
							label2LData.bottom =  new FormAttachment(140, 1000, 0);
							label2.setLayoutData(label2LData);
							label2.setText("Importer to use:");
						}
						{
							combo1 = new Combo(group3, SWT.NONE);
							FormData combo1LData = new FormData();
							combo1LData.width = 185;
							combo1LData.height = 27;
							combo1LData.left =  new FormAttachment(168, 1000, 0);
							combo1LData.right =  new FormAttachment(445, 1000, 0);
							combo1LData.top =  new FormAttachment(19, 1000, 0);
							combo1LData.bottom =  new FormAttachment(167, 1000, 0);
							combo1.setLayoutData(combo1LData);
							
							for( Importer item : manager.getImporters(null) )
							{
								combo1.add(item.getName());
							}
								
						}
					}
				}
				tabFolder1.setSelection(0);
			}

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
