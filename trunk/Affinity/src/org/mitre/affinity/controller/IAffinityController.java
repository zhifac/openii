package org.mitre.affinity.controller;

import org.mitre.affinity.view.menu.event.AffinityMenuItemListener;
import org.mitre.affinity.view.menu.event.AffinityMenuListener;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public interface IAffinityController<K extends Comparable<K>, V> extends AffinityMenuItemListener, AffinityMenuListener {
	public void findAndSelectClusterObject(String identifier);
}
