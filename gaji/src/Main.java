import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

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

        
    }
}


