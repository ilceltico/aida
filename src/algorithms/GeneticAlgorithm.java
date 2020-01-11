package algorithms;

import java.util.ArrayList;
import java.util.List;

import model.Measure;

public class GeneticAlgorithm {
	
	public GeneticAlgorithm() {
		super();
	}
	
	
	public List<Measure> produceNewIndividuals(List<Measure> population, int num) {
		List<Measure> result = new ArrayList<>();
		
		for (int i=0; i<num; i++) {
			List<Measure> candidates = RandomGenerator.getGenerator().randomFromPopulation(population, 4);
			Measure parent1 = new Tournament(candidates).getWinner();
			candidates = RandomGenerator.getGenerator().randomFromPopulation(population, 4);
			Measure parent2 = new Tournament(candidates).getWinner();
			
			Measure child = SimpleCrossOver.getInstance().apply(parent1, parent2);
			
			Measure childMutated = SimpleMutation.getInstance().mutate(child, 0.05);
			
			result.add(childMutated);
		}
		
		return result;
	}

}
