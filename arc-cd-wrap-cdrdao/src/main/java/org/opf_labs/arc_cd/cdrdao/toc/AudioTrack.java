/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import com.google.common.base.Preconditions;

/**
 * @author carl
 * 
 */
@SuppressWarnings("static-method")
public final class AudioTrack {
	private final int number;
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
	private AudioTrack(int number, boolean copyPermitted,
			boolean preEmphasisFlag, boolean isTwoChannel,
			boolean isFourChannel, String isrc, String file, String start,
			String length) {
		this.number = number;
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
		return TrackType.AUDIO;
	}

	@Override
	public String toString() {
		return this.file + " track:" + this.number + ", ISRC:" + this.isrc + ", start:" + this.start + ", length: " + this.length + "\n";
	}

	@SuppressWarnings("hiding")
	public static class Builder {
		private int number = 1;
		private boolean copyPermitted = false;
		private boolean preEmphasisFlag = false;
		private boolean isTwoChannel = true;
		private boolean isFourChannel = false;
		private String isrc = "";
		private String file = "";
		private String start = "0";
		private String length = "";

		public Builder(final int number) {
			Preconditions.checkArgument((number > 0) || (number < 100),
					"Track number out of range:" + number);
			this.number = number;
		}

		public Builder copyPermitted(final boolean copyPermitted) {
			this.copyPermitted = copyPermitted;
			return this;
		}

		public Builder preEmphasisFlag(final boolean preEmphasisFlag) {
			this.preEmphasisFlag = preEmphasisFlag;
			return this;
		}

		public Builder isTwoChannel(final boolean isTwoChannel) {
			this.isTwoChannel = isTwoChannel;
			return this;
		}

		public Builder isFourChannel(final boolean isFourChannel) {
			this.isFourChannel = isFourChannel;
			return this;
		}

		public Builder isrc(final String isrc) {
			Preconditions.checkNotNull(isrc, "isrc == null");
			this.isrc = isrc;
			return this;
		}

		public Builder file(final String file) {
			Preconditions.checkNotNull(file, "file == null");
			this.file = file;
			return this;
		}

		public Builder start(final String start) {
			Preconditions.checkNotNull(start, "start is null");
			Preconditions.checkArgument(!start.isEmpty(), "start.isEmpty()");
			this.start = start;
			return this;
		}

		public Builder length(final String length) {
			Preconditions.checkNotNull(length, "length is null");
			this.length = length;
			return this;
		}

		public AudioTrack build() {
			return new AudioTrack(this.number, this.copyPermitted,
					this.preEmphasisFlag, this.isTwoChannel,
					this.isFourChannel, this.isrc, this.file, this.start,
					this.length);
		}
	}
}
