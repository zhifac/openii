package org.openii.schemr.matcher;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Schema;


public class SimilarityMatrix {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimilarityMatrix t = new SimilarityMatrix(new Object [] {"Apple", "Bat", "Cat", "Dog", "Eleven"}, new Object [] {"1", "2", "3", "4"});
		t.setScore(0, 0, 1.1);
		t.setScore("Bat", "2", 2.2);
		System.out.println(t);
	}
	
	double [][] score;
	final Object [] columnIDs;
	final Object [] rowIDs;

	HashMap<Object, Integer> colMap;
	HashMap<Object, Integer> rowMap;

	public SimilarityMatrix(final Object [] cols, final Object [] rows) {
		this.score = new double [cols.length][rows.length];
		this.columnIDs = cols;
		this.rowIDs = rows;
		
		colMap = new HashMap<Object, Integer>();
		int i = 0;
		for (Object col : columnIDs) {
			colMap.put(col, i);
			i++;
		}
		rowMap = new HashMap<Object, Integer>();
		i = 0;
		for (Object row : rowIDs) {
			rowMap.put(row, i);
			i++;
		}
	}

	public double getScore(Object col, Object row) {
		if (!colMap.containsKey(col) || !rowMap.containsKey(row)) {
			throw new IllegalArgumentException("Invalid column or row object: " + col + ", " + row);
		}
		return score[this.colMap.get(col)][this.rowMap.get(row)];
	}

	public double getScore(int x, int y) {
		if (x > columnIDs.length || y > rowIDs.length) {
			throw new IllegalArgumentException("Invalid column or row index: " + x + ", " + y);			
		}
		return score[x][y];
	}

	public void setScore(Object col, Object row, double score) {
		if (!colMap.containsKey(col) || !rowMap.containsKey(row)) {
			throw new IllegalArgumentException("Invalid column or row object: " + col + ", " + row);
		}
		this.score[colMap.get(col)][rowMap.get(row)] = score;
	}
	
	public void setScore(int x, int y, double score) {
		if (x > columnIDs.length || y > rowIDs.length) {
			throw new IllegalArgumentException("Invalid column or row index: " + x + ", " + y);			
		}
		this.score[x][y] = score;
	}
	
	private final String GAP = "       ";
	private final int LEN = GAP.length();
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n  "+GAP+GAP+" ");
		for (Object o : columnIDs) {
			sb.append("| " + fixLength(o.toString().trim(), LEN) + " ");
		}
		sb.append("\n");
		for (Object o : rowIDs) {
			sb.append("| " + fixLength(o.toString().trim(), LEN*2) + " ");		
			int rowIndex = rowMap.get(o);
			for (int i = 0; i < columnIDs.length; i++) {
				sb.append("| " + fixLength(""+score[i][rowIndex], LEN) + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private static String fixLength(String s, int len) {
		String result;
		if (s.length() > len) {
			result =  s.substring(0, len);
		} else {
			result = s;
			while (result.length() < len) {
				result = result + " ";
			}
		}
		return result;	
	}
}
