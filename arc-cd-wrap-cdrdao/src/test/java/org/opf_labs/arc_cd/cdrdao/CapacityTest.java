/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Test class for Capacity class, just an immutable plus builder so light 
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 8 Oct 2013:13:13:48
 */
public class CapacityTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#isCdRw()}.
	 */
	@Test
	public void testIsCdRwFalse() {
		assertFalse(Capacity.Builder.newAudioDiscInitialised().build().isCdRw());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#isCdRw()}.
	 */
	@Test
	public void testIsCdRwTrue() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().cdRw(true).build().isCdRw());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#isEmptyCdr()}.
	 */
	@Test
	public void testIsEmptyCdrFalse() {
		assertFalse(Capacity.Builder.newAudioDiscInitialised().build().isEmptyCdr());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#isEmptyCdr()}.
	 */
	@Test
	public void testIsEmptyCdrTrue() {
		assertTrue(Capacity.Builder.newBlankCdrInitialised().build().isEmptyCdr());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#isAppendable()}.
	 */
	@Test
	public void testIsAppendableFalse() {
		assertFalse(Capacity.Builder.newAudioDiscInitialised().build().isAppendable());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#isAppendable()}.
	 */
	@Test
	public void testIsAppendableTrue() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().appendable(true).build().isAppendable());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getTemporal()}.
	 */
	@Test
	public void testGetTemporalNa() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().build().getTemporal() == CdrdaoDiskInfo.CDRDAO_NA);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getTemporal()}.
	 */
	@Test
	public void testGetTemporalValue() {
		assertTrue(Capacity.Builder.newBlankCdrInitialised().build().getTemporal() != CdrdaoDiskInfo.CDRDAO_NA);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getBlocks()}.
	 */
	@Test
	public void testGetBlocksAudio() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().build().getBlocks() == 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getBlocks()}.
	 */
	@Test
	public void testGetBlocksCdr() {
		assertTrue(Capacity.Builder.newBlankCdrInitialised().build().getBlocks() != 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getFreeMB()}.
	 */
	@Test
	public void testGetFreeMBAudio() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().build().getFreeMB() == 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getFreeMB()}.
	 */
	@Test
	public void testGetFreeMBCdr() {
		assertTrue(Capacity.Builder.newBlankCdrInitialised().build().getFreeMB() != 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getMaxMB()}.
	 */
	@Test
	public void testGetMaxMBAudio() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().build().getMaxMB() == 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#getMaxMB()}.
	 */
	@Test
	public void testGetMaxMBCdr() {
		assertTrue(Capacity.Builder.newBlankCdrInitialised().build().getMaxMB() != 0);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#toString()}.
	 */
	@Test
	public void testToStringNa() {
		assertTrue(Capacity.Builder.newAudioDiscInitialised().build().toString() == CdrdaoDiskInfo.CDRDAO_NA);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#toString()}.
	 */
	@Test
	public void testToString() {
		assertTrue(Capacity.Builder.newBlankCdrInitialised().build().toString() != CdrdaoDiskInfo.CDRDAO_NA);
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.cdrdao.Capacity#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(Capacity.class).verify();

	}
}
