/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import com.google.common.base.Preconditions;

/**
 * @author carl
 *
 */
public final class Track {
	public static final String DEFAULT = "UNKNOWN";
	private final String title;
	private final String artist;
	private Track() {
		this(DEFAULT, DEFAULT);
	}
	private Track(final String title) {
		this(title, DEFAULT);
	}
	private Track(final String title, final String artist) {
		this.title = title;
		this.artist = artist;
	}
	public static Track fromValues(final String title) {
		Preconditions.checkNotNull(title, "Track title is null");
		return new Track(title);
	}
	public static Track fromValues(final String title, final String artist) {
		Preconditions.checkNotNull(title, "Track title is null");
		Preconditions.checkNotNull(artist, "Track artist is null");
		return new Track(title, artist);
	}
	public String getTitle() {
		return this.title;
	}
	public String getArtist() {
		return this.artist;
	}
}
