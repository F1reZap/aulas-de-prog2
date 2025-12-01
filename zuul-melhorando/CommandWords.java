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
    // Adiciona as novas direções dos meus comandos divinos muahahahahahahhahahahahahahhahahahahahahahahahhahahaha
    //não funcinou então voltei pros padrões >:( jogo chatão
    private static final String[] validCommands = {
        "go", "quit", "help"
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

//sim eu apaguei aqls textão, ngm lê mais que 2 linhas hj em dia msm...