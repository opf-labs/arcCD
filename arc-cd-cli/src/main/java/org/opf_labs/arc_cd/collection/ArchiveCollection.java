/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.opf_labs.arc_cd.cdrdao.toc.TocItemRecord;
import org.opf_labs.audio.CueSheet;

/**
 * @author carl
 * 
 */
public final class ArchiveCollection {
	/** Extension for bin files */
	public static final String BIN_EXT = "bin";
	/** Extension for temp TOC files */
	public static final String TEMP_TOC_EXT = "toctmp";
	/** Extension for TOC files */
	public static final String TOC_EXT = "toc";
	/** Extension for CUE files */
	public static final String CUE_EXT = "cue";
	/** Extension for manifest files */
	public static final String MANIFEST_EXT = "man";
	/** Extension for info files */
	public static final String INFO_EXT = "info";
	private static final Logger LOGGER = Logger
			.getLogger(ArchiveCollection.class);

	private final File rootDirectory;
	private Map<Integer, CataloguedCd> toArchive = new HashMap<>();
	private Set<Integer> parseErrors = new HashSet<>();
	private Map<Integer, ArchiveItem> archived = new HashMap<>();

	/**
	 * @param rootPath
	 */
	public ArchiveCollection(String rootPath) {
		this(new File(rootPath));
	}

	/**
	 * @param root
	 */
	public ArchiveCollection(File root) {
		if (!root.isDirectory()) {
			LOGGER.fatal("Archive Collection root directory "
					+ root.getAbsolutePath()
					+ " should be an existing directory.");
			throw new IllegalArgumentException(
					"root should be an existing directory.");
		}
		this.rootDirectory = root;
		this.clearAndRepopulateArchiveMapsFromDisk();
	}

	/**
	 * @return the number of items in the archive
	 */
	public int size() {
		return this.archived.size();
	}
	
	/**
	 * @param id the id if the item to retrieve
	 * @return the item with the id passed or ArchiveItem.DEFAULT if not found
	 */
	public ArchiveItem getArchiveItem(Integer id) {
		if (this.archived.containsKey(id)) {
			return this.archived.get(id);
		}
		return ArchiveItem.DEFAULT;
	}
	
	/**
	 * @return the set of ids in the collection
	 */
	public Set<Integer> getArchiveIds() {
		return this.archived.keySet();
	}
	
	/**
	 * @param id the id of the Catalogued Item to retrieve
	 * @return the item with the id passed, or CataloguedCD.DEFAULT if not found
	 */
	public CataloguedCd getCataloguedItem(Integer id) {
		if (this.toArchive.containsKey(id)) {
			return this.toArchive.get(id);
		}
		return CataloguedCd.DEFAULT;
	}
	/**
	 * @return the set of ids of info files to be archived
	 */
	public Set<Integer> getCataloguedIds() {
		return this.toArchive.keySet();
	}
	
	/**
	 * @return the collection of archive items
	 */
	public Collection<ArchiveItem> getArchiveItems() {
		return this.archived.values();
	}
	/**
	 * @param id
	 *            the id of the item to retrieve
	 * @return the item found at that id, or the default item if non is found.
	 */
	public CdItemRecord getItemRecord(Integer id) {
		ArchiveItem item = this.archived.get(id);
		return (item != null) ? item.getInfo() : CdItemRecord.defaultItem();
	}

	/**
	 * Retrieve the TocItemRecord for an Integer id.
	 * @param id the Integer id of the item for which the TOC object is requested
	 * @return the TocItemRecord object for the Integer id
	 */
	public TocItemRecord getItemToc(Integer id) {
		ArchiveItem item = this.archived.get(id);
		return (item != null) ? item.getToc() : TocItemRecord.defaultInstance();
	}

	/**
	 * Retrieve the CueSheet for an Integer id.
	 * @param id the Integer id of the item for which the CueSheet is requested
	 * @return the CueSheet object for the Integer id
	 */
	public CueSheet getItemCueSheet(Integer id) {
		ArchiveItem item = this.archived.get(id);
		return (item != null) ? item.getCue() : ArchiveItem.DEFAULT_CUE;
	}

	/**
	 * Test the manifest for an ArchiveItem 
	 * @param id the Integer id of the item to manifest test
	 * @return the ManifestTest object holding the test results
	 */
	public ManifestTest checkItemManifest(Integer id) {
		ArchiveItem item = this.archived.get(id);
		return (item != null) ? item.checkManifest() : ManifestTest.defaultInstance();
	}

	/**
	 * @param id
	 *            the id of the item to archive
	 * @return true if archived successfully
	 * @throws IOException
	 */
	public boolean archiveItem(final int id) throws IOException {
		Integer intId = Integer.valueOf(id);
		if (this.toArchive.containsKey(intId)) {
			CataloguedCd cdToArchive = this.toArchive.get(intId);
			File infoFile = new File(this.rootDirectory.getAbsolutePath()
					+ File.separator + cdToArchive.getFormattedId() + "."
					+ INFO_EXT);
			File dest = new File(this.rootDirectory.getAbsolutePath()
					+ File.separator + cdToArchive.getFormattedId()
					+ File.separator + cdToArchive.getFormattedId() + "."
					+ INFO_EXT);

			if (!infoFile.exists())
				return false;
			infoFile.renameTo(dest);
			File itemDir = new File(this.rootDirectory.getAbsolutePath()
					+ File.separator + cdToArchive.getFormattedId());
			ArchiveItem archItem = ArchiveItem.fromDirectory(itemDir);
			archItem.createCue();
			archItem.writeManifestFile();
			return true;
		}
		return false;
	}

	/**
	 * @param infoFile
	 *            a file instance for the file containing the item record
	 * @return the CdItemRecord read from the file
	 * @throws FileNotFoundException
	 *             when the file can not be found
	 */
	public static CdItemRecord readItemRecordFromFile(File infoFile)
			throws FileNotFoundException {
		return CdItemRecord.fromInfoFile(infoFile);
	}

	/**
	 * @param id
	 *            the id of the Info file to open
	 * @return the info file for id id
	 */
	public File getInfoFile(int id) {
		Integer intId = Integer.valueOf(id);
		if (this.toArchive.containsKey(intId)) {
			return new File(String.format("%s%s%sd.%s",
					this.rootDirectory.getAbsolutePath(), File.separator,
					CataloguedCd.formatIdToString(intId), INFO_EXT));
		} else if (this.archived.containsKey(intId)) {
			return new File(String.format("%s%s%s%s%s.%s",
					this.rootDirectory.getAbsolutePath(), File.separator,
					CataloguedCd.formatIdToString(intId), File.separator, CataloguedCd.formatIdToString(intId), INFO_EXT));
		}
		return null;
	}

	private int clearAndRepopulateArchiveMapsFromDisk() {
		clearArchiveMaps();
		for (File file : this.rootDirectory.listFiles()) {
			LOGGER.debug("file:" + file);
			if (file.isDirectory()) {
				addArchiveItemFromDirectory(file);
			} else if (CdItemRecord.isInfoFile(file)) {
				addCataloguedCdFromFile(file);
			}
		}
		return this.archived.size();
	}

	private void clearArchiveMaps() {
		this.toArchive.clear();
		this.archived.clear();
	}

	private void addArchiveItemFromDirectory(final File directory) {
		try {
			Integer id = new Integer(Integer.parseInt(FilenameUtils
					.getBaseName(directory.getName())));
			ArchiveItem itemFromDirectory = ArchiveItem.DEFAULT;
			itemFromDirectory = ArchiveItem.fromDirectory(directory);
			this.archived.put(id, itemFromDirectory);
		} catch (NumberFormatException excep) {
			// No need for alarm, don't process non-numeric dirs
		}
	}

	private void addCataloguedCdFromFile(final File file) {
		Integer id = new Integer(Integer.parseInt(FilenameUtils
				.getBaseName(file.getName())));
		CdItemRecord item = CdItemRecord.defaultItem();
		CataloguedCd cd = CataloguedCd.defaultInstance();
		try {
			item = readItemRecordFromFile(file);
			cd = CataloguedCd.fromValues(id, item);
		} catch (FileNotFoundException excep) {
			LOGGER.warn("Problem reading item record for id:" + id + " from "
					+ file.getAbsolutePath());
			logAndRecordParseException(id, excep);
		}
		LOGGER.debug("Adding to archive:" + cd);
		this.toArchive.put(id, cd);
	}

	private void logAndRecordParseException(final Integer id,
			final Exception excep) {
		LOGGER.warn(excep.getMessage());
		LOGGER.warn(excep.getStackTrace());
		this.parseErrors.add(id);
	}
}
