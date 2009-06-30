package org.openii.schemrserver.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.openii.schemrserver.search.QueryFragment;


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
	
	public int getNumMatches(double threshold) {
		int count = 0;
		for(Object rowObj: rowObjs) {
			if (this.getMaxScoreForRow(rowObj) > threshold) {
				count++;
			}
		}
		return count;
	}	

	private ArrayList<Object> getAllMatchingObjects(Object o, double threshold) {
		if (rowMap.keySet().contains(o)) {
			return getAllMatchingObjectsForRow(o, threshold);
		} else if (colMap.keySet().contains(o)) {
			return getAllMatchingObjectsForColumn(o, threshold);
		} else {
			logger.warning("Unknow object requested "+ o.toString());
			return null;
		}		
	}
	
	private ArrayList<Object> getAllMatchingObjectsForColumn(Object o,
			double threshold) {
		ArrayList<Object> result = new ArrayList<Object>();
		for(Object rowObj: rowObjs) {
			double currScore = this.getScore(o, rowObj);
			if (currScore > threshold) {
				result.add(rowObj);
			}
		}
		return result;	
	}

	private ArrayList<Object> getAllMatchingObjectsForRow(Object o,
			double threshold) {
		ArrayList<Object> result = new ArrayList<Object>();
		for(Object colObj: colObjs) {
			double currScore = this.getScore(colObj, o);
			if (currScore > threshold) {
				result.add(colObj);
			}
		}
		return result;	
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

	public class QFSE {
		public QueryFragment qf;
		public SchemaElement se;
		public double score;
		public QFSE(QueryFragment qf, SchemaElement se, double score) {
			this.qf = qf;
			this.se = se;
			this.score = score;
		}
	}
	
	public class BinFragScore implements Comparable<BinFragScore>{
		public SchemaElement se;
		public double score;
		public Map<QueryFragment, QFSE> details;
		public BinFragScore(SchemaElement se, double score, Map<QueryFragment, QFSE> details) {
			this.se = se;
			this.score = score;
			this.details = details;
		}
		public int compareTo(BinFragScore o) {
			if (o.score == this.score) {
				return 0;
			} else if (o.score > this.score) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	/**
	 * "tightness of fit" 
	 * @param queryFragments 
	 * @param hg 
	 * @return the total score
	 */
	public double getTotalScore(HierarchicalGraph hg, ArrayList<QueryFragment> queryFragments, double threshold) {		
		// get qfrags with matches
		Map<QueryFragment, ArrayList<Object>> goodOnes 
			= new HashMap<QueryFragment, ArrayList<Object>>();
		Set<SchemaElement> bins = new HashSet<SchemaElement>();
		
		for (Object rowObj : rowObjs) {
			ArrayList<Object> currGoodSet = getAllMatchingObjectsForRow(rowObj, threshold);
			goodOnes.put((QueryFragment) rowObj, currGoodSet);
			for (Object co : currGoodSet) {
				SchemaElement matchSE = (SchemaElement)co;
				
				ArrayList<SchemaElement> parents = hg.getParentElements(matchSE.getId());
				if (parents != null && parents.size() > 0) {
					bins.addAll(parents);					
				} else {
					bins.add(matchSE);
				}
			}
		}

		Map<SchemaElement, ArrayList<QFSE>> binToKids 
			= new HashMap<SchemaElement, ArrayList<QFSE>>();
		// assign matches to all bins
		for (QueryFragment qf : goodOnes.keySet()) {
			for (SchemaElement b : bins) {
				for (Object seo : goodOnes.get(qf)) {
					SchemaElement matchSE = (SchemaElement) seo;
					// if this bin contains the good qf...
					if (hg.getChildElements(b.getId()).contains(matchSE) 
							|| b == matchSE) {
						ArrayList<QFSE> kids = binToKids.get(b);
						if (kids == null) {
							kids = new ArrayList<QFSE>();
							binToKids.put(b, kids);
						}
						kids.add(new QFSE(qf,matchSE, this.getScore(matchSE, qf)));
					}
				}
			}
		}
		
		// may be duplicate hits per qf within bin, and between bins
		
		// calculate greedy total for each bin
		ArrayList<BinFragScore> binScorePairs = new ArrayList<BinFragScore>();
		for (SchemaElement bin : binToKids.keySet()) {
			double total = 0;
			
			// track qfs within a bin that may match multiple
			Map<QueryFragment, QFSE> track = new HashMap<QueryFragment, QFSE>();
			for (QFSE qfse : binToKids.get(bin)) {
				if (track.get(qfse.qf) == null) {
					total += qfse.score;
					track.put(qfse.qf, qfse);
				}
			}
			binScorePairs.add(new BinFragScore(bin, total, track));
		}
		// take greedy path through bins, penalizing each farther bin by .1	
		// we can't use the total scores as they could double count some query frags
		BinFragScore [] sortedBins = binScorePairs.toArray(new BinFragScore[0]);
		Arrays.sort(sortedBins);
		
		Set<QueryFragment> accounted = new HashSet<QueryFragment>();
		double score = 0;
		double penalty = 1.0;
		for (BinFragScore bfs : sortedBins) {
			Map<QueryFragment, QFSE> currDetail = bfs.details;
			for (QueryFragment q : currDetail.keySet()) {
				if (!accounted.contains(q)) {
					score += currDetail.get(q).score * penalty;					
					accounted.add(q);
				}
			}
			penalty = penalty * .9;
		}
		return score;
	}
}




