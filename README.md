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
