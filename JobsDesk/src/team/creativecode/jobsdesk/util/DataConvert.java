package team.creativecode.jobsdesk.util;

import java.util.ArrayList;
import java.util.List;

public class DataConvert {

	public String convertListToString(List<String> list) {
		String s = "";
		for (String l : list) {
			switch(s) {
			case "":
				s = l;
				break;
			default:
				s = s + "||" + l;
				break;
			}
		}
		return s;
	}
	
	public List<String> convertStringToList(String text){
		List<String> list = new ArrayList<String>();
		for (String sp : text.split("||")) {
			list.add(sp);
		}
		return list;
	}
	
	public List<String> getStringComaData(String text){
		List<String> list = new ArrayList<String>();
		text = text.replace(",", " ");
		text = text.replace(", ", " ");
		
		for (String sp : text.split(" ")) {
			list.add(sp);
		}
		return list;
	}
	
}
