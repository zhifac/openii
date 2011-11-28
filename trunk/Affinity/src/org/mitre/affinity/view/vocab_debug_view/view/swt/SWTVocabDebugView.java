/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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

package org.mitre.affinity.view.vocab_debug_view.view.swt;

import org.mitre.affinity.view.vocab_debug_view.model.VocabDebugViewSets;
import org.mitre.affinity.view.vocab_debug_view.swing.VocabDebugPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class SWTVocabDebugView extends Canvas {
	private final VocabDebugPane vocabDebugPane;
	
	public SWTVocabDebugView(Composite parent, int style, VocabDebugViewSets sets) {		
		super(parent, style | SWT.EMBEDDED);
		
		java.awt.Frame frame = SWT_AWT.new_Frame(this);		
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
	
		vocabDebugPane = new VocabDebugPane(sets);
		panel.add(vocabDebugPane);	
		frame.add(panel);
	}

	public VocabDebugPane getVennDiagram() {
		return vocabDebugPane;
	}	
}