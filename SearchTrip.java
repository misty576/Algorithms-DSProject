import java.io.*;
import java.util.ArrayList;
import java.time.LocalTime;
import java.io.FileNotFoundException;
import java.io.IOException;



public class SearchTrip {

	private static BufferedReader reader;

	public static ArrayList<String> convertFile(String inputFile) throws FileNotFoundException, IOException {
		
		ArrayList<String> output = new ArrayList<>();
		
		reader = new BufferedReader(new FileReader(inputFile));
		
			while (reader.ready()) {
				
				output.add(reader.readLine());
			}
			return output;
	}
	
	public static ArrayList<String> getMatchedTrips (String time) throws FileNotFoundException, IOException {
		
		ArrayList<String> convertedList = convertFile("stop_times.txt");
		
		ArrayList<String> searchResult = new ArrayList<String>();
		
		int idx = 1;
		
		while (convertedList.size() > idx) {
			
			try {
				InputFiles inputfile = new InputFiles();
				String count = convertedList.get(idx);
				InputFiles.StopTimes StopTimeobj = inputfile.new StopTimes(count);
				idx += 1;
					LocalTime checkT = StopTimeobj.getArrivalTime();
					
					
					int hr = checkT.getHour();
					
					if (hr > 24) {
						convertedList.remove(idx);
					}
					
			} catch (Exception e) {
			}
			
					
		}
		
		for (int i = 1; i < convertedList.size(); i++) {
			
			String idx2 = convertedList.get(i);
			InputFiles inputfile = new InputFiles();
			InputFiles.StopTimes StopTimeobj = inputfile.new StopTimes(idx2);
			String busArrivalTime = StopTimeobj.getArrivalTimeStr();
			
			try {
				
				if(time.equals(busArrivalTime)) {
					
					String busInfo = "Trip Id: " + Integer.toString(StopTimeobj.getTripId()) + "\n"
							+ "Stop Id: " + Integer.toString(StopTimeobj.getStopId());
					searchResult.add(busInfo);
				}
			} catch (Exception e) {
				
			}
			
		}
		return searchResult;
	}
}
