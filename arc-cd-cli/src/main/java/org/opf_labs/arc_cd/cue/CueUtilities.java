/**
 * 
 */
package org.opf_labs.arc_cd.cue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.opf_labs.arc_cd.cdrdao.toc.AudioTrack;
import org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord;
import org.opf_labs.arc_cd.cdrdao.toc.TrackType;
import org.opf_labs.arc_cd.collection.CdItemRecord;
import org.opf_labs.arc_cd.collection.CdTrack;
import org.opf_labs.audio.CueSheet;
import org.opf_labs.audio.CueSheetSerializer;
import org.opf_labs.audio.FileData;
import org.opf_labs.audio.Index;
import org.opf_labs.audio.Position;
import org.opf_labs.audio.TrackData;

import com.google.common.base.Preconditions;

/**
 * @author carl
 *
 */
public final class CueUtilities {
	/** The delimeter used between time fields */
	public static final String TIME_DELIMETER = ":";
	/** TOC File format start time */
	public static final String TOC_START_TIME = "0";
	/** The number of hundreths in a second */
	public static final int HUNDRETHS_PER_SECOND = 100;
	/** The number of seconds in a minute */
	public static final int SECONDS_PER_MINUTE = 60;
	/** The number of frames in a second */
	public static final int FRAMES_PER_SECOND = 75;
	private static final int MINUTES_INDEX = 0;
	private static final int SECONDS_INDEX = 1;
	private static final int HUNDRETHS_INDEX = 2;

	private CueUtilities() {
		throw new AssertionError("Should not be in " + this.getClass().getName() + " default constructor.");
	}
	
	/**
	 * Converts a TOC format time to a CUE format position 
	 * @param time the TOC file time to convert
	 * @return a CUE file position
	 */
	public static Position timeToPosition(final String time) {
		Preconditions.checkNotNull(time, "time == null");
		Preconditions.checkArgument(!time.isEmpty(), "time.isEmpty()");
		int minutes = 0;
		int seconds = 0;
		int frames = 0;
		if (!time.equals(TOC_START_TIME)) {
			String[] timeParts = time.split(TIME_DELIMETER);
			if (timeParts.length != 3) throw new AssertionError("Expecting 3 part time string with " + TIME_DELIMETER + " delimeter: mm:ss:hh");
			frames = hundrethsToFrames(timeParts[HUNDRETHS_INDEX]);
			seconds = Integer.parseInt(timeParts[SECONDS_INDEX]);
			if (frames >= FRAMES_PER_SECOND) {
				seconds+=(frames / FRAMES_PER_SECOND);
				frames = frames % FRAMES_PER_SECOND;
			}
			minutes = Integer.parseInt(timeParts[MINUTES_INDEX]);
			if (seconds >= SECONDS_PER_MINUTE) {
				minutes+=(seconds / SECONDS_PER_MINUTE);
				seconds = seconds % SECONDS_PER_MINUTE;
			}
		}
		return new Position(minutes, seconds, frames);
	}
	
	/**
	 * Converts hundreths of a second, used by TOC format to divide seconds, to frames, used by CUE format to divide seconds
	 * @param hundreths the number of hundreths to convert
	 * @return the hundreths expressed as frames
	 */
	public static int hundrethsToFrames(final String hundreths) {
		Preconditions.checkNotNull(hundreths, "hundreths == null");
		Preconditions.checkArgument(!hundreths.isEmpty(), "hundreths.isEmpty()");
		float fltHundreths = Float.parseFloat(hundreths);
		return Math.round((fltHundreths * FRAMES_PER_SECOND) / HUNDRETHS_PER_SECOND);
	}
	
	/**
	 * @param toc the TOC object used to create the CUE file
	 * @param info the INFO object used to create the CUE file
	 * @return the created CueSheet object
	 */
	public static CueSheet cueFromTocAndInfo(final TocItemRecord toc, final CdItemRecord info) {
		Preconditions.checkNotNull(toc, "toc == null");
		Preconditions.checkNotNull(info, "info == null");
		Preconditions.checkState(toc.getTracks().size() >= info.getTracks().size(), "TocItemRecord and CdItemRecord have different number of tracks.");

		CueSheet cue = cueSheetFromInfo(info);
		FileData fileData = new FileData(cue);
		cue.getFileData().add(fileData);

		Iterator<CdTrack> infoTracks = info.getTracks().iterator();
		Iterator<AudioTrack> tocTracks = toc.getTracks().iterator();
		while (tocTracks.hasNext()) {
			AudioTrack audioTrack = tocTracks.next();
			if (audioTrack.getType() == TrackType.AUDIO) {
				CdTrack infoTrack = infoTracks.next();
				if (fileData.getFile() == null) {
					fileData.setFile(FilenameUtils.getName(audioTrack.getFile()));
				}
				TrackData cueTrack = trackDataFromTocAndInfoTracks(fileData, audioTrack, infoTrack);
				fileData.getTrackData().add(cueTrack);
			} else {
				TrackData cueTrack = trackDataFromTocTrack(fileData, audioTrack);
				fileData.getTrackData().add(cueTrack);
			}
		}

		return cue;
	}
	
	/**
	 * @param cueSheet the cue sheet to serialise to file
	 * @param file the file to write the CUE sheet to
	 * @return true if serialised to file, false otherwise
	 */
	public static boolean cueSheetToFile(CueSheet cueSheet, File file) {
		CueSheetSerializer css = new CueSheetSerializer();
		try (PrintWriter writer = new PrintWriter(file)) {
			writer.print(css.serializeCueSheet(cueSheet));
			writer.close();
		} catch (FileNotFoundException excep) {
			// TODO Auto-generated catch block
			excep.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static CueSheet cueSheetFromInfo(CdItemRecord info) {
		CueSheet cue = new CueSheet();
		cue.setPerformer(info.getAlbumArtist());
		cue.setTitle(info.getTitle());
		return cue;
	}
	
	private static TrackData trackDataFromTocAndInfoTracks(final FileData parent, final AudioTrack tocTrack, final CdTrack infoTrack) {
		TrackData cueTrack = trackDataFromTocTrack(parent, tocTrack);
		cueTrack.setTitle(infoTrack.getTitle());
		if (!infoTrack.getArtist().isEmpty() && !infoTrack.getArtist().equals(CdTrack.DEFAULT_ARTIST)) {
			cueTrack.setPerformer(infoTrack.getArtist());
		}
		return cueTrack;
	}
	
	private static TrackData trackDataFromTocTrack(final FileData parent, final AudioTrack tocTrack) {
		TrackData cueTrack = new TrackData(parent, tocTrack.getNumber(), tocTrack.getType().getCueSyntax());
		if (!tocTrack.getIsrc().isEmpty()) {
			cueTrack.setIsrcCode(tocTrack.getIsrc());
		}
		Index index = new Index(1, CueUtilities.timeToPosition(tocTrack.getStart()));
		cueTrack.getIndices().add(index);
		return cueTrack;
	}
}
