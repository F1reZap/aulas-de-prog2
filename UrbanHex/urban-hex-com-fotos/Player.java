//coisas relacionadas ao jogador

public class Player {
    private final Inventory inventory = new Inventory();
    private boolean rangido = false;

    public Player() {}
    //conexão ao inventário
    public Inventory getInventory() {
        return inventory;
    }
    //rangidos
    public boolean hasRangido() {
        return rangido;
    }

    public void setRangido(boolean v) {
        rangido = v;
    }
}