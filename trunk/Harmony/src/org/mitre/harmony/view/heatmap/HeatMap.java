package org.mitre.harmony.view.heatmap;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Vector;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
//import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;

/** Class used for displaying the heat map */
public class HeatMap extends JPanel implements MouseListener, MouseMotionListener, SelectedInfoListener
{
    // Constants for defining the various gradients available
    public final static Color[] GRADIENT_BLUE_TO_RED = createGradient(Color.BLUE, Color.RED, 500);
    public final static Color[] GRADIENT_BLACK_TO_WHITE = createGradient(Color.BLACK, Color.WHITE, 500);
    public final static Color[] GRADIENT_BLACK_TO_BLUE = createGradient(Color.BLACK, Color.BLUE, 500);
    public final static Color[] GRADIENT_RED_TO_GREEN = createGradient(Color.RED, Color.GREEN, 500);
    public final static Color[] GRADIENT_GREEN_YELLOW_ORANGE_RED = createMultiGradient(new Color[]{Color.green, Color.yellow, Color.orange, Color.red}, 500);
    public final static Color[] GRADIENT_RAINBOW = createMultiGradient(new Color[]{new Color(181, 32, 255), Color.blue, Color.green, Color.yellow, Color.orange, Color.red}, 500);
    public final static Color[] GRADIENT_HOT = createMultiGradient(new Color[]{Color.black, new Color(87, 0, 0), Color.red, Color.orange, Color.yellow, Color.white}, 500);
    public final static Color[] GRADIENT_HEAT = createMultiGradient(new Color[]{Color.black, new Color(105, 0, 0), new Color(192, 23, 0), new Color(255, 150, 38), Color.white}, 500);
    public final static Color[] GRADIENT_ROY = createMultiGradient(new Color[]{Color.red, Color.orange, Color.yellow}, 500);
	
	/** Stores the Harmony model */
    private HarmonyModel harmonyModel;
	
	private elementObject[] elementsX;
	private elementObject[] elementsY;
	private scoreGrid bigGrid;
    private Color[][] dataColors;
    int currentLevelX;
    int currentLevelY;
    
    //archiving
    private scoreGrid origBigGrid;
    private elementObject[] origElementsX;
	private elementObject[] origElementsY;
   
    private boolean drawLabels = false;
    private int lowerX;
    private int lowerY;
    private int upperX;
    private int upperY;

    private int movingX;
    private int movingY;
    private boolean drawRectangle = false;

    private int currentX;
    private int currentY;
    private boolean maxInfo = false;
    
    private int height;
    private int width;

    private int xLabelStart;
    private int xLabelEnd;
    private int yLabelStart;
    private int yLabelEnd;

    private Color[] colors;
    private Color bg = Color.white;
    private Color fg = Color.black;

    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;
    
    /** Constructs the heat map */
    public HeatMap(HarmonyModel harmonyModel)
    {
        super();
        currentX=-1;
        currentY=-1;
        this.harmonyModel = harmonyModel;
        
        this.colors = (Color[]) GRADIENT_HOT.clone();
        
        // just to get it off the ground.
        ArrayList<Integer> schemas = harmonyModel.getMappingManager().getSchemas();        
        Schema schema1 = harmonyModel.getSchemaManager().getSchema(schemas.get(0));
		Schema schema2 = harmonyModel.getSchemaManager().getSchema(schemas.get(1));
		this.setUp(schema1, schema2);		

        //updateGradient(GRADIENT_RED_TO_GREEN);
        updateGradient(GRADIENT_HOT);
        
        this.setPreferredSize(new Dimension(60+elementsX.length, 60+elementsY.length));
        this.setDoubleBuffered(true);

        this.bg = Color.white;
        this.fg = Color.black;
        
        // this is the expensive function that draws the data plot into a 
        // BufferedImage. The data plot is then cheaply drawn to the screen when
        // needed, saving us a lot of time in the end.
        //drawData();
    }
    
    public void setUp(Schema schema1, Schema schema2){
    	HierarchicalGraph graph1 = harmonyModel.getSchemaManager().getGraph(schema1.getId());
		HierarchicalGraph graph2 = harmonyModel.getSchemaManager().getGraph(schema2.getId());
		
		ArrayList<SchemaElement> elements1 = graph1.getGraphElements();
		ArrayList<SchemaElement> elements2 = graph2.getGraphElements();
		
		ArrayList<SchemaElement> roots1 = graph1.getRootElements();
		ArrayList<SchemaElement> roots2 = graph2.getRootElements();
		
		int x1 = elements1.size();
		int x2 = elements2.size();
		
		//create grid with matcher scores in it.
		
		int SpotX = 0;
		int SpotY = 0;
		
		//basic idea, use a stack to dfs the graph to generate a list of elements
		//that are in an order based on structure.
		Stack<SchemaElement> stack = new Stack<SchemaElement>();
		Stack<elementObject> eoStack = new Stack<elementObject>();
		Stack<Integer> level = new Stack<Integer>(); // tell me which level of graph I'm at.
		String forwardDelimitor = "  ";
		
		//no size is known ahead of time, due to graph/tree issues, hence stick into dynamic structure.
		ArrayList<elementObject> eXes = new ArrayList<elementObject>();
		ArrayList<elementObject> eYes = new ArrayList<elementObject>();
		
		//already seen it
		HashMap<SchemaElement,Integer> hashX = new HashMap<SchemaElement,Integer>();
		HashMap<SchemaElement,Integer> hashY = new HashMap<SchemaElement,Integer>();
		
		//elementsX = new elementObject[x1];
		//elementsY = new elementObject[x2];
		
		//handle graph1.
		for(SchemaElement seA: roots1){
			hashX.put(seA, 1);
			stack.push(seA); //push each root
			level.push(0);
			elementObject nEO = new elementObject(seA, graph1.getDisplayName(seA.getId()));
			eoStack.push(nEO);
		}
		elements1 = new ArrayList<SchemaElement>();
		int i=0;
		while(!stack.empty()){
			//pop top element
			System.out.println("Value is "+i++);
			SchemaElement current = stack.pop();
			elementObject eoCurrent = eoStack.pop();
			elements1.add(current);
			StringBuffer prefixBit = new StringBuffer();
			int levelAt = level.pop();
			for(int j=0; j < levelAt;j++) prefixBit.append(forwardDelimitor);
			prefixBit.append(graph1.getDisplayName(current.getId()));
			eXes.add(eoCurrent);
//			elementsX[SpotX] = eoCurrent;
			eoCurrent.setLevel(levelAt);
			eoCurrent.setLabel(prefixBit.toString());
			SpotX++;
			ArrayList<SchemaElement> children = graph1.getChildElements(current.getId());
			for(SchemaElement mychild: children){
				if(hashX.containsKey(mychild)) continue;
				hashX.put(mychild,1);
				stack.push(mychild);
				level.push(levelAt+1);
				elementObject nEO = new elementObject(mychild,graph1.getDisplayName(mychild.getId()));
				nEO.setParent(eoCurrent);
				eoCurrent.addChild(nEO);
				eoStack.push(nEO);
			}
		}
		x1=eXes.size();
		elementsX = new elementObject[x1];
		int placePointer=0;
		for(elementObject v:eXes){
			elementsX[placePointer++] = v;
		}
		
		//handle graph2.  yuckie, cookie-cuttering code.
		for(SchemaElement seA: roots2){
			hashY.put(seA,1);
			stack.push(seA); //push each root
			level.push(0);
			elementObject nEO = new elementObject(seA,graph2.getDisplayName(seA.getId()));
			eoStack.push(nEO);
		}
		elements2 = new ArrayList<SchemaElement>();
		i=0;
		while(!stack.empty()){
			//pop top element
			System.out.println("Value2 is "+i++);
			SchemaElement current = stack.pop();
			elementObject eoCurrent = eoStack.pop();
			elements2.add(current);
			StringBuffer prefixBit = new StringBuffer();
			int levelAt = level.pop();
			for(int j=0; j < levelAt;j++) prefixBit.append(forwardDelimitor);
			prefixBit.append(graph2.getDisplayName(current.getId()));
			eYes.add(eoCurrent);
			//elementsY[SpotY] = eoCurrent;
			eoCurrent.setLevel(levelAt);
			eoCurrent.setLabel(prefixBit.toString());
			SpotY++;
			ArrayList<SchemaElement> children = graph2.getChildElements(current.getId());
			for(SchemaElement mychild: children){
				if(hashY.containsKey(mychild)) continue;
				hashY.put(mychild,1);
				stack.push(mychild);
				level.push(levelAt+1);
				elementObject nEO = new elementObject(mychild,graph2.getDisplayName(mychild.getId()));
				nEO.setParent(eoCurrent);
				eoCurrent.addChild(nEO);
				eoStack.push(nEO);
			}
		}
		x2=eYes.size();
		elementsY = new elementObject[x2];
		placePointer=0;
		for(elementObject v:eYes){
			elementsY[placePointer++] = v;
		}
		
		SpotX = 0;
		SpotY = 0;
		bigGrid = new scoreGrid();
		for(SchemaElement se1: elements1){
			for(SchemaElement se2: elements2){
				Integer id = harmonyModel.getMappingCellManager().getMappingCellID(se1.getId(), se2.getId());
				if(id == null){
					SpotY++;
					continue;
				}
				MappingCell mc = harmonyModel.getMappingCellManager().getMappingCell(id);
				bigGrid.insert(se1.getId(), se2.getId(), mc.getScore());
			}
			SpotX++;
			SpotY=0;
		}
		
		//get labels.
		//setLabels(LabelsX, LabelsY, x1, x2);
		setLabels(x1, x2);
		
		updateData();
		this.setPreferredSize(new Dimension(60+elementsX.length, 60+elementsX.length));
        this.setDoubleBuffered(true);
        drawData();
        checkpointData();
        currentLevelX=0;
        currentLevelY=0;
    }
    
/*    void reOrderElements(scoreGrid bigGrid, elementObjectInterface xEOi, elementObjectInterface yEOi,
    		int lowX, int highX, int lowY, int highY)
    {
    	scoreGrid sG = getScores(xEOi.getChildren(),yEOi.getChildren(),bigGrid);
    	
    }
    
    **
     * We want to try to 'diagonalize' the score grid.  Do that here.
     *
    void ReorderElements(scoreGrid bigGrid){
    	elementObjectRoot bestRootX=new elementObjectRoot();
    	elementObjectRoot bestRootY=new elementObjectRoot();
    	
    	ArrayList<elementObject> xEOs = new ArrayList<elementObject>();
    	ArrayList<elementObject> yEOs = new ArrayList<elementObject>();
    	
    	double bestScore;
    	
    	//ok, lets do root level only first, to get a feel for the algorithm and
    	//take some baby steps first.
    	
    	//should work.
    	for(int q=0; q < rootX.getNumChildren(); q++){
    		xEOs.add(rootX.getChild(q));
    		rootX.getChild(q).setLastSpot(rootX.getNumChildren());
    	}
    	for(int q=0; q < rootY.getNumChildren(); q++){
    		yEOs.add(rootY.getChild(q));
    		rootY.getChild(q).setLastSpot(rootY.getNumChildren());
    	}
    	
    	while(true){
    		//sizes in each dimension
    		int xSize = xEOs.size();
    		int ySize = yEOs.size();
    		//size at this level
    		double[][] compareToMe = new double[xSize][ySize];
    		//which is bigger?
    		if(xSize >= ySize){
    			double[] tempVals = new double[xSize];
    			//this goes 1, .8, .6, .4, .2 eg. for xsize = 5.
    			for(int m=0; m < xSize; m++){
    				tempVals[m] = 1.0/((double)(xSize-1))*((double)(m));
    			}
	    		for(int j=0; j < xSize; j++){
	    			for(int k=0; k < ySize; k++){
	    				compareToMe[j][k] = tempVals[Math.abs(((int)Math.round(((double)k)/((double)ySize-1)*((double)xSize-1))-j))];
	    			}
	    		}
    		}
    		else{ //ySize > xSize
    			double[] tempVals = new double[ySize];
    			//this goes 1, .8, .6, .4, .2 eg. for xsize = 5.
    			for(int m=0; m < ySize; m++){
    				tempVals[m] = 1.0/((double)(ySize-1))*((double)(m));
    			}
	    		for(int j=0; j < ySize; j++){
	    			for(int k=0; k < xSize; k++){
	    				compareToMe[k][j] = tempVals[Math.abs(((int)Math.round(((double)k)/((double)xSize-1)*((double)ySize-1))-j))];
	    			}
	    		}
    		}
    		
    		scoreGrid sG = getScores(xEOs,yEOs,bigGrid);
    		
    		if(xSize >= ySize){
    			//for(int i=0; ){
    			//	for(int j=0;){
    					
    			//	}
    			//}
    		}
    		
    		
    		
    		//now, do ordering.
    		for(int i=0,j=0; i< xEOs.size() && j < yEOs.size(); ){
    			//just starting out
    			if(i==0 && j==0){
    				int maxX=xSize;
    				int maxY=ySize;
    				elementObject bestX=xEOs.get(0);
    				elementObject bestY=yEOs.get(0);
    				double bestVal=sG.get(bestX.getID(), bestY.getID());
    				for(int p=0; p< maxX; p++){
    					for(int q=0; q<maxY; q++){
    						if(sG.get(xEOs.get(p).getID(),yEOs.get(q).getID()) > bestVal){
    							bestX=xEOs.get(p); bestY = yEOs.get(q);
    						}
    						if(bestX.lastSpot < maxX) maxX = bestX.lastSpot;
    						if(bestY.lastSpot < maxY) maxY = bestY.lastSpot;
    					}
    				}
    				
    			}
    		}
    		for(int i=0; i < xEOs.size(); i++){
    			for(int j=0; j < yEOs.size(); j++){
    				
    			}
    		}
    	}
    	
    	//iterate over all possible combos that make sense.
    	
    }
    
    scoreGrid getScores(ArrayList<elementObject> xEOs, ArrayList<elementObject> yEOs, scoreGrid bigGrid){
    	scoreGrid sG = new scoreGrid();
    	
    	int xSize = xEOs.size();
		int ySize = yEOs.size();
		
		for(int i=0; i < xSize; i++){
			for(int j=0; j < ySize; j++){
				//get combined score
				double newScore = newScoreCombine(xEOs.get(i),yEOs.get(j),bigGrid);
				sG.insert(xEOs.get(i).getID(), yEOs.get(j).getID(), newScore);
			}
		}
		return sG;
    }
    
    double newScoreCombine(elementObject xEO, elementObject yEO, scoreGrid sG){
    	//create big arrays of child objects, in essence mapping out [m,n,bigm,bign] area of score array.
    	Vector<elementObject> xChildren = xEO.getDescendants();
    	Vector<elementObject> yChildren = yEO.getDescendants();
    		
    	//take average of max in each row or column, whichever is smallest.
    	double average=0;
    	if(xChildren.size() > yChildren.size()){
    		//rows dominate
    		for(int j=0; j < yChildren.size(); j++){
    			double biggestVal=0;
    			for(int k=0; k < xChildren.size(); k++){
    				if(biggestVal<sG.get(xChildren.get(k).getID(), yChildren.get(j).getID())) 
    					biggestVal = sG.get(xChildren.get(k).getID(), yChildren.get(j).getID());
    			}
    			average+=biggestVal;
    		}
    		average=average/new Double(yChildren.size());
    	}
    	else{
    		//columns dominate
    		for(int k=0; k < xChildren.size(); k++){
    			double biggestVal=0;
        		for(int j=0; j < yChildren.size(); j++){
    				if(biggestVal<sG.get(xChildren.get(k).getID(), yChildren.get(j).getID())) 
    					biggestVal = sG.get(xChildren.get(k).getID(), yChildren.get(j).getID());
    			}
    			average+=biggestVal;
    		}
    		average=average/new Double(xChildren.size());
    	}
    	return average;
    }
    */
    
    /** Resets the heat map back to its default settings */
    void reset()
    {
    	uncheckData(); currentLevelX=0;currentLevelY=0;
        drawLabels=false;
        setLowerXY(0,0);
        setBiggerXY(width,height); 
        updateDataColors();
        rescale(); 
        repaint(); 
    }
    
    /** Saves current modified mapping elements to schemastore */
    void SaveMapping(){
    	for(int j=0; j < elementsX.length; j++){
    		for(int k=0; k < elementsY.length;k++){
    			int xID = elementsX[j].getID();
    			int yID = elementsY[k].getID();
    			if(bigGrid.isModified(xID, yID) == true){
			    	Integer id = harmonyModel.getMappingCellManager().getMappingCellID(xID, yID);
			    	MappingCellManager.modifyMappingCell(id, bigGrid.get(xID, yID), System.getProperty("user.name"), true);
    			}
	    	}
    	}
    }
    
    /** Toggles the info box */
    void toggleInfoBox()
    	{ maxInfo = !maxInfo; repaint(); }
    
    /** Toggles the heat map labels */
    void toggleLabels()
    	{ drawLabels = !drawLabels; repaint(); }
    
    int computeNewSize(elementObject[] tElements, int level){
    	int answer = 0;
    	for(int j=0; j < tElements.length; j++){
    		if(tElements[j].getLevel() <= level) answer++;
    	}
    	return answer;
    }
    
    //ok, to make this work, we need some notion of what level we are at in each dimension
    //int levelX, levelY;
    //also need some notion of max level in each direction
    //int maxlevelX, maxlevelY;
    //these need to get set in setUp when constructing indentation scheme.
    void visualSummary(int newLevelX, int newLevelY){
    	//resurrect original data.
    	uncheckData();
    	
    	//compute size of new in each dimension;
    	int newXSize = computeNewSize(elementsX, newLevelX);
    	int newYSize = computeNewSize(elementsY, newLevelY);
    	
    	//create new data array.
    	scoreGrid newBigGrid = new scoreGrid();
    	elementObject[] aElementsX = new elementObject[newXSize];
    	elementObject[] aElementsY = new elementObject[newYSize];
    	
    	int SpotX=0;
    	int SpotY=0;
    	//go double nested loops through big data grid.
    	//storing in new data array.
    	for(int m=0;m<elementsX.length; SpotX++){
    		int running_m = m+1;
    		SpotY=0;
    		while(running_m < elementsX.length && elementsX[running_m].getLevel()>newLevelX) running_m++; //[m,running_m)
    		for(int n=0; n<elementsY.length;SpotY++){
    			int running_n = n+1;
        		while(running_n < elementsY.length && elementsY[running_n].getLevel()>newLevelY) running_n++; //[n,running_n)
        		//now, the square region [m,n,running_m,running_n) has been defined and is to be placed
        		//in SpotX,SpotY
        		newBigGrid.insert(elementsX[m].getID(), elementsY[n].getID(), scoreCombine(m,n,running_m,running_n));
        		//aLabelsY[SpotY] = labelsY[n];
        		aElementsY[SpotY] = elementsY[n];
        		n=running_n;
    		}
    		aElementsX[SpotX] = elementsX[m];
    		m=running_m;
    	}
    	setLabels(newXSize,newYSize);
    	checkpointData();
    	
    	elementsX=aElementsX;
    	elementsY=aElementsY;
    	bigGrid=newBigGrid;
    	
    	updateData();
		this.setPreferredSize(new Dimension(60+elementsX.length, 60+elementsY.length));
        this.setDoubleBuffered(true);
        drawData();
    }
    
    double scoreCombine(int smallM, int smallN, int bigM, int bigN){
    	//take average of max in each row or column, whichever is smallest.
    	double average=0;
    	if(bigM-smallM > bigN-smallN){
    		//rows dominate
    		for(int j=smallN; j < bigN; j++){
    			double biggestVal=0;
    			for(int k=smallM; k < bigM; k++){
    				if(bigGrid.contains(elementsX[k].getID(), elementsY[j].getID())){
    					if(biggestVal<bigGrid.get(elementsX[k].getID(), elementsY[j].getID())) biggestVal = bigGrid.get(elementsX[k].getID(), elementsY[j].getID());
    				}
    			}
    			average+=biggestVal;
    		}
    		average=average/new Double(bigN-smallN);
    	}
    	else{
    		//columns dominate
    		for(int k=smallM; k < bigM; k++){
    			double biggestVal=0;
        		for(int j=smallN; j < bigN; j++){
        			if(bigGrid.contains(elementsX[k].getID(), elementsY[j].getID())){
        				if(biggestVal<bigGrid.get(elementsX[k].getID(), elementsY[j].getID())) biggestVal = bigGrid.get(elementsX[k].getID(), elementsY[j].getID());
        			}
    			}
    			average+=biggestVal;
    		}
    		average=average/new Double(bigM-smallM);
    	}
    	return average;
    }

    public void setLabels(int numX, int numY){
    	xLabelStart =0;
        xLabelEnd =numX;
        yLabelStart =0;
        yLabelEnd =numY;
    }
    
    //save original data matrix and other info for possible aggregation of data points at
    //higher levels of granularity.  I.e. visual summarization.
    public void checkpointData(){
    	origElementsX = elementsX;
    	origElementsY = elementsY;
    	origBigGrid = bigGrid;    	
    }
    
    //resurrect original data matrix and other info from possible aggregation of data points at
    //higher levels of granularity.  I.e. visual summarization.
    public void uncheckData(){
    	bigGrid = origBigGrid;
    	elementsX = origElementsX;
    	elementsY = origElementsY;
    }
    
    int computeNewSize(int[] levels, int numLevels, int newLevelNum){
    	int answer=0;
    	for(int j=0; j<numLevels; j++){
    		if(levels[j] <=newLevelNum){
    			answer++;
    		}
    	}
    	return answer;
    }

    /**
     * Updates the foreground color. Calls repaint() when finished.
     * @param fg Specifies the desired foreground color
     */
    public void setColorForeground(Color fg)
    {
        this.fg = fg;
        repaint();
    }

    /**
     * Updates the background color. Calls repaint() when finished.
     * @param bg Specifies the desired background color
     */
    public void setColorBackground(Color bg)
    {
        this.bg = bg;
        repaint();
    }

    /**
     * Updates the gradient used to display the data. Calls drawData() and 
     * repaint() when finished.
     * @param colors A variable of type Color[]
     */
    public void updateGradient(Color[] colors)
    {
        this.colors = (Color[]) colors.clone();       
        updateDataColors();
        drawData();
        repaint();
    }
    
    /**
     * This uses the current array of colors that make up the gradient, and 
     * assigns a color to each data point, stored in the dataColors array, which
     * is used by the drawData() method to plot the points.
     */
    private void updateDataColors()
    {
        //We need to find the range of the data values,
        // in order to assign proper colors.
        double largest = Double.MIN_VALUE;
        double smallest = Double.MAX_VALUE;
        int currentXSize = elementsX.length;
        int currentYSize = elementsY.length;
        for (int x = 0; x < currentXSize; x++)
        {
            for (int y = 0; y < currentYSize; y++)
            {
            	if(bigGrid.contains(elementsX[x].getID(), elementsY[y].getID())){
            		largest = Math.max(bigGrid.get(elementsX[x].getID(), elementsY[y].getID()), largest);
            		smallest = Math.min(bigGrid.get(elementsX[x].getID(), elementsY[y].getID()), smallest);
            	}
            }
        }
        largest=1.0; //bump up largest to 1.0
        double range = largest - smallest;

        // dataColors is the same size as the data array
        dataColors = new Color[currentXSize][currentYSize];    

        //assign a Color to each data point
        for (int x = 0; x < currentXSize; x++)
        {
            for (int y = 0; y < currentYSize; y++)
            {
            	 if(bigGrid.contains(elementsX[x].getID(), elementsY[y].getID())){
	            	double norm = (bigGrid.get(elementsX[x].getID(), elementsY[y].getID()) - smallest) / range; // 0 < norm < 1
	                int color = (int) Math.floor(norm * (colors.length - 1));
	                dataColors[x][y] = colors[color];
            	}
            	else dataColors[x][y]=colors[0];
            }
        }
        
        //also check to see if currently selected item is "" 
        //assign "blue" color to currently moused over item
        int currentLX = getCurrentXGridLoc();
        int currentLY = getCurrentYGridLoc();
        if((0<=currentLX && currentLX < currentXSize) && 0<=currentLY && currentLY <currentYSize){
        	if(bigGrid.contains(elementsX[currentLX].getID(), elementsY[currentLY].getID())){	            
		        double norm = (bigGrid.get(elementsX[currentLX].getID(), elementsY[currentLY].getID()) - smallest) / range; // 0 < norm < 1
		        int color = (int) Math.floor(norm * (GRADIENT_BLACK_TO_BLUE.length - 1));
		        dataColors[currentLX][currentLY] = GRADIENT_BLACK_TO_BLUE[color];
        	}
        	else dataColors[currentLX][currentLY] = GRADIENT_BLACK_TO_BLUE[0];
        }
        
        //synch up with the line drawing view, and make the selected item
        //"blue"
        int xhighlight = getHighlightedXGridLoc();
        int yhighlight = getHighlightedYGridLoc();
        if(0<=xhighlight && xhighlight < currentXSize && 0<=yhighlight && yhighlight < currentYSize){
        	if(bigGrid.contains(elementsX[xhighlight].getID(), elementsY[yhighlight].getID())){	
	        	double norm = (bigGrid.get(elementsX[xhighlight].getID(), elementsY[yhighlight].getID()) - smallest) / range; // 0 < norm < 1
		        int color = (int) Math.floor(norm * (GRADIENT_BLACK_TO_BLUE.length - 1));
		        dataColors[xhighlight][yhighlight] = GRADIENT_BLACK_TO_BLUE[color];
        	}
        	else dataColors[xhighlight][yhighlight] = GRADIENT_BLACK_TO_BLUE[0];
        }
        
    }

    /**
     * Creates an array of Color objects for use as a gradient, using a linear 
     * interpolation between the two specified colors.
     * @param one Color used for the bottom of the gradient
     * @param two Color used for the top of the gradient
     * @param numSteps The number of steps in the gradient. 250 is a good number.
     */
    public static Color[] createGradient(Color one, Color two, int numSteps)
    {
        int r1 = one.getRed();
        int g1 = one.getGreen();
        int b1 = one.getBlue();

        int r2 = two.getRed();
        int g2 = two.getGreen();
        int b2 = two.getBlue();

        int newR = 0;
        int newG = 0;
        int newB = 0;

        Color[] gradient = new Color[numSteps];
        double iNorm;
        for (int i = 0; i < numSteps; i++)
        {
            iNorm = i / (double)numSteps; //a normalized [0:1] variable
            newR = (int) (r1 + iNorm * (r2 - r1));
            newG = (int) (g1 + iNorm * (g2 - g1));
            newB = (int) (b1 + iNorm * (b2 - b1));
            gradient[i] = new Color(newR, newG, newB);
        }

        return gradient;
    }

    /**
     * Creates an array of Color objects for use as a gradient, using an array of Color objects. It uses a linear interpolation between each pair of points.
     * @param colors An array of Color objects used for the gradient. The Color at index 0 will be the lowest color.
     * @param numSteps The number of steps in the gradient. 250 is a good number.
     */
    public static Color[] createMultiGradient(Color[] colors, int numSteps)
    {
        //we assume a linear gradient, with equal spacing between colors
        //The final gradient will be made up of n 'sections', where n = colors.length - 1
        int numSections = colors.length - 1;
        int gradientIndex = 0; //points to the next open spot in the final gradient
        Color[] gradient = new Color[numSteps];
        Color[] temp;

        if (numSections <= 0)
        {
            throw new IllegalArgumentException("You must pass in at least 2 colors in the array!");
        }

        for (int section = 0; section < numSections; section++)
        {
            //we divide the gradient into (n - 1) sections, and do a regular gradient for each
            temp = createGradient(colors[section], colors[section+1], numSteps / numSections);
            for (int i = 0; i < temp.length; i++)
            {
                //copy the sub-gradient into the overall gradient
                gradient[gradientIndex++] = temp[i];
            }
        }

        if (gradientIndex < numSteps)
        {
            //The rounding didn't work out in our favor, and there is at least
            // one unfilled slot in the gradient[] array.
            //We can just copy the final color there
            for (/* nothing to initialize */; gradientIndex < numSteps; gradientIndex++)
            {
                gradient[gradientIndex] = colors[colors.length - 1];
            }
        }

        return gradient;
    }

    /**
     * Updates the data display, calls drawData() to do the expensive re-drawing
     * of the data plot, and then calls repaint().
     * @param data The data to display, must be a complete array (non-ragged)
     */
    public void updateData()
    {
        updateDataColors();
        drawData();
        repaint();
    }
    
    /**
     * Creates a BufferedImage of the actual data plot.
     *
     * After doing some profiling, it was discovered that 90% of the drawing
     * time was spend drawing the actual data (not on the axes or tick marks).
     * Since the Graphics2D has a drawImage method that can do scaling, we are
     * using that instead of scaling it ourselves. We only need to draw the 
     * data into the bufferedImage on startup, or if the data or gradient
     * changes. This saves us an enormous amount of time. Thanks to 
     * Josh Hayes-Sheen (grey@grevian.org) for the suggestion and initial code
     * to use the BufferedImage technique.
     * 
     * Since the scaling of the data plot will be handled by the drawImage in
     * paintComponent, we take the easy way out and draw our bufferedImage with
     * 1 pixel per data point. Too bad there isn't a setPixel method in the 
     * Graphics2D class, it seems a bit silly to fill a rectangle just to set a
     * single pixel...
     *
     * This function should be called whenever the data or the gradient changes.
     */
    private void drawData()
    {
    	//compute size of new in each dimension;
    	int newXSize = elementsX.length; // computeNewSize(elementsX, currentLevelX);
    	int newYSize = elementsY.length; //computeNewSize(elementsY, currentLevelY);
    	
        bufferedImage = new BufferedImage(newXSize,newYSize, BufferedImage.TYPE_INT_RGB);
        bufferedGraphics = bufferedImage.createGraphics();
        
        for (int x = 0; x < newXSize; x++)
        {
            for (int y = newYSize - 1; y >= 0; y--)
            {
                bufferedGraphics.setColor(dataColors[x][y]);
                bufferedGraphics.fillRect( 
                        (int)Math.ceil(x), 
                        (int)Math.ceil(newYSize - y - 1),
                        1, 1);
            }
        }
    }

    // added by mdmorse
    private void drawUpdatedData()
    {
        bufferedImage = new BufferedImage(xLabelEnd-xLabelStart,yLabelEnd-yLabelStart, BufferedImage.TYPE_INT_RGB);
        bufferedGraphics = bufferedImage.createGraphics();
        
        for (int x = xLabelStart; x < xLabelEnd; x++)
        {
            for (int y = yLabelEnd-1; y >= yLabelStart; y--)
            {
                bufferedGraphics.setColor(dataColors[x][y]);
                bufferedGraphics.fillRect( 
                        (int)Math.ceil(x-xLabelStart), 
                        (int)Math.ceil(yLabelEnd - y - 1),
                        1, 1);
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        width = this.getWidth();
        height = this.getHeight();
        
        this.setOpaque(true);

        // clear the panel
        g2d.setColor(bg);
        g2d.fillRect(0, 0, width, height);

        // draw the heat map
        if (bufferedImage == null)
        {
            // Ideally, we only to call drawData in the constructor, or if we
            // change the data or gradients. We include this just to be safe.
            drawData();
        }

        if(drawLabels){        
           // The data plot itself is drawn with 1 pixel per data point, and the
           // drawImage method scales that up to fit our current window size. This
           // is very fast, and is much faster than the previous version, which 
           // redrew the data plot each time we had to repaint the screen.
           g2d.drawImage(bufferedImage,
                      11, 11,
                      width - 150,
                      height - 150,
                      0, 0,
                      bufferedImage.getWidth(), bufferedImage.getHeight(),
                      null);

           // border
           g2d.setColor(fg);
           g2d.drawRect(10, 10, width - 160, height - 160);
        
           //added mdmorse /////////////////
           double xDDist = ((width-160)/(double) (xLabelEnd-xLabelStart));
            g2d.rotate(Math.PI / 2);
            for (int x = xLabelStart; x < xLabelEnd; x++){
                g2d.drawString(elementsX[x].getLabel(), height-150, -10-((int)(xDDist*(x-xLabelStart))));
                if(x != 0){
                   if(elementsX[x].getLevel()<elementsX[x-1].getLevel()){// check(labelsX[x], labelsX[x-1])){
                	   Color c = g2d.getColor();
                	   g2d.setColor(Color.GREEN);
                     g2d.drawLine(10,-10-((int)(xDDist*(x-xLabelStart))),((int)(height - 150)), -10-((int)(xDDist*(x-xLabelStart))));
                     g2d.setColor(c);
                   }
                }
            }
            g2d.rotate( -Math.PI / 2);

           double yDDist = ((height-160)/(double) (yLabelEnd-yLabelStart));
            for (int y = yLabelStart; y < yLabelEnd; y++){
                g2d.drawString(elementsY[y].getLabel(), width-150, height-150-((int)(yDDist*(y-yLabelStart))));
                if(y != 0){
                   if(elementsY[y].getLevel() < elementsY[y-1].getLevel()){//check(labelsY[y], labelsY[y-1])){
                	   Color c = g2d.getColor();
                	   g2d.setColor(Color.GREEN);
                     g2d.drawLine(10,height-150-((int)(yDDist*(y-yLabelStart))),((int)(width - 150)), height-150-((int)(yDDist*(y-yLabelStart))));
                     g2d.setColor(c);
                   }
                }
            }

           //end added mdmorse
        }
        else{
           g2d.drawImage(bufferedImage,
                      11, 11,
                      width - 10,
                      height - 10,
                      0, 0,
                      bufferedImage.getWidth(), bufferedImage.getHeight(),
                      null);

           // border
           g2d.setColor(fg);
           g2d.drawRect(10, 10, width - 20, height - 20);

           //added mdmorse /////////////////
           double xDDist = ((width-20)/(double) (xLabelEnd-xLabelStart));
            g2d.rotate(Math.PI / 2);
            for (int x = xLabelStart; x < xLabelEnd; x++){
            	if(x != 0){
                   if(elementsX[x].getLevel()<elementsX[x-1].getLevel()){// check(labelsX[x], labelsX[x-1])){
                	   Color c = g2d.getColor();
                	   g2d.setColor(Color.GREEN);
                     g2d.drawLine(10,-10-((int)(xDDist*(x-xLabelStart))),((int)(height - 10)), -10-((int)(xDDist*(x-xLabelStart))));
                     g2d.setColor(c);
                   }
                }
            }
            g2d.rotate( -Math.PI / 2);

           double yDDist = ((height-20)/(double) (yLabelEnd-yLabelStart));
            for (int y = yLabelStart; y < yLabelEnd; y++){
            	if(y != 0){
                   if(elementsY[y].getLevel()< elementsY[y-1].getLevel()){//check(labelsY[y], labelsY[y-1])){
                	   Color c = g2d.getColor();
                	   g2d.setColor(Color.GREEN);
                     g2d.drawLine(10,height-10-((int)(yDDist*(y-yLabelStart))),((int)(width - 10)), height-10-((int)(yDDist*(y-yLabelStart))));
                	   g2d.setColor(c);
                   }
                }
            }

           //end added mdmorse
        }
        if(drawRectangle == true){
          drawMovingRectangle(g);
        }
        else if(maxInfo == true){
          drawMaxInfo(g);
        }
    }
   
    public void drawMaxInfo(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        //find out where to place the text.
        double localwidth;
        double localheight;
        if(drawLabels){ localwidth = width-160; localheight=height-160;
        } else{ localwidth = width-20; localheight=height-20;
        }
        double xPos = ((double)(localwidth)/(double) (xLabelEnd-xLabelStart));
        double yPos = ((double)(localheight)/(double) (yLabelEnd-yLabelStart));
        int xVal = (int) ((currentX-10)/(double) (xPos));
        int yVal = (int) ((currentY-10)/(double) (yPos));

        //make sure values are in right domain.
        xVal = xVal < 0? 0: xVal;
        xVal = xVal >= elementsX.length? elementsX.length-1:xVal;
        yVal = yVal < 0? 0: yVal;
        yVal = yVal >= elementsY.length? elementsY.length-1:yVal;
        
        int length = elementsX[xVal].getLabel().length();
        length= length>=elementsY[yLabelEnd-yVal-1].getLabel().length()?length:elementsY[yLabelEnd-yVal-1].getLabel().length();
        
        //draw a box around the text.
        g2d.setColor(Color.white);
        g2d.drawRect(currentX+14, currentY+7, length*8+1, 28);
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(currentX+15, currentY+8, length*8, 27);
        
        //draw the text.
        g2d.setColor(Color.white);
        g2d.drawString(elementsX[xVal].getLabel(), currentX+20, currentY+20);
        g2d.drawString(elementsY[yLabelEnd-yVal-1].getLabel(), currentX+20, currentY+30);
    }
    
    private int getCurrentYGridLoc(){
        double localheight;
        if(drawLabels){  localheight=height-160;
        } else{  localheight=height-20;
        }
        double yPos = ((double)(localheight)/(double) (yLabelEnd-yLabelStart));
        int yVal = (int) ((currentY-10)/(double) (yPos));
        return yLabelEnd-yVal-1;
        //return yVal;
    }
    
    private int getCurrentXGridLoc(){
    	double localwidth;
        if(drawLabels){ localwidth = width-160; 
        } else{ localwidth = width-20; 
        }
        double xPos = ((double)(localwidth)/(double) (xLabelEnd-xLabelStart));
        int xVal = (int) ((currentX-10)/(double) (xPos));
        return xVal;
    }
    
    private int getHighlightedXGridLoc(){
    	List<Integer>lefts = harmonyModel.getSelectedInfo().getElements(HarmonyConsts.RIGHT);
    	if(lefts != null && lefts.size() >0){
    		int highlighted = lefts.get(0);
    		for(int j=0; j < elementsX.length; j++){
    			if(highlighted == elementsX[j].getID()) return j;
    		}
    		return -1;
    	} else return -1;
    }
    
    private int getHighlightedYGridLoc(){
    	List<Integer>lefts = harmonyModel.getSelectedInfo().getElements(HarmonyConsts.LEFT);
    	if(lefts != null && lefts.size() > 0){
    		int highlighted = lefts.get(0);
    		for(int j=0; j < elementsY.length; j++){
    			if(highlighted == elementsY[j].getID()) return j;
    		}
    		return -1;
    	} else return -1;
    }
    
    public void drawMovingRectangle(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(lowerX,lowerY,lowerX,movingY);
        g2d.drawLine(lowerX,lowerY,movingX,lowerY);
        g2d.drawLine(movingX,lowerY,movingX,movingY);
        g2d.drawLine(lowerX,movingY,movingX,movingY);
    }
    
    void setUpdate(){
    	//get the location information.
    	double localwidth;
        double localheight;
        if(drawLabels){ localwidth = width-160; localheight=height-160;
        } else{ localwidth = width-20; localheight=height-20;
        }
        double xPos = ((double)(localwidth)/(double) (xLabelEnd-xLabelStart));
        double yPos = ((double)(localheight)/(double) (yLabelEnd-yLabelStart));
        int xVal = (int) ((currentX-10)/(double) (xPos));
        int yVal = (int) ((currentY-10)/(double) (yPos));
        
        // 0 out appropriate places.
        /*for(int j=0; j < xLabelEnd; j++){
        	data[j][yLabelEnd-yVal-1] = 0;
        }
        for(int j=0; j < yLabelEnd; j++){
        	data[xVal][yLabelEnd-j-1] = 0;
        }*/
        bigGrid.set(elementsX[xVal].getID(), elementsY[yLabelEnd-yVal-1].getID(), 0.99);
//        data[xVal][yLabelEnd-yVal-1] = 0.99;
        
        updateData();
    }

    //return true if two strings differ at table level.
    public static boolean check(String x1, String x2){
        String sx1;
        String sx2;
        if(x1.lastIndexOf('/') == -1){ sx1 = "";
        } else{
           sx1 = x1.substring(0,x1.lastIndexOf('/'));
        }
        if(x2.lastIndexOf('/') == -1){ sx2 = "";
        } else{
           sx2 = x2.substring(0,x2.lastIndexOf('/'));
        }
        if(x1.equals(x2)) return false;
        if(x1.equals(sx2)) return false;
        if(sx1.equals(x2)) return false;
        if(sx1.equals(sx2)) return false;
        return true;
    }

    public void mousePressed(MouseEvent e){
        setLowerXY(e.getX(),e.getY());
        maxInfo = false;
        System.out.println("Pressed " + e.getX() + " " + e.getY());
    }

    public void mouseReleased(MouseEvent e){
        setBiggerXY(e.getX(),e.getY());
        System.out.println("Released " + e.getX() + " " + e.getY());
        drawRectangle = false;
        if(reallyZoom()){
        	rescale();
        	repaint();
        } else{
        	properZoom();
        	//here, zoom in by hierarchy, ie if click in green section, zoom into that section.
        }
    }
    
    boolean reallyZoom(){
    	return false;
    }

    public void mouseClicked(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){

    }

    //Mouse motion events, next 2
    public void mouseDragged(MouseEvent e){
       setMovingXY(e.getX(),e.getY());
       drawRectangle = true;
       maxInfo = false;
       repaint();
    }
  
    public void mouseMoved(MouseEvent e){
       setCurrentXY(e.getX(),e.getY());
       updateDataColors();
       drawData();
       repaint();
    }
    
    /* methods for interface SelectedInfoListener */
    
    public void selectedSchemasModified(){
    	
    }
    
    public void selectedElementsModified(Integer role){
    	
    }

    public void displayedElementModified(Integer role){
    	
    }
    
    public void selectedMappingCellsModified(){
    	
    }

    void setLowerXY(int x, int y){
       lowerX = x;
       lowerY = y;
    }

    void setBiggerXY(int x, int y){
       upperX = x;
       upperY = y;
    }

    void setMovingXY(int x, int y){
       movingX = x;
       movingY = y;
    }

    void setCurrentXY(int x, int y){
       currentX = x;
       currentY = y;
    }
    
    public interface elementObjectInterface{
    	public void swapChildren(int j, int k);
    	public void addChild(elementObject eO);
    	public int getNumChildren();
    	public elementObject getChild(int j);
    	public ArrayList<elementObject> getChildren();
    	
    }
    
    public class elementObjectRoot implements elementObjectInterface{
    	ArrayList<elementObject> children;
    	
    	public elementObjectRoot(){
    		children = new ArrayList<elementObject>();
    	}
    	
    	public void addChild(elementObject eO){
    		if(children == null){
    			children = new ArrayList<elementObject>();
    		}
    		children.add(eO);
    	}
    	
    	public int getNumChildren(){
    		if(children == null){
    			return 0;
    		}
    		else{
    			int sum =0;
    			for(elementObject eO: children){
    				sum+=eO.getNumChildren();
    			}
    			return sum;
    		}
    	}
    	
    	public void swapChildren(int j, int k){
    		elementObject t1 = children.get(j);
    		elementObject t2 = children.get(k);
    		children.set(k,t1);
    		children.set(j,t2);
    	}
    	
    	public elementObject getChild(int j){
    		return children.get(j);
    	}
    	
    	public ArrayList<elementObject> getChildren(){
    		return children;
    	}
    }
    
    //tree like schema class
    public class elementObject implements elementObjectInterface{
    	ArrayList<elementObject> children;
    	
    	//all descendants.
    	Vector<elementObject> descendants;
    	
    	//my node
    	SchemaElement myElement;
    	
    	//my name
    	String myName;
    	
    	//last position possible in automated queue.
    	int lastSpot;
    	
    	//parent
    	elementObject parent;
    	
    	//level in tree
    	int levelAt;
    	
    	//label to show
    	String label;
    	
    	public elementObject(){
    		myElement = null; children = null; descendants =null; myName = null;
    	}
    	
    	public elementObject(SchemaElement se, String s){
    		myElement = se;
    		children = null;
    		descendants = null;
    		myName = s;
    	}
    	
    	public int getID(){
    		return myElement.getId();
    	}
    	
    	public void setParent(elementObject eoi){
    		parent = eoi;
    	}
    	
    	public elementObject getParent(){
    		return parent;
    	}
    	
    	public void addChild(elementObject eO){
    		if(children == null){
    			children = new ArrayList<elementObject>();
    		}
    		children.add(eO);
    	}
    	
    	public int getNumChildren(){
    		if(children == null){
    			return 1;
    		}
    		else{
    			int sum =0;
    			for(elementObject eO: children){
    				sum+=eO.getNumChildren();
    			}
    			return sum+1;
    		}
    	}
    	
    	public int getSize(){
    		if(children == null) return 0;
    		else return children.size();
    	}
    	
    	public void swapChildren(int j, int k){
    		elementObject t1 = children.get(j);
    		elementObject t2 = children.get(k);
    		children.set(k,t1);
    		children.set(j,t2);
    	}
    	
    	public String getName(){
    		return myName;
    	}
    	
    	public elementObject getChild(int j){
    		return children.get(j);
    	}
    	
    	public Vector<elementObject> getDescendants(){
    		if(descendants != null) return descendants;
    		descendants = new Vector<elementObject>();
    		//This node has no descendants.
    		if(getSize() == 0){ descendants.add(this); return descendants; }
    		for(int j=0; j < children.size(); j++){
    			descendants.addAll(getChild(j).getDescendants());
    		}
    		return descendants;
    	}
    	
    	public void setLastSpot(int i){ lastSpot = i; }
    	public int  getLastSpot(){ return lastSpot; }
    	public void setLevel(int i){ levelAt = i; }
    	public int getLevel(){ return levelAt; }
    	public String getLabel(){ return label; }
    	public void setLabel(String i){ label = i; }
    	
    	public ArrayList<elementObject> getChildren(){
    		return children;
    	}
    }
    
    public class GridElement{
    	public int ID1;
    	public int ID2;
    	
    	public GridElement(int one, int two){
    		ID1=one;
    		ID2=two;
    	}
    	
    	@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ID1;
			result = prime * result + ID2;
			return result;
		}
    	
    	@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GridElement other = (GridElement) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (ID1 != other.ID1)
				return false;
			if (ID2 != other.ID2)
				return false;
			return true;
		}

		private HeatMap getOuterType() {
			return HeatMap.this;
		}
    }
    
    public class ScoreValue{
    	double score;
    	boolean modified;
    	
    	public ScoreValue(double s){
			score = s; modified = false;
		}
    	public ScoreValue(double s, boolean b){
    		score = s; modified = b;
    	}
		public void setScore(double s){ score = s; modified = true;}
		public double getScore(){ return score; }
		public void markAsModified(){modified = true; }
		public boolean getModified(){ return modified; }
    }
    
    public class scoreGrid{
    	Hashtable<GridElement, ScoreValue> tGrid;
    	
    	public scoreGrid(){
    		tGrid = new Hashtable<GridElement, ScoreValue>();
    	}
    	
    	public void insert(int id1, int id2, double score){
    		tGrid.put(new GridElement(id1,id2), new ScoreValue(score));
    		tGrid.put(new GridElement(id2,id1), new ScoreValue(score));
    	}
    	
    	public boolean contains(int id1, int id2){
    		if(tGrid.containsKey(new GridElement(id1,id2))){
    			return true;
    		}
    		else if(tGrid.containsKey(new GridElement(id2,id1))){
    			return true;
    		}
    		else return false;
    	}
    	
    	public double get(int id1, int id2){
    		return tGrid.get(new GridElement(id1, id2)).getScore();
    	}
    	
    	public boolean isModified(int id1, int id2){
    		return tGrid.get(new GridElement(id1, id2)).getModified();
    	}
    	
    	//change an existing value.
    	public void set(int id1, int id2, double score){
    		tGrid.put(new GridElement(id1,id2), new ScoreValue(score,true));
    		tGrid.put(new GridElement(id2,id1), new ScoreValue(score,true));
    	}
    }
    
    void properZoom(){
    	//get x, y locations in grid
    	int xVal = getCurrentXGridLoc();
    	int yVal = getCurrentYGridLoc();
    	
    	//now, figure out which table they point to.
    	int XelementSpot = xVal;
    	if(elementsX[xVal].getChildren() == null){
    		elementObject parentX = elementsX[xVal].parent;
    		int i;
    		for(i=0; i < elementsX.length; i++){
    			if(elementsX[i].equals(parentX))
    				break;
    		}
    		XelementSpot = i;
    	}
    	
    	int YelementSpot = yVal;
    	if(elementsY[yVal].getChildren() == null){
    		elementObject parentY = elementsY[yVal].parent;
    		int i;
    		for(i=0; i < elementsY.length; i++){
    			if(elementsY[i].equals(parentY))
    				break;
    		}
    		YelementSpot = i;
    	}
    	
    	elementObject xElement = elementsX[XelementSpot];
    	elementObject yElement = elementsY[YelementSpot];
    	
    	//want to both restore checkpointed data 
    	reset();
    	
    	//compute size of new in each dimension;
    	int newXSize = xElement.getDescendants().size(); 
    	int newYSize = yElement.getDescendants().size();
    	
    	//create new data array.
    	scoreGrid newBigGrid = new scoreGrid();
    	elementObject[] aElementsX = new elementObject[newXSize];
    	elementObject[] aElementsY = new elementObject[newYSize];
    	
    	Vector<elementObject> xValues = xElement.getDescendants();
    	Vector<elementObject> yValues = yElement.getDescendants();
    	
    	int SpotX=0;
    	int SpotY=0;
    	//go double nested loops through big data grid.
    	//storing in new data array.
    	for(elementObject xOb: xValues){
    		aElementsX[SpotX++] = xOb;
    		for(elementObject yOb: yValues){
    			if(bigGrid.contains(xOb.getID(), yOb.getID())){
    				newBigGrid.insert(xOb.getID(), yOb.getID(), bigGrid.get(xOb.getID(), yOb.getID()));
    			}
    		}
    	}
    	for(elementObject yOb: yValues){
    		aElementsY[SpotY++] = yOb;
    	}
    	
    	setLabels(newXSize,newYSize);
    	
    	elementsX=aElementsX;
    	elementsY=aElementsY;
    	bigGrid=newBigGrid;
    	
    	updateData();
		this.setPreferredSize(new Dimension(60+elementsX.length, 60+elementsY.length));
        this.setDoubleBuffered(true);
        drawData();
    }

    void rescale(){
       if(drawLabels){
         xLabelStart = (int) ((double) ((double)(lowerX-10))/((double)(width-10-150))*((double)elementsX.length));
         xLabelEnd = (int) ((double) ((double)(upperX-10))/((double)(width-10-150))*((double)elementsX.length));
         yLabelStart = (int) ((double) ((double)(lowerY-10))/((double)(height-10-150))*((double)elementsY.length));
         yLabelEnd = (int) ((double) ((double)(upperY-10))/((double)(height-10-150))*((double)elementsY.length));
       } else{
         xLabelStart = (int) ((double) ((double)(lowerX-10))/((double)(width-10-10))*((double)elementsX.length));
         xLabelEnd = (int) ((double) ((double)(upperX-10))/((double)(width-10-10))*((double)elementsX.length));
         yLabelStart = (int) ((double) ((double)(lowerY-10))/((double)(height-10-10))*((double)elementsY.length));
         yLabelEnd = (int) ((double)((double)(upperY-10))/((double)(height-10-10))*((double)elementsY.length));
       }
 
         int low, high;
         low = yLabelStart;
         high = yLabelEnd;
         yLabelStart = elementsY.length - high;
         yLabelEnd = elementsY.length - low;

       drawUpdatedData();
    }
}
