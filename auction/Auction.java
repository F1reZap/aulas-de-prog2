import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kolling.
 * @version 2008.03.30
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }
    
    /**
     * Bid for a lot.
     * A message indicating whether the bid is successful or not
     * is printed.
     * @param lotNumber The lot number being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void bidFor(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                                   lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                                   " already has a bid of: " +
                                   highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * Rewritten so it does not assume lot n is at index n-1.
     *
     * This method now iterates through the collection of lots and
     * returns the lot whose internal number field matches the
     * requested lotNumber. This makes getLot robust against
     * removals of lots that would otherwise shift indices.
     *
     * @param lotNumber The number of the lot to return.
     * @return The Lot with the given number, or null if no such lot exists.
     */
    public Lot getLot(int lotNumber)
    {
        if(lotNumber < 1) {
            System.out.println("Lot number: " + lotNumber +
                               " does not exist.");
            return null;
        }
        // Iterate through the list and find a lot with the matching number.
        for(Lot lot : lots) {
            if(lot.getNumber() == lotNumber) {
                return lot;
            }
        }
        System.out.println("Lot number: " + lotNumber +
                           " does not exist.");
        return null;
    }

    /**
     * Close the auction: iterate through all lots and print details.
     * Any lot that received at least one bid is considered sold.
     * For sold lots, the output includes the name of the winning bidder
     * and the value of the winning bid. For unsold lots, a message
     * indicating that the lot was not sold is printed.
     */
    public void close()
    {
        for(Lot lot : lots) {
            Bid highest = lot.getHighestBid();
            if(highest != null) {
                System.out.println(lot.getNumber() + ": " +
                                   lot.getDescription() +
                                   "    Sold to: " + highest.getBidder().getName() +
                                   " for: " + highest.getValue());
            }
            else {
                System.out.println(lot.getNumber() + ": " +
                                   lot.getDescription() +
                                   "    Not sold.");
            }
        }
    }

    /**
     * Return a list of the lots that were not sold (no bids).
     *
     * This method iterates through the collection of lots and collects
     * those that have no highest bid into a new ArrayList, which is
     * returned to the caller.
     *
     * @return An ArrayList containing the unsold lots.
     */
    public ArrayList<Lot> getUnsold()
    {
        ArrayList<Lot> unsold = new ArrayList<Lot>();
        for(Lot lot : lots) {
            if(lot.getHighestBid() == null) {
                unsold.add(lot);
            }
        }
        return unsold;
    }

    /**
     * Remove the lot with the given lot number.
     * @param number The lot number to remove.
     * @return The Lot with the given number, or null if there is no such lot.
     *
     * This method assumes a lot with the given number may be stored at any
     * position in the collection. It searches for a Lot whose internal number
     * matches the requested number, removes it from the list and returns it.
     */
    public Lot removeLot(int number)
    {
        for(int i = 0; i < lots.size(); i++) {
            Lot lot = lots.get(i);
            if(lot.getNumber() == number) {
                lots.remove(i);
                return lot;
            }
        }
        return null;
    }
}