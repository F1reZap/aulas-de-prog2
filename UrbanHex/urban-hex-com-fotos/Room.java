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
 * Add: flags pros items (faquinha, reliquia) e presença do nomstro.
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

    // Adiciona uma saida numa direção especifica (ava é mermo?)
    public void setExit(String direction, Room neighbor) {
        setExit(direction, neighbor, false);
    }

    //marcar a saída como trancada
    public void setExit(String direction, Room neighbor, boolean locked) {
        exits.put(direction, new Exit(neighbor, locked));
    }

    // Recupera a saída 
    public Exit getExit(String direction) {
        return exits.get(direction);
    }

    // Recupera a sala vizinha (argumentavelmente a pior criação de todas: os vizinhos)
    public Room getExitRoom(String direction) {
        Exit e = exits.get(direction);
        return (e == null) ? null : e.getNeighbor();
    }

    public String getDescription()
    {
        return description;
    }

    /**
     * Retorna a descrição longa da sala, incluindo o texto fixo específico da sala
     * (Urban Hex) quando houver, além de itens, monstro e lista de saídas.
     */
    public String getLongDescription()
    {
        StringBuilder sb = new StringBuilder();

        String desc = (description == null) ? "" : description.toLowerCase();

        // mapear descrições ricas com base na descrição curta da sala
        if (desc.contains("entrada")) {
            sb.append("Em sua mente torturada e passos inseguros você se arrasta pelo chão enlameado do pátio e olha em objeção a casa que lhe trouxe a dor e amargura que agora assombra sua alma. Não é pesadelo ou loucura, você já descartou essas hipóteses a muito tempo. O corpo esquartejado de Jonas apodrece no salão de sua casa e apenas você sabe o porquê. Se você quiser escapar da sombra que se cola sobre seus passos você terá que entrar na casa e encontrar aquilo que você fez tamanho esforço para ignorar.\n");
            sb.append("Você sobe os degraus de pedra e se encontra frente-a-frente com as portas duplas de mogno negro.\n");
            sb.append("Você respira fundo.\n");
            sb.append("Que Deus tenha piedade de sua arrogante e mórbida curiosidade que levou você a tomar esse caminho.");
        }
        else if (desc.contains("hall principal") || desc.contains("hall")) {
            sb.append("Você se encontra no salão principal, poeira acumulada no chão forrado de carpete vermelho invade suas narinas e te faz espirrar por reflexo, as portas se fecham atrás de você com um estouro e você é envolto por completa escuridão, por rápidos agonizantes segundos você se vê em um completo vazio.\n");
            sb.append("Em pânico você puxa a lanterna de seu bolso e a fraca luz amarela inunda o local e lhe traz um breve alívio, um par de escadas em espiral ascendem para o segundo andar e duas outras portas na esquerda e direita levam para locais desconhecidos.\n");
            sb.append("A mansão está silenciosa, por enquanto.");
        }
        else if (desc.contains("corredor 1")) {
            sb.append("Por um buraco no teto um fio de luz prata escorre iluminando o corredor, manchas verde-musgo cobrem as paredes como uma invasão de uma força nociva vinda de outro plano da existência. Múltiplas portas, todas cobertas pelo mencionado mofo escondem a entrada para diferentes cômodos.\n");
            sb.append("O suspense e as possibilidades fazem você se sentir esmagado.");
        }
        else if (desc.contains("corredor 2")) {
            sb.append("Você se encontra em um corredor, a escuridão e a poeira são tão densas que a lanterna tem dificuldade em iluminar as paredes, antes brancas agora amareladas com manchas negras de musgo. O chão tabuado de madeira range com seus passos, ecoando seus grunhidos pelo prédio como um farol.");
        }
        else if (desc.contains("corredor 3")) {
            sb.append("Ao topo das escadas você se encontra em um corredor, buracos no teto deixam a luz prateada da lua trazem uma leve iluminação para o local, no seu primeiro passo você sente uma substância viscosa, ao levantar o pé você vê um rastro de um líquido ao qual você não consegue identificar. Algo estava aqui.");
        }
        else if (desc.contains("corredor 4")) {
            sb.append("Um corredor exterior, janelas tão grandes quanto você alinham a parede, muitas estão quebradas e vento congelante invade o prédio gelando seus ossos e levantando as cortinas, estar tão alto e tão exposto deixa um desconforto no seu peito.");
        }
        else if (desc.contains("corredor 5")) {
            sb.append("Quanto mais você anda você sente que o corredor não acaba, sempre que você olha para trás é quase como se você não tivesse saído do lugar. A escuridão se espalha infinitamente e impossivelmente, chega uma hora que sua lanterna não faz nada mais do que iluminar seus próprios pés.");
        }
        else if (desc.contains("corredor 6")) {
            sb.append("Um corredor escondido, diferente de todos os outros, esse local parece que não era para visitações de nenhum hóspede, a frente uma porta muito bem preservada, ao lado uma escada em espiral que desce ao primeiro andar.");
        }
        else if (desc.contains("quarto 1")) {
            sb.append("Um quarto de servos, tantos objetos em um só local você tem dificuldade em abrir a porta, o sentimento de claustrofobia faz seu coração apitar estar em um local tão apertado, mofo cobre sua visão e não a nenhum alívio…");
        }
        else if (desc.contains("quarto 2")) {
            sb.append("Um quarto de hóspedes, um local onde um dia haveria uma cama agora é um espaço vazio com marcas de arrasto no chão. A única luz vem de uma janela quebrada e suja. Muito emperrada para se abrir e muito borrada para ver o lado de fora…");
        }
        else if (desc.contains("quarto mestre")) {
            sb.append("O Quarto mestre, mesmo depois de todos esses anos o tempo não foi capaz de apagar todo o luxo e pompa, uma cama de casal que mal parece tocada, móveis envernizados de madeira negra, Um elevador pessoal que corta uma caminhada extensiva para chegar ao armazém, detalhes rococó de fios dourados entalhados na parede finalizam o visual de um quarto que antes abrigava alguém com muito poder, poder que você está descobrindo aos poucos não era só monetário.");
        }
        else if (desc.contains("cozinha")) {
            sb.append("O cheiro de mofo e azedo ataca suas narinas e faz seus olhos lacrimejar, mesas prateadas com potes e panelas cheios de comida agora brancas com mofo, você sente uma passagem na sua perna e se depara com um chão coberto de baratas, parece que esse lugar possui novos mestres.");
        }
        else if (desc.contains("biblioteca")) {
            sb.append("Livros atrás de livros empilhados em estantes que se esticam até a escuridão acima, tentar entender como essas paredes monumentais cabem dentro da casa quebra com o seu senso de direção. Quanto mais tempo você anda por esse lugar, mais você entende que as leis da natureza foram deixadas para trás e agora você caminha no limiar do impossível e você não foi o primeiro a dar esses passos.");
        }
        else if (desc.contains("escritório")) {
            sb.append("A sensação de entrar no escritório lembra o sentimento de andar por um cemitério, olhos conservados em jarros, insetos pregados na parede, livros grossos com capas enrugadas e simbolos marcados com liquido negro nas paredes. No centro uma mesa de pedra com papéis espalhados em sua superfície, escritas que lembram murmúrios de um lunático, sua espinha arrepiar, seria esse o seu destino quando isso tudo acabar?");
        }
        else if (desc.contains("varanda")) {
            sb.append("Você aproveita para respirar o frio ar da noite de lua cheia e se derramar sobre o parapeito, Ao longe você pode ver o topo das árvores rodeando a propriedade, logo abaixo um jardim antes belo agora corrompido por ervas daninhas e vinhas, o mundo está em silêncio, e por um breve momento você gostaria de ficar aqui para sempre.");
        }
        else if (desc.contains("salao") || desc.contains("cofre")) {
            // genérico para salaoDoCofre / cofre, mantém texto simples
            sb.append("Uma sala sombria com ecos do passado; cadeiras e mobiliário encobertos por lonas e poeira. Algo valioso parece ter estado aqui.");
        }
        else if (desc.contains("outside") || desc.contains("lado de fora") || desc.contains("fora")) {
            sb.append("Você está do lado de fora da mansão. O ar frio e a noite trazem uma sensação agridoce de alívio e perigo — escapar parece possível, mas a ansiedade persiste.");
        }
        else {
            // fallback: usar longDescription padrão
            sb.append(longDescription);
        }

        // acrescenta itens / estado nesta sala
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

    // Exibe as direções que existem nessa sala
    public String getExitString() {
        return String.join(" ", exits.keySet());
    }

    // --- métodos para itens / nomstro ---
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
                return;
            }
        }
    }

    /**
     * Retorna true se EXISTEM saídas e todas elas estão trancadas.
     */
    public boolean allExitsLocked() {
        if (exits.isEmpty()) return false;
        for (Exit e : exits.values()) {
            if (!e.isLocked()) return false;
        }
        return true;
    }

    /**
     * Destrava todas as saídas desta sala e também tenta destravar as
     * saídas recíprocas nas salas vizinhas.
     */
    public void unlockAllExits() {
        for (Exit e : exits.values()) {
            e.setLocked(false);
            Room neighbor = e.getNeighbor();
            if (neighbor != null) {
                neighbor.unlockExitTo(this);
            }
        }
    }
}