/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.AllArcCdrdaoWrapperTests;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class TocItemRecordTests {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord#getTracks()}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testGetTracks() throws URISyntaxException, IOException {
		File test = AllArcCdrdaoWrapperTests.getResourceAsFile("org/opf_labs/arc_cd/collection/cd-1.toc");
		TocItemRecord record = TocItemRecord.fromTocFile(test);
		for (AudioTrack track : record.getTracks()) {
			System.out.println(track.toString());
		}
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord#fromTocFile(java.io.File)}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void testFromTocFile() throws URISyntaxException, IOException {
		File test = AllArcCdrdaoWrapperTests.getResourceAsFile("org/opf_labs/arc_cd/collection/cd-1.toc");
		TocItemRecord.fromTocFile(test);
	}

}
