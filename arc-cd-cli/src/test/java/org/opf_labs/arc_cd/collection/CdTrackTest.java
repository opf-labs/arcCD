/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class CdTrackTest {
	final static String TEST_TITLE = "A test track title";
	final static String TEST_ARTIST = "A test track artist";
	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.CdTrack#defaultInstance()}.
	 */
	@Test
	public final void testDefaultInstance() {
		assertTrue(CdTrack.DEFAULT.equals(CdTrack.defaultInstance()));
		assertTrue(CdTrack.DEFAULT == CdTrack.defaultInstance());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.CdTrack#fromValues(java.lang.String)}.
	 */
	@Test
	public final void testFromValuesString() {
		CdTrack toTest = CdTrack.fromValues(TEST_TITLE);
		assertFalse(toTest.equals(CdTrack.defaultInstance()));
		assertTrue(toTest.equals(CdTrack.fromValues(TEST_TITLE)));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.CdTrack#fromValues(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testFromValuesStringString() {
		CdTrack toTest = CdTrack.fromValues(TEST_TITLE, TEST_ARTIST);
		assertFalse(toTest.equals(CdTrack.defaultInstance()));
		assertFalse(toTest.equals(CdTrack.fromValues(TEST_TITLE)));
		assertTrue(toTest.equals(CdTrack.fromValues(TEST_TITLE, TEST_ARTIST)));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.CdTrack#getTitle()}.
	 */
	@Test
	public final void testGetTitle() {
		CdTrack toTest = CdTrack.fromValues(TEST_TITLE, TEST_ARTIST);
		assertTrue(toTest.getTitle().equals(TEST_TITLE));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.CdTrack#getArtist()}.
	 */
	@Test
	public final void testGetArtist() {
		CdTrack toTest = CdTrack.fromValues(TEST_TITLE, TEST_ARTIST);
		assertTrue(toTest.getArtist().equals(TEST_ARTIST));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.CdTrack#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		EqualsVerifier.forClass(CdTrack.class).verify();
	}

}
