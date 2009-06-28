package org.openii.schemrserver.matcher;

import java.util.HashMap;
import java.util.logging.Logger;


public class SimilarityMatrix {

	private static final Logger logger = Logger.getLogger(SimilarityMatrix.class.getName());

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
	final Object [] colObjs;
	final Object [] rowObjs;

	HashMap<Object, Integer> colMap;
	HashMap<Object, Integer> rowMap;

	public SimilarityMatrix(final Object [] cols, final Object [] rows) {
		this.score = new double [cols.length][rows.length];
		this.colObjs = cols;
		this.rowObjs = rows;
		
		colMap = new HashMap<Object, Integer>();
		int i = 0;
		for (Object col : colObjs) {
			colMap.put(col, i);
			i++;
		}
		rowMap = new HashMap<Object, Integer>();
		i = 0;
		for (Object row : rowObjs) {
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
		if (x > colObjs.length || y > rowObjs.length) {
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
		if (x > colObjs.length || y > rowObjs.length) {
			throw new IllegalArgumentException("Invalid column or row index: " + x + ", " + y);			
		}
		this.score[x][y] = score;
	}
	
	private final String GAP = "         ";
	private final int LEN = GAP.length();
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+GAP+GAP+" ");
		for (Object o : colObjs) {
			sb.append("| " + fixLength(o.toString().trim(), LEN) + " ");
		}
		sb.append("\n");
		for (Object o : rowObjs) {
			sb.append(fixLength(o.toString().trim(), LEN*2) + " ");		
			int rowIndex = rowMap.get(o);
			for (int i = 0; i < colObjs.length; i++) {
				double val = score[i][rowIndex];
				String sval = val == 0.0 ? "" : Double.toString(val); 
				sb.append("| " + fixLength(sval, LEN) + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private static String fixLength(String s, int len) {
		String result = s.trim();
		if (s.length() > len) {
			result =  result.substring(0, len).trim();
		}
		if (result.length() < len) {
			while (result.length() < len) {
				result = result + " ";
			}
		}
		return result;	
	}

	
	public class ScoreEvidence {
		private Object obj;
		private double score;
		public ScoreEvidence(Object obj, double score) {
			this.obj = obj;
			this.score = score;
		}
		public Object getObj() {
			return obj;
		}
		public double getScore() {
			return score;
		}
	}
	
	public ScoreEvidence getMaxScoreEvidence(Object o) {
		return new ScoreEvidence(this.getMaxCorrespondentObject(o), this.getMaxScore(o));
	}
	
	private double getMaxScore(Object o) {
		if (rowMap.keySet().contains(o)) {
			return getMaxScoreForRow(o);
		} else if (colMap.keySet().contains(o)) {
			return getMaxScoreForColumn(o);
		} else {
			logger.warning("Unknow object requested "+ o.toString());
			return 0;
		}
	}

	private double getMaxScoreForColumn(Object o) {
		return this.getScore(o, getMaxCorrespondentObjectForColumn(o));
	}

	private double getMaxScoreForRow(Object o) {
		return this.getScore(getMaxCorrespondentObjectForRow(o), o);
	}

	private Object getMaxCorrespondentObject(Object o) {
		if (rowMap.keySet().contains(o)) {
			return getMaxCorrespondentObjectForRow(o);
		} else if (colMap.keySet().contains(o)) {
			return getMaxCorrespondentObjectForColumn(o);
		} else {
			logger.warning("Unknow object requested "+ o.toString());
			return null;
		}
	}

	private Object getMaxCorrespondentObjectForColumn(Object o) {
		double score = Double.NEGATIVE_INFINITY;
		Object result = null;
		for(Object rowObj: rowObjs) {
			double currScore = this.getScore(o, rowObj);
			if (currScore > score) {
				score = currScore;
				result = rowObj;
			}
		}
		return result;
	}

	private Object getMaxCorrespondentObjectForRow(Object o) {
		double score = Double.NEGATIVE_INFINITY;
		Object result = null;
		for(Object colObj: colObjs) {
			double currScore = this.getScore(colObj, o);
			if (currScore > score) {
				score = currScore;
				result = colObj;
			}
		}
		return result;
	}


	/**
	 * scorer averages best values together
	 * TODO: alternative approach: non-greedy approach w/ stable pairings would potentially yield better scores
	 * @return the total score
	 */
	public double getTotalScore() {		
		double score = 0;
		for (Object rowObj : rowObjs) {
			double maxScore = this.getMaxScoreForRow(rowObj);
			score += maxScore;
		}
		score = score / (double) rowObjs.length;
		return score;
	}

}




