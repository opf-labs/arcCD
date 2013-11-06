package org.opf_labs.arc_cd;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opf_labs.arc_cd.collection.ArchiveCollectionTest;
import org.opf_labs.arc_cd.collection.ArchiveItem;
import org.opf_labs.arc_cd.collection.ArchiveItemTest;
import org.opf_labs.arc_cd.collection.CdItemRecordTest;
import org.opf_labs.arc_cd.collection.CdTrackTest;
import org.opf_labs.arc_cd.collection.ItemManifestTest;
import org.opf_labs.arc_cd.collection.ManifestTestTest;
import org.opf_labs.arc_cd.config.ArcCdConfigTest;
import org.opf_labs.arc_cd.cue.CueSheetTest;
import org.opf_labs.arc_cd.cue.CueUtilitiesTest;

/**
 * Test suite for the ArcCd CLI project, supressed JavaDoc warnings
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 7 Oct 2013:23:31:18
 */
@SuppressWarnings("javadoc")
@RunWith(Suite.class)
@SuiteClasses({ArchiveCollectionTest.class, ArchiveItemTest.class, CdItemRecordTest.class, CdTrackTest.class, ItemManifestTest.class, ManifestTestTest.class, ArcCdConfigTest.class, CueSheetTest.class, CueUtilitiesTest.class })
public class AllArcCdCliTests {

	public static final File INFO_00001;
	public static final File INFO_00022;
	public static final File INFO_00103;
	public static final File INFO_00212;
	public static final File INFO_00304;
	public static final File INFO_00429;
	public static final File MANIFEST_00022;
	public static final File MANIFEST_00390;
	public static final File TEST_COLLECTION_ROOT;
	public static final ArchiveItem ITEM_00022;
	public static final ArchiveItem ITEM_00103;
	public static final ArchiveItem ITEM_00212;
	public static final ArchiveItem ITEM_00304;
	public static final ArchiveItem ITEM_00390;
	public static final ArchiveItem ITEM_00429;
	static {
		try {
			INFO_00001 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00001/00001.info");
			INFO_00022 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00022/00022.info");
			INFO_00103 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00103/00103.info");
			INFO_00212 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00212/00212.info");
			INFO_00304 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00304/00304.info");
			INFO_00429 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00429/00429.info");
			MANIFEST_00022 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00022/00022.man");
			MANIFEST_00390 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00390/00390.man");
			TEST_COLLECTION_ROOT = INFO_00022.getParentFile().getParentFile();
			ITEM_00022 = ArchiveItem.fromDirectory(INFO_00022.getParentFile());
			ITEM_00103 = ArchiveItem.fromDirectory(INFO_00103.getParentFile());
			ITEM_00212 = ArchiveItem.fromDirectory(INFO_00212.getParentFile());
			ITEM_00304 = ArchiveItem.fromDirectory(INFO_00304.getParentFile());
			ITEM_00390 = ArchiveItem.fromDirectory(MANIFEST_00390.getParentFile());
			ITEM_00429 = ArchiveItem.fromDirectory(INFO_00429.getParentFile());
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
