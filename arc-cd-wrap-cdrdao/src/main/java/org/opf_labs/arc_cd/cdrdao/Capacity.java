/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import com.google.common.base.Preconditions;

/**
 * Encapsulates the recordable media details gathered by cdrdao. Immutable class
 * with builder.
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 8 Oct 2013:12:10:52
 */

public final class Capacity {
	/** The default instance is an audio cd also */
	public static final Capacity DEFAULT = new Capacity();
	/** The default instance is an audio cd also */
	public static final Capacity AUDIO_DEFAULT = DEFAULT;
	private final boolean isCdRw;
	private final boolean isEmptyCdr;
	private final boolean isAppendable;
	private final String temporal;
	private final int blocks;
	private final int freeMB;
	private final int maxMB;

	private Capacity() {
		this(false, false, false, CdrdaoDiskInfo.CDRDAO_NA, 0, 0, 0);
	}
	Capacity(final boolean isCdRw, final boolean isEmptyCdr,
			final boolean isAppendable, final String duration,
			final int blocks, final int freeMB, final int maxMB) {
		this.isCdRw = isCdRw;
		this.isEmptyCdr = isEmptyCdr;
		this.isAppendable = isAppendable;
		this.temporal = duration;
		this.blocks = blocks;
		this.freeMB = freeMB;
		this.maxMB = maxMB;
	}

	/**
	 * @return the true if media is a CD-RW disc
	 */
	public boolean isCdRw() {
		return this.isCdRw;
	}

	/**
	 * @return the true if the media is a blank CD-R
	 */
	public boolean isEmptyCdr() {
		return this.isEmptyCdr;
	}

	/**
	 * @return the true if the media is appendable
	 */
	public boolean isAppendable() {
		return this.isAppendable;
	}

	/**
	 * TODO look at duration format
	 * 
	 * @return the capacity of the disk in mm:ss:?? format
	 */
	public String getTemporal() {
		return this.temporal;
	}

	/**
	 * @return the number of free blocks on the media
	 */
	public int getBlocks() {
		return this.blocks;
	}

	/**
	 * @return the free space in MB on the media
	 */
	public int getFreeMB() {
		return this.freeMB;
	}

	/**
	 * @return the theoretical capacity of the cdr
	 */
	public int getMaxMB() {
		return this.maxMB;
	}

	@Override
	public String toString() {
		String toString = this.temporal;
		if (this.hasCapacity()) {
			toString += " (" + this.blocks + " blocks, " + this.freeMB
					+ "/" + this.maxMB + " MB)";
		}
		return toString; 
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.blocks;
		result = prime * result + this.freeMB;
		result = prime * result + (this.isAppendable ? 1231 : 1237);
		result = prime * result + (this.isCdRw ? 1231 : 1237);
		result = prime * result + (this.isEmptyCdr ? 1231 : 1237);
		result = prime * result + this.maxMB;
		result = prime * result
				+ ((this.temporal == null) ? 0 : this.temporal.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Capacity)) {
			return false;
		}
		Capacity other = (Capacity) obj;
		if (this.blocks != other.blocks) {
			return false;
		}
		if (this.freeMB != other.freeMB) {
			return false;
		}
		if (this.isAppendable != other.isAppendable) {
			return false;
		}
		if (this.isCdRw != other.isCdRw) {
			return false;
		}
		if (this.isEmptyCdr != other.isEmptyCdr) {
			return false;
		}
		if (this.maxMB != other.maxMB) {
			return false;
		}
		if (this.temporal == null) {
			if (other.temporal != null) {
				return false;
			}
		} else if (!this.temporal.equals(other.temporal)) {
			return false;
		}
		return true;
	}

	private boolean hasCapacity() {
		return (this.blocks > 0);
	}

	/**
	 * Simple builder for capacity instances
	 */
	@SuppressWarnings("hiding")
	public static final class Builder {
		private static final int CDR_FREE_BLOCKS = 359845;
		private static final int CDR_FREE_MB = 702;
		private static final int CDR_MAX_MB = 807;
		private static final String CDR_TEMPORAL = "79:57:70";

		private boolean isCdRw = false;
		private boolean isEmptyCdr = false;
		private boolean isAppendable = false;
		private String temporal = CdrdaoDiskInfo.CDRDAO_NA;
		private int blocks = 0;
		private int freeMB = 0;
		private int maxMB = 0;

		private Builder() {
			// Do nothing
		}
		/**
		 * @param toCopy a Capacity object used to initialise the Builder instace
		 */
		private Builder(final Capacity toCopy) {
			this.isCdRw = toCopy.isCdRw();
			this.isEmptyCdr = toCopy.isEmptyCdr();
			this.isAppendable = toCopy.isAppendable();
			this.temporal = toCopy.getTemporal();
			this.blocks = toCopy.getBlocks();
			this.freeMB = toCopy.getFreeMB();
			this.maxMB = toCopy.getMaxMB();
		}

		/**
		 * @param isCdRw
		 *            the isCdRw to set
		 * @return the builder instance
		 */
		public Builder cdRw(boolean isCdRw) {
			this.isCdRw = isCdRw;
			return this;
		}

		/**
		 * @param isEmptyCdr
		 *            the isEmptyCdr to set
		 * @return the builder instance
		 */
		public Builder emptyCdr(boolean isEmptyCdr) {
			this.isEmptyCdr = isEmptyCdr;
			return this;
		}

		/**
		 * @param isAppendable
		 *            the isAppendable to set
		 * @return the builder instance
		 */
		public Builder appendable(boolean isAppendable) {
			this.isAppendable = isAppendable;
			return this;
		}

		/**
		 * @param temporal
		 *            the temporal to set
		 * @return the builder instance
		 */
		public Builder temporal(String temporal) {
			Preconditions.checkNotNull(temporal, "temporal == null");
			Preconditions.checkArgument(!temporal.isEmpty(), "temporal,isEmpty()");
			this.temporal = temporal;
			return this;
		}

		/**
		 * @param blocks
		 *            the blocks to set
		 * @return the builder instance
		 */
		public Builder blocks(int blocks) {
			Preconditions.checkArgument((blocks >= 0), "blocks < 0");
			this.blocks = blocks;
			return this;
		}

		/**
		 * @param freeMB
		 *            the freeMB to set
		 * @return the builder instance
		 */
		public Builder freeMB(int freeMB) {
			Preconditions.checkArgument((freeMB >= 0), "freeMB < 0");
			this.freeMB = freeMB;
			return this;
		}

		/**
		 * @param maxMB
		 *            the maxMB to set
		 * @return the builder instance
		 */
		public Builder maxMB(int maxMB) {
			Preconditions.checkArgument((maxMB >= 0), "maxMB < 0");
			this.maxMB = maxMB;
			return this;
		}

		/**
		 * @return the initialised capacity object
		 */
		public Capacity build() {
			return new Capacity(this.isCdRw, this.isEmptyCdr,
					this.isAppendable, this.temporal, this.blocks, this.freeMB,
					this.maxMB);
		}

		/**
		 * @return a new Builder instance populated as an non recordable audio
		 *         disk
		 */
		public static Builder newAudioDiscInitialised() {
			return new Builder();
		}

		/**
		 * @return a new Builder instance populated as an non recordable audio
		 *         disk
		 */
		public static Builder newBlankCdrInitialised() {
			return new Builder().emptyCdr(true).blocks(CDR_FREE_BLOCKS)
					.temporal(CDR_TEMPORAL).freeMB(CDR_FREE_MB)
					.maxMB(CDR_MAX_MB);
		}
		
		/**
		 * @param capacity the Capacity object to copy
		 * @return a Builder instance initialised from capacity
		 */
		public static Builder copyCapacity(final Capacity capacity) {
			Preconditions.checkNotNull(capacity, "capacity == null");
			return new Builder(capacity);
		}
	}
}
