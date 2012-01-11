package org.mitre.affinity.model;

import java.util.Collection;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.MultiTaskProgressMonitor;
import org.mitre.affinity.algorithms.clusterers.Clusterer;
import org.mitre.affinity.algorithms.dimensionality_reducers.IMultiDimScaler;
import org.mitre.affinity.algorithms.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.clusters.DistanceGrid;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public abstract class AffinityModel<K extends Comparable<K>, V> {	
	
	/** The clusters */
	protected ClustersContainer<K> clusters;	
	
	/** The distance grid */
	protected DistanceGrid<K> dg;
	
	/** The position grid */
	protected PositionGrid<K> pg;
	
	public abstract IClusterObjectManager<K, V> getClusterObjectManager();	

	public ClustersContainer<K> getClusters() {
		return clusters;
	}

	public void setClusters(ClustersContainer<K> clusters) {
		this.clusters = clusters;
	}	
	
	/**
	 * Get the distance grid.
	 * 
	 * @return the distance grid
	 */
	public DistanceGrid<K> getDistanceGrid() {
		return dg;
	}
	
	/**
	 * Get the position grid.
	 * 
	 * @return the position grid
	 */
	public PositionGrid<K> getPositionGrid() {
		return pg;
	}	
	
	/**
	 * Generates the distance grid, clusters, and the 2D position grid using the given list of cluster objects, 
	 * a distance function, a clusterer, and an MDS algorithm.
	 * 
	 * @param objectIDs
	 * @param distanceFunction
	 * @param clusterer
	 * @param mds
	 * @param progressMonitor
	 */
	public void generateClustersAndPositionGrid(Collection<K> objectIDs, DistanceFunction<K, V> distanceFunction, 
			Clusterer<K> clusterer, IMultiDimScaler<K> mds, IProgressMonitor progressMonitor) {
		MultiTaskProgressMonitor taskMonitor = null;
		if(progressMonitor != null) {
			taskMonitor = new MultiTaskProgressMonitor(progressMonitor, 2);
		}
		generateClusters(objectIDs, distanceFunction, clusterer, taskMonitor);
		if(taskMonitor != null) {
			taskMonitor.setNumTasksComplete(1);
		}
		generatePositionGrid(objectIDs, mds, taskMonitor);
		if(taskMonitor != null) {
			taskMonitor.setNumTasksComplete(2);
		}
	}
	
	/**
	 * Generates the distance grid and clusters using the given list of cluster objects, a distance function, and a clusterer.	
	 * 
	 * @param objectIDs
	 * @param distanceFunction
	 * @param clusterer
	 */
	public void generateClusters(Collection<K> objectIDs, DistanceFunction<K, V> distanceFunction, 
			Clusterer<K> clusterer, IProgressMonitor progressMonitor) {
		if(getClusterObjectManager() != null) {
			dg = distanceFunction.generateDistanceGrid(objectIDs, getClusterObjectManager(), progressMonitor);
			if(dg != null) {
				clusters = clusterer.generateClusters(objectIDs, dg, progressMonitor);
				if(clusters != null) {
					clusters = clusters.removeDuplicateClusterGroups(objectIDs, clusters);
				}
			}
		}
	}
	
	/**
	 * Generate the 2D position grid using the given MDS algorithm and the distance grid.
	 * 
	 * @param objectIDs
	 * @param mds
	 * @param progressMonitor
	 */
	public void generatePositionGrid(Collection<K> objectIDs, IMultiDimScaler<K> mds, 
			IProgressMonitor progressMonitor) {
		if(dg != null) {
			if(progressMonitor != null) {
				progressMonitor.setNote("Computing 2D layout");
			}
			if(mds == null || mds instanceof PrefuseForceDirectedMDS) {
				if(mds == null) {
					mds = new PrefuseForceDirectedMDS<K>();
				}			
				dg.rescale(0, 1000);
				mds.setObjectIDs(objectIDs);
			}		
			pg = mds.scaleDimensions(dg, true, false, 2, false, progressMonitor);
		}
	}	
}