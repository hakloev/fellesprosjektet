package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import models.*;

@SuppressWarnings("serial")
public class ParticipantRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		ParticipantStatus status = ((Participant)value).getParticipantStatus();
		if (status == null) {
			label.setIcon(ParticipantStatus.noStatusIcon);
		} else {
			label.setIcon(status.getStatusIcon());
		}
		
		return label;
	}

}
