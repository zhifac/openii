// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.view.harmonyPane.HarmonyFrame;

/**
 * Displays the dialog for displaying general information about Harmony
 * @author CWOLF
 */
public class AboutDialog extends JDialog implements MouseListener, MouseMotionListener
{
	/** Generates info pane */
	private JPanel infoPane()
	{
		// Sets up pane with Harmony image
		JPanel imagePane = new JPanel();
		imagePane.setBackground(Color.WHITE);
		imagePane.setBorder(new LineBorder(Color.DARK_GRAY));
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/mitre/harmony/view/graphics/SSM.jpg"));
		imagePane.add(new JLabel(new ImageIcon(image.getScaledInstance(75,75,Image.SCALE_SMOOTH))));

		// Set up link to MITRE website
		JLabel linkLabel = new JLabel("<html>The MITRE Corporation, McLean, VA, USA, <a href=www.mitre.org>www.mitre.org</a></html>");
		linkLabel.addMouseListener(this);
		linkLabel.addMouseMotionListener(this);
		
		// Sets up pane with all info text
		JPanel textPane = new JPanel();
		textPane.setBorder(new EmptyBorder(10,10,0,0));
		textPane.setLayout(new BoxLayout(textPane,BoxLayout.PAGE_AXIS));
		textPane.add(new JLabel("The Harmony Integration Workbench and Schema Matcher was developed by"));
		textPane.add(linkLabel);
		textPane.add(new JLabel("\u00a9 The MITRE Corporation 2006"));
		textPane.add(new JLabel("ALL RIGHTS RESERVED"));
		
		// Sets up pane with all info
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(imagePane,BorderLayout.WEST);
		pane.add(textPane,BorderLayout.CENTER);
		return pane;
	}
	
	/** Generates description pane */
	private JPanel descriptionPane()
	{
		// Sets up the description in a text box
		JTextArea descPane = new JTextArea();
		descPane.setBorder(new EmptyBorder(10,0,0,0));
		descPane.setBackground(null);
		descPane.setLineWrap(true);
		descPane.setWrapStyleWord(true);
		descPane.setEditable(false);
		descPane.setText("The Harmony Integration Workbench is a framework for coordinating multiple " +
						 "schema integration tools.  Each such tool helps an integration engineer to " +
						 "develop an executable mapping (for example, an SQL query) for translating " +
						 "instances of a source schema into instances of a target schema.  The framework " +
						 "includes a knowledge-base shared by the integration tools and an event model " +
						 "for communication.\n\n" +
						 "The Harmony Schema Matcher includes two tools: The Harmony Engine automatically " +
						 "proposes candidate correspondences between source and target schema elements.  " +
						 "The Harmony GUI allows an integration engineer to view a set of correspondences " +
						 "and manually edit the correspondences.\n\n" +
						 "The Harmony Integration Workbench and Schema Matcher are described in Peter " +
						 "Mork, Arnon Rosenthal, Len Seligman, Joel Korb, Ken Samuel, “Integration " +
						 "Workbench: Integrating Schema Integration Tools,” Second International Workshop " +
						 "on Database Interoperability (InterDB ‘06) at the IEEE International Conference " +
						 "on Data Engineering, Atlanta, GA, April 2006.");

		// Place description into a pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(descPane,BorderLayout.CENTER);
		return pane;
	}
	
	/** Initializes About pane within About dialog */
	private JPanel aboutPane()
	{		
		JPanel aboutPane = new JPanel();
		aboutPane.setBorder(new EmptyBorder(10,10,10,10));
		aboutPane.setLayout(new BorderLayout());
		aboutPane.add(infoPane(),BorderLayout.NORTH);
		aboutPane.add(descriptionPane(),BorderLayout.CENTER);
		return aboutPane;
	}
	
	/** Initializes About dialog */
	public AboutDialog()
	{
		super(HarmonyFrame.harmonyFrame.getFrame());
		
		// Initialize all settings for the project dialog
		setTitle("About Harmony");
		setSize(600,375);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(aboutPane());
    	setLocationRelativeTo(HarmonyFrame.harmonyFrame.getFrame());
    	setVisible(true);
   	}
	
	/** Launches MITRE webpage when link is selected */
	public void mouseClicked(MouseEvent e)
	{
		try {
			Graphics g = getGraphics();
			double min = g.getFontMetrics().getStringBounds("The MITRE Corporation, McLean, VA, USA, ",g).getWidth();
			double max = min + g.getFontMetrics().getStringBounds("www.mitre.org",g).getWidth();
			if(e.getX()>min && e.getX()<max)
			{
				String os = System.getProperty("os.name");		
				if(os != null && os.startsWith("Windows"))
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.mitre.org");
				else
					Runtime.getRuntime().exec("netscape http://www.javaworld.com");
			}
		} catch(IOException e2) {}
	}
	
	/** Changes cursor to a hand whenever over web link */
	public void mouseMoved(MouseEvent e)
	{
		// Calculate out the location of the web link
		Graphics g = getGraphics();
		double min = g.getFontMetrics().getStringBounds("The MITRE Corporation, McLean, VA, USA, ",g).getWidth();
		double max = min + g.getFontMetrics().getStringBounds("www.mitre.org",g).getWidth();

		// Change the mouse cursor dependent on if mouse is over web link or not
		if(e.getX()>min && e.getX()<max)
		{
			if(getCursor()!=Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else if(getCursor()!=Cursor.getDefaultCursor())
			setCursor(Cursor.getDefaultCursor());

	}
	
	/** Always changes the mouse back to default when it exits the info component */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }
	
	// Unused mouse listener actions
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}
