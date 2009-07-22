package org.mitre.openii.views.manager.mappings;

import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.views.manager.mappings.matchmaker.Feeder;
import org.mitre.schemastore.model.Mapping;

/**
 * Map all possible pair-wise schemas in a "Mapping" object 
 * @author HAOLI
 *
 */
public class MapAllDialog {
	
	public static void match( Shell shell, Mapping mapping ) { 
		
		Feeder feeder;
		
		try {
			// match maker runs n-way matches
			feeder = new Feeder(mapping);
			feeder.startRepeatedMatches(); 
			feeder.mergeMatches(); 
			System.out.println("Map All completed.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
