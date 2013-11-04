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

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class ItemManifestTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#getInfoMD5()}.
	 */
	@Test
	public final void testGetInfoMD5() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#getTocMD5()}.
	 */
	@Test
	public final void testGetTocMD5() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#getBinMD5()}.
	 */
	@Test
	public final void testGetBinMD5() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#getCueMD5()}.
	 */
	@Test
	public final void testGetCueMD5() {
		fail("Not yet implemented"); // TODO
	}

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
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#fromDirectory(org.opf_labs.arc_cd.collection.ArchiveItem)}.
	 */
	@Test
	public final void testFromDirectory() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#fromManifestFile(java.io.File)}.
	 */
	@Test
	public final void testFromManifestFile() {
		fail("Not yet implemented"); // TODO
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
