/**
 * A class to model a simple mail item. The item has sender and recipient
 * addresses, an optional subject, and a message string.
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30 (modified to add subject)
 */
public class MailItem
{
    // The sender of the item.
    private String from;
    // The intended recipient.
    private String to;
    // The subject of the message (may be empty or null for compatibility).
    private String subject;
    // The text of the message.
    private String message;

    /**
     * Create a mail item from sender to the given recipient,
     * containing the given message. (Compatibility constructor — no subject.)
     * @param from The sender of this item.
     * @param to The intended recipient of this item.
     * @param message The text of the message to be sent.
     */
    public MailItem(String from, String to, String message)
    {
        // Keep compatibility: subject left as null
        this(from, to, null, message);
    }

    /**
     * Create a mail item from sender to the given recipient,
     * with the given subject and message.
     * @param from The sender of this item.
     * @param to The intended recipient of this item.
     * @param subject The subject of the message (may be null or empty).
     * @param message The text of the message to be sent.
     */
    public MailItem(String from, String to, String subject, String message)
    {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    /**
     * @return The sender of this message.
     */
    public String getFrom()
    {
        return from;
    }

    /**
     * @return The intended recipient of this message.
     */
    public String getTo()
    {
        return to;
    }

    /**
     * @return The subject of the message, or null if none was provided.
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @return The text of the message.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Print this mail message to the text terminal.
     * The subject line is printed as well (empty if none).
     */
    public void print()
    {
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        // Print subject even if null/empty for visibility
        System.out.println("Subject: " + (subject == null ? "" : subject));
        System.out.println("Message: " + message);
    }
}