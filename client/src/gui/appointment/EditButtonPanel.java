package gui.appointment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class EditButtonPanel extends JPanel {

	
	EditButtonPanel() {
		
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0};
		gbl_panel_2.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		this.setLayout(gbl_panel_2);
		
		JButton btnRedigerListe = new JButton("Rediger liste");
		GridBagConstraints gbc_btnRedigerListe = new GridBagConstraints();
		gbc_btnRedigerListe.insets = new Insets(5, 5, 5, 5);
		gbc_btnRedigerListe.gridx = 0;
		gbc_btnRedigerListe.gridy = 0;
		this.add(btnRedigerListe, gbc_btnRedigerListe);
		
		JButton btnDeltar = new JButton("Deltar");
		GridBagConstraints gbc_btnDeltar = new GridBagConstraints();
		gbc_btnDeltar.insets = new Insets(0, 5, 5, 5);
		gbc_btnDeltar.gridx = 0;
		gbc_btnDeltar.gridy = 1;
		this.add(btnDeltar, gbc_btnDeltar);
		
		JButton btnDeltarIkke = new JButton("Deltar ikke");
		GridBagConstraints gbc_btnDeltarIkke = new GridBagConstraints();
		gbc_btnDeltarIkke.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeltarIkke.gridx = 0;
		gbc_btnDeltarIkke.gridy = 2;
		this.add(btnDeltarIkke, gbc_btnDeltarIkke);
		
		JButton btnSlett_1 = new JButton("Slett");
		GridBagConstraints gbc_btnSlett_1 = new GridBagConstraints();
		gbc_btnSlett_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnSlett_1.gridx = 0;
		gbc_btnSlett_1.gridy = 3;
		this.add(btnSlett_1, gbc_btnSlett_1);
		
	}
}
