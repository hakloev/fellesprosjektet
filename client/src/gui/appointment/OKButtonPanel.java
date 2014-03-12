package gui.appointment;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class OKButtonPanel extends JPanel implements ActionListener {
	
	
	OKButtonPanel() {
		
		JButton btnOk = new JButton("OK");
		this.add(btnOk);
		btnOk.addActionListener(this);
		
		JButton btnAvbryt = new JButton("Avbryt");
		this.add(btnAvbryt);
		btnAvbryt.addActionListener(this);
		
		JButton btnSlett = new JButton("Slett avtale");
		this.add(btnSlett);
		btnSlett.addActionListener(this);
		
		btnOK.setPreferredSize(btnSlett.getPreferredSize());
		btnAvbryt.setPreferredSize(btnSlett.getPreferredSize());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("OK")){
			JOptionPane.showMessageDialog(null, "Ny avtale lagt til i kalenderen", "Avtaleendring", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else if(actionCommand.equals("Avbryt")){
			
		}
		else if(actionCommand.equals("Slett")){
			JOptionPane.showConfirmDialog(null,
					"Er du sikker på at du vil slette avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);
		}
		
		
	}
}
