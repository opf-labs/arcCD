/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.opf_labs.utils.ProcessRunner;
import org.opf_labs.utils.ProcessRunner.ProcessRunnerException;
import org.opf_labs.utils.ProcessRunnerFactory;
import org.opf_labs.utils.ProcessRunnerImplFactory;

import com.google.common.base.Preconditions;

/**
 * So this class wraps the
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 30 Aug 2013:08:00:45
 */

public final class CdrdaoCliWrapper implements CdrdaoWrapper {
	private final static Logger LOGGER = Logger
			.getLogger(CdrdaoCliWrapper.class);

	private final ProcessRunnerFactory factory;
	private final CdrdaoExecutable cdrdao;
	private final CdrdaoCommands commandFactory;

	CdrdaoCliWrapper(final CdrdaoExecutable cdrdao) {
		this(cdrdao, CdrdaoCommands.getParanoidInstance(cdrdao.getCommand()),
				ProcessRunnerImplFactory.getInstance());
	}

	CdrdaoCliWrapper(final CdrdaoExecutable cdrdao,
			final CdrdaoCommands commandFactory,
			final ProcessRunnerFactory factory) {
		this.cdrdao = cdrdao;
		this.commandFactory = commandFactory;
		this.factory = factory;
	}

	@Override
	public String getCommand() {
		return this.cdrdao.getCommand();
	}

	@Override
	public String getVersion() {
		return this.cdrdao.getVersion();
	}

	@Override
	public List<String> getDevices() {
		return this.cdrdao.getDevices();
	}

	@Override
	public String getDefaultDevice() {
		return this.cdrdao.getDefaultDevice();
	}

	@Override
	public InputStream getDriveInfo() throws NoCdDeviceException {
		return this.getDriveInfo(this.getDefaultDevice());
	}

	@Override
	public InputStream getDriveInfo(String device) throws NoCdDeviceException {
		Preconditions.checkNotNull(device, "deviceName == null");
		Preconditions.checkArgument(!device.isEmpty(), "deviceName.isEmpty()");
		this.exceptionIfNotInDevices(device);
		try {
			return executeCommandAndGetErrorStream(this.factory
					.createProcessRunner(this.commandFactory.driveInfo(device)));
		} catch (CdrdaoException excep) {
			throw new NoCdDeviceException(
					"Problem obtaining cd device information", excep);
		}
	}

	@Override
	public boolean isDiskLoaded() throws NoCdDeviceException {
		return this.isDiskLoaded(this.getDefaultDevice());
	}

	@Override
	public boolean isDiskLoaded(final String device) throws NoCdDeviceException {
		Preconditions.checkNotNull(device, "deviceName == null");
		Preconditions.checkArgument(!device.isEmpty(), "deviceName.isEmpty()");
		return checkIfDiskLoaded(device);
	}

	@Override
	public InputStream getDiscInfo() throws NoCdDeviceException {
		return this.getDiscInfo(this.getDefaultDevice());
	}

	@Override
	public InputStream getDiscInfo(String deviceName)
			throws NoCdDeviceException {
		throw new AssertionError("Not implemented");
	}

	private boolean checkIfDiskLoaded(final String device)
			throws NoCdDeviceException {
		this.exceptionIfNotInDevices(device);
		Cdrdao.unmountCD();
		ProcessRunner runner = this.factory
				.createProcessRunner(this.commandFactory.diskInfo(device));
		try (InputStream processOutput = executeCommandAndGetErrorStream(runner);
				BufferedReader err = new BufferedReader(new InputStreamReader(
						processOutput))) {
			// Get first line, if it's not the intro line forget it
			String line;
			while ((line = err.readLine()) != null) {
				Matcher diskFoundMatch = Cdrdao.DISK_FOUND_PATTERN
						.matcher(line);
				if (diskFoundMatch.matches()) {
					IOUtils.closeQuietly(err);
					return true;
				}
			}
		} catch (IOException | CdrdaoException excep) {
			throw new NoCdDeviceException(excep);
		}

		// Check the process runner for problems
		return false;
	}

	private void exceptionIfNotInDevices(final String device)
			throws NoCdDeviceException {
		if (!this.getDevices().contains(device)) {
			throw new NoCdDeviceException("Device not found:" + device);
		}
	}

	@Override
	public InputStream readTocFromDefaultCdDevice() throws NoCdDeviceException,
			NoCdException, CdrdaoException {
		return readTocFromCdDevice(this.getDefaultDevice());
	}

	@Override
	public InputStream readTocFromCdDevice(final String deviceName)
			throws NoCdDeviceException, NoCdException, CdrdaoException {
		Preconditions.checkNotNull(deviceName, "deviceName == null");
		Preconditions.checkArgument(!deviceName.isEmpty(),
				"deviceName.isEmpty()");
		Cdrdao.unmountCD();
		Path tempPath = createTempDirOrTerminate();
		String tocPath = tempPath.toString() + File.separatorChar + "temp.toc";
		List<String> commands = this.commandFactory
				.readToc(deviceName, tocPath);
		ProcessRunner runner = this.factory.createProcessRunner(commands);
		try {
			runner.execute();
		} catch (ProcessRunnerException excep) {
			throw new CdrdaoException(StatusCode.NO_EXECUTION, excep);
		}

		// Check the process runner for problems
		int retCode = runner.getReturnCode();
		String[] lines = runner.getProcessErrorAsString().split("\n");
		for (String line : lines) {
			System.out.println(line);
		}
		if (retCode != 0) {
			throw new CdrdaoException(StatusCode.ERROR,
					"Error reading TOC from device:" + deviceName);
		}
		// Get the process output
		try {
			return new FileInputStream(tocPath);
		} catch (FileNotFoundException excep) {
			throw new CdrdaoException(StatusCode.NO_EXECUTION, excep);
		}
	}

	@Override
	public InputStream ripCdToBinFromDefaultCdDevice(File directoryForCdRip,
			String baseName) throws NoCdDeviceException, NoCdException,
			CdrdaoException {
		return ripCdToBinFromCdDevice(this.getDefaultDevice(),
				directoryForCdRip, baseName);
	}

	@Override
	public InputStream ripCdToBinFromCdDevice(final String deviceName,
			final File directoryForCdRip, final String baseName)
			throws NoCdDeviceException, NoCdException, CdrdaoException {
		Preconditions.checkNotNull(deviceName, "deviceName == null");
		Preconditions.checkNotNull(directoryForCdRip,
				"directoryForCdRip == null");
		Preconditions.checkNotNull(baseName, "baseName == null");
		Preconditions.checkArgument(!deviceName.isEmpty(),
				"deviceName.isEmpty()");
		Preconditions.checkArgument(!baseName.isEmpty(), "baseName.isEmpty()");
		Cdrdao.unmountCD();
		String binPath = directoryForCdRip.getAbsolutePath() + File.separator
				+ baseName + ".bin";
		String tocPath = directoryForCdRip.getAbsolutePath() + File.separator
				+ baseName + ".toc";

		// Get the process output
		return executeCommandAndGetErrorStream(this.factory
				.createProcessRunner(this.commandFactory.readCd(deviceName,
						tocPath, binPath)));
	}

	static InputStream executeCommandAndGetErrorStream(
			final ProcessRunner runner) throws CdrdaoException {
		try {
			runner.execute();
		} catch (ProcessRunnerException excep) {
			logWrapAndThrowException(StatusCode.NO_EXECUTION, excep);
		}

		// Check the process runner for problems
		if (runner.getReturnCode() != 0) {
			logWrapAndThrowException(StatusCode.ERROR,
					new Exception("Error executing cdrdao, return code:"
							+ runner.getReturnCode()));
		}
		IOUtils.closeQuietly(runner.getProcessOutput());
		return runner.getProcessError();
	}

	private static Path createTempDirOrTerminate() {
		try {
			return Files.createTempDirectory("cdrdao");
		} catch (IOException excep) {
			LOGGER.fatal("IOException thrown creating temporary directory.");
			LOGGER.fatal(excep.getMessage());
			LOGGER.fatal(excep.getStackTrace());
			throw new IllegalStateException(
					"IOException thrown creating temporary directory.", excep);
		}
	}

	private static void logWrapAndThrowException(final StatusCode status,
			final Exception cause) throws CdrdaoException {
		LOGGER.error("I/O Error running command process.");
		LOGGER.error(cause.getMessage());
		LOGGER.error(cause.getStackTrace());
		throw new CdrdaoException(status, cause);
	}
}
