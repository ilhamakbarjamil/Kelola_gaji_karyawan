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
        JButton deleteButton = new JButton("Delete Selected");
        JButton searchButton = new JButton("Search");
        JButton sortButton = new JButton("Sort by Salary");
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);
    }
}


