/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.TocItemRecord;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class TocItemRecordTests {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.TocItemRecord#getTracks()}.
	 */
	@Test
	public void testGetTracks() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.TocItemRecord#addTrack(org.opf_labs.arc_cd.cdrdao.TrackTiming)}.
	 */
	@Test
	public void testAddTrack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.TocItemRecord#fromTocFile(java.io.File)}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testFromTocFile() throws URISyntaxException, IOException {
		File test = AllArcCdrdaoWrapperTests.getResourceAsFile("org/opf_labs/arc_cd/collection/cd-1.toc");
		TocItemRecord.fromTocFile(test);
	}

}
