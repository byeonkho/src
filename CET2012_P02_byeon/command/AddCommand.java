package command;

import receiver.FileEditor;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * The AddCommand class implements the Command interface and encapsulates the addition of text to a file.
 * This command is responsible for adding a specified string to a file and supports undoing the action.
 */
public class AddCommand implements Command {

    /**
     * The FileEditor instance.
     */
    private final FileEditor fileEditor;
    /**
     * The instance of the input string.
     */
    private final String inputString;

    /**
     * Constructs an AddCommand with a specified FileEditor and input string.
     *
     * @param fileEditor The FileEditor instance used for file operations.
     * @param inputString The string to be added to the file.
     */
    public AddCommand(FileEditor fileEditor, String inputString) {
        this.fileEditor = fileEditor;
        this.inputString = inputString;
    }

    /**
     * Executes the add action using the FileEditor, adding the inputString to the file.
     *
     * @throws IOException if an I/O error occurs during the file operation.
     * @throws IllegalArgumentException if the inputString is invalid for file addition.
     */
    @Override
    public void execute() throws IOException, IllegalArgumentException {
        this.fileEditor.addAction(inputString);
    }

    /**
     * Undoes the last add action performed by this command, removing the recently added string from the file.
     *
     * @throws NoSuchElementException if there is no element to undo.
     * @throws IOException if an I/O error occurs during the file operation.
     */
    @Override
    public void undo() throws NoSuchElementException, IOException {
        this.fileEditor.undoAddAction();
    }
}
