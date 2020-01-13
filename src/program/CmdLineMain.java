package program;

import java.util.List;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import algorithms.GeneticAlgorithm;
import algorithms.RandomGenerator;
import model.Measure;

public class CmdLineMain {
	
	private static Sequencer sequencer;
	private static Track track;
	private static Sequence sequence;
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in); 
		
		
		// Generate random population
		List<Measure> population = RandomGenerator.getGenerator().randomMeasures(8, 8);
		GeneticAlgorithm alg = new GeneticAlgorithm();
		int generation = 1;
		
		do {
			System.out.println("\nGeneration " + generation);
			showPopulation(population);
			System.out.println("\nChoose the measure to play and set fitness. -1 to play all.\nType 'G' to generate a new epoch, 'E' to exit");
			String line = in.nextLine();
			if ("G".equals(line)) {
				System.out.println("Generating children...");
				population = alg.produceNewIndividuals(population, 6);
				population.addAll(RandomGenerator.getGenerator().randomMeasures(8, 2));
				generation++;
			} else if ("E".equals(line)) {
				System.out.println("Exiting...");
				break;
			} else {
				try {
					int index = Integer.parseInt(line);
					if (index < -1 || index >= population.size()) {
						System.out.println("Invalid number.");
						continue;
					}
					
					if (index == -1) {
						for (int i=0; i<population.size(); i++) {
							System.out.println("Playing Measure " + i + ", with fitness = " + population.get(i).getFitness());
							playMeasure(population.get(i));
						}
					} else {
						System.out.println("Playing Measure " + index + ", with fitness = " + population.get(index).getFitness());
						
						playMeasure(population.get(index));
						
						System.out.println("Insert a number for the new fitness value, anything else to keep the old value:");
						line = in.nextLine();
						
						try {
							int fitness = Integer.parseInt(line);
							population.get(index).setFitness(fitness);
						} catch (NumberFormatException e) {
							continue;
						}
					}
					
				} catch (NumberFormatException e) {
					System.out.println("Unrecognized Command.");
					continue;
				}
			}
			
		} while(true);
		
		in.close();
	}
	
	public static void showPopulation(List<Measure> population) {
		for (int i=0; i<population.size(); i++) {
			System.out.println("Measure " + i + ": fitness = " + population.get(i).getFitness() + "; notes = " + population.get(i).toString());
		}
	}
	
	
	public static void playMeasure(Measure m) {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open(); 
			
			sequence = new Sequence(Sequence.PPQ, 2);
			track = sequence.createTrack(); 
			
			m.addToTrack(track);
			
			track.add(new MidiEvent(new ShortMessage(128,1,50,100), m.getLength()));

			sequencer.setSequence(sequence); 
			
			sequencer.setTempoInBPM(120); 
			
			sequencer.start(); 
	
			while (true) { 
				
				Thread.sleep(500);
	
				if (!sequencer.isRunning()) { 
					Thread.sleep(1000); // Wait a bit for the last note to stop.
					sequencer.close(); 
					break;
				} 
			}
		
		} catch (InvalidMidiDataException | InterruptedException | MidiUnavailableException e) {
			e.printStackTrace();
		} 
	}

}
