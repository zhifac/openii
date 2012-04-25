package org.mitre.affinity.controller.schemas;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.algorithms.dimensionality_reducers.IMultiDimScaler;
import org.mitre.affinity.algorithms.dimensionality_reducers.PrefuseForceDirectedMDS;
import org.mitre.affinity.algorithms.distance_functions.schemas.JaccardDistanceFunction;
import org.mitre.affinity.controller.BasicAffinityController;
import org.mitre.affinity.model.Position;
import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.ClusterObjectPairValues;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.schemas.AffinitySchemaModel;
import org.mitre.affinity.model.schemas.ISchemaManager;
import org.mitre.affinity.view.craigrogram.Cluster2DViewPane;
import org.mitre.affinity.view.dendrogram.DendrogramCanvas;
import org.mitre.affinity.view.dialog.StackTraceDialog;
import org.mitre.affinity.view.menu.schemas.AffinityMenu_Schemas;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Controller for the schema-based version of Affinity.
 * 
 * @author CBONACETO
 *
 * @param <Integer>
 * @param <Schema>
 */
public class AffinitySchemaController extends BasicAffinityController<Integer, Schema> {
	
	/** Data used for ground-truthing Affinity against "gold standard" schemas */	
	private List<Schema> schemasForSavingStats;	
	private ArrayList<Integer> schemaIDs;
	private AffinitySchemaModel affinityModelForSavingStats;
	
	private ISchemaManager schemaManager;
	
	public AffinitySchemaController(Composite parentWindow,
			Cluster2DViewPane<Integer, Schema> craigrogram,
			DendrogramCanvas<Integer, Schema> dendrogram, 
			AffinityMenu_Schemas menu,
			AffinitySchemaModel affinityModel) {
		super(parentWindow, craigrogram, dendrogram, menu);
		this.affinityModelForSavingStats = affinityModel;
		schemaManager = affinityModel.getSchemaManager();
	}
	
	public void setSchemaIDsAndSchemas(ArrayList<Integer> schemaIDs, List<Schema> schemas) {
		this.schemaIDs = schemaIDs;
		this.schemasForSavingStats = schemas;
	}	

	@Override
	public void findAndSelectClusterObject(String identifier) {
		Integer schemaID = null;				
		if(schemaManager != null) {
			schemaID = schemaManager.findClusterObject(identifier);
		}		
		if(schemaID != null) {
			dendrogram.setSelectedClusterObject(schemaID);
			craigrogram.setSelectedClusterObject(schemaID);
			dendrogram.redraw();
			craigrogram.redraw();
		}
	}

	@Override
	protected boolean processMenuItemSelectedEvent(MenuItem menuItem,
			Integer menuItemID, Integer parentMenuID) {
		if(menuItemID != null) {		
			if(menuItemID == AffinityMenu_Schemas.FILE_SAVE_SCHEMA_STATS_ITEM) {
				//Save ground truth statisitics
				try {
					FileDialog fileDlg = new FileDialog(parentWindow.getShell(), SWT.SAVE);
					fileDlg.setText("Save Stats");
					fileDlg.setFilterPath("C:/");
					fileDlg.setFilterExtensions(new String[]{"*.xls"});
					fileDlg.setFileName("AffinityStatistics.xls");
					fileDlg.setOverwrite(true);
					String selectedFile = fileDlg.open();
					if(selectedFile != null) {
						writeToSpreadsheet(selectedFile);
					}
				} catch(Exception ex) {
					//Show stack trace dialog
					StackTraceDialog stackTraceDlg = new StackTraceDialog(parentWindow.getShell(), ex);			
					stackTraceDlg.setMessage("Unable to save stats, details:");
					stackTraceDlg.open();
				}
				return true;
			}
			else {			
				return super.processMenuItemSelectedEvent(menuItem, menuItemID, parentMenuID);
			}
		}
		return false;
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void writeToSpreadsheet(String selectedFileName){
		final List<Schema> schemas = this.schemasForSavingStats;
		AffinitySchemaModel affinityModel = this.affinityModelForSavingStats;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Statistics");

		HashMap<Integer, HashMap<Integer, Integer>> groundTruthOverlap = calculateGroundTruthOverlap(schemas, affinityModel);

		String[] colNames = {"Schema A", "Schema B", 
				"Size A", "Size B",
				"Ground Truth Intersection Size", "Ground Truth Union Size",
				"Jaccards (Ground Truth)", 
				"Bag Size A", "Bag Size B", 
				"Bag Intersection Size", "Bag Union Size" ,
				"1- Jaccards (Bag Words)", "Jaccards (Bag Words)", 
				"X coord (for A)", "Y coord (for A)", "X coord (for B)", "Y coord (for B)",
		"Euclidean Distance Between A and B"};
		Row headingRow = sheet.createRow(0);

		CellStyle headingStyle= wb.createCellStyle();
		Font headingFont = wb.createFont();
		headingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headingStyle.setFont(headingFont);

		for(int i=0; i<colNames.length; i++){
			Cell cell = headingRow.createCell(i);
			cell.setCellValue(colNames[i]);
			cell.setCellStyle(headingStyle);
		}

		int nextSchema = 1; //using this to only cover each pair once
		int rowNum = 1;
		Integer schemaAID, schemaBID;
		String schemaAname, schemaBname;
		int aXpos, aYpos, bXpos, bYpos;

		JaccardDistanceFunction distanceFunction = new JaccardDistanceFunction();
		DistanceGrid<Integer> dataDistGrid = distanceFunction.generateDistanceGrid(this.schemaIDs, affinityModel.getSchemaManager());
		ClusterObjectPairValues<Integer, Double> schemaBagIntersectSizes = distanceFunction.getIntersections();
		ClusterObjectPairValues<Integer, Double> schemaBagUnion = distanceFunction.getUnions();

		IMultiDimScaler<Integer> mds = new PrefuseForceDirectedMDS<Integer>();		
		affinityModel.getClusters().getDistanceGrid().rescale(0.0, 1000.0);
		((PrefuseForceDirectedMDS)mds).setObjectIDs(this.schemaIDs);
		PositionGrid<Integer> dataPosGrid = mds.scaleDimensions(affinityModel.getClusters().getDistanceGrid(), true, false, 2, false, null);

		for(int i=0; i<schemaIDs.size(); i++){
			schemaAID = schemaIDs.get(i);
			schemaAname = affinityModel.getSchemaManager().getSchema(schemaAID).getName();
			Position pos = dataPosGrid.getPosition(schemaAID);
			aXpos = (int)pos.getDimensionValue(0);
			aYpos = (int)pos.getDimensionValue(1);
			HashMap<Integer, Integer> groundTruthForI = groundTruthOverlap.get(schemaAID);
			int numElementsA = affinityModel.getSchemaManager().getSchemaInfo(schemaAID).getElements(null).size();
			numElementsA--;//-1 because taking out the blank "" element that always appears


			for(int j = nextSchema; j<schemaIDs.size(); j++){
				schemaBID = schemaIDs.get(j);

				Row dataRow = sheet.createRow(rowNum);

				Cell nameAcell = dataRow.createCell(0);
				nameAcell.setCellValue(schemaAID + " (" + schemaAname + ")");

				Cell nameBcell = dataRow.createCell(1);
				schemaBname = affinityModel.getSchemaManager().getSchema(schemaBID).getName();
				nameBcell.setCellValue(schemaBID + " (" + schemaBname + ")");

				//Size A
				Cell Asize = dataRow.createCell(2);
				Asize.setCellValue(numElementsA); 

				//Size B
				Cell Bsize = dataRow.createCell(3);
				int numElementsB = affinityModel.getSchemaManager().getSchemaInfo(schemaBID).getElements(null).size();
				numElementsB--;//-1 because taking out the blank "" element that always appears	        		
				Bsize.setCellValue(numElementsB); 

				//Ground Truth Intersection Size 
				Cell overlapGroundTruth = dataRow.createCell(4);
				double overlap = groundTruthForI.get(schemaBID);
				overlapGroundTruth.setCellValue(overlap);        		

				//Grount Truth Union Size
				Cell groundTruthUnion = dataRow.createCell(5);
				double unionSize = numElementsA + numElementsB - overlap;
				groundTruthUnion.setCellValue(unionSize);

				//Jaccards (Ground Truth)        		
				Cell jaccardsGroundTruth = dataRow.createCell(6);
				//jaccards ground truth is the size of the intersection over the size of the union
				double jaccardsGT = overlap/unionSize;
				//System.out.println(overlap + "/" + unionSize + "=" + jaccardsGT);
				jaccardsGroundTruth.setCellValue(jaccardsGT);


				Cell bagSizeA = dataRow.createCell(7);
				bagSizeA.setCellValue(distanceFunction.getTermCount(schemaAID));

				Cell bagSizeB = dataRow.createCell(8);
				bagSizeB.setCellValue(distanceFunction.getTermCount(schemaBID));

				Cell bagIntersectionSize = dataRow.createCell(9);
				bagIntersectionSize.setCellValue(schemaBagIntersectSizes.get(schemaAID,schemaBID));

				Cell bagUnionSize = dataRow.createCell(10);
				bagUnionSize.setCellValue(schemaBagUnion.get(schemaAID,schemaBID));

				Cell oneMjaccardAffinity  = dataRow.createCell(11);
				oneMjaccardAffinity.setCellValue(dataDistGrid.get(schemaAID, schemaBID));

				Cell jaccardAffinity = dataRow.createCell(12);
				jaccardAffinity.setCellValue(1-(dataDistGrid.get(schemaAID, schemaBID)));

				Cell aX = dataRow.createCell(13);
				aX.setCellValue(aXpos);

				Cell aY = dataRow.createCell(14);
				aY.setCellValue(aYpos);

				Cell bX = dataRow.createCell(15);
				bXpos = (int)dataPosGrid.getPosition(schemaBID).getDimensionValue(0);
				bX.setCellValue(bXpos);

				Cell bY = dataRow.createCell(16);
				bYpos = (int)dataPosGrid.getPosition(schemaBID).getDimensionValue(1);        		
				bY.setCellValue((int)bYpos);

				Cell euclidDist = dataRow.createCell(17);
				//sqrt of (x-x)^2 + (y-y)^2
				double xs = Math.pow((aXpos-bXpos), 2);
				double ys = Math.pow((aYpos-bYpos), 2);
				double euclid = Math.sqrt(xs+ys);
				euclidDist.setCellValue(euclid);

				rowNum++;
			}
			nextSchema++;
		}

		//resizing all the columns once all the data is in
		for(int i=0; i<colNames.length; i++){
			sheet.autoSizeColumn((short)i);
		}


		//save statistics to the selected spreadsheet
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(selectedFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {e.printStackTrace();}		
	}
	
	private HashMap<Integer, HashMap<Integer, Integer>> calculateGroundTruthOverlap(final List<Schema> schemas, AffinitySchemaModel affinityModel){
		HashMap<Integer,HashMap<Integer, Integer>> groundTruthOverlap = new HashMap<Integer, HashMap<Integer, Integer>>();
		//note: may need way to determine if they're from gold standard repository

		//key is a schemaID, ArryaList of Integers is Integers for all elements in that schema
		HashMap<Integer, ArrayList<String>> schemaElementParsedInts = new HashMap<Integer, ArrayList<String>>();
		
		//first parse each schema and create the ArrayList containing all element numbers	
		for(int i=0; i<schemas.size(); i++){
			Integer schemaIID = schemas.get(i).getId();
			//System.out.println("creating schema: " + schemaIID);
			SchemaInfo schemaIInfo = affinityModel.getSchemaManager().getSchemaInfo(schemaIID);
			ArrayList<SchemaElement> elementsI = schemaIInfo.getElements(null);
			ArrayList<String> schemasIntegers = new ArrayList<String>();

           for(int j=0; j<elementsI.size(); j++) {        	 
        	   String elementName = elementsI.get(j).getName();
        	   int beginNum = elementName.indexOf("(");
        	   int endNum = elementName.indexOf(")");
        	   String num = new String();
        	   for(int k=beginNum+1; k<endNum; k++){
        		   //System.out.println(elementName.charAt(k));
        		   num += elementName.charAt(k);
        	   }
        	   if(num != null && !num.equals("")){
        		   //going to put the element in 
        		   schemasIntegers.add(num);
        	   }
           }
           schemaElementParsedInts.put(schemaIID, schemasIntegers);
		}		
		
		//check to see how many in A match something in B (note we're only creating half of the matrix)
		//enter that into the groundtruth Overlap hashmap
		for(int i=0; i<schemas.size(); i++){
			Integer schemaIID = schemas.get(i).getId();
			ArrayList<String> schemaInums = schemaElementParsedInts.get(schemaIID);
			HashMap<Integer, Integer> schemaIDtoOverlap = new HashMap<Integer, Integer>();
			
			for(int j=i+1; j<schemas.size(); j++){
				Integer schemaJID = schemas.get(j).getId();
				ArrayList<String> schemaJnums = schemaElementParsedInts.get(schemaJID);
				int overlap = 0;
				for(int k=0; k<schemaJnums.size(); k++){
					if(schemaInums.contains(schemaJnums.get(k))){
						overlap++;
					}
				}
				schemaIDtoOverlap.put(schemaJID, overlap);
			}
			groundTruthOverlap.put(schemaIID, schemaIDtoOverlap);
		}
		return groundTruthOverlap;
	}
}