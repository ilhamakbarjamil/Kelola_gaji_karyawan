# Employee Management System

## Deskripsi
Employee Management System adalah sebuah aplikasi berbasis Java yang dirancang untuk mengelola data karyawan. Aplikasi ini memiliki antarmuka grafis (GUI) menggunakan Java Swing dan memungkinkan pengguna untuk:
- Menambah data karyawan.
- Menghapus data karyawan.
- Mencari karyawan berdasarkan nama.
- Mengurutkan karyawan berdasarkan gaji.
- Menyimpan dan memuat data dari database MySQL.

Aplikasi ini menggunakan JDBC (Java Database Connectivity) untuk menghubungkan aplikasi dengan database MySQL.

---

## Fitur
1. **Menambah Karyawan**
   - Memasukkan data nama, umur, gaji, dan posisi melalui form input.
   - Data disimpan ke tabel aplikasi dan database MySQL.

2. **Menghapus Karyawan**
   - Memilih data karyawan di tabel dan menghapusnya dari aplikasi serta database.

3. **Mencari Karyawan**
   - Mencari karyawan berdasarkan nama.
   - Baris tabel akan dipilih secara otomatis jika data ditemukan.

4. **Mengurutkan Karyawan**
   - Mengurutkan data karyawan berdasarkan gaji dalam urutan menaik.

5. **Database Integration**
   - Memuat data karyawan dari database saat aplikasi dijalankan.
   - Menyimpan data baru ke database.
   - Menghapus data dari database saat karyawan dihapus.

---

## Teknologi yang Digunakan
- **Bahasa Pemrograman**: Java
- **Framework GUI**: Swing
- **Database**: MySQL
- **Library JDBC**: Untuk koneksi ke database

---

## Cara Menjalankan Program

### **1. Persiapan Database**
1. Pastikan MySQL telah diinstal di komputer Anda.
2. Buat database dengan nama `uap_proglan` menggunakan perintah berikut:
   ```sql
   CREATE DATABASE uap_proglan;
   ```
3. Buat tabel `employees` dengan struktur berikut:
   ```sql
   CREATE TABLE employees (
       name VARCHAR(255) NOT NULL,
       age INT NOT NULL,
       salary DOUBLE NOT NULL,
       position VARCHAR(255) NOT NULL
   );
   ```
4. Pastikan MySQL berjalan pada `localhost` dan port default `3306`.
5. Ubah kredensial database di bagian berikut pada kode jika diperlukan:
   ```java
   String url = "jdbc:mysql://localhost:3306/uap_proglan";
   String user = "root"; // Ganti sesuai username MySQL Anda
   String password = ""; // Ganti sesuai password MySQL Anda
   ```

### **2. Menjalankan Program**
1. Pastikan Anda memiliki JDK yang terinstal (minimal JDK 8).
2. Kompilasi dan jalankan program:
   ```bash
   javac Main.java
   java Main
   ```
3. Jendela aplikasi akan terbuka dengan tabel kosong jika database berhasil terkoneksi.

### **3. Menggunakan Aplikasi**
- **Menambah Data:** Masukkan data di kolom input, lalu klik tombol "Add Employee".
- **Menghapus Data:** Pilih baris data di tabel, lalu klik tombol "Delete Selected".
- **Mencari Data:** Klik tombol "Search", masukkan nama karyawan, lalu klik OK.
- **Mengurutkan Data:** Klik tombol "Sort by Salary".

---

## Strukur Kode
1. **Employee Class**
   - Menyimpan informasi karyawan (nama, umur, gaji, posisi).

2. **Main Class**
   - Membuat GUI, mengatur interaksi pengguna, dan koneksi ke database.
   - Fungsi utama:
     - `connectToDatabase()`: Menghubungkan aplikasi ke database.
     - `loadDataFromDatabase()`: Memuat data karyawan dari database.
     - `saveToDatabase()`: Menyimpan data karyawan ke database.
     - `deleteFromDatabase()`: Menghapus data karyawan dari database.

---

## Dependensi
- **JDBC Driver MySQL**
  Pastikan Anda memiliki file `mysql-connector-java.jar` dan tambahkan ke classpath saat menjalankan program.
  ```bash
  java -cp .:mysql-connector-java.jar Main
  ```

---

## Catatan Penting
- Pastikan koneksi database berhasil sebelum menggunakan aplikasi.
- Jika ada error pada koneksi database, periksa konfigurasi koneksi (URL, username, password).
- Input data harus valid:
  - `Age`: Harus berupa angka.
  - `Salary`: Harus berupa angka desimal.

---

## Pengembangan Lebih Lanjut
Anda dapat mengembangkan aplikasi ini lebih lanjut dengan fitur berikut:
- Menambahkan validasi input lebih lengkap.
- Menambahkan fitur update/edit data karyawan.
- Mengintegrasikan fitur export/import data ke format Excel atau CSV.
- Mengimplementasikan autentikasi pengguna.

---

## Kontak
Jika Anda memiliki pertanyaan atau saran, silakan hubungi pembuat aplikasi melalui email: [ilhamakbarjamil8.com] && [farhanuzie77@webmail.umm.ac.id].



Pengujian (Testing)
Berikut adalah beberapa pengujian yang disarankan untuk memastikan kode berfungsi dengan baik:

1. Unit Testing untuk Database Connection

Gunakan framework seperti JUnit untuk memeriksa koneksi database:

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    @Test
    void testDatabaseConnection() {
        Main mainApp = new Main();
        Connection connection = mainApp.connectToDatabase();
        assertNotNull(connection, "Database connection should not be null");
    }
}
2. Unit Testing untuk CRUD

Pengujian untuk fungsi menyimpan, mengambil, dan menghapus data dari database.

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeCRUDTest {
    @Test
    void testSaveToDatabase() {
        Main mainApp = new Main();
        Employee emp = new Employee("Test", 25, 5000, "Engineer");

        assertDoesNotThrow(() -> mainApp.saveToDatabase(emp));
    }

    @Test
    void testDeleteFromDatabase() {
        Main mainApp = new Main();
        Employee emp = new Employee("Test", 25, 5000, "Engineer");

        assertDoesNotThrow(() -> mainApp.deleteFromDatabase(emp));
    }
}
3. Functional Testing untuk GUI

Untuk pengujian GUI, library seperti AssertJ atau Fest dapat digunakan untuk memvalidasi interaksi dengan antarmuka.




Jika Anda ingin menambahkan fitur Update Gaji pada program yang sudah Anda miliki, berikut adalah langkah-langkah dan bagian kode yang perlu ditambahkan:

1. Tambahkan Tombol Update Gaji
Pada bagian UI, tambahkan tombol Update Gaji. Cari bagian kode yang mendefinisikan tombol-tombol di dalam buttonPanel, dan tambahkan kode berikut:

JButton updateSalaryButton = new JButton("Update Gaji");
buttonPanel.add(updateSalaryButton);
2. Logika untuk Tombol Update Gaji
Tambahkan logika ketika tombol Update Gaji ditekan. Ini adalah bagian kode yang akan meminta gaji baru dari pengguna, memperbarui data karyawan, dan menyimpannya ke database:

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

                // Perbarui data di tabel dan daftar karyawan
                Employee employee = employeeList.get(selectedRow);
                employee.gaji = gajiBaru;

                tablemodel.setValueAt(gajiBaru, selectedRow, 2);

                // Perbarui data di database
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
3. Fungsi untuk Update Gaji di Database
Tambahkan fungsi untuk memperbarui gaji karyawan di database. Tambahkan metode berikut di kelas Anda:

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
