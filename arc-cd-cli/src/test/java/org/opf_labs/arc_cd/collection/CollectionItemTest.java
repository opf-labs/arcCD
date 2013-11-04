/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;

/**
 * @author carl
 * 
 */
public class CollectionItemTest {
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
	 * {@link org.opf_labs.arc_cd.collection.ArchiveCollection#isInfoFile(File)}
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testIsInfoFile() throws URISyntaxException {
		assertTrue(ArchiveCollection.isInfoFile(AllArcCdCliTests.INFO_00001));
	}
}
