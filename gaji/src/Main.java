import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

class Employee {
    String nama;
    int umur;
    double gaji;
    String posisi;

    public Employee(String nama, int umur, double gaji, String posisi) {
        this.nama = nama;
        this.umur = umur;
        this.gaji = gaji;
        this.posisi = posisi;
    }
}

public class Main{
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tablemodel;
    private ArrayList<Employee> employeeList;

    public Main(){
        employeeList = new ArrayList<>();
        frame = new JFrame("Management gaji karyawan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tablemodel = new DefaultTableModel(new String[]{"nama", "umur", "gaji", "posisi"}, 0);
        table = new JTable(tablemodel);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel inputJPanel = new JPanel(new GridLayout(5, 2));
        JTextField nama_field = new JTextField();
        JTextField umur_field = new JTextField();
        JTextField gaji_field = new JTextField();
        JTextField posisi_field = new JTextField();
        JButton add_button = new JButton("Tambah Karyawan");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nama_field);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(umur_field);
        inputPanel.add(new JLabel("Salary:"));
        inputPanel.add(gaji_field);
        inputPanel.add(new JLabel("Position:"));
        inputPanel.add(posisi_field);
        inputPanel.add(add_button);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton deleteButton = new JButton("Hapus Pilihan");
        JButton searchButton = new JButton("Cari");
        JButton sortButton = new JButton("Urutkan Gaji");
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loadDataFromDatabase();

        add_button.addActionListener(e ->{
            String nama = nama_field.getText();
            int umur;
            double gaji;
            String posisi = posisi_field.getText();

            try {
                umur = Integer.parseInt(umur_field.getText());
                gaji = Double.parseDouble(gaji_field.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Masukkan gaji dengan benar.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Employee employee = new Employee(nama, umur, gaji, posisi);
            employeeList.add(employee);
            tablemodel.addRow(new Object[]{nama, umur, gaji, posisi});

            saveToDatabase(employee);

            nama_field.setText("");
            umur_field.setText("");
            gaji_field.setText("");
            posisi_field.setText("");
        });

        deleteButton.addActionListener(e ->{
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Employee emp = employeeList.get(selectedRow);

                deleteFromDatabase(emp);

                employeeList.remove(selectedRow);
                tablemodel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> {
            String criteria = JOptionPane.showInputDialog(frame, "Masukkan Nama yang di cari:");
            if (criteria != null && !criteria.isEmpty()) {
                for (int i = 0; i < tablemodel.getRowCount(); i++) {
                    if (tablemodel.getValueAt(i, 0).toString().equalsIgnoreCase(criteria)) {
                        table.setRowSelectionInterval(i, i);
                        JOptionPane.showMessageDialog(frame, "Karyawan ditemukan: " + criteria);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Karyawan tidak di temukan.", "hasil pencarian", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        sortButton.addActionListener(e -> {
            employeeList.sort(Comparator.comparingDouble(emp -> emp.salary));
            tablemodel.setRowCount(0);
            for (Employee emp : employeeList) {
                tablemodel.addRow(new Object[]{emp.name, emp.age, emp.salary, emp.position});
            }
        });

        frame.setVisible(true);
    }

    private Connection connectToDatabase(){
        try{
            String url = "jdbc:mysql://localhost:3306/uap_proglan";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch(SQLException e){
            JOptionPane.showMessageDialog(frame, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void loadDataFromDatabase() {
        try (Connection conn = connectToDatabase()) {
            if (conn != null) {
                String sql = "SELECT * FROM employees";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    double salary = rs.getDouble("salary");
                    String position = rs.getString("position");
                    employeeList.add(new Employee(name, age, salary, position));
                    tablemodel.addRow(new Object[]{name, age, salary, position});
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Failed to load data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToDatabase(Employee employee){
        try (Connection conn = connectToDatabase()){
            String sql = "INSERT INTO employees (name, age, salary, position) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employee.nama);
            stmt.setInt(2, employee.umur);
            stmt.setDouble(3, employee.gaji);
            stmt.setString(4, employee.posisi);
            stmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "gagal menyimpan data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


