import java.util.List;
import java.util.Random;

public class Monster {
    private final Game game;
    private final Random random = new Random();
    private Thread pendingTimer = null;

    public Monster(Game game) {
        this.game = game;
    }

    /**
     * Tenta spawnar o monstro imediatamente em uma sala adjacente ao jogador.
     * Se já existe monstro no jogo, não faz nada.
     */
    public synchronized void spawnAdjacentImmediate(Room playerRoom) {
        if (game.getMonsterRoom() != null) return;

        List<Room> neigh = playerRoom.getNeighbors();
        if (neigh.isEmpty()) return;
        Room chosen = neigh.get(random.nextInt(neigh.size()));

        // cancela timers pendentes e coloca o monstro imediatamente
        cancelPendingSpawn();
        game.setMonsterRoom(chosen);

        System.out.println("você sente um terror indescritivel percorrer seu corpo, escolha seus próximos passos com sabedoria...");
    }

    /**
     * Tenta spawnar o monstro em uma sala adjacente, mas só efetiva a ação
     * se o jogador permanecer na alertRoom após o delay.
     * Se já houver monstro no jogo, não agenda nada.
     */
    public synchronized void spawnAdjacentWithDelay(Room alertRoom, long delayMillis) {
        if (game.getMonsterRoom() != null) return;

        List<Room> neigh = alertRoom.getNeighbors();
        if (neigh.isEmpty()) return;
        Room chosen = neigh.get(random.nextInt(neigh.size()));

        // se já houver monstro (ou outro timer), não agenda
        if (game.getMonsterRoom() != null) return;

        // Cancela qualquer timer anterior antes de agendar um novo
        cancelPendingSpawn();

        pendingTimer = new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                // timer cancelado
                return;
            }
            synchronized (Monster.this) {
                // só efetiva se o jogador ainda estiver na alertRoom
                if (game.getCurrentRoom() == alertRoom && game.getMonsterRoom() == null) {
                    // coloca o monstro na sala do jogador (entrada do monstro)
                    game.setMonsterRoom(alertRoom);
                    System.out.println("O monstro entrou na sua sala!");
                    game.handleMonsterEncounter(); // Game ainda gerencia encontro/resultado
                } else {
                    // jogador saiu da sala ou monstro já foi colocado - nada a fazer
                }
                pendingTimer = null;
            }
        }, "monster-timer");
        pendingTimer.start();

        System.out.println("você escuta passadas vindo em sua direção, CORRA");
    }

    /**
     * Cancela timer pendente se houver.
     */
    public synchronized void cancelPendingSpawn() {
        if (pendingTimer != null) {
            pendingTimer.interrupt();
            pendingTimer = null;
        }
    }
}