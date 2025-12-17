/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 *
 * A "Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. The exits are labelled north,
 * east, south, west (and up/down). For each direction, the room stores a
 * reference to the neighboring room.
 *
 * Added: flags for items (knives, relic) and for monster presence.
 */
/**
 * Class Room - uma sala do jogo.
 * Agora cada saída é um objeto Exit (vizinho + locked flag).
 * Também suporta itens simples: facas, relíquia e chaves.
 */
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Room
{
    private String description;
    private String longDescription;
    private HashMap<String, Exit> exits;

    // itens / estado nesta sala
    private boolean hasMonster = false;
    private boolean hasKnives = false;
    private boolean hasRelic = false;
    private boolean hasKey = false;

    public Room(String description)
    {
        this.description = description;
        this.longDescription = "Você está " + description + ".";
        exits = new HashMap<String, Exit>();
    }

    // Adiciona uma saida numa direção especifica 
    public void setExit(String direction, Room neighbor) {
        setExit(direction, neighbor, false);
    }

    // Versão que permite marcar a saída como trancada
    public void setExit(String direction, Room neighbor, boolean locked) {
        exits.put(direction, new Exit(neighbor, locked));
    }

    // Recupera a saída 
    public Exit getExit(String direction) {
        return exits.get(direction);
    }

    // Recupera a sala vizinha para compatibilidade 
    public Room getExitRoom(String direction) {
        Exit e = exits.get(direction);
        return (e == null) ? null : e.getNeighbor();
    }

    public String getDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(longDescription);

        if (hasKnives) {
            sb.append("\nHá um conjunto de facas aqui.");
        }
        if (hasRelic) {
            sb.append("\nUma relíquia brilhante repousa aqui.");
        }
        if (hasKey) {
            sb.append("\nUma chave está aqui, brilhando no chão.");
        }
        if (hasMonster) {
            sb.append("\nAlgo parece estar aqui com você... (algo ruge)");
        }

        String exitString = getExitString();
        if (!exitString.isEmpty()) {
            sb.append("\nExits: ").append(exitString);
        }

        return sb.toString();
    }

    // Exibe as direções que existem nesse jogo
    public String getExitString() {
        return String.join(" ", exits.keySet());
    }

    // --- métodos para itens / monstro ---
    public boolean hasMonster() { return hasMonster; }
    public void setHasMonster(boolean v) { hasMonster = v; }

    public boolean hasKnives() { return hasKnives; }
    public void setHasKnives(boolean v) { hasKnives = v; }

    public boolean hasRelic() { return hasRelic; }
    public void setHasRelic(boolean v) { hasRelic = v; }

    public boolean hasKey() { return hasKey; }
    public void setHasKey(boolean v) { hasKey = v; }

    /**
     * Retorna a lista de salas vizinhas.
     */
    public List<Room> getNeighbors() {
        List<Room> list = new ArrayList<>();
        for (Exit e : exits.values()) {
            list.add(e.getNeighbor());
        }
        return list;
    }

    /**
     * Retorna true se existe alguma saída trancada 
     */
    public boolean hasLockedExit() {
        for (Exit e : exits.values()) {
            if (e.isLocked()) return true;
        }
        return false;
    }

    /**
     * Procura uma saída que aponte para 'target' e destravar
     */
    public void unlockExitTo(Room target) {
        for (Map.Entry<String, Exit> en : exits.entrySet()) {
            Exit e = en.getValue();
            if (e.getNeighbor() == target && e.isLocked()) {
                e.setLocked(false);
                // como requisito: só uma saída trancada por sala, então podemos parar
                return;
            }
        }
    }
}