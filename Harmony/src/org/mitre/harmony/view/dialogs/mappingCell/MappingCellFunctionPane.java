//(c) The MITRE Corporation 2006
//ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.mappingCell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
* Displays the function information about the mapping cell
* @author KZheng
*/
public class MappingCellFunctionPane extends JPanel implements ActionListener, KeyListener
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;


	/** Stores the list of mapping cells to which this dialog pertains */
	private List<MappingCell> mappingCells2;
	
	// Components used within the mapping cell confidence pane
	private JCheckBox acceptCheckbox;
	private JCheckBox rejectCheckbox;
	private JTextField textField;
	private JComboBox functionComboBox;
	private JTextField formulaTextField;
	private JComboBox variableComboBox;
	
	private String currentExpression="";
	
	/** Indicates if all of the mapping cells are accepted */
	private boolean mappingCellsAccepted()
	{
		for(MappingCell mappingCell : mappingCells2)
			if(!mappingCell.getValidated() || mappingCell.getScore()!=1.0) return false;
		return true;
	}

	/** Indicates if all of the mapping cells are rejected */
	private boolean mappingCellsRejected()
	{
		for(MappingCell mappingCell : mappingCells2)
			if(!mappingCell.getValidated() || mappingCell.getScore()!=-1.0) return false;
		return true;
	}
	
	/**
	 * @return The generated function pane
	 */
	MappingCellFunctionPane(List<MappingCell> mappingCells1, HarmonyModel theHarmonyModel)
	{
		this.mappingCells2 = mappingCells1;
		
		this.harmonyModel = theHarmonyModel;
		
		//get selected schema id
		HashSet<Integer> h = harmonyModel.getProjectManager().getSchemaIDs(HarmonyConsts.LEFT);
		Iterator it = h.iterator();
		Integer schemaID = null;
	    while(it.hasNext()) {
	      Object val = it.next();      
	      schemaID = (Integer) val;  //assume only one schema on left side        
	    }

	    //get selected elements
		List<Integer> elementIdList = harmonyModel.getSelectedInfo().getSelectedElements(HarmonyConsts.LEFT);

		LinkedList variables = new LinkedList();
		
		//find the node names for selected elements
		for(int i=0; i < elementIdList.size(); i++)
		{
			SchemaInfo schemaInfo = harmonyModel.getSchemaManager().getSchemaInfo(schemaID);
			variables.add(schemaInfo.getDisplayName(elementIdList.get(i)));		
		}
				
		// Set up functionSelection info pane
		JPanel functionSelectionPane = new JPanel();
		functionSelectionPane.setLayout(new GridLayout(1,2));
	
		JLabel selectFunctionLabel = new JLabel("  Built-in Function:");
		selectFunctionLabel.setFont(new Font("Arial", Font.BOLD, 11));
		//functionSelectionPane.add(selectFunctionLabel);
			
		//set up combobox for function
		String[] functionNames = { "SUM(arg1, arg2)", "CAT(arg1, arg2)", "MUL(arg1, arg2)", "ADD(arg1, arg2)", "SUB(arg1, arg2)", "Add new function" };
		functionComboBox = new JComboBox(functionNames);
		functionComboBox.setBackground(Color.WHITE);
		functionComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
		functionComboBox.addActionListener(this);
		
		//variable input
		JLabel selectVariableLabel = new JLabel("  Variables:");
		selectVariableLabel.setFont(new Font("Arial", Font.BOLD, 11));
		//functionSelectionPane.add(selectFunctionLabel);
			
		//set up combobox for variable
		String[] variableNames = new String[variables.size()];
		for(int i=0; i<variables.size();i++){
			variableNames[i] = (String)variables.get(i);
		}
			
		variableComboBox = new JComboBox(variableNames);
		variableComboBox.addActionListener(this);
		variableComboBox.addKeyListener(this);
		variableComboBox.setBackground(Color.WHITE);
		variableComboBox.setFont(new Font("Arial", Font.PLAIN, 10));

		
		//functionComboBox.setMaximumRowCount(50);
		JPanel functionComboBoxPane = new JPanel(new FlowLayout());
		functionComboBoxPane.add(selectFunctionLabel);
		functionComboBoxPane.add(functionComboBox);
		functionComboBoxPane.add(selectVariableLabel);
		functionComboBoxPane.add(variableComboBox);
		
		functionSelectionPane.add(functionComboBoxPane);
		
		formulaTextField = new JTextField("Node C=");
		formulaTextField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		formulaTextField.setColumns(25);
		formulaTextField.setSize(20, 6);
		
		formulaTextField.addKeyListener(this);
		
		currentExpression = formulaTextField.getText();
		
		JPanel functionDisplayPane = new JPanel(new FlowLayout());
		
		JButton editButton = new JButton("Edit...");
		
		JLabel functionFormulaLabel = new JLabel("  Function Expression:");
		functionFormulaLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		functionFormulaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		functionFormulaLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		
		functionDisplayPane.add(formulaTextField);
		functionDisplayPane.add(editButton);
		
		

		
		// Set up confidence pane
		setBorder(BorderFactory.createTitledBorder("Mapping Function"));
		//setLayout(new GridLayout(2,1));
		//add(functionSelectionPane);
		//add(functionGridPane);
		
		setLayout(new BorderLayout());
		add(functionSelectionPane,BorderLayout.NORTH);
		add(functionDisplayPane,BorderLayout.SOUTH);
		//add(checkboxPane,BorderLayout.SOUTH);				
		
		
	}

	/** Indicates if the mapping cells have been accepted */
	boolean isAccepted()
		{ return acceptCheckbox.isSelected(); }
	
	/** Indicates if the mapping cells have been rejected */
	boolean isRejected()
		{ return rejectCheckbox.isSelected(); }
	
	/** Handles the pressing of dialog buttons */
	public void actionPerformed(ActionEvent e)
	{
		// If "Accept" checkbox chosen, unselect "Reject"
		if(e.getSource()==acceptCheckbox)
		{
			if(acceptCheckbox.isSelected()) rejectCheckbox.setSelected(false);
			else if(mappingCellsAccepted() || mappingCellsRejected()) acceptCheckbox.setSelected(true);
		
			//redraw mapping cell lines
			//reDrawMappingCellLines();
		}
		
		// If "Reject" checkbox chosen, unselect "Accept"
		if(e.getSource()==rejectCheckbox)
		{
			if(rejectCheckbox.isSelected()) acceptCheckbox.setSelected(false);
			else if(mappingCellsAccepted() || mappingCellsRejected()) rejectCheckbox.setSelected(true);
		}
		
		// function combobox
		if(e.getSource()==functionComboBox)
		{
			String funName = (String)functionComboBox.getSelectedItem();
			if(funName=="Add new function")
			{
			  //do something here to add a new function
			}
			else
			{
				funName = funName.substring(0, funName.indexOf("("));
		        System.out.println("function Name=" + funName);
		        addToExpression(funName+"(");
		        formulaTextField.requestFocus();
			}
		}
		
		// variable combobox
		if(e.getSource()==variableComboBox)
		{
			String varName = (String)variableComboBox.getSelectedItem();
	        System.out.println("varName=" + varName);
	        addToExpression(varName);
	        formulaTextField.requestFocus();
		}
		
		
	}
	
	
	//add item to function expression
	public void addToExpression(String item){
		currentExpression+=item;
		formulaTextField.setText(currentExpression);
		
	}
	
	/** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
    	//displayInfo(e, "KEY TYPED: ");
    	    	
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
	//displayInfo(e, "KEY PRESSED: ");
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {
	//displayInfo(e, "KEY RELEASED: ");
    	int id = e.getID();
    	if (id == KeyEvent.KEY_RELEASED) {
	    	currentExpression = formulaTextField.getText();
	    	System.out.println("Release currentExpression=" + currentExpression);
	    	formulaTextField.setText(currentExpression);
    	}
    }
    
    //get type character
    String getTypedCharacter(KeyEvent e)
    {
    	int id = e.getID();
        String keyString ="";
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString =c + "";
        };
        
        return keyString;
    }
    
    //For testing purpose
    private void displayInfo(KeyEvent e, String keyStatus){
        
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }
        
        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }
        
        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }
        
        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }
        
        //Display information about the KeyEvent...
        
        System.out.println(keyString);
        System.out.println(modString);
        System.out.println(locationString);
        System.out.println(actionString);

    }

    //Redw
    //reDrawMappingCellLines()
    {
    	
    }
}

