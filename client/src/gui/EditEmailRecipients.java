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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.*;

@SuppressWarnings("serial")
public class EditEmailRecipients extends JDialog implements ActionListener, ListSelectionListener {
	
	
	private JPanel contentPane;
	
	private Appointment appointment;
	private EmailListModel tempEmailListModel;
	private JTextField emailTextField;
	private JList<String> emailList;
	private JButton deleteButton;
	
	
	public EditEmailRecipients(JDialog parent, Appointment appointment) {
		super(parent, true);
		this.setTitle("Rediger e-post mottakere");
		this.setResizable(false);
		
		this.appointment = appointment;
		
		this.contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		
		JLabel newLabel = new JLabel("Ny e-post");
		GridBagConstraints gbc_newLabel = new GridBagConstraints();
		gbc_newLabel.insets = new Insets(5, 5, 5, 5);
		gbc_newLabel.gridx = 0;
		gbc_newLabel.gridy = 0;
		contentPane.add(newLabel, gbc_newLabel);
		
		emailTextField = new JTextField();
		emailTextField.setActionCommand("email");
		GridBagConstraints gbc_capacityTextField = new GridBagConstraints();
		gbc_capacityTextField.insets = new Insets(0, 5, 5, 5);
		gbc_capacityTextField.gridx = 0;
		gbc_capacityTextField.gridy = 1;
		contentPane.add(emailTextField, gbc_capacityTextField);
		emailTextField.setColumns(15);
		emailTextField.addActionListener(this);
		
		JButton addButton = new JButton("Legg til");
		addButton.setActionCommand("email");
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 5, 5, 5);
		gbc_addButton.anchor = GridBagConstraints.EAST;
		gbc_addButton.gridx = 0;
		gbc_addButton.gridy = 2;
		contentPane.add(addButton, gbc_addButton);
		addButton.addActionListener(this);
		
		deleteButton = new JButton("Slett");
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.insets = new Insets(0, 5, 5, 5);
		gbc_deleteButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_deleteButton.gridx = 0;
		gbc_deleteButton.gridy = 3;
		contentPane.add(deleteButton, gbc_deleteButton);
		deleteButton.addActionListener(this);
		deleteButton.setEnabled(false);
		
		deleteButton.setPreferredSize(addButton.getPreferredSize());
		
		JScrollPane emailScrollPane = new JScrollPane();
		emailScrollPane.setPreferredSize(new Dimension(140, 150));
		GridBagConstraints gbc_emailScrollPane = new GridBagConstraints();
		gbc_emailScrollPane.insets = new Insets(5, 0, 5, 5);
		gbc_emailScrollPane.gridx = 1;
		gbc_emailScrollPane.gridy = 0;
		gbc_emailScrollPane.gridheight = 4;
		contentPane.add(emailScrollPane, gbc_emailScrollPane);
		
		JLabel emailLabel = new JLabel(" e-post mottakere");
		emailScrollPane.setColumnHeaderView(emailLabel);
		
		
		tempEmailListModel = new EmailListModel(appointment.getEmailRecipientsList());
		emailList = new JList<String>(tempEmailListModel);
		emailScrollPane.setViewportView(emailList);
		emailList.addListSelectionListener(this);
		
		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.insets = new Insets(0, 5, 5, 5);
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 4;
		gbc_buttonPanel.gridwidth = 2;
		contentPane.add(buttonPanel, gbc_buttonPanel);
		
		JButton okButton = new JButton("OK");
		buttonPanel.add(okButton);
		okButton.addActionListener(this);
		
		JButton cancelButton = new JButton("Avbryt");
		buttonPanel.add(cancelButton);
		cancelButton.addActionListener(this);
		
		okButton.setPreferredSize(cancelButton.getPreferredSize());
		
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		String actionCommand = ae.getActionCommand();
		
		if (actionCommand.equals("email")) {
			if (! emailTextField.getText().equals("")) { // TODO Check for valid e-mail
				tempEmailListModel.addElement(emailTextField.getText());
			}
			
		} else if (actionCommand.equals("Slett")) {
			tempEmailListModel.removeElement(emailList.getSelectedValue());
			
		} else if (actionCommand.equals("OK")) {
			appointment.setEmailRecipientsList(tempEmailListModel);
			this.dispose();
			
		} else if (actionCommand.equals("Avbryt")) {
			this.dispose();
			
		}
	}


	@Override
	public void valueChanged(ListSelectionEvent lse) {
		if (emailList.getSelectedValue() != null) {
			deleteButton.setEnabled(true);
		} else {
			deleteButton.setEnabled(false);
		}
	}

	
	
	
}


