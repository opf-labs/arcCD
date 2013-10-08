/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class TocItemRecordTests {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.TocItemRecord#getTracks()}.
	 */
	@Test
	public void testGetTracks() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.TocItemRecord#addTrack(org.opf_labs.arc_cd.collection.TrackTiming)}.
	 */
	@Test
	public void testAddTrack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.collection.TocItemRecord#fromTocFile(java.io.File)}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testFromTocFile() throws URISyntaxException, IOException {
		File test = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/cd-1.toc");
		TocItemRecord.fromTocFile(test);
	}

}
