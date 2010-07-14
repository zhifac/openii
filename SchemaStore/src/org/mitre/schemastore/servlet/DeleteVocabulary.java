// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.Mapping;

/**
 * Handles the deleting of a vocabulary from the schema store web service
 * @author CWOLF
 */
public class DeleteVocabulary
{		
	/** Deletes the specified vocabulary from the web services */
	static boolean deleteVocabulary(DataManager manager, Integer projectID) throws RemoteException
	{		
		boolean success = true;
		
		// Delete the vocabulary mappings
		ArrayList<Mapping> mappings = manager.getProjectCache().getVocabularyMappings(projectID);
		for(Mapping mapping : mappings)
			success &= manager.getProjectCache().deleteMapping(mapping.getId());

		// Pull the vocabulary reference from the mapping
		if(success) success &= manager.getProjectCache().deleteVocabularyID(projectID);
		return success;
	}
}