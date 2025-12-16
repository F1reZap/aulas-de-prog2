/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.Random;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    private void createRooms()
    {
        Room outside, theater, pub, lab, office, camarim, despensa;

        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        // novas salas:
        camarim = new Room("in the dressing room");
        despensa = new Room("in the pantry");

        // inicializa as saídas
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);
        theater.setExit("down", camarim);

        camarim.setExit("up", theater);

        pub.setExit("east", outside);
        pub.setExit("up", despensa);

        despensa.setExit("down", pub);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  //joguin começa no meio do mato msm

        //eu ia traduzir tudo mais 153 linha slc nn compensa, fica só despensa e camarim na divina linguagem do PT-BR
    }

    public void play() 
    {            
        printWelcome();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: " + currentRoom.getExitString());
        System.out.println();
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit look help");
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Usa uma nova forma "de super sayajin" para recuperar as salas
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: " + currentRoom.getExitString());
            System.out.println();
        }
    }

    // variável que indica se ocorreu o rangido
    private boolean rangido = false;

    // Random compartilhado (ou crie um local dentro do método)
    private final Random random = new Random();

    // Getter para rangido
    public boolean isRangido() {
        return rangido;
    }

    // Setter para rangido (se precisar alterar externamente)
    public void setRangido(boolean rangido) {
        this.rangido = rangido;
    }

    /**
     * look: mostra a descrição longa do quarto atual e tem 1/3 de chance
     * de adicionar a frase "uma tábua range quando você pisa" e setar rangido=true.
     */
    public void look() {
        // supondo que exista currentRoom e que ele tenha getLongDescription()
        System.out.println(currentRoom.getLongDescription());

        // chance 1 em 3
        int chance = random.nextInt(3); // 0,1,2
        if (chance == 0) {
            System.out.println("Uma tábua range quando você pisa.");
            this.rangido = true;
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}
