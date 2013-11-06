/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

/**
 * @author carl
 *
 */
public enum TrackType {
	/** Unknown Track Type */
	UNKNOWN(0, "UNKNOWN"),
	/** Audio Track Type */
	AUDIO(2352, "AUDIO"),
	/** Mode 1 Track Type */
	MODE1(2048, "MODE1"),
	/** Mode 1 Raw Track Type */
	MODE1_RAW(2352, "MODE1"),
	/** Mode 2 Track Type */
	MODE2(2336, "MODE2"),
	/** Mode 2 Form 1 Track Type */
	MODE2_FORM1(2048),
	/** Mode 2 Form 2 Track Type */
	MODE2_FORM2(2324),
	/** Mode 2 Form Mix Track Type */
	MODE2_FORM_MIX(2336),
	/** Mode 2 Raw Track Type */
	MODE2_RAW(2352, "MODE2");
	
	private static final char CUE_SEPARATOR = '/';
	private final int bytes;
	private final String cueSyntax;
	
	private TrackType(final int bytes) {
		this(bytes, "");
	}
	
	private TrackType(final int bytes, final String cueSyntax) {
		this.bytes = bytes;
		this.cueSyntax = cueSyntax;
	}
	
	/**
	 * @return get the bytes value for the track type
	 */
	public int getBytes() {
		return this.bytes;
	}
	
	/**
	 * @return the CUE syntax for the TOC Track Type
	 */
	public String getCueSyntax() {
		if (this.cueSyntax.isEmpty() || (this == AUDIO))
			return this.cueSyntax;
		return this.cueSyntax + CUE_SEPARATOR + this.bytes;
	}
	
	/**
	 * @param tocValue the track type value grabbed from a TOC file
	 * @return the TrackType parsed from the TOC value
	 */
	public static TrackType fromTocValue(String tocValue) {
		for (TrackType type : TrackType.values()) {
			if (type.toString().equals(tocValue)) {
				return type;
			}
		}
		return UNKNOWN;
	}
}
