package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.CommandOriginator;
import fr.istic.aco.editor.core.Engine;
import fr.istic.aco.editor.core.Recorder;
import fr.istic.aco.editor.core.UndoManager;
import fr.istic.aco.editor.memento.EngineMemento;
import fr.istic.aco.editor.memento.Memento;
/**
 * The {@code CutSelectedCommand} class implements the functionality for cutting the
 * currently selected text into the clipboard.
 */
public class CutSelectedCommand implements CommandOriginator {
    private Engine engine;
    private Recorder recorder;
    private UndoManager undoManager;

    /**
     * Constructs a {@code CutSelectedCommand} with the specified dependencies.
     *
     * @param engine the text editing engine responsible for text editing actions
     * @param recorder the command recorder for saving and replaying the command
     * @param undoManager the undo manager for handling undo and redo functionality
     */
    public CutSelectedCommand(Engine engine, Recorder recorder, UndoManager undoManager){
        this.engine=engine;
        this.recorder=recorder;
        this.undoManager=undoManager;
    }

    /**
     * Returns {@code null} since this command does not require saving state for the Memento pattern.
     *
     * @return always {@code null}, as this command does not save state
     */
    @Override
    public EngineMemento getMemento() {
        return null;
    }

    /**
     * Does nothing since there is no state to restore for this command.
     *
     * @param memento the memento object (ignored)
     */
    @Override
    public void setMemento(Memento memento) {}

    /**
     * Executes the command by cutting the selected text into the clipboard.
     * <p>
     * Before executing, the current state of the {@link Engine} is stored in the
     * {@link UndoManager} as a memento. The command is also saved in the {@link Recorder}
     * for replay functionality.
     * </p>
     */
    @Override
    public void execute(){
        EngineMemento m = (EngineMemento) engine.getMemento();
        undoManager.store(m);
        engine.cutSelectedText();
        recorder.save(this);
    }
}
