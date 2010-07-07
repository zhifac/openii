/**
 * 
 */
package org.mitre.harmony.view.dialogs.mappingCell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.MappingManager;
import org.mitre.harmony.view.dialogs.widgets.AbstractButtonPane;
import org.mitre.schemastore.model.MappingCell;


/**
 * @author KZHENG
 *
 */
public class AddNewFunction extends JDialog implements MouseListener, MouseMotionListener, ActionListener {

	/**
	 * 
	 */
	public AddNewFunction() {
		super();
		// TODO Auto-generated constructor stub
	}

		/** Stores the Harmony model */
		private HarmonyModel harmonyModel;

		/** Stores the annotation pane */
		private MappingCellAnnotationPane annotationPane;
		
		/** Stores the confidence pane */
		private MappingCellConfidencePane confidencePane;
		
		private MappingCellFunctionPane functionPane;

		/** Stores the list of mapping cells to which this dialog pertains */
		private List<MappingCell> mappingCells;
		
		
		public ButtonPane btPane;
		
		private boolean usingFunction = false;
		
		/** Private class for defining the button pane */
		private class ButtonPane extends AbstractButtonPane
		{
			/** Constructs the button pane */
			public ButtonPane()	{ super(new String[]{"OK", "Cancel"},1,2); 
			}

			/** Handles selection of button */
			protected void buttonPressed(String label)
			{
				if(label.equals("OK"))
				{
					//For confience setting
					if(!usingFunction){
						// Throw out mapping cells if they have been rejected
						if(confidencePane.isRejected()){
							harmonyModel.getMappingManager().deleteMappingCells(mappingCells);
						}
						else
						{			
							// Adjust the mapping cells to reflect changes
							ArrayList<MappingCell> newMappingCells = annotationPane.getMappingCells();
							harmonyModel.getMappingManager().setMappingCells(newMappingCells);
							
							// If the mapping cells were accepted, validate as needed
							if(confidencePane.isAccepted()){
								harmonyModel.getMappingManager().validateMappingCells(newMappingCells);
							}
						}
					}
					else{
						if(functionPane.getFunctionName()==null||functionPane.getFunctionName()==""){
							//No function selected
							System.out.println("No function selected.");									
						}
						else{
							//using the function matching
							//generate a new function mapping cell
							MappingManager manager = harmonyModel.getMappingManager();
				
							//get the mapping ID:
							Integer mappingId = mappingCells.get(0).getMappingId();			
							//System.out.println("mappingID=" + mappingId);
							String author = System.getProperty("user.name");
							Date date = Calendar.getInstance().getTime();
							//String function = IdentityFunction.class.getCanonicalName();
							String function = functionPane.getFunctionName();
							System.out.println("FuncName=" + function);
							Integer[] inputs = functionPane.getInputs();
							Integer output = functionPane.getOutput();
							Integer functionID = functionPane.getFunctionId();
							
							System.out.println("output=" + output);
							
							//delete mapping cells
							harmonyModel.getMappingManager().deleteMappingCells(mappingCells);
							
							//create a function mappingcell
							MappingCell mappingCell = MappingCell.createFunctionMappingCell(null, mappingId, inputs, output, functionID, author, date, null);    	
							
							//MappingCell mappingCell = MappingCell.createValidatedMappingCell(id, mappingID, new Integer[]{leftID}, rightID, author, date, function, null);
							manager.getMapping(mappingId).setMappingCells(Arrays.asList(new MappingCell[]{mappingCell}));	
						}
					}
				}
					
				
				// Close link dialog
				dispose();
			}
		}
		
		/** Initializes the mapping cell dialog */
		public AddNewFunction(List<MappingCell> mappingCells, HarmonyModel harmonyModel)
		{
			super(harmonyModel.getBaseFrame());
			
			// Initialize the selected links
			this.mappingCells = mappingCells;
			this.harmonyModel = harmonyModel;
			
			// Set up the main dialog pane
			
			
			JPanel pane = new JPanel();
			pane.setName("Add new function");
			pane.setBorder(BorderFactory.createTitledBorder("Add New Function"));
			pane.setBorder(new LineBorder(Color.black, 1, false));
			pane.setLayout(new GridLayout(6,0));

			final JPanel panel = new JPanel();
			pane.add(panel);

			final JLabel funcNameLabel = new JLabel();
			panel.add(funcNameLabel);
			funcNameLabel.setText("Function Name:");

			final JTextField textField = new JTextField();
			textField.setColumns(15);
			panel.add(textField);

			final JPanel panel_1 = new JPanel();
			pane.add(panel_1);

			final JLabel funcDetailLabel = new JLabel();
			funcDetailLabel.setText("Function Detail:");
			panel_1.add(funcDetailLabel);

			final JTextField textField_1 = new JTextField();
			textField_1.setColumns(45);
			panel_1.add(textField_1);

			final JPanel panel_2 = new JPanel();
			pane.add(panel_2);
			
			//Function
			JLabel selectExistFunction = new JLabel("Existing Functions:", SwingConstants.RIGHT);
			panel_2.add(selectExistFunction);
			selectExistFunction.setFont(new Font("Arial", Font.BOLD, 11));
			
			
			JComboBox existFunctionList = new JComboBox();
			existFunctionList.setPreferredSize(new Dimension(200, 20));
			panel_2.add(existFunctionList);

			final JPanel panel_3 = new JPanel();
			pane.add(panel_3);

			final JLabel existingVariablesLabel = new JLabel();
			existingVariablesLabel.setText("Existing Variables:");
			panel_3.add(existingVariablesLabel);

			final JComboBox comboBox = new JComboBox();
			comboBox.setPreferredSize(new Dimension(200, 20));
			panel_3.add(comboBox);

			btPane = new ButtonPane();
			pane.add(btPane);

			
			// Set up ability to escape out of dialog
			Action escape = new AbstractAction() { public void actionPerformed(ActionEvent arg0) { dispose(); } };
			pane.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE),"escape");
			pane.getActionMap().put("escape", escape);
			
			// Initialize the dialog parameters
			setModal(true);
			setResizable(false);
			setUndecorated(true);
			setContentPane(pane);
			pack();
			
			// Listen for mouse actions to move the dialog pane
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		//-----------------------------------------------------------------
		// Purpose: Handles the movement of the dialog box by mouse actions
		//-----------------------------------------------------------------
		private Point mousePosition = null;
		public void mousePressed(MouseEvent e) { mousePosition=e.getPoint(); }
		public void mouseDragged(MouseEvent e)
		{
			if(mousePosition!=null)
			{
				int xShift=e.getPoint().x-mousePosition.x;
				int yShift=e.getPoint().y-mousePosition.y;
				setLocation(getLocation().x+xShift,getLocation().y+yShift);
			}
		}
		public void mouseReleased(MouseEvent e) { mousePosition=null; }
		public void mouseClicked(MouseEvent e) {}	
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="function"){
				//set function panel enabled
				usingFunction = true;
				functionPane.setDisable(false);
				confidencePane.setDisable(true);
				btPane.setEnabled(0, false); //set the OK button to dis enabled. 
			}
			else{
				//set confidence panel enabled
				usingFunction = false;
				functionPane.setDisable(true);
				confidencePane.setDisable(false);
				btPane.setEnabled(0, true); //set the OK button to enabled.
			}
		}
		
		//set buttonPane OK enabled.
		public void setButtonPaneOK(){
			btPane.setEnabled(0, true);
		}
		
		//set buttonPane disabled.
		public void setButtonPaneOKDisabled(){
			btPane.setEnabled(0, false);
		}
		
	}
