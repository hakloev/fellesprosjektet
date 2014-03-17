package gui.appointment;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Appointment;
import models.Participant;

@SuppressWarnings("serial")
class OKButtonPanel extends JPanel implements ActionListener {
	
	
	private JDialog parent;
	private Appointment appointment;
	private Participant currentUser;
	
	
	OKButtonPanel(JDialog parent, Appointment appointment, Participant currentUser) {
		this.parent = parent;
		this.appointment = appointment;
		this.currentUser = currentUser;
		
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
			if (parent instanceof EditAppointment) {
				appointment.save();
				/* TODO
				 * ID for new appointment
				 * show in calendar
				 */
				
			} else if (parent instanceof ViewAppointment) {
				// TODO code for saving participantstatus & alarm & if shown in calendar
			}
			parent.dispose();
			
		} else if(actionCommand.equals("Avbryt")){
			if (appointment.getAppointmentID() != 0) {
				appointment.refresh(); // TODO Implement proper cancel logic instead of using refresh
			}
			parent.dispose();
			
		} else if(actionCommand.equals("Slett avtale")){
			if (parent instanceof EditAppointment) {
				int choice = JOptionPane.showConfirmDialog(null,
						"Er du sikker på at du vil slette avtalen?", "Bekreft", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					// TODO slett avtalen fra kalenderen
					appointment.delete();
					parent.dispose();
				}
				
			} else if (parent instanceof ViewAppointment) {
				int choice = JOptionPane.showConfirmDialog(null,
						"Er du sikker på at du vil slette avtalen fra din kalender?", "Bekreft", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					//TODO slett avtalen fra kalenderen
					appointment.setShowInCalendar(false);
					currentUser.setShowInCalendar(false);
					parent.dispose();
				}
			}
		}
		
	}
}
