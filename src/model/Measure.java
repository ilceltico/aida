package model;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Track;

public class Measure {
	
	private int fitness = 0;
	
	private List<TickEvent> events = new ArrayList<>();
	
	
	public Measure() {
		super();
	}
	
	public Measure(List<TickEvent> events) {
		super();
		this.events = new ArrayList<TickEvent>(events);
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	public List<TickEvent> getTickEvents() {
		return new ArrayList<TickEvent>(this.events);
	}
	
	public void setTickEvents(List<TickEvent> events) {
		this.events = new ArrayList<TickEvent>(events);
	}
	
	public int getLength() {
		return this.events.size();
	}
	
	public void addToTrack(Track track) throws InvalidMidiDataException {
		for (int i=0; i<events.size(); i++) {
			events.get(i).addMidiEvent(track, i);
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (TickEvent event : events) {
			s.append(event.toString());
			s.append(", ");
		}
		s.delete(s.length()-2, s.length());
		return s.toString();
	}

}
