/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Interface for a Cdrdao wrapper
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 7 Sep 2013:17:05:30
 */

public interface CdrdaoWrapper {

	/**
	 * @return the command used to invoke cdrdao
	 */
	public String getCommand();

	/**
	 * @return the version of cdrdao wrapped
	 */
	public String getVersion();

	/**
	 * @return the list of CD devices detected
	 */
	public List<String> getDevices();

	/**
	 * @return the name of the default CD device 
	 */
	public String getDefaultDevice();
	
	/**
	 * @return the output of the drive-info command for the default drive
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 */
	public InputStream getDriveInfo()  throws NoCdDeviceException;
	
	/**
	 * @param deviceName the name of the device to query
	 * @return the output of the drive-info command for the named drive
	 * @throws NoCdDeviceException
	 *             device not found on the host
	 */
	public InputStream getDriveInfo(final String deviceName)  throws NoCdDeviceException;

	/**
	 * @return true if a CD is loaded into the default drive
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 */
	public boolean isDiskLoaded() throws NoCdDeviceException;

	/**
	 * @param deviceName
	 *            the name of the CD device to examine for a disk
	 * @return true if a disk is loaded, false otherwise
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 */
	public boolean isDiskLoaded(final String deviceName)
			throws NoCdDeviceException;

	/**
	 * @return the output of the disc-info command for the default drive
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 */
	public InputStream getDiscInfo()  throws NoCdDeviceException;
	
	/**
	 * @param deviceName the name of the device to query
	 * @return the output of the disc-info command for the named drive
	 * @throws NoCdDeviceException
	 *             device not found on the host
	 */
	public InputStream getDiscInfo(final String deviceName)  throws NoCdDeviceException;

	/**
	 * Read the CD Table of Contents from the CD inserted into the current
	 * drive.
	 * 
	 * @return a java.util.List of the CD TOC lines
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 * @throws NoCdException
	 *             if no CD is inserted into the default drive
	 * @throws CdrdaoException
	 *             when cdrdao returns non zero
	 */
	public InputStream readTocFromDefaultCdDevice()
			throws NoCdDeviceException, NoCdException, CdrdaoException;

	/**
	 * Read the CD Table of Contents from the CD inserted into the requested
	 * device..
	 * 
	 * @param deviceName
	 *            the name of the device to read the CD TOC from
	 * @return an InputStream to t
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 * @throws NoCdException
	 *             if no CD is inserted into the default drive
	 * @throws CdrdaoException
	 *             when cdrdao returns non zero
	 */
	public InputStream readTocFromCdDevice(final String deviceName)
			throws NoCdDeviceException, NoCdException, CdrdaoException;

	/**
	 * @param directoryForCdRip
	 * @param baseName
	 * @return a java.util.List of the CD TOC lines
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 * @throws NoCdException
	 *             if no CD is inserted into the default drive
	 * @throws CdrdaoException
	 *             when cdrdao returns non zero
	 */
	public InputStream ripCdToBinFromDefaultCdDevice(final File directoryForCdRip,
			String baseName) throws NoCdDeviceException, NoCdException, CdrdaoException;

	/**
	 * @param deviceName
	 * @param directoryForCdRip
	 * @param baseName
	 * @return a java.util.List of the CD TOC lines
	 * @throws NoCdDeviceException
	 *             if no CD device found on the host
	 * @throws NoCdException
	 *             if no CD is inserted into the default drive
	 * @throws CdrdaoException
	 *             when cdrdao returns non zero
	 */
	public InputStream ripCdToBinFromCdDevice(final String deviceName,
			final File directoryForCdRip, final String baseName)
			throws NoCdDeviceException, NoCdException, CdrdaoException;

	/**
	 * Enum to encapsulate the status codes returned from cdrdao.
	 * 
	 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl
	 *         Wilson</a>.</p> <a
	 *         href="https://github.com/carlwilson">carlwilson AT
	 *         github</a>.</p>
	 * @version 0.1
	 * 
	 *          Created 4 Oct 2013:00:50:41
	 */
	public enum StatusCode {
		/** Process failed to execute */
		NO_EXECUTION(-1000), 
		/** Everythin OK */
		OK(0),
		/** There's been a problem */
		ERROR(1);
		private final int returnCode;

		private StatusCode(final int returnCode) {
			this.returnCode = returnCode;
		}
		
		/**
		 * @return the Return Code
		 */
		public int getReturnCode() {
			return this.returnCode;
		}
	}

	/**
	 * Top level exception class for cdrdao
	 * 
	 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl
	 *         Wilson</a>.</p> <a
	 *         href="https://github.com/carlwilson">carlwilson AT
	 *         github</a>.</p>
	 * @version 0.1
	 * 
	 *          Created 4 Oct 2013:00:41:08
	 */
	public static class CdrdaoException extends Exception {
		private static final long serialVersionUID = 3113555239733574051L;
		private final StatusCode status;

		/**
		 * @see java.lang.Exception#Exception()
		 */
		public CdrdaoException() {
			super();
			this.status = StatusCode.OK;
		}

		/**
		 * @param status 
		 * @see java.lang.Exception#Exception()
		 */
		public CdrdaoException(final StatusCode status) {
			super();
			this.status = status;
		}

		/**
		 * @param status 
		 * @param message 
		 * @see java.lang.Exception#Exception(String)
		 */
		public CdrdaoException(final StatusCode status, final String message) {
			super(message);
			this.status = status;
		}
		
		/**
		 * @param status 
		 * @param cause 
		 * @see java.lang.Exception#Exception(Exception)
		 */
		public CdrdaoException(final StatusCode status, final Exception cause) {
			super(cause);
			this.status = status;
		}

		/**
		 * @param status 
		 * @param message 
		 * @param cause 
		 * @see java.lang.Exception#Exception(Exception)
		 */
		public CdrdaoException(final StatusCode status, final String message, final Exception cause) {
			super(message, cause);
			this.status = status;
		}
		
		/**
		 * @return the status code of the exception
		 */
		public StatusCode getStatus() {
			return this.status;
		}
	}

	/**
	 * Exception thrown when no cd device found
	 * 
	 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl
	 *         Wilson</a>.</p> <a
	 *         href="https://github.com/carlwilson">carlwilson AT
	 *         github</a>.</p>
	 * @version 0.1
	 * 
	 *          Created 7 Sep 2013:17:45:44
	 */
	public final static class NoCdDeviceException extends CdrdaoException {
		private static final long serialVersionUID = 2489185264229635872L;

		/**
		 * @see java.lang.Exception#Exception()
		 */
		public NoCdDeviceException() {
			super(StatusCode.ERROR);
		}

		/**
		 * @see java.lang.Exception#Exception(String)
		 */
		public NoCdDeviceException(String message) {
			super(StatusCode.ERROR, message);
		}

		/**
		 * @see java.lang.Exception#Exception(String, Throwable)
		 */
		public NoCdDeviceException(String message, Exception cause) {
			super(StatusCode.ERROR, message, cause);
		}

		/**
		 * @see java.lang.Exception#Exception(Throwable)
		 */
		public NoCdDeviceException(Exception cause) {
			super(StatusCode.ERROR, cause);
		}
	}

	/**
	 * Exception thrown when no cd found
	 * 
	 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl
	 *         Wilson</a>.</p> <a
	 *         href="https://github.com/carlwilson">carlwilson AT
	 *         github</a>.</p>
	 * @version 0.1
	 * 
	 *          Created 7 Sep 2013:17:45:44
	 */
	public final static class NoCdException extends CdrdaoException {
		private static final long serialVersionUID = 2489185264229635872L;

		/**
		 * @see java.lang.Exception#Exception()
		 */
		public NoCdException() {
			super(StatusCode.ERROR);
		}

		/**
		 * @see java.lang.Exception#Exception(String)
		 */
		public NoCdException(String message) {
			super(StatusCode.ERROR, message);
		}

		/**
		 * @see java.lang.Exception#Exception(String, Throwable)
		 */
		public NoCdException(String message, Exception cause) {
			super(StatusCode.ERROR, message, cause);
		}

		/**
		 * @see java.lang.Exception#Exception(Throwable)
		 */
		public NoCdException(Exception cause) {
			super(StatusCode.ERROR, cause);
		}
	}
}
