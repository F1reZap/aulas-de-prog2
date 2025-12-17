import java.util.Random;
import java.util.List;

/**
 * Game principal com mecânicas estendidas:
 * - facas (item) que podem ser encontradas com look em "cozinha"
 * - relíquia (item) encontrada em "cofre"; voltar à "entrada" com a relíquia vence o jogo
 * - monstro: pode estar em apenas uma sala; chance 1/10 de surgir em sala adjacente ao jogador
 * - se o monstro estiver adjacente, aparece o aviso de terror
 * - se o jogador entrar na sala do monstro: sem facas => morte; com facas => perde 1 faca e monstro some
 * - se look causar rangido, monstro é colocado em sala adjacente e inicia countdown de 5s; se jogador não sair, monstro entra na sala e ataca
 */
public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom = null;
    private Room startRoom = null;

    // monstro: só existe em no máximo 1 sala ao mesmo tempo
    private Room monsterRoom = null;

    // inventário
    private int knives = 0;
    private boolean hasRelic = false;

    // rangido (tábua)
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
        Room entrada = new Room("na entrada da mansão");
        Room hall = new Room("no hall principal");
        Room cozinha = new Room("na cozinha");
        Room estufa = new Room("na estufa");
        Room corredor1 = new Room("no corredor 1");
        Room corredor2 = new Room("no corredor 2");
        Room cofre = new Room("no cofre");
        Room salaoDoCofre = new Room("no salão do cofre");
        Room quarto1 = new Room("no quarto 1");
        Room quarto2 = new Room("no quarto 2");

        // Segundo andar
        Room hallSup = new Room("no hall superior");
        Room biblioteca = new Room("na biblioteca");
        Room corredor3 = new Room("no corredor 3");
        Room corredor4 = new Room("no corredor 4");
        Room corredor5 = new Room("no corredor 5");
        Room corredor6 = new Room("no corredor 6");
        Room escritorio = new Room("no escritório");
        Room quartoMestre = new Room("no quarto mestre");
        Room banheiro = new Room("no banheiro");
        Room varanda = new Room("na varanda");

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

        // posição inicial
        currentRoom = entrada;
        startRoom = entrada;

        // coloca os itens nas salas específicas
        cozinha.setHasKnives(true);   // facas ficam na cozinha (pegas ao usar look)
        cofre.setHasRelic(true);      // relíquia no cofre (pega ao usar look)

        // monstro inicialmente não aparece (aparecerá com chance 1/10)
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
        System.out.println(currentRoom.getLongDescription());
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
        System.out.println(currentRoom.getLongDescription());

        // primeiro checamos a possibilidade de spawn (1/10)
        spawnAdjacentMonsterChance();

        // agora avaliaremos o estado em relação ao monstro
        if (monsterRoom != null && monsterRoom == currentRoom) {
            // monstro na mesma sala -> encontro imediato
            handleMonsterEncounter();
        } else if (monsterRoom != null && isMonsterAdjacent()) {
            // monstro existe e está em uma sala adjacente
            System.out.println("você sente um terror indescritivel percorrer seu corpo, escolha seus próximos passos com sabedoria...");
        } else if (monsterRoom != null) {
            // monstro existe, mas não é adjacente nem na mesma sala
            System.out.println("Você está a salvo... Por enquanto");
        } // se monsterRoom == null -> não mostramos nada

        // se o jogador tem a relíquia e voltou à entrada => vence
        if (hasRelic && currentRoom == startRoom) {
            System.out.println("Você escapa com Sucesso");
            System.exit(0);
        }
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
        System.out.println(currentRoom.getLongDescription());
        previousRoom = null; // opcional: limpa após voltar

        // spawn possível antes de checar
        spawnAdjacentMonsterChance();

        if (monsterRoom != null && monsterRoom == currentRoom) {
            handleMonsterEncounter();
        } else if (monsterRoom != null && isMonsterAdjacent()) {
            System.out.println("você sente um terror indescritivel percorrer seu corpo, escolha seus próximos passos com sabedoria...");
        } else if (monsterRoom != null) {
            System.out.println("Você está a salvo... Por enquanto");
        }
    }

    /**
     * look: mostra a descrição longa do quarto atual, recolhe facas/relicas se existirem,
     * tem 1/3 de chance de fazer uma tábua ranger (quando isso acontece, o monstro é colocado
     * em uma sala adjacente e inicia uma contagem de 5s).
     */
    public void look()
    {
        System.out.println(currentRoom.getLongDescription());

        // coleta facas automaticamente se houverem na sala
        if (currentRoom.hasKnives()) {
            knives += 1;
            currentRoom.setHasKnives(false);
            System.out.println("Você encontrou Facas! Agora você tem " + knives + " faca(s).");
        }

        // coleta relíquia automaticamente
        if (currentRoom.hasRelic()) {
            hasRelic = true;
            currentRoom.setHasRelic(false);
            System.out.println("Você pegou a Relíquia! Volte à entrada para escapar com sucesso.");
        }

        // chance 1 em 3 rangido
        int chance = random.nextInt(3); 
        if (chance == 0 && !rangido) {
            System.out.println("Uma tábua range quando você pisa.");
            this.rangido = true;

            // coloca o monstro em uma sala adjacente ao jogador e inicia timer
            placeMonsterAdjacentTo(currentRoom);
            if (monsterRoom != null) {
                System.out.println("você escuta passadas vindo em sua direção, CORRA");
                startMonsterTimerForRoom(currentRoom);
            }
        } else {
            // caso não tenha rangido, ainda existe a chance 1/10 de spawn adjacente
            spawnAdjacentMonsterChance();
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

    // ----------------- Pdiddy / UTILS -----------------

    /**
     * Retorna true se monsterRoom é vizinha (adjacente) à currentRoom.
     */
    private boolean isMonsterAdjacent()
    {
        if (monsterRoom == null) return false;
        List<Room> neighbors = currentRoom.getNeighbors();
        return neighbors.contains(monsterRoom);
    }

    /**
     * Chance 1/10 de colocar o monstro em alguma sala adjacente (se o monstro não existir ainda).
     * Quando colocado, exibimos a mensagem de terror.
     */
    private void spawnAdjacentMonsterChance()
    {
        if (monsterRoom != null) return;
        if (random.nextInt(10) == 0) {
            placeMonsterAdjacentTo(currentRoom);
            if (monsterRoom != null) {
                System.out.println("você sente um terror indescritivel percorrer seu corpo, escolha seus próximos passos com sabedoria...");
            }
        }
    }

    /**
     * Coloca o monstro em uma sala aleatória adjacente à sala passada como parâmetro.
     */
    private synchronized void placeMonsterAdjacentTo(Room room)
    {
        List<Room> neigh = room.getNeighbors();
        if (neigh.isEmpty()) return;
        Room chosen = neigh.get(random.nextInt(neigh.size()));
        if (monsterRoom != null) {
            monsterRoom.setHasMonster(false);
        }
        monsterRoom = chosen;
        monsterRoom.setHasMonster(true);
    }

    /**
     * Inicia uma contagem de 5s a partir do momento em que o rangido ocorreu naquele 'alertRoom'.
     * Se ao final do tempo o jogador ainda estiver na mesma sala (não saiu), o monstro é colocado
     * na mesma sala do jogador e o encontro ocorre.
     */
    private void startMonsterTimerForRoom(Room alertRoom)
    {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
            synchronized (Game.this) {
                // se jogador ainda está na mesma sala alertada e o monstro ainda existe em alguma sala
                if (currentRoom == alertRoom && monsterRoom != null) {
                    // move monstro para a sala do jogador
                    if (monsterRoom != null) {
                        monsterRoom.setHasMonster(false);
                    }
                    monsterRoom = currentRoom;
                    monsterRoom.setHasMonster(true);

                    System.out.println("O monstro entrou na sua sala!");
                    handleMonsterEncounter();
                } else {
                    // jogador saiu da sala a tempo;
                }
            }
        }).start();
    }

    /**
     * Resolve um encontro em que o monstro e o jogador estão na mesma sala.
     * Se jogador tiver facas -> perde 1 faca e monstro some.
     * Se não tiver -> morre (jogo termina).
     */
    private synchronized void handleMonsterEncounter()
    {
        if (monsterRoom == null || monsterRoom != currentRoom) return;

        if (knives > 0) {
            System.out.println("O monstro te ataca porem sua faca te salva");
            knives -= 1;
            // faz o monstro sair da sala 
            monsterRoom.setHasMonster(false);
            monsterRoom = null;
        } else {
            System.out.println("O monstro te ataca sem chance de você revidar");
            System.out.println("Você morreu.");
            System.exit(0);
        }
    }
}