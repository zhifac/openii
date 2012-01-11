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
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.plaf.metal.MetalSliderUI;

import org.eclipse.swt.widgets.Display;
import org.mitre.affinity.view.craigrogram.Cluster2DView;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public class ClusterStepSizeSliderPane<K extends Comparable<K>, V> extends JPanel {
	protected static final long serialVersionUID = 1;

	//private int scaleRange = 100;
	
	private Cluster2DView<K, V> schema2DPlot;
	
	private int minStep = 1;
	private int maxStep = 3;

	// Manages confidence range constants within Harmony
	/*
		public static final int CONFIDENCE_SCALE = 100;
		private static final int MIN_CONFIDENCE = new Double(MappingCellManager.MIN_CONFIDENCE*CONFIDENCE_SCALE).intValue();
		private static final int MAX_CONFIDENCE = new Double(MappingCellManager.MAX_CONFIDENCE*CONFIDENCE_SCALE).intValue();
	 */

	private class ClusterStepSizeSlider extends JSlider {
		protected static final long serialVersionUID = 1;

		/**
		 * Class to handle display and action of the confidence slider
		 * @author CWOLF
		 */
		private class ClusterStepSizeSliderUI extends MetalSliderUI implements MouseListener, MouseMotionListener
		{		
			//--------------------------------------------------------------
			// Purpose: Helps get and set positions on the confidence slider
			//--------------------------------------------------------------
			private int getMinPos() { return (int)trackRect.getMaxY() - (minValue-slider.getMinimum())*trackRect.height/(slider.getMaximum()-slider.getMinimum()); }	
			private int getMaxPos() { return (int)trackRect.getMaxY() - (maxValue-slider.getMinimum())*trackRect.height/(slider.getMaximum()-slider.getMinimum()); }	
			private void setMinPos(int pos) { minValue = valueForYPosition(pos); }
			private void setMaxPos(int pos) { maxValue = valueForYPosition(pos); }

			/**
			 * Initializes confidence level slider
			 */
			ClusterStepSizeSliderUI(JSlider slider)
			{
				slider.addMouseListener(this);
				slider.addMouseMotionListener(this);
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

				// Restore original clip shape
				g.setClip(origShape);
			}

			/**
			 * Paints the track for the confidence slider
			 */
			public void paintTrack(Graphics g)
			{
				// First, draw entire track as empty
				thumbRect.y=0;
				super.paintTrack(g);

				// Save original graphic clip shape and then temporarily clip to defined range
				Shape origShape = g.getClip();
				g.setClip(getX(),getMaxPos(),getWidth(),getMinPos()-getMaxPos());

				// Calculate out the various color range endpoints
				int min=yPositionForValue(minValue);
				int max=yPositionForValue(maxValue);
				int pt1=(min-max)/2+max;

				// Paint the color ranges
				Graphics2D g2d = (Graphics2D)g;
				//g2d.setPaint(new GradientPaint(10,max,Color.green,10,pt1,Color.yellow));
				g2d.fillRect(10,max,4,pt1-max+1);
				//g2d.setPaint(new GradientPaint(10,pt1,Color.yellow,10,min,Color.red));
				g2d.fillRect(10,pt1,4,min-pt1);

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
				if(e.getX()>=thumbRect.x && e.getX()<=thumbRect.x+thumbRect.width)
				{
					if(e.getY()>=getMinPos() && e.getY()<=getMinPos()+thumbRect.getHeight()/2) minThumbSelected = true;
					if(e.getY()<=getMaxPos() && e.getY()>=getMaxPos()-thumbRect.getHeight()/2) maxThumbSelected = true;
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
					if(minThumbSelected) { setMinPos(e.getY()-3); if(getMinPos()<getMaxPos()) setMaxPos(getMinPos()); }
					if(maxThumbSelected) { setMaxPos(e.getY()+3); if(getMaxPos()>getMinPos()) setMinPos(getMaxPos()); }
					if(getMinPos()>trackRect.getMaxY()) setMinPos((int)trackRect.getMaxY());
					if(getMaxPos()>getMinPos()) setMaxPos(getMinPos());
					if(getMaxPos()<trackRect.y) setMaxPos(trackRect.y);
					if(getMinPos()<getMaxPos()) setMinPos(getMaxPos());
					repaint();
				}

				// Adjust min/max cluster step size settings if changes occurred
				if(minValue!=origMinValue || maxValue!=origMaxValue) {
					schema2DPlot.setMinClusterStep(minValue);
					schema2DPlot.setMaxClusterStep(maxValue);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							schema2DPlot.redraw();
						}
					});					
					//System.err.println("new min: " + minValue + ", new max: " + maxValue);
				}

			}

			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
		}

		// Holds the min and max value being controlled by the slider
		private int minValue = 0;
		private int maxValue = 0;

		/**
		 * Initializes the confidence slider
		 */
		private ClusterStepSizeSlider()
		{
			// Initialize super class of confidence slider
			super(minStep, maxStep);

			// Initialize labels for confidence slider
			Hashtable<Integer,JLabel> labels = new Hashtable<Integer,JLabel>();
			//for(int i=minStep; i<=maxStep; i+= (maxStep-minStep)/5) {
			for(int i=minStep; i<=maxStep; i++) {
			//for(int i=maxStep; i>=minStep; i--) {
				//System.out.println(i);
				labels.put(new Integer(i), new JLabel(" "+(i>0?"+":i<0?"\u2013":"")+String.valueOf(Math.abs(i))));
			}

			// Set attributes of confidence slider
			setOrientation(JSlider.VERTICAL);
			//setMajorTickSpacing((maxStep-minStep) / 5);
			setMajorTickSpacing(1);
			//setMinorTickSpacing((maxStep-minStep) / 20);
			setMinorTickSpacing(1);
			setPaintTicks(true);
			setPaintLabels(true);
			setLabelTable(labels);
			setUI(new ClusterStepSizeSliderUI(this));

			// Initialize the min and max confidence values
			//TODO: Fix this
			minValue = minStep;
			maxValue = maxStep;
			//minValue = new Double(Filters.getMinConfThreshold()*CONFIDENCE_SCALE).intValue();
			//maxValue = new Double(Filters.getMaxConfThreshold()*CONFIDENCE_SCALE).intValue();
		}
	}

	/**
	 * Initializes the confidence pane
	 */
	public ClusterStepSizeSliderPane(Cluster2DView<K, V> schema2DPlot, int minStep, int maxStep) {
		this.schema2DPlot = schema2DPlot;
		this.minStep = minStep;
		this.maxStep = maxStep;
		// Place confidence slider in confidence pane
		setLayout(new BorderLayout());
		add(new ClusterStepSizeSlider(),BorderLayout.CENTER);
	}
}