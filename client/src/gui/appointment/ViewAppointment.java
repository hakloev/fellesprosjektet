package gui.appointment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ViewAppointment extends JFrame {
	
	
	
	public ViewAppointment() {
		setLocation(100, 100);
		GridBagLayout gbl = new GridBagLayout();
		this.getContentPane().setLayout(gbl);
		
		// detailsPanel
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridx = 0;
		gbc_detailsPanel.gridy = 0;
		this.getContentPane().add(new DetailsPanel(), gbc_detailsPanel);
		
		// statusPanel
		GridBagConstraints gbc_statusPanel = new GridBagConstraints();
		gbc_statusPanel.gridx = 0;
		gbc_statusPanel.gridy = 1;
		gbc_statusPanel.anchor = GridBagConstraints.WEST;
		this.getContentPane().add(new StatusPanel(), gbc_statusPanel);
		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 2;
		this.getContentPane().add(new OKButtonPanel(), gbc_okButtonPanel);
		
		this.pack();
	}
}
