// File: Main.java
import java.util.Scanner;

public class Main {
    final private static Menu menuRestoran = new Menu();
    private static Pesanan pesananPelanggan = new Pesanan();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inisialisasiMenu();

        while (true) {
            System.out.println("Menu Utama:");
            System.out.println("1. Pesan Makanan/Minuman");
            System.out.println("2. Manajemen Menu Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1" -> menuPemesanan(scanner);
                case "2" -> menuPengelolaan(scanner);
                case "3" -> {
                    System.out.println("Terima kasih! Sampai jumpa lagi di kedai.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void inisialisasiMenu() {
        menuRestoran.tambahItem(new Makanan("Nasi Padang", 25000, "Makanan", "Utama"));
        menuRestoran.tambahItem(new Makanan("Ayam Goreng", 20000, "Makanan", "Lauk"));
        menuRestoran.tambahItem(new Makanan("Sate Ayam", 30000, "Makanan", "Lauk"));
        menuRestoran.tambahItem(new Makanan("Soto Ayam", 10000, "Makanan", "Lauk"));

        menuRestoran.tambahItem(new Minuman("Es Teh", 5000, "Minuman", "Dingin"));
        menuRestoran.tambahItem(new Minuman("Kopi", 10000, "Minuman", "Panas"));
        menuRestoran.tambahItem(new Diskon("Diskon Spesial", 0, "Diskon", 10));
    }

    private static void menuPemesanan(Scanner scanner) {
        menuRestoran.tampilkanMenu();
        System.out.println("\nMasukkan pesanan Anda (Nama Menu). Ketik 'selesai' untuk menyelesaikan pesanan:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            MenuItem item = menuRestoran.cariMenu(input);
            if (item != null) {
                pesananPelanggan.tambahPesanan(item);
            } else {
                System.out.println("Menu tidak ditemukan. Silakan coba lagi.");
            }
        }

        double total = pesananPelanggan.hitungTotal();
        double[] hasilDiskon = hitungDiskon(total);
        double totalDiskon = hasilDiskon[0];
        double totalSetelahDiskon = hasilDiskon[1];
        double totalDenganPajakDanPelayanan = totalSetelahDiskon + (totalSetelahDiskon * 0.1) + 2000;

        cetakStruk(totalDenganPajakDanPelayanan, total, totalDiskon);
        pesananPelanggan = new Pesanan(); // Reset pesanan setelah selesai
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
        scanner.nextLine(); // clear the buffer
        System.out.print("Masukkan kategori menu baru (Makanan/Minuman/Diskon): ");
        String kategori = scanner.nextLine();

        if (kategori.equalsIgnoreCase("Makanan")) {
            System.out.print("Masukkan jenis makanan: ");
            String jenisMakanan = scanner.nextLine();
            menuRestoran.tambahItem(new Makanan(nama, harga, kategori, jenisMakanan));
        } else if (kategori.equalsIgnoreCase("Minuman")) {
            System.out.print("Masukkan jenis minuman: ");
            String jenisMinuman = scanner.nextLine();
            menuRestoran.tambahItem(new Minuman(nama, harga, kategori, jenisMinuman));
        } else if (kategori.equalsIgnoreCase("Diskon")) {
            System.out.print("Masukkan persentase diskon: ");
            double diskon = scanner.nextDouble();
            scanner.nextLine(); // clear the buffer
            menuRestoran.tambahItem(new Diskon(nama, harga, kategori, diskon));
        } else {
            System.out.println("Kategori tidak valid.");
        }

        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahHargaMenu(Scanner scanner) {
        menuRestoran.tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah harganya: ");
        String nama = scanner.nextLine();

        MenuItem item = menuRestoran.cariMenu(nama);
        if (item != null) {
            System.out.print("Masukkan harga baru: ");
            double hargaBaru = scanner.nextDouble();
            scanner.nextLine(); // clear the buffer
            item.setHarga(hargaBaru);
            System.out.println("Harga menu berhasil diubah.");
        } else {
            System.out.println("Menu tidak ditemukan.");
        }
    }

    private static void hapusMenu(Scanner scanner) {
        menuRestoran.tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = scanner.nextLine();

        MenuItem item = menuRestoran.cariMenu(nama);
        if (item != null) {
            menuRestoran.hapusItem(item);
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Menu tidak ditemukan.");
        }
    }

    private static double[] hitungDiskon(double totalBiaya) {
        double totalDiskon = 0;
        boolean adaMinuman = false;
    
        // Memeriksa apakah ada item minuman dalam pesanan pelanggan
        for (MenuItem item : pesananPelanggan.getItemsDipesan()) {
            if (item instanceof Minuman) {
                adaMinuman = true;
                break;
            }
        }
    
        // Jika total pesanan lebih dari 100.000, berikan diskon 10%
        if (totalBiaya > 100000) {
            totalDiskon = totalBiaya * 0.10; // Diskon 10%
        } 
        // Jika total pesanan antara 50.000 dan 100.000, beri potongan 1 item minuman jika ada pesanan minuman
        else if (totalBiaya > 50000 && totalBiaya <= 100000) {
            if (adaMinuman) {
                // Mengambil harga item minuman pertama yang dipesan
                for (MenuItem item : pesananPelanggan.getItemsDipesan()) {
                    if (item instanceof Minuman) {
                        totalDiskon = item.getHarga(); // Potongan sesuai harga minuman pertama
                        break;
                    }
                }
            }
        }
    
        double totalSetelahDiskon = totalBiaya - totalDiskon;
        return new double[]{totalDiskon, totalSetelahDiskon};
    }
    

    private static void cetakStruk(double totalDenganPajakDanPelayanan, double totalBiaya, double totalDiskon) {
        System.out.println("\n===== Struk Pesanan =====");
        pesananPelanggan.tampilkanPesanan();
        System.out.println("Total Biaya: Rp " + totalBiaya);
        System.out.println("Total Diskon: Rp " + totalDiskon);
        System.out.println("Total Setelah Diskon: Rp " + (totalBiaya - totalDiskon));
        System.out.println("Pajak (10%): Rp " + (totalBiaya - totalDiskon) * 0.1);
        System.out.println("Biaya Pelayanan: Rp 2000");
        System.out.println("Total Bayar: Rp " + totalDenganPajakDanPelayanan);
        System.out.println("==========================");
    }
}
