/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * @author carl
 *
 */
public final class TocItemRecord {
	private final List<TrackTiming> tracks = new ArrayList<>();
	private static final Pattern TRACK_PATTERN = Pattern.compile("^.*\" ([0-9:]+) ([0-9:]+)$");
	
	private TocItemRecord() {
		// Disable default constructor
	}
	
	private TocItemRecord(List<TrackTiming> tracks) {
		this.tracks.addAll(tracks);
	}
	
	/**
	 * @return the list of track timings
	 */
	public List<TrackTiming> getTracks() {
		return Collections.unmodifiableList(this.tracks);
	}
	
	/**
	 * @param track the track to add
	 */
	public void addTrack(TrackTiming track) {
		this.tracks.add(track);
	}
	
	/**
	 * @param tocFile the table of contents file
	 * @return a TocItemRecord instance created from the file
	 * @throws IOException when there's a problem reading the file
	 */
	public static TocItemRecord fromTocFile(File tocFile) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(tocFile))) {
			List<TrackTiming> tracks = new ArrayList<>();
			String line;
			while((line = reader.readLine()) != null) {
				Matcher trackMatcher = TRACK_PATTERN.matcher(line);
				if (trackMatcher.matches())
					tracks.add(new TrackTiming(trackMatcher.group(1), trackMatcher.group(2)));
			}
			IOUtils.closeQuietly(reader);
			return new TocItemRecord(tracks);
		}
	}
}
