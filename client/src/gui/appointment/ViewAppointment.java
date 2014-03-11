package gui.appointment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.ParticipantListModel;

@SuppressWarnings("serial")
public class ViewAppointment extends JFrame {
	
	
	private JPanel contentPane;
	
	
	public ViewAppointment() {
		this.setTitle("Avtalevisning");
		setLocation(100, 100);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		// detailsPanel
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridx = 0;
		gbc_detailsPanel.gridy = 0;
		contentPane.add(new DetailsPanel(new ParticipantListModel()), gbc_detailsPanel);
		
		// statusPanel
		GridBagConstraints gbc_statusPanel = new GridBagConstraints();
		gbc_statusPanel.gridx = 0;
		gbc_statusPanel.gridy = 1;
		gbc_statusPanel.anchor = GridBagConstraints.WEST;
		contentPane.add(new StatusPanel(), gbc_statusPanel);
		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 2;
		contentPane.add(new OKButtonPanel(), gbc_okButtonPanel);
		
		this.pack();
	}
}
