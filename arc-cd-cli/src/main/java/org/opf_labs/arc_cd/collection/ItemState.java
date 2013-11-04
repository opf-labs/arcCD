/**
 * 
 */
package org.opf_labs.arc_cd.collection;

/**
 * @author carl
 *
 */
public enum ItemState {
	/** CD Item has had an identifier assigned */
	IDENTIFIED,
	/** CD Item has been catalogued, an info file has been created */
	CATALOGUED,
	/** The CD Audio data has been ripped to a bin file */
	STABILISED,
	/** The CUE file has been generated and a manifest created */
	ARCHIVED;
}
