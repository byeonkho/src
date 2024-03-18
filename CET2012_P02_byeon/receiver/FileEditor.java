package receiver;

import exceptions.CustomException;
import validators.EmailValidator;
import validators.NameValidator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The FileEditor class manages file operations such as adding, deleting, updating, and listing lines.
 * It handles the core file manipulation logic for the application.
 */
public class FileEditor {

    /**
     * The number of input parameters expected for an add command.
     */
    public static final int ADDCMD_NUM_OF_INPUT_PARAMS = 3;
    /**
     * Stores the lines read from or to be written to the file.
     */
    private final List<String> lines;
    /**
     * The file name with which the FileEditor will work.
     */
    private final String FILENAME;

    /**
     * Constructs a FileEditor for managing file operations on a specified file.
     * It attempts to read the existing content of the file upon initialization.
     *
     * @param FILENAME The name of the file to be managed.
     */
    public FileEditor(String FILENAME) {
        this.FILENAME = FILENAME;
        this.lines = new ArrayList<>();

        try {
            readFile();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a line to the file based on the provided input string. The string is expected
     * to contain certain parts like name and email, separated by semicolons.
     *
     * @param inputString The string containing the data to be added to the file.
     * @throws IOException If an I/O error occurs during writing to the file.
     * @throws IllegalArgumentException If the input string format is invalid.
     */
    public void addAction(String inputString) throws IOException, IllegalArgumentException{
        if (inputString == null || inputString.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }

        String[] parts = inputString.split(";");
        if (parts.length != FileEditor.ADDCMD_NUM_OF_INPUT_PARAMS) {
            throw new IllegalArgumentException("Input string must contain exactly three parts separated by semicolons.");
        }

        String[] names = {parts[0], parts[1]};
        if (!NameValidator.areValidNames(names)) {
            throw new IllegalArgumentException("Name fields cannot be empty.");
        }

        String email = parts[2].trim();

        boolean[] emailValidationResults = EmailValidator.isValidEmail(email);
        if (!emailValidationResults[0] && !emailValidationResults[1]) {
            throw new IllegalArgumentException("Invalid email input");
        }

        // Check if email field input is regular string
        if (emailValidationResults[1]) {
            email = toTitleCase(email);
        }

        String trimmedFirstName = toTitleCase(parts[0].trim());
        String trimmedLastName = toTitleCase(parts[1].trim());

        String formattedLineToAdd = trimmedFirstName + " " + trimmedLastName + " " + email;
        lines.add(formattedLineToAdd);

        storeToFile(); // Throws IOException

        System.out.println("add action performed. size is: " + lines.size());
    }

    /**
     * Undoes the last add action, removing the most recently added line from the file.
     *
     * @throws NoSuchElementException If there are no lines to undo.
     * @throws IOException If an I/O error occurs during file modification.
     */
    public void undoAddAction() throws NoSuchElementException, IOException {
        if (lines.isEmpty()) {
            throw new NoSuchElementException("No lines to undo.");
        }
        String undidLines = lines.getLast();
        lines.removeLast();

        // If storeToFile() throws an IOException, it will be propagated up to the caller
        storeToFile();

        System.out.println("Undo add: Removed " + undidLines);
    }


    /**
     * Deletes a line from the file at the specified index.
     *
     * @param index The index of the line to be deleted.
     * @throws IOException If an I/O error occurs during file modification.
     * @throws IndexOutOfBoundsException If the index is out of the file's bounds.
     */
    public void deleteAction(int index) throws IOException{
        // Check if the index is valid
        if (index < 0 || index > lines.size()) {
            throw new IndexOutOfBoundsException("Delete action failed: Index " + index + " is out of bounds.");
        }
            // Remove the element at the specified index
            lines.remove(index);

            // Write the updated list back to the file
            storeToFile();
            System.out.println("Delete action performed at index: " + index + ". Updated size is: " + lines.size());

    }

    /**
     * Undoes the last delete action, restoring the deleted line at its original position.
     *
     * @param index The index at which the line was deleted.
     * @param deletedLine The content of the deleted line to be restored.
     * @throws IOException If an I/O error occurs during file modification.
     */
    public void undoDeleteAction(int index, String deletedLine) throws IOException{
        if ((index < 0 || index > lines.size())) {
            throw new IndexOutOfBoundsException("Undo Delete action failed: Index " + index + " is out of bounds.");
        }
            lines.add(index, deletedLine);
            storeToFile();  // Assume storeToFile() could throw an IOException
            System.out.println("Undo delete: Restored '" + deletedLine + "' at index " + index);

    }

    /**
     * Updates a line in the file at the specified index with the new content.
     *
     * @param index The index of the line to be updated.
     * @param inputString The new content to replace the existing line.
     * @throws IOException If an I/O error occurs during file modification.
     */
    public void updateAction(int index, String inputString) throws IOException, IllegalArgumentException {
        // Check if the index is valid
        if (index < 0 || index >= lines.size()) {
            throw new IndexOutOfBoundsException("Update action failed: Index " + index + " is out of bounds.");
        }

        // Split the input into parts
        String[] inputParts = inputString.split(";");

        // Validate the input parts count
        if (inputParts.length < 2) {
            throw new IllegalArgumentException("Invalid input string. Must contain at least firstName.");
        }

        // Extract and trim the parts of the input string
        String trimmedFirstName = toTitleCase(inputParts[1].trim());
        String[] names = new String[]{trimmedFirstName}; // Default to just firstName

        // Check and assign lastName and email if they exist
        String trimmedLastName = "";
        String email = "";
        if (inputParts.length > 2) {
            trimmedLastName = toTitleCase(inputParts[2].trim());
            names = new String[]{trimmedFirstName, trimmedLastName}; // Add lastName to names array
        }
        if (inputParts.length > 3) {
            email = inputParts[3].trim();
            names = new String[]{trimmedFirstName, trimmedLastName}; // Add email to names array
        }

        // Validate names using NameValidator
        if (!NameValidator.areValidNames(names)) {
            throw new IllegalArgumentException("Name fields cannot be empty or invalid.");
        }

        // Validate email if provided
        boolean[] emailValidationResults = EmailValidator.isValidEmail(email);
        if (!email.isEmpty() && !emailValidationResults[0] && !emailValidationResults[1]) {
            throw new IllegalArgumentException("Invalid email input.");
        }

        // Format and update the line
        String[] beforeUpdatedLineParts = getLineAtIndex(index).split(" ");

        // If there is an email field to update
        if (!email.isEmpty()) {
            // If the field is a regular input and not an email address
            if (emailValidationResults[1]) {
                email = toTitleCase(email);
                beforeUpdatedLineParts[2] = email;
            }

            // If input is an email address
            else {
                beforeUpdatedLineParts[2] = email;
            }
            beforeUpdatedLineParts[0] = trimmedFirstName;
            beforeUpdatedLineParts[1] = trimmedLastName;

        }

        // If there is firstName, lastName to update
        else if (!trimmedLastName.isEmpty()) {
            beforeUpdatedLineParts[0] = trimmedFirstName;
            beforeUpdatedLineParts[1] = trimmedLastName;
        }

        // If only firstName to update
        else {
            beforeUpdatedLineParts[0] = trimmedFirstName;
        }

        String formattedLineToAdd = String.join(" ", beforeUpdatedLineParts);

        lines.set(index, formattedLineToAdd);

        // Write the updated list back to the file
        storeToFile();
        System.out.println("Update action performed at index: " + index);
    }


    /**
     * Undoes the last update action, restoring the original content of the line.
     *
     * @param index The index of the line that was updated.
     * @param beforeUpdatedLine The original content of the line to be restored.
     * @throws IOException If an I/O error occurs during file modification.
     */
    public void undoUpdateAction(int index, String beforeUpdatedLine) throws IOException {
        // Check if the index is valid before proceeding
        if (index < 0 || index > lines.size()) {
            throw new IndexOutOfBoundsException("Undo update failed: Index " + index + " is out of bounds.");
        }
        // Directly replace the line at the index with the original line
        lines.set(index, beforeUpdatedLine);
        storeToFile();
        System.out.println("Undo update: Restored '" + beforeUpdatedLine + "' at index " + index);
    }


    // UTILITY FUNCTIONS

    /**
     * Converts a given string to title case, where the first letter of each word is capitalized,
     * and the rest of the letters are in lowercase.
     *
     * @param input The string to be converted to title case.
     * @return A title-cased version of the input string, or the input itself if it's null or empty.
     */
    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            }
            else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }

    /**
     * Writes the current state of the lines list to the file, overwriting its current contents.
     *
     * @throws IOException if an I/O error occurs during writing to the file.
     */
    private void storeToFile() throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, false));
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            writer.close();
        }
        catch (IOException e) {
            throw new IOException("Failed to write to file: " + e);
        }
    }

    /**
     * Reads the contents of the file into the lines list, initializing the state of the FileEditor.
     *
     * @throws IOException if an I/O error occurs during reading from the file.
     */
    public void readFile() throws IOException{
        File file = new File(FILENAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                throw new IOException("Failed to create file: " + FILENAME, e);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException e) {
            throw new IOException("Failed to read file: " + FILENAME, e);
        }
    }

    /**
     * Displays the lines of the file to the standard output. Used to list the current contents of the file.
     *
     * @throws CustomException if there are no lines to display.
     */
    public void getLines() throws CustomException {
        if (lines.isEmpty()) {
            throw new CustomException("There are no lines to display.");
        }

        for (String line : lines) {
            System.out.println(line);
        }
    }

    /**
     * Retrieves a specific line from the file based on the index provided.
     *
     * @param index The zero-based index of the line to retrieve.
     * @return The line at the specified index, or null if the index is out of bounds.
     */
    public String getLineAtIndex(int index) {
        if (index >= 0 && index < lines.size()) {
            return lines.get(index);
        }
        return null;
    }

    /**
     * Returns the total number of lines currently in the file.
     *
     * @return The number of lines in the file.
     */
    public int getLinesSize() {
        return lines.size();
    }

    /**
     * Parses an index from a given input string, typically used to extract an index for command operations.
     *
     * @param input The input string containing the index, usually as the first part before a separator.
     * @throws NumberFormatException if the index is not a valid integer.
     * @return The parsed index, adjusted to be zero-based.
     */
    public static int getIndexFromString(String input) {
        String[] parts = input.split(";");
        // Assuming the first part of the split string is the index
        if (parts.length > 0) {
            try {
                int index = Integer.parseInt(parts[0].trim());  // Trim and parse the integer
                return index;  // Return the parsed index
            } catch (NumberFormatException e) {
                // Log the error or handle it as necessary
                System.out.println("Error parsing index: " + e.getMessage());
                return -1;
            }
        } else {
            return -1;
        }
    }

}

