/**
 * 
 */
package org.opf_labs.arc_cd.mmc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.opf_labs.arc_cd.mmc.CollectionManagerConfig.Builder;

/**
 * TODO JavaDoc for ArcCdOptionsParser.</p> TODO Tests for
 * ArcCdOptionsParser.</p> TODO Implementation for ArcCdOptionsParser.</p>
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 1 Oct 2013:11:13:35
 */

public final class CollectionManagerOptionsParser {
	// Constants for CLI Options
	private static final String MANIFEST_OPT = "manifest";
	private static final String MANIFEST_OPT_DESC = "Check all item manifests, generates MD5s and takes some time.";
	private static final String CUE_OPT = "cue";
	private static final String CUE_OPT_DESC = "Generates missing CUE files, and adds them to the manifests.";
	private static final String ROOT_OPT = "root";
	private static final String ROOT_OPT_ARG = "Collections root";
	private static final String ROOT_OPT_DESC = "Root directory for Archive Collections, defaults to ~/Music.";
	private static final String HELP_OPT = "help";
	private static final String HELP_OPT_DESC = "Print this message";
	private static final String NAME_OPT = "name";
	private static final String NAME_OPT_ARG = "Collection name";
	private static final String NAME_OPT_DESC = "Name of collection defaults to arccd, this with no root option will make make ~/Music/<name> the archive root.";

	// Create the options object
	private static final Options OPTIONS = new Options();
	static {
		Option help = new Option(HELP_OPT, HELP_OPT_DESC);
		Option manifest = new Option(MANIFEST_OPT, MANIFEST_OPT_DESC);
		Option cue = new Option(CUE_OPT, CUE_OPT_DESC);
		OptionBuilder.withArgName(ROOT_OPT_ARG);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(ROOT_OPT_DESC);
		Option root = OptionBuilder.create(ROOT_OPT);
		OptionBuilder.withArgName(NAME_OPT_ARG);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(NAME_OPT_DESC);
		Option name = OptionBuilder.create(NAME_OPT);
		OPTIONS.addOption(help);
		OPTIONS.addOption(manifest);
		OPTIONS.addOption(cue);
		OPTIONS.addOption(root);
		OPTIONS.addOption(name);
	}

	/**
	 * 
	 * @param args
	 * @return an ArcCdConfig instance populated from the passed arguments
	 * @throws ParseException when there's an error parsing the arguments
	 */
	public static CollectionManagerConfig parseConfigFromCommandLineArgs(String[] args) throws ParseException {
		CommandLineParser cmdParser = new GnuParser();
		CommandLine parsedArgs = cmdParser.parse(OPTIONS, args);
		CollectionManagerConfig.Builder builder = new Builder();

		builder.helpRequested(parsedArgs.hasOption(HELP_OPT));
		builder.generateCues(parsedArgs.hasOption(CUE_OPT));
		builder.checkManifests(parsedArgs.hasOption(MANIFEST_OPT));
		if (parsedArgs.hasOption(ROOT_OPT)) {
			builder.root(parsedArgs.getOptionValue(ROOT_OPT));
		}
		if (parsedArgs.hasOption(NAME_OPT)) {
			builder.name(parsedArgs.getOptionValue(NAME_OPT));
		}
		return builder.build();
	}

	/**
	 * @return the options object, needed to parse the help message 
	 */
	public static Options getOptions() {
		return OPTIONS;
	}
}
