package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CalendarCellRenderer extends DefaultTableCellRenderer {
	
	
	
	
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		//label.setWrapStyleWord(true);
        //label.setLineWrap(true);
		
		return label;
	}
	
}
