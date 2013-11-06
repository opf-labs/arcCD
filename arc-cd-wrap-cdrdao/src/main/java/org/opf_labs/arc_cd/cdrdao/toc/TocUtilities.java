/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.toc;

import java.util.regex.Pattern;

/**
 * @author carl
 *
 */
public final class TocUtilities {
	static final Pattern TRACK_START_COMMENT = Pattern
	.compile("^// Track ([0-9]+)");
	static final Pattern ISRC_PATTERN = Pattern
	.compile("^ISRC \"(.*)\"$");
	static final Pattern FILE_PATTERN = Pattern
	.compile("^FILE \"(.*)\" ([0-9:]+) ([0-9:]+)$");

	private TocUtilities() {
		throw new AssertionError("Should never be in default constructory");
	}
}
