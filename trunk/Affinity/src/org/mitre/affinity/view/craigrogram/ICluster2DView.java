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

import org.mitre.affinity.view.IClusterView;

/**
 * Interface for implementations that display clusters in a 2-D space.
 * 
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public interface ICluster2DView<K extends Comparable<K>, V> extends IClusterView<K, V> {
	
	public static enum Mode{PAN_AND_SELECT, SELECT_MULTIPLE_CLUSTER_OBJECTS};
	
	public void setZoom(float zoom);
	public float getZoom();
	public void zoomIn(int numLevels);
	public void zoomOut(int numLevels);
	public void fitInWindow();
	
	public void setMode(Mode mode);
	
	public void setLockAspectRatio(boolean lockAspectRatio);
	public boolean isLockAspectRatio();
	public void setClusterObjectNamesVisible(boolean b);
}