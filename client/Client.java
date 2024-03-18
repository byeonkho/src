package client;

import command.*;
import receiver.FileEditor;
import invoker.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The Client class serves as the entry point for the application, demonstrating the use of the command pattern
 * through various commands to manipulate and interact with a text file. It initializes the necessary components
 * like Invoker, FileEditor, and command history, then executes a series of commands to showcase their functionality.
 */
public class Client {

    /**
     * Default constructor does nothing.
     */
    public Client () {

    }
    /**
     * The filename for the data store, where the application will read from and write to.
     */
    public static final String FILENAME = "src/dataStore.txt";

    /**
     * The main method serves as the entry point for the application.
     * It initializes the system, creates commands, and executes them through the Invoker.
     * This method demonstrates how different commands can be added to a list, converted into an array,
     * and then executed in sequence by the invoker.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        /**
         * The invoker instance.
         */
        final Invoker invoker = new Invoker();
        /**
         * The FileEditor instance.
         */
        final FileEditor fileEditor = new FileEditor(FILENAME);
        /**
         * The stack for storing the command history, used for Undo operations.
         */
        final Stack<Command> commandHistory = UndoCommand.commandHistory;
        /**
         * The list of commands to be passed to the invoker.
         */
        final List<Command> commandList = new ArrayList<>();

        commandList.add(new AddCommand(fileEditor, "firstName; lastName; email@gmail.com"));
        commandList.add(new AddCommand(fileEditor, "firstName; lastName; validinput"));
        commandList.add(new UpdateCommand(fileEditor, "1;updatedFirstName;updatedLastName;updatedEmail@gmail.com;a;a;" +
                "a"));
//        commandList.add(new DeleteCommand(fileEditor, 1));
//        commandList.add(new UndoCommand());
//        commandList.add(new UndoCommand());

        commandList.add(new ListCommand(fileEditor));

        Command[] commands = commandList.toArray(new Command[0]); // Prep commands for passing into invoker

        invoker.setCommandsForExecution(commands);
        invoker.executeCommand(commandHistory);
    }
}
