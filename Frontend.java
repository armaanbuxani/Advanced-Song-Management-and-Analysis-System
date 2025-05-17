import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


import java.util.List;

public class Frontend implements FrontendInterface{
    private String min = "min";
    private String max = "max";
    private String year = "none";
    
    private BackendInterface backend;
    Scanner in = new Scanner(System.in);
    public Frontend(Scanner in, BackendInterface backend) {
        this.backend = backend;
        this.in = in;
    }

    /**
     * Repeated gives the user an opportunity to issue new commands until
     * they select Q to quit.
     */
    public void runCommandLoop() {
	try{
	displayMainMenu();
	//Firstly display the menue to the user
	String line = in.nextLine();
	//Then call different method base on the user inputs.
        if ("R".equals(line)) {
            readFile();
            runCommandLoop();
        } else if ("G".equals(line)) {
            getValues();
            runCommandLoop();
        } else if ("F".equals(line)) {
            setFilter();
            runCommandLoop();
        } else if ("D".equals(line)) {
            topFive();
            runCommandLoop();
        } else if ("Q".equals(line)) {
            return;
        } else {
            System.out.println("Invalid command.");
		runCommandLoop();
        }
	} catch (NoSuchElementException e){
	System.out.println("Invalid command.");
	}
    }

    /**
     * Displays the menu of command options to the user.
     */
    public void displayMainMenu() {
		
	String menu = """
	    
	    ~~~ Command Menu ~~~
	        [R]ead Data
	        [G]et Songs by Speed BPM [min - max]
	        [F]ilter Old Songs (by Max Year: none)
	        [D]isplay Five Most Danceable
	        [Q]uit
	    Choose command:""";
	menu=menu.replace("min",min).replace("max",max).replace("none",year);
	System.out.print(menu + " ");
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [R]ead Data command.
     */
    public void readFile() {
        
        System.out.print("Enter path to csv file to load: ");
    
        String filePath = in.nextLine(); 
        //Get the file path from the user
    
        try {
            backend.readData(filePath); 
            //Call readData method from backend
            System.out.println("Done reading file."); 
            //If the file is read successfully, print "Done reading file."
        } catch (IOException e) {
            System.out.println("Wrong file path."); 
            //If the file path is wrong, print "Wrong file path."
        }
    }
    
    /**
     * Provides text-based user interface and error handling for the 
     * [G]et Songs by Speed BPM command.
     */
    public void getValues() {
	    System.out.print("Enter range of values (MIN - MAX): ");

	    String userInput = in.nextLine();
        String[] values = userInput.split(" ");
	//Seperate the user input
        min = values[0];
        max = values[2];
	//Get the input value
        int low = Integer.parseInt(min);
        int high = Integer.parseInt(max);
	//Turn into integer
        List<String> songs = backend.getRange(low, high);
	if (songs.size() == 0) {
	//Combine with the change in BackendPlaceholder, this should be the case that low>high
            System.out.println("No song in range.");
            return;
        }
	//Otherwise, it should print all the song name.
        System.out.println(songs.size() + " songs found between " + min + " - " + max + ":");
        for (String song : songs) {
            System.out.println("\t" + song);
        }
        
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [F]ilter Old Songs (by Max Year) command.
     */
    public void setFilter() {
	    System.out.print("Enter maximum year: ");
        
        String userInput = in.nextLine();
        year = userInput;
        int maxYear = Integer.parseInt(userInput);
	//Get the user input and change ito integer
        List<String> songs = backend.filterOldSongs(maxYear);
	if(songs.size() == 0){
	//Combine with the change in the backendplaceholder, this should be the case that the year is too old.
            System.out.println("No song older than that.");
            return;
        }
	//Otherwise, it should print all the song name.
        System.out.println(songs.size() + " songs found between " + min + " - "+ max +" from "+ year +":");
        for (String song : songs) {
            System.out.println("\t" + song);
        }
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [D]isplay Five Most Danceable command.
     */
    public void topFive() {
	try{
        	List<String> songs = backend.fiveMostDanceable();
        	System.out.println("Top Five songs found between " + min + " - " + max + " from " + year + ":");
		//Just output what backend return.
		if(songs.size() == 0) {
			System.out.println("No song in this range.");
		} else{
        		for (int i = 0; i < songs.size(); i++) {
            			System.out.println("\t" + songs.get(i));
			}
		}
        } catch (IllegalStateException e) {
		System.out.println("Please firstly call [G]et songs by Speed BPM.");
	}
    }
}
