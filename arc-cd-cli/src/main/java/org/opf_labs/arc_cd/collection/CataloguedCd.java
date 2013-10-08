/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import com.google.common.base.Preconditions;

/**
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 *          Created 29 Sep 2013:20:54:39
 */
// TODO - JavaDoc for CataloguedItem
// TODO - Tests for CataloguedItem
public final class CataloguedCd {
	/** The default ID */
	public static final Integer DEFAULT_ID = new Integer(-1);
	/** The maximum ID */
	public static final Integer MAX_ID = new Integer(100000);
	/** The default Item */
	public static final CataloguedCd DEFAULT = new CataloguedCd(DEFAULT_ID,
			CdItemRecord.DEFAULT_ITEM);
	private final Integer id;
	private final CdItemRecord cdDetails;

	private CataloguedCd(final Integer id, final CdItemRecord cdDetails) {
		this.id = id;
		this.cdDetails = cdDetails;
	}

	/**
	 * Factory method for CataloguedCd instances.
	 * 
	 * @param id
	 *            the unique numeric id assigned to the cd
	 * @param cdDetails
	 *            the recorded details of the CD
	 * @return the CataloguedCd instance
	 */
	public static CataloguedCd fromValues(final Integer id,
			final CdItemRecord cdDetails) {
		Preconditions.checkNotNull(id, "id is null");
		Preconditions.checkArgument(id.compareTo(MAX_ID) < 0);
		Preconditions.checkNotNull(cdDetails, "cdDetails is null");
		return new CataloguedCd(id, cdDetails);
	}
	
	/**
	 * @return the default instance of CataloguedCd.
	 */
	public static CataloguedCd defaultInstance() {
		return DEFAULT;
	}

	/**
	 * @return the integer id assigned to this CD
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * @return instance's id as a five digit, zero padded string
	 */
	public String getFormattedId() {
		return formatIdToString(this.id);
	}

	/**
	 * @return the CD details
	 */
	public CdItemRecord getCdDetails() {
		return this.cdDetails;
	}
	
	/**
	 * @param id the Integer id to format
	 * @return the integer as a five digit, zero padded string
	 * @throws IllegalArgumentException if id >= MAX_ID
	 */
	public static String formatIdToString(Integer id) {
		Preconditions.checkArgument(id.compareTo(MAX_ID) < 0);
		return String.format("%05d", id);
	}
}
