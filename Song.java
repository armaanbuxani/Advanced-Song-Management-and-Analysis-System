import java.util.ArrayList;

public class Song implements SongInterface{
    private String title;
    private String artist;
    private String genres; // Assuming genres are stored in a single string
    private int year;
    private int bpm;
    private int energy;
    private int danceability;
    private int loudness;
    private int liveness;

    //private static ArrayList<Song> songList = new ArrayList<>();

    public Song(String title, String artist, String genres, int year, int bpm, int energy,
                         int danceability, int loudness, int liveness) {
        this.title = title;
        this.artist = artist;
        this.genres = genres;
        this.year = year;
        this.bpm = bpm;
        this.energy = energy;
        this.danceability = danceability;
        this.loudness = loudness;
        this.liveness = liveness;

        //songList.add(this);
    }

    //public static ArrayList<Song> getSongList() {
       // return songList;
    //}

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getGenres() {
        return genres;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int getDanceability() {
        return danceability;
    }

    @Override
    public int getLoudness() {
        return loudness;
    }

    @Override
    public int getLiveness() {
        return liveness;
    }

    @Override
    public int compareTo(SongInterface o) { //DOUBT
        return 0;
    }
}
