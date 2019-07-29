package unittests.Primitives;

import static org.junit.Assert.*;
import org.junit.Test;

import primitives.*;

/**
 * test for vector
 */
public class VectorTest {
	/**
	 * vector for test
	 */
	Vector v1 = new Vector(3.5, -5, 10);
	Vector v2 = new Vector(1, 1, 1);
	Vector v3 = new Vector(-1, -1, -1);

	/**
	 * check the add func
	 */
	@Test
	public void testAdd() {
		/*if(v2.add(v3).length()==0)
            assertTrue(true);
        else 
            fail("the test fail:");
		*/
		assertEquals(v1.add(v2), new Vector(4.5, -4, 11));
	}

	/**
	 * check the add func
	 */
	@Test
	public void testsSub() {
		assertEquals(v1.sub(v2), new Vector(2.5, -6, 9));
	}

	/**
	 * check if the normalize is correct
	 */
	@Test
	public void testNormalize() {
		v1 = v1.normal();
		assertEquals("", 1, v1.lenght(), 1e-10);
	}

	/**
	 * check the scalarMult func
	 */
	@Test
	public void testScalarMult() {
		assertEquals(v1.scalarMult(2), new Vector(7, -10, 20));
	}

	/**
	 * check the dotProduct func
	 */
	@Test
	public void testDotProduct() {
		
		/**
		 * test for regular vector
		 */
		assertEquals("", v1.dotProduct(v2), 8.5, 1e-10);
		
		/**
		 * boundary test
		 */
		assertEquals("", new Vector(1, 0, 0).dotProduct(new Vector(0, 0, 1)), 0, 1e-10);
		assertEquals("", new Vector(0, 1, 0).dotProduct(new Vector(0, 0, 1)), 0, 1e-10);
		assertEquals("", new Vector(1, 0, 0).dotProduct(new Vector(0, 1, 0)), 0, 1e-10);		
		assertEquals("", new Vector(-1, 0, 0).dotProduct(new Vector(0, 0, 1)), 0, 1e-10);
		assertEquals("", new Vector(0, -1, 0).dotProduct(new Vector(0, 0, 1)), 0, 1e-10);
		assertEquals("", new Vector(-1, 0, 0).dotProduct(new Vector(0, 1, 0)), 0, 1e-10);
		
		System.out.print(new Vector(1, 2, 3).dotProduct(new Vector(1, 1, -1)));
	}

	/**
	 * check the CrossProduct func
	 */
	@Test
	public void testCrossProduct() {
		/**
		 * test for regular vector
		 */
		assertEquals(v1.crossProduct(v2), new Vector(-15.0, 6.5, 8.5));
	
		/**
		 * boundary test
		 */
		try {
			new Vector(0, 0, -1).crossProduct(new Vector(0, 0, 1));
			fail("The above should have thrown an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	/**
	 * check the length func
	 */
	@Test
	public void testLength() {
		assertEquals("", v2.lenght(), Math.sqrt(3), 1e-10);
	}

}
