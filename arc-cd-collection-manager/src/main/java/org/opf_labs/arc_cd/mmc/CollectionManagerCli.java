/**
 * 
 */
package org.opf_labs.arc_cd.mmc;

import java.io.File;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.opf_labs.arc_cd.collection.ArchiveCollection;
import org.opf_labs.arc_cd.collection.ArchiveItem;
import org.opf_labs.arc_cd.collection.ManifestTest;
import org.opf_labs.arc_cd.collection.ManifestTest.Result;

/**
 * @author carl
 *
 */
public final class CollectionManagerCli {
	/** OK status */
	public static final int OK_STATUS = 0;
	/** Error status */
	public static final int ERROR_STATUS = 1;
	private final static Logger LOGGER = Logger.getLogger(CollectionManagerCli.class);
	private static CollectionManagerConfig CONFIG = CollectionManagerConfig.getDefault();
	/** The collection object */
	private final ArchiveCollection collection;
	
	private CollectionManagerCli(ArchiveCollection collection) {
		this.collection = collection;
	}
	
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Replace basic logging configuration
		BasicConfigurator.configure();
		if (args.length == 0) {
			outputHelpAndExit(OK_STATUS);
		}
		setupConfiguration(args);
		CollectionManagerCli manager = new CollectionManagerCli(new ArchiveCollection(CONFIG.getCollectionRoot()));
		if (CONFIG.generateCues()) {
			manager.generateCues();
		}
		if (CONFIG.checkManifests()) {
			manager.checkManifests();
		}
	}

	private static void setupConfiguration(final String args[]) {
		parseConfiguration(args);
		checkHelpRequested();
		validateConfiguration();
	}

	private static void parseConfiguration(final String args[]) {
		try {
			CONFIG = CollectionManagerOptionsParser.parseConfigFromCommandLineArgs(args);
		} catch (ParseException excep) {
			LOGGER.fatal("Fatal exception parsing command line arguments.");
			LOGGER.fatal(excep.getStackTrace());
			LOGGER.fatal(excep.getMessage());
			outputHelpAndExit(ERROR_STATUS);
		}
	}

	private static void checkHelpRequested() {
		if (CONFIG.helpRequested()) {
			outputHelpAndExit(OK_STATUS);
		}
	}

	private static void validateConfiguration() {
		if (!ensureCollectionRootExists()) {
			logFatalMessageAndTerminateWithCode("Collection root directory "
					+ CONFIG.getCollectionRoot() + " couldn't be created.",
					ERROR_STATUS);
		}
	}

	private static boolean ensureCollectionRootExists() {
		File collectionRoot = new File(CONFIG.getCollectionRoot());

		if (collectionRoot.isFile()) {
			LOGGER.error("Collection root:" + CONFIG.getCollectionRoot()
					+ " should be an existing directory NOT a file.");
			return false;
		} else if (!collectionRoot.exists()) {
			return collectionRoot.mkdirs();
		}
		return true;
	}

	private static void outputHelpAndExit(final int status) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("arccd-collection-mananger", CollectionManagerOptionsParser.getOptions());
		System.exit(status);
	}

	private static void logFatalMessageAndTerminateWithCode(
			final String message, final int exitCode) {
		LOGGER.fatal(message);
		System.exit(exitCode);
	}
	
	private void generateCues() {
		for (ArchiveItem item : this.collection.getArchiveItems()) {
			System.out.println("Generating cue for item:" + item.getId() + item.getInfo());
			if (!item.hasCue()){
				item.createCue();
			}
		}
	}

	private void checkManifests() {
		for (ArchiveItem item : this.collection.getArchiveItems()) {
			// System.out.println("Checking manifest for item:" + item.getInfo());
			ManifestTest manifest = item.checkManifest();
			if (!manifest.hasPassed()) {
				if (manifest.getBinResult() != Result.DELETED && manifest.getCueResult() != Result.ADDED)
				{
					System.err.println("Manifest test failed for item" + item.getId());
					System.err.println("Test Failed:" + manifest.toString());
				}
			}
		}
	}
}
