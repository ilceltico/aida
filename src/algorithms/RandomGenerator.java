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
	
	public int nextInt() {
		return random.nextInt();
	}
	
	public int nextInt(int bound) {
		return random.nextInt(bound);
	}
	
	public TickEvent randomEvent() {
		int rnd = random.nextInt(10);
		
		TickEvent result;
		
		switch(rnd) {
			case 0: result = new NoteOffEvent(); break;
			case 1: result = new NoteOnEvent(60); break;
			case 2: result = new NoteOnEvent(62); break;
			case 3: result = new NoteOnEvent(64); break;
			case 4: result = new NoteOnEvent(65); break;
			case 5: result = new NoteOnEvent(67); break;
			case 6: result = new NoteOnEvent(69); break;
			case 7: result = new NoteOnEvent(71); break;
			case 8: result = new NoteOnEvent(72); break;
			case 9: result = new HoldEvent(); break;
			default: result = new NoteOnEvent(rnd+59); break;
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
	
	public List<Measure> randomMeasures(int length, int numMeasures) {
		List<Measure> result = new ArrayList<>();
		for (int i=0; i<numMeasures; i++) {
			result.add(this.randomMeasure(length));
		}
		return result;
	}
	
	public List<Measure> randomFromPopulation(List<Measure> population, int num) {
		if (population == null || population.size() == 0)
			throw new IllegalArgumentException("Can't use empty populations");
		if (population.size() <= num)
			throw new IllegalArgumentException("Can't produce more results than population size");
		
		List<Measure> result = new ArrayList<>();
		
		for (int i=0; i<num; i++) {
			int choice = random.nextInt(population.size());
			
			if (result.contains(population.get(choice)))
				continue;
			
			result.add(population.get(choice));
		}
		
		return result;
	}
	
//	public List<Measure> randomParentsByFitness(List<Measure> population, int numParents) {
//		if (population == null || population.size() == 0)
//			throw new IllegalArgumentException("Can't use empty populations");
//		if (population.size() <= numParents)
//			throw new IllegalArgumentException("Can't produce more parents than population size");
//		
//		List<Measure> parents = new ArrayList<>();
//		
//		int randomMax = population.stream().map(x -> x.getFitness()).reduce(0, Integer::sum);
//		
//		for (int i=0; i<numParents; i++) {
//			int choice = random.nextInt(randomMax);
//			int populationIndex = 
//			
//			if (parents.contains(population.get(choice)))
//				continue;
//			
//		}
//		
//		return parents;
//	}

}
