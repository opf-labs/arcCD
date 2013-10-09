/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.Builder;
import org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.MediaType;
import org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.TocType;

/**
 * Test fixture for CdrdaoDiskInfo class and it's Builder
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 9 Oct 2013:23:43:28
 */
@SuppressWarnings("static-method")
public class CdrdaoDiskInfoTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.Builder#mediaType(MediaType)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testBuilderMediaTypeNull() {
		Builder testBuilder = Builder.getAudioCdInitialised();
		testBuilder.mediaType(null); 
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo#getMediaType()}.
	 */
	@Test
	public void testGetMediaTypeAudio() {
		CdrdaoDiskInfo testInstance = Builder.getAudioCdInitialised().build();
		assertTrue(testInstance.getMediaType().equals(CdrdaoDiskInfo.MediaType.CD));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo#getMediaType()}.
	 */
	@Test
	public void testGetMediaTypeNonAudio() {
		CdrdaoDiskInfo testInstance = Builder.getAudioCdInitialised().mediaType(MediaType.CD_R).build();
		assertFalse(testInstance.getMediaType().equals(CdrdaoDiskInfo.MediaType.CD));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.Builder#mediaType(MediaType)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testBuilderCapacityNull() {
		Builder testBuilder = Builder.getAudioCdInitialised();
		testBuilder.capacity(null); 
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo#getCapacity()}.
	 */
	@Test
	public void testGetCapacityAudio() {
		CdrdaoDiskInfo testInstance = Builder.getAudioCdInitialised().build();
		assertTrue(testInstance.getCapacity().equals(Capacity.AUDIO_DEFAULT));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo#getCapacity()}.
	 */
	@Test
	public void testGetCapacityCdr() {
		CdrdaoDiskInfo testInstance = Builder.getCdrInitialised().build();
		assertTrue(testInstance.getCapacity().equals(Capacity.Builder.newBlankCdrInitialised().build()));
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.Builder#mediaType(MediaType)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testBuilderTocTypeNull() {
		Builder testBuilder = Builder.getAudioCdInitialised();
		testBuilder.tocType(null); 
	}


	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo#getTocType()}.
	 */
	@Test
	public void testGetTocType() {
		CdrdaoDiskInfo testInstance = Builder.getCdrInitialised().build();
		assertTrue(testInstance.getTocType() == TocType.CDDA_OR_CDROM);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.Builder#mediaType(MediaType)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuilderSessionsLessThanZero() {
		Builder testBuilder = Builder.getAudioCdInitialised();
		testBuilder.sessions(-1); 
	}
	
	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.CdrdaoDiskInfo.Builder#mediaType(MediaType)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuilderLastTrackLessThanZero() {
		Builder testBuilder = Builder.getAudioCdInitialised();
		testBuilder.lastTrack(-1); 
	}
}
