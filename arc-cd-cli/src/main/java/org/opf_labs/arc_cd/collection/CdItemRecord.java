/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;


/**
 * @author carl
 */
public final class CdItemRecord {
	private static final String DEFAULT_STRING_VALUE = "UNKNOWN";
	/** A default CdItemRecord for failed lookup and testing */
	public static final CdItemRecord DEFAULT_ITEM = new CdItemRecord();
	/** Value for Various Artists artist field */
	public static final String VARIOUS = "Various Artists";
	/** Label for the Album Title field */
	public static final String ALBUM_TITLE = "AlbumTitle";
	/** Label for the Compilation field */
	public static final String COMPILATION = "Compilation";
	/** Label for the Album Artist field */
	public static final String ALBUM_ARTIST = "AlbumArtist";
	/** Label for a Track field */
	public static final String TRACK = "Track";
	/** Label for a Track Artist field */
	public static final String ARTIST = "Artist";
	private static final String OPEN_DELIM = "\\[";
	private static final String CLOSE_DELIM = "\\]";
	private static final String GRABBER = "(.+)$";
	private static final Pattern ALBUM_TITLE_PATTERN = Pattern.compile("^" + OPEN_DELIM + ALBUM_TITLE + CLOSE_DELIM + GRABBER);
	private static final Pattern COMPILATION_PATTERN = Pattern.compile("^" + OPEN_DELIM + COMPILATION + CLOSE_DELIM + GRABBER);
	private static final Pattern ALBUM_ARTIST_PATTERN = Pattern.compile("^" + OPEN_DELIM + ALBUM_ARTIST + CLOSE_DELIM + GRABBER);
	private static final Pattern TRACK_TITLE_PATTERN = Pattern.compile("^" + OPEN_DELIM + TRACK + CLOSE_DELIM + GRABBER);
	private static final Pattern ARTIST_PATTERN = Pattern.compile("^" + OPEN_DELIM + ARTIST + CLOSE_DELIM + GRABBER);

	private static final Logger LOGGER = Logger.getLogger(CdItemRecord.class); 

	final String title;
	final String albumArtist;
	final boolean isCompilation;
	final List<CdTrack> tracks;
	
	private CdItemRecord() {
		this(DEFAULT_STRING_VALUE, VARIOUS, new ArrayList<CdTrack>());
	}
	
	private CdItemRecord(final String title, final String artist, final List<CdTrack> tracks) {
		this(title, artist, tracks, (artist == VARIOUS));
	}
	
	private CdItemRecord(final String title, final String artist, final List<CdTrack> tracks, final boolean isCompilation) {
		this.title = title;
		this.albumArtist = (artist == null || artist.isEmpty()) ? VARIOUS : artist;
		this.isCompilation = isCompilation;
		this.tracks = new ArrayList<>(tracks);
	}
	
	/**
	 * 
	 * @return the item title, the title of the cd
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 
	 * @return the album artist for the CD item
	 */
	public String getAlbumArtist() {
		return this.albumArtist;
	}
	
	/**
	 * 
	 * @return a java.util.List of Track items, one for each track on the CD
	 */
	public List<CdTrack> getTracks() {
		return this.tracks;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CdItemRecord [title=" + this.title + ", albumArtist="
				+ this.albumArtist + ", isCompilation=" + this.isCompilation
				+ ", tracks=" + this.tracks + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((this.albumArtist == null) ? 0 : this.albumArtist.hashCode());
		result = prime * result + (this.isCompilation ? 1231 : 1237);
		result = prime * result
				+ ((this.title == null) ? 0 : this.title.hashCode());
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
		CdItemRecord other = (CdItemRecord) obj;
		if (this.albumArtist == null) {
			if (other.albumArtist != null)
				return false;
		} else if (!this.albumArtist.equals(other.albumArtist))
			return false;
		if (this.isCompilation != other.isCompilation)
			return false;
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		if (this.tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!this.tracks.equals(other.tracks)) {
			return false;
		}
		return true;
	}

	/**
	 * @param fileName
	 *            the file name to test if it matches an info filename
	 * @return true if the file name is five digits zero padded number
	 */
	public static boolean isInfoFilename(String fileName) {
		return fileName.matches("^[0-9]{5}\\." + ArchiveCollection.INFO_EXT);
	}

	/**
	 * @param file
	 *            a file to test to see if it's an info file
	 * @return true if the file is an info file
	 */
	public static boolean isInfoFile(File file) {
		return file.isFile() && CdItemRecord.isInfoFilename(file.getName());
	}

	/**
	 * @return the default instance of a CdItemRecord
	 */
	public static CdItemRecord defaultItem() {
		return DEFAULT_ITEM;
	}

	/**
	 * Factory method that instantiates the 
	 * @param itemFile
	 * @return a CdItemRecord populated from file or the DEFAULT item if non found
	 */
	public static CdItemRecord fromInfoFile(File itemFile) {
		Preconditions.checkNotNull(itemFile, "itemFile is null");
		Builder builder = new Builder();
		try (BufferedReader br = new BufferedReader(new FileReader(itemFile))) {
			builder = Builder.fromBufferedReader(br);
		} catch (FileNotFoundException excep) {
			LOGGER.info("Couldn't find file:" + itemFile.getAbsolutePath());
		} catch (IOException excep) {
			LOGGER.info("Problem reading item info file:" + itemFile.getAbsolutePath());
		}
		return builder.build();
	}
	
	/**
	 * Simple builder for the immutable CdItemRecord objects.
	 * 
	 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
	 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
	 * @version 0.1
	 * 
	 * Created 29 Sep 2013:20:47:47
	 */
	@SuppressWarnings({"javadoc", "hiding"})
	public static final class Builder {
		private String title = DEFAULT_STRING_VALUE;
		private String albumArtist = VARIOUS;
		boolean isCompilation = false;
		List<CdTrack> tracks = new ArrayList<>();
		private CdTrack currentTrack = CdTrack.defaultInstance();
		
		/**	creates a bare bones, default instance */
		public Builder() {
			// just the defaults required
		}
		
		/**
		 * Creates an instance with param title 
		 * @param title the title of the CD
		 */
		public Builder(final String title) {
			this.title = title;
		}
		
		public Builder title(final String title) {
			this.title = title;
			return this;
		}
		
		public Builder artist(final String artist) {
			this.albumArtist = artist;
			return this;
		}
		
		public Builder tracks(final List<CdTrack> tracks) {
			this.tracks = tracks;
			return this;
		}
		
		public Builder track(final CdTrack track) {
			this.tracks.add(track);
			return this;
		}
		
		public static Builder fromBufferedReader(BufferedReader reader) {
			Builder builder = new Builder();
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					builder.updateFromInfoLine(line.trim());
				}
			} catch (IOException excep) {
				// Going to return what we can
				LOGGER.error("Problem reading line from reader");
			}
			builder.tracks.add(builder.currentTrack);
			return builder;
		}
		
		private Builder updateFromInfoLine(final String infoLine) {
			final int GROUP_ORD = 1;
			Matcher trackMatcher = TRACK_TITLE_PATTERN.matcher(infoLine);
			if (trackMatcher.matches()) {
				if (this.currentTrack != CdTrack.DEFAULT) {
					this.tracks.add(this.currentTrack);
				}
				this.currentTrack = CdTrack.fromValues(trackMatcher.group(GROUP_ORD));
				return this;
			}
			Matcher artistMatcher = ARTIST_PATTERN.matcher(infoLine);
			if (artistMatcher.matches()) {
				this.currentTrack = CdTrack.fromValues(this.currentTrack.getTitle(), artistMatcher.group(GROUP_ORD));
			}
			Matcher albumTitleMatcher = ALBUM_TITLE_PATTERN.matcher(infoLine);
			if (albumTitleMatcher.matches()) {
				this.title = albumTitleMatcher.group(GROUP_ORD);
				return this;
			}
			Matcher albumArtistMatcher = ALBUM_ARTIST_PATTERN.matcher(infoLine);
			if (albumArtistMatcher.matches()) {
				this.albumArtist = albumArtistMatcher.group(GROUP_ORD);
				return this;
			}
			return this;
		}

		public CdItemRecord build() {
			return new CdItemRecord(this.title, this.albumArtist, this.tracks);
		}
	}
}
