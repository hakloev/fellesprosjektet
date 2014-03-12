package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tarald on 11.03.14.
 */
public class LoginMenu extends JDialog implements ActionListener{
    private JButton loginButton,exitButton;
    private JTextField passwordField, usernameField;
    private JLabel usernameLabel, passwordLabel;


    public LoginMenu(Frame parent){
        super(parent,"Login", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        setTitle("Login menu");


        setLocationRelativeTo(null);

        this.setModal(true);

        GridBagConstraints c;
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        usernameLabel = new JLabel("Name:");
        panel.add(usernameLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        usernameField = new JTextField(15);
        panel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        passwordLabel = new JLabel("Email:");
        panel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        passwordField = new JTextField(15);
        panel.add(passwordField, c);
        panel.setBorder(new LineBorder(Color.GRAY));


        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton, c);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton, c);

        getContentPane().add(panel,BorderLayout.CENTER);
        getContentPane().add(buttonPanel,BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if(e.getActionCommand() == "Login"){
            this.dispose();
        }
        else if(e.getActionCommand() == "Exit"){
            System.out.println("e = " + e);
            this.dispose();

        }
    }

}
