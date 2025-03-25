import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseTracker {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField amountField, descField;
    private JComboBox<String> categoryBox;
    private JLabel totalLabel;
    private double totalExpense = 0.0;

    public ExpenseTracker() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);
        
        panel.add(new JLabel("Category:"));
        categoryBox = new JComboBox<>(new String[]{"Food", "Transport", "Bills", "Entertainment", "Other"});
        panel.add(categoryBox);
        
        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);
        
        JButton addButton = new JButton("Add Expense");
        panel.add(addButton);

        totalLabel = new JLabel("Total: $0.00");
        panel.add(totalLabel);
        
        frame.add(panel, BorderLayout.NORTH);
        
        model = new DefaultTableModel(new String[]{"Amount", "Category", "Description"}, 0);
        table = new JTable(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });
        
        frame.setVisible(true);
    }

    private void addExpense() {
        String amountText = amountField.getText();
        String category = (String) categoryBox.getSelectedItem();
        String description = descField.getText();

        try {
            double amount = Double.parseDouble(amountText);
            model.addRow(new Object[]{amount, category, description});
            totalExpense += amount;
            totalLabel.setText("Total: $" + totalExpense);
            amountField.setText("");
            descField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTracker::new);
    }
}
