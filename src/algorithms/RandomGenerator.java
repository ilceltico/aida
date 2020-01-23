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
	
	private RandomGenerator() {
		super();
	}
	
	public int nextInt() {
		return random.nextInt();
	}
	
	public int nextInt(int bound) {
		return random.nextInt(bound);
	}
	
	public TickEvent randomEvent(int noteRange) {
		int rnd = random.nextInt(noteRange+2);
		
		TickEvent result;
		
		if(rnd == noteRange-2)
			result = new NoteOffEvent();
		else if(rnd == noteRange-1)
			result = new HoldEvent();
		else
			result = new NoteOnEvent(rnd);
		
		return result;
		
	}
	
	public Measure randomMeasure(int length, int noteRange) {
		List<TickEvent> events = new ArrayList<>();
		for (int i=0; i<length; i++) {
			events.add(this.randomEvent(noteRange));
		}
		return new Measure(events);
	}
	
	public List<Measure> randomMeasures(int length, int numMeasures, int noteRange) {
		List<Measure> result = new ArrayList<>();
		for (int i=0; i<numMeasures; i++) {
			result.add(this.randomMeasure(length, noteRange));
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
