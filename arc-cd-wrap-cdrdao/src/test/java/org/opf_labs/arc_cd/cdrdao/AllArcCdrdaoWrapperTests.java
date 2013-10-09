package org.opf_labs.arc_cd.cdrdao;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opf_labs.utils.Environments;
import org.opf_labs.utils.ProcessRunner;
import org.opf_labs.utils.ProcessRunnerImplFactory;
import org.opf_labs.utils.ProcessRunner.ProcessRunnerException;

import com.google.common.base.Preconditions;

/**
 * Test Suite for cdrdao wrapper
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 4 Oct 2013:16:50:18
 */
@RunWith(Suite.class)
@SuiteClasses({ CdrdaoCommandsTest.class, CdrdaoExecutableTest.class,
		CdrdaoCliWrapperFactoryTest.class, CdrdaoCliWrapperTest.class, CapacityTest.class })
public class AllArcCdrdaoWrapperTests {
	/**
	 * Given a string resource path and name returns a File object. Used to load
	 * test data, not meant to be a general utility method.
	 * 
	 * @param resName
	 *            the name of the resource to retrieve a file for
	 * @return the java.io.File for the named resource
	 * @throws URISyntaxException
	 *             if the named resource can't be converted to a URI
	 * @throws FileNotFoundException
	 *             if the resource can't be found
	 */
	final static File getResourceAsFile(String resName)
			throws URISyntaxException, FileNotFoundException {
		Preconditions.checkNotNull(resName, "resName == null");
		Preconditions.checkArgument(!resName.isEmpty(),
				"resname.isEmpty() ==  true");
		URL resourceLocation = ClassLoader.getSystemResource(resName);
		if (resourceLocation == null)
			throw new FileNotFoundException("resName could not be found.");
		return new File(resourceLocation.toURI());
	}

	final static boolean testForInstalledCdrdao() throws FileNotFoundException,
			URISyntaxException, ProcessRunnerException {
		if (!Environments.isUnix()) {
			return false;
		}
		File shellTest = getTestForInstalledCdrdaoScript();
		if (!Cdrdao.isFileExecutable(shellTest)) {
			fail("Either can't find or execute test script file:"
					+ shellTest.getAbsolutePath());
		}
		List<String> commands = Arrays.asList(new String[] { shellTest
				.getAbsolutePath() });
		ProcessRunner testScriptRunner = ProcessRunnerImplFactory.getInstance()
				.createProcessRunner(commands);
		testScriptRunner.execute();
		return (testScriptRunner.getReturnCode() == 0);
	}

	/**
	 * @return File object for the test for cdrdao test script
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getTestForInstalledCdrdaoScript()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/scripts/test-for-cdrdao.sh");
	}

	/**
	 * @return File object for the test for cdrdao default output
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getCdrdaoDefaultOutput()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/cdrdaoOut/default.txt");
	}

	/**
	 * @return File object for the test for cdrdao default output
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getCdrdaoScanbusOutput()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/cdrdaoOut/scanbus.txt");
	}

	/**
	 * @return File object for the test for cdrdao default output
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getCdrdaoDriveInfoOutput()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/cdrdaoOut/drive-info.txt");
	}

	/**
	 * @return File object for the test for cdrdao disk-info output when a disk is loaded
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getCdrdaoDiskLoadedTrueOutput()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/cdrdaoOut/disk-loaded-true.txt");
	}

	/**
	 * @return File object for the test for cdrdao disk-info output when a disk is NOT loaded
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getCdrdaoDiskLoadedFalseOutput()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/cdrdaoOut/disk-loaded-false.txt");
	}

	/**
	 * @return File object for the test for cdrdao read-toc output when a disk is NOT loaded
	 * @throws FileNotFoundException
	 *             if the test script file not found
	 * @throws URISyntaxException
	 *             shouldn't happen as file name is hard coded and good
	 */
	public final static File getCdrdaoReadTocOutput()
			throws FileNotFoundException, URISyntaxException {
		return AllArcCdrdaoWrapperTests
				.getResourceAsFile("org/opf_labs/arc_cd/cdrdaoOut/read-toc.txt");
	}

	static String detectCdrdaoVersion() {
		ProcessRunner cdrdaohelpRunner = ProcessRunnerImplFactory.getInstance()
				.createProcessRunner("cdrdao");
		try {
			cdrdaohelpRunner.execute();
			BufferedReader stderrReader = new BufferedReader(new InputStreamReader(
					cdrdaohelpRunner.getProcessError()));
			// The first line should give us the version
			String versionLine = stderrReader.readLine();
			return parseCdrdaoVersionLine(versionLine);
		} catch (ProcessRunnerException | IOException excep) {
			// So no installation detected, return UNKNOWN
			return "UNKNOWN";
		}
	}

	static String parseCdrdaoVersionLine(String line) {
		String version = "";
		if (line.startsWith("Cdrdao version ")) {
			return line.split(" ")[2];
		}
		return version;
	}
}
