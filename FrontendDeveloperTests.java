import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
public class FrontendDeveloperTests {
    /**
     * Test the readFile method.
     * This have two cases, one is when the file path isn't correct, it will lead to the output of Wrong file path.
     * The other case is when the file path is correct, it will lead to the output of Done reading file.
     */
    @Test
    public void testReadData() {
        String simulatedUserInput = "R\nmySongData.csv\nQ\n"; 
        //Seting a wrong file path
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(bais);
        //Set this as the user input
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        //Store the output
        Scanner in = new Scanner(System.in);
        IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>();
        BackendInterface backend = new BackendPlaceholder(tree);
        FrontendInterface frontend = new Frontend(in, backend);
        //Create a frontend object to test
        frontend.runCommandLoop();
        assertTrue(out.toString().contains("Wrong file path."), "When a wrong file path is entered, it should print 'Wrong file path.'");
        //Compare the output with the expected output, if they are the same, the test passes
	System.setOut(System.out);
	System.setIn(System.in);
    }
    /**
     * Test the getValue method.
     * This method will tell the user no song in range if the range is invalid.
     * For example, if the left edge is greater than the right edge, it will print No song in range.
     */
    @Test
    public void testGetSong() {
        String simulatedUserInput = "G\n90 - 80\nQ\n"; 
        //Seting a invalid range
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(bais);
        //Set this as the user input
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        //Store the output
        Scanner in = new Scanner(System.in);
        IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>();
        BackendInterface backend = new BackendPlaceholder(tree);
        FrontendInterface frontend = new Frontend(in, backend);
        //Create a frontend object to test
        frontend.runCommandLoop();
        assertTrue(out.toString().contains("No song in range."), "When a missordered range is entered, it should print 'No song inn range.'");
        //Compare the output with the expected output, if they are the same, the test passes
        System.setOut(System.out);
        System.setIn(System.in);
    }
    /**
     * Test the setFilter method.
     * This method will tell the user no song older than that if the year is out of the range of songs in csv file.
     * For example, 1490 is way to early for the songs in the csv file, so it will print No song older than that.
     */
    @Test
    public void testFilterOldSong() {
        String simulatedUserInput = "G\n80 - 90\nF\n1490\nQ\n"; 
        //Seting a year with no song before it
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(bais);
        //Set this as the user input
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        //Store the output
        Scanner in = new Scanner(System.in);
        IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>();
        BackendInterface backend = new BackendPlaceholder(tree);
        FrontendInterface frontend = new Frontend(in, backend);
        //Create a frontend object to test
        frontend.runCommandLoop();
        assertTrue(out.toString().contains("No song older than that."), "When a year out of the range of songs in csv file is entered, it should print 'No song older than that.'");
        //Compare the output with the expected output, if they are the same, the test passes
        System.setOut(System.out);
        System.setIn(System.in);
    }
    /**
     * Test the topFive method.
     * This test method will test whether the setting for finding the top five danceable songs is correct.
     * For example, in the placeholder file, it tells the songs that we are looking for the top danceable value should be between 80 - 90 from 2019.
     */
    @Test
    public void testDisplayFiveDanceable() {
	String simulatedUserInput = "G\n80 - 90\nF\n2019\nD\nQ\n"; 
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(bais);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        //Store the output
        Scanner in = new Scanner(System.in);
        IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>();
        BackendInterface backend = new BackendPlaceholder(tree);
        FrontendInterface frontend = new Frontend(in, backend);
        //Create a frontend object to test
        frontend.runCommandLoop();
        assertTrue(out.toString().contains("Top Five songs found between 80 - 90 from 2019:"), "The setting for finding the top five danceable songs is not correct.");
        //Compare the output with the expected output, if they are the same, the test passes
        System.setOut(System.out);
	System.setIn(System.in);
    }
    /**
     * Test the runCommandLoop method.
     * This test method will test whether the command loop is running correctly.
     * For example, the user input is R, G, F, D, Q, which means the user wants to read the file, get the songs in range, filter the old songs, display the top five danceable songs, and quit the program.
     * Then we should check the last output from the topFive method to see if the command loop is running correctly.
     */
    @Test
    public void testRunCommandLoop() {
        String simulatedUserInput = "R\nsongs.csv\nG\n80 - 90\nF\n2019\nD\nQ\n"; 
        //Seting a year with no song before it
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(bais);
        //Set this as the user input
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        //Store the output
        Scanner in = new Scanner(System.in);
        IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>();
        BackendInterface backend = new BackendPlaceholder(tree);
        FrontendInterface frontend = new Frontend(in, backend);
        //Create a frontend object to test
        frontend.runCommandLoop();
        assertTrue(out.toString().contains("Top Five songs found between 80 - 90 from 2019:"), "The command loop is not running correctly.");
        //Compare the output with the expected output, if they are the same, the test passes
        //Use the output of topFive to test whether the command loop is running correctly
        System.setOut(System.out);
        System.setIn(System.in);
    }
}
