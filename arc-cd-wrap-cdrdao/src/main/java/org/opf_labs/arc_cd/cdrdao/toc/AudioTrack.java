/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import com.google.common.base.Preconditions;

/**
 * @author carl
 * 
 */
public final class AudioTrack {
	private final int number;
	private final TrackType type;
	private final boolean copyPermitted;
	private final boolean preEmphasisFlag;
	private final boolean isTwoChannel;
	private final boolean isFourChannel;
	private final String isrc;
	private final String file;
	private final String start;
	private final String length;

	/**
	 * @param number
	 * @param copyPermitted
	 * @param preEmphasisFlag
	 * @param isTwoChannel
	 * @param isFourChannel
	 * @param isrc
	 * @param file
	 * @param start
	 * @param length
	 */
	private AudioTrack(int number, TrackType type, boolean copyPermitted,
			boolean preEmphasisFlag, boolean isTwoChannel,
			boolean isFourChannel, String isrc, String file, String start,
			String length) {
		this.number = number;
		this.type = type;
		this.copyPermitted = copyPermitted;
		this.preEmphasisFlag = preEmphasisFlag;
		this.isTwoChannel = isTwoChannel;
		this.isFourChannel = isFourChannel;
		this.isrc = isrc;
		this.file = file;
		this.start = start;
		this.length = length;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * @return the copyPermitted
	 */
	public boolean isCopyPermitted() {
		return this.copyPermitted;
	}

	/**
	 * @return the preEmphasisFlag
	 */
	public boolean isPreEmphasisFlag() {
		return this.preEmphasisFlag;
	}

	/**
	 * @return the isTwoChannel
	 */
	public boolean isTwoChannel() {
		return this.isTwoChannel;
	}

	/**
	 * @return the isFourChannel
	 */
	public boolean isFourChannel() {
		return this.isFourChannel;
	}

	/**
	 * @return the isrc
	 */
	public String getIsrc() {
		return this.isrc;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return this.file;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return this.start;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return this.length;
	}

	/**
	 * @return the track type
	 */
	public TrackType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return this.file + " track:" + this.number + ", ISRC:" + this.isrc + ", start:" + this.start + ", length: " + this.length + "\n";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.copyPermitted ? 1231 : 1237);
		result = prime * result
				+ ((this.file == null) ? 0 : this.file.hashCode());
		result = prime * result + (this.isFourChannel ? 1231 : 1237);
		result = prime * result + (this.isTwoChannel ? 1231 : 1237);
		result = prime * result
				+ ((this.isrc == null) ? 0 : this.isrc.hashCode());
		result = prime * result
				+ ((this.length == null) ? 0 : this.length.hashCode());
		result = prime * result
				+ ((this.type == null) ? 0 : this.type.hashCode());
		result = prime * result + this.number;
		result = prime * result + (this.preEmphasisFlag ? 1231 : 1237);
		result = prime * result
				+ ((this.start == null) ? 0 : this.start.hashCode());
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
		AudioTrack other = (AudioTrack) obj;
		if (this.copyPermitted != other.copyPermitted)
			return false;
		if (this.file == null) {
			if (other.file != null)
				return false;
		} else if (!this.file.equals(other.file))
			return false;
		if (this.isFourChannel != other.isFourChannel)
			return false;
		if (this.isTwoChannel != other.isTwoChannel)
			return false;
		if (this.isrc == null) {
			if (other.isrc != null)
				return false;
		} else if (!this.isrc.equals(other.isrc))
			return false;
		if (this.length == null) {
			if (other.length != null)
				return false;
		} else if (!this.length.equals(other.length))
			return false;
		if (this.type == null) {
			if (other.type != null)
				return false;
		} else if (!(this.type == other.type))
			return false;
		if (this.number != other.number)
			return false;
		if (this.preEmphasisFlag != other.preEmphasisFlag)
			return false;
		if (this.start == null) {
			if (other.start != null)
				return false;
		} else if (!this.start.equals(other.start))
			return false;
		return true;
	}

	/**
	 * A Builder object used to create new AudioTrack instances.
	 * @author carl
	 */
	@SuppressWarnings("hiding")
	public static class Builder {
		private int number = 1;
		private TrackType type = TrackType.AUDIO;
		private boolean copyPermitted = false;
		private boolean preEmphasisFlag = false;
		private boolean isTwoChannel = true;
		private boolean isFourChannel = false;
		private String isrc = "";
		private String file = "";
		private String start = "0";
		private String length = "";

		/**
		 * Create a new Builder with the passed track number
		 * @param number the track number 
		 */
		public Builder(final int number) {
			Preconditions.checkArgument((number > 0) || (number < 100),
					"Track number out of range:" + number);
			this.number = number;
		}

		/**
		 * @param type the new TrackType value
		 * @return this builder for chaining
		 */
		public Builder type(final TrackType type) {
			Preconditions.checkNotNull(type, "type == null");
			this.type = type;
			return this;
		}
		/**
		 * @param copyPermitted a new copyPermitted flag value
		 * @return this Builder object for chaining
		 */
		public Builder copyPermitted(final boolean copyPermitted) {
			this.copyPermitted = copyPermitted;
			return this;
		}

		/**
		 * @param preEmphasisFlag a new preEmphasisFlag flag value
		 * @return this Builder object for chaining
		 */
		public Builder preEmphasisFlag(final boolean preEmphasisFlag) {
			this.preEmphasisFlag = preEmphasisFlag;
			return this;
		}

		/**
		 * @param isTwoChannel a new isTwoChannel flag value
		 * @return this Builder object for chaining
		 */
		public Builder isTwoChannel(final boolean isTwoChannel) {
			this.isTwoChannel = isTwoChannel;
			return this;
		}

		/**
		 * @param isFourChannel a new isFourChannel flag value
		 * @return this Builder object for chaining
		 */
		public Builder isFourChannel(final boolean isFourChannel) {
			this.isFourChannel = isFourChannel;
			return this;
		}

		/**
		 * @param isrc an International Standard Recording Number for the track
		 * @return this Builder object for chaining
		 */
		public Builder isrc(final String isrc) {
			Preconditions.checkNotNull(isrc, "isrc == null");
			this.isrc = isrc;
			return this;
		}

		/**
		 * @param file the file value for the track
		 * @return this Builder object for chaining
		 */
		public Builder file(final String file) {
			Preconditions.checkNotNull(file, "file == null");
			this.file = file;
			return this;
		}

		/**
		 * @param start set the Track start
		 * @return this Builder object for chaining
		 */
		public Builder start(final String start) {
			Preconditions.checkNotNull(start, "start is null");
			Preconditions.checkArgument(!start.isEmpty(), "start.isEmpty()");
			this.start = start;
			return this;
		}

		/**
		 * @param length set the Track length
		 * @return this Builder object for chaining
		 */
		public Builder length(final String length) {
			Preconditions.checkNotNull(length, "length is null");
			this.length = length;
			return this;
		}

		/**
		 * Create a new AudioTrack instance from the Builder values
		 * @return the created AudioTrack
		 */
		public AudioTrack build() {
			return new AudioTrack(this.number, this.type, this.copyPermitted,
					this.preEmphasisFlag, this.isTwoChannel,
					this.isFourChannel, this.isrc, this.file, this.start,
					this.length);
		}
	}
}
