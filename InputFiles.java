import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class InputFiles {
		
	// We neglect stop_url location_type and parent_station
	
	// Our next goal is to parse each line containing the stop information
	
	public class Stop extends InputFiles {
		
		String stop_id;
		String stop_code;
		String stop_name;
		String stop_desc;
		String stop_lat;
		String stop_lon;
		String zone_id;
		
		public Stop (String line) {
			
			String[] values = line.split(",");
			
			// We now want to get rid of all the input lines with invalid entries, so we use a try/catch condition 
			
			try {
				
				this.stop_id = values[0];
				this.stop_code = values[1];
				this.stop_name = moveKeywords(values[2]);
				this.stop_desc = values[3];
				this.stop_lat = values[4];
				this.stop_lon = values[5];
				this.zone_id = values[6];
				
			} catch (Exception e){
				
				this.stop_id = "-1";
				
			}
		}
		
		public String moveKeywords(String input) {
            String stop_name = input;

            // We are asked in the assignment to move certain words from the start of the stop name ; this function does that.

            if (stop_name.substring(0, 8).compareTo("FLAGSTOP") == 0) {
                stop_name = stop_name.substring(9) + " FLAGSTOP";
                stop_name.trim();
            }

            if (stop_name.substring(0, 2).compareTo("WB") == 0) {
                stop_name = stop_name.substring(2) + " WB"; 
            }
            else if (stop_name.substring(0, 2).compareTo("NB") == 0) {
                stop_name = stop_name.substring(2) + " NB";
            }
            else if (stop_name.substring(0, 2).compareTo("SB") == 0) {
                stop_name = stop_name.substring(2) + " SB";
            }
            else if (stop_name.substring(0, 2).compareTo("EB") == 0) {
                stop_name = stop_name.substring(2) + " EB";
            }

            return stop_name.trim();
        }
		
		
		public String getID() {
			if (stop_id == "-1") {
				return "Unable to obtain stop ID";
			}
			return " " + this.stop_id;
		}
		
		public String getCode() {
			if (stop_id == "-1") {
				return "Unable to obtain stop ID";
			}
			return " " + this.stop_id;
		}
		
		
		
	}
	
	public class StopTimes extends InputFiles {
		
		int trip_id;
		LocalTime arrival_time;
		LocalTime departure_time;
		int stop_id;
		int stop_sequence;
		int stop_headsign;
		String arrival_time_str;
		
		public StopTimes (String line) {
			
			String[] values = line.split(",");
			
				
				this.arrival_time_str = values[1];
				try {
					this.arrival_time = LocalTime.parse(values[1], DateTimeFormatter.ofPattern("hh:mm:ss"));
				} catch (Exception e) {
					this.arrival_time = null;
				}
				
				try {
					this.departure_time = LocalTime.parse(values[2], DateTimeFormatter.ofPattern("hh:mm:ss"));
				} catch (Exception e) {
					this.departure_time = null;
				}
			
				this.trip_id = Integer.parseInt(values[0]);
				this.stop_id = Integer.parseInt(values[3]);
				/*
 * 
 * 				
				this.stop_sequence = Integer.parseInt(values[4]);
				this.stop_headsign= Integer.parseInt(values[5]);
 */
				
		}
		
		public int getTripId() {
			return trip_id;
		}
		
		public LocalTime getArrivalTime() {
			return arrival_time;
		}
		
		public String getArrivalTimeStr() {
			return arrival_time_str;
		}

		public int getStopId() {
			return stop_id;
		}
	}

}
