package org.mitre.harmony.view.heatmap;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 */

public class HeatMap extends JPanel implements MouseListener,KeyListener, MouseMotionListener
{
    private double[][] data;
    private String[] labelsX;
    private String[] labelsY;
    private int numLabelsX;
    private int numLabelsY;
    private Color[][] dataColors;

    // these four variables are used to print the axis labels
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

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

    private String title;
    private String xAxis;
    private String yAxis;

    private int height;
    private int width;

    private int xLabelStart;
    private int xLabelEnd;
    private int yLabelStart;
    private int yLabelEnd;

    private boolean drawTitle = false;
    private boolean drawXTitle = false;
    private boolean drawYTitle = false;
    private boolean drawLegend = false;
    private boolean drawXTicks = false;
    private boolean drawYTicks = false;

    private Color[] colors;
    private Color bg = Color.white;
    private Color fg = Color.black;

    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;
    
    /**
     * Produces a gradient from blue (low) to red (high)
     */
    public final static Color[] GRADIENT_BLUE_TO_RED = createGradient(Color.BLUE, Color.RED, 500);

    /**
     * Produces a gradient from black (low) to white (high)
     */
    public final static Color[] GRADIENT_BLACK_TO_WHITE = createGradient(Color.BLACK, Color.WHITE, 500);

    /**
     *Produces a gradient from red (low) to green (high)
     */
    public final static Color[] GRADIENT_RED_TO_GREEN = createGradient(Color.RED, Color.GREEN, 500);

    /**
     *Produces a gradient through green, yellow, orange, red
     */
    public final static Color[] GRADIENT_GREEN_YELLOW_ORANGE_RED = createMultiGradient(new Color[]{Color.green, Color.yellow, Color.orange, Color.red}, 500);

    /**
     *Produces a gradient through the rainbow: violet, blue, green, yellow, orange, red
     */
    public final static Color[] GRADIENT_RAINBOW = createMultiGradient(new Color[]{new Color(181, 32, 255), Color.blue, Color.green, Color.yellow, Color.orange, Color.red}, 500);

    /**
     *Produces a gradient for hot things (black, red, orange, yellow, white)
     */
    public final static Color[] GRADIENT_HOT = createMultiGradient(new Color[]{Color.black, new Color(87, 0, 0), Color.red, Color.orange, Color.yellow, Color.white}, 500);

    /**
     *Produces a different gradient for hot things (black, brown, orange, white)
     */
    public final static Color[] GRADIENT_HEAT = createMultiGradient(new Color[]{Color.black, new Color(105, 0, 0), new Color(192, 23, 0), new Color(255, 150, 38), Color.white}, 500);

    /**
     *Produces a gradient through red, orange, yellow
     */
    public final static Color[] GRADIENT_ROY = createMultiGradient(new Color[]{Color.red, Color.orange, Color.yellow}, 500);



    /**
     * @param data The data to display, must be a complete array (non-ragged)
     * @param colors A variable of the type Color[]. See also {@link #createMultiGradient} and {@link #createGradient}.
     */
    public HeatMap(double[][] data, Color[] colors)
    {
        super();

        this.data = data;
        updateGradient(colors);
        updateData(data);

        this.setPreferredSize(new Dimension(60+data.length, 60+data[0].length));
        this.setDoubleBuffered(true);

        this.bg = Color.white;
        this.fg = Color.black;
        
        // this is the expensive function that draws the data plot into a 
        // BufferedImage. The data plot is then cheaply drawn to the screen when
        // needed, saving us a lot of time in the end.
        drawData();
    }

    public void setLabels(String[] xLabels, String[] yLabels, int numX, int numY){
       labelsX = xLabels;
       labelsY = yLabels;
       numLabelsX = numX;
       numLabelsY = numY;
       xLabelStart =0;
       xLabelEnd =numX;
       yLabelStart =0;
       yLabelEnd =numY;
    }

    /**
     * Specify the coordinate bounds for the map. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param xMin The lower bound of x-values, used for axis labels
     * @param xMax The upper bound of x-values, used for axis labels
     */
    public void setCoordinateBounds(double xMin, double xMax, double yMin, double yMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        
        repaint();
    }


    /**
     * Specify the coordinate bounds for the X-range. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param xMin The lower bound of x-values, used for axis labels
     * @param xMax The upper bound of x-values, used for axis labels
     */
    public void setXCoordinateBounds(double xMin, double xMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        
        repaint();
    }

    /**
     * Specify the coordinate bounds for the Y-range. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param yMin The lower bound of y-values, used for axis labels
     * @param yMax The upper bound of y-values, used for axis labels
     */
    public void setYCoordinateBounds(double yMin, double yMax)
    {
        this.yMin = yMin;
        this.yMax = yMax;
        
        repaint();
    }

    /**
     * Updates the title. Calls repaint() when finished.
     * @param title The new title
     */
    public void setTitle(String title)
    {
        this.title = title;
        
        repaint();
    }

    /**
     * Updates the state of the title. Calls repaint() when finished.
     * @param drawTitle Specifies if the title should be drawn
     */
    public void setDrawTitle(boolean drawTitle)
    {
        this.drawTitle = drawTitle;
        
        repaint();
    }

    /**
     * Updates the X-Axis title. Calls repaint() when finished.
     * @param xAxisTitle The new X-Axis title
     */
    public void setXAxisTitle(String xAxisTitle)
    {
        this.xAxis = xAxisTitle;
        
        repaint();
    }

    /**
     * Updates the state of the X-Axis Title. Calls repaint() when finished.
     * @param drawXAxisTitle Specifies if the X-Axis title should be drawn
     */
    public void setDrawXAxisTitle(boolean drawXAxisTitle)
    {
        this.drawXTitle = drawXAxisTitle;
        
        repaint();
    }

    /**
     * Updates the Y-Axis title. Calls repaint() when finished.
     * @param yAxisTitle The new Y-Axis title
     */
    public void setYAxisTitle(String yAxisTitle)
    {
        this.yAxis = yAxisTitle;
        
        repaint();
    }

    /**
     * Updates the state of the Y-Axis Title. Calls repaint() when finished.
     * @param drawYAxisTitle Specifies if the Y-Axis title should be drawn
     */
    public void setDrawYAxisTitle(boolean drawYAxisTitle)
    {
        this.drawYTitle = drawYAxisTitle;
        
        repaint();
    }


    /**
     * Updates the state of the legend. Calls repaint() when finished.
     * @param drawLegend Specifies if the legend should be drawn
     */
    public void setDrawLegend(boolean drawLegend)
    {
        this.drawLegend = drawLegend;
        
        repaint();
    }

    /**
     * Updates the state of the X-Axis ticks. Calls repaint() when finished.
     * @param drawXTicks Specifies if the X-Axis ticks should be drawn
     */
    public void setDrawXTicks(boolean drawXTicks)
    {
        this.drawXTicks = drawXTicks;
        
        repaint();
    }

    /**
     * Updates the state of the Y-Axis ticks. Calls repaint() when finished.
     * @param drawYTicks Specifies if the Y-Axis ticks should be drawn
     */
    public void setDrawYTicks(boolean drawYTicks)
    {
        this.drawYTicks = drawYTicks;
        
        repaint();
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
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[0].length; y++)
            {
                largest = Math.max(data[x][y], largest);
                smallest = Math.min(data[x][y], smallest);
            }
        }
        double range = largest - smallest;

        // dataColors is the same size as the data array
        dataColors = new Color[data.length][data[0].length];    

        //assign a Color to each data point
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[0].length; y++)
            {
                double norm = (data[x][y] - smallest) / range; // 0 < norm < 1
                int color = (int) Math.floor(norm * (colors.length - 1));
                dataColors[x][y] = colors[color];
            }
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
    public void updateData(double[][] data)
    {
        this.data = data;

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
        bufferedImage = new BufferedImage(data.length,data[0].length, BufferedImage.TYPE_INT_RGB);
        bufferedGraphics = bufferedImage.createGraphics();
        
        for (int x = 0; x < data.length; x++)
        {
            for (int y = data[0].length - 1; y >= 0; y--)
            {
                bufferedGraphics.setColor(dataColors[x][y]);
                bufferedGraphics.fillRect( 
                        (int)Math.ceil(x), 
                        (int)Math.ceil(data[0].length - y - 1),
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
           int xDist = (int) ((width-160)/(double) (xLabelEnd-xLabelStart));
           double xDDist = ((width-160)/(double) (xLabelEnd-xLabelStart));
            g2d.rotate(Math.PI / 2);
            for (int x = xLabelStart; x < xLabelEnd; x++){
                g2d.drawString(labelsX[x], height-150, -10-((int)(xDDist*(x-xLabelStart))));
		if(x != 0){
                   if(check(labelsX[x], labelsX[x-1])){
                     g2d.drawLine(10,-10-((int)(xDDist*(x-xLabelStart))),((int)(height - 150)), -10-((int)(xDDist*(x-xLabelStart))));
                   }
                }
            }
            g2d.rotate( -Math.PI / 2);

           int yDist = (int) ((height-160)/(double) (yLabelEnd-yLabelStart));
           double yDDist = ((height-160)/(double) (yLabelEnd-yLabelStart));
            for (int y = yLabelStart; y < yLabelEnd; y++){
                g2d.drawString(labelsY[y], width-150, height-150-((int)(yDDist*(y-yLabelStart))));
		if(y != 0){
                   if(check(labelsY[y], labelsY[y-1])){
                     g2d.drawLine(10,height-150-((int)(yDDist*(y-yLabelStart))),((int)(width - 150)), height-150-((int)(yDDist*(y-yLabelStart))));
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
           int xDist = (int) ((width-20)/(double) (xLabelEnd-xLabelStart));
           double xDDist = ((width-20)/(double) (xLabelEnd-xLabelStart));
            g2d.rotate(Math.PI / 2);
            for (int x = xLabelStart; x < xLabelEnd; x++){
		if(x != 0){
                   if(check(labelsX[x], labelsX[x-1])){
                     g2d.drawLine(10,-10-((int)(xDDist*(x-xLabelStart))),((int)(height - 10)), -10-((int)(xDDist*(x-xLabelStart))));
                   }
                }
            }
            g2d.rotate( -Math.PI / 2);

           int yDist = (int) ((height-20)/(double) (yLabelEnd-yLabelStart));
           double yDDist = ((height-20)/(double) (yLabelEnd-yLabelStart));
            for (int y = yLabelStart; y < yLabelEnd; y++){
		if(y != 0){
                   if(check(labelsY[y], labelsY[y-1])){
                     g2d.drawLine(10,height-10-((int)(yDDist*(y-yLabelStart))),((int)(width - 10)), height-10-((int)(yDDist*(y-yLabelStart))));
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
        //g2d.setColor(Color.gray);
        //g2d.fillRect(currentX,currentY,currentX+50,currentY+50);
        g2d.setColor(Color.white);
        double xPos = ((double)(currentX-10)/(double) (xLabelEnd-xLabelStart));
        double yPos = ((double)(currentY-10)/(double) (yLabelEnd-yLabelStart));
        int xVal = (int) ((currentX-10)/(double) (xPos));
        g2d.drawString(labelsX[xVal], currentX+10, currentY+10);
    }

    public void drawMovingRectangle(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(lowerX,lowerY,lowerX,movingY);
        g2d.drawLine(lowerX,lowerY,movingX,lowerY);
        g2d.drawLine(movingX,lowerY,movingX,movingY);
        g2d.drawLine(lowerX,movingY,movingX,movingY);
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
        rescale();
        repaint();
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
       if(maxInfo == true){
         repaint();
       }
    }

    public void keyTyped(KeyEvent e){
    }


    public void keyReleased(KeyEvent e){
 
    }

    public void keyPressed(KeyEvent e){
       switch(e.getKeyCode()){
        case KeyEvent.VK_T: 
            drawLabels=false;
            setLowerXY(0,0);
            setBiggerXY(width,height); 
            rescale(); 
            repaint(); 
            break;
        case KeyEvent.VK_I: 
            if(maxInfo==false){
               maxInfo = true;
            } else{
               maxInfo = false;
               repaint();
            }
            break;
        default:
          if(drawLabels == true) drawLabels =false;
          else drawLabels = true;
          System.out.println("Clicked");
          repaint();
       }
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

    void rescale(){
       if(drawLabels){
         System.out.println("Height1 " + height + " width1 " + width);
         System.out.println("LowerX " + lowerX + " UpperX " + upperX);
         System.out.println("LowerY " + lowerY + " UpperY " + upperY);
         System.out.println("NumLabelsX " + numLabelsX + " NumLabelsY " + numLabelsY);
         float lX = (float) lowerX-10;
         float wX = (float) width-10-150;
         float nX = (float) numLabelsX;
         xLabelStart = (int) ((double) ((double)(lowerX-10))/((double)(width-10-150))*((double)numLabelsX));
         xLabelEnd = (int) ((double) ((double)(upperX-10))/((double)(width-10-150))*((double)numLabelsX));
         yLabelStart = (int) ((double) ((double)(lowerY-10))/((double)(height-10-150))*((double)numLabelsY));
         yLabelEnd = (int) ((double) ((double)(upperY-10))/((double)(height-10-150))*((double)numLabelsY));
       } else{
         System.out.println("Height2 " + height + " width2 " + width);
         System.out.println("LowerX " + lowerX + " UpperX " + upperX);
         System.out.println("LowerY " + lowerY + " UpperY " + upperY);
         System.out.println("NumLabelsX " + numLabelsX + " NumLabelsY " + numLabelsY);
         xLabelStart = (int) ((double) ((double)(lowerX-10))/((double)(width-10-10))*((double)numLabelsX));
         xLabelEnd = (int) ((double) ((double)(upperX-10))/((double)(width-10-10))*((double)numLabelsX));
         yLabelStart = (int) ((double) ((double)(lowerY-10))/((double)(height-10-10))*((double)numLabelsY));
         yLabelEnd = (int) ((double)((double)(upperY-10))/((double)(height-10-10))*((double)numLabelsY));
       }
 
         int low, high;
         low = yLabelStart;
         high = yLabelEnd;
         yLabelStart = numLabelsY - high;
         yLabelEnd = numLabelsY - low;

       System.out.println("X Start, End " + xLabelStart + ", " + xLabelEnd);
       System.out.println("Y Start, End " + yLabelStart + ", " + yLabelEnd);
       drawUpdatedData();
    }
}
