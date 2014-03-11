package gui.appointment;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class OKButtonPanel extends JPanel {
	
	
	
	OKButtonPanel() {
		
		JButton btnOk = new JButton("OK");
		this.add(btnOk);
		
		JButton btnAvbryt = new JButton("Avbryt");
		this.add(btnAvbryt);
		
		JButton btnSlett = new JButton("Slett");
		this.add(btnSlett);
	}
}
