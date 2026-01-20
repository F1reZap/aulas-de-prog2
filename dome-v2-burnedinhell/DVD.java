/**
 * The DVD class represents a DVD object. Information about the 
 * DVD is stored and can be retrieved. We assume that we only deal 
 * with movie DVDs at this stage.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class DVD extends Item 
{
    private String director;

    /**
     * Constructor for objects of class DVD
     * @param theTitle The title of this DVD.
     * @param theDirector The director of this DVD.
     * @param time The running time of the main feature.
     */
    public DVD(String theTitle, String theDirector, int time)
    {
        super(theTitle, time);
        director = theDirector;
    }

    /**
     * @return The director for this DVD.
     */
    public String getDirector()
    {
        return director;
    }

    /**
     * Print DVD-specific details. Reuses superclass print for base fields.
     */
    @Override
    public void print()
    {
        super.print();
        System.out.println("    Director: " + director);
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n    Director: " + director;
    }
}