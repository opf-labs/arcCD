/**
 * 
 */
package org.opf_labs.arc_cd.collection;

/**
 * @author carl
 *
 */
public class TrackTiming {
	private final String start;
	private final String duration;
	
	public TrackTiming(String start, String duration) {
		this.start = start;
		this.duration = duration;
	}
	
	public String getStart() {
		return this.start;
	}
	
	public String getDuration() {
		return this.duration;
	}
}
