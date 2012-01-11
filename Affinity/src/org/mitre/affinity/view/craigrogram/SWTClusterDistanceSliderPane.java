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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.ClusterDistanceSliderPane;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public class SWTClusterDistanceSliderPane<K extends Comparable<K>, V> extends Canvas {
	
	private final ClusterDistanceSliderPane<K, V> clusterDistanceSliderPane;

	public SWTClusterDistanceSliderPane(Composite parent, int style, ClustersContainer<K> clusters, int orientation) {
		super(parent, style | SWT.EMBEDDED);
		
		java.awt.Frame frame = SWT_AWT.new_Frame(this);		
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
		
		this.clusterDistanceSliderPane = new ClusterDistanceSliderPane<K, V>(clusters, orientation);
		Color background = parent.getBackground();
		clusterDistanceSliderPane.setBackground(new java.awt.Color(background.getRed(), background.getGreen(), background.getBlue()));
		panel.add(this.clusterDistanceSliderPane);	
		frame.add(panel);
	}
	
	public void setClusters(ClustersContainer<K> clusters) {
		clusterDistanceSliderPane.setClusters(clusters);
	}

	public ClusterDistanceSliderPane<K, V> getClusterDistanceSliderPane() {
		return clusterDistanceSliderPane;
	}
}