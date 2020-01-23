package model;

import java.util.HashMap;
import java.util.Map;
import model.IScale;
import model.IScale.*;

public class ScaleFactory {
	
	private static ScaleFactory instance;
	
	private Map<String, IScale> scaleMap;
	
	public static ScaleFactory getInstance() {
		if (instance == null)
			instance = new ScaleFactory();
		return instance;
	}
	
	private ScaleFactory() {
		scaleMap = new HashMap<>();
		scaleMap.put("NoScale", new DirectMap());
		scaleMap.put("CMajor", new ScaleCMajor());
		scaleMap.put("PentatonicCMajor", new ScalePentatonicCMajor());
	}
	
	public IScale getScale(String scaleName) {
		if (!scaleMap.containsKey(scaleName))
			throw new IllegalArgumentException("Invalid scale name");
		return scaleMap.get(scaleName);
	}

}
