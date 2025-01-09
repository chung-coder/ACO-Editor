package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.*;
import fr.istic.aco.editor.memento.EngineMemento;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.SelectionMemento;


/**
 * The {@code MoveSelection} class implements the functionality for selecting the text within the interval
 */
public class MoveSelection implements CommandOriginator {
    private Engine engine;
    private int previousEndIndex;
    private Invoker invoker;
    private Recorder recorder;
    private UndoManager undoManager;

    /**
     * Constructs a {@code MoveSelection} with the specified dependencies.
     *
     * @param engine the text editing engine responsible for text editing actions
     * @param invoker the invoker object that provides the text to be inserted
     * @param recorder the command recorder for saving and replaying the command
     * @param undoManager the undo manager for handling undo and redo functionality
     */
    public MoveSelection(Engine engine, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        this.engine = engine;
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    /**
     * Creates and returns a memento representing the current state of the selection command.
     * <p>
     * The memento stores the beginning and ending indices of the selection range, which are retrieved from the {@link Invoker}.
     * </p>
     *
     * @return a memento containing the selection range (beginning and ending indices)
     */
    @Override
    public Memento getMemento() {
        SelectionMemento m = new SelectionMemento();
        m.setBegin(invoker.getBeginIndex());
        m.setEnd(invoker.getEndIndex());
        return m;
    }

    /**
     * Restores the state of the command from the provided memento.
     * <p>
     * The beginning and ending indices stored in the memento are restored to the {@link Invoker}.
     * </p>
     *
     * @param memento the memento object containing the state to restore
     */
    @Override
    public void setMemento(Memento memento) {
        int begin = ((SelectionMemento) memento).getBegin();
        int end = ((SelectionMemento) memento).getEnd();
        invoker.setBeginIndex(begin);
        invoker.setEndIndex(end);

    }

    /**
     * Executes the command to update the text selection range.
     * <p>
     * This method performs the following steps:
     * <ul>
     *   <li>Saves the current state of the {@link Engine} in the {@link UndoManager} to enable undo functionality.</li>
     *   <li>Updates the selection range using the beginning and ending indices provided by the {@link Invoker}.</li>
     *   <li>Records the command in the {@link Recorder} for replay functionality.</li>
     * </ul>
     */
    @Override
    public void execute(){
        // Save the current state of the engine for undo/redo functionality
        EngineMemento m = (EngineMemento) engine.getMemento();
        undoManager.store(m);

        // Save the previous end index for validation
        previousEndIndex = engine.getSelection().getEndIndex();
        // Set up the new selection range
        if(previousEndIndex > invoker.getBeginIndex()){
            engine.getSelection().setBeginIndex(invoker.getBeginIndex());
            engine.getSelection().setEndIndex(invoker.getEndIndex());
        } else {
            engine.getSelection().setEndIndex(invoker.getEndIndex());
            engine.getSelection().setBeginIndex(invoker.getBeginIndex());
        }

        // Record the command for replay functionality
        recorder.save(this);
    }
}