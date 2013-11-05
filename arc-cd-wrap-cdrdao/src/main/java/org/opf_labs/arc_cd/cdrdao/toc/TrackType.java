/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

/**
 * @author carl
 *
 */
public enum TrackType {
	/** Audio Track Type */
	AUDIO(2352),
	/** Mode 1 Track Type */
	MODE1(2048),
	/** Mode 1 Raw Track Type */
	MODE1_RAW(2352),
	/** Mode 2 Track Type */
	MODE2(2336),
	/** Mode 2 Form 1 Track Type */
	MODE2_FORM1(2048),
	/** Mode 2 Form 2 Track Type */
	MODE2_FORM2(2324),
	/** Mode 2 Form Mix Track Type */
	MODE2_FORM_MIX(2336),
	/** Mode 2 Rae Track Type */
	MODE2_RAW(2352);
	
	private final int bytes;
	
	private TrackType(final int bytes) {
		this.bytes = bytes;
	}
	
	/**
	 * @return get the bytes value for the track type
	 */
	public int getBytes() {
		return this.bytes;
	}
}
