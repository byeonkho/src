package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The EmailValidator class provides static methods for validating email strings.
 * It checks if the input is either an accepted email pattern or alternate regular input string, and returns a
 * boolean array for both validations.
 */
public class EmailValidator {
    private static final String emailRegex = "^(?![-.])(?:(?!\\.\\-|\\-\\.))[A-Za-z0-9_]+(?:[.-][A-Za-z0-9_]+)*(?<![-" +
            ".])@" +
            "(?![-.])[A-Za-z0-9]+(?:[.-][A-Za-z0-9]+)*(?<![.-])\\.[a-z]{2,3}$" ;
    private static final String emailAlternate = "^\\w+$" ;
    private static final Pattern emailPattern = Pattern.compile(emailRegex);
    private static final Pattern alternatePattern = Pattern.compile(emailAlternate);

    private EmailValidator() {
    }

    /**
     * Validates if the given string is a valid email address according to the defined patterns.
     * Returns an array where the first boolean indicates if the standard email pattern matches,
     * and the second boolean indicates if the alternate pattern matches.
     *
     * @param email The string to be validated as an email address.
     * @return An array of two booleans indicating the results of standard and alternate pattern matching.
     */
    public static boolean[] isValidEmail(String email) {
        boolean[] result = new boolean[2];
        if (email != null) {
            Matcher matcher = emailPattern.matcher(email);
            Matcher alternateMatcher = alternatePattern.matcher(email);
            result[0] = matcher.matches();
            result[1] = alternateMatcher.matches();
        }
        return result; // Default is {false, false} if email is null
    }
}
