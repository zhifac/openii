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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a point in n-dimensional space.
 * 
 * @author CBONACETO
 *
 */
public class Position implements Iterable<Double> {
	/**
	 * The point in n-dimensional space
	 */
	private final List<Double> pos;
	private final int numDimensions;
	
	public Position(int numDimensions) {
		//this.pos = new Double[numDimensions];
		this.pos = new ArrayList<Double>(numDimensions);
		for(int i=0 ; i<numDimensions; i++) {
			this.pos.add(null);
		}
		this.numDimensions = numDimensions;
	}
	
	public Position(Double[] pos) {
		this.pos = new ArrayList<Double>(pos.length);
		for(Double d : pos) {
			this.pos.add(d);
		}
		this.numDimensions = pos.length;
		//this.pos = pos;
	}
	
	public Position(float[] pos) {
		this.pos = new ArrayList<Double>(pos.length);
		for(Float f : pos) {
			this.pos.add(f.doubleValue());
		}
		this.numDimensions = pos.length;
		//this.pos = pos;
	}
	
	public Position(Point2D point) {
		this.pos = new ArrayList<Double>(2);
		this.pos.add(point.getX());
		this.pos.add(point.getY());
		this.numDimensions = 2;
		/*
		this.pos = new Double[2];
		this.pos[0] = point.getX();
		this.pos[1] = point.getY();
		*/
	}
	
	public Position(double x, double y) {
		this.pos = new ArrayList<Double>(2);
		this.pos.add(x);
		this.pos.add(y);
		this.numDimensions = 2;
		/*
		this.pos = new Double[2];
		this.pos[0] = point.getX();
		this.pos[1] = point.getY();
		*/
	}
	
	public Position(Position position) {
		this.numDimensions = position.numDimensions;
		this.pos = new ArrayList<Double>(position.numDimensions);
		for(Double d : position.pos) {
			this.pos.add(d);
		}
	}

	public int getNumDimensions() {
		return this.numDimensions;
	}
	
	public void setDimensionValue(int dimension, Double value) {
		if(dimension < 0 || dimension >= this.numDimensions) {
			throw new java.lang.IllegalArgumentException("Error setting value: dimension out of bounds: " + dimension);
		}
		this.pos.set(dimension, value);
	}
	
	public double getDimensionValue(int dimension) {
		if(dimension < 0 || dimension >= this.numDimensions) {
			throw new java.lang.IllegalArgumentException("Error getting value: dimension out of bounds: " + dimension);
		}
		return this.pos.get(dimension);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder("(");
		boolean first = true;
		for(Double d : this.pos) {
			if(first) {			
				str.append(d);
				first = false;
			}
			else {
				str.append(", " + d);
			}			
		}
		str.append(")");
		return str.toString();
	}

	public Iterator<Double> iterator() {
		return this.pos.iterator();		
	}
}
