/**
 * The Item class represents a multi-media item.
 * Information about the item is stored and can be retrieved.
 * This class serves as a superclass for more specific items.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class Item
{
    private String title;
    private int playingTime;
    private boolean gotIt;
    private String comment;

    /**
     * Initialise the fields of the item.
     * @param theTitle The title of this item.
     * @param time The running time of this item.
     */
    public Item(String theTitle, int time)
    {
        title = theTitle;
        playingTime = time;
        gotIt = false;
        comment = "";
    }

    /**
     * Enter a comment for this item.
     * @param comment The comment to be entered.
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    /**
     * @return The comment for this item.
     */
    public String getComment()
    {
        return comment;
    }

    /**
     * Set the flag indicating whether we own this item.
     * @param ownIt true if we own the item, false otherwise.
     */
    public void setOwn(boolean ownIt)
    {
        gotIt = ownIt;
    }

    /**
     * @return true if we own a copy of this item.
     */
    public boolean getOwn()
    {
        return gotIt;
    }

    /**
     * Print details about this item to the text terminal.
     * Now delegates formatting to toString(), so subclasses can
     * override either toString() or print() and call super.print().
     */
    public void print()
    {
        System.out.println(this.toString());
    }

    /**
     * Override of Object.toString to centralize base formatting.
     * Subclasses can override toString() and call super.toString()
     * to reuse base formatting, or they can override print() and
     * call super.print().
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("title: ").append(title).append(" (").append(playingTime).append(" mins)");
        if(gotIt) {
            sb.append("*");
        }
        sb.append("\n    ").append(comment);
        return sb.toString();
    }
}