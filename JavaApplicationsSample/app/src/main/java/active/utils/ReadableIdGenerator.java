package active.utils;

public final class ReadableIdGenerator {
    // All possible chars for representing a Base31 value
    static final char[] DIGITS_BASE31 = {'0', '1', '2', '3', '4', '5', '6',
      '7', '8', '9', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
      'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};

    // Default radix for this converter.
    static final int RADIX = DIGITS_BASE31.length;

    // Length of the check sequence (one character)
    static final int CHECK_LENGTH = 1;

    // Length of a buffer to store stringified number as according to the spec.
    // The specification limits the HRI to 7 characters, plus one check
    // character.
    static final int SPEC_BUFFER_LENGTH = 8;

    // log31(Long.MAX_VALUE) = 12.7, rounding up makes 13 digits plus one more
    // for a checksum character.
    static final int FULL_BUFFER_LENGTH = 14;

    private ReadableIdGenerator() {
    }

    /**
     * Converts an integer number into its stringified representation using
     * Base31.
     *
     * @param value positive long to be converted. Maximum allowable value is
     *              27512614110 with zeros if the number is too small.
     * @return string representation of the integer number using Base31, may
     * contain decimal digits from '0' to '9' and uppercase Latin
     * characters excluding vowels.
     */
    public static String idToString(long value) {
        // long saveValue = value;
        // Do not accept negative numbers. Values larger than Long.MAX_VALUE
        // also turn out negative.
        if (value < 0) {
            throw new IllegalArgumentException(
              "Value to be converted must be positive");
        }

        char[] buf = new char[FULL_BUFFER_LENGTH];

        // Start filling the buffer from the character next to the last one. The
        // last will be a checkum char.
        int charPos = FULL_BUFFER_LENGTH - 1 - CHECK_LENGTH;
        int value31 = 0;
        while (value >= RADIX) {
            value31 = (int) (value % RADIX);
            buf[charPos--] = DIGITS_BASE31[value31];
            value = value / RADIX;
        }
        value31 = (int) value;
        buf[charPos] = DIGITS_BASE31[value31];

        // Calculate the check character, same formula as for MOD43 check, but
        // adjusted for Base31
        buf[FULL_BUFFER_LENGTH - 1] = calculateChecksum(buf, charPos,
          buf.length - 1 - CHECK_LENGTH);

        // Left-pad the generated value with zeros if value takes less than 7
        // digits
        while (charPos > FULL_BUFFER_LENGTH - SPEC_BUFFER_LENGTH) {
            buf[--charPos] = '0';
        }

        String strId = new String(buf, charPos, FULL_BUFFER_LENGTH - charPos);
        // Logger.debug(ReadableIdGenerator.class,
        // "idToString(" + saveValue + ") : [" + strId + "]" + (saveValue >
        // MAX_VALUE ? " [7+ digits]" : ""));
        return strId;

    }

    /**
     * Calculates the check character for a Base31 HRI.
     *
     * @param buf      buffer where Base31 is stored
     * @param startPos start position (leftmost, high-order) digit to start
     * @param endPos   ending position (rightmost, low-order) position
     * @return a check character from Base31 alphabet
     */
    private static char calculateChecksum(char[] buf, int startPos, int endPos) {
        int checkSum = 0;
        for (int curPos = startPos; curPos <= endPos; curPos++) {
            char curChar = buf[curPos];
            for (int k = 0; k < DIGITS_BASE31.length; k++) {
                if (DIGITS_BASE31[k] == curChar) {
                    checkSum += k;
                    break;
                }
            }
        }
        return DIGITS_BASE31[checkSum % RADIX];
    }
    
    public static void main(String[] args) {
    	System.out.println(idToString(10016));
	}
}
