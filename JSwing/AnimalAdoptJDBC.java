import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AnimalAdoptJDBC {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/animal_shelter";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Simran@123";

    private JFrame frame;
    private JTextField nameField, typeField, ageField, statusField, idField, updateNameField, updateTypeField,
            updateAgeField, updateStatusField;
    private JTable animalTable;
    private DefaultTableModel tableModel;

    public AnimalAdoptJDBC() {
        frame = new JFrame("Animal Adoption System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 20, 10)); // Insert & Update Forms side by side

        // Insert Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Animal"));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Adoption Status:"));
        statusField = new JTextField();
        inputPanel.add(statusField);

        JButton insertButton = new JButton("Insert Animal");
        insertButton.addActionListener(e -> insertAnimal());
        inputPanel.add(insertButton);

        formPanel.add(inputPanel);

        // Update Panel
        JPanel updatePanel = new JPanel(new GridBagLayout());
        updatePanel.setBorder(BorderFactory.createTitledBorder("Update Animal Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        updatePanel.add(new JLabel("Animal ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField();
        updatePanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updatePanel.add(new JLabel("New Name:"), gbc);
        gbc.gridx = 1;
        updateNameField = new JTextField();
        updatePanel.add(updateNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updatePanel.add(new JLabel("New Type:"), gbc);
        gbc.gridx = 1;
        updateTypeField = new JTextField();
        updatePanel.add(updateTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updatePanel.add(new JLabel("New Age:"), gbc);
        gbc.gridx = 1;
        updateAgeField = new JTextField();
        updatePanel.add(updateAgeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updatePanel.add(new JLabel("New Status:"), gbc);
        gbc.gridx = 1;
        updateStatusField = new JTextField();
        updatePanel.add(updateStatusField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton updateButton = new JButton("Update Details");
        updateButton.addActionListener(e -> updateAnimalDetails());
        buttonPanel.add(updateButton);

        JButton refreshButton = new JButton("Refresh Table");
        refreshButton.addActionListener(e -> displayAnimals());
        buttonPanel.add(refreshButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        updatePanel.add(buttonPanel, gbc);

        formPanel.add(updatePanel);

        frame.add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[] { "ID", "Name", "Type", "Age", "Status" }, 0);
        animalTable = new JTable(tableModel);
        animalTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(animalTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Animal List"));
        frame.add(tableScroll, BorderLayout.CENTER);

        frame.setVisible(true);
        displayAnimals();
    }

    private void insertAnimal() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO animals (name, type, age, adoption_status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nameField.getText());
                pstmt.setString(2, typeField.getText());
                pstmt.setInt(3, Integer.parseInt(ageField.getText()));
                pstmt.setString(4, statusField.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Animal inserted successfully.");
                displayAnimals();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAnimalDetails() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE animals SET name = ?, type = ?, age = ?, adoption_status = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, updateNameField.getText());
                pstmt.setString(2, updateTypeField.getText());
                pstmt.setInt(3, Integer.parseInt(updateAgeField.getText()));
                pstmt.setString(4, updateStatusField.getText());
                pstmt.setInt(5, Integer.parseInt(idField.getText()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Animal details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No animal found with the given ID.", "Update Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                displayAnimals();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAnimals() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM animals";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[] { rs.getInt("id"), rs.getString("name"), rs.getString("type"),
                            rs.getInt("age"), rs.getString("adoption_status") });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnimalAdoptJDBC::new);
    }
}
