package model;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public interface TickEvent {

	public abstract void addMidiEvent(Track track, int tick, IScale scale) throws InvalidMidiDataException;
	
	
	public class NoteOnEvent extends NoteOffEvent {
		
		byte note;
		
		public NoteOnEvent(byte note) {
			this.note = note;
		}
		
		public NoteOnEvent(int note) {
			this.note = (byte) note;
		}
		
		public byte getNote() {
			return this.note;
		}
		
		@Override
		public String toString() {
			return "ON_"+note;
		}

		@Override
		public void addMidiEvent(Track track, int tick, IScale scale) throws InvalidMidiDataException {
			//First Note off
			super.addMidiEvent(track, tick, scale);
			
			// Then note on
			track.add(new MidiEvent(new ShortMessage(144, 1, scale.map(this.getNote()), 100), tick));
			
				
		}
	}
	
	
	public class NoteOffEvent implements TickEvent {
		
		public NoteOffEvent() {
		}
		
		@Override
		public String toString() {
			return "OFF";
		}

		@Override
		public void addMidiEvent(Track track, int tick, IScale scale) throws InvalidMidiDataException {
			if (track == null)
				throw new IllegalArgumentException("Track can't be null.");
			if (track.size() != 0) {
				// Note off for the last note added (if any)
				MidiMessage m;
				int i = track.size()-1;
				while (i >= 0) {
					m = track.get(i).getMessage();
					if (m instanceof ShortMessage) {
						int noteToStop = ((ShortMessage) m).getData1();
						int code = ((ShortMessage) m).getCommand();
						if (code == 144) // NoteOn
							track.add(new MidiEvent(new ShortMessage(128, 1, noteToStop, 100), tick));
						break;
					}
					i--;
				}
			}
		}
		
	}
	
	
	public class HoldEvent implements TickEvent {
		
		public HoldEvent() {
		}
		
		@Override
		public String toString() {
			return "HOLD";
		}

		@Override
		public void addMidiEvent(Track track, int tick, IScale scale) {
		}
		
	}

}
