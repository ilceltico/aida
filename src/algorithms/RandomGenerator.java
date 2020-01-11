package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Measure;
import model.TickEvent;
import model.TickEvent.*;

public class RandomGenerator {
	
	private static RandomGenerator instance = null;
	private static Random random = null;
	
	public static RandomGenerator getGenerator() {
		if (instance == null) {
			instance = new RandomGenerator();
			random = new Random();
		}
		
		return instance;
	}
	
	public TickEvent randomEvent() {
		int rnd = random.nextInt(18);
		
		TickEvent result;
		
		switch(rnd) {
			case 0: result = new NoteOffEvent(); break;
			case 17: result = new HoldEvent(); break;
			default: result = new NoteOnEvent(rnd+50); break;
		}
		
		return result;
		
	}
	
	public Measure randomMeasure(int length) {
		List<TickEvent> events = new ArrayList<>();
		for (int i=0; i<length; i++) {
			events.add(this.randomEvent());
		}
		return new Measure(events);
	}

}
