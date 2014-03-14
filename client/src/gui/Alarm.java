package gui;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tarald on 12.03.14.
 */
@SuppressWarnings("serial")
public class Alarm extends JDialog implements ActionListener{
	
	
    private JRadioButton twentyFourButton, hourButton, minButton, customHoursButton, customDateTimeButton;
    private JLabel twentyFourLabel, hourLabel, minLabel, textLabel1;
    private JTextField customHoursTextField;
    private JSpinner dateSpinner;
    private JButton okButton, cancelButton;
    private ButtonGroup btnGroup;
    private JDialog parent;
    private Calendar warningDate, startDate;
    private Calendar[] alarmCalendar;

    private JPanel contentPane;


    public Alarm(JDialog parent, Calendar startDate, Calendar[] alarmCalendar){
        super(parent, true);
        this.setTitle("Velg alarm tidspunkt");
        this.setResizable(false);

        this.parent = parent;
        this.startDate = startDate;
        this.alarmCalendar = alarmCalendar;

        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        btnGroup = new ButtonGroup();

        twentyFourButton = new JRadioButton();
        twentyFourButton.setActionCommand("24 timer før");
        btnGroup.add(twentyFourButton);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPane.add(twentyFourButton, gbc);

        hourButton = new JRadioButton();
        hourButton.setActionCommand("1 time før");
        btnGroup.add(hourButton);
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPane.add(hourButton, gbc);

        minButton = new JRadioButton();
        minButton.setActionCommand("10 min før");
        btnGroup.add(minButton);
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPane.add(minButton, gbc);

        customHoursButton = new JRadioButton();
        customHoursButton.setActionCommand("customHoursButton");
        btnGroup.add(customHoursButton);
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPane.add(customHoursButton, gbc);

        customDateTimeButton = new JRadioButton();
        customDateTimeButton.setActionCommand("customDateTimeButton");
        btnGroup.add(customDateTimeButton);
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPane.add(customDateTimeButton, gbc);

        twentyFourLabel = new JLabel("24 timer før");
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPane.add(twentyFourLabel, gbc);

        hourLabel = new JLabel("1 time før");
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPane.add(hourLabel, gbc);

        minLabel = new JLabel("10 min før");
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPane.add(minLabel, gbc);

        customHoursTextField = new JTextField("HH:MM");
        customHoursTextField.setColumns(5);
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        contentPane.add(customHoursTextField, gbc);

        textLabel1 = new JLabel("før");
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        contentPane.add(textLabel1, gbc);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy  HH:mm");
        dateSpinner.setEditor(timeEditor);
        dateSpinner.setValue(new Date());
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPane.add(dateSpinner, gbc);

        JPanel buttonPanel = new JPanel();
        
        okButton = new JButton("Ok");
        buttonPanel.add(okButton);
        okButton.addActionListener(this);

        cancelButton = new JButton("Avbryt");
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(this);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        contentPane.add(buttonPanel, gbc);
        
        okButton.setPreferredSize(cancelButton.getPreferredSize());

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Avbryt")){
            dispose();
        }
        else if(e.getActionCommand().equals("Ok")){
            if (btnGroup.getSelection() == null) {
            	JOptionPane.showMessageDialog(null, "Du må velge et tidspunkt eller trykke avbryt", "Feil", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            String command = btnGroup.getSelection().getActionCommand();
            warningDate = startDate;
            
            switch (command){
                case "24 timer før":
                    warningDate.add(Calendar.DAY_OF_MONTH, -1);
                    alarmCalendar[0] = warningDate;
                    dispose();  
                    break;
                case "1 time før":
                    warningDate.add(Calendar.HOUR_OF_DAY, -1);
                    alarmCalendar[0] = warningDate;
                    dispose();
                    break;
                case "10 min før":
                    warningDate.add(Calendar.MINUTE, -10);
                    alarmCalendar[0] = warningDate;
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
                    alarmCalendar[0] = warningDate;
                    dispose();
                    break;
                case "customDateTimeButton":
                    Date midDate = (Date) dateSpinner.getValue();
                    warningDate = Calendar.getInstance();
                    warningDate.setTimeInMillis(midDate.getTime());
                    alarmCalendar[0] = warningDate;
                    dispose();
                    break;
            }
        }
    }
}
