package gui.appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tarald on 12.03.14.
 */
public class Alert extends JDialog implements ActionListener{
    private JRadioButton twentyFourButton, hourButton, minButton, customHoursButton, customDateTimeButton;
    private JLabel twentyFourLabel, hourLabel, minLabel, textLabel1;
    private JTextField customHoursTextField;
    private JButton okButton, cancelButton;
    private ButtonGroup btnGroup;
    private Calendar warningDate, startDate;

    public Alert(Frame parent, Calendar startDate){
        super(parent,"Alarm tidspunkt", true);
        this.startDate = startDate;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        setTitle("Alarm tidspunkt");

        setLocationRelativeTo(null);
        setModal(true);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        btnGroup = new ButtonGroup();

        twentyFourButton = new JRadioButton();
        twentyFourButton.setActionCommand("24 timer før");
        btnGroup.add(twentyFourButton);
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(twentyFourButton, c);

        hourButton = new JRadioButton();
        hourButton.setActionCommand("1 time før");
        btnGroup.add(hourButton);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(hourButton, c);

        minButton = new JRadioButton();
        minButton.setActionCommand("10 min før");
        btnGroup.add(minButton);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(minButton, c);

        customHoursButton = new JRadioButton();
        customHoursButton.setActionCommand("customHoursButton");
        btnGroup.add(customHoursButton);
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        buttonPanel.add(customHoursButton, c);

        customDateTimeButton = new JRadioButton();
        customDateTimeButton.setActionCommand("customDateTimeButton");
        btnGroup.add(customDateTimeButton);
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
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "dd.MM.yyyy  HH:mm");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Avbryt")){
            dispose();
        }
        else if(e.getActionCommand().equals("Ok")){
            String command = btnGroup.getSelection().getActionCommand();
            warningDate = startDate;
            switch (command){
                case "24 timer før":
                    warningDate.add(Calendar.DAY_OF_MONTH,-1);
                    dispose();  
                    break;
                case "1 time før":
                    warningDate.add(Calendar.HOUR_OF_DAY, -1);
                    dispose();
                    break;
                case "10 min før":
                    warningDate.add(Calendar.MINUTE,-10);
                    dispose();
                    break;
                case "customHoursButton":
                    String[] time = customHoursTextField.getText().split(":");
                    if(time.length == 0 || time[0].length() != 2 || time[1].length() != 2){
                        JOptionPane.showMessageDialog(this,"Feil format, du må skrive tid på formatet HH:mm!");
                        break;
                    }
                    int hours = Integer.parseInt(time[0]);
                    warningDate.add(Calendar.HOUR_OF_DAY, -hours);
                    int minutes = Integer.parseInt(time[1]);
                    warningDate.add(Calendar.MINUTE, -minutes);
                    dispose();
                    break;
                case "customDateTimeButton":
                    break;



            }
        }
    }
}
