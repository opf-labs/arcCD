/**
 * 
 */
package org.opf_labs.arc_cd.cli;

import java.util.Scanner;

import org.opf_labs.arc_cd.collection.CataloguedCd;

/**
 * @author carl
 * 
 */
public final class ArcCdInputProcessor {
	static Scanner in = new Scanner(System.in);

	private ArcCdInputProcessor() {
		throw new AssertionError();
	}

	/**
	 * Prompt user for numeric id, parses it and returns if parses OK.
	 * 
	 * @return the numeric id input by the user
	 */
	public static Integer getInputId() {
		Integer id = CataloguedCd.DEFAULT_ID;
		String cdId = "";
		System.out
				.println("Please enter a numerical ID of 5 or less digits for the CD");
		while (cdId.isEmpty()) {
			String cd = in.nextLine();
			if (cd.matches("^[\\d]{1,5}")) {
				id = new Integer(Integer.parseInt(cd));
				cdId = String.format("%05d", id);
			} else {
				System.out
						.println("Input should be a numerical ID of 5 digits or less");
			}
		}
		return id;
	}

	/**
	 * @return true if the user inputs y|Y, false otherwise
	 */
	public static boolean confirmChoice() {
		String resp = in.nextLine();
		return resp.equalsIgnoreCase("y");
	}
}
