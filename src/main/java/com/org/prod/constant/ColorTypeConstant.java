package com.org.prod.constant;

import java.util.HashMap;
import java.util.Map;

public class ColorTypeConstant {

	public static Map<String,String> colorToRgbHashMap;
	static {
		colorToRgbHashMap = new HashMap<>();
		colorToRgbHashMap.put("Black","000000");
		colorToRgbHashMap.put("Red","FF0000");
		colorToRgbHashMap.put("Green","008000");
		colorToRgbHashMap.put("Blue","0000FF");
		colorToRgbHashMap.put("White","FFFFFF");
		colorToRgbHashMap.put("Orange","FFA500");
		colorToRgbHashMap.put("Yellow","FFFF00");
		colorToRgbHashMap.put("Aqua","00FFFF");
		colorToRgbHashMap.put("Lavender","E6E6FA");
		colorToRgbHashMap.put("Purple","800080");
		colorToRgbHashMap.put("Grey","808080");
		colorToRgbHashMap.put("Maroon","800000");
		colorToRgbHashMap.put("Pink","FFC0CB");
	}
	
	
	public static Map<String, String> getColorToRgbHashMap() {
		return colorToRgbHashMap;
	}
	public static void setColorToRgbHashMap(Map<String, String> colorToRgbHashMap) {
		ColorTypeConstant.colorToRgbHashMap = colorToRgbHashMap;
	}
	
}
