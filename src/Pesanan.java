import java.util.ArrayList;
class Pesanan {
    final private ArrayList<MenuItem> itemsDipesan;

    public Pesanan() {
        this.itemsDipesan = new ArrayList<>();
    }

    public void tambahPesanan(MenuItem item) {
        itemsDipesan.add(item);
    }

    public void tampilkanPesanan() {
        for (MenuItem item : itemsDipesan) {
            item.tampilMenu();
        }
    }

    public double hitungTotal() {
        double total = 0;
        for (MenuItem item : itemsDipesan) {
            total += item.getHarga();
        }
        return total;
    }

    public ArrayList<MenuItem> getItemsDipesan() {
        return itemsDipesan;
    }
}