package org.opf_labs.arc_cd;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opf_labs.arc_cd.collection.CollectionItemTest;
import org.opf_labs.arc_cd.collection.ItemManifestTest;
import org.opf_labs.arc_cd.config.ArcCdConfigTest;
import org.opf_labs.arc_cd.cue.CueSheetTest;
import org.opf_labs.arc_cd.cue.CueUtilitiesTest;

/**
 * Test suite for the ArcCd CLI project
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 7 Oct 2013:23:31:18
 */
@RunWith(Suite.class)
@SuiteClasses({ArcCdConfigTest.class, CollectionItemTest.class, ItemManifestTest.class, CueSheetTest.class, CueUtilitiesTest.class })
public class AllArcCdCliTests {

	public static final File INFO_00001;
	public static final File INFO_00022;
	static {
		try {
			INFO_00001 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00001/00001.info");
			INFO_00022 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00022/00022.info");
		} catch (URISyntaxException excep) {
			throw new AssertionError("Couldn't load test data.");
		}
	}

	/**
	 * Given a string resource path and name returns a File object. Used to load
	 * test data, not meant to be a general utility method.
	 * 
	 * @param resName
	 *            the name of the resource to retrieve a file for
	 * @return the java.io.File for the named resource
	 * @throws URISyntaxException
	 *             if the named resource can't be converted to a URI
	 */
	public final static File getResourceAsFile(String resName)
			throws URISyntaxException {
		return new File(ClassLoader.getSystemResource(resName).toURI());
	}
}
