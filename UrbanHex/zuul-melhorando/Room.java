/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.HashMap;

public class Room 
{
    private String description;
    private String LongDescription;
    private HashMap<String, Room> exits;

    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        this.LongDescription = LongDescription;
        exits = new HashMap<String, Room>();
    }

    // Adiciona uma saida numem uma direção especifica tendeu?
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    // Recupera a sala associada a direção anterior (é quase auto explicativo)
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
        return LongDescription;
    }

    // Exibe as direções que existem nesse jogo de maluco
    public String getExitString() {
        return String.join(" ", exits.keySet());
    }
}