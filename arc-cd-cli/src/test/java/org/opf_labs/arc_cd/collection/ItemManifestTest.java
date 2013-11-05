/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class ItemManifestTest {
	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#defaultInstance()}.
	 */
	@Test
	public final void testDefaultInstance() {
		assertTrue(ItemManifest.DEFAULT == ItemManifest.defaultInstance());
		assertTrue(ItemManifest.DEFAULT.getInfoMD5().equals(DigestUtils.md5Hex("")));
		assertTrue(ItemManifest.DEFAULT.getTocMD5().equals(DigestUtils.md5Hex("")));
		assertTrue(ItemManifest.DEFAULT.getBinMD5().equals(DigestUtils.md5Hex("")));
		assertTrue(ItemManifest.DEFAULT.getCueMD5().equals(DigestUtils.md5Hex("")));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#fromItemDirectory(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testFromDirectory() {
		ItemManifest fromItem = ItemManifest.fromItemDirectory(AllArcCdCliTests.ITEM_00022);
		ItemManifest fromManifest = ItemManifest.fromManifestFile(AllArcCdCliTests.MANIFEST_00022);
		assertTrue(fromManifest.equals(fromItem));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#fromBufferedReader(java.io.BufferedReader)}.
	 */
	@Test
	public final void testFromBufferedReader() {
		// String value of default instance
		String defaultString = ItemManifest.DEFAULT.toString();
		BufferedReader br = new BufferedReader(new StringReader(defaultString));
		ItemManifest fromString = ItemManifest.fromBufferedReader(br);
		assertTrue(ItemManifest.DEFAULT.equals(fromString));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		EqualsVerifier.forClass(ItemManifest.class).verify();
	}
}
