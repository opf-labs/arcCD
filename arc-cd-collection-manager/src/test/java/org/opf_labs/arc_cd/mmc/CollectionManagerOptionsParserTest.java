/**
 * 
 */
package org.opf_labs.arc_cd.mmc;

import static org.junit.Assert.*;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

/**
 * @author carl
 *
 */
@SuppressWarnings("static-method")
public class CollectionManagerOptionsParserTest {

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#parseConfigFromCommandLineArgs(java.lang.String[])}.
	 * @throws ParseException 
	 */
	@Test
	public final void testParseConfigFromDefaultCommandLineArgs() throws ParseException {
		CollectionManagerConfig config = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(new String[]{});
		assertTrue(config.getArchiveRoot().equals(CollectionManagerConfig.ROOT_DEFAULT));
		assertTrue(config.getName().equals(CollectionManagerConfig.NAME_DEFAULT));
		assertFalse(config.helpRequested());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#parseConfigFromCommandLineArgs(java.lang.String[])}.
	 * @throws ParseException 
	 */
	@Test
	public final void testParseConfigFromCommandLineArgsHelp() throws ParseException {
		CollectionManagerConfig config = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(new String[]{"--help"});
		assertTrue(config.getArchiveRoot().equals(CollectionManagerConfig.ROOT_DEFAULT));
		assertTrue(config.getName().equals(CollectionManagerConfig.NAME_DEFAULT));
		assertTrue(config.helpRequested());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#parseConfigFromCommandLineArgs(java.lang.String[])}.
	 * @throws ParseException 
	 */
	@Test
	public final void testParseConfigFromCommandLineArgsName() throws ParseException {
		CollectionManagerConfig config = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(new String[]{"--name", "test"});
		assertTrue(config.getArchiveRoot().equals(CollectionManagerConfig.ROOT_DEFAULT));
		assertFalse(config.getName().equals(CollectionManagerConfig.NAME_DEFAULT));
		assertFalse(config.helpRequested());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#parseConfigFromCommandLineArgs(java.lang.String[])}.
	 * @throws ParseException 
	 */
	@Test
	public final void testParseConfigFromCommandLineArgsRoot() throws ParseException {
		CollectionManagerConfig config = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(new String[]{"--root", "test"});
		assertFalse(config.getArchiveRoot().equals(CollectionManagerConfig.ROOT_DEFAULT));
		assertTrue(config.getName().equals(CollectionManagerConfig.NAME_DEFAULT));
		assertFalse(config.helpRequested());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#parseConfigFromCommandLineArgs(java.lang.String[])}.
	 * @throws ParseException 
	 */
	@Test
	public final void testParseConfigFromCommandLineArgsCue() throws ParseException {
		CollectionManagerConfig config = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(new String[]{"--cue"});
		assertTrue(config.getArchiveRoot().equals(CollectionManagerConfig.ROOT_DEFAULT));
		assertTrue(config.getName().equals(CollectionManagerConfig.NAME_DEFAULT));
		assertFalse(config.helpRequested());
		assertTrue(config.generateCues());
		assertFalse(config.checkManifests());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#parseConfigFromCommandLineArgs(java.lang.String[])}.
	 * @throws ParseException 
	 */
	@Test
	public final void testParseConfigFromCommandLineArgsManifest() throws ParseException {
		CollectionManagerConfig config = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(new String[]{"--manifest"});
		assertTrue(config.getArchiveRoot().equals(CollectionManagerConfig.ROOT_DEFAULT));
		assertTrue(config.getName().equals(CollectionManagerConfig.NAME_DEFAULT));
		assertFalse(config.helpRequested());
		assertTrue(config.checkManifests());
		assertFalse(config.generateCues());
	}

	/**
	 * Test method for {@link org.opf_labs.arc_cd.mmc.CollectionManagerOptionsParser#getOptions()}.
	 * @throws ParseException 
	 */
	@Test
	public final void testGetOptions() throws ParseException {
		Options options = CollectionManagerOptionsParser.getOptions();
		assertTrue(options.hasOption("help"));
		assertTrue(options.hasOption("root"));
		assertTrue(options.hasOption("name"));
	}

}
