/**
 * A simple model of a mail server. The server is able to receive
 * mail items for storage, deliver them to clients on demand, and
 * (optional) route/forward mail to other named servers using simple
 * user@domain addressing.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class MailServer
{
    // Storage for mail items on this server.
    private List<MailItem> items;

    // The server's name / domain used for routing (e.g. "serverA.com").
    private String name;

    // Registry of named servers for routing (simple in-memory registry).
    private static Map<String, MailServer> registry = new HashMap<String, MailServer>();

    // Counter for default server names (if no name provided).
    private static int defaultIdCounter = 0;

    /**
     * Construct a mail server with an auto-generated local name (for compatibility).
     * The generated name will be "localhost1", "localhost2", ...
     */
    public MailServer()
    {
        this("localhost" + (++defaultIdCounter));
    }

    /**
     * Construct a mail server with the given name (domain).
     * The server is registered under that name for routing.
     * @param name The name/domain of the server (e.g. "serverA.com").
     */
    public MailServer(String name)
    {
        this.name = name;
        items = new ArrayList<MailItem>();
        registry.put(name, this);
    }

    /**
     * Optional: lookup a registered server by name (domain).
     */
    public static MailServer getRegisteredServer(String name)
    {
        return registry.get(name);
    }

    /**
     * Return how many mail items are waiting for a user.
     * @param who The user to check for.
     * @return How many items are waiting.
     */
    public int howManyMailItems(String who)
    {
        int count = 0;
        for(MailItem item : items) {
            if(item.getTo().equals(who)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the next mail item for a user or null if there
     * are none.
     * @param who The user requesting their next item.
     * @return The user's next item.
     */
    public MailItem getNextMailItem(String who)
    {
        Iterator<MailItem> it = items.iterator();
        while(it.hasNext()) {
            MailItem item = it.next();
            if(item.getTo().equals(who)) {
                it.remove();
                return item;
            }
        }
        return null;
    }

    /**
     * Add the given mail item to the message list.
     * This method also handles simple routing: if the recipient
     * is of the form user@domain and domain is a registered server,
     * the message will be forwarded to that server.
     * The 'from' field is qualified with this server's name if not already qualified.
     * @param item The mail item to be stored on the server (or forwarded).
     */
    public void post(MailItem item)
    {
        String to = item.getTo();
        String subject = item.getSubject();
        String message = item.getMessage();
        String from = item.getFrom();

        // Qualify the sender with this server's name if it is not already qualified.
        String qualifiedFrom = from;
        if(qualifiedFrom == null) {
            qualifiedFrom = "unknown@" + this.name;
        } else if(!qualifiedFrom.contains("@")) {
            qualifiedFrom = qualifiedFrom + "@" + this.name;
        }

        if(to != null && to.contains("@")) {
            // Recipient specified as user@domain
            int at = to.indexOf('@');
            String user = to.substring(0, at);
            String domain = to.substring(at + 1);
            if(domain.equals(this.name)) {
                // Delivered locally: store with recipient = user (no domain)
                MailItem stored = new MailItem(qualifiedFrom, user, subject, message);
                items.add(stored);
            }
            else {
                // Forward to another registered server if known
                MailServer dest = registry.get(domain);
                if(dest != null) {
                    // Create a new MailItem for forwarding; recipient user is local to dest.
                    MailItem forward = new MailItem(qualifiedFrom, user, subject, message);
                    dest.post(forward);
                }
                else {
                    // No route to domain: message undeliverable in this simple model.
                    System.out.println("MailServer (" + this.name + "): No route to domain '" + domain + "' for recipient '" + to + "'. Message from " + qualifiedFrom + " lost.");
                }
            }
        }
        else {
            // No domain provided: local delivery to this server
            MailItem stored = new MailItem(qualifiedFrom, to, subject, message);
            items.add(stored);
        }
    }

    /**
     * Optional getter for server name.
     */
    public String getName() {
        return name;
    }
}