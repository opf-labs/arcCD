/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import com.google.common.base.Preconditions;

/**
 * @author carl
 *
 */
public class TrackTiming {
	private final String start;
	private final String duration;
	
	/**
	 * @param start
	 * @param duration
	 */
	public TrackTiming(final String start, final String duration) {
		Preconditions.checkNotNull(start, "start is null");
		Preconditions.checkArgument(!start.isEmpty(), "start.isEmpty()");
		Preconditions.checkNotNull(duration, "duration is null");
		Preconditions.checkArgument(!duration.isEmpty(), "duration.isEmpty()");
		this.start = start;
		this.duration = duration;
	}
	
	/**
	 * @return the start time of the track as a String
	 */
	public String getStart() {
		return this.start;
	}
	
	/**
	 * @return the duration of the track as a string
	 */
	public String getDuration() {
		return this.duration;
	}
}
