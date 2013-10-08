/**
 * 
 */
package org.opf_labs.arc_cd.collection;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * TODO JavaDoc for CataloguedCdStack.</p>
 * TODO Tests for CataloguedCdStack.</p>
 * TODO Implementation for CataloguedCdStack.</p>
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 1 Oct 2013:16:08:06
 */

public final class CataloguedCdManager {
	/** The default ID */
	public static final Integer DEFAULT_ID = Integer.valueOf(-1);
	private final NavigableMap<Integer, CataloguedCd> managedCdRecords;
	private Integer currentId = DEFAULT_ID;
	
	private CataloguedCdManager() {
		throw new AssertionError("Do not enter default constructor for " + this.getClass().getName());
	}
	
	private CataloguedCdManager(final Map<Integer, CataloguedCd> cdsToManage) {
		this.managedCdRecords = new TreeMap<>(cdsToManage);
		this.managedCdRecords.put(DEFAULT_ID, CataloguedCd.DEFAULT);
	}
	
	/**
	 * @return the current Integer id 
	 */
	public Integer getCurrentId() {
		return this.currentId;
	}
	
	/**
	 * @return the sorted set of all ids in the catalogue
	 */
	public SortedSet<Integer> getAllIds() {
		return Collections.unmodifiableSortedSet(new TreeSet<>(this.managedCdRecords.keySet()));
	}
	
	/**
	 * @param requestedId
	 * @return the catalogue record for the requested id
	 */
	public CataloguedCd getCatalogueRecordById(Integer requestedId) {
		if (this.managedCdRecords.containsKey(requestedId)) this.currentId = requestedId;
		return this.managedCdRecords.get(requestedId);
	}
	
	/**
	 * @return the current catalogue record
	 */
	public CataloguedCd getCurrentCatalogueRecord() {
		return this.getCatalogueRecordById(this.currentId);
	}
	/**
	 * @return the first record in the catalogue, ie. the lowest id
	 */
	public CataloguedCd getFirstCatalogueRecord() {
		this.currentId = this.managedCdRecords.firstKey();
		return this.managedCdRecords.get(this.currentId);
	}
	
	/**
	 * @return the next catalogue record, this becomes the current record
	 */
	public CataloguedCd getNextCatalogueRecord() {
		this.currentId = this.managedCdRecords.ceilingKey(this.currentId);
		if (this.currentId == null) {
			this.currentId = DEFAULT_ID;
		}
		return this.managedCdRecords.get(this.currentId);
	}
	
	/**
	 * @param idToRemove
	 */
	public void removeCatalogueRecord(Integer idToRemove) {
		this.managedCdRecords.remove(idToRemove);
		this.getNextCatalogueRecord();
	}
	
	/**
	 * @return the number of items in the catalogue
	 */
	public int size() {
		return this.managedCdRecords.size();
	}
}
