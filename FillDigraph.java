import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FillDigraph {
	EdgeWeightedDigraph G;
	private static LocalTime currentArrivalTime;
	private static LocalTime currentDepartureTime;
	private static LocalTime nextArrivalTime;
	private static LocalTime nextDepartureTime;
	

	FillDigraph(){
	
	BufferedReader reader;
	try {

		int numberOfLines = 0;
		
		reader = new BufferedReader(new FileReader("stops.txt"));
		reader.readLine();
		
		while (reader.ready()) {
			
			numberOfLines += 1;
			reader.readLine();
			
		}
		G = new EdgeWeightedDigraph(numberOfLines);
		
		int i = 0;
		reader = new BufferedReader(new FileReader("stops.txt"));
		String currentLine = reader.readLine();
		currentLine = reader.readLine();
		while (currentLine != null) {
			String[] splitLine = currentLine.split(",");
			G.mapStop(Integer.parseInt(splitLine[0]),i);
			i++;
			currentLine = reader.readLine();
			
		}
			
		
		reader = new BufferedReader(new FileReader("transfers.txt"));
		reader.readLine();
		String current_line = reader.readLine();
		
		while (current_line != null) {
			String[] splitLine = current_line.split(",");
			int transfer_type = Integer.parseInt(splitLine[2]);
			int wght = 1;
			int from_stop_id = Integer.parseInt(splitLine[0]);
			int to_stop_id = Integer.parseInt(splitLine[1]);
			
			if (transfer_type == 0) {
				wght = 2;
			}
			else if (transfer_type == 2) {
				int min_transfer_time = Integer.parseInt(splitLine[3]);
				wght = min_transfer_time/100;
			}
			G.createEdge(from_stop_id, to_stop_id, wght);
			current_line = reader.readLine();
		}
		
		
		
		// We use two readers to check if the stop sequences are aligned.
		BufferedReader readerOne = new BufferedReader(new FileReader("stop_times.txt"));
		BufferedReader readerTwo = new BufferedReader(new FileReader("stop_times.txt"));
		
		
		readerOne.readLine();
		readerTwo.readLine();
		readerTwo.readLine();
		
		currentLine = readerOne.readLine();
		String nextLine = readerTwo.readLine();
		
		while (currentLine != null && nextLine != null) {
			
			String[] splitCurrentLine = currentLine.split(",");
			String[] splitNextLine = nextLine.split(",");
			
			
			int currentTripId = Integer.parseInt(splitCurrentLine[0]);
			int nextTripId = Integer.parseInt(splitNextLine[0]);
			int currentStopId = Integer.parseInt(splitCurrentLine[3]);
			int nextStopId = Integer.parseInt(splitNextLine[3]);
			
			try {
				currentArrivalTime = LocalTime.parse(splitCurrentLine[1], DateTimeFormatter.ofPattern("hh:mm:ss"));
				currentDepartureTime = LocalTime.parse(splitCurrentLine[2], DateTimeFormatter.ofPattern("hh:mm:ss"));
				nextArrivalTime = LocalTime.parse(splitNextLine[1], DateTimeFormatter.ofPattern("hh:mm:ss"));
				nextDepartureTime = LocalTime.parse(splitNextLine[2], DateTimeFormatter.ofPattern("hh:mm:ss"));
			} catch (Exception e) {
				currentArrivalTime = null;
				nextArrivalTime = null;
			}
			
			if (currentTripId == nextTripId && nextArrivalTime != null && nextDepartureTime != null) {
				G.createEdge(currentStopId, nextStopId, 1);
		
			}
			
			currentLine = readerOne.readLine();
			nextLine = readerTwo.readLine();
			
		} 
	} catch(Exception e){
		e.printStackTrace();
	}
	
}

    EdgeWeightedDigraph returnGraph()
    {
			return G;
    }
    
}
