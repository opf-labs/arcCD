/**
 * 
 */
package org.opf_labs.arc_cd.collection;

/**
 * @author carl
 * 
 */
public class ManifestTest {
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
	private final boolean passed;

	private ManifestTest() {
		this(Result.DELETED, Result.DELETED, Result.DELETED, Result.DELETED);
	}

	private ManifestTest(final Result infoResult, final Result tocResult,
			final Result binResult, final Result cueResult) {
		this.infoResult = infoResult;
		this.tocResult = tocResult;
		this.binResult = binResult;
		this.cueResult = cueResult;
		this.passed = ((this.infoResult == Result.OK)
				&& (this.tocResult == Result.OK)
				&& (this.binResult == Result.OK)
				&& (this.cueResult == Result.OK));
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
	 * @return the default ManifestTest instance for failed lookups and testing
	 */
	public static ManifestTest defaultInstance() {
		return DEFAULT;
	}

	/**
	 * @return the hasPassed
	 */
	public boolean hasPassed() {
		return this.passed;
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
