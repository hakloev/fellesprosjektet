package gui.appointment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class EditAppointment extends JFrame {
	
	
	private JPanel contentPane;
	
	
	public EditAppointment() {
		this.setTitle("Rediger avtale");
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
		
		// okButtonPanel
		GridBagConstraints gbc_okButtonPanel = new GridBagConstraints();
		gbc_okButtonPanel.gridx = 0;
		gbc_okButtonPanel.gridy = 1;
		gbc_okButtonPanel.gridwidth = 2;
		contentPane.add(new OKButtonPanel(), gbc_okButtonPanel);
		
		// editButtonPanel
		GridBagConstraints gbc_editButtonPanel = new GridBagConstraints();
		gbc_editButtonPanel.gridx = 1;
		gbc_editButtonPanel.gridy = 0;
		gbc_editButtonPanel.gridheight = 2;
		gbc_editButtonPanel.anchor = GridBagConstraints.NORTH;
		contentPane.add(new EditButtonPanel(), gbc_editButtonPanel);
		
		this.pack();
	}
}
