/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.opf_labs.arc_cd.cdrdao.toc.AudioTrack.Builder;

import com.google.common.base.Preconditions;

/**
 * @author carl
 * 
 */
public final class TocItemRecord {
	private static final Logger LOGGER = Logger.getLogger(TocItemRecord.class); 
	private static final TocItemRecord DEFAULT = new TocItemRecord(new ArrayList<AudioTrack>());
	private final int size;
	private final List<AudioTrack> tracks;

	private TocItemRecord(List<AudioTrack> tracks) {
		this.size = tracks.size();
		this.tracks = new ArrayList<>();
		this.tracks.addAll(tracks);
	}

	/**
	 * @return the list of track timings
	 */
	public List<AudioTrack> getTracks() {
		return Collections.unmodifiableList(this.tracks);
	}
	
	/**
	 * @return the number of tracks in the TOC
	 */
	public int size() {
		return this.size;
	}

	@Override
	public String toString() {
		StringBuilder value = new StringBuilder();
		for (AudioTrack track : this.tracks) {
			value.append(track.toString());
		}
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.tracks == null) ? 0 : this.tracks.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TocItemRecord other = (TocItemRecord) obj;
		if (this.tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!this.tracks.equals(other.tracks))
			return false;
		return true;
	}

	/**
	 * @return the default instance
	 */
	public static TocItemRecord defaultInstance() {
		return DEFAULT;
	}

	/**
	 * @param tocFile
	 *            the table of contents file
	 * @return a TocItemRecord instance created from the file
	 */
	public static TocItemRecord fromTocFile(File tocFile) {
		Preconditions.checkNotNull(tocFile, "tocFile == null");
		Preconditions.checkArgument(tocFile.exists(), "TOC file doesn't exist");
		try (BufferedReader reader = new BufferedReader(new FileReader(tocFile))) {
			TocItemRecord record = fromBufferedReader(reader);
			return record;
		} catch (IOException excep) {
			LOGGER.error(excep);
			return DEFAULT;
		}
	}

	/**
	 * @param stream
	 *            java.io.InputStream with TOC content
	 * @return the TocItemRecord instance parsed from the stream contents
	 * @throws IOException
	 *             if the stream can't be read
	 */
	public static TocItemRecord fromInputStream(InputStream stream)
			throws IOException {
		Preconditions.checkNotNull(stream, "stream == null");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream))) {
			TocItemRecord record = fromBufferedReader(reader);
			return record;
		} finally {
			// Do nothing
		}
	}

	private static TocItemRecord fromBufferedReader(BufferedReader reader)
			throws IOException {
		Preconditions.checkNotNull(reader, "reader == null");
		List<AudioTrack> tracks = new ArrayList<>();
		String line;
		String lastTrack = "";
		while ((line = reader.readLine()) != null) {
			System.err.println(line);
			Matcher trackStartMatcher = TocUtilities.TRACK_START_COMMENT.matcher(line);
			if (trackStartMatcher.matches()) {
				lastTrack = trackStartMatcher.group(1);
			}
			Matcher trackTypeMatcher = TocUtilities.TRACK_TYPE_PATTERN.matcher(line);
			if (trackTypeMatcher.matches()) {
				tracks.add(parseTrack(lastTrack, TrackType.fromTocValue(trackTypeMatcher.group(1)), reader));
			}
		}
		return new TocItemRecord(tracks);
	}

	private static AudioTrack parseTrack(String numberMatch, TrackType type,
			BufferedReader reader) throws IOException {
		int number = Integer.parseInt(numberMatch);
		Builder builder = new Builder(number);
		builder.type(type);
		String line;
		while (((line = reader.readLine()) != null) && !(line.isEmpty())) {
			Matcher isrcMatcher = TocUtilities.ISRC_PATTERN.matcher(line);
			if (isrcMatcher.matches()) {
				builder.isrc(isrcMatcher.group(1));
			}
			Matcher fileMatcher = TocUtilities.FILE_PATTERN.matcher(line);
			if (fileMatcher.matches()) {
				builder.file(fileMatcher.group(1));
				builder.start(fileMatcher.group(2));
				builder.length(fileMatcher.group(3));
			}
		}
		return builder.build();
	}
}
