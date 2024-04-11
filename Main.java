import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JLabel[][] timetableLabels;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> dayComboBox;

    public Main() {
        setTitle("LAB MAnagement System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[][] timetableData = {
                {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
                {"8:00 - 9:00", "", "", "", "", "", ""},
                {"9:00 - 10:00", "", "", "", "", "", ""},
                {"10:00 - 11:00", "", "", "", "", "", ""},
                {"11:00 - 12:00", "", "", "", "", "", ""},
                {"12:00 - 1:00", "", "", "", "", "", ""}
        };

        timetableLabels = new JLabel[timetableData.length][timetableData[0].length];

        JPanel timetablePanel = new JPanel(new GridLayout(timetableData.length, timetableData[0].length));
        
        for (int i = 0; i < timetableData.length; i++) {
            for (int j = 0; j < timetableData[0].length; j++) {
                timetableLabels[i][j] = new JLabel(timetableData[i][j], SwingConstants.CENTER);
                timetableLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                timetablePanel.add(timetableLabels[i][j]);
            }
        }

        getContentPane().add(timetablePanel, BorderLayout.CENTER);

        String[] times = {"8:00 - 9:00", "9:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 1:00"};
        timeComboBox = new JComboBox<>(times);

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        dayComboBox = new JComboBox<>(days);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEventDialog dialog = new AddEventDialog(Main.this);
                dialog.setVisible(true);
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Time:"));
        controlPanel.add(timeComboBox);
        controlPanel.add(new JLabel("Day:"));
        controlPanel.add(dayComboBox);
        controlPanel.add(addButton);

        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void addEvent(String time, String day, String eventName) {
        int rowIndex = -1;
        for (int i = 0; i < timetableLabels.length; i++) {
            if (timetableLabels[i][0].getText().equals(time)) {
                rowIndex = i;
                break;
            }
        }

        int columnIndex = -1;
        for (int j = 0; j < timetableLabels[0].length; j++) {
            if (timetableLabels[0][j].getText().equals(day)) {
                columnIndex = j;
                break;
            }
        }

        if (rowIndex != -1 && columnIndex != -1) {
            String currentText = timetableLabels[rowIndex][columnIndex].getText();
            if (!currentText.isEmpty()) {
                currentText += ", ";
            }
            timetableLabels[rowIndex][columnIndex].setText(currentText + eventName);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid time or day.");
        }
    }

    class AddEventDialog extends JDialog {
        private JTextField eventNameField;

        public AddEventDialog(JFrame parent) {
            super(parent, "Add Unoccupied LAB", true);
            setSize(300, 100);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new FlowLayout());

            panel.add(new JLabel("LAB Name:"));
            eventNameField = new JTextField(20);
            panel.add(eventNameField);

            JButton addButton = new JButton("Add");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String time = (String) timeComboBox.getSelectedItem();
                    String day = (String) dayComboBox.getSelectedItem();
                    String eventName = eventNameField.getText();
                    addEvent(time, day, eventName);
                    dispose();
                }
            });
            panel.add(addButton);

            getContentPane().add(panel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}