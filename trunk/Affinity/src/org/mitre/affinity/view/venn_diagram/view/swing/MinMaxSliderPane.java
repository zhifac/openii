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

package org.mitre.affinity.view.venn_diagram.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.plaf.metal.MetalSliderUI;

public class MinMaxSliderPane extends JPanel {
	protected static final long serialVersionUID = 1L;

	private int minSize = 0;
	private int maxSize = 1000;

	private List<MinMaxSliderListener> listeners;
	
	private MinMaxSlider slider;

	public void addMinMaxSliderListener(MinMaxSliderListener listener) {
		this.listeners.add(listener);
	}

	public boolean removeMinMaxSliderListener(MinMaxSliderListener listener) {
		return this.listeners.remove(listener);
	}
	
	/**
	 * Sets the min value (range between 0 - 1) of the slider. Should be called from
	 * the Swing event dispatch thread as it forces the slider to be repainted.
	 */
	public void setMinValue(double minValue) {
		slider.minValue = (int)(minValue * 1000);
		if(slider.minValue > slider.maxValue)
			slider.maxValue = slider.minValue;
		slider.repaint();
	}
	
	/**
	 * Sets the max value (range between 0 - 1) of the slider. Should be called from
	 * the Swing event dispatch thread as it forces the slider to be repainted.
	 */
	public void setMaxValue(double maxValue) {
		slider.maxValue = (int)(maxValue * 1000);
		if(slider.maxValue < slider.minValue)
			slider.minValue = slider.maxValue;
		slider.repaint();
	}

	/**
	 * Class constructing the min-max slider
	 */
	private class MinMaxSlider extends JSlider
	{
		protected static final long serialVersionUID = 1L;

		/**
		 * Class to handle display and action of the min-max slider
		 */
		private class MinMaxSliderUI extends MetalSliderUI implements MouseListener, MouseMotionListener
		{	
			private int orientation;
			private double minD; 
			private double maxD;
			private boolean maxSliderEnabled = true;

			//--------------------------------------------------------------
			// Purpose: Helps get and set positions on the confidence slider
			//--------------------------------------------------------------
			private int getMinPos() {				
				if(orientation == JSlider.HORIZONTAL) {
					return (int)trackRect.getMinX() + (minValue)*trackRect.width/(slider.getMaximum()-slider.getMinimum());
				}
				else
					return (int)trackRect.getMaxY() - (minValue-slider.getMinimum())*trackRect.height/(slider.getMaximum()-slider.getMinimum()); 
			}	
			private int getMaxPos() { 
				if(orientation == JSlider.HORIZONTAL) {
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
			}
			private void setMaxPos(int pos) { 
				if(orientation == JSlider.HORIZONTAL)
					maxValue = valueForXPosition(pos);
				else
					maxValue = valueForYPosition(pos);
			}			

			/**
			 * Initializes confidence level slider
			 */
			MinMaxSliderUI(JSlider slider, boolean maxSliderEnabled)
			{
				slider.addMouseListener(this);
				slider.addMouseMotionListener(this);
				this.maxSliderEnabled = maxSliderEnabled;
				this.orientation = slider.getOrientation();
			}
			
			@Override
			public void installUI(JComponent c) {
				UIManager.put("Slider.trackWidth",new Integer(7));
				UIManager.put("Slider.majorTickLength",new Integer(6));		UIManager.put("Slider.horizontalThumbIcon",MetalIconFactory.getHorizontalSliderThumbIcon());
				UIManager.put("Slider.verticalThumbIcon",MetalIconFactory.getVerticalSliderThumbIcon());
				super.installUI(c);
			}

			/**
			 * Paints the min and max thumbs for the confidence slider
			 */
			public void paintThumb(Graphics g)
			{
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
					if(maxSliderEnabled) {
						thumbRect.y = getMaxPos()-thumbRect.height/2;
						g.setClip(thumbRect.x,getMaxPos()-thumbRect.height/2,thumbRect.width,thumbRect.height/2+1);
						super.paintThumb(g);
						g.drawLine(thumbRect.x,(int)thumbRect.getCenterY(),(int)thumbRect.getMaxX(),(int)thumbRect.getCenterY());
					}
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
					if(maxSliderEnabled) {
						//thumbRect.y = getMaxPos()-thumbRect.height/2;
						thumbRect.x = getMaxPos()-thumbRect.width/2;
						g.setClip(getMaxPos(),thumbRect.y,thumbRect.width/2+1,thumbRect.height);
						super.paintThumb(g);
						g.drawLine((int)thumbRect.getCenterX(), thumbRect.y,(int)thumbRect.getCenterX(),(int)thumbRect.getMaxY());
					}
				}

				// Restore original clip shape
				g.setClip(origShape);
			}		

			/**
			 * Paints the track for the confidence slider
			 * Note: Now paints the track + tick marks on top of the track
			 */
			public void paintTrack(Graphics g)
			{
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
				}
				else {				
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
			public void paint(Graphics g, JComponent component)
			{
				paintTrack(g);
				paintThumb(g);
				paintTicks(g);
				paintLabels(g);
			}

			// Variables to track which slider knob is currently active
			private boolean minThumbSelected = false;
			private boolean maxThumbSelected = false;

			/**
			 * Determines what slider thumb to make active
			 */
			public void mousePressed(MouseEvent e)
			{
				if(orientation == JSlider.VERTICAL) {
					if(e.getX()>=thumbRect.x && e.getX()<=thumbRect.x+thumbRect.width)
					{
						if(e.getY()>=getMinPos() && e.getY()<=getMinPos()+thumbRect.getHeight()/2) 
							minThumbSelected = true;
						if(maxSliderEnabled && e.getY()<=getMaxPos() && e.getY()>=getMaxPos()-thumbRect.getHeight()/2) 
							maxThumbSelected = true;
					}
				}
				else {
					if(e.getY()>=thumbRect.y && e.getY()<=thumbRect.y+thumbRect.height)
					{
						if(e.getX()>=(getMinPos()-thumbRect.width/2) && e.getX()<=(getMinPos())) 
							minThumbSelected = true;
						//if(e.getX()>=(getMinPos()-thumbRect.width/2) && e.getX()<=(getMinPos())) minThumbSelected = true;
						//if(e.getX()<=(getMaxPos()+thumbRect.width/2) && e.getX()>=getMaxPos()-thumbRect.getWidth()/2) maxThumbSelected = true;
						if(maxSliderEnabled && e.getX()<=(getMaxPos()+thumbRect.width/2) && e.getX()>=getMaxPos())
							maxThumbSelected = true;
					}
				}
			}

			/**
			 * Make both slider thumbs inactive
			 */
			public void mouseReleased(MouseEvent e)
			{ minThumbSelected = false; maxThumbSelected = false; }

			/**
			 * Moves the active slider thumb based on mouse movement
			 */
			public void mouseDragged(MouseEvent e)
			{
				// First, store old values to make sure update is only done if needed
				int origMinValue = minValue;
				int origMaxValue = maxValue;

				// Move active thumb based on mouse movement
				if(minThumbSelected || maxThumbSelected)
				{
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
					this.fireMinMaxSliderEvent();
				}
			}

			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
			
			/** Notify listeners if slider value changed */
			protected void fireMinMaxSliderEvent() {
				if(listeners != null && !listeners.isEmpty()) {					
					for(MinMaxSliderListener listener : listeners) {
						listener.minMaxSliderMoved(minD, maxD);
					}
				}
			}		
		}

		// Holds the min and max value being controlled by the slider
		protected int minValue = 0;
		protected int maxValue = 0;

		protected MinMaxSliderUI sliderUI;

		/**
		 * Initializes the min-max slider
		 */
		private MinMaxSlider(int orientation, int currMin, int currMax, boolean maxSliderEnabled)
		{
			// Initialize super class of confidence slider
			super(minSize, maxSize);

			// Initialize labels for confidence slider
			Hashtable<Integer,JLabel> labels = new Hashtable<Integer,JLabel>();
			for(int i=minSize; i<=maxSize; i+= (maxSize-minSize)/5)
				labels.put(new Integer(i), new JLabel(" "+(i>0?"+":i<0?"\u2013":"")+String.valueOf(i/1000.0)));

			// Set attributes of confidence slider
			setOrientation(orientation);

			setMajorTickSpacing((maxSize-minSize) / 5);
			setMinorTickSpacing((maxSize-minSize) / 20);

			setPaintTicks(true);
			setPaintLabels(true);
			setLabelTable(labels);
			this.sliderUI = new MinMaxSliderUI(this, maxSliderEnabled); 
			setUI(sliderUI);

			// Initialize the min and max slider values
			minValue = currMin;
			maxValue = currMax;		
		}
	}

	/**
	 * Initializes the min-max slider pane
	 * @param clusters 
	 */
	public MinMaxSliderPane(int orientation, boolean maxSliderEnabled)
	{
		this(orientation, 0.d, 1.d, maxSliderEnabled);
	}
	
	public MinMaxSliderPane(int orientation, double currMin, double currMax, boolean maxSliderEnabled)
	{
		super();
		this.listeners = new LinkedList<MinMaxSliderListener>();
		
		//this.minSize = minSize;
		//this.maxSize = maxSize;
		
		// Place min-max slider in min-max slider pane
		setLayout(new BorderLayout());
		this.slider = new MinMaxSlider(orientation, (int)(currMin*1000), (int)(currMax*1000), maxSliderEnabled); 
		add(slider, BorderLayout.CENTER);	
	}
}
