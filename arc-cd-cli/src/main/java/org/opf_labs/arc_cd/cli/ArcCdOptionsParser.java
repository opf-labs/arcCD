/**
 * 
 */
package org.opf_labs.arc_cd.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.opf_labs.arc_cd.config.ArcCdConfig;
import org.opf_labs.arc_cd.config.ArcCdConfig.Builder;

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

final class ArcCdOptionsParser {
	// Constants for CLI Options
	private static final String ROOT_OPT = "root";
	private static final String ROOT_OPT_ARG = "Archive root";
	private static final String ROOT_OPT_DESC = "Root directory for cd archive, defaults to ~/Music.";
	private static final String HELP_OPT = "help";
	private static final String HELP_OPT_DESC = "Print this message";
	private static final String TEST_OPT = "test";
	private static final String TEST_OPT_DESC = "Run in non-interactive mode for testing.";
	private static final String CDRDAO_OPT = "cdr";
	private static final String CDRDAO_OPT_ARG = "cdrdao path";
	private static final String CDRDAO_OPT_DESC = "Path to cdrdao application, only required for windows, but will try ./bin.\nOn *nix systems install cdrdao package, e.g. sudo apt-get install cdrdao.\nOn OSX use Mac ports or homebrew to install cdrdao.";
	private static final String NAME_OPT = "name";
	private static final String NAME_OPT_ARG = "Collection name";
	private static final String NAME_OPT_DESC = "Name of collection defaults to arccd, this with no root option will make make ~/Music/<name> the archive root.";

	// Create the options object
	private static final Options OPTIONS = new Options();
	static {
		Option help = new Option(HELP_OPT, HELP_OPT_DESC);
		Option test = new Option(TEST_OPT, TEST_OPT_DESC);
		OptionBuilder.withArgName(ROOT_OPT_ARG);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(ROOT_OPT_DESC);
		Option root = OptionBuilder.create(ROOT_OPT);
		OptionBuilder.withArgName(NAME_OPT_ARG);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(NAME_OPT_DESC);
		Option name = OptionBuilder.create(NAME_OPT);
		OptionBuilder.withArgName(CDRDAO_OPT_ARG);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(CDRDAO_OPT_DESC);
		Option cdrdaoPath = OptionBuilder.create(CDRDAO_OPT);
		OPTIONS.addOption(help);
		OPTIONS.addOption(test);
		OPTIONS.addOption(root);
		OPTIONS.addOption(name);
		OPTIONS.addOption(cdrdaoPath);
	}

	/**
	 * 
	 * @param args
	 * @return an ArcCdConfig instance populated from the passed arguments
	 * @throws ParseException when there's an error parsing the arguments
	 */
	public static ArcCdConfig parseConfigFromCommandLineArgs(String[] args) throws ParseException {
		CommandLineParser cmdParser = new GnuParser();
		CommandLine parsedArgs = cmdParser.parse(OPTIONS, args);
		ArcCdConfig.Builder builder = new Builder();

		builder.helpRequested(parsedArgs.hasOption(HELP_OPT));
		if (parsedArgs.hasOption(ROOT_OPT)) {
			builder.root(parsedArgs.getOptionValue(ROOT_OPT));
		}
		if (parsedArgs.hasOption(NAME_OPT)) {
			builder.name(parsedArgs.getOptionValue(NAME_OPT));
		}
		if (parsedArgs.hasOption(CDRDAO_OPT)) {
			builder.cdrdao(parsedArgs.getOptionValue(CDRDAO_OPT));
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
