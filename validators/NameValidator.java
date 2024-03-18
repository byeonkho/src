package validators;

/**
 * The NameValidator class provides static methods for validating name strings.
 * It checks if the provided names are not null, not empty, and do not consist only of whitespace.
 */
public class NameValidator {

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private NameValidator() {}

    /**
     * Validates that all provided strings in the variable argument list are not null, not empty,
     * and not only whitespace. It ignores strings containing '@' symbol, which are presumably email addresses.
     *
     * @param names The array of names (or strings) to validate.
     * @return true if all names are valid, false otherwise. A valid name is not null, not empty,
     * and contains more than just whitespace.
     */
    public static boolean areValidNames(String... names) {
        for (String name : names) {
            if (name.contains("@")) {
                continue; // Skip validation for strings containing '@' assuming they are emails
            }
            if (name == null || name.trim().isEmpty()) {
                return false; // Return false if any name is null, empty, or only whitespace
            }
        }
        return true; // Return true if all names pass the validation check
    }
}
