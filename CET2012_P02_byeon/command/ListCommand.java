package command;

import exceptions.CustomException;
import receiver.FileEditor;

/**
 * The ListCommand class implements the Command interface and encapsulates the operation of listing
 * the contents of the file managed by the FileEditor. This command is used to display the current state
 * of the file to the user.
 */
public class ListCommand implements Command {

    /**
     * The FileEditor instance.
     */
    private final FileEditor fileEditor;

    /**
     * Constructs a ListCommand with the specified FileEditor.
     *
     * @param fileEditor The FileEditor instance used to list the contents of the file.
     */
    public ListCommand(FileEditor fileEditor) {
        this.fileEditor = fileEditor;
    }

    /**
     * Executes the command to list the lines of the file by invoking the FileEditor's method.
     *
     * @throws CustomException if an error occurs during listing the file contents.
     */
    @Override
    public void execute() throws CustomException {
        this.fileEditor.getLines();
    }

    /**
     * Undo operation for the ListCommand, which is not supported as listing is a read-only action.
     *
     * @throws CustomException to indicate that the List command cannot be undone.
     */
    @Override
    public void undo() throws CustomException {
        throw new CustomException("List command is not undoable.");
    }
}
