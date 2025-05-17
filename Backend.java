import java.io.File;
import java.io.IOException;
import java.util.*;


public class Backend implements BackendInterface {
    private IterableSortedCollection<SongInterface> songTree;

    public Backend(IterableSortedCollection<SongInterface> songTree) {
        this.songTree = songTree;
    }

    // Saved BPM Range List for future use
    private List<SongInterface> BPMSongsList = new ArrayList<>();

    // Saved max year if set by filterOldSongs
    private int maxYearFilter = 2030;

    // If getRange() is called
    private boolean isGetRangeCalled = false;
    
    /**
     * Loads data from the .csv file referenced by filename.
     *
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    @Override
    public void readData(String filename) throws IOException {
        try {
            File file = new File(filename); // Saving file
            Scanner scanner = new Scanner(file); // Scanning file

            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skipping header line
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // Scanning entire line
                List<String> values = new ArrayList<>();
                boolean inQuotes = false;
                StringBuilder buffer = new StringBuilder();

                for (char c : line.toCharArray()) {
                    if (c == '"') { // Checks for commas in quotes
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        values.add(buffer.toString()); // Adds the buffer to values when a comma is found outside quotes
                        buffer.setLength(0); // Resets the buffer
                    } else {
                        buffer.append(c); // Adds the character to the buffer
                    }
                }
                values.add(buffer.toString()); // Add the last value

                // Retrieving data
                String title = values.get(0);
                String artist = values.get(1);
                String genre = values.get(2);
                int year = Integer.parseInt(values.get(3));
                int bpm = Integer.parseInt(values.get(4));
                int energy = Integer.parseInt(values.get(5));
                int danceability = Integer.parseInt(values.get(6));
                int loudness = Integer.parseInt(values.get(7));
                int liveness = Integer.parseInt(values.get(8));

                // Saves in song list as song
                Song song = new Song(title, artist, genre, year, bpm, energy,
                        danceability, loudness, liveness);
                this.songTree.insert(song);
            }
        } catch (IOException e) { // catch for invalid file
            throw e;
        }
    }

    /**
     * Retrieves a list of song titles for songs that have a Speed (BPM)
     * within the specified range (sorted by BPM in ascending order).  If
     * a maxYear filter has been set using filterOldSongs(), then only songs
     * on Billboard during or before that maxYear should be included in the
     * list that is returned by this method.
     * <p>
     * Note that either this bpm range, or the resulting unfiltered list
     * of songs should be saved for later use by the other methods defined in
     * this class.
     *
     * @param low  is the minimum Speed (BPM) of songs in the returned list
     * @param high is the maximum Speed (BPM) of songs in the returned list
     * @return List of titles for all songs in specified range
     */
    @Override
    public List<String> getRange(int low, int high) {
        List<SongInterface> tempList = new ArrayList<>(); // List for songs in BPM range

        Iterator<SongInterface> iterator = this.songTree.iterator();

	this.isGetRangeCalled = true;

        // Filters songs in BPM range and adds to List, while checking for maxYearFilter
        while (iterator.hasNext()) {
            SongInterface song = iterator.next();
            if (song.getBPM() >= low && song.getBPM() <= high && song.getYear() <= maxYearFilter) {
                tempList.add(song);
            }
        }

        // Organizes list in ascending order
        tempList.sort(Comparator.comparingInt(SongInterface::getBPM));

        // Saving list for future use
        BPMSongsList = tempList;

        // Returning titles
        List<String> titles = new ArrayList<>();
        for (SongInterface song : BPMSongsList) {
            titles.add(song.getTitle());
        }
        return titles;
    }

    /**
     * Filters the list of songs returned by future calls of getRange() and
     * fiveMostDanceable() to only include older songs.  If getRange() was
     * previously called, then this method will return a list of song titles
     * (sorted in ascending order by Speed BPM) that only includes songs on
     * Billboard on or before the specified maxYear.  If getRange() was not
     * previously called, then this method should return an empty list.
     * <p>
     * Note that this maxYear threshold should be saved for later use by the
     * other methods defined in this class.
     *
     * @param maxYear is the maximum year that a returned song was on Billboard
     * @return List of song titles, empty if getRange was not previously called
     */
    @Override
    public List<String> filterOldSongs(int maxYear) {
        this.maxYearFilter = maxYear; // Saves max year for future use

        if (!isGetRangeCalled) { // Checks if getRange() was called
            return new ArrayList<>(); // Return null list if getRange() wasn't called
        } else {
            List<String> filteredTitles = new ArrayList<>(); // Creates array for titles
            for (SongInterface song : BPMSongsList) { // Filters songs with max year
                if (song.getYear() <= maxYear) {
                    filteredTitles.add(song.getTitle());
                }
            }
            // Reverses list so its in ascending order
            List<String> filteredOrdered = new ArrayList<>();
            for (int i = filteredTitles.size() - 1; i >= 0; i--) {
                filteredOrdered.add(filteredTitles.get(i));
            }
            return filteredTitles; // returns titles after filtered old songs
        }
    }

    /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a maxYear threshold has been set by
     * filterOldSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most danceable will
     * be returned by this method as a List of Strings in increasing order of
     * danceability.  Each string contains the danceability followed by a
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, return all of them.
     *
     * @return List of five most danceable song titles and their danceabilities
     * @throws IllegalStateException when getRange() was not previously called.
     */
    @Override
    public List<String> fiveMostDanceable() {
        if (!isGetRangeCalled) { // Throws exception if getRange() wasn't previously called
            throw new IllegalStateException();
        }

        // Filters max year
        List<SongInterface> songsToFilter = new ArrayList<>();
        for (SongInterface song : BPMSongsList) {
            if (song.getYear() <= maxYearFilter) {
                songsToFilter.add(song);
            }
        }

        // Sorts songs in order of danceability
        songsToFilter.sort(Comparator.comparingInt(SongInterface::getDanceability).reversed());

        // Adds and returns top 5 danceable titles in a list
        List<String> top5DanceableTitlesTemp = new ArrayList<>();
        for (int i = 0; i < Math.min(5, songsToFilter.size()); i++) {
            SongInterface song = songsToFilter.get(i);
            top5DanceableTitlesTemp.add(song.getDanceability() + ": " + song.getTitle());
        }

        // Reverses list so its in ascending order
        List<String> top5DanceableTitles = new ArrayList<>();
        for (int i = top5DanceableTitlesTemp.size() - 1; i >= 0; i--) {
            top5DanceableTitles.add(top5DanceableTitlesTemp.get(i));
        }
        return top5DanceableTitles;
    }
}
