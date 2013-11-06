/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord;
import org.opf_labs.arc_cd.cue.CueUtilities;
import org.opf_labs.audio.CueParser;
import org.opf_labs.audio.CueSheet;

import com.google.common.base.Preconditions;

/**
 * @author carl
 * 
 */
public final class ArchiveItem {
	/** A default root file for an item, use the current directory */
	public static final File DEFAULT_ROOT = new File(".");
	/** A default CUE sheet for failed lookups and testing */
	public static final CueSheet DEFAULT_CUE = new CueSheet();
	/** The default ArchiveItem instance for failed lookups and testing */
	public static final ArchiveItem DEFAULT = new ArchiveItem();
	private static final Logger LOGGER = Logger.getLogger(ArchiveItem.class);

	private ItemState state = ItemState.CATALOGUED;
	private final File rootDirectory;
	private final CataloguedCd cdItem;

	private ArchiveItem() {
		this(DEFAULT_ROOT, CataloguedCd.DEFAULT);
	}

	private ArchiveItem(final File root, final CataloguedCd cataloguedItem) {
		this.rootDirectory = root;
		this.cdItem = cataloguedItem;
	}

	/**
	 * @return the unique, numeric id assigned to this CD
	 */
	public Integer getId() {
		return this.cdItem.getId();
	}

	/**
	 * @return the CD Details for the archive cd
	 */
	public CataloguedCd getItem() {
		return this.cdItem;
	}

	/**
	 * @return the CdItemRecord for this item
	 */
	public CdItemRecord getInfo() {
		return (this.hasInfo()) ? CdItemRecord.fromInfoFile(this.getInfoFile())
				: CdItemRecord.defaultItem();
	}

	/**
	 * @return the TocItemRecord for this item
	 */
	public TocItemRecord getToc() {
		return (this.hasToc()) ? TocItemRecord.fromTocFile(this.getTocFile())
				: TocItemRecord.defaultInstance();
	}

	/**
	 * @return the CueSheet for this item
	 */
	public CueSheet getCue() {
		try {
			return this.hasCue() ? CueParser.parse(this.getCueFile())
					: DEFAULT_CUE;
		} catch (IOException excep) {
			LOGGER.error(excep);
			return DEFAULT_CUE;
		}

	}

	/**
	 * 
	 * @return the recorded ItemManifest for this item
	 */
	public ItemManifest getRecordedManifest() {
		return this.hasManifest() ? ItemManifest.fromManifestFile(this
				.getManifestFile()) : ItemManifest.defaultInstance();
	}

	/**
	 * @return the ManifestTest result of checking this item's manifest
	 */
	public ManifestTest checkManifest() {
		return ManifestTest.testItem(this);
	}

	/**
	 * @return the collection's root directory
	 */
	public File getRootDirectory() {
		return this.rootDirectory;
	}

	/**
	 * @return the items state
	 */
	public ItemState getState() {
		return this.state;
	}

	/**
	 * Will create a CUE file for the item if non-exists.
	 * @return true if a cue sheet exists or is created successfully 
	 */
	public boolean createCue() {
		return createCue(false);
	}

	/**
	 * @param overwrite if set true will overwrite the existing CUE sheet
	 * @return true if 
	 */
	public boolean createCue(final boolean overwrite) {
		if (!this.hasInfo() || !this.hasToc()) {
			return false;
		}
		if (this.hasCue()) {
			if (!overwrite) {
				return true;
			}
			if (!this.getCueFile().delete()) {
				return false;
			}
		}
		File cueFile = this.getCueFile();
		CueSheet cueSheet = CueUtilities.cueFromTocAndInfo(this.getToc(), this.getInfo());
		return CueUtilities.cueSheetToFile(cueSheet, cueFile);
	}
	/**
	 * @return the path to the original info file name before archiving
	 */
	public String getRootInfoPath() {
		return this.rootDirectory.getParent() + File.separator
				+ this.cdItem.getFormattedId() + "."
				+ ArchiveCollection.INFO_EXT;
	}

	/**
	 * @return the String path to the item's info file
	 */
	public String getInfoPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator
				+ this.cdItem.getFormattedId() + "."
				+ ArchiveCollection.INFO_EXT;
	}

	/**
	 * @return the String path to the item's TOC file
	 */
	public String getTocPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator
				+ this.cdItem.getFormattedId() + "."
				+ ArchiveCollection.TOC_EXT;
	}

	/**
	 * @return the String path to the item's CUE file
	 */
	public String getCuePath() {
		return this.rootDirectory.getAbsolutePath() + File.separator
				+ this.cdItem.getFormattedId() + "."
				+ ArchiveCollection.CUE_EXT;
	}

	/**
	 * @return the String path to the item's bin file
	 */
	public String getBinPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator
				+ this.cdItem.getFormattedId() + "."
				+ ArchiveCollection.BIN_EXT;
	}

	/**
	 * @return the String path to the item's manifest file
	 */
	public String getManifestPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator
				+ this.cdItem.getFormattedId() + "."
				+ ArchiveCollection.MANIFEST_EXT;
	}

	/**
	 * @return the item's root info file File object
	 */
	public File getRootInfoFile() {
		return new File(this.getRootInfoPath());
	}

	/**
	 * @return the item's info file File object
	 */
	public File getInfoFile() {
		return new File(this.getInfoPath());
	}

	/**
	 * @return the item's TOC file File object
	 */
	public File getTocFile() {
		return new File(this.getTocPath());
	}

	/**
	 * @return the item's CUE file File object
	 */
	public File getCueFile() {
		return new File(this.getCuePath());
	}

	/**
	 * @return the item's bin file File object
	 */
	public File getBinFile() {
		return new File(this.getBinPath());
	}

	/**
	 * @return the item's manifest file File object
	 */
	public File getManifestFile() {
		return new File(this.getManifestPath());
	}

	/**
	 * @return true if item has an info file
	 */
	public boolean hasInfo() {
		return existsAndIsFile(this.getInfoPath());
	}

	/**
	 * @return true if item has a TOC file
	 */
	public boolean hasToc() {
		return existsAndIsFile(this.getTocPath());
	}

	/**
	 * @return true if item has a CUE file
	 */
	public boolean hasCue() {
		return existsAndIsFile(this.getCuePath());
	}

	/**
	 * @return true if item has a bin file
	 */
	public boolean hasBin() {
		return existsAndIsFile(this.getBinPath());
	}

	/**
	 * @return true if item has a manifest file
	 */
	public boolean hasManifest() {
		return existsAndIsFile(this.getManifestPath());
	}

	/**
	 * @return true if the item is archived, false otherwise
	 */
	public boolean isArchived() {
		return this.hasInfo() && this.hasToc() && this.hasBin()
				&& this.hasManifest();
	}

	/**
	 * write out the manifest file.
	 * 
	 * @throws IOException
	 */
	public void writeManifestFile() throws IOException {
		File manFile = new File(this.getManifestPath());
		if (manFile.exists())
			manFile.delete();
		ItemManifest manifest = ItemManifest.fromItemDirectory(this);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(manFile))) {
			writer.write(manifest.toString());
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * Create a new ArchiveItem from a collection root id and an catalogued CD
	 * item
	 * 
	 * @param collectionRoot
	 *            the root of the archive collection
	 * @param item
	 *            the catalogued Item
	 * @return the new archive item
	 */
	public static ArchiveItem fromValues(File collectionRoot, CataloguedCd item) {
		Preconditions.checkNotNull(collectionRoot, "collectionRoot is null");
		Preconditions.checkNotNull(item, "item is null");
		File itemRoot = new File(collectionRoot.getAbsolutePath()
				+ File.separator + item.getFormattedId());
		if (itemRoot.exists()) {
			if (itemRoot.isFile())
				throw new IllegalArgumentException("Item root dir " + itemRoot
						+ " is a file");
		} else {
			if (!itemRoot.mkdirs())
				throw new IllegalStateException("Cannot create item directory:"
						+ itemRoot);
		}

		// Find the info item in root if it's there and move it to the item dir
		ArchiveItem archItem = new ArchiveItem(itemRoot, item);
		File rootInfo = new File(archItem.getRootInfoPath());
		File archInfo = new File(archItem.getInfoPath());
		if (!archInfo.exists() && rootInfo.exists()) {
			if (!rootInfo.renameTo(archInfo))
				throw new IllegalStateException("Couldn't rename info file.");
		} else if (archInfo.exists() && rootInfo.exists()) {
			rootInfo.delete();
		}
		if (!archItem.hasBin() | !archItem.hasInfo() | !archItem.hasToc())
			throw new IllegalStateException("Item " + item.getFormattedId()
					+ " has not been archived correctly");
		return archItem;
	}

	/**
	 * Creates a new ArchiveItem from a collection item directory
	 * 
	 * @param itemDir
	 *            the directory for this item
	 * @return the new ArchiveItem created from the directory
	 */
	public static ArchiveItem fromDirectory(File itemDir) {
		Preconditions.checkNotNull(itemDir, "itemDir is null");
		Preconditions
				.checkArgument(itemDir.isDirectory(),
						"itemDir:" + itemDir.getAbsolutePath()
								+ " is not a directory.");
		int idFromDirName = Integer.parseInt(itemDir.getName());
		File infoFile = new File(itemDir.getAbsolutePath() + File.separator
				+ itemDir.getName() + "." + ArchiveCollection.INFO_EXT);
		CdItemRecord item = CdItemRecord.fromInfoFile(infoFile);
		CataloguedCd cd = CataloguedCd.fromValues(new Integer(idFromDirName),
				item);
		return new ArchiveItem(itemDir, cd);
	}

	private static boolean existsAndIsFile(String pathToTest) {
		File toTest = new File(pathToTest);
		return existsAndIsFile(toTest);
	}

	private static boolean existsAndIsFile(File toTest) {
		return toTest.exists() && toTest.isFile();
	}
}
