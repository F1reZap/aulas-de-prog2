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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Room
{
    private String description;
    private String longDescription;
    private HashMap<String, Room> exits;

    // itens / estado nesta sala
    private boolean hasMonster = false;
    private boolean hasKnives = false;
    private boolean hasRelic = false;

    public Room(String description)
    {
        this.description = description;
        this.longDescription = "Você está " + description + ".";
        exits = new HashMap<String, Room>();
    }

    // Adiciona uma saida numa direção especifica
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    // Recupera a sala associada a direção anterior
    public Room getExit(String direction)
    {
        return exits.get(direction);
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
    public boolean hasMonster() {
        return hasMonster;
    }
    public void setHasMonster(boolean v) {
        hasMonster = v;
    }

    public boolean hasKnives() {
        return hasKnives;
    }
    public void setHasKnives(boolean v) {
        hasKnives = v;
    }

    public boolean hasRelic() {
        return hasRelic;
    }
    public void setHasRelic(boolean v) {
        hasRelic = v;
    }

    /**
     * Retorna a lista de salas vizinhas (valores do mapa de exits).
     */
    public List<Room> getNeighbors() {
        return new ArrayList<Room>(exits.values());
    }
}