/**
 * The ClockDisplay class implements a digital clock display for a
 * clock that now supports hours, minutes and seconds, and displays time
 * in 12-hour format with am/pm. Internally hours are kept as 0-23.
 *
 * The clock receives "ticks" via the timeTick method once per second
 * (it increments seconds). Minutes and hours roll over in the usual way.
 *
 * Author: adapted from Michael Kolling and David J. Barnes
 */
public class ClockDisplay
{
    private NumberDisplay hours;
    private NumberDisplay minutes;
    private NumberDisplay seconds;
    private String displayString;    // simulates the actual display

    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 00:00:00 (midnight).
     */
    public ClockDisplay()
    {
        this(0, 0, 0);
    }

    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by hour and minute,
     * seconds default to 0.
     */
    public ClockDisplay(int hour, int minute)
    {
        this(hour, minute, 0);
    }

    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by hour, minute and second.
     */
    public ClockDisplay(int hour, int minute, int second)
    {
        hours = new NumberDisplay(24);
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);
        setTime(hour, minute, second);
    }

    /**
     * This method should get called once every second - it makes
     * the clock display go one second forward.
     */
    public void timeTick()
    {
        seconds.increment();
        if(seconds.getValue() == 0) {  // seconds rolled over
            minutes.increment();
            if(minutes.getValue() == 0) { // minutes rolled over
                hours.increment();
            }
        }
        updateDisplay();
    }

    /**
     * Set the time of the display to the specified hour and
     * minute. Seconds are set to zero.
     */
    public void setTime(int hour, int minute)
    {
        setTime(hour, minute, 0);
    }

    /**
     * Set the time of the display to the specified hour, minute and second.
     */
    public void setTime(int hour, int minute, int second)
    {
        hours.setValue(hour);
        minutes.setValue(minute);
        seconds.setValue(second);
        updateDisplay();
    }

    /**
     * Return the current time of this display in the format HH:MM:SS am/pm.
     */
    public String getTime()
    {
        return displayString;
    }

    /**
     * Update the internal string that represents the display.
     * The display is rendered in 12-hour format with am/pm (lowercase).
     * Hours are shown as two digits (01..12) to keep display aligned.
     */
    private void updateDisplay()
    {
        int hour24 = hours.getValue();
        String ampm = (hour24 < 12) ? "am" : "pm";
        int hour12 = hour24 % 12;
        if(hour12 == 0) {
            hour12 = 12;
        }
        String hourStr = String.format("%02d", hour12);
        displayString = hourStr + ":" + minutes.getDisplayValue() + ":" +
                seconds.getDisplayValue() + " " + ampm;
    }
}