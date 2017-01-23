package scc.flashcards.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.InternalServerErrorException;

import com.owlike.genson.Genson;

public class LearningStrategyFactory {

	private static Map<String, Class> nameToClassMap;
	
	public static LearningStrategy create(String strategyName){
		Class resultClass = getClassMap().get(strategyName);
		try {
			return (LearningStrategy)resultClass.newInstance();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new InternalServerErrorException(new Genson().serialize("Couldn't instantiate learning strategy "+strategyName));
		}
	}

	private static Map<String, Class> getClassMap() {
		if(nameToClassMap == null) {
			nameToClassMap = new HashMap<String, Class>();
			nameToClassMap.put("linear", LinearLearningStrategy.class);
			nameToClassMap.put("flat_exponential", FlatExponentialLearningStrategy.class);
			nameToClassMap.put("steep_exponential", SteepExponentialLearningStrategy.class);
			nameToClassMap.put("advanced", AdvancedLearningStrategy.class);
		}
		
		return nameToClassMap;
	}
}
