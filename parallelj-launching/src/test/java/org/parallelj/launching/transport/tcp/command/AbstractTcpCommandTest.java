package org.parallelj.launching.transport.tcp.command;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>AbstractTcpCommandTest</code> contains tests for the class <code>{@link AbstractTcpCommand}</code>.
 *
 * @generatedBy CodePro at 09/12/11 17:14
 * @author fr22240
 * @version $Revision: 1.0 $
 */
public class AbstractTcpCommandTest {
	/**
	 * Run the int compareTo(TcpCommand) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testCompareTo_1()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		TcpCommand o = new AsyncLaunch();

		int result = fixture.compareTo(o);

		// add additional test code here
		assertEquals(0, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testEquals_1()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		Object obj = "";

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testEquals_2()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		Object obj = "asynclaunch";

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testEquals_3()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		Object obj = new Object();

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testEquals_5()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		Object obj = new AsyncLaunch();

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testEquals_6()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		Object obj = new AsyncLaunch();

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testEquals_7()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();
		Object obj = new Object();

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the int hashCode() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testHashCode_1()
		throws Exception {
		AbstractTcpCommand fixture = new AsyncLaunch();

		int result = fixture.hashCode();

		// add additional test code here
		assertEquals(295786799, result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(AbstractTcpCommandTest.class);
	}
}