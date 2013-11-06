/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;

/**
 * @author carl
 * 
 */
public class CdItemRecordTest {
	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.collection.CdItemRecord#fromInfoFile(java.io.File)}
	 * 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testArtistFromFile() throws URISyntaxException, IOException {
		CdItemRecord item = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00001);
		assertTrue(item.albumArtist, item.albumArtist.equals("Pink Floyd"));
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.collection.CdItemRecord#fromInfoFile(java.io.File)}
	 * 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testTitleFromFile() throws URISyntaxException, IOException {
		CdItemRecord item = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00001);
		assertTrue(item.title.equals("Wish You Were Here"));
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.collection.CdItemRecord#fromInfoFile(java.io.File)}
	 * 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testTracksFromFile() throws URISyntaxException, IOException {
		CdItemRecord item = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00001);
		assertTrue(item.tracks.size() == 2);
	}

	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.collection.CdItemRecord#isInfoFile(File)}
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testIsInfoFile() throws URISyntaxException {
		assertTrue(CdItemRecord.isInfoFile(AllArcCdCliTests.INFO_00001));
	}
	
	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.collection.CdItemRecord#isInfoFilename(String)}
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testIsInfoFilename() throws URISyntaxException {
		assertFalse(CdItemRecord.isInfoFilename(""));
		assertFalse(CdItemRecord.isInfoFilename("001"));
		assertFalse(CdItemRecord.isInfoFilename("001.info"));
		assertTrue(CdItemRecord.isInfoFilename("99999.info"));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.ItemManifest#equals(java.lang.Object)}.
	 */
	@Test
	@SuppressWarnings("static-method")
	public final void testEqualsObject() {
		EqualsVerifier.forClass(CdItemRecord.class).verify();
	}
	
}
