
public class Exit {
    private Room neighbor;
    private boolean locked;

    public Exit(Room neighbor, boolean locked) {
        this.neighbor = neighbor;
        this.locked = locked;
    }

    public Room getNeighbor() {
        return neighbor;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}