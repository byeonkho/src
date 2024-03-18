package command;

import exceptions.CustomException;
import java.io.IOException;

/**
 * The Command interface defines the structure for command objects used in the application.
 * It enforces the implementation of the execute and undo methods, allowing for operations
 * to be performed and reverted as part of the command pattern implementation.
 */
public interface Command {
    /**
     * Executes the command, performing the action encapsulated by the command implementation.
     * This method may throw exceptions if the command encounters issues during execution.
     *
     * @throws CustomException if there are application-specific errors during execution.
     * @throws IOException if there is an I/O error during the execution.
     */
    void execute() throws CustomException, IOException;

    /**
     * Reverses the action performed by the execute method, effectively "undoing" the command's effect.
     * This method may throw exceptions if the command encounters issues during the undo operation.
     *
     * @throws CustomException if there are application-specific errors during the undo operation.
     * @throws IOException if there is an I/O error during the undo operation.
     */
    void undo() throws CustomException, IOException;
}
