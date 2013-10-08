package org.opf_labs.arc_cd;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opf_labs.arc_cd.collection.CollectionItemTest;
import org.opf_labs.arc_cd.collection.TocItemRecordTests;
import org.opf_labs.arc_cd.config.ArcCdConfigTest;

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
@SuiteClasses({ArcCdConfigTest.class, CollectionItemTest.class, TocItemRecordTests.class})
public class AllArcCdCliTests {

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
