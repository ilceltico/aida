package algorithms;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import model.Measure;

public class Tournament {
	
	private List<Measure> participants;
	
	public Tournament(List<Measure> participants) {
		if (participants == null || participants.size() == 0) 
			throw new IllegalArgumentException("Participants to the Tournament can't be empty");
		this.participants = participants;
	}
	
	public Measure getWinner() {
		// Random in case of tie:
		// Get max
		int maxFitness = participants.stream().max(Comparator.comparingInt(m -> m.getFitness())).get().getFitness();
		List<Measure> tied = participants.stream().filter(a -> a.getFitness() == maxFitness).collect(Collectors.toList());
		
		if (tied.size() > 1) {
			int winnerIndex = RandomGenerator.getGenerator().nextInt(tied.size());
			return tied.get(winnerIndex);
		} else
			return tied.get(0);
		
		// Argmax
//		return participants.stream().max(Comparator.comparingInt(a -> a.getFitness())).get();
	}

}
