public class Inventory {
    private int knives = 0;
    private int keys = 0;
    private boolean hasRelic = false;

    public Inventory() {}

    public int getKnives() {
        return knives;
    }

    public void addKnife() {
        knives += 1;
    }

    public boolean useKnife() {
        if (knives > 0) {
            knives -= 1;
            return true;
        }
        return false;
    }

    public int getKeys() {
        return keys;
    }

    public void addKey() {
        keys += 1;
    }

    public void addKeys(int n) {
        keys += n;
    }

    public boolean useKey() {
        if (keys > 0) {
            keys -= 1;
            return true;
        }
        return false;
    }

    public boolean hasRelic() {
        return hasRelic;
    }

    public void pickRelic() {
        hasRelic = true;
    }
}