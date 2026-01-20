/**
 * The CD class represents a CD object. Information about the 
 * CD is stored and can be retrieved.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class CD extends Item
{
    private String artist;
    private int numberOfTracks;

    /**
     * Initialize the CD.
     * @param theTitle The title of the CD.
     * @param theArtist The artist of the CD.
     * @param tracks The number of tracks on the CD.
     * @param time The playing time of the CD.
     */
    public CD(String theTitle, String theArtist, int tracks, int time)
    {
        super(theTitle, time);
        artist = theArtist;
        numberOfTracks = tracks;
    }

    /**
     * @return The artist for this CD.
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * @return The number of tracks on this CD.
     */
    public int getNumberOfTracks()
    {
        return numberOfTracks;
    }

    /**
     * Print CD-specific details. Reuses superclass print for base fields.
     */
    @Override
    public void print()
    {
        super.print();
        System.out.println("    Artist: " + artist);
        System.out.println("    Tracks: " + numberOfTracks);
    }

    /**
     * Optionally override toString to provide a single-string representation.
     * This demonstrates the alternative approach (printing via toString).
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n    Artist: ").append(artist);
        sb.append("\n    Tracks: ").append(numberOfTracks);
        return sb.toString();
    }
}