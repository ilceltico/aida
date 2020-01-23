package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import algorithms.RandomGenerator;
import algorithms.SimpleCrossOver;
import model.Measure;

public class SimpleCrossOverTest {
	
	@Test
	public void testApply() {
		Measure m1 = RandomGenerator.getGenerator().randomMeasure(8, 10);
		Measure m2 = RandomGenerator.getGenerator().randomMeasure(8, 10);
		
		Measure r = SimpleCrossOver.getInstance().apply(m1, m2);
		assertEquals(8, r.getLength());
		
		m1 = RandomGenerator.getGenerator().randomMeasure(10, 10);
		r = SimpleCrossOver.getInstance().apply(m1, m2);
		assertEquals(8, r.getLength());
		
		r = SimpleCrossOver.getInstance().apply(m2, m1);
		assertEquals(8, r.getLength());
	}

}
