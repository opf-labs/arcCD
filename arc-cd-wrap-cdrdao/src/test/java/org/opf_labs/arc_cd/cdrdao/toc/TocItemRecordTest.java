/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.AllArcCdrdaoWrapperTests;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class TocItemRecordTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord#defaultInstance()}.
	 */
	@Test
	public final void testDefaultInstance() {
		TocItemRecord testItem = TocItemRecord.defaultInstance();
		assertTrue(testItem == TocItemRecord.defaultInstance());
		assertTrue(testItem.equals(TocItemRecord.defaultInstance()));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord#fromTocFile(java.io.File)}.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public final void testFromTocFile() throws FileNotFoundException, URISyntaxException {
		TocItemRecord fromFile = TocItemRecord.fromTocFile(AllArcCdrdaoWrapperTests.getResourceAsFile("org/opf_labs/arc_cd/collection/cd-1.toc"));
		assertTrue(fromFile.size() == 11);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord#fromInputStream(java.io.InputStream)}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public final void testFromInputStream() throws FileNotFoundException, IOException, URISyntaxException {
		try (InputStream inStream = new FileInputStream(AllArcCdrdaoWrapperTests.getResourceAsFile("org/opf_labs/arc_cd/collection/cd-1.toc"))) {
			TocItemRecord fromFile = TocItemRecord.fromInputStream(inStream);
			assertTrue(fromFile.size() == 11);
		}
	}

}
