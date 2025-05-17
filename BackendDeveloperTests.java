import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackendDeveloperTests {

    private IterableSortedCollection<SongInterface> songTree = new ISCPlaceholder<>();

    /**
     * Tests if readData method correctly loads data from csv file, and throws exceptions for
     * invalid file paths
     */
    @Test
    public void testReadData() {
        ISCPlaceholder<SongInterface> tree = new ISCPlaceholder<>();
        Backend backend = new Backend(tree);

        // Testing if method does not throw exception with valid file
        try {
            backend.readData("songs.csv");
            assertTrue(true);
        }
        catch (IOException e) {
            assertTrue(false, "IOException should not be thrown for valid file");
        }

        boolean throwException = false;
        try {
            backend.readData("invalidFile.csv");
        }
        catch (IOException e) {
            throwException = true;
            assertTrue(throwException, "IOException should be thrown");
        }
    }

    /**
     * Tests if songs in range of BPM match expected values, and is in ascending order
     *
     */
    @Test
    public void testRange() {
        // Creating song instances
        SongInterface song1 = new Song("Song 1", "Artist 1", "genre 1", 2010,
                202, 333, 200,203,283);
        SongInterface song2 = new Song("Song 2", "Artist 2", "genre 1", 2010,
                205, 333, 200,203,283);
        SongInterface song3 = new Song("Song 3", "Artist 3", "genre 1", 2010,
                190, 333, 200,203,283);
        songTree.insert(song1);
        songTree.insert(song2);
        songTree.insert(song3);
        Backend backend = new Backend(songTree);

        List<String> rangeTester = backend.getRange(200, 206);

        // Testing getRange in ascending order
        assertEquals(2, rangeTester.size(), rangeTester.toString()); // size should be 2
        // First song should be Song 1
        assertEquals("Song 1", rangeTester.get(0),
                "First song not correct");
        // Second song should be Song 2
        assertEquals("Song 2", rangeTester.get(1), "Second song not correct");
    }

    /**
     *
     */
    @Test
    public void testRangeCase2() {
        // Creating song instances
        SongInterface song1 = new Song("Song 1", "Artist 1", "genre 1", 2010,
                185, 333, 200,203,283);
        SongInterface song2 = new Song("Song 2", "Artist 2", "genre 1", 2010,
                183, 333, 200,203,283);
        SongInterface song3 = new Song("Song 3", "Artist 3", "genre 1", 2011,
                185, 333, 200,203,283);
        songTree.insert(song1);
        songTree.insert(song2);
        songTree.insert(song3);
        Backend backend = new Backend(songTree);

        // Input values
        backend.filterOldSongs(2010);
        List<String> songsInRange = backend.getRange(180, 186);

        // Testing getRange in ascending order
        assertEquals(2, songsInRange.size(), songsInRange.toString()); // size should be 2
        // First song should be Song 1
        assertEquals("Song 2", songsInRange.get(0),
                "First song not correct");
        // Second song should be Song 2
        assertEquals("Song 1", songsInRange.get(1), "Second song not correct");
    }

    /**
     * Tests if when calling filterOldSongs without using getRange, it provides an empty list.
     */
    @Test
    public void testFilterOldSongs() {
        ISCPlaceholder<SongInterface> tree = new ISCPlaceholder<>();
        Backend backend = new Backend(tree);

        List<String> filteredSongs = backend.filterOldSongs(2010);
        // Size of list should be 0
        assertEquals(0, filteredSongs.size(), "Filtered songs should be 0 songs");
    }

    @Test
    public void testFilterOldSongsCase2() {
        // Creating song instances
        SongInterface song1 = new Song("Song 1", "Artist 1", "genre 1", 2015,
                200, 333, 200,203,283);
        SongInterface song2 = new Song("Song 2", "Artist 2", "genre 1", 2010,
                193, 333, 200,203,283);
        SongInterface song3 = new Song("Song 3", "Artist 3", "genre 1", 2011,
                200, 333, 200,203,283);
        songTree.insert(song1);
        songTree.insert(song2);
        songTree.insert(song3);
        Backend backend = new Backend(songTree);

        // Input values
        backend.getRange(190,206);

        List<String> filteredSongs = backend.filterOldSongs(2014);

        // Testing getRange in ascending order
        assertEquals(2, filteredSongs.size(), filteredSongs.toString()); // size should be 2
        // First song should be Song 3
        assertEquals("Song 2", filteredSongs.get(0),
                "First song not correct");
        // Second song should be Song 2
        assertEquals("Song 3", filteredSongs.get(1), "Second song not correct");
    }

    /**
     * Tests if top 5 most danceable songs in a certain range and year prints out
     */
    @Test
    public void testFiveMostDanceable() {
        // Creating song instances
        SongInterface song1 = new Song("Song 1", "Artist 1", "genre 1", 2010,
                123, 333, 200,203,283);
        SongInterface song2 = new Song("Song 2", "Artist 2", "genre 1", 2010,
                123, 333, 201,203,283);
        SongInterface song3 = new Song("Song 3", "Artist 3", "genre 1", 2010,
                123, 333, 202,203,283);
        SongInterface song4 = new Song("Song 4", "Artist 4", "genre 1", 2010,
                123, 333, 203,203,283);
        SongInterface song5 = new Song("Song 5", "Artist 5", "genre 1", 2010,
                123, 333, 204,203,283);
        SongInterface song6 = new Song("Song 6", "Artist 6", "genre 1", 2010,
                123, 333, 205,203,283);
        songTree.insert(song1);
        songTree.insert(song2);
        songTree.insert(song3);
        songTree.insert(song4);
        songTree.insert(song5);
        songTree.insert(song6);
        Backend backend = new Backend(songTree);

        // Applying range and filter
        backend.getRange(121,131);
        backend.filterOldSongs(2010);

        List<String> fiveMostDanceable = backend.fiveMostDanceable();

        // Testing 5 most danceable in ascending order
        assertEquals(5, fiveMostDanceable.size(), fiveMostDanceable.toString()); // size should be 5
        // First song should be Song 2
        assertEquals("201: Song 2", fiveMostDanceable.get(0), "First song not correct");
        // Second song should be Song 3
        assertEquals("202: Song 3", fiveMostDanceable.get(1), "Second song not correct");
        // Third song should be Song 4
        assertEquals("203: Song 4", fiveMostDanceable.get(2), "Third song not correct");
        // Fourth song should be Song 5
        assertEquals("204: Song 5", fiveMostDanceable.get(3), "Fourth song not correct");
        // Fifth song should be Song 6
        assertEquals("205: Song 6", fiveMostDanceable.get(4), "Fifth song not correct");
    }

    /**
     * Tests if when entering filename, if it is read
     */
    @Test
    public void testIntegrationReadData() {
        // User input simulation
        String userInput = "R\nsongs.csv\nQ\n";
	System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        // Saves output content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Backend backend = new Backend(songTree);
        Scanner scanner = new Scanner(System.in);
        // Initializes frontend with backend
        FrontendInterface frontend = new Frontend(scanner, backend);

        frontend.runCommandLoop(); // Runs frontend

        // Checks if file is read
        String output = outContent.toString();
        assertTrue(output.contains("Done reading file."));

        // Resets system
        System.setOut(System.out);
        System.setIn(System.in);
    }

    /**
     * Tests if 2 songs are printed out, after filtering year and BPM 
     */
    @Test
    public void testIntegrationFilterSongs() {
        // User input simulation to filter year to 2010 and BPM to 180-186
        String input = "F\n2010\nG\n180 - 186\nQ\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Saves output content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Creates song instances and saving in backend
        SongInterface song1 = new Song("Song 1", "Artist 1", "genre 1", 2010,
                185, 333, 200,203,283);
        SongInterface song2 = new Song("Song 2", "Artist 2", "genre 1", 2010,
                183, 333, 200,203,283);
        SongInterface song3 = new Song("Song 3", "Artist 3", "genre 1", 2011,
                185, 333, 200,203,283);
        songTree.insert(song1);
        songTree.insert(song2);
        songTree.insert(song3);
        Backend backend = new Backend(songTree);

        // Creates frontend, implementing backend
        Scanner scanner = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(scanner, backend);

        frontend.runCommandLoop(); // Runs frontend

        String outputStr = outContent.toString();
        // Tests if output has "Enter maximum year:" after user selects filter year
        assertTrue(outputStr.contains("Enter maximum year:"));
        // Tests if output has "Enter range of values (MIN - MAX):" after user selects BPM range
        assertTrue(outputStr.contains("Enter range of values (MIN - MAX):"));
        // Tests if output finds the 2 songs filtered
        assertTrue(outputStr.contains("2 songs found between 180 - 186"));
        // Tests if first song is Song 2
        assertTrue(outputStr.contains("Song 2"));
        // Tests if second song is Song 1
        assertTrue(outputStr.contains("Song 1"));

        // Resets system
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * Tests if frontend handles an invalid command correctly
     */
    @Test
    public void testInvalidCommandForPartner() {
        // User input simulation of entering an invalid command: "X"
        String simulatedUserInput = "X\nQ\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // Saves output content
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Backend backend = new Backend(songTree);
        Scanner scanner = new Scanner(System.in);
        // Initializes frontend
        FrontendInterface frontend = new Frontend(scanner, backend);
        frontend.runCommandLoop();
        // Checks if frontend handles invalid command correctly
        assertTrue(out.toString().contains("Invalid command."), "Frontend not handling invalid command");

        // Resets system
        System.setOut(System.out);
        System.setIn(System.in);
    }

    /**
     * Tests if frontend handles calling top 5 danceable songs without getting range
     */
    @Test
    public void testDisplayTopFiveWithoutGetRangeForPartner() {
        // User input simulation to call top 5 danceable without getting range
        String simulatedUserInput = "D\nQ\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // Saves output content
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Scanner in = new Scanner(System.in);

        Backend backend = new Backend(songTree);
        Scanner scanner = new Scanner(System.in);
        // Initializes frontend
        FrontendInterface frontend = new Frontend(scanner, backend);
        frontend.runCommandLoop();
        String output = out.toString();
        // Tests if frontend correctly handles calling top 5 danceable songs without getting range
        assertTrue(output.contains("Please firstly call [G]et songs by Speed BPM."), output);

        // Resets system
        System.setOut(System.out);
        System.setIn(System.in);
    }
}
