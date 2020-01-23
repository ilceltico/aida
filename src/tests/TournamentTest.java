package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import algorithms.RandomGenerator;
import algorithms.Tournament;
import model.Measure;

public class TournamentTest {
	
	@Test
	public void testWinner() {
		List<Measure> participants = new ArrayList<>();
		for (int i=0; i<4; i++) {
			Measure m = RandomGenerator.getGenerator().randomMeasure(8, 10);
			m.setFitness(i);
			participants.add(m);
		}
		Tournament t = new Tournament(participants);
		
		assertEquals(3, t.getWinner().getFitness());
	}
	
	@Test
	public void testOne() {
		List<Measure> participants = new ArrayList<>();
		Measure m = RandomGenerator.getGenerator().randomMeasure(8, 10);
		m.setFitness(1);
		participants.add(m);
		Tournament t = new Tournament(participants);
		
		assertEquals(1, t.getWinner().getFitness());
	}

}
