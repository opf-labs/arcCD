/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

/**
 * @author carl
 * 
 */
public final class ItemManifest {
	private static final String INFO_HEADER = "info:";
	private static final String TOC_HEADER = "toc:";
	private static final String BIN_HEADER = "bin:";
	private static final String CUE_HEADER = "cue:";
	static final String EMPTY_MD5 = DigestUtils.md5Hex("");
	/** Default instance with MD5s of empty string for all field values */
	public static final ItemManifest DEFAULT = new ItemManifest();
	private static final Logger LOGGER = Logger.getLogger(ItemManifest.class); 

	private final String infoMD5;
	private final String tocMD5;
	private final String binMD5;
	private final String cueMD5;

	private ItemManifest() {
		this(EMPTY_MD5, EMPTY_MD5, EMPTY_MD5, EMPTY_MD5);
	}

	private ItemManifest(final String infoMD5, final String tocMD5,
			final String binMD5, final String cueMD5) {
		this.infoMD5 = infoMD5;
		this.tocMD5 = tocMD5;
		this.binMD5 = binMD5;
		this.cueMD5 = cueMD5;
	}

	/**
	 * @return the infoMD5
	 */
	public String getInfoMD5() {
		return this.infoMD5;
	}

	/**
	 * @return the tocMD5
	 */
	public String getTocMD5() {
		return this.tocMD5;
	}

	/**
	 * @return the binMD5
	 */
	public String getBinMD5() {
		return this.binMD5;
	}

	/**
	 * @return the cueMD5
	 */
	public String getCueMD5() {
		return this.cueMD5;
	}

	/**
	 * @return the default ItemManifest object used for handling missing lookups and for testing
	 */
	public static ItemManifest defaultInstance() {
		return DEFAULT;
	}

	/**
	 * Create a new ItemManifest from the files in it's directory
	 * @param item the ArchiveItem to generate a new Manifest for
	 * @return the ItemManifest generated from the directory
	 */
	public static ItemManifest fromItemDirectory(final ArchiveItem item) {
		Preconditions.checkNotNull(item, "item == null");
		Preconditions.checkNotNull(item.getRootDirectory(), "item.getRootDirectory() == null");
		Preconditions.checkArgument(item.getRootDirectory().isDirectory(), "item.getRootDirectory() is not a directory.");
		
		Builder itemBuilder = new Builder();
		try {
			if (item.hasInfo()) {
				itemBuilder.infoMD5(DigestUtils.md5Hex(new FileInputStream(item
						.getInfoFile())));
			}
			if (item.hasToc()) {
				itemBuilder.tocMD5(DigestUtils.md5Hex(new FileInputStream(item
						.getTocFile())));
			}
			if (item.hasBin()) {
				itemBuilder.binMD5(DigestUtils.md5Hex(new FileInputStream(item
						.getBinFile())));
			}
			if (item.hasCue()) {
				itemBuilder.cueMD5(DigestUtils.md5Hex(new FileInputStream(item
						.getCueFile())));
			}
		} catch (IOException excep) {
			// This shouldn't happen so log and use an empty builder to flag problems
		}
		return itemBuilder.build();
	}
	
	/**
	 * Create a new ItemManifest from the manifest file
	 * @param manifestFile the manifest file
	 * @return an ItemManifest created from the manifest file
	 */
	public static ItemManifest fromManifestFile(File manifestFile) {
		Preconditions.checkNotNull(manifestFile, "manifestFile == null");
		Preconditions.checkArgument(manifestFile.isFile(), "manifestFile.isFile() != true");
		Builder builder = new Builder();
		try (BufferedReader br = new BufferedReader(new FileReader(manifestFile))) {
			builder = Builder.fromBufferedReader(br);
		} catch (IOException excep) {
			// Going to return what we can
			LOGGER.warn("Problem closing manifest file.");
		}
		return builder.build();
	}
	
	/**
	 * Create a new ItemManifest from a BufferedReader
	 * @param br the BufferedReader to use
	 * @return the ItemManifest created from the BufferedReader contents
	 */
	public static ItemManifest fromBufferedReader(BufferedReader br) {
		return Builder.fromBufferedReader(br).build();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.binMD5 == null) ? 0 : this.binMD5.hashCode());
		result = prime * result
				+ ((this.cueMD5 == null) ? 0 : this.cueMD5.hashCode());
		result = prime * result
				+ ((this.infoMD5 == null) ? 0 : this.infoMD5.hashCode());
		result = prime * result
				+ ((this.tocMD5 == null) ? 0 : this.tocMD5.hashCode());
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
		ItemManifest other = (ItemManifest) obj;
		if (this.binMD5 == null) {
			if (other.binMD5 != null)
				return false;
		} else if (!this.binMD5.equals(other.binMD5))
			return false;
		if (this.cueMD5 == null) {
			if (other.cueMD5 != null)
				return false;
		} else if (!this.cueMD5.equals(other.cueMD5))
			return false;
		if (this.infoMD5 == null) {
			if (other.infoMD5 != null)
				return false;
		} else if (!this.infoMD5.equals(other.infoMD5))
			return false;
		if (this.tocMD5 == null) {
			if (other.tocMD5 != null)
				return false;
		} else if (!this.tocMD5.equals(other.tocMD5))
			return false;
		return true;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(INFO_HEADER + this.infoMD5 + "\n");
		builder.append(TOC_HEADER + this.tocMD5 + "\n");
		builder.append(BIN_HEADER + this.binMD5 + "\n");
		builder.append(CUE_HEADER + this.cueMD5 + "\n");
		return builder.toString();
	}
	
	/**
	 * A Builder class for an ItemManifest
	 */
	public static class Builder {
		private String infoMD5 = EMPTY_MD5;
		private String tocMD5 = EMPTY_MD5;
		private String binMD5 = EMPTY_MD5;
		private String cueMD5 = EMPTY_MD5;

		/**
		 * @param md5 the MD5 hex value of the info file
		 * @return the builder object
		 */
		public Builder infoMD5(final String md5) {
			Preconditions.checkNotNull(md5, "md5 == null");
			Preconditions
					.checkArgument(!md5.isEmpty(), "md5.isEmpty() == true");
			this.infoMD5 = md5;
			return this;
		}

		/**
		 * @param md5 the MD5 hex value of the TOC file
		 * @return the builder object
		 */
		public Builder tocMD5(final String md5) {
			Preconditions.checkNotNull(md5, "md5 == null");
			Preconditions
					.checkArgument(!md5.isEmpty(), "md5.isEmpty() == true");
			this.tocMD5 = md5;
			return this;
		}

		/**
		 * @param md5 the MD5 hex value of the bin file
		 * @return the builder object
		 */
		public Builder binMD5(final String md5) {
			Preconditions.checkNotNull(md5, "md5 == null");
			Preconditions
					.checkArgument(!md5.isEmpty(), "md5.isEmpty() == true");
			this.binMD5 = md5;
			return this;
		}

		/**
		 * @param md5 the MD5 hex value of the cue file
		 * @return the builder object
		 */
		public Builder cueMD5(final String md5) {
			Preconditions.checkNotNull(md5, "md5 == null");
			Preconditions
					.checkArgument(!md5.isEmpty(), "md5.isEmpty() == true");
			this.cueMD5 = md5;
			return this;
		}

		/**
		 * @return the ItemManifest created from the Builder fields
		 */
		public ItemManifest build() {
			return new ItemManifest(this.infoMD5, this.tocMD5, this.binMD5,
					this.cueMD5);
		}
		/**
		 * @param reader the reader to create the Builder from 
		 * @return the Builder created from the BufferedReader contents
		 */
		public static Builder fromBufferedReader(BufferedReader reader) {
			Builder builder = new Builder();
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					builder.parseManifestLine(line.trim());
				}
			} catch (IOException excep) {
				// Going to return what we can
				LOGGER.warn("Problem reading line from reader");
			}
			return builder;
		}

		private void parseManifestLine(final String line) {
			if (line.startsWith(INFO_HEADER)) {
				this.infoMD5(line.substring(INFO_HEADER.length()));
			} else if (line.startsWith(TOC_HEADER)) {
				this.tocMD5(line.substring(TOC_HEADER.length()));
			} else if (line.startsWith(BIN_HEADER)) {
				this.binMD5(line.substring(BIN_HEADER.length()));
			} else if (line.startsWith(CUE_HEADER)) {
				this.cueMD5(line.substring(CUE_HEADER.length()));
			}
		}

	}
}
