/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

/**
 * Class encapsulating the disk information returned by the cdrdao disk-info
 * command.
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 8 Oct 2013:11:39:14
 */

public final class CdrdaoDiskInfo {
	/** cdrdao not applicable string */
	public final static String CDRDAO_NA = "n/a";
	/** cdrdao CD-DA or CD-ROM TOC Type string */
	public final static String CDDA_CDROM_TOCTYPE = "n/a";

	/**
	 * Enumeration for known CD table of contents types
	 */
	public enum TocType {
		/** CD-DA or CD-ROM TOC Type */
		CDDA_OR_CDROM(CdrdaoDiskInfo.CDDA_CDROM_TOCTYPE),
		/** Not applicable */
		NA(CdrdaoDiskInfo.CDRDAO_NA);

		private final String value;

		TocType(final String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	/**
	 * Enumaration for CD Media Type
	 */
	public enum MediaType {
		/** CD-RW */
		CD_RW,
		/** CD-R */
		CD_R,
		/** CD */
		CD,
		/** DVD */
		DVD,
		/** Blu Ray */
		BLU_RAY,
		/** Unknown */
		UNKNOWN;
	}

	private final MediaType mediaType;
	private final Capacity capacity;
	private final TocType tocType;
	private final int sessions;
	private final int lastTrack;

	CdrdaoDiskInfo(final MediaType mediaType, final Capacity capacity,
			final TocType tocType, final int sessions, final int lastTrack) {
		this.mediaType = mediaType;
		this.capacity = capacity;
		this.tocType = tocType;
		this.sessions = sessions;
		this.lastTrack = lastTrack;
	}

	/**
	 * @return the enumeration indicating the inserted media type
	 */
	public MediaType getMediaType() {
		return this.mediaType;
	}

	/**
	 * @return a Capacity instance holding details of the meida capcity
	 */
	public Capacity getCapacity() {
		return this.capacity;
	}

	/**
	 * @return the table of contents type
	 */
	public TocType getTocType() {
		return this.tocType;
	}

	/**
	 * @return the sessions
	 */
	public int getSessions() {
		return this.sessions;
	}

	/**
	 * @return the lastTrack
	 */
	public int getLastTrack() {
		return this.lastTrack;
	}

	/**
	 * Builder class for the CdrdaoDiskInfo class
	 * 
	 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl
	 *         Wilson</a>.</p> <a
	 *         href="https://github.com/carlwilson">carlwilson AT
	 *         github</a>.</p>
	 * @version 0.1
	 * 
	 *          Created 8 Oct 2013:12:04:24
	 */
	public static final class Builder {
		private MediaType mediaType = MediaType.CD;
		private Capacity capacity;
		private TocType tocType;
		private int sessions;
		private int lastTrack;

		private Builder() {
			
		}
		/**
		 * @return the final instance
		 */
		public CdrdaoDiskInfo build() {
			return new CdrdaoDiskInfo(this.mediaType, this.capacity,
					this.tocType, this.sessions, this.lastTrack);
		}
	}
}
