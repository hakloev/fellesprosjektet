package gui.appointment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class EditButtonPanel extends JPanel {

	
	EditButtonPanel() {
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		JButton btnRedigerListe = new JButton("Rediger liste");
		GridBagConstraints gbc_btnRedigerListe = new GridBagConstraints();
		gbc_btnRedigerListe.insets = new Insets(5, 0, 5, 5);
		gbc_btnRedigerListe.gridx = 0;
		gbc_btnRedigerListe.gridy = 0;
		this.add(btnRedigerListe, gbc_btnRedigerListe);
		
		JButton btnDeltar = new JButton("Deltar");
		GridBagConstraints gbc_btnDeltar = new GridBagConstraints();
		gbc_btnDeltar.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeltar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeltar.gridx = 0;
		gbc_btnDeltar.gridy = 1;
		this.add(btnDeltar, gbc_btnDeltar);
		
		JButton btnDeltarIkke = new JButton("Deltar ikke");
		GridBagConstraints gbc_btnDeltarIkke = new GridBagConstraints();
		gbc_btnDeltarIkke.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeltarIkke.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeltarIkke.gridx = 0;
		gbc_btnDeltarIkke.gridy = 2;
		this.add(btnDeltarIkke, gbc_btnDeltarIkke);
		
		JButton btnSlett = new JButton("Slett");
		GridBagConstraints gbc_btnSlett = new GridBagConstraints();
		gbc_btnSlett.insets = new Insets(0, 0, 5, 5);
		gbc_btnSlett.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSlett.gridx = 0;
		gbc_btnSlett.gridy = 3;
		this.add(btnSlett, gbc_btnSlett);
		
	}
}
