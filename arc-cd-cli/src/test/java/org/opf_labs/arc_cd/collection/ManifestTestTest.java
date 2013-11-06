/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;
import org.opf_labs.arc_cd.collection.ManifestTest.Result;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class ManifestTestTest {


	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#defaultInstance()}.
	 */
	@Test
	public final void testDefaultInstance() {
		ManifestTest notDefault = ManifestTest.testItem(AllArcCdCliTests.ITEM_00022);
		ManifestTest defaultItem = ManifestTest.defaultInstance();
		ManifestTest secondDefaultItem = ManifestTest.defaultInstance();
		assertTrue(!notDefault.equals(defaultItem));
		assertTrue(!notDefault.equals(secondDefaultItem));
		assertTrue(defaultItem.equals(secondDefaultItem));
		assertTrue(defaultItem == secondDefaultItem);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#testItem(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testTestItemPass() {
		ManifestTest passTest = ManifestTest.testItem(AllArcCdCliTests.ITEM_00022);
		assertTrue(passTest.hasPassed());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#testItem(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testTestMissingInfo() {
		ManifestTest failTest = ManifestTest.testItem(AllArcCdCliTests.ITEM_00390);
		assertFalse(failTest.hasPassed());
		assertTrue(failTest.getBinResult() == Result.OK);
		assertTrue(failTest.getInfoResult() == Result.DELETED);
		assertTrue(failTest.getTocResult() == Result.OK);
		assertTrue(failTest.getCueResult() == Result.OK);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#testItem(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testTestMissingBin() {
		ManifestTest failTest = ManifestTest.testItem(AllArcCdCliTests.ITEM_00304);
		assertFalse(failTest.hasPassed());
		assertTrue(failTest.getBinResult() == Result.DELETED);
		assertTrue(failTest.getInfoResult() == Result.OK);
		assertTrue(failTest.getTocResult() == Result.OK);
		assertTrue(failTest.getCueResult() == Result.OK);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#testItem(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testTestMissingToc() {
		ManifestTest failTest = ManifestTest.testItem(AllArcCdCliTests.ITEM_00429);
		assertFalse(failTest.hasPassed());
		assertTrue(failTest.getBinResult() == Result.OK);
		assertTrue(failTest.getInfoResult() == Result.OK);
		assertTrue(failTest.getTocResult() == Result.DELETED);
		assertTrue(failTest.getCueResult() == Result.OK);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#testItem(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testTestAlteredBin() {
		ManifestTest failTest = ManifestTest.testItem(AllArcCdCliTests.ITEM_00212);
		assertFalse(failTest.hasPassed());
		assertTrue(failTest.getBinResult() == Result.ALTERED);
		assertTrue(failTest.getInfoResult() == Result.OK);
		assertTrue(failTest.getTocResult() == Result.OK);
		assertTrue(failTest.getCueResult() == Result.OK);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ManifestTest#testItem(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testTestAddedCue() {
		ManifestTest failTest = ManifestTest.testItem(AllArcCdCliTests.ITEM_00103);
		assertFalse(failTest.hasPassed());
		assertTrue(failTest.getBinResult() == Result.DELETED);
		assertTrue(failTest.getInfoResult() == Result.OK);
		assertTrue(failTest.getTocResult() == Result.OK);
		assertTrue(failTest.getCueResult() == Result.ADDED);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		EqualsVerifier.forClass(ManifestTest.class).verify();
	}
}
