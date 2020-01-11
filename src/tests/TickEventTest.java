package tests;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.TickEvent;
import model.TickEvent.NoteOnEvent;
import model.TickEvent.NoteOffEvent;

public class TickEventTest {
	
	private Sequencer sequencer;
	private Track track;
	Sequence sequence;
	
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
	public void testNoteOn() {
		try {
			TickEvent tickEvent = new NoteOnEvent((byte) 50);
			
			tickEvent.addMidiEvent(track, 4);
			tickEvent.addMidiEvent(track, 8);
			tickEvent.addMidiEvent(track, 12);
			
			//Only 2 of them play, because the track ends right after the last one.
			
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testNoteOff() {
		try {
			TickEvent tickEvent = new NoteOnEvent((byte) 50);
			TickEvent offEvent = new NoteOffEvent();
			
			tickEvent.addMidiEvent(track, 4);
			tickEvent.addMidiEvent(track, 8);
			tickEvent.addMidiEvent(track, 12);
			offEvent.addMidiEvent(track, 16);
			
			tickEvent.addMidiEvent(track, 20);
			offEvent.addMidiEvent(track, 24);
			offEvent.addMidiEvent(track, 28);
			
			tickEvent.addMidiEvent(track, 32);
			offEvent.addMidiEvent(track, 36);
			
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} 
	}
	
}
