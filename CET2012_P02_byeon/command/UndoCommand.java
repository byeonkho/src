package command;

import exceptions.CustomException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The UndoCommand class implements the Command interface and provides the functionality
 * to undo the last executed command. It uses a stack to keep track of the command history.
 */
public class UndoCommand implements Command {

    /**
     * A static stack to keep track of the command history for undo operations.
     */
    public final static Stack<Command> commandHistory = new Stack<>();

    /**
     * Default constructor does nothing.
     */
    public UndoCommand() {
    }

    /**
     * Executes the undo operation by popping the last command from the history stack
     * and calling its undo method. This action reverses the effect of the last command executed.
     *
     * @throws CustomException           if the command history is empty or an application-specific error occurs.
     * @throws IOException               if an I/O error occurs during the undo operation.
     * @throws NoSuchElementException    if no element exists to pop from the command history.
     * @throws IllegalArgumentException  if an argument passed to the undo command is invalid.
     * @throws IndexOutOfBoundsException if an index used during the undo operation is out of bounds.
     */
    @Override
    public void execute() throws CustomException, IOException, NoSuchElementException, IllegalArgumentException,
            IndexOutOfBoundsException {
        if (commandHistory.isEmpty()) {
            throw new CustomException("Command history is empty. Nothing to undo.");
        }

        Command topCommand = commandHistory.pop();
        topCommand.undo();
    }

    /**
     * Provides an undo functionality for the UndoCommand itself, which "skips" the current UndoCommand in the event
     * there are consecutive UndoCommands in the stack.
     *
     * @throws CustomException if the command history is empty or an application-specific error occurs.
     * @throws IOException     if an I/O error occurs during the undo operation.
     */
    @Override
    public void undo() throws CustomException, IOException {
        if (commandHistory.isEmpty()) {
            throw new CustomException("Command history is empty. Nothing to undo.");
        }
        Command topCommand = commandHistory.pop();
        topCommand.undo();
    }
}
