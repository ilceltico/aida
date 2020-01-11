package algorithms;

import java.util.List;

import model.Measure;
import model.TickEvent;

public class SimpleCrossOver {
	
	private static SimpleCrossOver instance;
	
	public static SimpleCrossOver getInstance() {
		if (instance == null)
			instance = new SimpleCrossOver();
		return instance;
	}
	
	public Measure apply(Measure parent1, Measure parent2) {
		if (parent1 == null || parent2 == null || parent1.getTickEvents().size() == 0 || parent2.getTickEvents().size() == 0)
			throw new IllegalArgumentException("Parents must contain something");;
		
		// events1 is the shortest
		List<TickEvent> events1 = (parent1.getLength()<parent2.getLength()) ? parent1.getTickEvents() : parent2.getTickEvents();
		List<TickEvent> events2 = (parent2.getLength()<parent1.getLength()) ? parent1.getTickEvents() : parent2.getTickEvents();
		
		int splitPoint1 = 1+RandomGenerator.getGenerator().nextInt(events1.size()-1);
		int splitPoint2 = events2.size() - (events1.size() - splitPoint1);
		
		List<TickEvent> childEvents = events1.subList(0, splitPoint1);
		childEvents.addAll(events2.subList(splitPoint2, events2.size()));
		
		return new Measure(childEvents);
		
	}

}
