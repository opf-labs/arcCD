/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.CdrdaoWrapper.CdrdaoException;

/**
 * TODO JavaDoc for CdrdaoCliWrapperFactoryTest.</p> TODO Tests for
 * CdrdaoCliWrapperFactoryTest.</p> TODO Implementation for
 * CdrdaoCliWrapperFactoryTest.</p>
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 5 Oct 2013:22:20:27
 */
@SuppressWarnings("static-method")
public class CdrdaoCliWrapperFactoryTest {
	private static final String EMPTY_STRING = "";
	private static boolean CDRDAO_INSTALLED = false;
	private static String CDRDAO_VERSION = EMPTY_STRING;
	private static File BAD_EXE;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BAD_EXE = AllArcCdrdaoWrapperTests.getTestForInstalledCdrdaoScript();
		CDRDAO_INSTALLED = AllArcCdrdaoWrapperTests.testForInstalledCdrdao();
		CDRDAO_VERSION = AllArcCdrdaoWrapperTests.detectCdrdaoVersion();
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#isCdrdaoInstalled()}
	 * .
	 */
	@Test
	public void testIsCdrdaoInstalled() {
		assertTrue(
				"SelfTest:" + CDRDAO_INSTALLED,
				(CdrdaoCliWrapperFactory.isCdrdaoInstalled() == CDRDAO_INSTALLED));
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#getInstalledVersion()}
	 * .
	 */
	@Test
	public void testGetInstalledVersion() {
		assertTrue("Detected version:" + CDRDAO_VERSION + ", Wrapper version:"
				+ CdrdaoCliWrapperFactory.getInstalledVersion(),
				CDRDAO_VERSION.equals(CdrdaoCliWrapperFactory
						.getInstalledVersion()));
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#getInstalledInstance()}
	 * .
	 */
	@Test
	public void testGetInstalledInstance() {
		try {
			CdrdaoCliWrapperFactory.getInstalledInstance();
		} catch (CdrdaoException excep) {
			if (CDRDAO_INSTALLED) {
				fail("Detected and could not obtain installed instance");
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#createInstanceFromExecutablePath(String)}
	 * 
	 * @throws CdrdaoException
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateInstanceNullPath() throws CdrdaoException {
		// test with a bad path, the current directory
		CdrdaoCliWrapperFactory.createInstanceFromExecutablePath(null);
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#createInstanceFromExecutablePath(String)}
	 * 
	 * @throws CdrdaoException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInstanceEmptyPath() throws CdrdaoException {
		// test with a bad path, the current directory
		CdrdaoCliWrapperFactory.createInstanceFromExecutablePath(EMPTY_STRING);
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#createInstanceFromExecutablePath(String)}
	 * 
	 * @throws CdrdaoException
	 */
	@Test(expected = CdrdaoException.class)
	public void testCreateInstanceBadPath() throws CdrdaoException {
		// test with a bad path, the current directory
		CdrdaoCliWrapperFactory.createInstanceFromExecutablePath(".");
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#createInstanceFromExecutablePath(String)}
	 * 
	 * @throws CdrdaoException
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateInstanceFromNullExectableFile() throws CdrdaoException {
		// test with a bad path, the current directory
		CdrdaoCliWrapperFactory.createInstanceFromExecutableFile(null);
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.cdrdao.CdrdaoCliWrapperFactory#createInstanceFromExecutablePath(java.lang.String)}
	 * .
	 * @throws CdrdaoException 
	 */
	@Test(expected = CdrdaoException.class)
	public void testCreateInstanceFromBadExectableFile() throws CdrdaoException {
		CdrdaoCliWrapperFactory.createInstanceFromExecutableFile(BAD_EXE);
	}
}
