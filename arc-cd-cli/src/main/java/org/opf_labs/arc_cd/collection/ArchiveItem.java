/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Preconditions;

/**
 * @author carl
 *
 */
public final class ArchiveItem {

	public static final File DEFAULT_ROOT = new File(".");
	public static final ArchiveItem DEFAULT = new ArchiveItem();
	
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
	 * @return the collection's root directory
	 */
	public File getRootDirectory() {
		return this.rootDirectory;
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
	private String createManifestString() throws FileNotFoundException, IOException {
		StringBuilder builder = new StringBuilder();

		File digestFile = new File(this.getInfoPath());
		try (BufferedInputStream infoStr = new BufferedInputStream(new FileInputStream(digestFile))) {
			String infoMd5 = DigestUtils.md5Hex(infoStr);
			builder.append("info:" + infoMd5 + "\n");
			IOUtils.closeQuietly(infoStr);
		}

		File tocFile = new File(this.getTocPath());
		try (BufferedInputStream tocStr = new BufferedInputStream(new FileInputStream(tocFile))) {
			String tocMd5 = DigestUtils.md5Hex(tocStr);
			builder.append("toc:" + tocMd5 + "\n");
			IOUtils.closeQuietly(tocStr);
		}
		
		File binFile = new File(this.getBinPath());
		try (BufferedInputStream binStr = new BufferedInputStream(new FileInputStream(binFile))) {
			String binMd5 = DigestUtils.md5Hex(binStr);
			builder.append("bin:" + binMd5 + "\n");
			IOUtils.closeQuietly(binStr);
		}
		return builder.toString();
	}
	/**
	 * write out the manifest file.
	 * @throws IOException
	 */
	public void writeManifestFile() throws IOException {
		File manFile = new File(this.getManifestPath());
		if (manFile.exists()) manFile.delete();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(manFile))) {
			writer.write(this.createManifestString());
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

	public static ArchiveItem fromDirectory(File itemDir) throws FileNotFoundException {
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
