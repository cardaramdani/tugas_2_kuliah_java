// File: Diskon.java
public class Diskon extends MenuItem {
    final private double diskon;

    public Diskon(String nama, double harga, String kategori, double diskon) {
        super(nama, harga, kategori);
        this.diskon = diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + getNama() + " - Diskon: " + diskon + "%");
    }

    public double getDiskon() {
        return diskon;
    }
}
