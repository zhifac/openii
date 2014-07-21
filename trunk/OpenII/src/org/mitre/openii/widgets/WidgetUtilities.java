package org.mitre.openii.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Class for sorting widget utilities */
public class WidgetUtilities
{
	/** Sorts the provided array by name */
	public static <T> ArrayList<T> sortList(ArrayList<T> list)
	{
		/** Handles the comparison of list items */
		class ItemComparator implements Comparator<Object>
		{
			public int compare(Object item1, Object item2)
				{ 
					if (item1 == item2) return 0;
					if (item1 == null) return -1;
					return item1.toString().compareToIgnoreCase(item2.toString()); }
		}
		
		Collections.sort(list, new ItemComparator());
		return list;		
	}
}