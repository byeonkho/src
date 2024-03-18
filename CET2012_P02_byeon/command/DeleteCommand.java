package command;

import receiver.FileEditor;
import java.io.IOException;

/**
 * The DeleteCommand class implements the Command interface and encapsulates the deletion of a line from a file.
 * This command stores the state necessary to delete a specific line and to undo this deletion if needed.
 */
public class DeleteCommand implements Command {
    /**
     * The line index to be deleted.
     */
    private final int index;
    /**
     * The instance of FileEditor.
     */
    private final FileEditor fileEditor;
    /**
     * The line to be deleted, saved for future Undo operations.
     */
    private String deletedLine;

    /**
     * Constructs a DeleteCommand for a specific line index using the given FileEditor.
     * The index is adjusted to be zero-based internally.
     *
     * @param fileEditor The FileEditor instance used for file operations.
     * @param index The line index to be deleted, assuming 1-based input for user convenience.
     */
    public DeleteCommand(FileEditor fileEditor, int index) {
        this.index = index - 1;  // Adjust index to be zero-based
        this.fileEditor = fileEditor;
    }

    /**
     * Constructs a DeleteCommand using a string to represent the line index.
     * This constructor parses the string to an integer and adjusts for zero-based indexing.
     *
     * @param fileEditor The FileEditor instance used for file operations.
     * @param index String representing the line index to delete, assumed to be 1-based.
     */
    public DeleteCommand(FileEditor fileEditor, String index) {
        this.index = Integer.parseInt(index.trim()) - 1; // Convert string to integer and adjust for zero-based indexing
        this.fileEditor = fileEditor;
    }

    /**
     * Executes the delete action using the FileEditor, removing the specified line from the file.
     * The deleted line is stored in case undo is called later.
     *
     * @throws IndexOutOfBoundsException if the specified index is out of the file's line range.
     * @throws IOException if an I/O error occurs during the file operation.
     */
    @Override
    public void execute() throws IndexOutOfBoundsException, IOException {
        if (index < 0 || index >= this.fileEditor.getLinesSize()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        this.deletedLine = this.fileEditor.getLineAtIndex(index);
        if (this.deletedLine == null) {
            throw new IndexOutOfBoundsException("No line found at index: " + index);
        }

        this.fileEditor.deleteAction(index);
    }

    /**
     * Undoes the delete action performed by this command, restoring the deleted line to the file.
     *
     * @throws IndexOutOfBoundsException if the specified index is out of the file's line range.
     * @throws IOException if an I/O error occurs during the file operation.
     */
    @Override
    public void undo() throws IndexOutOfBoundsException, IOException {
        this.fileEditor.undoDeleteAction(index, deletedLine);
    }

    /**
     * Gets the line that was deleted by this command.
     *
     * @return The deleted line as a String.
     */
    public String getDeletedLine() {
        return deletedLine;
    }

    /**
     * Gets the zero-based index of the line that was deleted.
     *
     * @return The index of the deleted line.
     */
    public int getIndex() {
        return index;
    }
}
