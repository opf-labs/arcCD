/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.CdrdaoCommands.Command;
import org.opf_labs.arc_cd.cdrdao.CdrdaoCommands.Option;
import org.opf_labs.arc_cd.cdrdao.CdrdaoCommands.ParanoiaModeOption;

/**
 * Test fixture for the CdrdaoCommands class.
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 7 Oct 2013:11:43:11
 */
public class CdrdaoCommandsTest {
	private static final String EMPTY_STRING = "";
	private static final String COMMAND = "cdrdao";
	private static final String DEVICE = "/dev/sr0";
	private static final String TOC_PATH = "/path/example.toc";
	private static final String BIN_PATH = "/path/example.bin";
	
	/**
	 * Test method for creating a new instance.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetNewInstanceNullCommand() {
		CdrdaoCommands.getNewInstance(null, ParanoiaModeOption.NO_CHECKING);
	}

	/**
	 * Test method for creating a new instance.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetNewInstanceEmptyCommand() {
		CdrdaoCommands.getNewInstance(EMPTY_STRING, ParanoiaModeOption.NO_CHECKING);
	}

	/**
	 * Test method for creating a new instance.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetNewInstanceNullParanoia() {
		CdrdaoCommands.getNewInstance(COMMAND, null);
	}

	/**
	 * Test method for creating a new instance.
	 */
	@Test
	public void testGetNewInstanceParanoiaNoChecking() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.NO_CHECKING);
		assertTrue(testInstance.paranoiaMode == ParanoiaModeOption.NO_CHECKING);
	}

	/**
	 * Test method for creating a new instance.
	 */
	@Test
	public void testGetNewInstanceParanoiaAudio() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.AUDIO_DATA_CHECKS);
		assertTrue(testInstance.paranoiaMode == ParanoiaModeOption.AUDIO_DATA_CHECKS);
	}

	/**
	 * Test method for creating a new instance.
	 */
	@Test
	public void testGetNewInstanceParanoiaOverlappedReading() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.OVERLAPPED_READING);
		assertTrue(testInstance.paranoiaMode == ParanoiaModeOption.OVERLAPPED_READING);
	}

	/**
	 * Test method for creating a new instance.
	 */
	@Test
	public void testGetNewInstanceParanoiaScratchRepair() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.SCRATCH_REPAIR);
		assertTrue(testInstance.paranoiaMode == ParanoiaModeOption.SCRATCH_REPAIR);
	}

	/**
	 * Test method for creating a paranoid instance.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetParanoidInstanceNullCommand() {
		CdrdaoCommands.getParanoidInstance(null);
	}

	/**
	 * Test method for creating a paranoid instance.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetParanoidInstanceEmptyCommand() {
		CdrdaoCommands.getParanoidInstance(EMPTY_STRING);
	}

	/**
	 * Test method for creating a paranoid instance.
	 */
	@Test
	public void testGetParanoidInstance() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		assertTrue(testInstance.paranoiaMode == ParanoiaModeOption.SCRATCH_REPAIR);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#scanbus()}.
	 */
	@Test
	public void testScanbus() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		List<String> scanBusCommandList = testInstance.scanbus();
		assertTrue(scanBusCommandList.contains(COMMAND));
		assertTrue(scanBusCommandList.contains(Command.SCANBUS.value()));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#driveInfo(java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testDriveInfoNullDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.driveInfo(null);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#driveInfo(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDriveInfoEmptyDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.driveInfo(EMPTY_STRING);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#driveInfo(java.lang.String)}.
	 */
	@Test
	public void testDriveInfo() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		List<String> driveInfoCommandList = testInstance.driveInfo(DEVICE);
		checkDefaultOptions(driveInfoCommandList, Command.DRIVE_INFO);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#diskInfo(java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testDiskInfoNullDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.diskInfo(null);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#diskInfo(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDiskInfoEmptyDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.diskInfo(EMPTY_STRING);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#diskInfo(java.lang.String)}.
	 */
	@Test
	public void testDiskInfo() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		List<String> discInfoCommandList = testInstance.diskInfo(DEVICE);
		checkDefaultOptions(discInfoCommandList, Command.DISC_INFO);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readToc(java.lang.String, java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReadTocNullDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readToc(null, TOC_PATH);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readToc(java.lang.String, java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReadTocNullTocPath() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readToc(DEVICE, null);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readToc(java.lang.String, java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReadTocEmptyDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readToc(EMPTY_STRING, TOC_PATH);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readToc(java.lang.String, java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReadTocEmptyTocPath() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readToc(DEVICE, EMPTY_STRING);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readToc(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadToc() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		List<String> readTocCommandList = testInstance.readToc(DEVICE, TOC_PATH);
		checkDefaultOptions(readTocCommandList, Command.READ_TOC);
		assertTrue(readTocCommandList.contains(Option.READ_RAW_OPTION.getOption()));
		assertTrue(readTocCommandList.contains(Option.FAST_TOC_OPTION.getOption()));
		assertTrue(readTocCommandList.contains(TOC_PATH));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReadCdNullDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readCd(null, TOC_PATH, BIN_PATH);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReadCdNullTocPath() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readCd(DEVICE, null, BIN_PATH);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReadCdNullBinPath() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readCd(DEVICE, TOC_PATH, null);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReadCdEmptyDevice() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readCd(EMPTY_STRING, TOC_PATH, BIN_PATH);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReadCdEmptyTocPath() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readCd(DEVICE, EMPTY_STRING, BIN_PATH);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReadCdEmptyBinPath() {
		CdrdaoCommands testInstance = CdrdaoCommands.getParanoidInstance(COMMAND);
		testInstance.readCd(DEVICE, TOC_PATH, EMPTY_STRING);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadCdNoChecking() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.NO_CHECKING);
		List<String> readTocCommandList = testInstance.readCd(DEVICE, TOC_PATH, BIN_PATH);
		checkReadCdOptions(readTocCommandList, ParanoiaModeOption.NO_CHECKING);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadCdOverlappedReading() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.OVERLAPPED_READING);
		List<String> readTocCommandList = testInstance.readCd(DEVICE, TOC_PATH, BIN_PATH);
		checkReadCdOptions(readTocCommandList, ParanoiaModeOption.OVERLAPPED_READING);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadCdAudioDataChecks() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.AUDIO_DATA_CHECKS);
		List<String> readTocCommandList = testInstance.readCd(DEVICE, TOC_PATH, BIN_PATH);
		checkReadCdOptions(readTocCommandList, ParanoiaModeOption.AUDIO_DATA_CHECKS);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCommands#readCd(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadCdScratchRepair() {
		CdrdaoCommands testInstance = CdrdaoCommands.getNewInstance(COMMAND, ParanoiaModeOption.SCRATCH_REPAIR);
		List<String> readTocCommandList = testInstance.readCd(DEVICE, TOC_PATH, BIN_PATH);
		checkReadCdOptions(readTocCommandList, ParanoiaModeOption.SCRATCH_REPAIR);
	}
	
	private static void checkReadCdOptions(final List<String> optionList, final ParanoiaModeOption paranoiaOption) {
		checkDefaultOptions(optionList, Command.READ_CD);
		assertTrue(optionList.contains(Option.READ_RAW_OPTION.getOption()));
		assertTrue(optionList.contains(CdrdaoCommands.ParanoiaModeOption.PARANOIA_MODE_OPTION));
		assertTrue(optionList.contains(paranoiaOption.getModeValueAsString()));
		assertTrue(optionList.contains(TOC_PATH));
		assertTrue(optionList.contains(BIN_PATH));
	}
	
	private static void checkDefaultOptions(final List<String> optionList, final Command subcommand) {
		assertTrue(optionList.contains(COMMAND));
		assertTrue(optionList.contains(subcommand.value()));
		assertTrue(optionList.contains(Option.DEVICE.getOption()));
		assertTrue(optionList.contains(DEVICE));
	}
}
