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

package org.mitre.affinity.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.plaf.metal.MetalSliderUI;

import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClusterStep;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.view.event.ClusterDistanceChangeEvent;
import org.mitre.affinity.view.event.ClusterDistanceChangeListener;

/**
 * @author cbonaceto
 *
 */
public class ClusterDistanceSliderPane<K extends Comparable<K>, V> extends JPanel {
	
	protected static final long serialVersionUID = 1L;

	private static final int minSize = 0;
	
	private static final int maxSize = 1000;
	
	private final ClusterDistanceSlider slider;
	
	private List<ClusterDistanceChangeListener> listeners;	
	
	/**
	 * Initializes the cluster distance slider pane
	 * @param clusters 
	 */
	public ClusterDistanceSliderPane(ClustersContainer<K> clusters, int orientation) {
		super();
		this.listeners = Collections.synchronizedList(new LinkedList<ClusterDistanceChangeListener>());
				
		setLayout(new BorderLayout());
		
		Box controlBox = Box.createHorizontalBox();
		
		//Add the slider
		slider = new ClusterDistanceSlider(clusters, orientation);
		controlBox.add(slider, BorderLayout.WEST);
		
		//Add the fine-grain control arrow buttons
		controlBox.add(Box.createHorizontalStrut(20));
		JButton downButton = new JButton("<"); 
		downButton.setMargin(new Insets(5,5,5,5));
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) { slider.moveMaxThumbDown(1); }
		});
		controlBox.add(downButton);
		JButton upButton = new JButton(">");
		upButton.setMargin(new Insets(5,5,5,5));
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) { slider.moveMaxThumbUp(1); }
		});
		controlBox.add(Box.createHorizontalStrut(3));
		controlBox.add(upButton);
		this.add(controlBox);
		
		//Add listener to also use the left/right arrow keys for fine-grain control
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ev) {
				System.out.println("key pressed");
				if(ev.getKeyCode() == KeyEvent.VK_RIGHT) 
					slider.moveMaxThumbUp(1);
				else if(ev.getKeyCode() == KeyEvent.VK_LEFT) {
					slider.moveMaxThumbDown(1);
				}
			}			
		});	
	}
	
	public void setClusters(ClustersContainer<K> clusters) {
		slider.additionalTickLocations.clear();
		slider.setClusters(clusters);
	}
	
	public synchronized void addClusterDistanceChangeListener(ClusterDistanceChangeListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public synchronized boolean removeClusterDistanceChangeListener(ClusterDistanceChangeListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * Notify listeners if slider value changed
	 */
	protected synchronized void fireClusterDistanceChangeEvent() {
		if(listeners != null && !listeners.isEmpty()) {
			//ClusterDistanceChangeEvent event = new ClusterDistanceChangeEvent((minValue/1000D), (maxValue/1000D));
			//System.out.println("maxD = " + (maxD) + ", maxValue = " + (maxValue/1000D));
			ClusterDistanceChangeEvent event = new ClusterDistanceChangeEvent(this, 
					slider.sliderUI.minD, slider.sliderUI.maxD);
			for(ClusterDistanceChangeListener listener : listeners) {
				listener.clusterDistanceChanged(event);
			}
		}
	}
	
	public void setClusterDistances(double minDistance, double maxDistance) {
		//TODO: Check this
		slider.minValue = (int)(minDistance * 1000);
		slider.maxValue = (int)(maxDistance * 1000);
		slider.sliderUI.minD = minDistance;
		slider.sliderUI.maxD = maxDistance;		 
		slider.repaint();
	}
	
	public void addClusterDistanceTickLocation(double distance, boolean highlighted) {
		slider.addAdditionalTickLocation(distance, highlighted);
	}
	
	public void clearAdditionalClusterDistanceTickLocations() {
		slider.clearAdditionalTickLocations();
	}
	
	/**
	 * Class constructing the cluster distance slider
	 * @author CWOLF
	 */
	private class ClusterDistanceSlider extends JSlider {
		private static final long serialVersionUID = 1L;
		
		/** The clusters */
		private ClustersContainer<K> allClusters;	
		
		/** Locations of any additional cluster distance tick marks */
		private List<TickLocation> additionalTickLocations;
		
		// Holds the min and max value being controlled by the slider
		private int minValue = 0;
		private int maxValue = 0;
		
		protected ClusterDistanceSliderUI sliderUI;
		
		/**
		 * Initializes the cluster distance slider
		 */
		private ClusterDistanceSlider(ClustersContainer<K> clusters, int orientation) {
			// Initialize super class of confidence slider
			super(minSize, maxSize);
			
			allClusters = clusters;
			additionalTickLocations = new LinkedList<TickLocation>();
			
			// Initialize labels for confidence slider
			Hashtable<Integer,JLabel> labels = new Hashtable<Integer,JLabel>();
			for(int i=minSize; i<=maxSize; i+= (maxSize-minSize)/5)
				labels.put(new Integer(i), new JLabel(" "+(i>0?"+":i<0?"\u2013":"")+String.valueOf(i/1000.0)));
			
			// Set attributes of slider
			setOrientation(orientation);			
			setMajorTickSpacing((maxSize-minSize) / 5);
			setMinorTickSpacing((maxSize-minSize) / 20);			
			setPaintTicks(true);
			setPaintLabels(true);
			setLabelTable(labels);
			
			this.sliderUI = new ClusterDistanceSliderUI(this); 
			setUI(sliderUI);
			
			// Initialize the min and max values of slider
			minValue = 0;
			maxValue = 1000;		
		}
		
		public void setClusters(ClustersContainer<K> clusters) {
			allClusters = clusters;
			sliderUI.clusterTickLocations = null;
			repaint();
		}
		
		public void addAdditionalTickLocation(double distance, boolean highlighted) {
			additionalTickLocations.add(new TickLocation(distance, highlighted));
			sliderUI.clusterTickLocations = null;
			repaint();
		}
		
		public void clearAdditionalTickLocations() {
			if(!additionalTickLocations.isEmpty()) {
				additionalTickLocations.clear();
				sliderUI.clusterTickLocations = null;
				repaint();
			}
		}
		
		public void moveMaxThumbUp(int numClicks) {
			sliderUI.moveMaxThumbUp(numClicks);
		}
		
		public void moveMaxThumbDown(int numClicks) {
			sliderUI.moveMaxThumbDown(numClicks);
		}
		
		/**
		 * Class to handle display and action of the confidence slider
		 * @author CWOLF
		 */
		private class ClusterDistanceSliderUI extends MetalSliderUI implements MouseListener, MouseMotionListener {	
			private int orientation;
			private int prevMin = 0, prevMax = 0;
			private double minD; 
			private double maxD;
			
			protected ArrayList<TickLocation> clusterTickLocations;
			//protected ArrayList<Integer> clusterTickLocations;
			//protected ArrayList<Double> clusterTickLocationsD;
			int clusterTickIndex = 0;			
			
			//--------------------------------------------------------------
			// Purpose: Helps get and set positions on the confidence slider
			//--------------------------------------------------------------
			private int getMinPos() {				
				if(orientation == JSlider.HORIZONTAL) {					
					//return (int)trackRect.getMaxX() - (minValue-slider.getMinimum())*trackRect.width/(slider.getMaximum()-slider.getMinimum());
					return (int)trackRect.getMinX() + (minValue)*trackRect.width/(slider.getMaximum()-slider.getMinimum());
				}
				else
					return (int)trackRect.getMaxY() - (minValue-slider.getMinimum())*trackRect.height/(slider.getMaximum()-slider.getMinimum()); 
			}	
			private int getMaxPos() { 
				if(orientation == JSlider.HORIZONTAL) {
					//return (int)trackRect.getMaxX() - (maxValue-slider.getMinimum())*trackRect.width/(slider.getMaximum()-slider.getMinimum());
					return (int)trackRect.getMinX() + (maxValue)*trackRect.width/(slider.getMaximum()-slider.getMinimum());
				}
				else
					return (int)trackRect.getMaxY() - (maxValue-slider.getMinimum())*trackRect.height/(slider.getMaximum()-slider.getMinimum()); 
			}	
			private void setMinPos(int pos) {
				if(orientation == JSlider.HORIZONTAL)
					minValue = valueForXPosition(pos);
				else
					minValue = valueForYPosition(pos); 
				//System.out.println("set minValue to: " + minValue + ", from pos: " + pos + ", new minPos: " + getMinPos());
			}			
			private void setMaxPos(int pos) { 
				if(orientation == JSlider.HORIZONTAL)
					maxValue = valueForXPosition(pos);
				else
					maxValue = valueForYPosition(pos);
				//System.out.println("maxvalue=" + pos);
			}			
		
			/**
			 * Initializes the cluster distance slider UI
			 */
			ClusterDistanceSliderUI(JSlider slider)	{
				slider.addMouseListener(this);
				slider.addMouseMotionListener(this);
				this.orientation = slider.getOrientation();
			}
			
			@Override
			public void installUI(JComponent c) {
				UIManager.put("Slider.trackWidth",new Integer(7));
				UIManager.put("Slider.majorTickLength",new Integer(6));		
				UIManager.put("Slider.horizontalThumbIcon", MetalIconFactory.getHorizontalSliderThumbIcon());
				UIManager.put("Slider.verticalThumbIcon", MetalIconFactory.getVerticalSliderThumbIcon());
				super.installUI(c);
			}
	
			/**
			 * Paints the min and max thumbs for the confidence slider
			 */
			public void paintThumb(Graphics g) {
				// Store original clip shape
				Shape origShape = g.getClip();
				g.setColor(isFocusOwner() ? new Color(99,130,191) : Color.black);
				
				if(orientation == JSlider.VERTICAL) {
					// Draw min thumb
					thumbRect.y = getMinPos()-thumbRect.height/2;
					g.setClip(thumbRect.x,getMinPos(),thumbRect.width,thumbRect.height/2+1);
					super.paintThumb(g);
					g.drawLine(thumbRect.x,(int)thumbRect.getCenterY(),(int)thumbRect.getMaxX(),(int)thumbRect.getCenterY());

					// Draw max thumb
					thumbRect.y = getMaxPos()-thumbRect.height/2;
					g.setClip(thumbRect.x,getMaxPos()-thumbRect.height/2,thumbRect.width,thumbRect.height/2+1);
					super.paintThumb(g);
					g.drawLine(thumbRect.x,(int)thumbRect.getCenterY(),(int)thumbRect.getMaxX(),(int)thumbRect.getCenterY());
				}
				else {
					// Draw min thumb					
					//thumbRect.y = getMinPos()-thumbRect.height/2;
					thumbRect.x = getMinPos()-thumbRect.width/2;
					//System.out.println("drawing min thumb, minPos: " + getMinPos() + ", thumRect.x: " + thumbRect.x);
					g.setClip(getMinPos()-thumbRect.width/2,thumbRect.y,thumbRect.width/2+1,thumbRect.height);
					super.paintThumb(g);
					g.drawLine((int)thumbRect.getCenterX(),thumbRect.y,(int)thumbRect.getCenterX(),(int)thumbRect.getMaxY());

					// Draw max thumb
					//thumbRect.y = getMaxPos()-thumbRect.height/2;
					thumbRect.x = getMaxPos()-thumbRect.width/2;
					g.setClip(getMaxPos(),thumbRect.y,thumbRect.width/2+1,thumbRect.height);
					super.paintThumb(g);
					g.drawLine((int)thumbRect.getCenterX(), thumbRect.y,(int)thumbRect.getCenterX(),(int)thumbRect.getMaxY());
				}
				
				// Restore original clip shape
				g.setClip(origShape);
			}			
			
			//paints tick marks indicating cluster locations
			public void paintClusters(Graphics g) {
				if(allClusters == null) {return;}
				
				int min, max;
				if(orientation == JSlider.VERTICAL) {
					min = 0;
					max = (int)trackRect.getMaxY();
				}
				else {
					min = (int)trackRect.getMinX();
					max = (int)trackRect.getMaxX();
				}
				
				boolean computeClusterTickLocations = false;
				if(clusterTickLocations == null || min != prevMin || max != prevMax) {
					clusterTickLocations = new ArrayList<TickLocation>();
					//clusterTickLocations = new ArrayList<Integer>();
					//clusterTickLocationsD = new ArrayList<Double>();
					prevMin = min;
					prevMax = max;
					computeClusterTickLocations = true;
				}
				
				if(orientation == JSlider.VERTICAL) {
					thumbRect.y=0;				
					//g2d.setColor(Color.black);
					g.setColor(new Color(0, 0, 0, 255));
					
					if(computeClusterTickLocations) {
						// Paint the clustering indicator tick mark locations on the slider						
						int numYpixels = min-max;
						int step = 0;
						for(ClusterStep<K> cs : allClusters) {
							for(ClusterGroup<K> cg : cs.getClusterGroups()) {
								if(step!=0) {
									//cg.getDistance() will be between 0.0 and 1.0									
									int clustTickY = ((int)(numYpixels*cg.getDistance())) + max;
									clusterTickLocations.add(new TickLocation(clustTickY, cg.getDistance(), false));
									//clusterTickLocations.add(clustTickY);
									//clusterTickLocationsD.add(cg.getDistance());
									g.fillRect(8, clustTickY, 6, 1);
								}
							}
							step++; 
						}
						if(additionalTickLocations != null && !additionalTickLocations.isEmpty()) {
							for(TickLocation tickLocation : additionalTickLocations) {
								tickLocation.location = ((int)(numYpixels*tickLocation.distance)) + max;
								clusterTickLocations.add(tickLocation);
								if(tickLocation.highlighted) {
									g.fillRect(8, tickLocation.location, 8, 2);
								} else {
									g.fillRect(8, tickLocation.location, 6, 1);
								}
							}
						}
						Collections.sort(clusterTickLocations);
					} else {
						// Paint the clustering indicator tick mark locations on the slider
						for(TickLocation tickLocation: clusterTickLocations) {
							if(tickLocation.highlighted) {
								g.fillRect(8, tickLocation.location, 8, 2);
							} else {
								g.fillRect(8, tickLocation.location, 6, 1);
							}
						}
					}
				} else {
					thumbRect.x=0;				
					//g2d.setColor(Color.black);
					g.setColor(new Color(0, 0, 0, 255));
					int yCoord =  trackRect.y + trackRect.height/2 + 2;
					int tickWidth = 1;
					int tickHeight = 8;
					
					if(computeClusterTickLocations) {
						// Paint the clustering indicator tick mark locations on the slider
						int numXpixels = max-min;
						int step = 0;
						for(ClusterStep<K> cs : allClusters){
							for(ClusterGroup<K> cg : cs.getClusterGroups()){
								if(step!=0){
									//cg.getDistance() will be between 0.0 and 1.0									
									int clustTickX = ((int)(numXpixels*cg.getDistance())) + min;
									clusterTickLocations.add(new TickLocation(clustTickX, cg.getDistance(), false));
									//clusterTickLocations.add(clustTickX);		
									//clusterTickLocationsD.add(cg.getDistance());
									g.fillRect(clustTickX, yCoord, tickWidth, tickHeight);
								}
							}
							step++; 
						}
						if(additionalTickLocations != null && !additionalTickLocations.isEmpty()) {
							for(TickLocation tickLocation : additionalTickLocations) {
								tickLocation.location = ((int)(numXpixels*tickLocation.distance)) + min;
								clusterTickLocations.add(tickLocation);
								if(tickLocation.highlighted) {									
									g.fillRect(tickLocation.location, yCoord, tickWidth+2, tickHeight+1);
								} else {
									g.fillRect(tickLocation.location, yCoord, tickWidth, tickHeight);
								}
							}
						}
						Collections.sort(clusterTickLocations);
					} else {
						// Paint the clustering indicator tick mark locations on the slider
						for(TickLocation tickLocation : clusterTickLocations) {
							if(tickLocation.highlighted) {									
								g.fillRect(tickLocation.location, yCoord, tickWidth+2, tickHeight+1);
							} else {
								g.fillRect(tickLocation.location, yCoord, tickWidth, tickHeight);
							}
						}
					}
				}
			}

			/**
			 * Paints the track for the confidence slider
			 * Note: Now paints the track + tick marks on top of the track
			 */
			public void paintTrack(Graphics g) {
				// Save original graphic clip shape and then temporarily clip to defined range
				Shape origShape = g.getClip();
				
				if(orientation == JSlider.VERTICAL) {
					// First, draw entire track as empty
					thumbRect.y=0;
					super.paintTrack(g);				

					g.setClip(getX(),getMaxPos(),getWidth(),getMinPos()-getMaxPos());

					// Calculate out the various color range endpoints
					int min=yPositionForValue(minSize);
					int max=yPositionForValue(maxSize);
					//int pt1=(min-max)/2+max;

					// Paint the color ranges
					Graphics2D g2d = (Graphics2D)g;		
					//g2d.fillRect(10,max,4,pt1-max+1);
					//g2d.fillRect(10,pt1,4,min-pt1);
					g2d.fillRect(10, max, 4, min);
				} else {				
					// First, draw entire track as empty
					thumbRect.x = thumbRect.width/2;					
					super.paintTrack(g);
					
					g.setClip(getMinPos(),trackRect.y + trackRect.height/2 - 2,getMaxPos()-getMinPos(),trackRect.height/4);
					//g.setColor(Color.black);					
					g.fillRect(getMinPos(),trackRect.y + trackRect.height/2 - 2,getMaxPos()-getMinPos(),trackRect.height/4);

					// Calculate out the various color range endpoints
					//int min=xPositionForValue(minSize);
					//int max=xPositionForValue(maxSize);
					//int pt1=(min-max)/2+max;

					// Paint the color ranges
					//Graphics2D g2d = (Graphics2D)g;		
					//g2d.fillRect(10,max,4,pt1-max+1);
					//g2d.fillRect(10,pt1,4,min-pt1);

					//g2d.fillRect(10, max, 4, min);					
					//g.fillRect(max, 10, min, 4);					
				}
				
				// Reset graphic clip shape with original
				g.setClip(origShape);
			}
						
			/**
			 * Handles painting of the confidence slider
			 * @param g Graphics object used for drawing the slider
			 */
			public void paint(Graphics g, JComponent component) {
				paintTrack(g);
				paintThumb(g);
				paintClusters(g);
				paintTicks(g);
				paintLabels(g);
			}
			
			// Variables to track which slider knob is currently active
			private boolean minThumbSelected = false;
			private boolean maxThumbSelected = false;

			/**
			 * Determines what slider thumb to make active
			 */
			public void mousePressed(MouseEvent e) {
				if(orientation == JSlider.VERTICAL) {
					if(e.getX()>=thumbRect.x && e.getX()<=thumbRect.x+thumbRect.width) {
						if(e.getY()>=getMinPos() && e.getY()<=getMinPos()+thumbRect.getHeight()/2) minThumbSelected = true;
						if(e.getY()<=getMaxPos() && e.getY()>=getMaxPos()-thumbRect.getHeight()/2) maxThumbSelected = true;
					}
				} else {
					if(e.getY()>=thumbRect.y && e.getY()<=thumbRect.y+thumbRect.height) {
						if(e.getX()>=(getMinPos()-thumbRect.width/2) && e.getX()<=(getMinPos())) minThumbSelected = true;
						//if(e.getX()>=(getMinPos()-thumbRect.width/2) && e.getX()<=(getMinPos())) minThumbSelected = true;
						//if(e.getX()<=(getMaxPos()+thumbRect.width/2) && e.getX()>=getMaxPos()-thumbRect.getWidth()/2) maxThumbSelected = true;
						if(e.getX()<=(getMaxPos()+thumbRect.width/2) && e.getX()>=getMaxPos()) maxThumbSelected = true;
					}
				}				
				//DEBUG CODE
				/*
				if(minThumbSelected)
					System.out.println("min thumb selected, minPos= " + getMinPos());
				else if(maxThumbSelected)
					System.out.println("max thumb selected, maxPos = " + getMaxPos());
					*/
			}

			/**
			 * Make both slider thumbs inactive
			 */
			public void mouseReleased(MouseEvent e)
				{ minThumbSelected = false; maxThumbSelected = false; }
			
			/**
			 * Moves the active slider thumb based on mouse movement
			 */
			public void mouseDragged(MouseEvent e) {
				// First, store old values to make sure update is only done if needed
				int origMinValue = minValue;
				int origMaxValue = maxValue;
				
				// Move active thumb based on mouse movement
				if(minThumbSelected || maxThumbSelected) {
					if(orientation == JSlider.VERTICAL) {
						if(minThumbSelected) { setMinPos(e.getY()-3); if(getMinPos()<getMaxPos()) setMaxPos(getMinPos()); }
						//if(maxThumbSelected) { setMaxPos(e.getY()+3); if(getMaxPos()>getMinPos()) setMinPos(getMaxPos()); }
						if(maxThumbSelected) { setMaxPos(e.getY()+3); if(getMaxPos()>getMinPos()) setMinPos(getMaxPos()); }
						if(getMinPos()>trackRect.getMaxY()) setMinPos((int)trackRect.getMaxY());
						if(getMaxPos()>getMinPos()) setMaxPos(getMinPos());
						if(getMaxPos()<trackRect.y) setMaxPos(trackRect.y);
						if(getMinPos()<getMaxPos()) setMinPos(getMaxPos());
					}
					else {
						if(minThumbSelected) { setMinPos(e.getX()-3); if(getMinPos()>getMaxPos()) setMaxPos(getMinPos()); }						
						if(maxThumbSelected) { setMaxPos(e.getX()+3); if(getMaxPos()<getMinPos()) setMinPos(getMaxPos()); }
						if(getMinPos()>trackRect.getMaxX()) { setMinPos((int)trackRect.getMaxX()); }
						if(getMaxPos()<getMinPos()) setMaxPos(getMinPos());
						if(getMaxPos()<trackRect.x) { setMaxPos(trackRect.x); }
						if(getMinPos()>getMaxPos()) { setMinPos(getMaxPos()); }
					}					
					repaint();
				}
				
				// Notify listeners if slider value changed				
				if(minValue!=origMinValue || maxValue!=origMaxValue) {
					this.maxD = maxValue/1000D;
					this.minD = minValue/1000D;
					fireClusterDistanceChangeEvent();
				}
			}
	
			//if the mouse is clicked then move the slider thumb
			//	down to the next cluster if it is max
			//  up to the next clustger if it is min
			public void mouseClicked(MouseEvent e) {}
			
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
			
			public void moveMaxThumbUp(int numClicks) {
				//Go up to the next cluster tick mark
				int origPos = maxValue;
				int currPos = getMaxPos();
				
				if(Math.abs(clusterTickLocations.get(clusterTickIndex).location-currPos) < 2) {
					if(clusterTickIndex < clusterTickLocations.size() - 1) {						
						setMaxPos(clusterTickLocations.get(++clusterTickIndex).location);						
					}
				} else {
					for(clusterTickIndex = 0; clusterTickIndex < clusterTickLocations.size(); clusterTickIndex++) {					
						int clusterTickLocation = clusterTickLocations.get(clusterTickIndex).location;
						if(currPos <= clusterTickLocation) {
							if(Math.abs(clusterTickLocation-currPos) < 2 && clusterTickIndex < clusterTickLocations.size()-1) {
								setMaxPos(clusterTickLocations.get(++clusterTickIndex).location);
							} else { 
								setMaxPos(clusterTickLocations.get(clusterTickIndex).location);
							}
							break;
						}
					}
					
					if(clusterTickIndex >= clusterTickLocations.size()) {
						clusterTickIndex = clusterTickLocations.size() - 1;
					}
				}				
				
				if(orientation == JSlider.VERTICAL) {						 
					if(getMaxPos()>getMinPos())  setMinPos(getMaxPos()); 						
					//if(getMaxPos()>getMinPos()) setMaxPos(getMinPos());
					//if(getMaxPos()<trackRect.y) setMaxPos(trackRect.y);
					//if(getMinPos()<getMaxPos()) setMinPos(getMaxPos());
				}
				else {													
					if(getMaxPos()<getMinPos()) setMinPos(getMaxPos()); 
					//if(getMinPos()>trackRect.getMaxX()) { setMinPos((int)trackRect.getMaxX()); }
					//if(getMaxPos()<getMinPos()) setMaxPos(getMinPos());
					//if(getMaxPos()<trackRect.x) { setMaxPos(trackRect.x); }
					//if(getMinPos()>getMaxPos()) { setMinPos(getMaxPos()); }
				}					
				repaint();
				
				//System.out.println("in move thumb up, set tick index to: " + clusterTickIndex);
				
				if(origPos != maxValue) {
					//Notify listeners that a slider value changed
					this.maxD = clusterTickLocations.get(clusterTickIndex).distance;
					this.minD = minValue/1000D;
					fireClusterDistanceChangeEvent();
				}
			}
			
			public void moveMaxThumbDown(int numClicks) {				
				//Go up to the previous cluster tick mark
				int origPos = maxValue;
				int currPos = getMaxPos();				
				
				if(Math.abs(clusterTickLocations.get(clusterTickIndex).location-currPos) < 2) {
					if(clusterTickIndex > 0) {
						clusterTickIndex--;
						setMaxPos(clusterTickLocations.get(clusterTickIndex).location);						
					}
				}
				else {
					for(clusterTickIndex = clusterTickLocations.size() - 1; clusterTickIndex >= 0; clusterTickIndex--) {					
						int clusterTickLocation = clusterTickLocations.get(clusterTickIndex).location;
						if(currPos >= clusterTickLocation) {
							if(Math.abs(clusterTickLocation-currPos) < 2 && clusterTickIndex > 0) {
								setMaxPos(clusterTickLocations.get(--clusterTickIndex).location);							
							}
							else 
								setMaxPos(clusterTickLocations.get(clusterTickIndex).location);							
							break;
						}
					}
					
					if(clusterTickIndex < 0)
						clusterTickIndex = 0;
				}
				
				if(orientation == JSlider.VERTICAL) {						 
					if(getMaxPos()>getMinPos())  setMinPos(getMaxPos()); 						
					//if(getMaxPos()>getMinPos()) setMaxPos(getMinPos());
					//if(getMaxPos()<trackRect.y) setMaxPos(trackRect.y);
					//if(getMinPos()<getMaxPos()) setMinPos(getMaxPos());
				}
				else {													
					if(getMaxPos()<getMinPos()) {setMinPos(getMaxPos());} 
					//if(getMinPos()>trackRect.getMaxX()) { setMinPos((int)trackRect.getMaxX()); }
					//if(getMaxPos()<getMinPos()) setMaxPos(getMinPos());
					//if(getMaxPos()<trackRect.x) { setMaxPos(trackRect.x); }
					//if(getMinPos()>getMaxPos()) { setMinPos(getMaxPos()); }
				}					
				repaint();
				
				//System.out.println("in move thumb down, set tick index to: " + clusterTickIndex);
				
				if(origPos != maxValue) {
					//Notify listeners that a slider value changed
					this.maxD = clusterTickLocations.get(clusterTickIndex).distance;
					this.minD = minValue/1000D;				
					fireClusterDistanceChangeEvent();
				}
			}			
		}
	}
	
	class TickLocation implements Comparable<TickLocation> {
		Integer location;
		
		Double distance;
		
		boolean highlighted;
		
		public TickLocation() {}
		
		public TickLocation(Double distance, boolean highlighted) {
			this(null, distance, highlighted);
		}
		
		public TickLocation(Integer location, Double distance, boolean highlighted) {
			this.location = location;
			this.distance = distance;
			this.highlighted = highlighted;
		}
		
		@Override
		public int compareTo(TickLocation tickLocation) {
			return location.compareTo(tickLocation.location);
		}				
	}
}