/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import static org.junit.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;
import org.opf_labs.arc_cd.cdrdao.toc.AudioTrack.Builder;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class AudioTrackTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.AudioTrack#getNumber()}.
	 */
	@Test
	public final void testGetNumber() {
		AudioTrack toTest = new Builder(1).build();
		assertTrue(toTest.getNumber() ==  1);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.toc.AudioTrack#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		EqualsVerifier.forClass(AudioTrack.class).verify();
	}

}
