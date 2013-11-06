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
		assertFalse(fromFile.getItemRecord(Integer.valueOf(1)) == CdItemRecord.DEFAULT_ITEM);
		assertTrue(fromFile.getItemRecord(Integer.valueOf(2)) == CdItemRecord.DEFAULT_ITEM);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#size()}.
	 */
	@Test
	public final void testSize() {
		ArchiveCollection fromFile = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT);
		assertTrue(fromFile.size() == 7);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getArchiveIds()}.
	 */
	@Test
	public final void testGetArchiveIds() {
		ArchiveCollection fromFile = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT);
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(1)));
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(22)));
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(103)));
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(212)));
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(304)));
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(390)));
		assertTrue(fromFile.getArchiveIds().contains(Integer.valueOf(429)));
		assertFalse(fromFile.getArchiveIds().contains(Integer.valueOf(430)));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getArchiveItem(Integer)}.
	 */
	@Test
	public final void testGetArchiveItem() {
		ArchiveCollection fromFile = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT);
		ArchiveItem item = fromFile.getArchiveItem(Integer.valueOf(22));
		assertFalse(item.equals(ArchiveItem.DEFAULT));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getArchiveItems()}.
	 */
	@Test
	public final void testGetArchiveItems() {
		ArchiveCollection fromFile = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT);
		assertTrue(fromFile.getArchiveItems().size() == fromFile.size());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getItemToc(java.lang.Integer)}.
	 */
	@Test
	public final void testGetItemToc() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		assertFalse(fromString.getItemToc(Integer.valueOf(22)) == TocItemRecord.defaultInstance());
		assertTrue(fromString.getItemToc(Integer.valueOf(2)) == TocItemRecord.defaultInstance());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ArchiveCollection#getItemCueSheet(java.lang.Integer)}.
	 */
	@Test
	public final void testGetItemCueSheet() {
		ArchiveCollection fromString = new ArchiveCollection(AllArcCdCliTests.TEST_COLLECTION_ROOT.getAbsolutePath());
		assertFalse(fromString.getItemCueSheet(Integer.valueOf(22)) == ArchiveItem.DEFAULT_CUE);
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
