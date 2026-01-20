
public class VideoGame extends Item
{
    private String platform;
    private String genre;
    
    public VideoGame(String theTitle, String platform, String genre, int time)
    {
        super(theTitle, time);
        this.platform = platform;
        this.genre = genre;
    }

    public String getPlatform()
    {
        return platform;
    }

    public String getGenre()
    {
        return genre;
    }

    @Override
    public void print()
    {
        super.print();
        System.out.println("    Platform: " + platform);
        System.out.println("    Genre:    " + genre);
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n    Platform: " + platform + "\n    Genre:    " + genre;
    }
}