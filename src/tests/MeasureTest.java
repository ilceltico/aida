package tests;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Measure;
import model.TickEvent;
import model.TickEvent.*;

public class MeasureTest {
	
	private Sequencer sequencer;
	private Track track;
	private Sequence sequence;
	
	@Before
	public void initialize() {
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
	public void testMeasure() {
		List<TickEvent> events = new ArrayList<>();
		events.add(new NoteOnEvent(50));
		events.add(new NoteOffEvent());
		events.add(new NoteOnEvent(52));
		events.add(new NoteOnEvent(54));
		events.add(new HoldEvent());
		events.add(new NoteOnEvent(56));
		events.add(new NoteOnEvent(58));
		events.add(new NoteOffEvent());
		
		Measure m = new Measure(events);
		
		try {
			m.addToTrack(track);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

}
