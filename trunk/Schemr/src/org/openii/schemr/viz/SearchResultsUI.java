package org.openii.schemr.viz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openii.schemr.MatchSummary;

public class SearchResultsUI extends JFrame {
	
	private static final Logger logger = Logger.getLogger(SearchResultsUI.class.getName());

	JPanel sidePanel;
	JPanel mainPanel;
	Visualizer v;
	
	MatchSummary[] msarray;
	
	public SearchResultsUI(MatchSummary[] msarray) {
		
        this.setTitle("Schemr");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		this.msarray = msarray;
		this.sidePanel = createSidePanel();
		this.sidePanel.setSize(200, 768);
		this.mainPanel = new JPanel();
		this.mainPanel.setSize(1024, 768);
		
		this.setSize(1224, 768);
		this.setLayout(new BorderLayout());
		this.add(sidePanel, BorderLayout.WEST);
		this.add(mainPanel, BorderLayout.EAST);

		update(msarray[0]);
	}
	
	private void update(MatchSummary ms) {
		try {
			this.v = new PrefuseVisualizer(ms);
			mainPanel.removeAll();
			mainPanel.add(v.getDisplayComponent());
			this.pack();
			this.setVisible(true);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot connecto SchemaStore", e);
		}
	}

	private JPanel createSidePanel() {
		
		JPanel sidePanel = new JPanel();
		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		
		for (final MatchSummary ms : this.msarray) {
			
			String name = ms.getSchema().getName();
			JButton jb = new JButton(name);
			
			jb.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						update(ms);					
					}
				}
			);
			
			sidePanel.add(jb);
		}
		return sidePanel;
	}

}
