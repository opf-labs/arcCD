/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opf_labs.utils.Environments;
import org.opf_labs.utils.ProcessRunner;
import org.opf_labs.utils.ProcessRunner.ProcessRunnerException;
import org.opf_labs.utils.ProcessRunnerImplFactory;

/**
 * Package class to hold constants and utility methods.
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 6 Oct 2013:22:20:03
 */

public final class Cdrdao {
	/**	The default command to invoke an installed version of cdrdao */
	public static final String CDRDAO_COMMAND = "cdrdao";
	final static String CDRDAO_ERROR = "ERROR:";
	/** Default version number for undetected cdrdao */
	public final static String DEFAULT_VERSION = "UNKNOWN";
	final static Pattern DISK_FOUND_PATTERN = Pattern.compile("^That data below may not reflect.*$");
	final static Pattern UNIT_NOT_READY_ERROR_PATTERN = Pattern.compile("^"
	+ CDRDAO_ERROR + "[\\s]?Unit not ready.*$");
	final static Pattern VERSION_PATTERN = Pattern.compile(
	".*([\\d]+.[\\d]+.[\\d]+).*", Pattern.DOTALL);
	private Cdrdao() {
		throw new AssertionError("Should never enter class cosntructor");
	}

	static boolean checkIfDefaultCdrdaoOutput(final String output) {
		return VERSION_PATTERN.matcher(output).matches();
	}
	
	static String getCdrdaoDefaultOutput(final String command) {
		ProcessRunner runner = getProcessRunner(new String[] { command });
		try {
			runner.execute();
		} catch (ProcessRunnerException excep) {
			return "";
		}
		return runner.getProcessErrorAsString();
	}

	private static ProcessRunner getProcessRunner(final String[] commands) {
		return ProcessRunnerImplFactory.getInstance().createProcessRunner(
				commands);
	}
	static boolean isFileExecutable(File file) {
		if (!file.isFile()) {
			return false;
		}
		if (!file.canExecute()) {
			return file.setExecutable(true);
		}
		return true;
	}
	static String parseCdrdaoVersion(final String output) {
		Matcher matcher = VERSION_PATTERN.matcher(output);
		if (!matcher.matches()) {
			return DEFAULT_VERSION;
		}
	
		return matcher.group(1);
	}

	static void unmountCD() {
		if (!Environments.isMac())
			return;
		String[] commands = { "sudo", "umount", "/Volumes/Audio CD" };
		ProcessRunner runner = getProcessRunner(commands);
		runner.setCollection(false);
		try {
			runner.execute();
		} catch (ProcessRunnerException excep) {
			// Fail silently
		}
	}
}
