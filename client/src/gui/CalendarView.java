package gui;

import gui.appointment.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class CalendarView extends JFrame implements ActionListener{

	private JTable table;


	/**
	 * Create the application.
	 */
	public CalendarView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setTitle("Kalender - Firma X");
		this.setResizable(false);
		this.setBounds(100, 100, 1000, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		this.getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{29, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton button = new JButton("< Forrige");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.insets = new Insets(0, 5, 0, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);
		
		JButton btnNeste = new JButton("Neste >");
		GridBagConstraints gbc_btnNeste = new GridBagConstraints();
		gbc_btnNeste.anchor = GridBagConstraints.WEST;
		gbc_btnNeste.insets = new Insets(0, 0, 0, 5);
		gbc_btnNeste.gridx = 2;
		gbc_btnNeste.gridy = 0;
		panel_1.add(btnNeste, gbc_btnNeste);
		
		JLabel lblUke = new JLabel("Uke:");
		GridBagConstraints gbc_lblUke = new GridBagConstraints();
		gbc_lblUke.anchor = GridBagConstraints.WEST;
		gbc_lblUke.insets = new Insets(0, 0, 0, 5);
		gbc_lblUke.gridx = 3;
		gbc_lblUke.gridy = 0;
		panel_1.add(lblUke, gbc_lblUke);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);
		comboBox.setPreferredSize(new Dimension(50, 20));
		comboBox.addItem(9);
		comboBox.addItem(10);
		comboBox.addItem(11);
		comboBox.addItem(12);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(2014), null, null, new Integer(1)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 5;
		gbc_spinner.gridy = 0;
		panel_1.add(spinner, gbc_spinner);
		
		JLabel lblBruker = new JLabel("Viser:");
		GridBagConstraints gbc_lblBruker = new GridBagConstraints();
		gbc_lblBruker.anchor = GridBagConstraints.WEST;
		gbc_lblBruker.insets = new Insets(0, 0, 0, 5);
		gbc_lblBruker.gridx = 6;
		gbc_lblBruker.gridy = 0;
		panel_1.add(lblBruker, gbc_lblBruker);
		
		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.anchor = GridBagConstraints.WEST;
		gbc_comboBox_1.gridx = 7;
		gbc_comboBox_1.gridy = 0;
		panel_1.add(comboBox_1, gbc_comboBox_1);
		comboBox_1.setPreferredSize(new Dimension(200, 20));
		comboBox_1.addItem("Kristian Volden");
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);
		
		JButton btnNyAvtale = new JButton("Ny avtale");
		GridBagConstraints gbc_btnNyAvtale = new GridBagConstraints();
		gbc_btnNyAvtale.insets = new Insets(0, 0, 5, 0);
		gbc_btnNyAvtale.gridx = 0;
		gbc_btnNyAvtale.gridy = 1;
		panel.add(btnNyAvtale, gbc_btnNyAvtale);
		btnNyAvtale.addActionListener(this);
		
		JButton btnAvtalevisning = new JButton("Avtalevisning");
		GridBagConstraints gbc_btnAvtalevisning = new GridBagConstraints();
		gbc_btnAvtalevisning.insets = new Insets(0, 0, 5, 0);
		gbc_btnAvtalevisning.gridx = 0;
		gbc_btnAvtalevisning.gridy = 2;
		panel.add(btnAvtalevisning, gbc_btnAvtalevisning);
		btnAvtalevisning.addActionListener(this);
		
		JButton btnSlettavtale = new JButton("Slett avtale");
		GridBagConstraints gbc_btnSlettavtale = new GridBagConstraints();
		gbc_btnSlettavtale.insets = new Insets(0, 0, 5, 0);
		gbc_btnSlettavtale.gridx = 0;
		gbc_btnSlettavtale.gridy = 3;
		panel.add(btnSlettavtale, gbc_btnSlettavtale);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		panel.add(scrollPane, gbc_scrollPane);
		
		JList list = new JList(new DefaultListModel());
		scrollPane.setViewportView(list);
		DefaultListModel listModel = (DefaultListModel) list.getModel();
		listModel.addElement("Siri avslo møteinnkalling");
		listModel.addElement("Per har invitert deg til et møte");
		listModel.addElement("Kake i kakerommet!");
		listModel.addElement("Tomt for dopapir");
		listModel.addElement("Bowling om ti minutter");
		
		JLabel lblVarsler = new JLabel("Varsler:");
		scrollPane.setColumnHeaderView(lblVarsler);
		
		JPanel panel_3 = new JPanel();
		this.getContentPane().add(panel_3, BorderLayout.CENTER);
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setPreferredSize(new Dimension(800, 433));
		table.setRowHeight(29);
		table.setGridColor(new Color(192, 192, 192));
		panel_3.add(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"},
				{"07.00", null, null, null, null, null, null, null},
				{"08.00", null, null, null, null, null, null, null},
				{"09.00", null, null, null, null, null, null, null},
				{"10.00", null, null, null, null, null, null, null},
				{"11.00", null, null, null, null, null, null, null},
				{"12.00", null, null, null, null, null, null, null},
				{"13.00", null, null, null, null, null, null, null},
				{"14.00", null, null, null, null, null, null, null},
				{"15.00", null, null, null, null, null, null, null},
				{"16.00", null, null, null, null, null, null, null},
				{"17.00", null, null, null, null, null, null, null},
				{"18.00", null, null, null, null, null, null, null},
				{"19.00", null, null, null, null, null, null, null},
				{"20.00", null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "Mandag", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		
		this.setVisible(true);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand == "Ny avtale"){
			EditAppointment editApp = new EditAppointment();
			editApp.setVisible(true);
		}
		else if(actionCommand == "Avtalevisning"){
		//frmKalenderFirma.add(editApp);
		ViewAppointment viewApp = new ViewAppointment();
		viewApp.setVisible(true);
		} 	
	}

}
