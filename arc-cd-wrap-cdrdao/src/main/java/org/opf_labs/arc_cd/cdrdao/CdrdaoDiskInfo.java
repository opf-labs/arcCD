/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import com.google.common.base.Preconditions;

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
	@SuppressWarnings("hiding")
	public static final class Builder {
		private MediaType mediaType = MediaType.CD;
		private Capacity capacity = Capacity.Builder.newAudioDiscInitialised()
				.build();
		private TocType tocType = TocType.CDDA_OR_CDROM;
		private int sessions = 1;
		private int lastTrack = 1;

		private Builder() {

		}

		/**
		 * @param mediaType
		 *            the media type of the inserted cd
		 * @return this Builder instance
		 */
		public Builder mediaType(final MediaType mediaType) {
			Preconditions.checkNotNull(mediaType, "mediaType == null");
			this.mediaType = mediaType;
			return this;
		}

		/**
		 * @param capacity
		 *            the capacity of any recordable media
		 * @return this Builder instance
		 */
		public Builder capacity(final Capacity capacity) {
			Preconditions.checkNotNull(capacity, "capacity == null");
			this.capacity = capacity;
			return this;
		}

		/**
		 * @param tocType
		 *            the table of contents type for the disc
		 * @return this Builder instance
		 */
		public Builder tocType(final TocType tocType) {
			Preconditions.checkNotNull(tocType, "tocType == null");
			this.tocType = tocType;
			return this;
		}

		/**
		 * @param sessions
		 *            the number of sessions on the media
		 * @return this Builder instance
		 */
		public Builder sessions(final int sessions) {
			Preconditions.checkArgument(sessions >= 0, "sessions < 0");
			this.sessions = sessions;
			return this;
		}

		/**
		 * @param lastTrack
		 *            the number of the last track on the media
		 * @return this Builder instance
		 */
		public Builder lastTrack(final int lastTrack) {
			Preconditions.checkArgument(lastTrack >= 0, "lastTrack < 0");
			this.lastTrack = lastTrack;
			return this;
		}

		/**
		 * @return a Builder instance initialised for an audio cd
		 */
		public static Builder getAudioCdInitialised() {
			return new Builder();
		}

		/**
		 * @param lastTrack
		 *            the number of the last track on the media
		 * @return a Builder instance initialised for an audio cd
		 */
		public static Builder getAudioCdInitialised(final int lastTrack) {
			return getAudioCdInitialised().lastTrack(lastTrack);
		}

		/**
		 * @param lastTrack
		 *            the number of the last track on the media
		 * @param sessions
		 *            the number of sessions on the media
		 * @return a Builder instance initialised for an audio cd
		 */
		public static Builder getAudioCdInitialised(final int lastTrack,
				final int sessions) {
			return getAudioCdInitialised(lastTrack).sessions(sessions);
		}

		/**
		 * @return a Builder instance initialised for an audio cd
		 */
		public static Builder getCdrInitialised() {
			return getAudioCdInitialised(0, 0)
					.mediaType(MediaType.CD_R)
					.capacity(Capacity.Builder.newBlankCdrInitialised().build());
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
