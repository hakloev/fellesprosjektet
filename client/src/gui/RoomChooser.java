package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controllers.LogoutException;
import models.*;

@SuppressWarnings("serial")
public class RoomChooser extends JDialog {
	
	
	//private JDialog parent;
	
	private JDialog thisDialog;
	private JPanel contentPane;
	private Room[] saveRoomHere;
	
	private JTextField capacityTextField;
	private RoomListModel roomListModel;
	private JList<Room> roomList;
	
	
	
	public RoomChooser(JDialog parent, int capacity, Room[] saveRoomHere) throws LogoutException {
		super(parent, true);
		this.setTitle("Velg rom");
		this.setResizable(false);
		this.thisDialog = this;
		this.saveRoomHere = saveRoomHere;
		//this.parent = parent;
		
		this.contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		JLabel capacityLabel = new JLabel("Minimum kapasitet");
		GridBagConstraints gbc_capacityLabel = new GridBagConstraints();
		gbc_capacityLabel.insets = new Insets(5, 5, 5, 5);
		gbc_capacityLabel.gridx = 0;
		gbc_capacityLabel.gridy = 0;
		contentPane.add(capacityLabel, gbc_capacityLabel);
		
		capacityTextField = new JTextField();
		capacityTextField.setActionCommand("capacity");
		GridBagConstraints gbc_capacityTextField = new GridBagConstraints();
		gbc_capacityTextField.insets = new Insets(0, 5, 5, 5);
		gbc_capacityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_capacityTextField.anchor = GridBagConstraints.NORTH;
		gbc_capacityTextField.gridx = 0;
		gbc_capacityTextField.gridy = 1;
		contentPane.add(capacityTextField, gbc_capacityTextField);
		capacityTextField.setText(capacity + "");
		capacityTextField.addActionListener(actionListener);
		
		JButton refreshButton = new JButton("Nytt søk");
		GridBagConstraints gbc_refreshButton = new GridBagConstraints();
		gbc_refreshButton.insets = new Insets(0, 5, 5, 5);
		//gbc_refreshButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_refreshButton.anchor = GridBagConstraints.NORTH;
		gbc_refreshButton.gridx = 0;
		gbc_refreshButton.gridy = 2;
		contentPane.add(refreshButton, gbc_refreshButton);
		refreshButton.addActionListener(actionListener);
		
		JScrollPane roomScrollPane = new JScrollPane();
		roomScrollPane.setPreferredSize(new Dimension(140, 150));
		GridBagConstraints gbc_roomScrollPane = new GridBagConstraints();
		gbc_roomScrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_roomScrollPane.gridx = 1;
		gbc_roomScrollPane.gridy = 0;
		gbc_roomScrollPane.gridheight = 3;
		contentPane.add(roomScrollPane, gbc_roomScrollPane);
		
		JLabel roomLabel = new JLabel(" Ledige rom");
		roomScrollPane.setColumnHeaderView(roomLabel);
		
		roomListModel = new RoomListModel(capacity);
		roomListModel.initialize();
		roomList = new JList<Room>(roomListModel);
		roomScrollPane.setViewportView(roomList);
		
		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.insets = new Insets(0, 5, 5, 5);
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 3;
		gbc_buttonPanel.gridwidth = 2;
		contentPane.add(buttonPanel, gbc_buttonPanel);
		
		JButton okButton = new JButton("OK");
		buttonPanel.add(okButton);
		okButton.addActionListener(actionListener);
		
		JButton cancelButton = new JButton("Avbryt");
		buttonPanel.add(cancelButton);
		cancelButton.addActionListener(actionListener);
		
		okButton.setPreferredSize(cancelButton.getPreferredSize());
		
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
	
	
	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("Nytt søk") || ae.getActionCommand().equals("capacity")) {
				try {
					roomListModel.setCapacity(Integer.parseInt(capacityTextField.getText()));
					roomListModel.refresh();
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Du må skrive inn et tall", "Feil", JOptionPane.ERROR_MESSAGE);
					capacityTextField.setText("");
				} catch (LogoutException e) {
					// TODO notify calendarView
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
				
			} else if (ae.getActionCommand().equals("OK")) {
				if (roomList.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(null, "Du må velge et rom eller trykke avbryt", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}
				saveRoomHere[0] = roomList.getSelectedValue();
				thisDialog.dispose();
				
			} else if (ae.getActionCommand().equals("Avbryt")) {
				thisDialog.dispose();
			}
		}
	};
	
}
