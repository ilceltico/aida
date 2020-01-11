package tests;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithms.RandomGenerator;
import model.Measure;

public class RandomGeneratorTest {
	private RandomGenerator gen;
	private Sequencer sequencer;
	private Track track;
	Sequence sequence;
	
	@Before
	public void initialize() {
		gen = RandomGenerator.getGenerator();
		
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open(); 
			
			sequence = new Sequence(Sequence.PPQ, 2);
			track = sequence.createTrack(); 
			
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		} 
				
	}
	
	@After
	public void endTest() {
		try {

			sequencer.setSequence(sequence); 
			
			sequencer.setTempoInBPM(120); 
			
			sequencer.start(); 
	
			while (true) { 
				
				Thread.sleep(500);
	
				// Exit the program when sequencer has stopped playing. 
				if (!sequencer.isRunning()) { 
					Thread.sleep(1000); // Wait a bit for the last note to stop.
					sequencer.close(); 
					break;
				} 
			}
		
		} catch (InvalidMidiDataException | InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testRandomEvent() {
		
		try {
			gen.randomEvent().addMidiEvent(track, 0);
			gen.randomEvent().addMidiEvent(track, 2);
			gen.randomEvent().addMidiEvent(track, 4);
			track.add(new MidiEvent(new ShortMessage(128,1,50,100), 6));
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
			
		
	}
	
	@Test
	public void testRandomMeasure() {
		
		Measure m = gen.randomMeasure(50);
		try {
			m.addToTrack(track);
			track.add(new MidiEvent(new ShortMessage(128,1,50,100), 6));
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
	}

}
