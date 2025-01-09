package fr.istic.aco.editor.commands;

/**
 * Represents a generic command in the text editor application.
 * Each command encapsulates a distinct action or operation that can be performed within the editor.
 */

public interface Command {

    /**
     * Executes the encapsulated action defined by the command.
     * Each implementing class defines the specific behavior to be performed when this method is invoked.
     */
    void execute();
}
