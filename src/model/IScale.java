package model;

public interface IScale {
	
	/**
	 * 
	 * @param number: input number to convert to MIDI note number (0 maps to first note)
	 * @return
	 */
	int map(int number);
	
	public class DirectMap implements IScale {
		public int map(int number) {
			return number;
		}
	}
	
	public class ScaleCMajor implements IScale {
		public int map(int number) {
			int modulo = Math.abs(number % 7);
			int base = number / 7;
			int result;
			switch(modulo) {
				case 0: result = 60; break;
				case 1: result = 62; break;
				case 2: result = 64; break;
				case 3: result = 65; break;
				case 4: result = 67; break;
				case 5: result = 69; break;
				case 6: result = 71; break;
				default: throw new IllegalArgumentException();
			}
			result = result + base * 12;
			if (result > 127 || result < 0)
				throw new IllegalArgumentException("Illegal note value");
			return result;
		}
	}
	
	public class ScalePentatonicCMajor implements IScale {
		public int map(int number) {
			int modulo = Math.abs(number % 5);
			int base = number / 5;
			int result;
			switch(modulo) {
				case 0: result = 60; break;
				case 1: result = 62; break;
				case 2: result = 64; break;
				case 3: result = 67; break;
				case 4: result = 69; break;
				default: throw new IllegalArgumentException();
			}
			result = result + base * 12;
			if (result > 127 || result < 0)
				throw new IllegalArgumentException("Illegal note value");
			return result;
		}
	}

}


