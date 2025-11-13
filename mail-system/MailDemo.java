/**
 * Demonstration of cross-server messaging.
 * Compile and run this class to see delivery between servers.
 */
public class MailDemo
{
    public static void main(String[] args)
    {
        // Create two servers with distinct names (domains).
        MailServer serverA = new MailServer("serverA.com");
        MailServer serverB = new MailServer("serverB.com");

        // Create clients attached to their respective servers.
        MailClient alice = new MailClient(serverA, "alice");
        MailClient bob = new MailClient(serverB, "bob");

        // Alice sends to Bob on another server (qualified address)
        alice.sendMailItem("bob@serverB.com", "Hello Bob", "Hi Bob, this is Alice on serverA.");
        // Bob retrieves his mail (from serverB)
        System.out.println("Bob's mailbox:");
        bob.printNextMailItem();

        // Bob replies to Alice, specifying Alice's server
        bob.sendMailItem("alice@serverA.com", "Re: Hello Bob", "Hi Alice, got your message.");
        System.out.println("\nAlice's mailbox:");
        alice.printNextMailItem();

        // Also show that local addressing still works (no domain)
        alice.sendMailItem("alice", "Self Test", "This is a local to-self message.");
        System.out.println("\nAlice's mailbox after local send:");
        alice.printNextMailItem();
    }
}