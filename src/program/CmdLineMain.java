package program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	private static String baseFolder = "results";
	private static String thisExecutionFolder = java.time.LocalDateTime.now().toString();
	
	private static int populationSize = 8;
	private static int measureLength = 16;
	private static int randomNewIndividuals = 2;
	
	private static double mutationProbability = 0.2;
	private static int tournamentSize = 4;
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in); 
		
		
		// Generate random population
		List<Measure> population = RandomGenerator.getGenerator().randomMeasures(measureLength, populationSize);
		GeneticAlgorithm alg = new GeneticAlgorithm(mutationProbability, tournamentSize);
		int generation = 1;
		
		setupFolders();
		
		do {
			System.out.println("\nGeneration " + generation);
			savePopulation(population, generation);
			showPopulation(population);
			System.out.println("\nChoose the measure to play and set fitness. -1 to play all.\nType 'G' to generate a new epoch, 'E' to exit");
			String line = in.nextLine();
			if ("G".equals(line)) {
				System.out.println("Saving previous generation...");
				saveGenerationInfo(population, generation);
				System.out.println("Generating children...");
				population = alg.produceNewIndividuals(population, populationSize-randomNewIndividuals);
				population.addAll(RandomGenerator.getGenerator().randomMeasures(measureLength, randomNewIndividuals));
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
	
	public static void saveGenerationInfo(List<Measure> population, int generation) {
		String path = baseFolder + File.separatorChar + 
				thisExecutionFolder + File.separatorChar + "gen_" + 
				generation + File.separatorChar + "info.txt";
		try {
			PrintWriter writer = new PrintWriter(new File(path));
			for (int i=0; i<population.size(); i++) {
				writer.write("Measure " + i + ": fitness = " + population.get(i).getFitness() + "; notes = " + population.get(i).toString() + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void savePopulation(List<Measure> population, int generation) {
		try {
			
			String path = baseFolder + File.separatorChar + 
					thisExecutionFolder + File.separatorChar + "gen_" + generation;
			File folder = new File(path);
			if (!folder.mkdir()) {
				System.out.println("Couldn't create folder for generation " + generation);
				return;
			}
			
			
			for (int i=0; i<population.size(); i++) {
				Measure m = population.get(i);
				
				sequencer = MidiSystem.getSequencer();
				sequencer.open(); 
				
				sequence = new Sequence(Sequence.PPQ, 2);
				track = sequence.createTrack(); 
				
				ShortMessage sm = new ShortMessage(192,1,1,0); //Set instrument (the third number, 1, is Acoustic Piano)
				track.add(new MidiEvent(sm, 0));
				
				m.addToTrack(track);
				
				track.add(new MidiEvent(new ShortMessage(128,1,50,100), m.getLength()));
		
				sequencer.setSequence(sequence); 
				
				sequencer.setTempoInBPM(120); 
				
				
				
				File f = new File(path + File.separatorChar + "measure_"+i+".mid");
				MidiSystem.write(sequence,1,f);
			}
			
		} catch (InvalidMidiDataException | MidiUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void playMeasure(Measure m) {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open(); 
			
			sequence = new Sequence(Sequence.PPQ, 2);
			track = sequence.createTrack(); 
			
			
			ShortMessage sm = new ShortMessage(192,1,1,0); //Set instrument (the third number, 1, is Acoustic Piano)
			track.add(new MidiEvent(sm, 0));
			
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
	
	public static void setupFolders() {
		//Create base folder
		File f = new File(baseFolder);
		if (f.exists()) {
			if (!f.isDirectory()) {
				System.out.println(baseFolder + " already exists and is not a folder");
				System.exit(1);
			}
		} else {
			if (!f.mkdir()) {
				System.out.println("Error in " + baseFolder + " folder creation.");
				System.exit(1);
			}
		}
		f = new File(baseFolder + File.separatorChar + thisExecutionFolder);
		if (!f.mkdir()) {
			System.out.println("Error in " + thisExecutionFolder + " folder creation.");
			System.exit(1);
		}
				
	}

}
