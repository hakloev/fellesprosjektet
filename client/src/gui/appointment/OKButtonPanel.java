package gui.appointment;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class OKButtonPanel extends JPanel {
	
	
	OKButtonPanel() {
		
		JButton btnOK = new JButton("OK");
		this.add(btnOK);
		
		JButton btnAvbryt = new JButton("Avbryt");
		this.add(btnAvbryt);
		
		JButton btnSlett = new JButton("Slett avtale");
		this.add(btnSlett);
		
		btnOK.setPreferredSize(btnSlett.getPreferredSize());
		btnAvbryt.setPreferredSize(btnSlett.getPreferredSize());
	}
}
