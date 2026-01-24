public class Player {
    private final Inventory inventory = new Inventory();
    private boolean rangido = false;

    public Player() {}

    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasRangido() {
        return rangido;
    }

    public void setRangido(boolean v) {
        rangido = v;
    }
}