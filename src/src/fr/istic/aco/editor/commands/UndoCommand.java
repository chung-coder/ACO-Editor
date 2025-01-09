package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.UndoManager;

/**
 * The {@code RedoCommand} class implements the functionality for undoing the last performed action in the text editor.
 */
public class UndoCommand implements Command {
    private UndoManager undoManager;

    /**
     * Constructs a {@code UndoCommand} with the specified dependencies.
     *
     * @param undoManager the undo manager for handling undo and redo functionality
     */
    public UndoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    /**
     * Executes the undo operation.
     * <p>
     * This method invokes the {@link UndoManager#undo()} method to undo the most recent action
     * in the text editor.
     * </p>
     */
    @Override
    public void execute(){
        undoManager.undo();
    }
}
