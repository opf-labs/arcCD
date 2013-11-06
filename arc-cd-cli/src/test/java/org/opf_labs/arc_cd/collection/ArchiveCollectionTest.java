/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;
import org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class ArchiveCollectionTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#ArchiveCollection(java.lang.String)}.
	 */
	@Test
	public final void testArchiveCollectionString() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		assertTrue(fromString.size() > 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#ArchiveCollection(java.io.File)}.
	 */
	@Test
	public final void testArchiveCollectionFile() {
		ArchiveCollection fromFile = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT);
		assertTrue(fromFile.size() > 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getItemRecord(java.lang.Integer)}.
	 */
	@Test
	public final void testGetItemRecord() {
		ArchiveCollection fromFile = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT);
		assertTrue(fromFile.getItemRecord(Integer.valueOf(1)) != CdItemRecord.DEFAULT_ITEM);
		assertTrue(fromFile.getItemRecord(Integer.valueOf(2)) == CdItemRecord.DEFAULT_ITEM);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getItemToc(java.lang.Integer)}.
	 */
	@Test
	public final void testGetItemToc() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		assertTrue(fromString.getItemToc(Integer.valueOf(22)) != TocItemRecord.defaultInstance());
		assertTrue(fromString.getItemToc(Integer.valueOf(2)) == TocItemRecord.defaultInstance());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getItemCueSheet(java.lang.Integer)}.
	 */
	@Test
	public final void testGetItemCueSheet() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		assertTrue(fromString.getItemCueSheet(Integer.valueOf(22)) != ArchiveItem.DEFAULT_CUE);
		assertTrue(fromString.getItemCueSheet(Integer.valueOf(2)) == ArchiveItem.DEFAULT_CUE);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#checkItemManifest(java.lang.Integer)}.
	 */
	@Test
	public final void testCheckItemManifest() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		assertTrue(fromString.checkItemManifest(Integer.valueOf(22)).hasPassed());
		assertFalse(fromString.checkItemManifest(Integer.valueOf(103)).hasPassed());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getInfoFile(int)}.
	 */
	@Test
	public final void testGetInfoFile() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		File info = fromString.getInfoFile(22);
		File otherInfo = AllArcCdCliTests.INFO_00022;
		assertTrue(info.equals(otherInfo));
	}

}
