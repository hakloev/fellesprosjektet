package gui;

import helperclasses.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.OutboundWorker;
import controllers.SocketListener;
import models.*;
import controllers.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
    private Employee[] saveEmployeeHere;


    public LoginScreen(Frame parent, Employee[] saveEmployeeHere){
        super(parent,"Innlogging", true);

        this.saveEmployeeHere = saveEmployeeHere;
        
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
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand() == "Logg inn") {
            
            /* test code */
            this.dispose();
            saveEmployeeHere[0] = new Employee("arvid", "Arvid Pettersen");
            return;
            /* end test code */
            
            // real logic below. Uncomment for production environment!
            /*
            SocketListener client = new SocketListener();
            if (client.connect()) {
            	OutboundWorker.sendRequest(new Request("login", "post",
            			new Login(usernameField.getText(), passwordField.getPassword().toString())));
            	
            	Object[] response = new Object[1];
            	new LoginWaiter(response);
            	
            	if (response[0] != null) {
            		if (response[0] instanceof Integer) {
            			JOptionPane.showMessageDialog(null, "Tidsavbrudd!", "Feil", JOptionPane.ERROR_MESSAGE);
            			
            		} else if (response[0] instanceof Employee) {
            			saveEmployeeHere[0] = (Employee)response[0];
            			this.dispose();
            			return;
            			
            		} else {
            			JOptionPane.showMessageDialog(null, "Det oppstod en feil!", "Feil", JOptionPane.ERROR_MESSAGE);
            		}
        			
            	} else {
            		JOptionPane.showMessageDialog(null, "Feil brukernavn eller passord", "Feil", JOptionPane.ERROR_MESSAGE);
            	}
            	
            	try {
					SocketListener.getSocket().close();
				} catch (IOException e) {
					// Don't care
					//e.printStackTrace();
				}
            	
>>>>>>> origin/K
            } else {
                JOptionPane.showMessageDialog(null, "Kunne ikke koble til serveren!", "Feil", JOptionPane.ERROR_MESSAGE);
            }
<<<<<<< HEAD


        }
        else if(e.getActionCommand().equals("Avslutt")) {
            System.exit(0);
=======
            */
        
        } else if (ae.getActionCommand() == "Avslutt") {
        	System.exit(0);

        }
    }


	class LoginWaiter extends Thread {
		LoginWaiter(Object[] response) {
			InboundWorker.register(this);
			try {
				sleep(30000); // 30 second timeout
			} catch (InterruptedException e) {
				//e.printStackTrace();
				response[0] = InboundWorker.getResponse();
				InboundWorker.unregister(this);
				return;
			}
			InboundWorker.unregister(this);
			response[0] = new Integer(1);
		}
	}
	
	
	
}



