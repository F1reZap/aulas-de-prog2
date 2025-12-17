import java.util.Random;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom = null;
    private boolean rangido = false;
    private final Random random = new Random();

    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    private void createRooms()
    {
        // Primeiro andar (térreo)
        Room entrada = new Room("-");
        Room hall = new Room("-");
        Room cozinha = new Room("-");
        Room estufa = new Room("-");
        Room corredor1 = new Room("-");
        Room corredor2 = new Room("-");
        Room cofre = new Room("-");
        Room salaoDoCofre = new Room("-");
        Room quarto1 = new Room("-");
        Room quarto2 = new Room("-");

        // Segundo andar
        Room hallSup = new Room("-");
        Room biblioteca = new Room("-");
        Room corredor3 = new Room("-");
        Room corredor4 = new Room("-"); 
        Room corredor5 = new Room("-");
        Room corredor6 = new Room("-");
        Room escritorio = new Room("-");
        Room quartoMestre = new Room("-");
        Room banheiro = new Room("-");
        Room varanda = new Room("-"); 

        // Conexões térreo
        entrada.setExit("north", hall);
        hall.setExit("south", entrada);

        hall.setExit("north", corredor1);
        hall.setExit("south", corredor2); 
        corredor2.setExit("north", hall);

        corredor2.setExit("south", estufa);
        estufa.setExit("north", corredor2);

        cozinha.setExit("north", hall);
        hall.setExit("south", cozinha);

        cozinha.setExit("east", estufa);
        estufa.setExit("west", cozinha);

        corredor1.setExit("north", quarto1);
        quarto1.setExit("south", corredor1);
        corredor1.setExit("east", quarto2); 
        quarto2.setExit("west", corredor1);

        hall.setExit("north", salaoDoCofre);
        salaoDoCofre.setExit("south", hall);
        salaoDoCofre.setExit("north", cofre);
        cofre.setExit("south", salaoDoCofre);

        // Conexões segundo andar
        hall.setExit("up", hallSup);
        hallSup.setExit("down", hall);

        hallSup.setExit("south", corredor5);
        corredor5.setExit("north", hallSup);
        corredor5.setExit("east", biblioteca);
        biblioteca.setExit("west", corredor5);

        hallSup.setExit("north", corredor3);
        corredor3.setExit("south", hallSup);
        corredor3.setExit("east", corredor6);
        corredor6.setExit("west", corredor3);
        corredor6.setExit("north", quartoMestre);
        quartoMestre.setExit("south", corredor6);
        corredor6.setExit("west", escritorio);
        escritorio.setExit("east", corredor6);

        corredor3.setExit("west", banheiro);
        banheiro.setExit("east", corredor3);

        currentRoom = entrada;
    }

    public void play()
    {
        printWelcome();
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bem-vindo à Mansão Assombrada!");
        System.out.println("Explore a mansão procurando as chaves necessárias para a sua fuga.");
        System.out.println("Type 'help' se precisar de ajuda.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: " + currentRoom.getExitString());
        System.out.println();
    }

    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
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
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    private void printHelp()
    {
        System.out.println("Você está perdido em uma mansão assustadora.");
        System.out.println("Uma entidade o persegue a cada movimento.");
        System.out.println();
        System.out.println("Comandos disponíveis:");
        System.out.println("   go   back   look   quit   help");
    }

    private void goRoom(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: " + currentRoom.getExitString());
            System.out.println();
        }
    }

    private void goBack()
    {
        if (previousRoom == null) {
            System.out.println("Não há sala anterior.");
            return;
        }
        currentRoom = previousRoom;
        System.out.println("Você voltou.");
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: " + currentRoom.getExitString());
        System.out.println();
        previousRoom = null; // opcional: limpa após voltar
    }

    public void look()
    {
        System.out.println(currentRoom.getLongDescription());

        int chance = random.nextInt(3);
        if (chance == 0 && !rangido) {
            System.out.println("Uma tábua range quando você pisa.");
            rangido = true;
        }
    }

    private boolean quit(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }
}