import java.util.ArrayList;

class Menu {
    final private ArrayList<MenuItem> menuItems;

    public Menu() {
        this.menuItems = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        menuItems.add(item);
    }

    public void tampilkanMenu() {
        for (MenuItem item : menuItems) {
            item.tampilMenu();
        }
    }

    public MenuItem cariMenu(String nama) {
        for (MenuItem item : menuItems) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        return null;
    }

    public void hapusItem(MenuItem item) {
        menuItems.remove(item);
    }
}
