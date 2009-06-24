package org.openii.schemrserver;

import java.io.File;

public class Consts {

	public static final String SCHEMR_WEB_DERBY_DIR = "file:///C:/eclipse_workspaces/openii/schemr_data/derby_data";

	public static final String LOCAL_DATA_DIR = "C:" + File.pathSeparator
			+ "eclipse_workspaces" + File.pathSeparator + "openii"
			+ File.pathSeparator + "schemr_data";
	public static final String LOCAL_INDEX_DIR = LOCAL_DATA_DIR + File.pathSeparator + "lucene_index";

}
