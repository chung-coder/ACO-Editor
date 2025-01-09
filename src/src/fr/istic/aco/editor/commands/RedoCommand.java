package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.UndoManager;

/**
 * The {@code RedoCommand} class implements the functionality for redoing the last undone action in the text editor.
 */
public class RedoCommand implements Command {

    private UndoManager undoManager;

    /**
     * Constructs a {@code RedoCommand} with the specified dependencies.
     *
     * @param undoManager the undo manager for handling undo and redo functionality
     */
    public RedoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    /**
     * Executes the redo operation.
     * <p>
     * This method invokes the {@link UndoManager#redo()} method to redoing the last undone action
     * in the text editor.
     * </p>
     */
    @Override
    public void execute(){
        undoManager.redo();
    }
}
