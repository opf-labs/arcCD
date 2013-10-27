/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

/**
 * @author carl
 *
 */
public enum TrackType {
	AUDIO(2352),
	MODE1(2048),
	MODE1_RAW(2352),
	MODE2(2336),
	MODE2_FORM1(2048),
	MODE2_FORM2(2324),
	MODE2_FORM_MIX(2336),
	MODE2_RAW(2352);
	
	private final int bytes;
	
	private TrackType(final int bytes) {
		this.bytes = bytes;
	}
	
	public int getBytes() {
		return this.bytes;
	}
}
