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

package org.mitre.affinity.view.craigrogram;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.ClusterStepSizeSliderPane;

public class SWTClusterStepSizeSliderPane<K extends Comparable<K>, V> extends Canvas {
	
	private ClusterStepSizeSliderPane<K, V> clusterStepSizePane;

	public SWTClusterStepSizeSliderPane(Composite parent, int style, Cluster2DView<K, V> schema2DPlot, 
			int minStep, int maxStep) {
		super(parent, style | SWT.EMBEDDED);
		
		java.awt.Frame frame = SWT_AWT.new_Frame(this);		
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
		
		this.clusterStepSizePane = new ClusterStepSizeSliderPane<K, V>(schema2DPlot, minStep, maxStep);
		panel.add(this.clusterStepSizePane);	
		frame.add(panel);	
	}

	public ClusterStepSizeSliderPane<K, V> getClusterStepSizePane() {
		return clusterStepSizePane;
	}
}