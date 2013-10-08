/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.opf_labs.arc_cd.cdrdao.CdrdaoWrapper.CdrdaoException;
import org.opf_labs.arc_cd.cdrdao.CdrdaoWrapper.StatusCode;
import org.opf_labs.arc_cd.cdrdao.test.MockCdrdaoProcessRunner;
import org.opf_labs.utils.ProcessRunner;
import org.opf_labs.utils.ProcessRunner.ProcessRunnerException;
import org.opf_labs.utils.ProcessRunnerFactory;
import org.opf_labs.utils.ProcessRunnerImplFactory;

import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc for CdrdaoWrapperFactory.</p> TODO Tests for
 * CdrdaoWrapperFactory.</p> TODO Implementation for CdrdaoWrapperFactory.</p>
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 5 Oct 2013:21:04:13
 */

public final class CdrdaoCliWrapperFactory {
	private final static Pattern DEVICE_PATTERN = Pattern.compile("^(.*)[\\s]?:.*$");

	private final static Logger LOGGER = Logger
			.getLogger(CdrdaoCliWrapperFactory.class);

	private final static Map<String, CdrdaoExecutable> VERIFIED_EXECUTABLES = Collections
			.synchronizedMap(new HashMap<String, CdrdaoExecutable>());
	private final static boolean CDRDAO_INSTALLED;
	private final static CdrdaoExecutable INSTALLED_CDRDAO;
	static {
		BasicConfigurator.configure();
		String defaultOutput = Cdrdao
				.getCdrdaoDefaultOutput(Cdrdao.CDRDAO_COMMAND);
		CDRDAO_INSTALLED = Cdrdao.checkIfDefaultCdrdaoOutput(defaultOutput);
		if (CDRDAO_INSTALLED) {
			String version = Cdrdao.parseCdrdaoVersion(defaultOutput);
			List<String> devices = scanForDevices(Cdrdao.CDRDAO_COMMAND,
					ProcessRunnerImplFactory.getInstance());
			INSTALLED_CDRDAO = CdrdaoExecutable.getNewInstance(
					Cdrdao.CDRDAO_COMMAND, version, devices);
			LOGGER.trace("Installed version:" + version);
		} else {
			INSTALLED_CDRDAO = CdrdaoExecutable.DEFAULT;
		}
	}

	/**
	 * @return the installed cdrdao insance
	 * @throws CdrdaoException
	 *             if cdrdao not installed
	 */
	public static CdrdaoCliWrapper getInstalledInstance()
			throws CdrdaoException {
		if (!isCdrdaoInstalled())
			throw new CdrdaoException(StatusCode.ERROR, "");
		return new CdrdaoCliWrapper(INSTALLED_CDRDAO);
	}

	/**
	 * @param path
	 *            the string path to the cdrdao executable
	 * @return a new wrapper instance
	 * @throws CdrdaoException
	 *             if path doesn't point to a cdrdao executable
	 */
	public static CdrdaoWrapper createInstanceFromExecutablePath(
			final String path) throws CdrdaoException {
		Preconditions.checkNotNull(path, "path == null");
		Preconditions.checkArgument(!path.isEmpty(), "path.isEmpty()");
		return createInstanceFromExecutableFile(new File(path));
	}

	/**
	 * @param executable
	 *            the cdrdao executable
	 * @return a new wrapper instance
	 * @throws CdrdaoException
	 *             if file is not a cdrdao executable
	 */
	public static CdrdaoWrapper createInstanceFromExecutableFile(
			final File executable) throws CdrdaoException {
		Preconditions.checkNotNull(executable, "executable == null");
		String canonicalPath;
		try {
			canonicalPath = executable.getCanonicalPath();
		} catch (IOException excep) {
			throw new CdrdaoException(StatusCode.ERROR,
					"Could not parse canonical path for exe:"
							+ executable.getAbsolutePath());
		}
		if (VERIFIED_EXECUTABLES.containsKey(canonicalPath)) {
			return new CdrdaoCliWrapper(VERIFIED_EXECUTABLES.get(canonicalPath));
		}
		String out = verifyCdrdaoExeAndReturnOutput(executable);
		String version = Cdrdao.parseCdrdaoVersion(out);
		List<String> devices = scanForDevices(executable.getAbsolutePath(),
				ProcessRunnerImplFactory.getInstance());
		CdrdaoExecutable cdrdao = CdrdaoExecutable.getNewInstance(
				canonicalPath, version, devices);
		VERIFIED_EXECUTABLES.put(canonicalPath, cdrdao);
		return new CdrdaoCliWrapper(cdrdao);
	}

	/**
	 * @return true if an installed version of cdrdao detected
	 */
	public static boolean isCdrdaoInstalled() {
		return CDRDAO_INSTALLED;
	}

	/**
	 * @return the installed version identifer as a string
	 */
	public static String getInstalledVersion() {
		return INSTALLED_CDRDAO.getVersion();
	}

	static CdrdaoCliWrapper createTestableInstance(final ProcessRunnerFactory testMock) {
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				MockCdrdaoProcessRunner.COMMAND_OUTPUT_MAP.get("scanbus"))))) {
			reader.mark(1000);
			String version = Cdrdao.parseCdrdaoVersion(IOUtils.toString(reader));
			reader.reset();
			List<String> devices = parseCdDeviceNames(reader);
			IOUtils.closeQuietly(reader);
			CdrdaoExecutable cdrdao = CdrdaoExecutable.getNewInstance(
					Cdrdao.CDRDAO_COMMAND, version, devices);
			return new CdrdaoCliWrapper(cdrdao,
					CdrdaoCommands.getParanoidInstance(cdrdao.getCommand()),
					testMock);
		} catch (IOException excep) {
			throw new IllegalStateException("Couldn't read in scanbus test data.", excep);
		}
	}

	private static List<String> scanForDevices(final String cdrdaoCommand,
			ProcessRunnerFactory factory) {
		List<String> deviceList = Collections
				.unmodifiableList(new ArrayList<String>());
		ProcessRunner runner = factory.createProcessRunner(CdrdaoCommands
				.scanbusCommand(cdrdaoCommand));
		try {
			runner.execute();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					runner.getProcessError()));
			deviceList = parseCdDeviceNames(reader);
		} catch (ProcessRunnerException excep) {
			// return the empty list
		}
		return deviceList;
	}

	private static String verifyCdrdaoExeAndReturnOutput(final File executable)
			throws CdrdaoException {
		if (!Cdrdao.isFileExecutable(executable)) {
			throw new CdrdaoException(StatusCode.ERROR,
					"Cdrdao executable must be an existing executable file.");
		}
		String out = Cdrdao
				.getCdrdaoDefaultOutput(executable.getAbsolutePath());
		if (!Cdrdao.checkIfDefaultCdrdaoOutput(out)) {
			throw new CdrdaoException(StatusCode.ERROR,
					"Cdrdao executable does not apper to be cdrdao.");
		}
		return out;
	}

	private static List<String> parseCdDeviceNames(
			final BufferedReader cdrdaoScanbusReader) {
		List<String> deviceList = new ArrayList<>();
		String line;
		try {
			while ((line = cdrdaoScanbusReader.readLine()) != null) {
				Matcher devMatch = DEVICE_PATTERN.matcher(line);
				if (devMatch.matches()){
					deviceList.add(devMatch.group(1).trim());
				}
			}
		} catch (IOException excep) {
			// return the list as is
		}
		return Collections.unmodifiableList(deviceList);
	}
}
