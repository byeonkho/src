package invoker;

import command.Command;
import exceptions.CustomException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Invoker class is responsible for executing a series of commands and managing the command history.
 * It allows for command execution and supports the functionality to undo commands if necessary.
 */
public class Invoker {

    /**
     * A list to store commands that are to be executed.
     */
    private final ArrayList<Command> commandsToExecute = new ArrayList<>();

    /**
     * Default constructor does nothing.
     */
    public Invoker () {
    }

    /**
     * Adds a set of commands to the list of commands to be executed.
     *
     * @param commands An array of Command objects to be added to the command execution list.
     */
    public void setCommandsForExecution(Command[] commands) {
        this.commandsToExecute.addAll(Arrays.asList(commands));
    }

    /**
     * Executes all commands stored in the command list and maintains a history of executed commands
     * to support undo operations.
     *
     * @param commandHistory A stack to store the history of executed commands for undo functionality.
     */
    public void executeCommand(Stack<Command> commandHistory) {
        for (Command cmd : commandsToExecute) {
            try {
                cmd.execute();
                commandHistory.push(cmd);
            } catch (CustomException e) {
                System.out.println("Failed to execute command: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Failed at IO: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index input: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("No element exists: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        commandsToExecute.clear();  // Clear the list after execution to avoid re-executing the same commands
    }
}
