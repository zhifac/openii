package org.openii.schemrserver.importer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Clean extends LoadSchemaFile{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setClient();
		ArrayList<Integer> deletableSchemas;
			System.out.println("Deletable schemas are being cleared.");
			try {
				deletableSchemas = client.getDeletableSchemas();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			for (Integer i : deletableSchemas){
				
				try {
					client.deleteSchema(i);
					System.out.println("Delete Schema ID: " + i);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return;
		
	}

}
