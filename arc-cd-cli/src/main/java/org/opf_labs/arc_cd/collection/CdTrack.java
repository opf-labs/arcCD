/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import com.google.common.base.Preconditions;

/**
 * @author carl
 *
 */
public final class CdTrack {
	/** Default value of unknown title */
	public static final String DEFAULT_TITLE = "UNKNOWN";
	/** Default value of unknown artist */
	public static final String DEFAULT_ARTIST = "UNKNOWN";
	/** Default instance */
	public static final CdTrack DEFAULT = new CdTrack();
	private final String title;
	private final String artist;

	private CdTrack() {
		this(DEFAULT_TITLE, DEFAULT_ARTIST);
	}
	private CdTrack(final String title) {
		this(title, DEFAULT_ARTIST);
	}
	private CdTrack(final String title, final String artist) {
		this.title = title;
		this.artist = artist;
	}
	/**
	 * @param title the title for the track, the artits will default to UNKNOWN
	 * @return the initialised Track 
	 */
	public static CdTrack fromValues(final String title) {
		Preconditions.checkNotNull(title, "Track title is null");
		Preconditions.checkArgument(!title.isEmpty(), "title.isEmpty()");
		return new CdTrack(title);
	}
	/**
	 * @param title the title for the track
	 * @param artist the artist performing the track
	 * @return the initialised track instance
	 */
	public static CdTrack fromValues(final String title, final String artist) {
		Preconditions.checkNotNull(title, "Track title is null");
		Preconditions.checkArgument(!title.isEmpty(), "title.isEmpty()");
		Preconditions.checkNotNull(artist, "Track artist is null");
		Preconditions.checkArgument(!artist.isEmpty(), "artist.isEmpty()");
		return new CdTrack(title, artist);
	}
	/**
	 * @return the track title as a String
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * @return the track artist as a string
	 */
	public String getArtist() {
		return this.artist;
	}
}
