package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.OutboundWorker;
import controllers.SocketListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tarald on 11.03.14.
 */
@SuppressWarnings("serial")
public class LoginScreen extends JDialog implements ActionListener{
	
	
    private JButton loginButton, exitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel, passwordLabel;
    private String[] saveUsernameHere;
    SocketListener client;
    public static Boolean loggedIn;


    public LoginScreen(Frame parent, String[] saveUsernameHere){
        super(parent,"Innlogging", true);
        this.saveUsernameHere = saveUsernameHere;
        loggedIn = false;
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        
        JPanel panel = new JPanel(new GridBagLayout());
        setContentPane(panel);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        usernameLabel = new JLabel("Brukernavn:");
        panel.add(usernameLabel, gbc);

        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        passwordLabel = new JLabel("Passord:");
        panel.add(passwordLabel, gbc);

        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Logg inn");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton, gbc);
        
        exitButton = new JButton("Avslutt");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton, gbc);
        
        exitButton.setPreferredSize(loginButton.getPreferredSize());
        
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Logg inn")) {
        	//Creating a SocketClient object
            client = new SocketListener();
            //trying to establish connection to the server
            Boolean connected = client.connect();

            /* test code */
            /* end test code */

            if (connected) {
            	String username = usernameField.getText();
                while (loggedIn == false) {
                    OutboundWorker.login(username, passwordField.getPassword());
            		saveUsernameHere[0] = username;
            		this.dispose();
                }


            } else {
            	JOptionPane.showMessageDialog(null, "Kunne ikke koble til serveren!", "Feil", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
        else if(e.getActionCommand().equals("Avslutt")) {
            System.exit(0);
        }
    }

}
