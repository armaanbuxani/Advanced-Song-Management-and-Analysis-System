import java.util.List;
import java.io.IOException;

/**
 * This class doesn't really work.  The methods are hardcoded to output values
 * as placeholders throughout development.  It demonstrates the architecture
 * of the Backend class that will be implemented in a later week.
 */
public class BackendPlaceholder implements BackendInterface {

    public BackendPlaceholder(IterableSortedCollection<SongInterface> tree) {}

    /**
     * Loads data from the .csv file referenced by filename.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    public void readData(String filename) throws IOException {
	if(!filename.equals("songs.csv")) {
	//To interact with the frontend tests, this need to throw exception.
            throw new IOException();
        }
	// Note: this placeholder doesn't need to output anything,
	// it will be implemented by the backend developer in P105.
    }

    /**
     * Retrieves a list of song titles for songs that have a Speed (BPM)
     * within the specified range (sorted by BPM in ascending order).  If 
     * a maxYear filter has been set using filterOldSongs(), then only songs
     * on Billboard durring or before that maxYear should be included in the 
     * list that is returned by this method.
     *
     * Note that either this bpm range, or the resulting unfiltered list
     * of songs should be saved for later use by the other methods defined in 
     * this class.
     *
     * @param low is the minimum Speed (BPM) of songs in the returned list
     * @param hight is the maximum Speed (BPM) of songs in the returned list
     * @return List of titles for all songs in specified range 
     */
    public List<String> getRange(int low, int high) {
	if(low > high) {
	//To interact with the frontend tests, this method need to firstly retuen a no element array list.
            return java.util.Arrays.asList(new String[]{});
        }
	// placeholder just returns a hard coded list of songs
	return java.util.Arrays.asList(new String[]{
		"Hey, Soul Sister",
		"Love The Way You Lie",
		"TiK ToK",
		"Bad Romance",
		"Just the Way You Are"
	    });
    }

    /**
     * Filters the list of songs returned by future calls of getRange() and 
     * fiveMostDanceable() to only include older songs.  If getRange() was 
     * previously called, then this method will return a list of song titles
     * (sorted in ascending order by Speed BPM) that only includes songs on
     * Billboard on or before the specified maxYear.  If getRange() was not 
     * previously called, then this method should return an empty list.
     *
     * Note that this maxYear threshold should be saved for later use by the 
     * other methods defined in this class.
     *
     * @param maxYear is the maximum year that a returned song was on Billboard
     * @return List of song titles, empty if getRange was not previously called
     */
    public List<String> filterOldSongs(int maxYear) {
	if(maxYear < 1700) {
	//To interact with the front end tests, this method should firstly return the no element array list.
            return java.util.Arrays.asList(new String[]{});
        }
	return java.util.Arrays.asList(new String[]{
		"Hey, Soul Sister",
		"Love The Way You Lie"
	    });
    }

    /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a maxYear threshold has been set by
     * filterOldSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most danceable will 
     * be returned by this method as a List of Strings in increasing order of 
     * speed (bpm).  Each string contains the danceability followed by a 
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, display all of them.
     *
     * @return List of five most danceable song titles and their danceabilities
     * @throws IllegalStateException when getRange() was not previously called.
     */
    public List<String> fiveMostDanceable() {
	return java.util.Arrays.asList(new String[]{
		"65: Hey, Soul Sister",
		"75: Love The Way You Lie"
	    });	
    }    
}
