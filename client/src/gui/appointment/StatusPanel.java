package gui.appointment;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class StatusPanel extends JPanel {
	
	
	
	StatusPanel() {
		
		JLabel lblDeltagerstatus = new JLabel("Deltagerstatus");
		this.add(lblDeltagerstatus);
		
		JComboBox comboBox = new JComboBox();
		this.add(comboBox);
		comboBox.addItem("Deltar");
		comboBox.addItem("Deltar ikke");
		
	}
	
}
