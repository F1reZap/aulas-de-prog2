/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    private static final String[] validCommands = {
        "go", "quit", "help", "look"
    };

    public CommandWords()
    {}

    public boolean isCommand(String aString)
    {
        for(String cmd : validCommands)
            if(cmd.equals(aString))
                return true;
        return false;
    }
}

