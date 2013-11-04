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
	private static final File INFO_00001;
	static {
		try {
			INFO_00001 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00001.info");
		} catch (URISyntaxException excep) {
			throw new AssertionError("Couldn't load test data.");
		}
	}
	/**
	 * Test method for
	 * {@link org.opf_labs.arc_cd.collection.CdItemRecord#fromInfoFile(java.io.File)}
	 * 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testArtistFromFile() throws URISyntaxException, IOException {
		CdItemRecord item = CdItemRecord.fromInfoFile(INFO_00001);
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
		CdItemRecord item = CdItemRecord.fromInfoFile(INFO_00001);
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
		CdItemRecord item = CdItemRecord.fromInfoFile(INFO_00001);
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
		File test = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00001.info");
		assertTrue(ArchiveCollection.isInfoFile(test));
	}
}
