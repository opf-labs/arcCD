/**
 * 
 */
package org.opf_labs.arc_cd.cue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.opf_labs.arc_cd.AllArcCdCliTests;
import org.opf_labs.arc_cd.cdrdao.toc.AudioTrack;
import org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord;
import org.opf_labs.arc_cd.collection.CdItemRecord;
import org.opf_labs.audio.CueSheet;
import org.opf_labs.audio.CueSheetSerializer;
import org.opf_labs.audio.Position;
import org.opf_labs.audio.TrackData;

/**
 * @author carl
 *
 */
public class CueUtilitiesTest {
	static final String[] TEST_HUNDRETHS = new String[] {"0", "10", "22", "50", "99", "100"};
	static final int[] TEST_FRAMES = new int[] {0, 8, 17, 38, 74, 75};
	static final String[] TEST_TIMES = new String[] {"0", "00:00:99", "12:50:50", "07:59:99", "02:01:00"};
	static final Position[] TEST_POSITIONS = new Position[] {new Position(0, 0, 0), new Position(0, 0, 74), new Position(12, 50, 38), new Position(7, 59, 74), new Position(2, 1, 0)};
	static final File TOC_00022;
	static final File TOC_CD1;
	static {
		try {
			TOC_00022 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/00022/00022.toc");
			TOC_CD1 = AllArcCdCliTests.getResourceAsFile("org/opf_labs/arc_cd/collection/misc/cd-1.toc");
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Couldn't find test data");
		}
	}


	@Before
	public void setUp() {
		assertEquals(TEST_HUNDRETHS.length, TEST_FRAMES.length);
		assertEquals(TEST_TIMES.length, TEST_POSITIONS.length);
	}
	
	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#hundrethsToFrames(java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testHundrethsToFramesNullHundreths() {
		CueUtilities.hundrethsToFrames(null);
	}
	
	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#hundrethsToFrames(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testHundrethsToFramesEmptyHundreths() {
		CueUtilities.hundrethsToFrames("");
	}
	
	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#hundrethsToFrames(java.lang.String)}.
	 */
	@Test
	public void testHundrethsToFrames() {
		for (int iLoop = 0; iLoop < TEST_HUNDRETHS.length; iLoop++) {
			assertEquals(TEST_FRAMES[iLoop], CueUtilities.hundrethsToFrames(TEST_HUNDRETHS[iLoop]));
		}
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#timeToPosition(java.lang.String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTimeToPositionNullTime() {
		CueUtilities.timeToPosition(null);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#timeToPosition(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTimeToPositionEmptyTime() {
		CueUtilities.timeToPosition("");
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#timeToPosition(java.lang.String)}.
	 */
	@Test
	public void testTimeToPosition() {
		for (int iLoop = 0; iLoop < TEST_TIMES.length; iLoop++) {
			assertEquals(TEST_POSITIONS[iLoop].getMinutes(), CueUtilities.timeToPosition(TEST_TIMES[iLoop]).getMinutes());
			assertEquals(TEST_POSITIONS[iLoop].getSeconds(), CueUtilities.timeToPosition(TEST_TIMES[iLoop]).getSeconds());
			assertEquals(TEST_POSITIONS[iLoop].getFrames(), CueUtilities.timeToPosition(TEST_TIMES[iLoop]).getFrames());
		}
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#cueFromTocAndInfo(org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord, org.opf_labs.arc_cd.collection.CdItemRecord)}.
	 * @throws FileNotFoundException 
	 */
	@Test(expected=NullPointerException.class)
	public void testCueFromTocAndInfoNullToc() throws FileNotFoundException {
		CdItemRecord itemRecord = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00022);
		CueUtilities.cueFromTocAndInfo(null, itemRecord);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#cueFromTocAndInfo(org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord, org.opf_labs.arc_cd.collection.CdItemRecord)}.
	 * @throws IOException 
	 */
	@Test(expected=NullPointerException.class)
	public void testCueFromTocAndInfoNullInfo() throws IOException {
		TocItemRecord tocRecord = TocItemRecord.fromTocFile(TOC_00022);
		CueUtilities.cueFromTocAndInfo(tocRecord, null);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#cueFromTocAndInfo(org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord, org.opf_labs.arc_cd.collection.CdItemRecord)}.
	 * @throws IOException 
	 */
	@Test(expected=IllegalStateException.class)
	public void testCueFromTocAndInfoDifferingTracks() throws IOException {
		TocItemRecord tocRecord = TocItemRecord.fromTocFile(TOC_CD1);
		CdItemRecord itemRecord = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00022);
		CueUtilities.cueFromTocAndInfo(tocRecord, itemRecord);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cue.CueUtilities#cueFromTocAndInfo(org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord, org.opf_labs.arc_cd.collection.CdItemRecord)}.
	 * @throws IOException 
	 */
	@Test
	public void testCueFromTocAndInfoCdDetails() throws IOException {
		CdItemRecord itemRecord = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00022);
		TocItemRecord tocRecord = TocItemRecord.fromTocFile(TOC_00022);
		CueSheet cue = CueUtilities.cueFromTocAndInfo(tocRecord, itemRecord);
		assertTrue(cue.getPerformer().equals(itemRecord.getAlbumArtist()));
		assertTrue(cue.getTitle().equals(itemRecord.getTitle()));
	}

	@Test
	public void testCueFromTocAndInfoTrackTocDetails() {
		CdItemRecord itemRecord = CdItemRecord.fromInfoFile(AllArcCdCliTests.INFO_00022);
		TocItemRecord tocRecord = TocItemRecord.fromTocFile(TOC_00022);
		CueSheet cue = CueUtilities.cueFromTocAndInfo(tocRecord, itemRecord);
		assertEquals(tocRecord.getTracks().size(), cue.getAllTrackData().size());
		Iterator<AudioTrack> tocTracks = tocRecord.getTracks().iterator();
		Iterator<TrackData> cueTracks = cue.getAllTrackData().iterator();
		while(tocTracks.hasNext() && cueTracks.hasNext()) {
			AudioTrack tocTrack = tocTracks.next();
			assertEquals(FilenameUtils.getName(tocTrack.getFile()), cue.getFileData().get(0).getFile());
			TrackData cueTrack = cueTracks.next();
			assertEquals(cueTrack.getIsrcCode(), tocTrack.getIsrc().isEmpty() ? null : tocTrack.getIsrc());
			assertEquals(cueTrack.getNumber(), tocTrack.getNumber());
		}
		CueSheetSerializer serializer = new CueSheetSerializer();
		System.out.println(serializer.serializeCueSheet(cue));
	}
}
