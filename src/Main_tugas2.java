import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main_tugas2 {
    private static final ArrayList<Menu_tugas2> menuList = new ArrayList<>();
    private static final ArrayList<String> pesanan = new ArrayList<>();
    private static final ArrayList<Integer> jumlahPesanan = new ArrayList<>();
    private static final Map<String, String> users = new HashMap<>();

    private static boolean isLoggedIn = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inisialisasiMenu();
        inisialisasiUsers();

        while (true) {
            System.out.println("Menu Utama:");
            System.out.println("1. Pesan Makanan/Minuman");
            System.out.println("2. Manajemen Menu Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1" -> menuPemesanan(scanner);
                case "2" -> {
                    if (isLoggedIn || login(scanner)) {
                        menuPengelolaan(scanner);
                    } else {
                        System.out.println("Login gagal. Silakan coba lagi.");
                    }
                }
                case "3" -> {
                    System.out.println("Terima kasih! Sampai jumpa lagi dikedai carda ramdani.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void inisialisasiMenu() {
        menuList.add(new Menu_tugas2("Nasi Padang", 25000, "Makanan"));
        menuList.add(new Menu_tugas2("Ayam Goreng", 20000, "Makanan"));
        menuList.add(new Menu_tugas2("Sate Ayam", 30000, "Makanan"));
        menuList.add(new Menu_tugas2("Soto Ayam", 22000, "Makanan"));
        
        menuList.add(new Menu_tugas2("Es Teh", 5000, "Minuman"));
        menuList.add(new Menu_tugas2("Jus Jeruk", 15000, "Minuman"));
        menuList.add(new Menu_tugas2("Kopi", 10000, "Minuman"));
        menuList.add(new Menu_tugas2("Air Mineral", 3000, "Minuman"));
    }

    private static void inisialisasiUsers() {
        // Pengguna yang diizinkan untuk mengakses manajemen menu
        users.put("admin", "password123");
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            isLoggedIn = true;
            System.out.println("Login berhasil.");
            return true;
        } else {
            System.out.println("Username atau password salah.");
            return false;
        }
    }

    private static void menuPemesanan(Scanner scanner) {
        tampilkanMenu();
        System.out.println("\nMasukkan pesanan Anda (tulis dengan format format: Nama Menu = Jumlah pesanan). Ketik 'selesai' untuk menyelesaikan pesanan:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                break;
            }
            String[] parts = input.split(" = ");
            if (parts.length == 2) {
                String namaMenu = parts[0];
                try {
                    int jumlah = Integer.parseInt(parts[1]);
                    if (isValidMenu(namaMenu)) {
                        pesanan.add(namaMenu);
                        jumlahPesanan.add(jumlah);
                    } else {
                        System.out.println("Menu tidak ditemukan. Silakan coba lagi.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Jumlah harus berupa angka. Silakan coba lagi.");
                }
            } else {
                System.out.println("Format input salah. Silakan coba lagi.");
            }
        }

        double totalBiaya = hitungTotalBiaya();
        double[] diskonDanTotalSetelahDiskon = hitungDiskon(totalBiaya);
        double totalSetelahDiskon = diskonDanTotalSetelahDiskon[1];
        double totalDenganPajakDanPelayanan = totalSetelahDiskon * 1.1 + 20000;

        cetakStruk(totalDenganPajakDanPelayanan, totalBiaya, diskonDanTotalSetelahDiskon[0]);
        pesanan.clear();
        jumlahPesanan.clear();
    }

    private static boolean isValidMenu(String namaMenu) {
        for (Menu_tugas2 menu : menuList) {
            if (menu.getNama().equalsIgnoreCase(namaMenu)) {
                return true;
            }
        }
        return false;
    }

    private static void tampilkanMenu() {
        System.out.println("Menu Restoran:");
        System.out.println("Makanan:");
        for (Menu_tugas2 menu : menuList) {
            if (menu.getKategori().equalsIgnoreCase("Makanan")) {
                System.out.println(menu.getNama() + " - Rp " + menu.getHarga());
            }
        }
        System.out.println("Minuman:");
        for (Menu_tugas2 menu : menuList) {
            if (menu.getKategori().equalsIgnoreCase("Minuman")) {
                System.out.println(menu.getNama() + " - Rp " + menu.getHarga());
            }
        }
    }

    private static double hitungTotalBiaya() {
        double total = 0;
        for (int i = 0; i < pesanan.size(); i++) {
            for (Menu_tugas2 menu : menuList) {
                if (menu.getNama().equalsIgnoreCase(pesanan.get(i))) {
                    total += menu.getHarga() * jumlahPesanan.get(i);
                }
            }
        }
        return total;
    }

    private static double[] hitungDiskon(double totalBiaya) {
        double diskon = 0;
        if (totalBiaya > 100000) {
            diskon = totalBiaya * 0.1;
            totalBiaya *= 0.9;
        } else if (totalBiaya > 50000) {
            for (int i = 0; i < pesanan.size(); i++) {
                for (Menu_tugas2 menu : menuList) {
                    if (menu.getNama().equalsIgnoreCase(pesanan.get(i)) && menu.getKategori().equalsIgnoreCase("Minuman")) {
                        diskon = menu.getHarga();
                        totalBiaya -= menu.getHarga();
                        break;
                    }
                }
            }
        }
        return new double[]{diskon, totalBiaya};
    }

    private static void cetakStruk(double totalDenganPajakDanPelayanan, double totalBiaya, double totalDiskon) {
        System.out.println("\nStruk Pesanan:");
        System.out.println("Item Pesanan:");
        for (int i = 0; i < pesanan.size(); i++) {
            for (Menu_tugas2 menu : menuList) {
                if (menu.getNama().equalsIgnoreCase(pesanan.get(i))) {
                    System.out.println(pesanan.get(i) + " x " + jumlahPesanan.get(i) + " = Rp " + (menu.getHarga() * jumlahPesanan.get(i)));
                }
            }
        }
        System.out.println("Total Biaya: Rp " + totalBiaya);
        System.out.println("Total Diskon: Rp " + totalDiskon);
        System.out.println("Total Setelah Diskon: Rp " + (totalBiaya - totalDiskon));
        System.out.println("Pajak (10%): Rp " + ((totalBiaya - totalDiskon) * 0.1));
        System.out.println("Biaya Pelayanan: Rp 20000");
        System.out.println("Total Bayar: Rp " + totalDenganPajakDanPelayanan);
    }

    private static void menuPengelolaan(Scanner scanner) {
        while (true) {
            System.out.println("Menu Pengelolaan Restoran:");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih opsi: ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1" -> tambahMenu(scanner);
                case "2" -> ubahHargaMenu(scanner);
                case "3" -> hapusMenu(scanner);
                case "4" -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void tambahMenu(Scanner scanner) {
        System.out.print("Masukkan nama menu baru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga menu baru: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();  // clear the buffer
        System.out.print("Masukkan kategori menu baru (Makanan/Minuman): ");
        String kategori = scanner.nextLine();

        menuList.add(new Menu_tugas2(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahHargaMenu(Scanner scanner) {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah harganya: ");
        String nama = scanner.nextLine();

        for (Menu_tugas2 menu : menuList) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                System.out.print("Masukkan harga baru: ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine();  // clear the buffer

                // Konfirmasi
                System.out.print("Apakah Anda yakin ingin mengubah harga menu ini? (Ya/Tidak): ");
                String konfirmasi = scanner.nextLine();
                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    menu.setHarga(hargaBaru);
                    System.out.println("Harga menu berhasil diubah.");
                } else {
                    System.out.println("Harga menu tidak jadi diubah.");
                }
                return;
            }
        }
        System.out.println("Menu tidak ditemukan.");
    }

    private static void hapusMenu(Scanner scanner) {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = scanner.nextLine();

        for (Menu_tugas2 menu : menuList) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                // Konfirmasi
                System.out.print("Apakah Anda yakin ingin menghapus menu ini? (Ya/Tidak): ");
                String konfirmasi = scanner.nextLine();
                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    menuList.remove(menu);
                    System.out.println("Menu berhasil dihapus.");
                } else {
                    System.out.println("Menu tidak jadi dihapus.");
                }
                return;
            }
        }
        System.out.println("Menu tidak ditemukan.");
    }
}
