import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class EmpRegister extends JFrame implements ActionListener {
    JLabel l1, l2;
    JTextField tf1, tf2;
    JButton insertBtn, deleteBtn, updateBtn, displayBtn;
    Connection con;

    EmpRegister() {
        setTitle("Employee Registration Form");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        l1 = new JLabel("Employee ID:");
        l2 = new JLabel("Employee Name:");
        tf1 = new JTextField();
        tf2 = new JTextField();
        insertBtn = new JButton("Insert");
        deleteBtn = new JButton("Delete");
        updateBtn = new JButton("Update");
        displayBtn = new JButton("Display");

        insertBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        displayBtn.addActionListener(this);

        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(insertBtn);
        add(deleteBtn);
        add(updateBtn);
        add(displayBtn);

        l1.setBounds(30, 30, 100, 30);
        tf1.setBounds(150, 30, 200, 30);
        l2.setBounds(30, 70, 100, 30);
        tf2.setBounds(150, 70, 200, 30);

        insertBtn.setBounds(30, 120, 90, 40);
        deleteBtn.setBounds(130, 120, 90, 40);
        updateBtn.setBounds(230, 120, 90, 40);
        displayBtn.setBounds(330, 120, 90, 40);

        connectToDatabase();

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/christ", "root", "Sridhar@2006");
            System.out.println("Database Connected Successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.");
            return;
        }

        try {
            if (e.getSource() == insertBtn) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO employees VALUES(?, ?)");
                stmt.setInt(1, Integer.parseInt(tf1.getText()));
                stmt.setString(2, tf2.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record inserted successfully.");
            } else if (e.getSource() == deleteBtn) {
                PreparedStatement stmt = con.prepareStatement("DELETE FROM employees WHERE empid=?");
                stmt.setInt(1, Integer.parseInt(tf1.getText()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record deleted successfully.");
            } else if (e.getSource() == updateBtn) {
                PreparedStatement stmt = con.prepareStatement("UPDATE employees SET empname=? WHERE empid=?");
                stmt.setString(1, tf2.getText());
                stmt.setInt(2, Integer.parseInt(tf1.getText()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record updated successfully.");
            } else if (e.getSource() == displayBtn) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
                StringBuilder data = new StringBuilder();
                while (rs.next()) {
                    data.append("Employee ID: ").append(rs.getInt(1)).append(", Employee Name: ").append(rs.getString(2)).append("\n");
                }
                JOptionPane.showMessageDialog(this, data.toString().isEmpty() ? "No records found" : data.toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EmpRegister();
    }
}
