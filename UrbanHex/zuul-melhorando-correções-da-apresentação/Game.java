/**
 * Game do Urban Hex (com certeza nn é um zuul modificado):
 * - facas (item) que podem ser encontradas com look em "cozinha" é pavê ou pacumê?
 * - relíquia (item) encontrada em "cofre"; voltar à "entrada" com a relíquia vence o jogo grazaDeus isso tem final
 * - monstro: pode estar em apenas uma sala; chance 1/10 de surgir em sala adjacente ao jogador canonicamente ele é bem buxa
 */

import java.util.Random;
import java.util.List;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom = null;
    private Room startRoom = null;

    // monstro agora controlado por Monster helper
    private Room monsterRoom = null;
    private Monster monsterManager;

    // jogador / inventario
    private final Player player = new Player();

    // rangido foi movido para player.rangido

    private final Random random = new Random();

    public Game()
    {
        parser = new Parser();
        monsterManager = new Monster(this);
        createRooms();
    }

    /**
     * Cria saídas bidirecionais entre duas salas, com chance 1/5 de estar trancada.
     * Garante que, se for escolhida uma saída trancada, ela só será colocada caso
     * nenhuma das salas já tenha saída trancada (evita múltiplas trancas por sala).
     * A flag 'locked' é partilhada nas duas direções (porta é uma entidade única).
     *"Socorro eu quero café mais são 3:00 da manhã e se eu for na cozinha minha mãe acorda"
     */
    private void setBidirectionalExitWithPossibleLock(Room a, String dirA, Room b, String dirB) {
        boolean locked = false;
        // escolhe lock com 1/5 de chance, mas só se nenhuma das salas já tiver saída trancada
        if (!a.hasLockedExit() && !b.hasLockedExit() && random.nextInt(5) == 0) {
            locked = true;
        }
        a.setExit(dirA, b, locked);
        b.setExit(dirB, a, locked);
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

        // Conexões térreo (bidirecionais, corrigidas)
        setBidirectionalExitWithPossibleLock(entrada, "north", hall, "south");

        setBidirectionalExitWithPossibleLock(hall, "north", corredor1, "south");
        setBidirectionalExitWithPossibleLock(hall, "south", corredor2, "north");

        setBidirectionalExitWithPossibleLock(corredor2, "south", estufa, "north");

        setBidirectionalExitWithPossibleLock(cozinha, "north", hall, "south");
        setBidirectionalExitWithPossibleLock(cozinha, "east", estufa, "west");

        setBidirectionalExitWithPossibleLock(corredor1, "north", quarto1, "south");
        setBidirectionalExitWithPossibleLock(corredor1, "east", quarto2, "west");

        setBidirectionalExitWithPossibleLock(hall, "north", salaoDoCofre, "south");
        setBidirectionalExitWithPossibleLock(salaoDoCofre, "north", cofre, "south");

        // Conexões segundo andar
        setBidirectionalExitWithPossibleLock(hall, "up", hallSup, "down");

        setBidirectionalExitWithPossibleLock(hallSup, "south", corredor5, "north");
        setBidirectionalExitWithPossibleLock(corredor5, "east", biblioteca, "west");

        setBidirectionalExitWithPossibleLock(hallSup, "north", corredor3, "south");
        setBidirectionalExitWithPossibleLock(corredor3, "east", corredor6, "west");
        setBidirectionalExitWithPossibleLock(corredor6, "north", quartoMestre, "south");
        setBidirectionalExitWithPossibleLock(corredor6, "west", escritorio, "east");

        setBidirectionalExitWithPossibleLock(corredor3, "west", banheiro, "east");

        // posição inicial
        currentRoom = entrada;
        startRoom = entrada;

        // coloca os itens nas salas específicas
        cozinha.setHasKnives(true);   // facas ficam na cozinha (pegas ao usar look)
        cofre.setHasRelic(true);      // relíquia no cofre (pega ao usar look)
        // keys aparecem aleatoriamente ao usar look
    }

    public void play()
    {
        printWelcome();
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Satisfação aspira. vá em paz.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bem-vindo à Mansão!");
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
            System.out.println("Não sei oq cê quis dizer xará...");
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
        System.out.println("Você está fazendo uma exploração urbana em uma mansão.");
        System.out.println("Dizem que ela é assombrada então cuidado.");
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
        Exit exit = currentRoom.getExit(direction);

        if (exit == null) {
            System.out.println("Tem porta não!");
            return;
        }

        // se saída trancada
        if (exit.isLocked()) {
            if (!player.getInventory().useKey()) {
                System.out.println("está saida está trancada e você não tem chaves");
                return;
            } else {
                // desbloqueia porta (consome chave)
                System.out.println("Você abre a porta, mais sua chave quebra...");
                // também destranca a saída recíproca (se houver)
                Room neighbor = exit.getNeighbor();
                exit.setLocked(false);
                neighbor.unlockExitTo(currentRoom);
            }
        }

        // arredar
        previousRoom = currentRoom;
        currentRoom = exit.getNeighbor();
        System.out.println(currentRoom.getLongDescription());

        // nova verificação: se todas as saídas estiverem trancadas, o monstro aparece e te ataca aqui
        handleAllExitsLockedInCurrentRoom();

        // checar monstro / spawn seila só tem que manter a lógica antiga
        spawnAdjacentMonsterChance();

        Room mr = getMonsterRoom();
        if (mr != null && mr == currentRoom) {
            handleMonsterEncounter();
        } else if (mr != null && isMonsterAdjacent()) {
            System.out.println("você sente um terror indescritivel percorrer seu corpo, escolha seus próximos passos com sabedoria...");
        } else if (mr != null) {
            System.out.println("Você está a salvo... Por enquanto");
        }

        // se o jogador tem a relíquia e voltou à entrada ---> vence
        if (player.getInventory().hasRelic() && currentRoom == startRoom) {
            System.out.println("Você escapa com Sucesso");
            System.exit(0);
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
        previousRoom = null; 
        // nova verificação: se todas as saídas estiverem trancadas, o monstro aparece aqui
        handleAllExitsLockedInCurrentRoom();

        // spawn possível antes de checar
        spawnAdjacentMonsterChance();

        Room mr = getMonsterRoom();
        if (mr != null && mr == currentRoom) {
            handleMonsterEncounter();
        } else if (mr != null && isMonsterAdjacent()) {
            System.out.println("você sente um terror indescritivel percorrer seu corpo, escolha seus próximos passos com sabedoria...");
        } else if (mr != null) {
            System.out.println("Você está a salvo... Por enquanto");
        }
    }

    /**
     * look: mostra a descrição longa do quarto atual, recolhe facas/relicas se existirem,
     * chances:
     * - 1/3 de dar rangido (como antes)
     * - chaves: chance X de aparecer ao usar look (só se a sala não tiver chave ainda)
     */
    public void look()
    {
        System.out.println(currentRoom.getLongDescription());

        // coleta facas automaticamente se houverem na sala
        if (currentRoom.hasKnives()) {
            player.getInventory().addKnife();
            currentRoom.setHasKnives(false);
            System.out.println("Você encontrou Facas! Agora você tem " + player.getInventory().getKnives() + " faca(s).");
        }

        // coleta relíquia automaticamente
        if (currentRoom.hasRelic()) {
            player.getInventory().pickRelic();
            currentRoom.setHasRelic(false);
            System.out.println("Você pegou a Relíquia! Volte à entrada para escapar com sucesso.");
        }

        // chaves: aparecem aleatoriamente
        if (!currentRoom.hasKey()) {
            if (random.nextInt(8) == 0) { // ajuste probabilidade aqui
                currentRoom.setHasKey(true);
                System.out.println("Uma chave aparece no chão!");
            }
        }

        // coleta quico automaticamente 
        if (currentRoom.hasKey()) {
            player.getInventory().addKey();
            currentRoom.setHasKey(false);
            System.out.println("Você pegou uma chave! Agora tem " + player.getInventory().getKeys() + " chave(s).");
        }

        // chance 1 em 3 do chão fazer: Nheeeeeeein
        int chance = random.nextInt(3);
        if (chance == 0 && !player.hasRangido()) {
            System.out.println("Uma tábua range quando você pisa.");
            player.setRangido(true);

            // agenda spawn com delay: monstro numa sala adjacente se o jogador ficar
            monsterManager.spawnAdjacentWithDelay(currentRoom, 5000L);
        } else {
            // caso não tenha rangido ainda tem chance do monstro aparecer imediatamente
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

    // ----------------- MONSTRO / UTILS -----------------

    public synchronized Room getCurrentRoom() {
        return currentRoom;
    }

    public synchronized void setMonsterRoom(Room r) {
        // cancela timers pendentes antes de mudar estado
        monsterManager.cancelPendingSpawn();

        if (monsterRoom != null) {
            monsterRoom.setHasMonster(false);
        }
        monsterRoom = r;
        if (monsterRoom != null) {
            monsterRoom.setHasMonster(true);
        }
    }

    public synchronized Room getMonsterRoom() {
        return monsterRoom;
    }

    public synchronized void clearMonsterRoom() {
        monsterManager.cancelPendingSpawn();
        if (monsterRoom != null) {
            monsterRoom.setHasMonster(false);
            monsterRoom = null;
        }
    }

    private boolean isMonsterAdjacent()
    {
        Room mr = getMonsterRoom();
        if (mr == null) return false;
        List<Room> neighbors = currentRoom.getNeighbors();
        return neighbors.contains(mr);
    }

    private void spawnAdjacentMonsterChance()
    {
        if (getMonsterRoom() != null) return;
        if (random.nextInt(10) == 0) {
            // spawn imediato
            monsterManager.spawnAdjacentImmediate(currentRoom);
        }
    }

    /**
     * Se a sala atual tiver todas as saídas trancadas, forçamos a aparição do monstro nela
     * e tratamos o encontro conforme regra nova.
     */
    private void handleAllExitsLockedInCurrentRoom() {
        if (currentRoom != null && currentRoom.allExitsLocked()) {
            // força aparição do monstro aqui
            clearMonsterRoom(); // cancela timers e limpa qualquer monstro anterior
            setMonsterRoom(currentRoom);

            System.out.println("O monstro tranca todas as suas saidas e te ataca...");
            handleMonsterEncounter();
        }
    }

    /**
     * Handle do encontro: se o monstro estiver na mesma sala do jogador.
     * Mantive sincronização para evitar races com timers.
     */
    public synchronized void handleMonsterEncounter()
    {
        if (monsterRoom == null || monsterRoom != currentRoom) return;

        if (player.getInventory().getKnives() > 0) {
            System.out.println("O monstro te ataca porem sua faca te salva");
            player.getInventory().useKnife();

            currentRoom.unlockAllExits();
            player.getInventory().addKeys(2);
            System.out.println("O monstro foge deixando todas as saídas destravadas. Duas chaves caem no chão.");
            System.out.println("Agora você tem " + player.getInventory().getKeys() + " chave(s).");

            clearMonsterRoom();
        } else {
            System.out.println("O monstro te ataca sem chance de você revidar");
            System.out.println("Você morreu.");
            System.exit(0);
        }
    }
}