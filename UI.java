import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class UI {

	public static Iterable<String> listOfStops = new Queue<String>();

	
	public static void main(String[] args) throws FileNotFoundException, IOException {

		TST<String> tst = new TST<String>("stops.txt");
		Scanner input = new Scanner(System.in);
		System.out.println("Please choose an option of what function you want to run: \n" +
							" Option 1 (Type \"1\") -> Shortest path between two bus stops \n" +
							" Option 2 (Type \"2\") -> Search for bus stop \n" +
							" Option 3 (Type \"3\") -> Search for trips using arrival time \n " +
							" Or type \"exit\" to terminate the program \n");
		System.out.println("Input -> ");

		String userInput = input.nextLine();

		
	 if (userInput.equals("exit")) {
		 System.out.println("terminating program...");
		 System.exit(0);
	 }
	 else if (userInput.equals("1")){
		 System.out.println("On the first line type in the starting stop number; hit enter and then type in the final stop on the second line");
		 String initial_stop = input.nextLine();
		 String final_stop = input.nextLine();
		 
		 
		 int initial_stopInt = Integer.parseInt(initial_stop);
		 int final_stopInt = Integer.parseInt(final_stop);
		 
		 FillDigraph digraph = new FillDigraph();
		 EdgeWeightedDigraph G = digraph.returnGraph();
		 
		 DijkstraSP dijkstrasp = new DijkstraSP(G, initial_stopInt);
		 
		 String shortestPath = dijkstrasp.sp(initial_stopInt, final_stopInt);
		 System.out.println(shortestPath);
		
	 }
	 else if (userInput.equals("2")) {
		 System.out.println("\nPlease type in the word of a bus stop you want to look for");
		 
		 String userInput2 = input.nextLine();
		 String cap_userInput2 = userInput2.toUpperCase();
		 System.out.println("Please find below the results obtained by the program: \n");
		 tst.getStopInformation(cap_userInput2).forEach((info)->{
			 System.out.println(info);
		 });
		 
	 }
	 else if (userInput.equals("3")) {
				System.out.println("\nPlease enter the arrival time below in the following format ->  hh:mm:ss \n");
				
				ArrayList<String> arrivalTimesList = new ArrayList<String>();
				System.out.println("Input -> ");
				String userInput1 = input.nextLine();
				System.out.println("Please wait a moment. The program takes around 30 seconds maximum to return results:\n");
				
				if (userInput1.length() > 3) {
					
					if (userInput1.charAt(1) == ':') {
						userInput1 = " " + userInput1;
					}
				
				if (validTime(userInput1)) {
					arrivalTimesList = SearchTrip.getMatchedTrips(userInput1);
					
					if (arrivalTimesList.isEmpty()) {
						System.out.println("We could not find any results for the given input. Please try again.");
						System.exit(0);
					}
					System.out.println("\nPlease find below the trips found: \n");
					
					for (int i = 0; i< arrivalTimesList.size(); i++) {
						System.out.println("\nTrip Id: ");
						System.out.println(arrivalTimesList.get(i));
					}
				}
				
				System.exit(0);
			
			
			
		 }
	 }  else {
		 System.out.println("Invalid entry. Please run the application again to go back to option screen");
		 System.exit(0);
	}
	
		

}
	
	public static boolean validTime(String input) {

		boolean result = true;
		if (input.length() < 1 || input.length() > 8) {
			System.out.println("Invalid input, please run the program to try again.");
			return false;
		}

		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i)) == true) {
				System.out.print("Invalid input, time inputs must contain only numbers. Please run the program to try again.");
				return false;
			}
		}

		if (input.length() == 8) {
			if (input.charAt(2) != ':' || input.charAt(5) != ':') {
				System.out.println("Invalid input, please use the correct formatting. Please run the program to try again.");
				return false;
			}
		}

		String[] checkInput = null;

		if (input.charAt(0) == ' ') {
			input = input.substring(1);
		}
		checkInput = input.split(":");

		if (Integer.parseInt(checkInput[0]) > 23 || Integer.parseInt(checkInput[1]) > 59 || Integer.parseInt(checkInput[2]) > 59) {
			System.out.println("Invalid input, please use a valid time. Please run the program to try again.");
			result = false;
		}


		return result;
	}
	
	

}
