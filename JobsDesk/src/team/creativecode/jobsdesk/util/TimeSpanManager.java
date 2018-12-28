package team.creativecode.jobsdesk.util;

public class TimeSpanManager {

	// format: ?d ?h ?m ?s
	public int convertRawTimeToSecond(String text) {
		int time = 0;
		
		String[] split = text.split(" ");
		for (String s : split) {
			if (s.contains("d")) {
				s.replace("d", "");
				time += Integer.parseInt(s) * 24 * 60 * 60;
			}
			else if (s.contains("h")) {
				s.replace("h", "");
				time += Integer.parseInt(s) * 60 * 60;
			}
			else if (s.contains("m")) {
				s.replace("m", "");
				time += Integer.parseInt(s) * 60;
			}
			else if (s.contains("s")) {
				s.replace("s", "");
				time += Integer.parseInt(s);
			}
		}
		
		return time;
	}
	
	public int convertRawTimeToMinute(String text) {
		int time = 0;
		
		String[] split = text.split(" ");
		for (String s : split) {
			if (s.contains("d")) {
				s.replace("d", "");
				time += Integer.parseInt(s) * 24 * 60;
			}
			else if (s.contains("h")) {
				s.replace("h", "");
				time += Integer.parseInt(s) * 60;
			}
			else if (s.contains("m")) {
				s.replace("m", "");
				time += Integer.parseInt(s);
			}
		}
		
		return time;
	}
	
	public int convertRawTimeToHour(String text) {
		int time = 0;
		
		String[] split = text.split(" ");
		for (String s : split) {
			if (s.contains("d")) {
				s.replace("d", "");
				time += Integer.parseInt(s) * 24;
			}
			else if (s.contains("h")) {
				s.replace("h", "");
				time += Integer.parseInt(s);
			}
		}
		
		return time;
	}
	
}
