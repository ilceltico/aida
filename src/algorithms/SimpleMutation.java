package algorithms;

import java.util.List;

import model.Measure;
import model.TickEvent;

public class SimpleMutation {
	
	private static SimpleMutation instance;
	
	public static SimpleMutation getInstance() {
		if (instance == null)
			instance = new SimpleMutation();
		return instance;
	}
	
	public Measure mutate(Measure m, double probability) {
		if (m == null || m.getLength() == 0)
			throw new IllegalArgumentException("Can't mutate empty Measures");
		if (probability < 0 || probability > 1)
			throw new IllegalArgumentException("Invalid probability");
		
		List<TickEvent> events = m.getTickEvents();
		
		for (int i = 0; i<events.size(); i++) {
			boolean mutate = RandomGenerator.getGenerator().nextInt(1000) < probability*1000;
			if (mutate) {
				TickEvent e = RandomGenerator.getGenerator().randomEvent();
				events.set(i, e);
			}
		}
		
		return new Measure(events);
	}

}
