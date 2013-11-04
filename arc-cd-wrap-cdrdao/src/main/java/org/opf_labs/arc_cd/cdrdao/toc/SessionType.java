/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

/**
 * @author carl
 *
 */
public enum SessionType {
	CD_DA("CD_DA"),
	CD_ROM("CD_ROM"),
	CD_ROM_XA("CD_ROM_XA");
	
	private final String text;
	private SessionType(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
