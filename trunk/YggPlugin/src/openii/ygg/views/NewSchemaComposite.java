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

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;

public class NewSchemaComposite extends org.eclipse.swt.widgets.Composite {
	private CLabel cLabel1;
	private Text schemaName;
	private Label label1;
	private Button create;
	private Text description;
	private Label label2;
	private Text authorName;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		NewSchemaComposite inst = new NewSchemaComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
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

	public NewSchemaComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			//thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(685, 292);
			{
				cLabel1 = new CLabel(this, SWT.NONE);
				GridData cLabel1LData = new GridData();
				cLabel1LData.horizontalAlignment = GridData.END;
				cLabel1.setLayoutData(cLabel1LData);
				cLabel1.setText("Schema Name");
			}
			{
				schemaName = new Text(this, SWT.NONE);
				GridData schemaNameLData = new GridData();
				schemaNameLData.heightHint = 17;
				schemaNameLData.grabExcessHorizontalSpace = true;
				schemaNameLData.horizontalAlignment = GridData.FILL;
				schemaName.setLayoutData(schemaNameLData);
			}
			{
				label1 = new Label(this, SWT.NONE);
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.END;
				label1.setLayoutData(label1LData);
				label1.setText("Author Name");
			}
			{
				authorName = new Text(this, SWT.NONE);
				GridData authorNameLData = new GridData();
				authorNameLData.grabExcessHorizontalSpace = true;
				authorNameLData.horizontalAlignment = GridData.FILL;
				authorName.setLayoutData(authorNameLData);
			}
			{
				label2 = new Label(this, SWT.NONE);
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.END;
				label2LData.verticalAlignment = GridData.BEGINNING;
				label2.setLayoutData(label2LData);
				label2.setText("Description");
			}
			{
				description = new Text(this, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
				GridData descriptionLData = new GridData();
				descriptionLData.horizontalAlignment = GridData.FILL;
				descriptionLData.verticalAlignment = GridData.FILL;
				descriptionLData.grabExcessHorizontalSpace = true;
				descriptionLData.grabExcessVerticalSpace = true;
				description.setLayoutData(descriptionLData);
			}
			{
				create = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData createLData = new GridData();
				createLData.horizontalSpan = 2;
				createLData.horizontalAlignment = GridData.CENTER;
				create.setLayoutData(createLData);
				create.setText("Create");
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
