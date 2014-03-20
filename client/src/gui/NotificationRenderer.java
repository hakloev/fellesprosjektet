package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import models.Notification;

@SuppressWarnings("serial")
public class NotificationRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if (! ((Notification)value).isSeen() ) {
			label.setBackground(Color.RED);
			label.setForeground(Color.BLACK);
		}
		
		return label;
	}

}
