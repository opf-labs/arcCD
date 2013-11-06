/**
 * 
 */
package org.opf_labs.arc_cd.collection;

/**
 * @author carl
 * 
 */
public final class ManifestTest {
	/**
	 * Enumeration used to record manifest test result
	 * @author carl
	 *
	 */
	public enum Result {
		/** OK Result */
		OK, 
		/** Item Added Result */
		ADDED,
		/** Item Deleted Result */
		DELETED,
		/** Item Altered Result */
		ALTERED;
	}

	/**	Default ManifestTest instance */
	public static final ManifestTest DEFAULT = new ManifestTest();

	private final Result infoResult;
	private final Result tocResult;
	private final Result binResult;
	private final Result cueResult;

	private ManifestTest() {
		this(Result.DELETED, Result.DELETED, Result.DELETED, Result.DELETED);
	}

	private ManifestTest(final Result infoResult, final Result tocResult,
			final Result binResult, final Result cueResult) {
		this.infoResult = infoResult;
		this.tocResult = tocResult;
		this.binResult = binResult;
		this.cueResult = cueResult;
	}

	/**
	 * @return the infoResult
	 */
	public Result getInfoResult() {
		return this.infoResult;
	}

	/**
	 * @return the tocResult
	 */
	public Result getTocResult() {
		return this.tocResult;
	}

	/**
	 * @return the binResult
	 */
	public Result getBinResult() {
		return this.binResult;
	}

	/**
	 * @return the cueResult
	 */
	public Result getCueResult() {
		return this.cueResult;
	}

	/**
	 * @return the hasPassed
	 */
	public boolean hasPassed() {
		return ((this.infoResult == Result.OK)
				&& (this.tocResult == Result.OK)
				&& (this.binResult == Result.OK)
				&& (this.cueResult == Result.OK));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ManifestTest [infoResult=" + this.infoResult + ", tocResult="
				+ this.tocResult + ", binResult=" + this.binResult
				+ ", cueResult=" + this.cueResult + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.binResult == null) ? 0 : this.binResult.hashCode());
		result = prime * result
				+ ((this.cueResult == null) ? 0 : this.cueResult.hashCode());
		result = prime * result
				+ ((this.infoResult == null) ? 0 : this.infoResult.hashCode());
		result = prime * result
				+ ((this.tocResult == null) ? 0 : this.tocResult.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManifestTest other = (ManifestTest) obj;
		if (this.binResult != other.binResult)
			return false;
		if (this.cueResult != other.cueResult)
			return false;
		if (this.infoResult != other.infoResult)
			return false;
		if (this.tocResult != other.tocResult)
			return false;
		return true;
	}

	/**
	 * @return the default ManifestTest instance for failed lookups and testing
	 */
	public static ManifestTest defaultInstance() {
		return DEFAULT;
	}

	/**
	 * Create a new instance from passed values.
	 * 
	 * @param infoResult the result of the info file test
	 * @param tocResult the result of the toc file test
	 * @param binResult the result of the bin file test
	 * @param cueResult the result of the cue file test
	 * @return the ManifestTest object
	 */
	public static ManifestTest fromValues(final Result infoResult, final Result tocResult,
			final Result binResult, final Result cueResult) {
		return new ManifestTest(infoResult, tocResult,
				binResult, cueResult);
	}
	/**
	 * @param item the item to manifest test
	 * @return the result of the test
	 */
	public static ManifestTest testItem(ArchiveItem item) {
		ItemManifest recordedState = item.getRecordedManifest();
		ItemManifest currentState = ItemManifest.fromItemDirectory(item);
		Result infoResult = calcResult(recordedState.getInfoMD5(), currentState.getInfoMD5());
		Result tocResult = calcResult(recordedState.getTocMD5(), currentState.getTocMD5());
		Result binResult = calcResult(recordedState.getBinMD5(), currentState.getBinMD5());
		Result cueResult = calcResult(recordedState.getCueMD5(), currentState.getCueMD5());
		return new ManifestTest(infoResult, tocResult, binResult, cueResult);
	}

	private static Result calcResult(String recordedMD5, String currentMD5) {
		if (recordedMD5.equalsIgnoreCase(currentMD5)) return Result.OK;
		if (recordedMD5.equals(ItemManifest.EMPTY_MD5)) return Result.ADDED;
		if (currentMD5.equalsIgnoreCase(ItemManifest.EMPTY_MD5)) return Result.DELETED;
		return Result.ALTERED;
	}
}
