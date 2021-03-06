package org.parallelj.launching.transport.tcp;

import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;
import org.parallelj.launching.transport.tcp.command.TcpCommand;
import org.parallelj.launching.transport.tcp.command.TcpIpCommands;

/**
 * The class <code>TcpIpCommandsTest</code> contains tests for the class <code>{@link TcpIpCommands}</code>.
 *
 * @generatedBy CodePro at 09/12/11 17:14
 * @author fr22240
 * @version $Revision: 1.0 $
 */
public class TcpIpCommandsTest {
	/**
	 * Run the Map<String, TcpCommand> getCommands() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 09/12/11 17:14
	 */
	@Test
	public void testGetCommands_1()
		throws Exception {

		Map<String, TcpCommand> result = TcpIpCommands.getCommands();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.util.ServiceConfigurationError: org.parallelj.launching.transport.tcp.command.TcpCommand: Provider org.parallelj.launching.transport.tcp.command.Help could not be instantiated: java.lang.ClassCastException
		//       at java.util.ServiceLoader.fail(ServiceLoader.java:207)
		//       at java.util.ServiceLoader.access$100(ServiceLoader.java:164)
		//       at java.util.ServiceLoader$LazyIterator.next(ServiceLoader.java:353)
		//       at java.util.ServiceLoader$1.next(ServiceLoader.java:421)
		//       at org.parallelj.launching.transport.tcp.TcpIpCommands.<init>(TcpIpCommands.java:53)
		//       at org.parallelj.launching.transport.tcp.TcpIpCommands.<clinit>(TcpIpCommands.java:45)
		assertNotNull(result);
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
		new org.junit.runner.JUnitCore().run(TcpIpCommandsTest.class);
	}
}