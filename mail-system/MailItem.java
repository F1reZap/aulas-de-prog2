/**
 * A class to model a simple mail item. The item has sender and recipient
 * addresses, an optional subject, and a message string.
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
     */
    public MailItem(String from, String to, String message)
    {
        // Keep compatibility: subject left as null
        this(from, to, null, message);
    }

    /**
     * Create a mail item from sender to the given recipient,
     * with the given subject and message.
     */
    public MailItem(String from, String to, String subject, String message)
    {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }

    public void print()
    {
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Subject: " + (subject == null ? "" : subject));
        System.out.println("Message: " + message);
    }
}