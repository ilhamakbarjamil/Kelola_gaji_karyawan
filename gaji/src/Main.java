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
    ArrayList<Employee> employeeList;

    public Main(){
        employeeList = new ArrayList<>();
        frame = new JFrame("Management gaji karyawan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tablemodel = new DefaultTableModel(new String[]{"nama", "umur", "gaji", "posisi"}, 0);
        table = new JTable(tablemodel);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
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
        JButton updateSalaryButton = new JButton("Update Gaji");
        buttonPanel.add(updateSalaryButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loadDataFromDatabase();

        add_button.addActionListener(e -> {
            String nama = nama_field.getText().trim();
            String umurText = umur_field.getText().trim();
            String gajiText = gaji_field.getText().trim();
            String posisi = posisi_field.getText().trim();
            
            if (nama.isEmpty() || umurText.isEmpty() || gajiText.isEmpty() || posisi.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Semua field harus diisi!", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!nama.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(frame, 
                    "Nama hanya boleh berisi huruf!", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            for (Employee emp : employeeList) {
                if (emp.nama.equalsIgnoreCase(nama)) {
                    JOptionPane.showMessageDialog(frame, 
                        "Nama karyawan sudah ada!", 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            int umur;
            double gaji;
        
            try {
                umur = Integer.parseInt(umurText);
                gaji = Double.parseDouble(gajiText);
        
                if (umur <= 0 || gaji <= 0) {
                    JOptionPane.showMessageDialog(frame, 
                        "Umur dan gaji harus lebih besar dari 0!", 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);
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
        
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Umur harus berupa angka bulat dan gaji harus berupa angka!", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
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

        updateSalaryButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String nama = tablemodel.getValueAt(selectedRow, 0).toString();
                int umur = Integer.parseInt(tablemodel.getValueAt(selectedRow, 1).toString());
                String posisi = tablemodel.getValueAt(selectedRow, 3).toString();
        
                String gajiBaruStr = JOptionPane.showInputDialog(frame, "Masukkan gaji baru untuk karyawan " + nama + ":");
                if (gajiBaruStr != null && !gajiBaruStr.isEmpty()) {
                    try {
                        double gajiBaru = Double.parseDouble(gajiBaruStr);
        
                        Employee employee = employeeList.get(selectedRow);
                        employee.gaji = gajiBaru;
        
                        tablemodel.setValueAt(gajiBaru, selectedRow, 2);
        
                        updateSalaryInDatabase(employee);
        
                        JOptionPane.showMessageDialog(frame, "Gaji berhasil diperbarui.");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Masukkan gaji dalam format angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih baris terlebih dahulu untuk memperbarui gaji.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        sortButton.addActionListener(e -> {
            employeeList.sort(Comparator.comparingDouble(emp -> emp.gaji));
            tablemodel.setRowCount(0);
            for (Employee emp : employeeList) {
                tablemodel.addRow(new Object[]{emp.nama, emp.umur, emp.gaji, emp.posisi});
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

    private void updateSalaryInDatabase(Employee employee) {
        try (Connection conn = connectToDatabase()) {
            if (conn != null) {
                String sql = "UPDATE employees SET salary = ? WHERE name = ? AND age = ? AND position = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, employee.gaji);
                stmt.setString(2, employee.nama);
                stmt.setInt(3, employee.umur);
                stmt.setString(4, employee.posisi);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Gagal memperbarui gaji: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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

    private void deleteFromDatabase(Employee employee) {
        try (Connection conn = connectToDatabase()) {
            if (conn != null) {
                String sql = "DELETE FROM employees WHERE name = ? AND age = ? AND salary = ? AND position = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, employee.nama);
                stmt.setInt(2, employee.umur);
                stmt.setDouble(3, employee.gaji);
                stmt.setString(4, employee.posisi);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Failed to delete data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}


