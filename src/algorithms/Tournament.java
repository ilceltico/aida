package algorithms;

import java.util.Comparator;
import java.util.List;

import model.Measure;

public class Tournament {
	
	private List<Measure> participants;
	
	public Tournament(List<Measure> participants) {
		if (participants == null || participants.size() == 0) 
			throw new IllegalArgumentException("Participants to the Tournament can't be empty");
		this.participants = participants;
	}
	
	public Measure getWinner() {
		return participants.stream().max(Comparator.comparingInt(a -> a.getFitness())).get();
	}

}
