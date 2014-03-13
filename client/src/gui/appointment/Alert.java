package gui.appointment;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by Tarald on 12.03.14.
 */
public class Alert extends JDialog{
    private JRadioButton twentyFourButton, hourButton, minButton, customHoursButton, customDateTimeButton;
    private JLabel twentyFourLabel, hourLabel, minLabel, textLabel1;
    private JTextField customHoursTextField;
    private JButton okButton, cancelButton;

    public Alert(Frame parent){

        super(parent,"Alarm tidspunkt", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        setTitle("Alarm tidspunkt");
        //setSize(500,500);

        setLocationRelativeTo(null);
        setModal(true);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        ButtonGroup btnGoup = new ButtonGroup();

        twentyFourButton = new JRadioButton();
        btnGoup.add(twentyFourButton);
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(twentyFourButton, c);

        hourButton = new JRadioButton();
        btnGoup.add(hourButton);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(hourButton, c);

        minButton = new JRadioButton();
        btnGoup.add(minButton);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(minButton, c);

        customHoursButton = new JRadioButton();
        btnGoup.add(customHoursButton);
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(customHoursButton, c);

        customDateTimeButton = new JRadioButton();
        btnGoup.add(customDateTimeButton);
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(customDateTimeButton, c);

        JPanel infoPanel = new JPanel();

        twentyFourLabel = new JLabel("24 timer før");
        c.gridy = 0;
        c.gridx = 1;
        c.gridwidth = 2;
        buttonPanel.add(twentyFourLabel,c);

        hourLabel = new JLabel("1 time før");
        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 2;
        buttonPanel.add(hourLabel,c);

        minLabel = new JLabel("10 min før");
        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        buttonPanel.add(minLabel,c);

        customHoursTextField = new JTextField("HH:MM");
        c.gridy = 3;
        c.gridx = 1;
        buttonPanel.add(customHoursTextField, c);

        textLabel1 = new JLabel("Før");
        c.gridy = 3;
        c.gridx = GridBagConstraints.RELATIVE;
        buttonPanel.add(textLabel1,c);

        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd  HH:mm");
        spinner.setEditor(timeEditor);
        spinner.setValue(new Date());
        c.gridy = 4;
        c.gridx = 1;
        c.gridwidth = 2;
        buttonPanel.add(spinner,c);

        okButton = new JButton("Ok");
        infoPanel.add(okButton);

        cancelButton = new JButton("Avbryt");
        infoPanel.add(cancelButton);


        add(buttonPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.SOUTH);


        pack();


    }
}
