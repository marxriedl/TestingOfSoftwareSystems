package at.jku.fim.datalinksimulation.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: OSI Simulation Framework
 * </p>
 * <p>
 * Copyright: (c) 2004-07
 * </p>
 * <p>
 * Company: FIM@JKU, www.fim.uni-linz.ac.at
 * </p>
 * 
 * @author Students
 * @version $Id: CRCUtils.java,v 1.1 2005/10/27 13:10:11 aputzinger Exp $
 */

public class CRCUtils {

	final static private long GENERATOR = 0x104A11DB7l;

	/**
	 * This methods performs the "sanity-check" of a frame. It tests if the
	 * frame has changed during transmission by evaluating the CRC.<BR>
	 * 
	 * @param frame
	 *            Complete EasyL2 frame data; the last 32 Bits (last 4 entries
	 *            in array) represent the CRC
	 * @param logger
	 *            Logger to use
	 * @return Returns true, if CRC check is ok; false otherwise
	 */
	public static boolean validateCRC(int[] frame, Logger logger) {
		// Please consider, that the integer elements of frame may only contain
		// values from 0 to 255

		// frame = <FRAMEDATA> <CRC>
		// 1. calculate Remainder:
		// calculateRemainder calculates the remainder of the division frame /
		// GENERATOR in GF2
		// like the pencil and paper division discussed in the lectures
		// 2. check if remainder is 0
		// remainder = 0 : CRC is OK
		// remainder != 0 : CRC is not OK

		int remainder = calculateRemainder(frame);

		logger.log(Level.ALL, "CRC validated: "
				+ (remainder == 0 ? "CRC OK" : "CRC wrong!"));

		return remainder == 0;
	}

	/**
	 * Calculates the CRC of a frame and insert the CRC into the frame.
	 * 
	 * @param frame
	 *            Complete frame data; the last 32 bits (last 4 entries in
	 *            array) are unset and have to be filled with the CRC
	 * @param logger
	 *            Logger to use
	 */
	public static void insertCrc(int[] frame, Logger logger) {
		// Please consider, that the integer elements of frame may only contain
		// values from 0 to 255

		// frame = <FRAMEDATA> <undefined>
		// 1. clear last 32 bits of frame for CRC:
		// frame = <FRAMEDATA> 0000 0000 0000 0000 0000 0000 0000 0000
		// 2. calculate Remainder => CRC:
		// calculateRemainder calculates the remainder of the division frame /
		// GENERATOR in GF2
		// like the pencil and paper division discussed in the lectures
		// 3. add remainder (CRC) to frame:
		// frame = <FRAMEDATA> <CRC>

		for (int i = 0; i < 4; i++) {
			// clear last 32 bits of frame for crc
			frame[frame.length - 1 - i] = 0;
		}

		// calculate remainder of the division frame / GENERATOR in GF2
		int remainder = calculateRemainder(frame);

		// add remainder (CRC) to frame
		for (int i = 0; i < 4; i++) {
			frame[frame.length - 1 - i] = (remainder >> i * 8 & 0xFF);
		}

		logger.log(Level.ALL, "CRC calculated and attached to frame: "
				+ Integer.toBinaryString((int) remainder));

	}

	private static int calculateRemainder(int[] frame) {
		long remainder = 0;
		int temp;

		for (int i = 0; i < frame.length * 8; i++) {
			remainder = remainder << 1; // make space for new bit

			// fill with next bit of frame
			temp = frame[i / 8]; // get current frame
			temp = (temp >> (7 - i % 8)) & 1; // get current bit of frame
			remainder = (remainder | temp); // add current frame to remainder

			if (remainder >> 32 > 0) {
				// if remainder fits into generator, i.e. in GF2 the 33rd bit of
				// remainder = 1
				// calculate new remainder
				remainder = remainder ^ GENERATOR;

				// example: remainder = 1011000010, generator pol. = 1110010011
				// 1011000010000 : 1110010011 =
				// 1110010011
				// 0101010001 --> new remainder

			}

		}

		return (int) remainder;
	}

}