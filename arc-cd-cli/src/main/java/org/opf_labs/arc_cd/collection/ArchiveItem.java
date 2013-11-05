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
import org.opf_labs.audio.CueParser;
import org.opf_labs.audio.CueSheet;

import com.google.common.base.Preconditions;

/**
 * @author carl
 *
 */
public final class ArchiveItem {
	public static final File DEFAULT_ROOT = new File(".");
	public static final CueSheet DEFAULT_CUE = new CueSheet();
	public static final ArchiveItem DEFAULT = new ArchiveItem();
	private static final Logger LOGGER = Logger
			.getLogger(ArchiveItem.class);
	
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
	public CdItemRecord getInfo() {
		return (this.hasInfo()) ? CdItemRecord.fromInfoFile(this.getInfoFile()) : CdItemRecord.defaultItem();
	}
	public TocItemRecord getToc() {
		return (this.hasToc()) ? TocItemRecord.fromTocFile(this.getTocFile()) : TocItemRecord.defaultInstance();
	}

	public CueSheet getCue() {
		try {
			return this.hasCue() ? CueParser.parse(this.getCueFile()) : DEFAULT_CUE;
		} catch (IOException excep) {
			LOGGER.error(excep);
			return DEFAULT_CUE;
		}

	}
	
	public ItemManifest getManifest() {
		return this.hasManifest() ? ItemManifest.fromManifestFile(this.getManifestFile()) : ItemManifest.defaultInstance();
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
	 * @return
	 */
	public String getRootInfoPath() {
		return this.rootDirectory.getParent() + File.separator + this.cdItem.getFormattedId() + "." + ArchiveCollection.INFO_EXT;
	}
	public String getInfoPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator + this.cdItem.getFormattedId() + "." + ArchiveCollection.INFO_EXT;
	}
	public String getTocPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator + this.cdItem.getFormattedId() + "." + ArchiveCollection.TOC_EXT;
	}
	public String getCuePath() {
		return this.rootDirectory.getAbsolutePath() + File.separator + this.cdItem.getFormattedId() + "." + ArchiveCollection.CUE_EXT;
	}
	public String getBinPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator + this.cdItem.getFormattedId() + "." + ArchiveCollection.BIN_EXT;
	}
	public String getManifestPath() {
		return this.rootDirectory.getAbsolutePath() + File.separator + this.cdItem.getFormattedId() + "." + ArchiveCollection.MANIFEST_EXT;
	}
	
	public File getRootInfoFile() {
		return new File(this.getRootInfoPath());
	}
	public File getInfoFile() {
		return new File(this.getInfoPath());
	}
	public File getTocFile() {
		return new File(this.getTocPath());
	}
	public File getCueFile() {
		return new File(this.getCuePath());
	}
	public File getBinFile() {
		return new File(this.getBinPath());
	}
	public File getManifestFile() {
		return new File(this.getManifestPath());
	}
	
	public boolean hasInfo() {
		return existsAndIsFile(this.getInfoPath());
	}
	public boolean hasToc() {
		return existsAndIsFile(this.getTocPath());
	}
	public boolean hasCue() {
		return existsAndIsFile(this.getCuePath());
	}
	public boolean hasBin() {
		return existsAndIsFile(this.getBinPath());
	}
	public boolean hasManifest() {
		return existsAndIsFile(this.getManifestPath());
	}
	/**
	 * @return true if the item is archived, false otherwise
	 */
	public boolean isArchived() {
		return this.hasInfo() && this.hasToc() && this.hasBin() && this.hasManifest();
	}

	/**
	 * write out the manifest file.
	 * @throws IOException
	 */
	public void writeManifestFile() throws IOException {
		File manFile = new File(this.getManifestPath());
		if (manFile.exists()) manFile.delete();
		ItemManifest manifest = ItemManifest.fromDirectory(this);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(manFile))) {
			writer.write(manifest.toString());
			IOUtils.closeQuietly(writer);
		}
	}
	public static ArchiveItem fromValues(File collectionRoot, CataloguedCd item) {
		Preconditions.checkNotNull(collectionRoot, "collectionRoot is null");
		Preconditions.checkNotNull(item, "item is null");
		File itemRoot = new File(collectionRoot.getAbsolutePath() + File.separator + item.getFormattedId());
		if (itemRoot.exists()) {
			if (itemRoot.isFile()) throw new IllegalArgumentException("Item root dir " + itemRoot + " is a file");
		} else {
			if (!itemRoot.mkdirs()) throw new IllegalStateException("Cannot create item directory:" + itemRoot);
		}
		
		// Find the info item in root if it's there and move it to the item dir
		ArchiveItem archItem = new ArchiveItem(itemRoot, item);
		File rootInfo = new File(archItem.getRootInfoPath());
		File archInfo = new File(archItem.getInfoPath());
		if (!archInfo.exists() && rootInfo.exists()) {
			if (!rootInfo.renameTo(archInfo)) throw new IllegalStateException("Couldn't rename info file.");
		} else if (archInfo.exists() && rootInfo.exists()) {
			rootInfo.delete();
		}
		if (!archItem.hasBin() | !archItem.hasInfo() | !archItem.hasToc())
			throw new IllegalStateException("Item " + item.getFormattedId() + " has not been archived correctly");
		return archItem;
	}

	public static ArchiveItem fromDirectory(File itemDir) {
		Preconditions.checkNotNull(itemDir, "itemDir is null");
		Preconditions.checkArgument(itemDir.isDirectory(), "itemDir:" + itemDir.getAbsolutePath() + " is not a directory.");
		int idFromDirName = Integer.parseInt(itemDir.getName());
		File infoFile = new File(itemDir.getAbsolutePath() + File.separator + itemDir.getName() + "." + ArchiveCollection.INFO_EXT);
		CdItemRecord item = CdItemRecord.fromInfoFile(infoFile);
		CataloguedCd cd = CataloguedCd.fromValues(new Integer(idFromDirName), item);
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
