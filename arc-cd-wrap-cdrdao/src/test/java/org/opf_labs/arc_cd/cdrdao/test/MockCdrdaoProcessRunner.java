/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.opf_labs.arc_cd.cdrdao.AllArcCdrdaoWrapperTests;
import org.opf_labs.arc_cd.cdrdao.CdrdaoCommands.Command;
import org.opf_labs.utils.ProcessRunner;

/**
 * A mock test class that mimics a ProcessRunner, by taking cdrdao commands
 * and returning test output from test data files.  The mapping of commands
 * to test data is handled in the COMMAND_OUTPUT_MAP below.
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 6 Oct 2013:00:12:41
 */

public final class MockCdrdaoProcessRunner implements ProcessRunner {
	private static final String ENCODING = "UTF-8";
	private int returnCode = 0;
	/**	A map between commands and expected outputs for testing */
	public static final Map<String, File> COMMAND_OUTPUT_MAP = new HashMap<>();
	static {
		try {
			COMMAND_OUTPUT_MAP.put("", AllArcCdrdaoWrapperTests.getCdrdaoDefaultOutput());
			COMMAND_OUTPUT_MAP.put(Command.SCANBUS.value(), AllArcCdrdaoWrapperTests.getCdrdaoScanbusOutput());
			COMMAND_OUTPUT_MAP.put(Command.DRIVE_INFO.value(), AllArcCdrdaoWrapperTests.getCdrdaoDriveInfoOutput());
			COMMAND_OUTPUT_MAP.put(Command.DISC_INFO.value(), AllArcCdrdaoWrapperTests.getCdrdaoDiskLoadedTrueOutput());
			COMMAND_OUTPUT_MAP.put(Command.READ_TOC.value(), AllArcCdrdaoWrapperTests.getCdrdaoReadTocOutput());
		} catch (FileNotFoundException | URISyntaxException excep) {
			throw new IllegalStateException("Cant create test output set.");
		}
	}
	private final String command;
	
	MockCdrdaoProcessRunner() {
		this.command = "";
	}
	
	MockCdrdaoProcessRunner(final List<String> commands) {
		this.command = commands.get(1);
	}
	/**
	 * @see org.opf_labs.utils.ProcessRunner#setEnviroment(java.util.Map)
	 */
	@Override
	public void setEnviroment(Map<String, String> enviroment) {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#setInputStream(java.io.InputStream)
	 */
	@Override
	public void setInputStream(InputStream processInput) {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#setStartingDir(java.io.File)
	 */
	@Override
	public void setStartingDir(File startingDir) {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#setCommand(java.util.List)
	 */
	@Override
	public void setCommand(List<String> commands) {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#setTimeout(long)
	 */
	@Override
	public void setTimeout(long timeout) {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#setCollection(boolean)
	 */
	@Override
	public void setCollection(boolean collect) {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#getProcessOutput()
	 */
	@Override
	public InputStream getProcessOutput() {
		return new ByteArrayInputStream(new byte[]{});
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#getProcessError()
	 */
	@Override
	public InputStream getProcessError() {
		try {
			return new BufferedInputStream(new FileInputStream(COMMAND_OUTPUT_MAP.get(this.command)));
		} catch (IOException excep) {
			throw new AssertionError("Cannot read test data.");
		}
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#getReturnCode()
	 */
	@Override
	public int getReturnCode() {
		return this.returnCode;
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#isTimedOut()
	 */
	@Override
	public boolean isTimedOut() {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#getProcessOutputAsString()
	 */
	@Override
	public String getProcessOutputAsString() {
		// No need to implement
		throw new AssertionError("Not implemented");
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#getProcessErrorAsString()
	 */
	@Override
	public String getProcessErrorAsString() {
		try {
			return IOUtils.toString(new FileInputStream(COMMAND_OUTPUT_MAP.get(this.command)), ENCODING);
		} catch (IOException excep) {
			throw new IllegalStateException("Could not read test file into a string", excep);
		}
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunner#execute()
	 */
	@Override
	public void execute() throws ProcessRunnerException {
		// No need to implement
	}
}
