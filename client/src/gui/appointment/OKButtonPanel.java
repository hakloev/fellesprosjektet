package gui.appointment;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class OKButtonPanel extends JPanel implements ActionListener {
	
	
	JDialog parent;
	
	
	OKButtonPanel(JDialog parent) {
		this.parent = parent;
		
		JButton btnOK = new JButton("OK");
		this.add(btnOK);
		btnOK.addActionListener(this);
		
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
	public void actionPerformed(ActionEvent ae) {
		String actionCommand = ae.getActionCommand();
		
		if(actionCommand.equals("OK")){
			//JOptionPane.showMessageDialog(null, "Ny avtale lagt til i kalenderen", "Avtaleendring", JOptionPane.INFORMATION_MESSAGE);
			parent.dispose();
			
		} else if(actionCommand.equals("Avbryt")){
			parent.dispose();
			
		} else if(actionCommand.equals("Slett avtale")){
			if (parent instanceof EditAppointment) {
				int choice = JOptionPane.showConfirmDialog(null,
						"Er du sikker på at du vil slette avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					//slett avtalen
				}
				parent.dispose();
				
			} else if (parent instanceof ViewAppointment) {
				int choice = JOptionPane.showConfirmDialog(null,
						"Er du sikker på at du vil slette avtalen fra din kalender?", "Bekreft", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					//slett avtalen
				}
				parent.dispose();
			}
		}
		
	}
}
