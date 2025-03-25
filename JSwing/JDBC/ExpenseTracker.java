package JDBC;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExpenseTracker {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField amountField, descField;
    private JComboBox<String> categoryBox;
    private JLabel totalLabel;
    private double totalExpense = 0.0;
    private static final String URL = "jdbc:mysql://localhost:3306/ExpenseTracker";
    private static final String USER = "root";
    private static final String PASSWORD = "yourpass";

    public ExpenseTracker() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        
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
        
        JButton updateButton = new JButton("Update Expense");
        panel.add(updateButton);
        
        JButton deleteButton = new JButton("Delete Expense");
        panel.add(deleteButton);
        
        totalLabel = new JLabel("Total: $0.00");
        panel.add(totalLabel);
        
        frame.add(panel, BorderLayout.NORTH);
        
        model = new DefaultTableModel(new String[]{"ID", "Amount", "Category", "Description"}, 0);
        table = new JTable(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> addExpense());
        updateButton.addActionListener(e -> updateExpense());
        deleteButton.addActionListener(e -> deleteExpense());
        
        frame.setVisible(true);
        createTable();
        loadExpenses();
    }

    private void addExpense() {
        String amountText = amountField.getText();
        String category = (String) categoryBox.getSelectedItem();
        String description = descField.getText();
        
        try {
            double amount = Double.parseDouble(amountText);
            insertExpense(category, amount, description);
            amountField.setText("");
            descField.setText("");
            loadExpenses();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createTable() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "category VARCHAR(50), " +
                    "amount DOUBLE, " +
                    "description TEXT, " +
                    "createdAt DATETIME DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertExpense(String category, double amount, String description) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO expenses (category, amount, description) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, category);
                pstmt.setDouble(2, amount);
                pstmt.setString(3, description);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadExpenses() {
        model.setRowCount(0);
        totalExpense = 0.0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM expenses")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                String description = rs.getString("description");
                model.addRow(new Object[]{id, amount, category, description});
                totalExpense += amount;
            }
            totalLabel.setText("Total: $" + totalExpense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an expense to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        String amountText = amountField.getText();
        String category = (String) categoryBox.getSelectedItem();
        String description = descField.getText();

        try {
            double amount = Double.parseDouble(amountText);
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "UPDATE expenses SET category=?, amount=?, description=? WHERE id=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, category);
                    pstmt.setDouble(2, amount);
                    pstmt.setString(3, description);
                    pstmt.setInt(4, id);
                    pstmt.executeUpdate();
                }
            }
            loadExpenses();
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an expense to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(selectedRow, 0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM expenses WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadExpenses();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTracker::new);
    }
}
