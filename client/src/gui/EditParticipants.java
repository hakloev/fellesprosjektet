package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EditParticipants extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	public static void client(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditParticipants frame = new EditParticipants();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public EditParticipants() {
		//setBounds(100, 100, 500, 301);
		setLocation(100, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{80, 80, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(120, 0));
		
		JLabel lblGrupper = new JLabel("Grupper");
		scrollPane.setColumnHeaderView(lblGrupper);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JButton button = new JButton("->");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridheight = 2;
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		contentPane.add(button, gbc_button);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(120, 0));
		
		JLabel lblDeltagere = new JLabel("Deltagere");
		scrollPane_1.setColumnHeaderView(lblDeltagere);
		
		JList list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 0;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		JButton button_1 = new JButton("<-");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 3;
		gbc_button_1.gridy = 0;
		contentPane.add(button_1, gbc_button_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setPreferredSize(new Dimension(120, 0));
		
		JLabel lblAnsatte = new JLabel("Ansatte");
		scrollPane_2.setColumnHeaderView(lblAnsatte);
		
		JList list_2 = new JList();
		scrollPane_2.setViewportView(list_2);
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_2.gridheight = 2;
		gbc_scrollPane_2.gridx = 4;
		gbc_scrollPane_2.gridy = 0;
		contentPane.add(scrollPane_2, gbc_scrollPane_2);
		
		JButton button_2 = new JButton("->");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 3;
		gbc_button_2.gridy = 1;
		contentPane.add(button_2, gbc_button_2);
		
		JPanel panel = new JPanel();
		
		JButton btnOk = new JButton("OK");
		panel.add(btnOk);
		
		JButton btnAvbryt = new JButton("Avbryt");
		panel.add(btnAvbryt);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		
		this.pack();
	}
}
