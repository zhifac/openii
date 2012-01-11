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

package org.mitre.affinity.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author cbonaceto
 *
 * @param <K>
 */
public class PositionGrid<K extends Comparable<K>> implements Iterable<Entry<K, Position>> {
	
	/** Maps cluster object id to its position in n-dimensional space */
	private final Map<K, Position> positions;	
	
	/** The number of dimensions */
	private final int numDimensions;
	
	private double xScaleFactor;
	
	private double yScaleFactor;
	
	public PositionGrid(int numDimensions) {
		this.numDimensions = numDimensions;
		this.positions = new HashMap<K, Position>();
	}
	
	/**
	 * Translates all values by the given x and y offset amounts
	 * 
	 * @param xOffset
	 * @param yOffset
	 */
	public void translate(double xOffset, double yOffset) {
		//System.out.println("translating: " + (xOffset) + "," + (yOffset));
		for(Position p : this.positions.values()) {
			//p.setDimensionValue(0, (p.getDimensionValue(0) - this.xOffset + xOffset));
			//p.setDimensionValue(1, (p.getDimensionValue(1) - this.yOffset + yOffset));
			p.setDimensionValue(0, (p.getDimensionValue(0) + xOffset));
			p.setDimensionValue(1, (p.getDimensionValue(1) + yOffset));
		}
		//this.xOffset = xOffset;
		//this.yOffset = yOffset;
	}
	
	
	public void center(double x1, double y1, double x2, double y2) {
		//Determine the current x/y min and x/y max values
		double xMin = Double.POSITIVE_INFINITY;
		double xMax = Double.NEGATIVE_INFINITY;		
		double yMin = Double.POSITIVE_INFINITY;
		double yMax = Double.NEGATIVE_INFINITY;
		
		for(Position p : this.positions.values()) {
			double xVal = p.getDimensionValue(0);
			double yVal = p.getDimensionValue(1);
			
			if(xVal > xMax) 
				xMax = xVal;
			if(xVal < xMin)
				xMin = xVal;
			
			if(yVal > yMax) 
				yMax = yVal;
			if(yVal < yMin)
				yMin = yVal;
		}
		
		//Translate to center the position grid between x1/y1 and x2/y2
		translate(((x2-x1) - (xMax-xMin))/2, ((y2-y1) - (yMax-yMin))/2);
	}
	
	/**
	 * Rescale the values in the position grid such that they are in the range [newMin newMax]
	 * 
	 * @param newMin: the new minimum position value
	 * @param newMax: the new maximimum position value
	 */
	public double rescale(double newMin, double newMax) {
		//Determine the current min and max values
		double oldMax = Double.NEGATIVE_INFINITY;
		double oldMin = Double.POSITIVE_INFINITY;
		Iterator<Position> posIter = this.getPositionsIter();
		while(posIter.hasNext()) {
			for(Double d : posIter.next()) {
				if(d > oldMax) 
					oldMax = d;
				if(d < oldMin) 
					oldMin = d;
			}
		}
		
		//Rescale so that the minimum position value is newMin and the maximum position value is newMax		
		double addAmount = newMin - oldMin;
		double scaleFactor = newMax / (oldMax + newMin - oldMin);
		posIter = this.getPositionsIter();
		while(posIter.hasNext()) {
			Position p = posIter.next();
			for(int dim=0; dim<p.getNumDimensions(); dim++) {				
				p.setDimensionValue(dim, (p.getDimensionValue(dim) + addAmount) * scaleFactor);
			}
			/*
			for(Double d : posIter.next()) {
				d *= scaleFactor;				
			}
			*/
		}
		
		return scaleFactor;
	}
	
	/**
	 * Rescale the values in the position grid such that the x coordinates are in the range [xMin, xMax] and
	 * the y coordinates are in the range [yMin, yMax]
	 * 
	 * @param xMin
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 * @return
	 */
	public void rescale(double xMin, double xMax, double yMin, double yMax) {
		//Determine the current x/y min and x/y max values
		double oldXMin = Double.POSITIVE_INFINITY;
		double oldXMax = Double.NEGATIVE_INFINITY;
		
		double oldYMin = Double.POSITIVE_INFINITY;
		double oldYMax = Double.NEGATIVE_INFINITY;
		
		Iterator<Position> posIter = this.getPositionsIter();
		while(posIter.hasNext()) {
			Position p = posIter.next();
			double xVal = p.getDimensionValue(0);
			double yVal = p.getDimensionValue(1);
			
			if(xVal > oldXMax) 
				oldXMax = xVal;
			if(xVal < oldXMin)
				oldXMin = xVal;
			
			if(yVal > oldYMax) 
				oldYMax = yVal;
			if(yVal < oldYMin)
				oldYMin = yVal;
		}
		
		//Rescale so that the minimum position value is newMin and the maximum position value is newMax
		//for(int dim=0; dim<2; dim++) {
		double xAddAmount = xMin - oldXMin;
		this.xScaleFactor = xMax / (oldXMax + xMin - oldXMin);

		double yAddAmount = yMin - oldYMin;
		this.yScaleFactor = yMax / (oldYMax + yMin - oldYMin);

		posIter = this.getPositionsIter();
		while(posIter.hasNext()) {
			Position p = posIter.next();
			
			p.setDimensionValue(0, (p.getDimensionValue(0) + xAddAmount) * xScaleFactor);
			p.setDimensionValue(1, (p.getDimensionValue(1) + yAddAmount) * yScaleFactor);			
			
			/*
			for(int dim=0; dim<p.getNumDimensions(); dim++) {				
				p.setDimensionValue(dim, (p.getDimensionValue(dim) + addAmount) * scaleFactor);
			}
			*/			
		}
		//}
		//return scaleFactor;
	}	
	
	/** 
	 * @param objectID the id of the cluster object whose position to retrieve
	 * @return
	 */
	public Position getPosition(K objectID) {
		return this.positions.get(objectID);
	}
	
	
	/**
	 * @param objectID the id of the schema whose position to set
	 * @param pos the position to set
	 */
	public void setPosition(K objectID, Position pos) {
		if(pos.getNumDimensions() != this.numDimensions) {
			throw new java.lang.IllegalArgumentException("Error setting position: Must have " + this.numDimensions + "dimensions");
		}
		positions.put(objectID, pos);
	}
	
	public Iterator<Position> getPositionsIter() {
		return positions.values().iterator(); 
	}
	
	public Iterator<K> getObjectIDsIter() {
		return positions.keySet().iterator();
	}
	
	public int getNumDimensions() {
		return numDimensions;
	}

	public Iterator<Entry<K, Position>> iterator() {		
		return positions.entrySet().iterator();		
	}
}