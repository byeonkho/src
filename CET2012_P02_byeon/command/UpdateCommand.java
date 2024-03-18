package command;

import exceptions.CustomException;
import receiver.FileEditor;
import java.io.IOException;

/**
 * The UpdateCommand class implements the Command interface, handling the update of content within a file.
 * It allows updating a specific line in the file and supports undoing the operation by restoring the original content.
 */
public class UpdateCommand implements Command {
    /**
     * The FileEditor instance used to perform file operations like updating content.
     */
    private final FileEditor fileEditor;

    /**
     * The index of the line in the file to be updated. It is zero-based internally.
     */
    private final int index;

    /**
     * The new content string that will replace the existing line at the index.
     */
    private final String inputString;

    /**
     * Stores the original content of the line before the update operation, used for undoing the change.
     */
    private String beforeUpdatedLine;

    /**
     * Constructs an UpdateCommand to update a line specified in the inputString in the file managed by fileEditor.
     * The line number to update is extracted from the inputString and converted to a zero-based index.
     *
     * @param fileEditor The FileEditor instance used for file operations.
     * @param inputString The string containing the index and new content for the update.
     * @throws NumberFormatException if the index part of inputString is not a valid integer.
     */
    public UpdateCommand(FileEditor fileEditor, String inputString) {
        this.fileEditor = fileEditor;
        this.inputString = inputString;
        this.index = FileEditor.getIndexFromString(inputString) - 1;
    }


    /**
     * Executes the update operation by replacing the line at the specified index with the new content.
     * Before updating, it stores the current content of the line for possible undoing.
     *
     * @throws IOException if an I/O error occurs during file access.
     * @throws IndexOutOfBoundsException if the index is out of the file's bounds.
     */
    @Override
    public void execute() throws IOException, IndexOutOfBoundsException {
        this.beforeUpdatedLine = this.fileEditor.getLineAtIndex(index);
        if (this.beforeUpdatedLine == null) {
            throw new IndexOutOfBoundsException("No line found at index: " + index);
        }
        this.fileEditor.updateAction(index, inputString);
    }

    /**
     * Undoes the update operation by restoring the line's content to its state before the update.
     *
     * @throws CustomException if a custom error occurs during the undo operation.
     * @throws IOException if an I/O error occurs during file access.
     */
    @Override
    public void undo() throws CustomException, IOException {
        this.fileEditor.undoUpdateAction(index, beforeUpdatedLine);
    }

    /**
     * Gets the content of the line before it was updated by this command.
     *
     * @return The content of the line before the update.
     */
    public String getBeforeUpdatedLine() {
        return beforeUpdatedLine;
    }
}
